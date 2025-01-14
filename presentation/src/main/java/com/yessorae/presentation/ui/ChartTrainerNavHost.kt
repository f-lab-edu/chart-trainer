package com.yessorae.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yessorae.presentation.ui.screen.chartgame.chartGameScreen
import com.yessorae.presentation.ui.screen.chartgame.navigateToChartGameScreen
import com.yessorae.presentation.ui.screen.chartgamehistory.chartGameHistoryScreen
import com.yessorae.presentation.ui.screen.chartgamehistory.navigateToChartGameHistoryScreen
import com.yessorae.presentation.ui.screen.home.HOME_ROUTE
import com.yessorae.presentation.ui.screen.home.homeScreen
import com.yessorae.presentation.ui.screen.tradehistory.navigateToTradeHistoryScreen
import com.yessorae.presentation.ui.screen.tradehistory.tradeHistoryScreen

@Composable
fun ChartTrainerNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeScreen(
            navigateToChartGame = { chartGameId: Long? ->
                navController.navigateToChartGameScreen(chartGameId = chartGameId)
            },
            navigateToChartGameHistory = {
                navController.navigateToChartGameHistoryScreen()
            }
        )

        chartGameScreen(
            navigateToBack = navController::popBackStack,
            navigateToTradeHistory = { chartGameId: Long ->
                navController.navigateToTradeHistoryScreen(chartGameId = chartGameId)
            }
        )

        chartGameHistoryScreen(
            navigateToBack = navController::popBackStack,
            navigateToTradeHistory = { chartGameId: Long ->
                navController.navigateToTradeHistoryScreen(chartGameId = chartGameId)
            }
        )

        tradeHistoryScreen(
            navigateToBack = navController::popBackStack
        )
    }
}
