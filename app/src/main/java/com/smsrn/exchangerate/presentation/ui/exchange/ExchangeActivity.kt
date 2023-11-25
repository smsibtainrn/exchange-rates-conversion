package com.smsrn.exchangerate.presentation.ui.exchange

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.smsrn.exchangerate.R
import com.smsrn.exchangerate.core.BaseActivity
import com.smsrn.exchangerate.core.BaseViewModel
import com.smsrn.exchangerate.databinding.ActivityExchangeRateBinding
import com.smsrn.exchangerate.network.Response
import com.smsrn.exchangerate.presentation.ui.conversions.ConversionsActivity
import com.smsrn.exchangerate.utils.Constants.IntentExtras.AMOUNT
import com.smsrn.exchangerate.utils.Constants.IntentExtras.CURRENCY
import com.smsrn.exchangerate.utils.custom.DialPadNumber
import com.smsrn.exchangerate.utils.extensions.hideLoader
import com.smsrn.exchangerate.utils.extensions.showLoader
import com.smsrn.exchangerate.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeActivity : BaseActivity<ActivityExchangeRateBinding>(), View.OnClickListener {

    override fun getLayout(): Int = R.layout.activity_exchange_rate
    private val viewModel: ExchangeViewModel by viewModels()
    private var amount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        viewModel.exchangeRates.observe(this@ExchangeActivity) {
            when (it) {
                is Response.Loading -> {
                    showLoader()
                }
                is Response.Success -> {
                    viewModel.currencies.value = it.data.rates.keys.toList()
                    hideLoader()
                }
                is Response.Error -> {
                    showToast(it.message)
                    hideLoader()
                }
            }
        }

        setListeners()
    }

    private fun setListeners() {
        binding.btnViewConversions.setOnClickListener {
            startActivity(ConversionsActivity::class, intentModifier = {
                putExtra(CURRENCY, viewModel.selectedCurrency.value)
                putExtra(AMOUNT, amount)
            })
        }

        listOf(
            binding.dialpad.dpnOne,
            binding.dialpad.dpnTwo,
            binding.dialpad.dpnThree,
            binding.dialpad.dpnFour,
            binding.dialpad.dpnFive,
            binding.dialpad.dpnSix,
            binding.dialpad.dpnSeven,
            binding.dialpad.dpnEight,
            binding.dialpad.dpnNine,
            binding.dialpad.dpnZero,
            binding.dialpad.dpnDot,
        ).forEach { it.setOnClickListener(this) }

        binding.dialpad.imgViewErase.setOnClickListener {
            if (amount.isNotBlank()) {
                amount = amount.substring(0, amount.length - 1)
                binding.editTextAmount.setText(amount)
            } else {
                binding.editTextAmount.text?.clear()
            }
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            is DialPadNumber -> {
                if (amount.isEmpty() && view.digit == ".") return
                if (amount == "0" && view.digit == "0") return
                if (view.digit == "." && amount.contains(".")) return

                amount += view.digit
                binding.editTextAmount.setText(amount)
            }
        }
    }
}