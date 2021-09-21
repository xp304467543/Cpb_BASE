package com.customer.component.recyclegif.gif

import android.content.Context
import android.graphics.drawable.Drawable

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
interface Emoji {

    val emojiText: CharSequence

    val res: Any

    val isDeleteIcon: Boolean

    val defaultResId: String

    val cacheKey: Any

    fun getDrawable(context: Context): Drawable

}