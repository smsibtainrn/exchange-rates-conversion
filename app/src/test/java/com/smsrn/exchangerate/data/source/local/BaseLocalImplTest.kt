package com.smsrn.exchangerate.data.source.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.smsrn.exchangerate.data.source.local.db.DBExchangeRate
import com.smsrn.exchangerate.data.source.local.db.ExchangeRatesDao
import com.smsrn.exchangerate.data.source.local.preference.Preferences
import com.smsrn.exchangerate.utils.TrampolineSchedulerRule
import org.junit.Before
import org.junit.Rule

open class BaseLocalImplTest {
    @get:Rule
    val firstRule = InstantTaskExecutorRule()

    @get:Rule
    val secondRule = TrampolineSchedulerRule()
    protected lateinit var context: Context
    protected lateinit var preferencesHelper: Preferences
    protected lateinit var db: DBExchangeRate
    protected lateinit var exchangeRateDao: ExchangeRatesDao

    @Before
    open fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        preferencesHelper = Preferences(context)
        db = Room.inMemoryDatabaseBuilder(context, DBExchangeRate::class.java)
            .allowMainThreadQueries().build()
        exchangeRateDao = db.exchangeRatesDao()
    }
}