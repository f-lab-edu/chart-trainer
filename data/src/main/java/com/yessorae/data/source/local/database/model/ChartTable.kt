package com.yessorae.data.source.local.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import java.time.LocalDateTime

@Entity(tableName = ChartTable.NAME)
data class ChartTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COL_GAME_ID)
    val gameId: Long,
    @ColumnInfo(name = COL_TICKER_SYMBOL)
    val tickerSymbol: String,
    @ColumnInfo(name = COL_START_DATE_TIME)
    val startDateTime: LocalDateTime,
    @ColumnInfo(name = COL_END_DATE_TIME)
    val endDateTime: LocalDateTime,
    @ColumnInfo(name = COL_TICKS)
    val ticks: List<Tick>,
    @ColumnInfo(name = COL_TICK_UNIT)
    val tickUnit: TickUnit
) {
    companion object {
        const val NAME = "chart_table"
        const val COL_GAME_ID = "game_id"
        const val COL_TICKER_SYMBOL = "ticker_symbol"
        const val COL_START_DATE_TIME = "start_date_time"
        const val COL_END_DATE_TIME = "end_date_time"
        const val COL_TICKS = "ticks"
        const val COL_TICK_UNIT = "tick_unit"
    }
}
