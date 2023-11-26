package com.smsrn.exchangerate.data.source.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smsrn.exchangerate.data.repository.ExchangeRateLocal
import com.smsrn.exchangerate.presentation.fakes.FakePresentationData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */
@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class ExchangeRateLocalImplTest : BaseLocalImplTest() {

    lateinit var exchangeRateLocal: ExchangeRateLocal

    @Before
    override fun setUp() {
        super.setUp()
        exchangeRateLocal = ExchangeRateLocalImpl(preferencesHelper, exchangeRateDao)
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