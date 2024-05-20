package com.yessorae.data.source.local.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Upsert

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Long

    @Update
    suspend fun updateAll(entities: List<T>)

    @Update
    suspend fun update(entity: T)

    @Upsert
    suspend fun insertOrReplaceAll(entities: List<T>)

    @Upsert
    suspend fun insertOrReplace(entity: T)

    @Delete
    suspend fun delete(entity: T)
}
