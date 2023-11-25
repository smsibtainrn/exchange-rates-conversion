package com.smsrn.exchangerate.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.smsrn.exchangerate.R

/**
 * Created by Sibtain Raza on 5/4/2023.
 * sraza@adnocdistribution.ae
 */
object LoaderDialog {
    private var dialog: Dialog? = null

    private fun createDialog(context: Context) {
        checkLoader()
        dialog = Dialog(context)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun showLoader(context: Context, isCancellable: Boolean = false) {
        try {
            createDialog(context)
            dialog?.setCancelable(isCancellable)
            dialog?.show()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkLoader() {
        try {
            if (dialog?.isShowing == true) hideLoader()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoader() {
        try {
            dialog?.dismiss()
            dialog?.cancel()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}