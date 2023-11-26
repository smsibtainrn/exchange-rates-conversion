package com.smsrn.exchangerate.domain.models

import java.util.*

data class ExchangeRate(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: TreeMap<String, Double>
) : BaseResponse()