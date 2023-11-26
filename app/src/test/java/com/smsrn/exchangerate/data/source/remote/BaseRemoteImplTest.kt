package com.smsrn.exchangerate.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.smsrn.exchangerate.data.mapper.domainEntity.ExchangeRateDomainEntityMapper
import com.smsrn.exchangerate.data.source.remote.service.FakeExchangeRateService
import com.smsrn.exchangerate.utils.TrampolineSchedulerRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */
open class BaseRemoteImplTest {
    @get:Rule
    val firstRule = InstantTaskExecutorRule()

    @get:Rule
    val secondRule = TrampolineSchedulerRule()

    val exchangeRateService = FakeExchangeRateService()
    val exchangeRateMapper = ExchangeRateDomainEntityMapper()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }
}