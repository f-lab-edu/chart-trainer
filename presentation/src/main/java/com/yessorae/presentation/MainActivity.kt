package com.yessorae.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yessorae.presentation.ui.designsystem.theme.ChartTrainerTheme
import com.yessorae.presentation.ui.screen.chartgame.chartGameScreen
import com.yessorae.presentation.ui.screen.chartgame.navigateToChartGameScreen
import com.yessorae.presentation.ui.screen.home.HOME_ROUTE
import com.yessorae.presentation.ui.screen.home.homeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChartTrainerApp()
        }
    }
}

@Composable
fun ChartTrainerApp() {
    ChartTrainerTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ChartTrainerNavHost()
        }
    }
}

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
            }
        )

        chartGameScreen(
            navigateToBack = navController::popBackStack,
            navigateToChartGameHistory = {
                // TODO::LATER #7 에서 작성
            }
        )
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChartTrainerTheme {
        Greeting("Android")
    }
}
