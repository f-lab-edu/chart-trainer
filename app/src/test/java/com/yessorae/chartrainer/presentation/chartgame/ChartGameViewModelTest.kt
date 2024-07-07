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
import com.yessorae.data.source.network.polygon.PolygonChartNetworkDataSource
import com.yessorae.domain.common.ChartTrainerLogger
import com.yessorae.domain.entity.ChartGame
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.entity.value.asMoney
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
import com.yessorae.presentation.ui.screen.chartgame.model.CandleStickChartUi
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameEvent
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenState
import com.yessorae.presentation.ui.screen.chartgame.model.ChartGameScreenUserAction
import com.yessorae.presentation.ui.screen.chartgame.model.TradeOrderUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChartGameViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val dispatcherRule = MainDispatcherRule(dispatcher)

    // fake
    private lateinit var polygonChartApi: FakePolygonChartApi
    private lateinit var chartGameDao: FakeChartGameDao
    private lateinit var chartDao: FakeChartDao
    private lateinit var tickDao: FakeTickDao
    private lateinit var tradeDao: FakeTradeDao
    private lateinit var chartRequestArgumentHelper: FakeChartRequestArgumentHelper
    private lateinit var chartTrainerPreferencesDataSource: ChartTrainerPreferencesDataSource

    // real datasource
    private lateinit var chartNetworkDataSource: ChartNetworkDataSource
    private lateinit var localDBDataSource: ChartTrainerLocalDBDataSource

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

    private val DUMMY_TICKER = "DUMMY"
    private val DUMMY_TICK_VALUE = 3.0


    @Before
    fun setup() {
        // api
        polygonChartApi = FakePolygonChartApi()
        polygonChartApi.setTickerToDto(
            ticker = DUMMY_TICKER,
            dto = createChartDto(
                ticker = DUMMY_TICKER,
                ticks = (1..100).map {
                    createTickDto(singleValue = DUMMY_TICK_VALUE)
                }
            )
        )

        // dao
        chartGameDao = FakeChartGameDao()
        tickDao = FakeTickDao()
        chartDao = FakeChartDao(ticksFlow = tickDao.ticksFlow)
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
        chartRequestArgumentHelper = FakeChartRequestArgumentHelper()
        chartRequestArgumentHelper.currentRandomTicker = DUMMY_TICKER

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

    fun createUser(
        balance: Money,
        winCount: Int = 0,
        loseCount: Int = 0,
        averageRateOfProfit: Double = 0.0
    ) = User(
        balance = balance,
        winCount = winCount,
        loseCount = loseCount,
        averageRateOfProfit = averageRateOfProfit
    )

    @Test
    fun `screenState should show loading when initially`() = runTest {
        assertEquals(
            true,
            viewModel.screenState.value.showLoading
        )
    }

    @Test
    fun `screenState should match with datasource when collect`() = runTest {
        val appleTicker = "AAPL"
        val appleTickValue = 1.0
        val tickListSize = 100
        val totalTurn = 50
        val initialBalance = 3000.asMoney()
        val user = createUser(balance = initialBalance)
        polygonChartApi.setTickerToDto(
            ticker = appleTicker,
            dto = createChartDto(
                ticker = appleTicker,
                ticks = (1..tickListSize).map {
                    createTickDto(singleValue = appleTickValue)
                }
            )
        )
        chartRequestArgumentHelper.currentRandomTicker = appleTicker
        chartTrainerPreferencesDataSource.updateUser(user)
        chartTrainerPreferencesDataSource.updateTotalTurn(totalTurn)

        viewModel.screenState.test {
            val visibleTickListSize = tickListSize - totalTurn
            assertEquals(
                ChartGameScreenState(
                    currentTurn = ChartGame.START_TURN,
                    totalTurn = totalTurn,
                    showLoading = false,
                    gameProgress = 0.02f,
                    candleStickChart = CandleStickChartUi(
                        opening = (1..visibleTickListSize).map { appleTickValue },
                        closing = (1..visibleTickListSize).map { appleTickValue },
                        low = (1..visibleTickListSize).map { appleTickValue },
                        high = (1..visibleTickListSize).map { appleTickValue }
                    ),
                    clickData = ChartGameScreenState.ClickData(
                        // FakeDao 에서 ID 를 1부터 부여함
                        gameId = 1L,
                        currentBalance = initialBalance,
                        currentStockPrice = appleTickValue.asMoney(),
                        currentTurn = ChartGame.START_TURN,
                    )
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `screenState should change chart with initial game state when click new chart button`() =
        runTest {
            // 첫번째 저장되는 차트게임 ID 는 1이다.
            val gameId = 1L
            val teslaTicker = "TSLA"
            val teslaTickValue = 2.0
            val tickListSize = 100
            val totalTurn = 50
            val initialBalance = 3000.asMoney()
            val user = createUser(balance = initialBalance)
            polygonChartApi.setTickerToDto(
                ticker = teslaTicker,
                dto = createChartDto(
                    ticker = teslaTicker,
                    ticks = (1..tickListSize).map {
                        createTickDto(singleValue = teslaTickValue)
                    }
                )
            )
            chartTrainerPreferencesDataSource.updateUser(user)
            chartTrainerPreferencesDataSource.updateTotalTurn(totalTurn)

            chartRequestArgumentHelper.currentRandomTicker = teslaTicker
            viewModel.handleChartGameScreenUserAction(
                userAction = ChartGameScreenUserAction.ClickNewChartButton(gameId = gameId)
            )

            viewModel.screenState.test {
                val visibleTickListSize = tickListSize - totalTurn
                assertEquals(
                    ChartGameScreenState(
                        currentTurn = ChartGame.START_TURN,
                        totalTurn = totalTurn,
                        totalProfit = 0.0,
                        rateOfProfit = 0.0,
                        gameProgress = 0.02f,
                        showLoading = false,
                        candleStickChart = CandleStickChartUi(
                            opening = (1..visibleTickListSize).map { teslaTickValue },
                            closing = (1..visibleTickListSize).map { teslaTickValue },
                            low = (1..visibleTickListSize).map { teslaTickValue },
                            high = (1..visibleTickListSize).map { teslaTickValue }
                        ),
                        tradeOrderUi = TradeOrderUi.Hide,
                        clickData = ChartGameScreenState.ClickData(
                            gameId = gameId,
                            ownedAverageStockPrice = Money.ZERO,
                            currentBalance = initialBalance,
                            currentStockPrice = teslaTickValue.asMoney(),
                            currentTurn = ChartGame.START_TURN,
                            ownedStockCount = 0
                        )
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `screenEvent should emit MoveToBack when user click quit chart game button`() = runTest {
        viewModel.screenEvent.test {
            viewModel.handleChartGameScreenUserAction(
                userAction = ChartGameScreenUserAction.ClickQuitGameButton(gameId = 1L)
            )

            assertEquals(
                ChartGameEvent.MoveToBack,
                awaitItem()
            )
        }
    }

    @Test
    fun `screenState should not be completed but be ended when user click quit chart game button`() = runTest {
        viewModel.screenState.test {
            val old = awaitItem()

            viewModel.handleChartGameScreenUserAction(
                userAction = ChartGameScreenUserAction.ClickQuitGameButton(gameId = 1L)
            )

            assertEquals(
                old.copy(
                    isGameEnd = true
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `screenState should show buying trade ui when user click buy button`() = runTest {
        viewModel.screenState.test {
            val oldState = awaitItem()
            val gameId = 1L
            val currentTurn = 10
            val ownedStockCount = 10
            val ownedAverageStockPrice = 500.asMoney()
            val currentStockPrice = 1000.asMoney()

            viewModel.handleChartGameScreenUserAction(
                userAction = ChartGameScreenUserAction.ClickBuyButton(
                    gameId = gameId,
                    ownedStockCount = ownedStockCount,
                    ownedAverageStockPrice = ownedAverageStockPrice,
                    currentBalance = 2900.asMoney(),
                    currentStockPrice = currentStockPrice,
                    currentTurn = currentTurn
                )
            )

            assertEquals(
                oldState.copy(
                    tradeOrderUi = TradeOrderUi.Buy(
                        showKeyPad = false,
                        maxAvailableStockCount = 2,
                        currentStockPrice = currentStockPrice.value,
                        clickData = TradeOrderUi.Buy.ClickData(
                            gameId = gameId,
                            maxAvailableStockCount = 2,
                            ownedStockCount = ownedStockCount,
                            ownedAverageStockPrice = ownedAverageStockPrice,
                            currentStockPrice = currentStockPrice,
                            currentTurn = currentTurn,
                        )
                    )
                ),
                awaitItem()
            )
        }
    }
}


