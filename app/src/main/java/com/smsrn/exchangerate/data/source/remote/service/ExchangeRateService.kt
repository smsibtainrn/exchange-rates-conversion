package com.smsrn.exchangerate.data.source.remote.service

import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.domain.model.ExchangeRate
import com.smsrn.exchangerate.network.Request
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */
interface ExchangeRateService {

    @GET("historical/{date}.json")
    suspend fun fetchHistoricalExchangeRates(
        @Path("date") date: String,
        @Query("base") base: String,
        @Query("app_id") appId: String = BuildConfig.EXCHANGE_RATE_API_KEY
    ): ExchangeRate
}