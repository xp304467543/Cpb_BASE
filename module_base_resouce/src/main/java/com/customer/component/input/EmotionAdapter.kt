package com.customer.component.input

import android.content.Context
import android.widget.RelativeLayout
import com.customer.component.panel.emotion.Emotion
import com.customer.component.panel.emotion.Emotions
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.ViewUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/1
 * @ Describe
 *
 */
class EmotionAdapter(val context: Context) : BaseRecyclerAdapter<Emotion>() {
    override fun getItemLayoutId(viewType: Int) = R.layout.vh_emotion_item_layout

    override fun bindData(holder: RecyclerViewHolder, position: Int, data: Emotion?) {
        val img = holder.getImageView(R.id.image)
//        val container  = holder.getView(R.id.container)
        val param = img.layoutParams as RelativeLayout.LayoutParams
//        val para2 = container.layoutParams
        if (isBigGif(data?.text.toString()) == true){
            param.height = ViewUtils.dp2px(65)
            param.width = ViewUtils.dp2px(65)
//            para2.width = ViewUtils.dp2px(55)
//            para2.height = ViewUtils.dp2px(55)
        }else{
            param.height = ViewUtils.dp2px(25)
            param.width = ViewUtils.dp2px(25)
//            para2.width = ViewUtils.dp2px(25)
//            para2.height = ViewUtils.dp2px(25)
        }
        img.layoutParams = param
//        container.layoutParams = para2
        data?.drawableRes?.let { img.setImageResource(it) }
    }



    private fun isBigGif(key: String): Boolean? {
        return Emotions.EMOTIONS_TWO.containsKey(key)
    }
}