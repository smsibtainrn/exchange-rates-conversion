package com.smsrn.exchangerate.domain.network

import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.network.Protocol
import javax.inject.Inject

class ApiGatewayProtocol @Inject constructor() : Protocol() {

    override val baseURL = BuildConfig.EXCHANGE_RATE_ENDPOINT
}