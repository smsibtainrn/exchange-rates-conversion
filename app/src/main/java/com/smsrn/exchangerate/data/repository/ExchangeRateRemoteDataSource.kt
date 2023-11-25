package com.smsrn.exchangerate.data.repository

import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity


/**
 * Created by Sibtain Raza on 11/24/2023.
 * sraza@adnocdistribution.ae
 */
interface ExchangeRateRemoteDataSource {
    fun getExchangeRate(): ExchangeRateEntity
}