package com.smsrn.exchangerate.network

import com.google.gson.Gson
import com.smsrn.exchangerate.domain.model.BaseResponse
import com.smsrn.exchangerate.network.ProtocolException.SOMETHING_WENT_WRONG
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RequestAdapter<E>(
    private val call: Call<E>,
    private val protocol: Protocol
) : Request<E> {

    private var suppressUnAuthorizedPropagation: Boolean = false

    override suspend fun execute() = suspendCancellableCoroutine { continuation ->
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = performExecution()
                continuation.resume(response)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    private fun performExecution(): Response<E?> {
        try {
            val rawResponse = call.execute()

            rawResponse.errorBody()?.let {
                return checkProtocolException(rawResponse)
            } ?: run {
                if (rawResponse.isSuccessful) {
                    return Response.Success(rawResponse.body())
                } else {
                    return Response.Error(SOMETHING_WENT_WRONG, 500)
                }
            }
        } catch (e: Exception) {
            return checkFailedException(e)
        }
    }

    private fun checkFailedException(e: Exception): Response.Error {
        val exceptionMessage: String = when (e) {
            is SocketTimeoutException -> protocol.errorConnectionTimeout
            is ConnectException -> protocol.errorConnectionFailed
            is UnknownHostException -> protocol.errorUnknownHost
            else -> SOMETHING_WENT_WRONG
        }
        return Response.Error(exceptionMessage)
    }

    private fun checkProtocolException(rawResponse: retrofit2.Response<E>): Response.Error {
        var exceptionMessage: String? = null
        var exceptionCode: Int = rawResponse.code()

        if (rawResponse.code() == 401) {
            protocol.onUnauthorized()
        }

        if (!protocol.exceptions.isNullOrEmpty() &&
            protocol.exceptions?.containsKey(rawResponse.code()) == true
        ) {
            exceptionMessage = protocol.exceptions!![rawResponse.code()]
        } else if (ProtocolException.exceptions.containsKey(rawResponse.code())) {
            exceptionMessage = ProtocolException.exceptions[rawResponse.code()]
        }

        try {
            val res: BaseResponse =
                Gson().fromJson(rawResponse.errorBody()?.string(), BaseResponse::class.java)
            exceptionMessage = res.message
            res.code?.let { exceptionCode = it }
        } catch (e: Exception) {
            rawResponse.errorBody()?.let {
                exceptionMessage = it.string()
            }
        }

        return Response.Error(exceptionMessage ?: SOMETHING_WENT_WRONG, exceptionCode)
    }

    override suspend fun invoke(): Response<E?> {
        return execute()
    }

    override fun suppressUnAuthorizedPropagation(): Request<E> {
        suppressUnAuthorizedPropagation = true
        return this
    }

    override fun cancel() {
        call.cancel()
    }

    class Factory(private val protocol: Protocol) : CallAdapter.Factory() {
        override fun get(
            returnType: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
        ): CallAdapter<*, *>? {
            if (getRawType(returnType) != Request::class.java)
                return null

            check(returnType is ParameterizedType) { "Request must have generic type (e.g., Request<ResponseBody>)" }

            return object : CallAdapter<Call<*>, Request<*>> {
                override fun responseType(): Type =  getParameterUpperBound(0, returnType)

                override fun adapt(call: Call<Call<*>>): Request<*> = RequestAdapter(call, protocol)
            }
        }
    }
}