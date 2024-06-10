package com.yessorae.presentation.ui.designsystem.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(
    @StringRes id: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, this.getString(id), duration).show()
}
