package com.example.store.data.util

sealed class Resource<T>(val data: T? = null, val exception: Exception? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(exception: Exception, data: T? = null) : Resource<T>(data, exception)
    class Loading<T> : Resource<T>()
}