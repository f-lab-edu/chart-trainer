package com.yessorae.presentation.ui.screen.tradehistory

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yessorae.presentation.ui.NavigationConstants.CHART_GAME_ID_ARG_KEY

const val TRADE_HISTORY_BASE_ROUTE = "trade_history"
const val TRADE_HISTORY_ROUTE_WITH_ARGS =
    "$TRADE_HISTORY_BASE_ROUTE?$CHART_GAME_ID_ARG_KEY={$CHART_GAME_ID_ARG_KEY}"

fun NavController.navigateToTradeHistoryScreen(
    chartGameId: Long,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = "$TRADE_HISTORY_BASE_ROUTE?$CHART_GAME_ID_ARG_KEY=$chartGameId",
        navOptions = navOptions
    )
}

fun NavGraphBuilder.tradeHistoryScreen(navigateToBack: () -> Unit) {
    composable(
        route = TRADE_HISTORY_ROUTE_WITH_ARGS,
        arguments = listOf(
            navArgument(CHART_GAME_ID_ARG_KEY) {
                type = NavType.LongType
            }
        )
    ) {
        TradeHistoryScreenRoute(
            navigateToBack = navigateToBack
        )
    }
}
