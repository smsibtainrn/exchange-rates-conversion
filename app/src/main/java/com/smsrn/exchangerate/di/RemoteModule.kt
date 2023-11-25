package com.smsrn.exchangerate.di

import com.smsrn.exchangerate.data.repository.ExchangeRateRemote
import com.smsrn.exchangerate.data.source.remote.ExchangeRateRemoteImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Sibtain Raza on 11/26/2023.
 * smsibtainrn@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    @Singleton
    fun provideExchangeRateRemote(exchangeRateRemote: ExchangeRateRemoteImpl): ExchangeRateRemote {
        return exchangeRateRemote
    }
}