package com.yessorae.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.source.local.database.model.ChartGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChartGameDao : BaseDao<ChartGameEntity> {
    @Query(
        """
            SELECT * from ${ChartGameEntity.NAME} WHERE id = (:id) 
        """
    )
    fun getChartGameAsFlow(id: Long): Flow<ChartGameEntity>

    @Query(
        """
            SELECT * from ${ChartGameEntity.NAME} WHERE id = (:id) 
        """
    )
    suspend fun getChartGame(id: Long): ChartGameEntity
}