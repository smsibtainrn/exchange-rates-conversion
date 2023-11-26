package com.smsrn.exchangerate.presentation.ui.exchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smsrn.exchangerate.core.network.Response
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.domain.interactor.GetExchangeRatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {

    var exchangeRates = MutableLiveData<Response<ExchangeRateEntity>>()
    val currencies = MutableLiveData<List<String>>()
    var amount = MutableLiveData<String>()
    var selectedCurrency = MutableLiveData<String?>()

    init {
        fetchExchangeRates()
    }

    fun fetchExchangeRates() =
        viewModelScope.launch {
            exchangeRates.postValue(Response.Loading)
            getExchangeRatesUseCase.invoke("USD").catch {
                exchangeRates.postValue(Response.Error(it.message ?: ""))
            }.collect {
                exchangeRates.postValue(Response.Success(it))
            }
        }

    fun setCurrency(value: String?) {
        selectedCurrency.value = value
    }
}