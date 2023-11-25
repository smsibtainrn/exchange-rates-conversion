package com.smsrn.exchangerate.domain.interactor

import com.smsrn.exchangerate.domain.repository.ExchangeRateRepository
import com.smsrn.exchangerate.utils.Utils
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/25/2023.
 * sraza@adnocdistribution.ae
 */
class GetExchangeRatesUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend operator fun invoke(currencyCode: String, onlyFromCache: Boolean = false) =
        if (onlyFromCache) {
            exchangeRateRepository.fetchHistoricalExchangeRates(currencyCode)
        } else {
            exchangeRateRepository.fetchHistoricalExchangeRates(
                currencyCode, Utils.getDate(-1)
            )
        }
}