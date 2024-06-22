package com.yessorae.data.repository

import com.yessorae.data.di.ChartTrainerDispatcher
import com.yessorae.data.di.Dispatcher
import com.yessorae.data.source.ChartNetworkDataSource
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.local.database.model.asDomainModel
import com.yessorae.data.source.local.database.model.asEntity
import com.yessorae.data.source.local.preference.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.network.polygon.model.chart.asDomainModel
import com.yessorae.domain.common.ChartRequestArgumentHelper
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.exception.ChartGameException
import com.yessorae.domain.repository.ChartRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ChartRepositoryImpl @Inject constructor(
    private val networkDataSource: ChartNetworkDataSource,
    private val localDBDataSource: ChartTrainerLocalDBDataSource,
    private val appPreferences: ChartTrainerPreferencesDataSource,
    private val chartRequestArgumentHelper: ChartRequestArgumentHelper,
    @Dispatcher(ChartTrainerDispatcher.IO)
    private val dispatcher: CoroutineDispatcher
) : ChartRepository {
    override suspend fun fetchNewChartRandomly(totalTurn: Int): Chart =
        withContext(dispatcher) {
            fetchNewChartRandomlyWithRetry(
                currentRetryCount = 0,
                totalTurn = totalTurn
            )
        }

    private suspend fun fetchNewChartRandomlyWithRetry(
        currentRetryCount: Int,
        totalTurn: Int
    ): Chart {
        // RETRY_COUNT 만큼 시도했는데도 실패하면 IllegalStateException 발생.
        // 거의 발생하지 않아도 안전망 역할
        if (currentRetryCount > RETRY_COUNT) {
            throw ChartGameException.HardToFetchTradeException
        }

        val dto = networkDataSource
            .getChart(
                ticker = chartRequestArgumentHelper.getRandomTicker(),
                tickUnit = appPreferences.getTickUnit(),
                from = chartRequestArgumentHelper.getFromDate(),
                to = chartRequestArgumentHelper.getToDate()
            )

        // 서버에서 가져온 차트가 totalTurn 보다 작으면 다시 요청
        if ((dto.ticks.size) < totalTurn) {
            return fetchNewChartRandomlyWithRetry(
                currentRetryCount = currentRetryCount + 1,
                totalTurn = totalTurn
            )
        }

        val chart = dto.asDomainModel(TickUnit.DAY)

        val chartId = localDBDataSource.insertChart(chart.asEntity())
        localDBDataSource.insertTicks(chart.ticks.map { it.asEntity(chartId = chartId) })
        return chart.copy(id = chartId)
    }

    // TODO::LATER 인-메모리 캐싱. ChartTrainerInMemoryDataSource 같은 이름으로 만들어서 활용.
    // Chart 데이터는 게임 시작하고나서 변하지 않는다. 또한, 다음 턴을 호출할 때마다 참조된다.
    // 따라서 메모리 캐싱 해두면 매번 DB에 다녀올 필요 없이 성능을 개선할 수 있다.
    override suspend fun fetchChart(gameId: Long): Chart {
        val chartId = localDBDataSource.getChartId(gameId = gameId)
        val chartEntity = localDBDataSource.getChart(id = chartId)
        val ticks = localDBDataSource.getTicks(chartId = chartId)

        return chartEntity.asDomainModel(ticks.map { it.asDomainModel() })
    }

    companion object {
        private const val RETRY_COUNT = 3
    }
}
