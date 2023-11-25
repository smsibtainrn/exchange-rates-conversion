package com.smsrn.exchangerate.utils.bindings

import android.widget.AutoCompleteTextView
import androidx.databinding.BindingAdapter
import com.smsrn.exchangerate.utils.Utils
import com.smsrn.exchangerate.utils.extensions.ItemSelectedListener
import com.smsrn.exchangerate.utils.extensions.setAutoCompleteSelectedListener
import com.smsrn.exchangerate.utils.extensions.setAutoCompleteValue
import com.smsrn.exchangerate.utils.extensions.setSpinnerEntries

object AutoCompleteBindings {
    @BindingAdapter("app:items")
    @JvmStatic
    fun AutoCompleteTextView.setItems(items: List<Any>?) {
        setSpinnerEntries(items)
    }

    @BindingAdapter("app:onItemSelected")
    @JvmStatic
    fun AutoCompleteTextView.setOnItemSelectedListener(itemSelectedListener: ItemSelectedListener?) {
        setAutoCompleteSelectedListener(itemSelectedListener)
    }

    @BindingAdapter(value = ["app:previousValue", "app:paddingZero"], requireAll = false)
    @JvmStatic
    fun AutoCompleteTextView.setNewValue(newValue: Any?, paddingZero: Boolean?) {
        setAutoCompleteValue(newValue, paddingZero ?: false)
    }

    @BindingAdapter("app:hideKeyboard")
    @JvmStatic
    fun AutoCompleteTextView.hideKeyboard(value: Boolean) {
        if (value) setOnClickListener { Utils.hideKeyboard(this) }
    }
}