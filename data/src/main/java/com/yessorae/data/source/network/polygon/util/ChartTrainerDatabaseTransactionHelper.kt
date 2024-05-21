package com.yessorae.data.source.network.polygon.util

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import javax.inject.Inject

class ChartTrainerDatabaseTransactionHelper @Inject constructor(
    private val roomDatabase: RoomDatabase
) : DatabaseTransactionHelper {
    override suspend fun runTransaction(run: suspend () -> Unit) {
        roomDatabase.withTransaction(run)
    }
}
