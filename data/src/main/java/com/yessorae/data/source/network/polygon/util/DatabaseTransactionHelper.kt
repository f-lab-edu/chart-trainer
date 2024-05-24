package com.yessorae.data.source.network.polygon.util

/**
 * 여러 테이블에 대해 데이터베이스를 업데이트할 때 사용
 */
interface DatabaseTransactionHelper {
    suspend fun runTransaction(run: suspend () -> Unit)
}
