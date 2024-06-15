package com.yessorae.presentation.ui.screen.tradehistory.component

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeHistoryTopAppBar(onClickClose: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.trade_history_title))
        },
        actions = {
            IconButton(onClick = onClickClose) {
                Icon(
                    imageVector = ChartTrainerIcons.TradeList,
                    contentDescription = null
                )
            }
        }
    )
}
