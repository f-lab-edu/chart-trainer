package com.yessorae.data.source.local.database.converter

import androidx.room.TypeConverter
import com.yessorae.data.util.toLocalDateTime
import com.yessorae.data.util.toMilliSecond
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun longToLocalDateTime(value: Long?): LocalDateTime? = value?.toLocalDateTime()

    @TypeConverter
    fun localDateTimeToLong(instant: LocalDateTime?): Long? = instant?.toMilliSecond()
}
