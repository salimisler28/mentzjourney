package com.mentz.mentzjourney.data.helpers

import com.mentz.mentzjourney.core.AppException
import retrofit2.Response

suspend fun <T> makeRequest(
    call: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = call.invoke()

        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: run {
                Result.failure(Exception("Null response"))
            }
        } else {
            Result.failure(AppException.NetworkException(message = response.message()))
        }
    } catch (e: Exception) {
        when (e) {
            is java.net.UnknownHostException -> Result.failure(AppException.NoInternetConnectionException)
            else -> Result.failure(AppException.NetworkException(message = e.message))
        }
    }
}