package com.customer.component.recyclegif.gif

import android.text.NoCopySpan
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
class SpXSpannableFactory(private val mNoCopySpans: List<NoCopySpan>) : Spannable.Factory() {

    override fun newSpannable(source: CharSequence): Spannable {
        val spannableStringBuilder = SpannableStringBuilder()
        mNoCopySpans.forEach {
            spannableStringBuilder.setSpan(it, 0, 0,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE or Spanned.SPAN_PRIORITY)
        }
        spannableStringBuilder.append(source)
        return spannableStringBuilder
    }
}
