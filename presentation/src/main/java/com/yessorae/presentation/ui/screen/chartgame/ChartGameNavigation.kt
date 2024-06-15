package com.yessorae.presentation.ui.screen.chartgame

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val CHART_GAME_ID_ARG_KEY = "chartGameId"
const val CHART_GAME_BASE_ROUTE = "chart_game"
const val CHART_GAME_ROUTE_WITH_ARGS =
    "$CHART_GAME_BASE_ROUTE?$CHART_GAME_ID_ARG_KEY={$CHART_GAME_ID_ARG_KEY}"

fun NavController.navigateToChartGameScreen(
    chartGameId: Long? = null,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$CHART_GAME_BASE_ROUTE?$CHART_GAME_ID_ARG_KEY=$chartGameId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.chartGameScreen(
    navigateToBack: () -> Unit,
    navigateToTradeHistory: (Long) -> Unit
) {
    composable(
        route = CHART_GAME_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(CHART_GAME_ID_ARG_KEY) {
                nullable = true
                type = NavType.StringType
            }
        )
    ) {
        ChartGameRoute(
            navigateToBack = navigateToBack,
            navigateToChartGameHistory = navigateToTradeHistory
        )
    }
}
