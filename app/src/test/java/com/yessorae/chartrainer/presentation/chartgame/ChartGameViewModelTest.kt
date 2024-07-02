package com.yessorae.chartrainer.presentation.chartgame

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.yessorae.chartrainer.MainDispatcherRule
import com.yessorae.chartrainer.fake.FakeChartDao
import com.yessorae.chartrainer.fake.FakeChartGameDao
import com.yessorae.chartrainer.fake.FakeChartRequestArgumentHelper
import com.yessorae.chartrainer.fake.FakeChartTrainerPreferencesDataSource
import com.yessorae.chartrainer.fake.FakePolygonChartApi
import com.yessorae.chartrainer.fake.FakeTickDao
import com.yessorae.chartrainer.fake.FakeTradeDao
import com.yessorae.chartrainer.fake.model.createChartDto
import com.yessorae.chartrainer.fake.model.createTickDto
import com.yessorae.data.repository.ChartGameRepositoryImpl
import com.yessorae.data.repository.ChartRepositoryImpl
import com.yessorae.data.repository.TradeRepositoryImpl
import com.yessorae.data.repository.UserRepositoryImpl
import com.yessorae.data.source.ChartNetworkDataSource
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.local.database.ChartTrainerLocalDBDataSourceImpl
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
import com.yessorae.data.source.network.polygon.PolygonChartNetworkDataSource
import com.yessorae.data.source.network.polygon.api.PolygonChartApi
import com.yessorae.domain.common.ChartRequestArgumentHelper
import com.yessorae.domain.common.ChartTrainerLogger
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.TradeRepository
import com.yessorae.domain.repository.UserRepository
import com.yessorae.domain.usecase.ChangeChartUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeChartGameUseCase
import com.yessorae.domain.usecase.TradeStockUseCase
import com.yessorae.domain.usecase.UpdateNextTickUseCase
import com.yessorae.presentation.ui.screen.chartgame.ChartGameViewModel
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenUserAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ChartGameViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val dispatcherRule = MainDispatcherRule(dispatcher)

    private lateinit var polygonChartApi: PolygonChartApi
    private lateinit var chartGameDao: ChartGameDao
    private lateinit var chartDao: ChartDao
    private lateinit var tickDao: TickDao
    private lateinit var tradeDao: TradeDao

    private lateinit var chartRequestArgumentHelper: ChartRequestArgumentHelper

    // datasource
    private lateinit var chartNetworkDataSource: ChartNetworkDataSource
    private lateinit var localDBDataSource: ChartTrainerLocalDBDataSource
    private lateinit var chartTrainerPreferencesDataSource: ChartTrainerPreferencesDataSource

    // repository
    private lateinit var userRepository: UserRepository
    private lateinit var chartRepository: ChartRepository
    private lateinit var tradeRepository: TradeRepository
    private lateinit var chartGameRepository: ChartGameRepository

    // usecase
    private lateinit var subscribeChartGameUseCase: SubscribeChartGameUseCase
    private lateinit var changeChartUseCase: ChangeChartUseCase
    private lateinit var tradeStockUseCase: TradeStockUseCase
    private lateinit var updateNextTickUseCase: UpdateNextTickUseCase
    private lateinit var quitChartGameUseCase: QuitChartGameUseCase
    private lateinit var logger: ChartTrainerLogger
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ChartGameViewModel

    private val testTicks = (1..300).map {
        createTickDto(
            closePrice = it.toDouble(),
            openPrice = it.toDouble(),
            maxPrice = it.toDouble(),
            minPrice = it.toDouble(),
            startTimestamp = it.toLong(),
            volumeWeightedAveragePrice = it.toDouble(),
            transactionCount = it
        )
    }
    private val APPLE_TICKER = "AAPL"
    private val TESLA_TICKER = "TSLA"


    fun setup(
        chartApi: PolygonChartApi = FakePolygonChartApi(),
        randomChartRequestArgumentHelper: ChartRequestArgumentHelper = FakeChartRequestArgumentHelper()
    ) {
        // api
        polygonChartApi = chartApi

        // dao
        chartGameDao = FakeChartGameDao()
        tickDao = FakeTickDao()
        chartDao = FakeChartDao(ticksFlow = (tickDao as FakeTickDao).ticksFlow)
        tradeDao = FakeTradeDao()

        // datasource
        chartNetworkDataSource = PolygonChartNetworkDataSource(
            api = polygonChartApi
        )
        localDBDataSource = ChartTrainerLocalDBDataSourceImpl(
            chartGameDao = chartGameDao,
            chartDao = chartDao,
            tradeDao = tradeDao,
            tickDao = tickDao
        )
        chartTrainerPreferencesDataSource = FakeChartTrainerPreferencesDataSource()

        // helper
        chartRequestArgumentHelper = randomChartRequestArgumentHelper

        // repository
        userRepository = UserRepositoryImpl(
            appPreference = chartTrainerPreferencesDataSource
        )
        chartRepository = ChartRepositoryImpl(
            networkDataSource = chartNetworkDataSource,
            localDBDataSource = localDBDataSource,
            appPreferences = chartTrainerPreferencesDataSource,
            dispatcher = dispatcher,
            chartRequestArgumentHelper = chartRequestArgumentHelper
        )
        tradeRepository = TradeRepositoryImpl(
            localDBDataSource = localDBDataSource
        )
        chartGameRepository = ChartGameRepositoryImpl(
            localDataSource = localDBDataSource,
            chartGamePreferencesDataSource = chartTrainerPreferencesDataSource,
            dispatcher = dispatcher
        )

        // usecase
        subscribeChartGameUseCase = SubscribeChartGameUseCase(
            userRepository = userRepository,
            chartRepository = chartRepository,
            chartGameRepository = chartGameRepository
        )
        changeChartUseCase = ChangeChartUseCase(
            userRepository = userRepository,
            chartRepository = chartRepository,
            chartGameRepository = chartGameRepository
        )
        tradeStockUseCase = TradeStockUseCase(
            chartGameRepository = chartGameRepository,
            tradeRepository = tradeRepository,
            userRepository = userRepository
        )
        updateNextTickUseCase = UpdateNextTickUseCase(
            chartGameRepository = chartGameRepository,
            chartRepository = chartRepository,
            userRepository = userRepository
        )
        quitChartGameUseCase = QuitChartGameUseCase(
            userRepository = userRepository,
            chartGameRepository = chartGameRepository
        )

        logger = object : ChartTrainerLogger {
            override fun cehLog(throwable: Throwable) {
                // do nothing
            }
            // 기획자가 요청한 데이터가 있다면 mocking 하고 상호작용도 테스트할 것
        }
        savedStateHandle = SavedStateHandle()

        viewModel = ChartGameViewModel(
            subscribeChartGameUseCase = subscribeChartGameUseCase,
            changeChartUseCase = changeChartUseCase,
            tradeStockUseCase = tradeStockUseCase,
            updateNextTickUseCase = updateNextTickUseCase,
            quitChartGameUseCase = quitChartGameUseCase,
            logger = logger,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun chart_is_changed_and_game_is_re_initialized_when_click_new_chart_button() = runTest {
        // given
        setup(
            chartApi = FakePolygonChartApi(
                tickerToDtoMap = mapOf(
                    APPLE_TICKER to createChartDto(ticker = APPLE_TICKER, ticks = testTicks),
                    TESLA_TICKER to createChartDto(ticker = TESLA_TICKER, ticks = testTicks)
                )
            ),
            randomChartRequestArgumentHelper = FakeChartRequestArgumentHelper(
                getTicker = { callingCount ->
                    if (callingCount > 0) {
                        APPLE_TICKER
                    } else {
                        TESLA_TICKER
                    }
                }
            )
        )
        viewModel.screenState.launchIn(this)

        // when
        viewModel.handleChartGameScreenUserAction(
            userAction = ChartGameScreenUserAction.ClickNewChartButton(
                gameId = 1L
            )
        )


        viewModel.screenState.test {
            assertEquals(
                ...
            )
        }
    }
}
