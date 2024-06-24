package com.yessorae.chartrainer.fake

import com.yessorae.data.source.local.database.dao.BaseDao

abstract class FakeBaseDao<T> : BaseDao<T> {
    protected val items = mutableListOf<T>()
    private var currentId = 1L

    override suspend fun insertAll(entities: List<T>) {
        entities.forEach { insert(it) }
    }

    override suspend fun insert(entity: T): Long {
        items.add(entity)
        return currentId++
    }

    override suspend fun updateAll(entities: List<T>) {
        entities.forEach { update(it) }
    }

    override suspend fun update(entity: T) {
        val index = items.indexOf(entity)
        if (index != -1) {
            items[index] = entity
        }
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
        items.remove(entity)
    }
}
