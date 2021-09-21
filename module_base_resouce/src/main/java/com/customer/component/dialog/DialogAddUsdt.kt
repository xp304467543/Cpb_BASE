package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.customer.data.mine.MineApi
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.dialog_usdt.*

/**
 *
 * @ Author  QinTian
 * @ Date  5/8/21
 * @ Describe
 *
 */
class DialogAddUsdt(context: Context, var address: String?) : Dialog(context) {

    private var onSuccess: ((str:String) -> Unit)? = null
    init {
        setContentView(R.layout.dialog_usdt)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window?.attributes
//      lp.alpha = 0.7f // 透明度
        window?.attributes = lp
        setCanceledOnTouchOutside(false)
        initDialog()
    }

    fun setOnSuccessListener(listener: (position: String) -> Unit) {
        onSuccess = listener
    }


    fun initDialog() {
        imgClose.setOnClickListener {
            dismiss()
        }
        if (!address.isNullOrEmpty()) {
            usdtAddress.setText(address)
        }
        tvConfirmChange.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (usdtAddress.text.isNullOrEmpty() || usdtAddressHint.text.isNullOrEmpty()) {
                    ToastUtils.showToast("请输入完整信息")
                } else {
                    MineApi.addUserBankCard(
                        "",
                        usdtAddress.text.toString().trim(),
                        usdtAddressHint.text.toString().trim(),
                        2
                    ) {
                        onSuccess {
                            onSuccess?.invoke(usdtAddress.text.toString().trim())
                            ToastUtils.showToast("添加成功")
                        }
                        onFailed {
                            ToastUtils.showToast(it.getMsg())
                        }
                    }
                }
            }
        }
    }
}