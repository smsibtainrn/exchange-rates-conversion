package com.smsrn.exchangerate.presentation.fakes

import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import java.util.*

object FakePresentationData {

    fun fetchExchangeRates(): ExchangeRateEntity {
        val response = ExchangeRateEntity("USD", TreeMap<String, Double>().apply {
            put("AED", 3.67261)
            put("EUR", 0.918451)
            put("INR", 83.364555)
            put("PKR", 284.804209)
            put("USD", 1.00)
        })
        return response
    }
}
