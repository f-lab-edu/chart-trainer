package com.yessorae.data.source.network.polygon.util

import androidx.room.withTransaction
import com.yessorae.data.source.local.database.ChartTrainerDatabase
import javax.inject.Inject

class ChartTrainerDatabaseTransactionHelper @Inject constructor(
    private val roomDatabase: ChartTrainerDatabase
) : DatabaseTransactionHelper {
    override suspend fun runTransaction(run: suspend () -> Unit) {
        roomDatabase.withTransaction(run)
    }
}
