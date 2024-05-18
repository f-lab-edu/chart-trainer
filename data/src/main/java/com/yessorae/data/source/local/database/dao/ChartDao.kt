package com.yessorae.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.source.local.database.model.ChartEntity

@Dao
interface ChartDao : BaseDao<ChartEntity> {
    @Query(
        """
        SELECT * FROM ${ChartEntity.NAME} WHERE id = :id
        """
    )
    suspend fun getChart(id: Long): ChartEntity
}
