package com.smsrn.exchangerate.network

import android.util.Log
import com.smsrn.exchangerate.BuildConfig
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

        request.url.newBuilder()
            .addQueryParameter("app_id", BuildConfig.EXCHANGE_RATE_API_KEY)
            .build().apply { requestBuilder.url(this) }

        getHeaders()?.forEach {
            Log.d("HEADERS", "${it.key}:${it.value}")
            requestBuilder.addHeader(it.key, it.value)
        }

        return try {
            chain.proceed(requestBuilder.build())
        } catch (e: ProtocolException) {
            Response.Builder().request(chain.request()).code(204).protocol(Protocol.HTTP_1_1)
                .build()
        }
    }

    open fun getHeaders(): Map<String, String>? = null
}