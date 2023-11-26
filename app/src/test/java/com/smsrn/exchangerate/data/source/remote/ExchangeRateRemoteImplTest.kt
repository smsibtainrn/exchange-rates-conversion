package com.smsrn.exchangerate.data.source.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smsrn.exchangerate.data.repository.ExchangeRateRemote
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.annotation.Config
import java.io.IOException


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */
@ExperimentalCoroutinesApi
@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class ExchangeRateRemoteImplTest : BaseRemoteImplTest() {

    lateinit var exchangeRateRemote: ExchangeRateRemote

    @Before
    override fun setUp() {
        super.setUp()
        exchangeRateRemote = ExchangeRateRemoteImpl(exchangeRateService, exchangeRateMapper)
    }

    @Test
    fun `Get Success Response on ExchangeRate`() = runBlocking {
        val response = exchangeRateRemote.getExchangeRate("USD")
        assertEquals(exchangeRateMapper.toEntity(exchangeRateService.successResponse), response)
    }

    @Test(expected = IOException::class)
    fun `Get IO Exception on ExchangeRate`() = runTest {
        exchangeRateService.exception = IOException()
        exchangeRateRemote.getExchangeRate("USD")
    }
}