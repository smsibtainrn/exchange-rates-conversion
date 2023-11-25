package com.smsrn.exchangerate.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.smsrn.exchangerate.ExchangeRateApp
import com.smsrn.exchangerate.utils.Constants.DateTimeFormats.YYYY_MM_DD
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Sibtain Raza on 11/23/2023.
 * smsibtainrn@gmail.com
 */
object Utils {

    fun getDate(dayDistance: Int = 0, pattern: String = YYYY_MM_DD): String {
        val calendarOneDayBefore = Calendar.getInstance().apply {
            time = Calendar.getInstance().time
            add(Calendar.DAY_OF_MONTH, dayDistance)
        }
        val dateOneDayBefore = calendarOneDayBefore.time
        val dateFormat = SimpleDateFormat(pattern, Locale.US)
        return dateFormat.format(dateOneDayBefore)
    }

    fun getConversionRate(
        amount: Double, currentCurrencyRateUSD: Double, requiredCurrencyRateUSD: Double
    ): Double {
        val amountInUSD = amount / currentCurrencyRateUSD // Convert AED to USD
        return amountInUSD * requiredCurrencyRateUSD // Convert USD to PKR
    }

    fun hideKeyboard(view: View?) {
        view?.let {
            val imm =
                ExchangeRateApp.appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}