package com.yessorae.data.common

import java.time.LocalDateTime
import java.time.ZoneOffset

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this / 1000, 0, ZoneOffset.UTC)
}
