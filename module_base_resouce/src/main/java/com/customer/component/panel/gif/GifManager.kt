package com.customer.component.panel.gif

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.util.ArrayMap
import androidx.core.content.ContextCompat
import com.customer.component.panel.GifEmoticonHelper
import com.customer.component.panel.emotion.CenterImageSpan
import com.customer.component.panel.emotion.Emotions
import com.customer.component.recyclegif.gif.ProxyDrawable
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.utils.ViewUtils.dp2px
import java.util.*
import java.util.regex.Pattern


/**
 *
 * @ Author  QinTian
 * @ Date  1/29/21
 * @ Describe
 *
 */
object GifManager {

    var EMOTION_CLASSIC_MAP2 = ArrayMap<String, Int>()
    init {
        EMOTION_CLASSIC_MAP2[":G1:"] = R.drawable.g1
        EMOTION_CLASSIC_MAP2[":G2:"] = R.drawable.g2
        EMOTION_CLASSIC_MAP2[":G3:"] = R.drawable.g3
        EMOTION_CLASSIC_MAP2[":G4:"] = R.drawable.g4
        EMOTION_CLASSIC_MAP2[":G5:"] = R.drawable.g5
        EMOTION_CLASSIC_MAP2[":G6:"] = R.drawable.g6
        EMOTION_CLASSIC_MAP2[":G7:"] = R.drawable.g7
        EMOTION_CLASSIC_MAP2[":G8:"] = R.drawable.g8
        EMOTION_CLASSIC_MAP2[":G9:"] = R.drawable.g9
        EMOTION_CLASSIC_MAP2[":G10:"] = R.drawable.g10
        EMOTION_CLASSIC_MAP2[":G11:"] = R.drawable.g11
        EMOTION_CLASSIC_MAP2[":G12:"] = R.drawable.g12
        EMOTION_CLASSIC_MAP2[":G13:"] = R.drawable.g13
        EMOTION_CLASSIC_MAP2[":G14:"] = R.drawable.g14
        EMOTION_CLASSIC_MAP2[":G15:"] = R.drawable.g15
        EMOTION_CLASSIC_MAP2[":G16:"] = R.drawable.g16
        EMOTION_CLASSIC_MAP2[":G17:"] = R.drawable.g17
        EMOTION_CLASSIC_MAP2[":G18:"] = R.drawable.g18
        EMOTION_CLASSIC_MAP2[":G19:"] = R.drawable.g19
        EMOTION_CLASSIC_MAP2[":G20:"] = R.drawable.g20
        EMOTION_CLASSIC_MAP2[":G21:"] = R.drawable.g21
        EMOTION_CLASSIC_MAP2[":G22:"] = R.drawable.g22
        EMOTION_CLASSIC_MAP2[":G23:"] = R.drawable.g23
        EMOTION_CLASSIC_MAP2[":G24:"] = R.drawable.g24
        EMOTION_CLASSIC_MAP2[":G25:"] = R.drawable.g25
        EMOTION_CLASSIC_MAP2[":G26:"] = R.drawable.g26
        EMOTION_CLASSIC_MAP2[":G27:"] = R.drawable.g27
        EMOTION_CLASSIC_MAP2[":G28:"] = R.drawable.g28
        EMOTION_CLASSIC_MAP2[":G29:"] = R.drawable.g29
        EMOTION_CLASSIC_MAP2[":G30:"] = R.drawable.g30
        EMOTION_CLASSIC_MAP2[":G31:"] = R.drawable.g31
        EMOTION_CLASSIC_MAP2[":G32:"] = R.drawable.g32
        EMOTION_CLASSIC_MAP2[":G33:"] = R.drawable.g33
        EMOTION_CLASSIC_MAP2[":G34:"] = R.drawable.g34
        EMOTION_CLASSIC_MAP2[":G35:"] = R.drawable.g35
        EMOTION_CLASSIC_MAP2[":G36:"] = R.drawable.g36
        EMOTION_CLASSIC_MAP2[":G37:"] = R.drawable.g37
        EMOTION_CLASSIC_MAP2[":G38:"] = R.drawable.g38


        EMOTION_CLASSIC_MAP2[":bowtie:"] = R.drawable.bowtie
        EMOTION_CLASSIC_MAP2[":smiley:"] = R.drawable.smiley
        EMOTION_CLASSIC_MAP2[":kissing_heart:"] = R.drawable.kissing_heart
        EMOTION_CLASSIC_MAP2[":satisfied:"] = R.drawable.satisfied
        EMOTION_CLASSIC_MAP2[":stuck_out_tongue_closed_eyes:"] = R.drawable.stuck_out_tongue_closed_eyes
        EMOTION_CLASSIC_MAP2[":stuck_out_tongue:"] = R.drawable.stuck_out_tongue

        EMOTION_CLASSIC_MAP2[":smile:"] = R.drawable.smile
        EMOTION_CLASSIC_MAP2[":relaxed:"] = R.drawable.relaxed
        EMOTION_CLASSIC_MAP2[":kissing_closed_eyes:"] = R.drawable.kissing_closed_eyes
        EMOTION_CLASSIC_MAP2[":grin:"] = R.drawable.grin
        EMOTION_CLASSIC_MAP2[":grinning:"] = R.drawable.grinning
        EMOTION_CLASSIC_MAP2[":sleeping:"] = R.drawable.sleeping

        EMOTION_CLASSIC_MAP2[":laughing:"] = R.drawable.laughing
        EMOTION_CLASSIC_MAP2[":smirk:"] = R.drawable.smirk
        EMOTION_CLASSIC_MAP2[":flushed:"] = R.drawable.flushed
        EMOTION_CLASSIC_MAP2[":wink:"] = R.drawable.wink
        EMOTION_CLASSIC_MAP2[":kissing:"] = R.drawable.kissing
        EMOTION_CLASSIC_MAP2[":worried:"] = R.drawable.worried

        EMOTION_CLASSIC_MAP2[":blush:"] = R.drawable.blush
        EMOTION_CLASSIC_MAP2[":heart_eyes:"] = R.drawable.heart_eyes
        EMOTION_CLASSIC_MAP2[":relieved:"] = R.drawable.relieved
        EMOTION_CLASSIC_MAP2[":stuck_out_tongue_winking_eye:"] = R.drawable.stuck_out_tongue_winking_eye
        EMOTION_CLASSIC_MAP2[":kissing_smiling_eyes:"] = R.drawable.kissing_smiling_eyes
        EMOTION_CLASSIC_MAP2[":frowning:"] = R.drawable.frowning


        EMOTION_CLASSIC_MAP2[":H1:"] = R.drawable.h1
        EMOTION_CLASSIC_MAP2[":H2:"] = R.drawable.h2
        EMOTION_CLASSIC_MAP2[":H3:"] = R.drawable.h3
        EMOTION_CLASSIC_MAP2[":H4:"] = R.drawable.h4
        EMOTION_CLASSIC_MAP2[":H5:"] = R.drawable.h5
        EMOTION_CLASSIC_MAP2[":H6:"] = R.drawable.h6
        EMOTION_CLASSIC_MAP2[":H7:"] = R.drawable.h7
        EMOTION_CLASSIC_MAP2[":H8:"] = R.drawable.h8
        EMOTION_CLASSIC_MAP2[":H9:"] = R.drawable.h9
        EMOTION_CLASSIC_MAP2[":H10:"] = R.drawable.h10
        EMOTION_CLASSIC_MAP2[":H11:"] = R.drawable.h11
        EMOTION_CLASSIC_MAP2[":H12:"] = R.drawable.h12
        EMOTION_CLASSIC_MAP2[":H13:"] = R.drawable.h13
        EMOTION_CLASSIC_MAP2[":H14:"] = R.drawable.h14
        EMOTION_CLASSIC_MAP2[":H15:"] = R.drawable.h15
        EMOTION_CLASSIC_MAP2[":H16:"] = R.drawable.h16
        EMOTION_CLASSIC_MAP2[":H17:"] = R.drawable.h17
        EMOTION_CLASSIC_MAP2[":H18:"] = R.drawable.h18
        EMOTION_CLASSIC_MAP2[":H19:"] = R.drawable.h19
        EMOTION_CLASSIC_MAP2[":H20:"] = R.drawable.h20
    }
    private val sPatternEmotion = Pattern.compile("\\:(.*?)\\:")

