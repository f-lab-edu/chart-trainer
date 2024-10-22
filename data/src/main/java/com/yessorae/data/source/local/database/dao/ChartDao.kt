package com.yessorae.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.yessorae.data.source.local.database.model.ChartEntity
import com.yessorae.data.source.local.database.model.ChartWithTicksEntity

@Dao
interface ChartDao : BaseDao<ChartEntity> {
    @Query(
        """
            SELECT * FROM ${ChartEntity.NAME} WHERE id = :id
        """
    )
    suspend fun getChart(id: Long): ChartEntity

    @Transaction
    @Query(
        """
            SELECT * FROM ${ChartEntity.NAME} WHERE id = :id
        """
    )
    suspend fun getChartWithTicks(id: Long): ChartWithTicksEntity
}
