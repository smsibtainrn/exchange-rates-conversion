package com.smsrn.exchangerate.utils

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.ExchangeRateApp

object AppToast {
    private var mToast: Toast? = null

    fun showToast(toastMessage: String) {
        createToast(toastMessage)
    }

    fun showToast(@StringRes resId: Int) {
        createToast(ExchangeRateApp.appContext.getString(resId))
    }

    fun showDebugToast(toastMessage: String) {
        if (BuildConfig.DEBUG) createToast(toastMessage)
    }

    fun showLongToast(toastMessage: String) {
        createToast(toastMessage, Toast.LENGTH_LONG)
    }

    fun showLongToast(@StringRes resId: Int) {
        createToast(ExchangeRateApp.appContext.getString(resId), Toast.LENGTH_LONG)
    }

    private fun createToast(string: String?, toastDuration: Int = Toast.LENGTH_SHORT) {
        Handler(Looper.getMainLooper()).post {
            try {
                mToast?.cancel()
                mToast = Toast.makeText(ExchangeRateApp.appContext, string, toastDuration)
                mToast?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}