package com.demo.namelistapp.core

import com.demo.namelistapp.network.NetworkResult
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(NetworkResult.Loading())

        try {
            saveFetchResult(fetch())
            query().map { NetworkResult.Success(it) }
        } catch (throwable: Throwable) {
            query().map { NetworkResult.Error(throwable.message ?: "Some thing went wrong.", it) }
        }
    } else {
        query().map { NetworkResult.Success(it) }
    }

    emitAll(flow)
}