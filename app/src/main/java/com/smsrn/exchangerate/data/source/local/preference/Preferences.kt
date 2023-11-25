package com.smsrn.exchangerate.data.source.local.preference

import android.content.Context
import com.smsrn.exchangerate.data.source.local.preference.PreferencesKeys.EXCHANGE_RATES_CACHE_TIME
import javax.inject.Inject

class Preferences @Inject constructor(context: Context) : PreferencesBase(context) {

    var lastCacheExchangeRatesTime: Long
        get() = get(EXCHANGE_RATES_CACHE_TIME, 0L) as Long
        set(lastCacheExchangeRatesTime) = put(EXCHANGE_RATES_CACHE_TIME, lastCacheExchangeRatesTime)
}