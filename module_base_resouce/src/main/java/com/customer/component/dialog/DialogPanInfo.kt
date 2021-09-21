package com.customer.component.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import androidx.appcompat.app.AppCompatDialog
import com.fh.module_base_resouce.R
import kotlinx.android.synthetic.main.dialog_pan_info.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/28/21
 * @ Describe
 *
 */
class DialogPanInfo (context: Context, var current: Int) : AppCompatDialog(context) {

    private var mListener: (() -> Unit)? = null
    fun setConfirmClickListener(listener: () -> Unit) {
        mListener = listener
    }


    init {
        setContentView(R.layout.dialog_pan_info)
        window?.setWindowAnimations(R.style.dialogWindowAnim)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        setCanceledOnTouchOutside(false)
        initPan()
    }



    private fun initPan() {
        closeImg.setOnClickListener {
            dismiss()
        }
        if (current == 0){
            imgCenter.setImageResource(R.mipmap.ic_pan_dh)
            centerText.text = "钻石余额不足，快去兑换吧"
            goRecharge.text = "去兑换"
        }else{
            imgCenter.setImageResource(R.mipmap.ic_pan_cz)
            centerText.text = "您的余额不足请充值"
            goRecharge.text = "去充值"
        }
        goRecharge.setOnClickListener {
            mListener?.invoke()
        }
    }
}