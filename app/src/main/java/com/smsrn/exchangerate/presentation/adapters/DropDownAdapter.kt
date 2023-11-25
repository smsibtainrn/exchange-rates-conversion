package com.smsrn.exchangerate.presentation.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


/**
 * Created by Sibtain Raza on 10/14/2021.
 * sibtain.raza@10pearls.com
 */
class DropDownAdapter<T>(
    mContext: Context,
    private var items: List<T> = ArrayList(),
    layout: Int = android.R.layout.simple_spinner_dropdown_item
) : ArrayAdapter<T>(mContext, layout, items) {

    var itemsFiltered: ArrayList<T> = items as ArrayList<T>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = itemsFiltered[position].toString()
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.text = itemsFiltered[position].toString()
        return label
    }

    override fun getCount(): Int {
        return itemsFiltered.size
    }

    override fun getItem(position: Int): T {
        return itemsFiltered[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}