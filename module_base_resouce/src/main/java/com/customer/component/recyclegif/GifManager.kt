//package com.customer.component.recyclegif
//
//import android.content.Context
//import android.graphics.drawable.Drawable
//import android.text.Spannable
//import android.text.SpannableString
//import android.text.style.ImageSpan
//import com.customer.component.panel.emotion.Emotion
//import com.customer.component.panel.emotion.Emotions
//import com.customer.component.recyclegif.gif.DefaultGifEmoji
//import com.customer.component.recyclegif.gif.Emoji
//import com.customer.component.recyclegif.gif.GifIsoheightImageSpan
//import java.util.*
//import java.util.regex.Pattern
//
///**
// *
// * @ Author  QinTian
// * @ Date  1/29/21
// * @ Describe
// *
// */
//object GifManager {
//
//    private val emoticons = HashMap<Pattern, Emoji>()
//    private var emojiGifs = arrayListOf<String>()
//    private val drawableCacheMap = HashMap<Any, Drawable>()
//    private var defaultGifEmojis = arrayListOf<DefaultGifEmoji>()
//
//
//    private fun initDefault(context: Context) {
//        for ((key, value) in Emotions.EMOTIONS) {
//            defaultGifEmojis.add(DefaultGifEmoji(key, value))
//        }
//        for ((key, value) in Emotions.EMOTIONS_ONE) {
//            defaultGifEmojis.add(DefaultGifEmoji(key, value))
//        }
//        for ((key, value) in Emotions.EMOTIONS_TWO) {
//            defaultGifEmojis.add(DefaultGifEmoji(key, value))
//        }
//    }
//
//
//    fun getDrawableByEmoji(context: Context, emoji: Emotion): Drawable {
//        val drawable: Drawable
////        if (drawableCacheMap.containsKey(emoji.text)) {
//            drawable = drawableCacheMap[emoji.text]!!
////        }
////        else {
////            drawable = emoji.getDrawable(context)
////            drawableCacheMap[emoji.text] = drawable
////        }
//        return drawable
//    }
//
//    fun getSpannableByPattern(context: Context, text: String): Spannable {
//        val spannableString = SpannableString(text)
//        for ((key, emoji) in emoticons) {
//            val matcher = key.matcher(text)
//            while (matcher.find()) {
//                var imageSpan: ImageSpan? = null
//
//                if (emoji is Emotion) {
//                    imageSpan = getImageSpanByEmoji(context, emoji)
//                }
//                if (imageSpan != null) {
//                    spannableString.setSpan(imageSpan,
//                        matcher.start(), matcher.end(),
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                }
//            }
//        }
//        return spannableString
//    }
//
//
//    private fun getImageSpanByEmoji(context: Context, emoji: Emotion): ImageSpan {
//        val imageSpan: ImageSpan
//        val gifDrawable: Drawable
////        if (drawableCacheMap.containsKey(emoji.text)) {
//            gifDrawable = drawableCacheMap[emoji.text]!!
//            imageSpan = GifIsoheightImageSpan(gifDrawable)
////        }
////        else {
////            gifDrawable = emoji.getDrawable(context)
////            imageSpan = GifIsoheightImageSpan(gifDrawable)
////            drawableCacheMap[emoji.text] = gifDrawable
////        }
//        return imageSpan
//    }
//
//}