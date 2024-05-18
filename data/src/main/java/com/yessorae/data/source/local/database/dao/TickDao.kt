package com.yessorae.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.yessorae.data.source.local.database.model.TickEntity

@Dao
interface TickDao : BaseDao<TickEntity> {
    @Query(
        """
            SELECT * FROM ${TickEntity.NAME} WHERE ${TickEntity.COL_CHART_ID} = :chartId
        """
    )
    suspend fun getTicks(chartId: Long): List<TickEntity>
}
