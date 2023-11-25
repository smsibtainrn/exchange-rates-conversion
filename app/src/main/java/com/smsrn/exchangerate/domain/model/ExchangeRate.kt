package com.smsrn.exchangerate.domain.model

import java.util.*

data class ExchangeRate(
    val disclaimer: String,
    val license: String,
    val timestamp: String,
    val base: String,
    val rates: TreeMap<String, Double>
) : BaseResponse()