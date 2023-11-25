package com.smsrn.exchangerate.di

import android.content.Context
import androidx.room.Room
import com.smsrn.exchangerate.data.repository.ExchangeRateLocal
import com.smsrn.exchangerate.data.source.local.ExchangeRateLocalImpl
import com.smsrn.exchangerate.data.source.local.db.DBExchangeRate
import com.smsrn.exchangerate.data.source.local.db.ExchangeRatesDao
import com.smsrn.exchangerate.data.source.local.preference.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Sibtain Raza on 11/23/2023.
 * smsibtainrn@gmail.com
 */
@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context) = Preferences(app)

    @Provides
    @Singleton
    fun provideCookDatabase(
        @ApplicationContext app: Context,
    ): DBExchangeRate {
        return Room.databaseBuilder(
            app,
            DBExchangeRate::class.java, "exchange_rate.db"
        ).apply {
            allowMainThreadQueries()
        }.build()
    }

    @Provides
    @Singleton
    fun provideExchangeRateDao(exchangeRate: DBExchangeRate): ExchangeRatesDao {
        return exchangeRate.exchangeRatesDao()
    }

    @Provides
    @Singleton
    fun provideExchangeRateLocal(exchangeRateLocal: ExchangeRateLocalImpl): ExchangeRateLocal {
        return exchangeRateLocal
    }
}