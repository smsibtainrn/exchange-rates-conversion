package com.smsrn.exchangerate

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */

@HiltAndroidApp
class ExchangeRateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {
        private lateinit var application: ExchangeRateApp

        val appContext: Context
            get() = application.applicationContext
    }
}