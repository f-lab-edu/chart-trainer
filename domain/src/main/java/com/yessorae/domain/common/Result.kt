package com.yessorae.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val throwable: Throwable) : Result<Nothing>()
}

fun Flow<Result<Unit>>.delegateEmptyResultFlow(): Flow<Result<Unit>> {
    return this
        .emitLoadingResultOnStart()
        .emitFailureResultOnCatch()
        .emitSuccessResultOnEmpty()
}

fun <T> Flow<T>.delegateValueResultFlow(): Flow<Result<T>> {
    return this
        .mapToSuccessResult()
        .emitLoadingResultOnStart()
        .emitFailureResultOnCatch()
}

private fun <T> Flow<T>.mapToSuccessResult(): Flow<Result<T>> {
    return this.map { Result.Success(data = it) }
}

private fun Flow<Result<Unit>>.emitSuccessResultOnEmpty(): Flow<Result<Unit>> {
    return this.onCompletion {
        emit(Result.Success(data = Unit))
    }
}

private fun <T> Flow<Result<T>>.emitLoadingResultOnStart(): Flow<Result<T>> {
    return this.onStart {
        emit(Result.Loading)
    }
}

private fun <T> Flow<Result<T>>.emitFailureResultOnCatch(): Flow<Result<T>> {
    return this.catch { throwable ->
        emit(Result.Failure(throwable = throwable))
    }
}
