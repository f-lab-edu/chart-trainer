package com.yessorae.chartrainer

import app.cash.turbine.test
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
import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.domain.entity.value.Money
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
import com.yessorae.presentation.ui.screen.home.model.HomeState
import com.yessorae.presentation.ui.screen.home.model.SettingDialogState
import com.yessorae.presentation.ui.screen.home.model.SettingInfoUi
import com.yessorae.presentation.ui.screen.home.model.UserInfoUi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
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

    private fun createUserInfoUi(
        currentBalance: Money = Money(0.0),
        winCount: Int = 0,
        loseCount: Int = 0,
        averageRateOfProfit: Float = 0f,
        rateOfWinning: Float = 0f,
        rateOfLosing: Float = 0f
    ) = UserInfoUi(
        currentBalance = currentBalance,
        winCount = winCount,
        loseCount = loseCount,
        averageRateOfProfit = averageRateOfProfit,
        rateOfWinning = rateOfWinning,
        rateOfLosing = rateOfLosing
    )

    private fun createSettingInfoUi(
        commissionRate: Float = 0f,
        totalTurn: Int = 0,
        tickUnit: TickUnit = DefaultValues.defaultTickUnit
    ) = SettingInfoUi(
        commissionRate = commissionRate,
        totalTurn = totalTurn,
        tickUnit = tickUnit
    )

    private fun createHomeState(
        userInfoUi: UserInfoUi = createUserInfoUi(),
        settingInfoUi: SettingInfoUi = createSettingInfoUi(),
        bottomButtonState: HomeBottomButtonUi = HomeBottomButtonUi.Loading,
        screenLoading: Boolean = false,
        error: Boolean = false
    ) = HomeState(
        userInfoUi = userInfoUi,
        settingInfoUi = settingInfoUi,
        bottomButtonState = bottomButtonState,
        screenLoading = screenLoading,
        error = error
    )

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

//    @Test
//    fun initiate_state_is_loading() = runTest {
//        viewModel.screenState.test {
//            assertEquals(
//                createHomeState(
//                    screenLoading = true,
//                    error = false
//                ),
//                awaitItem()
//            )
//        }
//    }

    @Test
    fun success_state_corresponds_to_ui_state() =
        runTest {
            // test 안으로 넣으면 test 통과하지 못함
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
    fun home_bottom_button_state_on_going_game_corresponds_to_last_chart_game_id() =
        runTest {
            chartTrainerPreferencesDataSource.updateLastChartGameId(1L)

            viewModel.screenState.test {
                assertEquals(
                    HomeBottomButtonUi.KeepGoingGameOrQuit(
                        clickData = HomeBottomButtonUi.KeepGoingGameOrQuit.ClickData(
                            lastChartGameId = 1L
                        )
                    ),
                    awaitItem().bottomButtonState
                )
            }

            chartTrainerPreferencesDataSource.clearLastChartGameId()

            viewModel.screenState.test {
                assertEquals(
                    HomeBottomButtonUi.NewGame,
                    awaitItem().bottomButtonState
                )
            }
        }

    @Test
    fun navigate_to_new_chart_game_when_click_start_chart_game() =
        runTest {
            viewModel.screenEvent.test {
                viewModel.handleUserAction(userAction = HomeScreenUserAction.ClickStartChartGame)

                assertEquals(
                    HomeScreenEvent.NavigateToChartGameScreen(chartGameId = null),
                    awaitItem()
                )
            }
        }

    @Test
    fun navigate_to_chart_game_when_click_keep_going_chart_game() =
        runTest {
            viewModel.screenEvent.test {
                viewModel.handleUserAction(
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
    fun bottom_button_change_to_new_game_start_button_when_click_quit_in_progress_chart_game() =
        runTest {
            viewModel.handleUserAction(
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
    fun show_commission_rate_setting_dialog_when_click_commission_rate() =
        runTest {
            viewModel.handleUserAction(userAction = HomeScreenUserAction.ClickCommissionRate)

            viewModel.screenState.test {
                assertEquals(
                    SettingDialogState.CommissionRate(initialValue = ""),
                    awaitItem().settingDialogState
                )
            }
        }

    @Test
    fun update_commission_rate_when_click_update_commission_rate_with_valid_value() =
        runTest {
            viewModel.handleUserAction(
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

    // TODO::SR-N 입력이 invalid 한 경우 추가

    @Test
    fun show_total_turn_setting_dialog_when_click_total_turn() =
        runTest {
            viewModel.handleUserAction(
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
    fun update_total_turn_when_click_update_total_turn_with_valid_value() =
        runTest {
            viewModel.handleUserAction(
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

    // TODO::SR-N 입력이 invalid 한 경우 추가

    @Test
    fun show_tick_unit_setting_dialog_when_click_tick_unit() =
        runTest {
            val existingValue = viewModel.screenState.value.settingInfoUi.tickUnit
            viewModel.handleUserAction(
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
    fun update_tick_unit_when_click_update_tick_unit() = runTest {
        viewModel.handleUserAction(
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
    fun navigate_to_chart_game_history_when_click_chart_game_history_button() =
        runTest {
            viewModel.screenEvent.test {
                viewModel.handleUserAction(
                    userAction = HomeScreenUserAction.ClickChartGameHistory
                )

                assertEquals(
                    HomeScreenEvent.NavigateToChartGameHistoryScreen,
                    awaitItem()
                )
            }
        }
}
