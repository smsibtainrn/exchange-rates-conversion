package com.smsrn.exchangerate.presentation.ui.conversions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smsrn.exchangerate.domain.interactor.GetExchangeRatesUseCase
import com.smsrn.exchangerate.domain.models.ExchangeConversion
import com.smsrn.exchangerate.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/24/2023.
 * sraza@adnocdistribution.ae
 */
@HiltViewModel
class ConversionsViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {

    var amount = MutableLiveData<Double>()
    var selectedCurrency = MutableLiveData<String>()

    val conversions = MutableLiveData<List<ExchangeConversion>>()

    fun fetchExchangeRates() = viewModelScope.launch {
        getExchangeRatesUseCase.invoke("USD", true).collectLatest { rates ->
            val selectedCurrencyRateUSD = rates.rates[selectedCurrency.value]
            rates.rates.remove(selectedCurrency.value)
            conversions.value = rates.rates.map { exchange ->
                ExchangeConversion(
                    exchange.key,
                    Utils.getConversionRate(
                        amount.value!!, selectedCurrencyRateUSD!!, exchange.value
                    )
                )
            }
        }
    }
}