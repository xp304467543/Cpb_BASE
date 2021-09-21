package com.customer.component.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.customer.data.UserInfoSp
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_red_rain.*
import kotlinx.android.synthetic.main.dialog_task.*
import java.math.BigDecimal
import java.math.BigInteger

/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/29
 * @ Describe
 *
 */
class DialogRedRain  (context: Context, var money:BigDecimal?) : Dialog(context) {

    var canContain = true //是否继续

    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_red_rain)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        setCanceledOnTouchOutside(false)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        if (money?.compareTo(BigDecimal.ZERO)?:0  == 1){
            redMoney.text = money.toString()
            imgGoOrClose.setImageResource(R.mipmap.ic_get_red)
            canContain = false
        }else{
            imgGoOrClose.setImageResource(R.mipmap.ic_jx_red)
            redMoney.background = ViewUtils.getDrawable(R.mipmap.ic_red_next)
            canContain = true
        }
        imgClose.setOnClickListener {
            dismiss()
        }
    }
}