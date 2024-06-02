package com.yessorae.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yessorae.data.source.local.database.converter.LocalDateTimeConverter
import com.yessorae.data.source.local.database.converter.MoneyConverter
import com.yessorae.data.source.local.database.dao.ChartDao
import com.yessorae.data.source.local.database.dao.ChartGameDao
import com.yessorae.data.source.local.database.dao.TickDao
import com.yessorae.data.source.local.database.dao.TradeDao
import com.yessorae.data.source.local.database.model.ChartEntity
import com.yessorae.data.source.local.database.model.ChartGameEntity
import com.yessorae.data.source.local.database.model.TickEntity
import com.yessorae.data.source.local.database.model.TradeEntity

@Database(
    entities = [
        ChartGameEntity::class,
        ChartEntity::class,
        TickEntity::class,
        TradeEntity::class
    ],
    version = 1
)
@TypeConverters(
    LocalDateTimeConverter::class,
    MoneyConverter::class
)
abstract class ChartTrainerDatabase : RoomDatabase() {
    abstract fun getChartDao(): ChartDao
    abstract fun getChartGameDao(): ChartGameDao
    abstract fun getTickDao(): TickDao
    abstract fun getTradeDao(): TradeDao

    companion object {
        const val NAME = "chart-trainer-database"
    }
}
