package com.yessorae.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yessorae.presentation.ui.designsystem.theme.ChartTrainerTheme

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
