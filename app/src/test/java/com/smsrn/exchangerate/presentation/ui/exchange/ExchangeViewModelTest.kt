package com.smsrn.exchangerate.presentation.ui.exchange

import androidx.lifecycle.Observer
import com.smsrn.exchangerate.core.network.Response
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.domain.interactor.GetExchangeRatesUseCase
import com.smsrn.exchangerate.presentation.fakes.FakePresentationData
import com.smsrn.exchangerate.utils.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mockito


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */

@ExperimentalCoroutinesApi
class ExchangeViewModelTest : BaseViewModelTest() {

    private val exchangeRatesUseCase: GetExchangeRatesUseCase = mockk()
    private lateinit var observer: Observer<Response<ExchangeRateEntity>>
    private lateinit var inOrder: InOrder

    @Before
    override fun setUp() {
        super.setUp()
        observer = Mockito.mock(Observer::class.java) as Observer<Response<ExchangeRateEntity>>
        inOrder = Mockito.inOrder(observer)
    }

    @Test
    fun `GET - Exchange Rates - Should Return ExchangeRateEntity From Use Case`() = runTest {
        val exchangeRates = FakePresentationData.fetchExchangeRates()

        coEvery { exchangeRatesUseCase("USD") } returns flowOf(exchangeRates)

        setViewModelUseCase()
        advanceUntilIdle()

        inOrder.verify(observer).onChanged(Response.Loading)
        inOrder.verify(observer).onChanged(Response.Success(exchangeRates))
    }

    @Test
    fun `GET - Exchange Rates - Should Return Error From Use Case`() = runTest {
        val errorMessage = "Runtime Error"

        coEvery { exchangeRatesUseCase("USD") } returns flow { throw RuntimeException(errorMessage) }

        setViewModelUseCase()
        advanceUntilIdle()

        inOrder.verify(observer).onChanged(Response.Loading)
        inOrder.verify(observer).onChanged(Response.Error(errorMessage))
    }

    private fun setViewModelUseCase() {
        val viewModel = ExchangeViewModel(exchangeRatesUseCase)
        viewModel.exchangeRates.observeForever(observer)
    }
}