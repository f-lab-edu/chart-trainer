package com.yessorae.presentation.ui.screen.chartgame

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val CHART_GAME_ID = "chartGameId"
const val CHART_GAME_BASE_ROUTE = "chart_game"
const val CHART_GAME_ROUTE_WITH_ARGS = "$CHART_GAME_BASE_ROUTE?$CHART_GAME_ID={$CHART_GAME_ID}"
fun NavController.navigateToChartGameScreen(
    chartGameId: Long? = null,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$CHART_GAME_BASE_ROUTE?$CHART_GAME_ID=$chartGameId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.chartGameScreen(
    navigateToBack: () -> Unit,
    navigateToChartGameHistory: (Long) -> Unit
) {
    composable(
        route = CHART_GAME_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(CHART_GAME_ID) {
                nullable = true
                type = NavType.StringType
            }
        )
    ) {
        ChartGameRoute(
            navigateToBack = navigateToBack,
            navigateToChartGameHistory = navigateToChartGameHistory
        )
    }
}
