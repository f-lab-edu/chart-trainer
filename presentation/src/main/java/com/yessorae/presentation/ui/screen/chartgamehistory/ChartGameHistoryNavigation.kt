package com.yessorae.presentation.ui.screen.chartgamehistory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val CHART_GAME_HISTORY_ROUTE = "chart_game_history_route"

fun NavController.navigateToChartGameHistoryScreen(navOptions: NavOptions? = null) {
    this.navigate(
        route = CHART_GAME_HISTORY_ROUTE,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.chartGameHistoryScreen(
    navigateToBack: () -> Unit,
    navigateToTradeHistory: (Long) -> Unit
) {
    composable(
        route = CHART_GAME_HISTORY_ROUTE
    ) {
        ChartGameHistoryRoute(
            navigateToBack = navigateToBack,
            navigateToTradeHistory = navigateToTradeHistory
        )
    }
}
