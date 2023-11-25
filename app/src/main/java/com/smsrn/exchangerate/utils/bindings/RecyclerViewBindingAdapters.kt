package com.smsrn.exchangerate.utils.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smsrn.exchangerate.presentation.adapters.listeners.OnItemListener
import com.smsrn.exchangerate.utils.extensions.withAdapter


object RecyclerViewBindingAdapters {
    @BindingAdapter(
        value = ["layoutId", "listeners", "items", "hasFixedSize", "defaultPosition"],
        requireAll = false
    )
    @JvmStatic
    fun <T> setAdapter(
        view: RecyclerView,
        layoutId: Int?,
        listeners: OnItemListener<T>?,
        items: List<T>?,
        hasFixedSize: Boolean = false,
        defaultPosition: Int?
    ) {
        if (layoutId == null) return
        view.withAdapter(layoutId, listeners, items, defaultPosition ?: -1)
        view.setHasFixedSize(hasFixedSize)
    }
}