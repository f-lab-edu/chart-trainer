package com.yessorae.data.common

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun longToLocalDateTime(value: Long?): LocalDateTime? = value?.toLocalDateTime()

    @TypeConverter
    fun localDateTimeToLong(instant: LocalDateTime?): Long? = instant?.toMilliSecond()
}
