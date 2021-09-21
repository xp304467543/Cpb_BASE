package com.customer.component.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.fh.module_base_resouce.R
import kotlinx.android.synthetic.main.dialog_round_rule.*


/**
 *
 * @ Author  QinTian
 * @ Date  6/26/21
 * @ Describe
 *
 */
class DialogRoundRule( context: Context) : Dialog(context) {


    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_round_rule)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        closeImg.setOnClickListener {
            dismiss()
        }
    }

}