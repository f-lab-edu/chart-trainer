package com.yessorae.data.source.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.domain.entity.Chart
import com.yessorae.domain.entity.tick.TickUnit
import java.time.LocalDateTime

@Entity(tableName = ChartEntity.NAME)
data class ChartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID)
    val id: Long,
    @ColumnInfo(name = COL_TICKER_SYMBOL)
    val tickerSymbol: String,
    @ColumnInfo(name = COL_START_DATE_TIME)
    val startDateTime: LocalDateTime?,
    @ColumnInfo(name = COL_END_DATE_TIME)
    val endDateTime: LocalDateTime?,
    @ColumnInfo(name = COL_TICK_UNIT)
    val tickUnit: TickUnit
) {
    companion object {
        const val NAME = "chart_table"
        const val COL_ID = "id"
        const val COL_TICKER_SYMBOL = "ticker_symbol"
        const val COL_START_DATE_TIME = "start_date_time"
        const val COL_END_DATE_TIME = "end_date_time"
        const val COL_TICK_UNIT = "tick_unit"
    }
}

fun Chart.asEntity() =
    ChartEntity(
        id = id,
        tickerSymbol = tickerSymbol,
        startDateTime = startDateTime,
        endDateTime = endDateTime,
        tickUnit = tickUnit
    )
