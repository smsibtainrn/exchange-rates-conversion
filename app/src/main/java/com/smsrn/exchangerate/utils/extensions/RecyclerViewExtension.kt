package com.smsrn.exchangerate.utils.extensions

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.smsrn.exchangerate.presentation.adapters.Adapter
import com.smsrn.exchangerate.presentation.adapters.listeners.OnItemListener


fun <T> RecyclerView.withAdapter(
    @LayoutRes layout: Int,
    listeners: OnItemListener<T>? = null,
    list: List<T>? = null,
    selectedPosition: Int = -1
) {
    adapter = Adapter(layout, listeners).apply {
        this.selectedPosition = selectedPosition
        list?.let { items = list as ArrayList<T> }
    }
}