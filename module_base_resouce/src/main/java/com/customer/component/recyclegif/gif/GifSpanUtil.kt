package com.customer.component.recyclegif.gif

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Selection
import android.text.Spannable
import android.text.SpannableString

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */

fun createResizeGifDrawableSpan(gifDrawable: Drawable, text: CharSequence): Spannable {
    val spannable = SpannableString(text)
    val imageSpan = ResizeIsoheightImageSpan(gifDrawable)
    spannable.setSpan(imageSpan,
        0, text.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

fun createGifDrawableSpan(gifDrawable: Drawable, text: CharSequence): Spannable {
    val imageSpan = GifIsoheightImageSpan(gifDrawable)
    val spannable = SpannableString(text)
    spannable.setSpan(imageSpan,
        0, spannable.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

fun insertSpannableString(editable: Editable, text: CharSequence) {
    var start = Selection.getSelectionStart(editable)
    var end = Selection.getSelectionEnd(editable)
    if (end < start) {
        val temp = start
        start = end
        end = temp
    }
    editable.replace(start, end, text)
}

fun Editable.insertSpan(text: CharSequence) {
    var start = getSpanStart(Selection.SELECTION_START)
    var end = getSpanEnd(Selection.SELECTION_START)
    if (end < start) {
        val temp = start
        start = end
        end = temp
    }
    replace(start, end, text)
}