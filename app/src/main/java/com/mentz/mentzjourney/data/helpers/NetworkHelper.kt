package com.mentz.mentzjourney.data.helpers

import retrofit2.Response

suspend fun <T> makeRequest(
    call: suspend () -> Response<T>
): Result<T> {
    val response = call.invoke()

    return if (response.isSuccessful) {
        response.body()?.let {
            Result.success(it)
        } ?: run {
            Result.failure(Exception("Null response"))
        }
    } else {
        Result.failure(Exception(response.message()))
    }
}