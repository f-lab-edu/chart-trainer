package com.yessorae.chartrainer.presentation.home

import app.cash.turbine.test
import com.yessorae.chartrainer.MainDispatcherRule
import com.yessorae.chartrainer.fake.FakeChartDao
import com.yessorae.chartrainer.fake.FakeChartGameDao
import com.yessorae.chartrainer.fake.FakeChartTrainerPreferencesDataSource
import com.yessorae.chartrainer.fake.FakeTickDao
import com.yessorae.chartrainer.fake.FakeTradeDao
import com.yessorae.data.repository.ChartGameRepositoryImpl
import com.yessorae.data.repository.UserRepositoryImpl
import com.yessorae.data.source.ChartTrainerLocalDBDataSource
import com.yessorae.data.source.ChartTrainerPreferencesDataSource
import com.yessorae.data.source.local.database.ChartTrainerLocalDBDataSourceImpl
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
import com.yessorae.domain.common.DefaultValues.MAX_TOTAL_TURN
import com.yessorae.domain.common.DefaultValues.MIN_TOTAL_TURN
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.asMoney
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.UserRepository
import com.yessorae.domain.usecase.ChangeCommissionRateSettingUseCase
import com.yessorae.domain.usecase.ChangeTickUnitSettingUseCase
import com.yessorae.domain.usecase.ChangeTotalTurnSettingUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeHomeDataUseCase
import com.yessorae.domain.usecase.SubscribeLastGameIdUseCase
import com.yessorae.presentation.ui.screen.home.HomeViewModel
import com.yessorae.presentation.ui.screen.home.model.HomeBottomButtonUi
import com.yessorae.presentation.ui.screen.home.model.HomeScreenEvent
import com.yessorae.presentation.ui.screen.home.model.HomeScreenUserAction
import com.yessorae.presentation.ui.screen.home.model.SettingDialogState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class HomeViewHomeModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @JvmField
    @Rule
    val dispatcherRule = MainDispatcherRule(dispatcher)

    // dao
    private lateinit var chartGameDao: ChartGameDao
    private lateinit var chartDao: ChartDao
    private lateinit var tickDao: TickDao
    private lateinit var tradeDao: TradeDao

    // datasource
    private lateinit var localDBDataSource: ChartTrainerLocalDBDataSource
    private lateinit var chartTrainerPreferencesDataSource: ChartTrainerPreferencesDataSource

    // repository
    private lateinit var userRepository: UserRepository
    private lateinit var chartGameRepository: ChartGameRepository

    // usecase
    private lateinit var subscribeHomeDataUseCase: SubscribeHomeDataUseCase
    private lateinit var subscribeLastGameIdUseCase: SubscribeLastGameIdUseCase
    private lateinit var changeCommissionRateSettingUseCase: ChangeCommissionRateSettingUseCase
    private lateinit var changeTickUnitSettingUseCase: ChangeTickUnitSettingUseCase
    private lateinit var changeTotalTurnSettingUseCase: ChangeTotalTurnSettingUseCase
    private lateinit var quitChartGameUseCase: QuitChartGameUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        chartGameDao = FakeChartGameDao()
        tickDao = FakeTickDao()
        chartDao = FakeChartDao(ticksFlow = (tickDao as FakeTickDao).ticksFlow)
        tradeDao = FakeTradeDao()

        localDBDataSource = ChartTrainerLocalDBDataSourceImpl(
            chartGameDao = chartGameDao,
            chartDao = chartDao,
            tradeDao = tradeDao,
            tickDao = tickDao
        )
        chartTrainerPreferencesDataSource = FakeChartTrainerPreferencesDataSource()

        userRepository = UserRepositoryImpl(
            appPreference = chartTrainerPreferencesDataSource
        )
        chartGameRepository = ChartGameRepositoryImpl(
            localDataSource = localDBDataSource,
            chartGamePreferencesDataSource = chartTrainerPreferencesDataSource,
            dispatcher = dispatcher
        )

        subscribeHomeDataUseCase = SubscribeHomeDataUseCase(
            userRepository = userRepository
        )
        subscribeLastGameIdUseCase = SubscribeLastGameIdUseCase(
            chartGameRepository = chartGameRepository
        )
        changeCommissionRateSettingUseCase = ChangeCommissionRateSettingUseCase(
            userRepository = userRepository
        )

        changeTickUnitSettingUseCase = ChangeTickUnitSettingUseCase(
            userRepository = userRepository
        )

        changeTotalTurnSettingUseCase = ChangeTotalTurnSettingUseCase(
            userRepository = userRepository
        )

        quitChartGameUseCase = QuitChartGameUseCase(
            userRepository = userRepository,
            chartGameRepository = chartGameRepository
        )

        viewModel = HomeViewModel(
            subscribeUserDataUseCase = subscribeHomeDataUseCase,
            subscribeLastGameIdUseCase = subscribeLastGameIdUseCase,
            changeCommissionRateSettingUseCase = changeCommissionRateSettingUseCase,
            changeTickUnitSettingUseCase = changeTickUnitSettingUseCase,
            changeTotalTurnSettingUseCase = changeTotalTurnSettingUseCase,
            quitChartGameUseCase = quitChartGameUseCase
        )
    }

    @Test
    fun `initial state should be loading`() = runTest {
        assertEquals(
            createHomeState(
                screenLoading = true,
                error = false
            ),
            viewModel.screenState.value
        )
    }


    @Test
    fun `state should match with datasource when success`() = runTest {
        chartTrainerPreferencesDataSource.apply {
            updateUser(
                user = User(
                    balance = 1000.asMoney(),
                    winCount = 99,
                    loseCount = 1,
                    averageRateOfProfit = 0.99
                )
            )
            updateLastChartGameId(gameId = 1L)
            updateTickUnit(tickUnit = TickUnit.HOUR)
            updateTotalTurn(turn = 1)
            updateCommissionRate(rate = 0.2)
        }

        viewModel.screenState.test {
            assertEquals(
                createHomeState(
                    userInfoUi = createUserInfoUi(
                        currentBalance = 1000.asMoney(),
                        winCount = 99,
                        loseCount = 1,
                        averageRateOfProfit = 0.99f,
                        rateOfWinning = 0.99f,
                        rateOfLosing = 0.01f
                    ),
                    settingInfoUi = createSettingInfoUi(
                        commissionRate = 0.2f,
                        totalTurn = 1,
                        tickUnit = TickUnit.HOUR
                    ),
                    bottomButtonState = HomeBottomButtonUi.KeepGoingGameOrQuit(
                        clickData = HomeBottomButtonUi.KeepGoingGameOrQuit.ClickData(
                            lastChartGameId = 1L
                        )
                    ),
                    screenLoading = false,
                    error = false
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `bottomButtonState should keepGoingGame or quit when lastChartGameId has value`() = runTest {
        val lastChartGameId = 1L
        chartTrainerPreferencesDataSource.updateLastChartGameId(lastChartGameId)

        viewModel.screenState.test {
            assertEquals(
                HomeBottomButtonUi.KeepGoingGameOrQuit(
                    clickData = HomeBottomButtonUi.KeepGoingGameOrQuit.ClickData(
                        lastChartGameId = lastChartGameId
                    )
                ),
                awaitItem().bottomButtonState
            )
        }
    }

    @Test
    fun `bottomButtonState should be newGame when lastChartGameId is cleared`() = runTest {
        chartTrainerPreferencesDataSource.clearLastChartGameId()

        viewModel.screenState.test {
            assertEquals(
                HomeBottomButtonUi.NewGame,
                awaitItem().bottomButtonState
            )
        }
    }

    @Test
    fun `screeEvent should emit navigateToChartGameScreen when user click start chart game button`() = runTest {
        viewModel.screenEvent.test {
            viewModel.onUserAction(userAction = HomeScreenUserAction.ClickStartChartGame)

            assertEquals(
                HomeScreenEvent.NavigateToChartGameScreen(chartGameId = null),
                awaitItem()
            )
        }
    }

    @Test
    fun `screenEvent should emit navigateToChartGameScreen with value when user click keep going chart game button`() = runTest {
        viewModel.screenEvent.test {
            viewModel.onUserAction(
                userAction = HomeScreenUserAction.ClickKeepGoingChartGame(
                    lastChartGameId = 1L
                )
            )

            assertEquals(
                HomeScreenEvent.NavigateToChartGameScreen(chartGameId = 1L),
                awaitItem()
            )
        }
    }

    @Test
    fun `bottomButtonState should be newGame when user click quit in progress chart game`() = runTest {
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.ClickQuitInProgressChartGame(
                lastChartGameId = 1L
            )
        )

        viewModel.screenState.test {
            assertEquals(
                HomeBottomButtonUi.NewGame,
                awaitItem().bottomButtonState
            )
        }
    }

    @Test
    fun `settingDialogState should be commissionRate when user click commission rate`() = runTest {
        viewModel.onUserAction(userAction = HomeScreenUserAction.ClickCommissionRate)

        viewModel.screenState.test {
            assertEquals(
                SettingDialogState.CommissionRate(initialValue = ""),
                awaitItem().settingDialogState
            )
        }
    }

    @Test
    fun `settingInfoUi should be updated when user update commission rate with valid value`() = runTest {
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.UpdateCommissionRate("123")
        )

        viewModel.screenState.test {
            assertEquals(
                viewModel.screenState.value.settingInfoUi.copy(
                    commissionRate = 0.123f
                ),
                awaitItem().settingInfoUi
            )
        }
    }

    @Test
    fun `screenEvent should emit commissionRateSettingError when user update commission rate with invalid value`() = runTest {
        viewModel.screenEvent.test {
            viewModel.onUserAction(
                userAction = HomeScreenUserAction.UpdateCommissionRate("invalid number")
            )

            assertEquals(
                HomeScreenEvent.CommissionRateSettingError,
                awaitItem()
            )
        }
    }

    @Test
    fun `settingDialogState should be totalTurn when user click total turn`() = runTest {
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.ClickTotalTurn
        )

        viewModel.screenState.test {
            assertEquals(
                SettingDialogState.TotalTurn(initialValue = ""),
                awaitItem().settingDialogState
            )
        }
    }

    @Test
    fun `settingInfoUi should be updated when user update total turn with valid value`() = runTest {
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.UpdateTotalTurn("60")
        )

        viewModel.screenState.test {
            assertEquals(
                viewModel.screenState.value.settingInfoUi.copy(
                    totalTurn = 60
                ),
                awaitItem().settingInfoUi
            )
        }
    }

    @Test
    fun `screenEvent should emit totalTurnSettingError when user update total turn with not a number value`() = runTest {
        viewModel.screenEvent.test {
            viewModel.onUserAction(
                userAction = HomeScreenUserAction.UpdateTotalTurn("not number")
            )

            assertEquals(
                HomeScreenEvent.TotalTurnSettingError,
                awaitItem()
            )
        }
    }

    @Test
    fun `screenEvent should emit totalTurnSettingError when user update total turn with value smaller minimum`() = runTest {
        val invalidValue = Random.nextInt(until = MIN_TOTAL_TURN)

        viewModel.screenEvent.test {
            viewModel.onUserAction(
                userAction = HomeScreenUserAction.UpdateTotalTurn("$invalidValue")
            )

            assertEquals(
                HomeScreenEvent.TotalTurnSettingError,
                awaitItem()
            )
        }
    }

    @Test
    fun `screenEvent should emit totalTurnSettingError when user update total turn with value bigger than maximum`() = runTest {
        val invalidValue = Random.nextInt(from = MAX_TOTAL_TURN + 1, until = Int.MAX_VALUE)

        viewModel.screenEvent.test {
            viewModel.onUserAction(
                userAction = HomeScreenUserAction.UpdateTotalTurn("$invalidValue")
            )

            assertEquals(
                HomeScreenEvent.TotalTurnSettingError,
                awaitItem()
            )
        }
    }

    @Test
    fun `settingDialogState should be tickUnit when user click tick unit`() = runTest {
        val existingValue = viewModel.screenState.value.settingInfoUi.tickUnit
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.ClickTickUnit
        )

        viewModel.screenState.test {
            assertEquals(
                SettingDialogState.TickUnit(
                    initialTickUnit = existingValue
                ),
                awaitItem().settingDialogState
            )
        }
    }

    @Test
    fun `settingInfoUi should be updated when user update tick unit`() = runTest {
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.UpdateTickUnit(
                newValue = TickUnit.HOUR
            )
        )

        viewModel.screenState.test {
            assertEquals(
                viewModel.screenState.value.settingInfoUi.copy(
                    tickUnit = TickUnit.HOUR
                ),
                awaitItem().settingInfoUi
            )
        }
    }

    @Test
    fun `screenEvent should emit navigateToChartGameHistoryScreen when user click chart game history button`() = runTest {
        viewModel.screenEvent.test {
            viewModel.onUserAction(
                userAction = HomeScreenUserAction.ClickChartGameHistory
            )

            assertEquals(
                HomeScreenEvent.NavigateToChartGameHistoryScreen,
                awaitItem()
            )
        }
    }

    @Test
    fun `settingDialogState should be none when user dismiss dialog`() = runTest {
        viewModel.onUserAction(
            userAction = HomeScreenUserAction.DismissDialog
        )

        viewModel.screenState.test {
            assertEquals(
                SettingDialogState.None,
                awaitItem().settingDialogState
            )
        }
    }
}
