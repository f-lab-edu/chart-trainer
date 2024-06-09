package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.component.DefaultTextButton
import com.yessorae.presentation.ui.designsystem.theme.Dimen
import com.yessorae.presentation.ui.designsystem.theme.StockUpColor
import com.yessorae.presentation.ui.designsystem.theme.TradeTextColor
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews
import com.yessorae.presentation.ui.designsystem.util.UiConstants.NUMBER_OF_BELOW_DECIMAL_POINT_OF_COMMISSION

@Composable
fun CommissionRateSettingDialog(
    initialValue: String,
    onDismissRequest: () -> Unit,
    onDone: (String) -> Unit
) {
    var value by remember(key1 = initialValue) {
        mutableStateOf(initialValue)
    }

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(width = 300.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(Dimen.defaultLayoutSidePadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.home_commission_rate_setting),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.surfaceContainerHighest,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "0.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    modifier = Modifier.padding(
                        end = 8.dp
                    )
                )
                BasicTextField(
                    value = value,
                    onValueChange = { newValue ->
                        // 3 자리만 '보여주겠다'는 건 View 레이어 책임으로 간주..?
                        if (newValue.length <= NUMBER_OF_BELOW_DECIMAL_POINT_OF_COMMISSION) {
                            value = newValue
                        }
                    },
                    modifier = Modifier.focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onDone(value) }
                    ),
                    decorationBox = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TextFieldBelowDecimalPoint(
                                text = value.firstOrNull() ?: '0',
                                isFocused = value.isEmpty()
                            )

                            TextFieldBelowDecimalPoint(
                                text = value.getOrNull(1) ?: '0',
                                isFocused = value.length == 1
                            )

                            TextFieldBelowDecimalPoint(
                                text = value.getOrNull(2) ?: '0',
                                isFocused = value.length == 2
                            )
                        }
                    },
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                DefaultTextButton(
                    text = stringResource(id = R.string.common_cancel),
                    onClick = {
                        onDismissRequest()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                        contentColor = StockUpColor
                    ),
                    shape = MaterialTheme.shapes.small
                )
                DefaultTextButton(
                    text = stringResource(id = R.string.home_done),
                    onClick = {
                        onDone(value)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = StockUpColor,
                        contentColor = TradeTextColor
                    ),
                    shape = MaterialTheme.shapes.small
                )
            }
        }
    }
}

@Composable
private fun TextFieldBelowDecimalPoint(
    modifier: Modifier = Modifier,
    text: Char,
    isFocused: Boolean
) {
    Box(
        modifier = modifier
            .size(
                width = 29.dp,
                height = 40.dp
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow,
                shape = MaterialTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    MaterialTheme.colorScheme.surfaceContainerHighest
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                },
                shape = MaterialTheme.shapes.small
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text.toString())
    }
}

@DevicePreviews
@Composable
private fun CommissionRateSettingDialogPreview() {
    CommissionRateSettingDialog(
        initialValue = "012",
        onDismissRequest = {},
        onDone = {}
    )
}
