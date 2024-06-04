package com.yessorae.presentation.ui.designsystem.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.yessorae.domain.entity.tick.TickUnit
import com.yessorae.presentation.R

@Composable
fun provideTickUnitText(tickUnit: TickUnit) =
    when (tickUnit) {
        TickUnit.DAY -> stringResource(id = R.string.common_day)
        TickUnit.HOUR -> stringResource(id = R.string.common_hour)
    }
