package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_long_sel.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/2/21
 * @ Describe
 *
 */
class DialogLogSel(context: Context, var current: String = "6") : Dialog(context) {

    private var mItemClickListener: ((pos: String) -> Unit)? = null

    fun setSelectListener(SelectListener: ((it: String) -> Unit)) {
        mItemClickListener = SelectListener
    }

    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_long_sel)
        val lp = window?.attributes
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lp?.height = ViewUtils.dp2px(210)
        window?.attributes = lp
        initEvent()
    }

    fun initEvent() {
        when(current){
            "6" ->{
                bt1.isChecked = true
                bt2.isChecked = false
                bt3.isChecked = false
                bt4.isChecked = false
            }
            "8" ->{
                bt1.isChecked = false
                bt2.isChecked = true
                bt3.isChecked = false
                bt4.isChecked = false
            }
            "10" ->{
                bt1.isChecked = false
                bt2.isChecked = false
                bt3.isChecked = true
                bt4.isChecked = false
            }
            "12" ->{
                bt1.isChecked = false
                bt2.isChecked = false
                bt3.isChecked = false
                bt4.isChecked = true
            }
        }

        bt1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                current = "6"
                bt2.isChecked = false
                bt3.isChecked = false
                bt4.isChecked = false
            mItemClickListener?.invoke("6")
            dismiss()
            }

        }
        bt2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                current = "8"
                bt1.isChecked = false
                bt3.isChecked = false
                bt4.isChecked = false
                mItemClickListener?.invoke("8")
                dismiss()
            }

        }
        bt3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                current = "10"
                bt1.isChecked = false
                bt2.isChecked = false
                bt4.isChecked = false
                mItemClickListener?.invoke("10")
                dismiss()
            }
        }
        bt4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                current = "12"
                bt2.isChecked = false
                bt3.isChecked = false
                bt1.isChecked = false
                mItemClickListener?.invoke("12")
                dismiss()
            }
        }
        lin1.setOnClickListener {
            bt1.isChecked = true
            bt2.isChecked = false
            bt3.isChecked = false
            bt4.isChecked = false
            current = "6"
            mItemClickListener?.invoke("6")
            dismiss()
        }
        lin2.setOnClickListener {
            bt1.isChecked = false
            bt2.isChecked = true
            bt3.isChecked = false
            bt4.isChecked = false
            current = "8"
            mItemClickListener?.invoke("8")
            dismiss()
        }
        lin3.setOnClickListener {
            bt1.isChecked = false
            bt2.isChecked = false
            bt3.isChecked = true
            bt4.isChecked = false
            current = "10"
            mItemClickListener?.invoke("10")
            dismiss()
        }
        lin4.setOnClickListener {
            bt1.isChecked = false
            bt2.isChecked = false
            bt3.isChecked = false
            bt4.isChecked = true
            current = "12"
            mItemClickListener?.invoke("12")
            dismiss()
        }
    }
}