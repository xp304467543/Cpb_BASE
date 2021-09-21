package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.fh.module_base_resouce.R
import com.glide.GlideUtil
import kotlinx.android.synthetic.main.dialog_red_paper.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/27
 * @ Describe
 *
 */
class DialogRedPaper (context: Context) : Dialog(context) {

    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_red_paper)
        window?.setDimAmount(0f)
        initViewEvent()
    }

    private var mOpenListener: (() -> Unit)? = null
    fun setOnOpenClickListener(listener: () -> Unit) {
        mOpenListener = listener
    }

    private fun initViewEvent() {
        imgRedPaper.setOnClickListener {
            mOpenListener?.invoke()
        }
    }

    fun showGetRed(name: String, gift_text: String, prise: String, url: String) {
        imgRedPaper.visibility = View.GONE
        linNoGetRed.visibility = View.GONE
        tvOpenTop.visibility = View.VISIBLE
        tvRedMoney.visibility = View.VISIBLE
        openRedContainer.visibility = View.VISIBLE
        endText.visibility = View.VISIBLE
        tvRedMoney.text = prise
        GlideUtil.loadCircleImage(context,url, imgRedAvatar,true)
        tvRedPaperOpenContent.text = gift_text
        tvRedPaperOpenName.text = name
    }

    fun noGetRed(name: String, gift_text: String, url: String) {
        imgRedPaper.visibility = View.GONE
        linNoGetRed.visibility = View.VISIBLE
        tvOpenTop.visibility = View.GONE
        tvRedMoney.visibility = View.GONE
        openRedContainer.visibility = View.VISIBLE
        endText.visibility = View.GONE
        GlideUtil.loadCircleImage(context,url, imgRedAvatar,true)
        tvRedPaperOpenContent.text = gift_text
        tvRedPaperOpenName.text = name
    }

    override fun dismiss() {
        super.dismiss()
        closeView()
    }

    private fun closeView() {
        openRedContainer.visibility = View.GONE
        imgRedPaper.visibility = View.VISIBLE
    }
}