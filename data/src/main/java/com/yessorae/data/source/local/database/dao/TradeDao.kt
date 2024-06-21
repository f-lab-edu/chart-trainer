package com.yessorae.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.source.local.database.model.TradeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeDao : BaseDao<TradeEntity> {
    @Query(
        """
            SELECT * FROM ${TradeEntity.NAME} WHERE ${TradeEntity.COL_GAME_ID} = :gameId
        """
    )
    suspend fun getTrades(gameId: Long): List<TradeEntity>
}
