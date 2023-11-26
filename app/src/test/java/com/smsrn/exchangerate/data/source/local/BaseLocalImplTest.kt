package com.smsrn.exchangerate.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.testing.TestLifecycleOwner
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.smsrn.exchangerate.data.source.local.db.DBExchangeRate
import com.smsrn.exchangerate.data.source.local.db.ExchangeRatesDao
import com.smsrn.exchangerate.data.source.local.preference.Preferences
import com.smsrn.exchangerate.utils.CurrentThreadExecutor
import com.smsrn.exchangerate.utils.TrampolineSchedulerRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule

open class BaseLocalImplTest {
    @get:Rule
    val firstRule = InstantTaskExecutorRule()

    @get:Rule
    val secondRule = TrampolineSchedulerRule()

    internal var failMessage = "Net error"
    internal var requestTimeOutError = "Request timed out"

    lateinit var preferencesHelper: Preferences
    lateinit var db: DBExchangeRate
    lateinit var exchangeRateDao: ExchangeRatesDao
    protected lateinit var context: Context

    @OptIn(ExperimentalCoroutinesApi::class)
    internal val testDispatcher = StandardTestDispatcher()

    internal val lifecycleOwner = TestLifecycleOwner(
        Lifecycle.State.RESUMED,
        CurrentThreadExecutor().asCoroutineDispatcher()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    open fun setUp() {
        Dispatchers.setMain(testDispatcher)
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, DBExchangeRate::class.java)
            .allowMainThreadQueries().build()
        exchangeRateDao = db.exchangeRatesDao()
        preferencesHelper = Preferences(context)
    }
}