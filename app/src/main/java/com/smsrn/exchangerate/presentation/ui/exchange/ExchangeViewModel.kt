package com.smsrn.exchangerate.presentation.ui.exchange

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.smsrn.exchangerate.core.BaseViewModel
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.domain.interactor.GetExchangeRatesUseCase
import com.smsrn.exchangerate.domain.model.ExchangeRate
import com.smsrn.exchangerate.domain.repository.ExchangeRateRepository
import com.smsrn.exchangerate.network.Response
import com.smsrn.exchangerate.utils.CoroutineContextProvider
import com.smsrn.exchangerate.utils.Utils.getDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


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
    var selectedCurrency = MutableLiveData<String?>()

    init {
        fetchExchangeRates()
    }

    private fun fetchExchangeRates() =
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