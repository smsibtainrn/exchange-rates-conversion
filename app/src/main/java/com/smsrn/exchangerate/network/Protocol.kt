package com.smsrn.exchangerate.network

import android.util.Log
import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.network.ProtocolException.ERROR_CONNECTION_FAILED
import com.smsrn.exchangerate.network.ProtocolException.ERROR_CONNECTION_TIME_OUT
import com.smsrn.exchangerate.network.ProtocolException.ERROR_UNKNOWN_HOST
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

    var unAuthorizedEvent: OnUnauthorizedEvent? = null
    open var suppressUnAuthorizedPropagation: Boolean = false

    open val exceptions: HashMap<Int, String>? = null

    open val errorConnectionTimeout: String = ERROR_CONNECTION_TIME_OUT
    open val errorConnectionFailed: String = ERROR_CONNECTION_FAILED
    open val errorUnknownHost: String = ERROR_UNKNOWN_HOST

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

    open fun onUnauthorized(request: Request<*>, parsedErrorMessage: String) {}

    fun onUnauthorized() {
        if (!suppressUnAuthorizedPropagation)
            unAuthorizedEvent?.onUnauthorized()
    }
}