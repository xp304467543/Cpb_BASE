package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.customer.data.home.HomeApi
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.rxnetgo.exception.ApiException
import kotlinx.android.synthetic.main.dialog_buy_times.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/26/21
 * @ Describe
 *
 */
class DialogRoundTimes (context: Context) : Dialog(context) {

    var current = 1

    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_buy_times)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initEvent()
    }

    private var getBuyListener: ((it: Int) -> Unit)? = null

    fun getUserDiamondSuccessListener(getTextListener: ((it: Int) -> Unit)) {
        getBuyListener = getTextListener
    }

    private var getBuyFailedListener: ((e:ApiException) -> Unit)? = null

    fun getUserDiamondFailedListener(getTextListener: ((e:ApiException) -> Unit)) {
        getBuyFailedListener = getTextListener
    }

    private fun initEvent() {
        imgClose.setOnClickListener {
            dismiss()
        }
        buttonSure?.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                HomeApi.getRoundTimes(current){
                    onSuccess {
                        getBuyListener?.invoke(it.times_now?:0)
                        ToastUtils.showToast("购买成功")
                        dismiss()
                    }
                    onFailed {
                        getBuyFailedListener?.invoke(it)
                    }
                }
            }
        }
        imgAdd?.setOnClickListener {
            current++
            tvNum.text = current.toString()
            tvDiamond.text = (20*current).toString()
        }
        imgDel?.setOnClickListener {
            if(current!=0){
                current--
                tvNum.text = current.toString()
                tvDiamond.text = (20*current).toString()
            }
        }
    }
}