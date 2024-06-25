package com.yessorae.chartrainer

import com.yessorae.chartrainer.fake.FakeChartDao
import com.yessorae.chartrainer.fake.FakeChartGameDao
import com.yessorae.chartrainer.fake.FakeChartTrainerPreferencesDataSource
import com.yessorae.chartrainer.fake.FakeTickDao
import com.yessorae.chartrainer.fake.FakeTradeDao
import com.yessorae.data.repository.ChartGameRepositoryImpl
import com.yessorae.data.repository.UserRepositoryImpl
import com.yessorae.data.source.local.database.ChartTrainerLocalDBDataSourceImpl
import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.usecase.ChangeCommissionRateSettingUseCase
import com.yessorae.domain.usecase.ChangeTickUnitSettingUseCase
import com.yessorae.domain.usecase.ChangeTotalTurnSettingUseCase
import com.yessorae.domain.usecase.QuitChartGameUseCase
import com.yessorae.domain.usecase.SubscribeHomeDataUseCase
import com.yessorae.presentation.ui.screen.home.HomeViewModel
import com.yessorae.presentation.ui.screen.home.model.HomeBottomButtonUi
import com.yessorae.presentation.ui.screen.home.model.HomeState
import com.yessorae.presentation.ui.screen.home.model.SettingDialogState
import com.yessorae.presentation.ui.screen.home.model.SettingInfoUi
import com.yessorae.presentation.ui.screen.home.model.UserInfoUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HomeViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val dispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val dispatcherRule = MainDispatcherRule(dispatcher)

    // dao
    private val chartGameDao = FakeChartGameDao()
    private val chartDao = FakeChartDao()
    private val tickDao = FakeTickDao()
    private val tradeDao = FakeTradeDao()

    // datasource
    private val localDBDataSource = ChartTrainerLocalDBDataSourceImpl(
        chartGameDao = chartGameDao,
        chartDao = chartDao,
        tradeDao = tradeDao,
        tickDao = tickDao
    )
    private val chartTrainerPreferencesDataSource = FakeChartTrainerPreferencesDataSource()

    // repository
    private val userRepository = UserRepositoryImpl(
        appPreference = chartTrainerPreferencesDataSource
    )
    private val chartGameRepository = ChartGameRepositoryImpl(
        localDataSource = localDBDataSource,
        chartGamePreferencesDataSource = chartTrainerPreferencesDataSource,
        dispatcher = dispatcher
    )

    // usecase
    private val subscribeHomeDataUseCase = SubscribeHomeDataUseCase(
        userRepository = userRepository,
        chartGameRepository = chartGameRepository
    )

    private val changeCommissionRateSettingUseCase = ChangeCommissionRateSettingUseCase(
        userRepository = userRepository
    )

    private val changeTickUnitSettingUseCase = ChangeTickUnitSettingUseCase(
        userRepository = userRepository
    )

    private val changeTotalTurnSettingUseCase = ChangeTotalTurnSettingUseCase(
        userRepository = userRepository
    )

    private val quitChartGameUseCase = QuitChartGameUseCase(
        userRepository = userRepository,
        chartGameRepository = chartGameRepository
    )

    lateinit var viewModel: HomeViewModel

    private val initialUserInfo = UserInfoUi(
        currentBalance = Money.of(0.0),
        winCount = 0,
        loseCount = 0,
        averageRateOfProfit = 0f,
        rateOfWinning = 0f,
        rateOfLosing = 0f
    )

    private val initialSettingInfoUi = SettingInfoUi(
        commissionRate = 0f,
        totalTurn = 0,
        tickUnit = DefaultValues.defaultTickUnit
    )

    private val initialHomeState = HomeState(
        userInfoUi = initialUserInfo,
        settingInfoUi = initialSettingInfoUi,
        bottomButtonState = HomeBottomButtonUi.Loading,
        screenLoading = false,
        error = false,
        settingDialogState = SettingDialogState.None
    )

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            subscribeUserDataUseCase = subscribeHomeDataUseCase,
            changeCommissionRateSettingUseCase = changeCommissionRateSettingUseCase,
            changeTickUnitSettingUseCase = changeTickUnitSettingUseCase,
            changeTotalTurnSettingUseCase = changeTotalTurnSettingUseCase,
            quitChartGameUseCase = quitChartGameUseCase
        )
    }


//    @Test
//    fun `initial state is loading`() = runTest {
//        assertEquals(
//            initialHomeState.copy(
//                screenLoading = true,
//                error = false
//            ),
//            viewModel.screenState.first()
//        )
//    }


}

