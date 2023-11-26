package com.smsrn.exchangerate.data.source.local

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smsrn.exchangerate.core.network.Response
import com.smsrn.exchangerate.data.repository.ExchangeRateLocal
import com.smsrn.exchangerate.data.source.local.entity.ExchangeRateEntity
import com.smsrn.exchangerate.domain.interactor.GetExchangeRatesUseCase
import com.smsrn.exchangerate.presentation.fakes.FakePresentationData
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.Mockito
import org.robolectric.annotation.Config


/**
 * Created by Sibtain Raza on 11/26/2023.
 * sraza@adnocdistribution.ae
 */
@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class ExchangeRateLocalImplTest : BaseLocalImplTest() {

    private val exchangeRatesUseCase: GetExchangeRatesUseCase = mockk()

    lateinit var exchangeRateLocal: ExchangeRateLocal
    private lateinit var observer: Observer<Response<ExchangeRateEntity>>
    private lateinit var inOrder: InOrder

    @Before
    override fun setUp() {
        super.setUp()
        observer = Mockito.mock(Observer::class.java) as Observer<Response<ExchangeRateEntity>>
        exchangeRateLocal = ExchangeRateLocalImpl(preferencesHelper, exchangeRateDao)
        inOrder = Mockito.inOrder(observer)
    }

    @Test
    fun `Should Return ExchangeRateEntity From Local Room Cache`() = runBlocking {
        val exchangeRateEntity = FakePresentationData.fetchExchangeRates()
        exchangeRateLocal.saveExchangeRate(exchangeRateEntity)
        val exchangeRate = exchangeRateLocal.getExchangeRate("USD")
        assertEquals(exchangeRate, exchangeRateEntity)
    }

    @Test
    fun `Should Return false If ExchangeRateEntity Not Exist In Local Room Cache`() = runBlocking {
        val isCached = exchangeRateLocal.isCached("USD")
        assertEquals(isCached, false)
    }

    @Test
    fun `Should Return true If ExchangeRateEntity Exist In Local Room Cache`() = runBlocking {
        val exchangeRateEntity = FakePresentationData.fetchExchangeRates()
        exchangeRateLocal.saveExchangeRate(exchangeRateEntity)
        val isCached = exchangeRateLocal.isCached("USD")
        assertEquals(isCached, true)
    }

    @Test
    fun `Should Return isExpired as false`() = runBlocking {
        val exchangeRateEntity = FakePresentationData.fetchExchangeRates()
        exchangeRateLocal.saveExchangeRate(exchangeRateEntity)
        val isExpired = exchangeRateLocal.isExpired()
        assertEquals(isExpired, false)
    }

    @Test
    fun `Should Return isExpired as true`() = runBlocking {
        val exchangeRateEntity = FakePresentationData.fetchExchangeRates()

        exchangeRateLocal.saveExchangeRate(exchangeRateEntity)
        exchangeRateLocal.setLastCacheTime(System.currentTimeMillis() - ((31 * 60 * 1000).toLong()))
        val isExpired = exchangeRateLocal.isExpired()
        assertEquals(isExpired, true)
    }
}