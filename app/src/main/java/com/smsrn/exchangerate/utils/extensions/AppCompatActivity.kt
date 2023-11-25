package com.smsrn.exchangerate.utils.extensions

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.smsrn.exchangerate.BuildConfig
import com.smsrn.exchangerate.presentation.dialogs.LoaderDialog
import com.smsrn.exchangerate.utils.AppToast


/**
 * Created by Sibtain Raza on 11/22/2023.
 * smsibtainrn@gmail.com
 */
fun AppCompatActivity.showToast(toastMessage: String) {
    AppToast.showToast(toastMessage)
}

fun AppCompatActivity.showToast(@StringRes resId: Int) {
    AppToast.showToast(resId)
}

fun AppCompatActivity.showDebugToast(toastMessage: String) {
    if (BuildConfig.DEBUG) AppToast.showDebugToast(toastMessage)
}

fun AppCompatActivity.showLongToast(toastMessage: String) {
    AppToast.showLongToast(toastMessage)
}

fun AppCompatActivity.showLongToast(@StringRes resId: Int) {
    AppToast.showLongToast(resId)
}

fun AppCompatActivity.showLoader(isCancellable: Boolean = false) {
    if (!isFinishing) {
        LoaderDialog.showLoader(this, isCancellable)
    }
}

fun AppCompatActivity.hideLoader() {
    LoaderDialog.hideLoader()
}
