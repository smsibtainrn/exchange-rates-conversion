package com.smsrn.exchangerate.core

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KClass


/**
 * Created by Sibtain Raza on 5/3/2023.
 * smsibtainrn@gmail.com
 */

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected val TAG : String = this.javaClass.simpleName

    protected lateinit var binding: B

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = DataBindingUtil.setContentView(this, getLayout())
        binding.lifecycleOwner = this
    }

    fun startActivity(
        clazz: KClass<out BaseActivity<*>>,
        uri: Uri? = null,
        requestCode: Int? = null,
        finishCurrent: Boolean = false,
        intentModifier: (Intent.() -> Unit)? = null,
        extrasModifier: (Bundle.() -> Unit)? = null
    ) {
        val intent = Intent(this, clazz.java)
        intent.data = uri
        startActivityImpl(extrasModifier, intentModifier, intent, requestCode, finishCurrent)
    }

    fun startActivity(
        action: String,
        uri: Uri? = null,
        requestCode: Int? = null,
        finishCurrent: Boolean = false,
        intentModifier: (Intent.() -> Unit)? = null,
        extrasModifier: (Bundle.() -> Unit)? = null
    ) {
        val intent = Intent(action, uri)
        startActivityImpl(extrasModifier, intentModifier, intent, requestCode, finishCurrent)
    }

    private fun startActivityImpl(
        extrasModifier: (Bundle.() -> Unit)?,
        intentModifier: (Intent.() -> Unit)? = null,
        intent: Intent,
        requestCode: Int?,
        finishCurrent: Boolean
    ) {
        extrasModifier?.let {
            val bundle = Bundle()
            bundle.it()
            intent.putExtras(bundle)
        }

        intentModifier?.let { intent.it() }

        if (requestCode != null)
            startActivityForResult(intent, requestCode)
        else
            startActivity(intent)

        if (finishCurrent)
            finish()
    }
}