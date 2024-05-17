package com.yessorae.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yessorae.data.common.LocalDateTimeConverter
import com.yessorae.data.source.local.database.model.ChartGameTable
import com.yessorae.data.source.local.database.model.ChartTable
import com.yessorae.data.source.local.database.model.TickTable
import com.yessorae.data.source.local.database.model.TradeTable

@Database(
    entities = [
        ChartGameTable::class,
        ChartTable::class,
        TickTable::class,
        TradeTable::class
    ],
    version = 1
)
@TypeConverters(
    LocalDateTimeConverter::class
)
abstract class ChartTrainerDatabase : RoomDatabase()
