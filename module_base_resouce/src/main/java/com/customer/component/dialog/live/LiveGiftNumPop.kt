package com.customer.component.dialog.live

import android.content.Context
import android.widget.LinearLayout
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.popup.BasePopupWindow

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/26
 * @ Describe
 *
 */
class LiveGiftNumPop(context: Context) : BasePopupWindow(context, R.layout.pop_live_gift_num) {

    var tv1314: LinearLayout
    var tv520: LinearLayout
    var tv88: LinearLayout
    var tv58: LinearLayout
    var tv10: LinearLayout
    var tv1: LinearLayout

    init {
        width = ViewUtils.dp2px(120)
        tv1314 = findView(R.id.tv1314)
        tv520 = findView(R.id.tv520)
        tv88 = findView(R.id.tv88)
        tv58 = findView(R.id.tv58)
        tv10 = findView(R.id.tv10)
        tv1 = findView(R.id.tv1)
        getSelectText()
    }


    private var getSelectTextListener: ((it: String) -> Unit)? = null

    fun getUserDiamondSuccessListener(getTextListener: ((it: String) -> Unit)) {
        getSelectTextListener = getTextListener
    }

    private fun getSelectText() {
        tv1314.setOnClickListener {
            getSelectTextListener?.invoke("1314")
            dismiss()
        }
        tv520.setOnClickListener {
            getSelectTextListener?.invoke("520")
            dismiss()
        }
        tv88.setOnClickListener {
            getSelectTextListener?.invoke("88")
            dismiss()
        }
        tv58.setOnClickListener {
            getSelectTextListener?.invoke("58")
            dismiss()
        }
        tv10.setOnClickListener {
            getSelectTextListener?.invoke("10")
            dismiss()
        }
        tv1.setOnClickListener {
            getSelectTextListener?.invoke("1")
            dismiss()
        }

    }

}