package com.smsrn.exchangerate.presentation.ui.conversions

import android.os.Bundle
import androidx.activity.viewModels
import com.smsrn.exchangerate.R
import com.smsrn.exchangerate.core.BaseActivity
import com.smsrn.exchangerate.core.BaseViewModel
import com.smsrn.exchangerate.databinding.ActivityConversionsBinding
import com.smsrn.exchangerate.utils.Constants.IntentExtras.AMOUNT
import com.smsrn.exchangerate.utils.Constants.IntentExtras.CURRENCY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConversionsActivity : BaseActivity<ActivityConversionsBinding>() {
    override fun getLayout() = R.layout.activity_conversions
    private val viewModel: ConversionsViewModel by viewModels()
    private val currency by lazy { intent.getStringExtra(CURRENCY) }
    private val amount by lazy { intent.getStringExtra(AMOUNT) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.tvCurrency.text = currency
        binding.tvCurrencyAmount.text = amount
    }
}