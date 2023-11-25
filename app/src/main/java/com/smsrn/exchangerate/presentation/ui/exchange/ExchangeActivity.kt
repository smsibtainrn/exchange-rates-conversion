package com.smsrn.exchangerate.presentation.ui.exchange

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.smsrn.exchangerate.R
import com.smsrn.exchangerate.core.BaseActivity
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
    private val regex = Regex("^(0(\\.\\d{1,2})?|[1-9]\\d{0,5}(\\.\\d{1,2})?)$")

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

        viewModel.amount.observe(this@ExchangeActivity) {
            binding.btnViewConversions.isEnabled =
                it.matches(regex) && !viewModel.selectedCurrency.value.isNullOrEmpty()
        }

        setListeners()
    }

    private fun setListeners() {
        binding.btnViewConversions.setOnClickListener {
            if (viewModel.amount.value.isNullOrEmpty()) {
                showToast(R.string.amount_empty)
            } else if (viewModel.amount.value?.matches(regex) != true) {
                showToast(R.string.amount_invalid_message)
            } else {
                startActivity(ConversionsActivity::class, intentModifier = {
                    putExtra(CURRENCY, viewModel.selectedCurrency.value)
                    putExtra(AMOUNT, viewModel.amount.value?.toDouble())
                })
            }
        }

        listOf(
            binding.dialpad.dpnOne, binding.dialpad.dpnTwo, binding.dialpad.dpnThree,
            binding.dialpad.dpnFour, binding.dialpad.dpnFive, binding.dialpad.dpnSix,
            binding.dialpad.dpnSeven, binding.dialpad.dpnEight, binding.dialpad.dpnNine,
            binding.dialpad.dpnZero, binding.dialpad.dpnDot
        ).forEach { it.setOnClickListener(this) }

        binding.dialpad.imgViewErase.setOnClickListener {
            var text = viewModel.amount.value?.toString()
            if (text?.isNotBlank() == true) {
                text = text.substring(0, text.length - 1)
                viewModel.amount.value = text
            }
        }
    }


    override fun onClick(view: View?) {
        when (view) {
            is DialPadNumber -> {
                val oldText = viewModel.amount.value ?: ""
                val digit = view.digit
                val newText = "$oldText$digit"
                if (!oldText.contains(".") && digit == "." && regex.matches(oldText)) {
                    viewModel.amount.value = newText
                } else if (regex.matches(newText)) {
                    viewModel.amount.value = newText
                }
            }
        }
    }
}