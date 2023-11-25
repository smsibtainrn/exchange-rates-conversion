package com.smsrn.exchangerate.utils.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

object TextViewBindings {
    @BindingAdapter("app:formatDouble")
    @JvmStatic
    fun formatDouble(view: TextView, number: Double) {
        try {
            val format = DecimalFormat("#,###.###")
            view.text = format.format(number)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}