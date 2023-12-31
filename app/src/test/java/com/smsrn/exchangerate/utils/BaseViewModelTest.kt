package com.smsrn.exchangerate.utils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val firstRule = InstantTaskExecutorRule()

    @get:Rule
    val secondRule = TrampolineSchedulerRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }
}