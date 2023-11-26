package com.smsrn.exchangerate.data.source.remote.service

import com.smsrn.exchangerate.domain.models.ExchangeRate
import java.util.*


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */
class FakeExchangeRateService : ExchangeRateService {

    var exception: Exception? = null
    var successResponse = ExchangeRate(
        "Usage subject to terms: https://openexchangerates.org/terms",
        "https://openexchangerates.org/license",
        1700697599,
        "USD",
        TreeMap<String, Double>().apply {
            put("AED", 3.67261)
            put("EUR", 0.918451)
            put("INR", 83.364555)
            put("PKR", 284.804209)
            put("USD", 1.00)
        })

    override suspend fun fetchHistoricalExchangeRates(
        date: String, base: String, appId: String
    ): ExchangeRate {
        exception?.let { throw it }
        return successResponse
    }
}