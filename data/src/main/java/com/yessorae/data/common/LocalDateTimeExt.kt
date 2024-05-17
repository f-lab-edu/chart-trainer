package com.yessorae.data.common

import java.time.LocalDateTime
import java.time.ZoneOffset

fun LocalDateTime.toMilliSecond(): Long {
    return this.toEpochSecond(ZoneOffset.UTC) * 1000
}

fun Long.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.ofEpochSecond(this / 1000, 0, ZoneOffset.UTC)
}
