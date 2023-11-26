package com.smsrn.exchangerate.utils
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.testing.TestLifecycleOwner
import com.smsrn.exchangerate.data.source.local.db.DBExchangeRate
import com.smsrn.exchangerate.data.source.local.preference.Preferences
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule

open class BaseViewModelTest {
    @get:Rule
    val firstRule = InstantTaskExecutorRule()

    @get:Rule
    val secondRule = TrampolineSchedulerRule()

    internal var failMessage = "Net error"
    internal var requestTimeOutError = "Request timed out"

//    internal val fakeExchangeRateApiService = FakeAuthApiService()

    internal val preferencesHelper: Preferences = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val testDispatcher = StandardTestDispatcher()

    internal val lifecycleOwner = TestLifecycleOwner(
        Lifecycle.State.RESUMED,
        CurrentThreadExecutor().asCoroutineDispatcher()
    )

    internal val db = mockk<DBExchangeRate>()
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }
}