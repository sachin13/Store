package com.example.store.data.repository.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import com.example.store.data.util.*
import kotlinx.coroutines.flow.flowOn


open class BaseRepository : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    protected suspend fun <T> apiCall(request: suspend () -> Response<T>): Flow<Resource<T>> =
        flow<Resource<T>> {
            try {
                val result = request.invoke()
                if (result.isSuccessful) {
                    if (result.body() != null)
                        emit(Resource.Success(result.body()!!))
                    else
                        emit(Resource.Error(ResponseNullException()))
                } else {
                    emit(Resource.Error(RequestNotSuccessException()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }.flowOn(coroutineContext)

    /*
        Original version
        https://stackoverflow.com/questions/58486364/networkboundresource-with-kotlin-coroutines
    */
    protected inline fun <ResultType, RequestType> networkBoundResource(
        crossinline fetch: suspend () -> Flow<RequestType>,
        crossinline map: (RequestType) -> (ResultType)
    ) = flow {
        emit(Resource.Loading())
        when (val apiResponse = fetch().first()) {
            is Resource.Success<*> -> {
                emit(Resource.Success(map(apiResponse)))
            }
            is Resource.Error<*> -> {
                emit(Resource.Error(apiResponse.exception ?: Exception()))
            }
        }
    }
}