package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.SpannableString
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_video_tips_confirm.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/23
 * @ Describe
 *
 */
class DialogMovieTips (context: Context, title:String,content: SpannableString, cancel: String, confirm: String, contentDes: String) : Dialog(context) {
    init {
        setContentView(R.layout.dialog_video_tips_confirm)
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window?.attributes
        lp?.width = ViewUtils.dp2px(320) // 宽度
        lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT  // 高度
//      lp.alpha = 0.7f // 透明度
        window?.attributes = lp
        initText(title,content, contentDes,confirm, cancel)
    }

    private fun initText(title:String,content: SpannableString, contentDes: String, confirm: String, cancel: String) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.visibility = View.VISIBLE
            tvContent.text = content
        }
        if (!TextUtils.isEmpty(contentDes)) {
            tvContentDescription.visibility = View.VISIBLE
            tvContentDescription.text = contentDes
        }
        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.visibility = View.VISIBLE
            tvConfirm.text = confirm
        }
        if (!TextUtils.isEmpty(cancel)) {
            tvCancel.visibility = View.VISIBLE
            tvCancel.text = cancel
        }
        if (tvConfirm !== null) {
            tvConfirm.setOnClickListener {
                dismiss()
                mListener?.invoke()
            }
        }
        if (tvCancel !== null) {
            tvCancel?.setOnClickListener {
                dismiss()
                mListenerCancel?.invoke()
            }
        }
        if (imgClose!=null){
            imgClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private var mListener: (() -> Unit)? = null
    fun setConfirmClickListener(listener: () -> Unit) {
        mListener = listener
    }

    private var mListenerCancel: (() -> Unit)? = null
    fun setCanCalClickListener(listener: () -> Unit) {
        mListenerCancel = listener
    }


    fun setEnable(enable: Boolean) {
        tvConfirm.isEnabled = enable
    }

}