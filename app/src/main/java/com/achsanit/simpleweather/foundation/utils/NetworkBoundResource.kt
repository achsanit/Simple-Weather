package com.achsanit.simpleweather.foundation.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    val resource = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            val resultType = fetch()

            saveFetchResult(resultType)

            query().map { Resource.Success(it) }
        } catch (e: HttpException) {
            when(e.code()) {
                in 400..499 -> {
                    query().map {
                        Resource.Error(
                            e.message.toString() + "-${e.code()}",
                            e.code(), it
                        )
                    }
                }
                in 500..599 -> {
                    query().map {
                        Resource.ServerError(
                            e.message.toString() + "-${e.code()}",
                            e.code(), it
                        )
                    }
                }
                else -> {
                    query().map {
                        Resource.Error(
                            e.message.toString() + "-${e.code()}",
                            e.code(), it
                        )
                    }
                }
            }
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            query().map {
                Resource.Error(e.message.toString(), CodeError.NO_INTERNET_CONNECTION)
            }
        } catch (e: SocketTimeoutException) {
            e.printStackTrace()
            query().map {
                Resource.Error(e.message.toString(), CodeError.REQUEST_TIME_OUT)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            query().map {
                Resource.Error(e.message.toString(), CodeError.SOMETHING_WENT_WRONG)
            }
        }
    } else {
        query().map {
            Resource.Success(it)
        }
    }
    emitAll(resource)
}