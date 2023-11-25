package com.smsrn.exchangerate.utils.custom

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.CharacterStyle
import android.text.style.StyleSpan

/**
 * Created by Juad on 3/7/2018.
 */

class SimpleSpanBuilder {

    private val spanSections: MutableList<SpanSection>
    private val stringBuilder: StringBuilder = StringBuilder()

    private inner class SpanSection constructor(private val text: String, private val startIndex: Int, vararg styles: CharacterStyle) {
        private val styles: Array<out CharacterStyle> = styles

        fun apply(spanStringBuilder: SpannableStringBuilder?) {
            if (spanStringBuilder == null) return
            for (style in styles) {
                spanStringBuilder.setSpan(style, startIndex, startIndex + text.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    }

    init {
        spanSections = ArrayList()
    }

    fun append(text: String, vararg styles: CharacterStyle): SimpleSpanBuilder {
        if (styles != null && styles.size > 0) {
            spanSections.add(SpanSection(text, stringBuilder.length, *styles))
        }
        stringBuilder.append(text)
        return this
    }

    fun bold(text: String) = append(text, StyleSpan(Typeface.BOLD))

    fun appendWithSpace(text: String, vararg styles: CharacterStyle): SimpleSpanBuilder {
        return append("$text ", *styles)
    }

    fun appendWithLineBreak(text: String, vararg styles: CharacterStyle): SimpleSpanBuilder {
        return append(text + "\n", *styles)
    }

    fun lineBreak(): SimpleSpanBuilder {
        return append("\n")
    }

    fun space(): SimpleSpanBuilder {
        return append(" ")
    }

    fun build(): SpannableStringBuilder {
        val ssb = SpannableStringBuilder(stringBuilder.toString())
        for (section in spanSections) {
            section.apply(ssb)
        }
        return ssb
    }

    override fun toString(): String {
        return stringBuilder.toString()
    }
}
