package com.smsrn.exchangerate.di

import com.smsrn.exchangerate.data.source.remote.service.ExchangeRateService
import com.smsrn.exchangerate.data.repository.ExchangeRateRepositoryImpl
import com.smsrn.exchangerate.data.source.ExchangeRateDataSourceFactory
import com.smsrn.exchangerate.domain.repository.ExchangeRateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Sibtain Raza on 5/3/2023.
 * sraza@adnocdistribution.ae
 */

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideExchangeRateRepository(
        exchangeRateDataSourceFactory: ExchangeRateDataSourceFactory,
    ): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(exchangeRateDataSourceFactory)
    }
}