package com.customer.component.recyclegif.gif

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.fh.module_base_resouce.R
import pl.droidsonroids.gif.GifDrawable
import java.io.File
import java.io.IOException

/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
class DefaultGifEmoji(private val emojiconString: String, override val emojiText: String) :
    Emoji {


    override val res: Any
        get() = emojiconString


    override val isDeleteIcon: Boolean
        get() = false

    override val defaultResId: String
        get() = "smiley.gif"
//            R.drawable.smiley

    override val cacheKey: Any
        get() = emojiconString
//            if (emojiconFile.exists()) {
//            emojiconFile.absolutePath
//        } else {
//            R.drawable.smiley
//        }


    override fun getDrawable(context: Context): Drawable {
        try {
            return ProxyDrawable(GifDrawable(emojiconString))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return BitmapDrawable(context.resources,
            BitmapFactory.decodeStream(context.assets.open(defaultResId)))

//        ContextCompat.getDrawable(context, defaultResId)!!
    }


}