   @SuppressLint("Range")
   fun textWithGif(str:String, context: Context):SpannableString? {
       //以下为将表情转换为gif的代码
       val matcherEmotion = sPatternEmotion.matcher(str)
       val spannableString = SpannableString(str)
       while (matcherEmotion.find()){
           val gifName = matcherEmotion.group()
           val gifRes = EMOTION_CLASSIC_MAP2[matcherEmotion.group()]
           try {
               if (isGif(gifName)) {
                   if (isBigGif(gifName)) {
                       val drawable: Drawable? = gifRes?.let { ViewUtils.getDrawable(it) }
                       drawable?.setBounds(8, 8, dp2px(30), dp2px(30))
                       val span = drawable?.let { ImageSpan(it) }
                       spannableString.setSpan(span, matcherEmotion.start(), matcherEmotion.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                       val gifDrawable = GifEmoticonHelper.getInstance().getGifDrawable(context, 40,gifRes)
//                       val imageSpan = ImageSpan(gifDrawable)
//                       spannableString.setSpan(imageSpan, matcherEmotion.start(), matcherEmotion.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                   }else{
                       val drawable: Drawable? = gifRes?.let { ViewUtils.getDrawable(it) }
                       drawable?.setBounds(8, 8, dp2px(22), dp2px(22))
                       val span = drawable?.let { ImageSpan(it) }
                       spannableString.setSpan(span, matcherEmotion.start(), matcherEmotion.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//                       val gifDrawable = GifEmoticonHelper.getInstance().getGifDrawable(context, 22,gifRes)
//                       val imageSpan = ImageSpan(gifDrawable)
//                       spannableString.setSpan(imageSpan, matcherEmotion.start(), matcherEmotion.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                   }
               }else{
                   val drawable: Drawable? = gifRes?.let { ViewUtils.getDrawable(it) }
                   drawable?.setBounds(8, 8, dp2px(20), dp2px(20))
                   val span = drawable?.let { ImageSpan(it) }
                   spannableString.setSpan(span, matcherEmotion.start(), matcherEmotion.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

               }
           }catch (e:Exception){ }
       }
       return spannableString
    }
    fun textWithGifKeyBord(str:String, context: Context):SpannableString? {
        val matcherEmotion = sPatternEmotion.matcher(str)
        val spannableString = SpannableString(str)
        while (matcherEmotion.find()){
            val key = matcherEmotion.group()
            val imgRes = EMOTION_CLASSIC_MAP2[key]
            try {
                val drawable = imgRes?.let { ContextCompat.getDrawable(context, it) }
                if (isBigGif(key)){
                    drawable?.setBounds(0, 0, dp2px(24), dp2px(44))
                }else   drawable?.setBounds(0, 0, dp2px(20), dp2px(20))

                val span = drawable?.let { CenterImageSpan(it) }
                spannableString.setSpan(span, matcherEmotion.start(),  matcherEmotion.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }catch (e:Exception){ }
        }
        return spannableString
    }

    private fun isGif(key: String): Boolean {
        return Emotions.EMOTIONS.containsKey(key) || Emotions.EMOTIONS_TWO.containsKey(key)
    }

     fun isBigGif(key: String): Boolean {
        return Emotions.EMOTIONS_TWO.containsKey(key)
    }

     fun initGifDrawable(context: Context) {
//        for ((key,value) in EMOTION_CLASSIC_MAP2){
//            if (!drawableCacheMap.containsKey(key)) {
//                if (isGif(key)) {
//                    val gifDrawable:ProxyDrawable? = value?.let { ProxyDrawable(GifDrawable(context.resources, it)) }
//                    drawableCacheMap[key] = gifDrawable
//                }
//            }
//        }
    }

}