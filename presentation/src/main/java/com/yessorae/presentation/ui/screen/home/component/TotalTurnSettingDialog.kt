package com.yessorae.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yessorae.presentation.R
import com.yessorae.presentation.ui.designsystem.util.DevicePreviews

@Composable
fun TotalTurnSettingDialog(
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

    BaseSettingDialog(
        title = stringResource(id = R.string.home_total_turn_setting),
        onDismissRequest = onDismissRequest,
        onDone = {
            onDone(value)
        },
        content = {
            BasicTextField(
                value = value,
                onValueChange = { newValue ->
                    value = newValue
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onDone(value) }
                ),
                singleLine = true
            )
        }
    )
}

@DevicePreviews
@Composable
private fun TotalTurnSettingDialogPreview() {
    TotalTurnSettingDialog(
        initialValue = "60",
        onDismissRequest = {},
        onDone = {}
    )
}
