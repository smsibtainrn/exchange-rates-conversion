package com.smsrn.exchangerate.domain.network

import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.core.network.Protocol
import javax.inject.Inject

class ApiGatewayProtocol @Inject constructor() : Protocol() {

    override val baseURL = BuildConfig.EXCHANGE_RATE_ENDPOINT

    override fun getQueryParameters() = HashMap<String, String>().apply {
        put("app_id", BuildConfig.EXCHANGE_RATE_API_KEY)
    }
}