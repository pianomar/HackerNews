package com.omarhezi.reignhackernews.latestposts.model.models

sealed class ResponseResult<out R> {
    data class Success<out T>(val data: T) : ResponseResult<T>()
    data class Error(val errorType: ErrorType? = ErrorType.GENERIC, val message: String? = null) : ResponseResult<Nothing>()

    enum class ErrorType {
        NETWORK, GENERIC
    }
}