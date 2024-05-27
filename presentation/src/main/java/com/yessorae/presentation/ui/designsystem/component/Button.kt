package com.yessorae.presentation.ui.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import com.yessorae.presentation.ui.designsystem.theme.ChartTrainerTheme
import com.yessorae.presentation.ui.designsystem.util.ChartTrainerIcons
import com.yessorae.presentation.ui.designsystem.util.ThemePreviews

// TODO::LATER UI 다듬기하면서 함께 고쳐질 예정. 우선 래핑함

@Composable
fun ChartTrainerCustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border =border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )

}

@Composable
fun DefaultTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    shape: Shape = ButtonDefaults.textShape,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        shape = shape,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
        )
    }

}

@Composable
fun DefaultIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        Icon(imageVector = imageVector, contentDescription = null)
    }
}

@ThemePreviews
@Composable
fun DefaultTextButtonPreview() {
    ChartTrainerTheme {
        DefaultTextButton(
            onClick = {},
            text = "매수",
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        )
    }
}

@ThemePreviews
@Composable
fun DefaultTextButtonPreview2() {
    ChartTrainerTheme {
        DefaultTextButton(
            onClick = {},
            text = "매도",
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        )
    }
}

@ThemePreviews
@Composable
fun DefaultIconButtonPreview() {
    ChartTrainerTheme {
        DefaultIconButton(onClick = {}, imageVector = ChartTrainerIcons.ChangeChart)
    }
}

