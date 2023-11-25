package com.smsrn.exchangerate.core.network

/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */

sealed class Response<out T> {
    object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T
    ) : Response<T>()

    data class Error(
        val message: String,
        var code: Int = -1,
        var subCode: Int = -1
    ) : Response<Nothing>()
}