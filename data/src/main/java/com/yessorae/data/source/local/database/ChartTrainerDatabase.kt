package com.yessorae.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yessorae.data.common.LocalDateTimeConverter
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
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
abstract class ChartTrainerDatabase : RoomDatabase() {
    abstract fun getChartDao(): ChartDao
    abstract fun getChartGameDao(): ChartGameDao
    abstract fun getTickDao(): TickDao
    abstract fun getTradeDao(): TradeDao
}