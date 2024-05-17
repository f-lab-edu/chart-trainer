package com.yessorae.data.source.local.database.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.yessorae.domain.entity.value.Money

data class ChartGameTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COL_CURRENT_TURN)
    val currentTurn: Int,
    @ColumnInfo(name = COL_TOTAL_TURN)
    val totalTurn: Int,
    @ColumnInfo(name = COL_START_BALANCE)
    val startBalance: Money,
    @ColumnInfo(name = COL_CURRENT_BALANCE)
    val currentBalance: Money,
    @ColumnInfo(name = COL_IS_QUIT)
    val isQuit: Boolean
) {
    companion object {
        const val COL_CURRENT_TURN = "current_turn"
        const val COL_TOTAL_TURN = "total_turn"
        const val COL_START_BALANCE = "start_balance"
        const val COL_CURRENT_BALANCE = "current_balance"
        const val COL_IS_QUIT = "is_quit"
    }
}
