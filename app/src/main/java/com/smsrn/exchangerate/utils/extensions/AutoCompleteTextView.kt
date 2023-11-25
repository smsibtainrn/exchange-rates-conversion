package com.smsrn.exchangerate.utils.extensions

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import com.smsrn.exchangerate.presentation.adapters.DropDownAdapter


/**
 * Created by Sibtain Raza on 11/23/2023.
 * sraza@adnocdistribution.ae
 */

fun AutoCompleteTextView.setSpinnerEntries(items: List<Any>?) {
    if (!items.isNullOrEmpty()) {
        val arrayAdapter = DropDownAdapter(context, items)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        doAfterTextChanged {
            arrayAdapter.filter.filter(it)
        }
        setAdapter(arrayAdapter)
    }
}

fun AutoCompleteTextView.setAutoCompleteSelectedListener(listener: ItemSelectedListener?) {
    onItemClickListener = listener?.let {
        AdapterView.OnItemClickListener { parent, view, position, p3 ->
            if (tag != position) {
                listener.onItemSelected(parent?.getItemAtPosition(position))
            }


        }
    }
}

fun AutoCompleteTextView.setAutoCompleteValue(value: Any?, paddingZero: Boolean = false) {
    if (adapter != null && value != null) {
        val position = (adapter as ArrayAdapter<Any>).getPosition(value)
        setText(value.toString(), false)
        tag = position
        if (paddingZero) setPaddingHorizontal(0)
    }
}

interface ItemSelectedListener {
    fun onItemSelected(item: Any?)
}