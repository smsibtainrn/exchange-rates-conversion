package com.smsrn.exchangerate.di

import com.smsrn.exchangerate.utils.CoroutineContextProvider
import com.smsrn.exchangerate.utils.CoroutineContextProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Sibtain Raza on 11/25/2023.
 * sraza@adnocdistribution.ae
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCoroutineContextProvider(contextProvider: CoroutineContextProviderImpl): CoroutineContextProvider =
        contextProvider
}