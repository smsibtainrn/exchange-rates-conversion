package com.smsrn.exchangerate.di

import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.data.source.remote.service.ExchangeRateService
import com.smsrn.exchangerate.domain.network.ApiGatewayProtocol
import com.smsrn.exchangerate.network.Protocol
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Sibtain Raza on 11/22/2023.
 * sraza@adnocdistribution.ae
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthService(protocol: ApiGatewayProtocol): ExchangeRateService =
        createServiceWithProtocol(protocol, ExchangeRateService::class.java)

    companion object {
        fun <T : Any> createServiceWithProtocol(protocol: Protocol, serviceName: Class<T>): T {
            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logging)
                }
                addInterceptor(protocol).build()
                readTimeout(protocol.readTimeOutMs, TimeUnit.MILLISECONDS)
                writeTimeout(protocol.writeTimeOutMs, TimeUnit.MILLISECONDS)
                connectTimeout(protocol.connectionTimeOutMs, TimeUnit.MILLISECONDS)
            }.build()

            val retrofit = Retrofit.Builder().apply {
                client(client)
                addConverterFactory(GsonConverterFactory.create())
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                baseUrl(protocol.baseURL)
            }.build()

            return retrofit.create(serviceName)
                ?: throw IllegalStateException("ServiceManager not yet initialized.")
        }
    }
}