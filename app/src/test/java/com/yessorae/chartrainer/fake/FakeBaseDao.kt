package com.yessorae.chartrainer.fake

import com.yessorae.data.source.local.database.dao.BaseDao

abstract class FakeBaseDao<T> : BaseDao<T> {
    protected var items: List<T> = listOf()
    var currentId = 1L
        private set

    override suspend fun insertAll(entities: List<T>) {
        entities.forEach { insert(it) }
    }

    override suspend fun insert(entity: T): Long {
        items = items.toMutableList().apply {
            add(entity)
        }
        return currentId++
    }

    override suspend fun updateAll(entities: List<T>) {
        entities.forEach { update(it) }
    }

    override suspend fun insertOrReplaceAll(entities: List<T>) {
        entities.forEach { insertOrReplace(it) }
    }

    override suspend fun insertOrReplace(entity: T) {
        if (!items.contains(entity)) {
            insert(entity)
        } else {
            update(entity)
        }
    }

    override suspend fun delete(entity: T) {
        items = items.toMutableList().apply {
            remove(entity)
        }
    }
}
