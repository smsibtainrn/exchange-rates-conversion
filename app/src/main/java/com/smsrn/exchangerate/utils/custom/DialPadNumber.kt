package com.smsrn.exchangerate.utils.custom

import android.content.Context
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.smsrn.exchangerate.R

class DialPadNumber(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {
    lateinit var digit: String
    var characters: String?

    init {
//        View.inflate(context, R.layout.layout_dialpad,this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DialPadNumber, 0, 0)
        //digit = typedArray.getString(R.styleable.DialPadNumber_dpn_digit)
        typedArray.getString(R.styleable.DialPadNumber_dpn_digit)?.let {
            digit = it
        }
        characters = typedArray.getString(R.styleable.DialPadNumber_dpn_characters)
        val builder = SimpleSpanBuilder()

        // if a subtitle exits make it's size 40% of the original text
        if (!characters.isNullOrBlank()) {
            builder.appendWithLineBreak(digit)
            builder.append(characters!!, RelativeSizeSpan(0.4f))
        } else {
            builder.append(digit)
        }
        text = builder.build()

        typedArray.recycle()
    }

    fun sib(){}
}