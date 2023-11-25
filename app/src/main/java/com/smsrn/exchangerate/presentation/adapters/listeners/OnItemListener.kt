package com.smsrn.exchangerate.presentation.adapters.listeners

import android.view.View


/**
 * Created by Sibtain Raza on 5/3/2023.
 * smsibtainrn@gmail.com
 */
interface OnItemListener<T> {
    fun onItemClick(view: View, position: Int, item: T, previousPosition:Int) {}
    fun onItemLongClick(view: View, position: Int, item: T, previousPosition:Int) {}
}