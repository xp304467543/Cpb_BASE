package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_success.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/23
 * @ Describe
 *
 */
class DialogSuccess (context: Context, content: String, img: Int,info:String = "") : Dialog(context) {
    init {
        setContentView(R.layout.dialog_success)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window?.attributes
        lp?.width = ViewGroup.LayoutParams.WRAP_CONTENT // 宽度
        lp?.height = ViewUtils.dp2px(80)  // 高度
//      lp.alpha = 0.7f // 透明度
        window?.attributes = lp
        setCanceledOnTouchOutside(true)
        initText(content, img,info)
    }

    private fun initText(content: String, img: Int,info:String = "") {
        tvDialogText.text = content
        ivDialogImg.setImageResource(img)
        tvInfo.text = info
    }
}