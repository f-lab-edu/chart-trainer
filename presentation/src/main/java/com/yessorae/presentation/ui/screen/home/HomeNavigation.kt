package com.yessorae.presentation.ui.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home_route"
fun NavGraphBuilder.homeScreen(navigateToChartGame: (Long?) -> Unit) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreenRoute(
            navigateToChartGame = navigateToChartGame
        )
    }
}
