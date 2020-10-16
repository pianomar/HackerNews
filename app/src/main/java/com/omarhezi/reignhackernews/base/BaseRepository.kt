package com.omarhezi.reignhackernews.base

import android.util.Log
import com.omarhezi.reignhackernews.latestposts.model.models.ResponseResult
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository(private val tag: String) {
    protected fun handleResponseError(throwable: Throwable) =
        when (throwable) {
            is IOException -> ResponseResult.Error(ResponseResult.ErrorType.NETWORK)
            is HttpException -> {
                Log.e(tag, throwable.code().toString())
                val errorResponse = throwable.response()?.message()
                ResponseResult.Error(ResponseResult.ErrorType.GENERIC, errorResponse)
            }
            else -> ResponseResult.Error(ResponseResult.ErrorType.GENERIC)
        }
}