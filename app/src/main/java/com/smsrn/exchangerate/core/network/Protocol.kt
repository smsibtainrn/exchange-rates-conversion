package com.smsrn.exchangerate.core.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import java.net.ProtocolException

/**
 * Created by Sibtain Raza on 12/7/2022.
 * smsibtainrn@gmail.com
 */
abstract class Protocol : Interceptor {

    abstract val baseURL: String

    open val readTimeOutMs: Long = 60000
    open val writeTimeOutMs: Long = 60000
    open val connectionTimeOutMs: Long = 60000

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder =
            chain.request().newBuilder().method(chain.request().method, chain.request().body)

        getHeaders()?.forEach {
            Log.d("HEADERS", "${it.key}:${it.value}")
            requestBuilder.addHeader(it.key, it.value)
        }

        getQueryParameters()?.let { parameters ->
            with(request.url.newBuilder()) {
                parameters.map {
                    Log.d("QUERY_PARAMETERS", "${it.key}:${it.value}")
                    addQueryParameter(it.key, it.value)
                }
                build().apply { requestBuilder.url(this) }
            }
        }

        return try {
            chain.proceed(requestBuilder.build())
        } catch (e: ProtocolException) {
            Response.Builder().request(chain.request()).code(204).protocol(Protocol.HTTP_1_1)
                .build()
        }
    }

    open fun getHeaders(): Map<String, String>? = null
    open fun getQueryParameters(): Map<String, String>? = null
}