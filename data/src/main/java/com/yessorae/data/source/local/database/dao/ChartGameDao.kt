/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
