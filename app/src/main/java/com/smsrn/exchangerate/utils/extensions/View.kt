package com.smsrn.exchangerate.utils.extensions

import android.view.View


/**
 * Created by Sibtain Raza on 11/23/2023.
 * sraza@adnocdistribution.ae
 */

fun View.setPaddingHorizontal(padding: Int) {
    setPadding(padding, paddingTop, padding, paddingBottom)
}

fun View.setPaddingVertical(padding: Int) {
    setPadding(paddingLeft, padding, paddingRight, padding)
}