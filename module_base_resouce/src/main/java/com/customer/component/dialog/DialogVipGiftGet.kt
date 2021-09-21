package com.customer.component.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import com.customer.data.UserInfoSp
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_vip_gift_get.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/6/21
 * @ Describe
 *
 */
class DialogVipGiftGet(context: Context) : Dialog(context) {

    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_vip_gift_get)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
        initEvent()
    }

    private var mItemClickListener: ((name: String, address: String, phone: String) -> Unit)? = null


    fun setSelectListener(SelectListener: ((name: String, address: String, phone: String) -> Unit)) {
        mItemClickListener = SelectListener
    }

    fun initEvent() {

        if (!UserInfoSp.getVipGiftUser()
                .isNullOrEmpty()
        ) userName.setText(UserInfoSp.getVipGiftUser())
        if (!UserInfoSp.getVipGiftAddress()
                .isNullOrEmpty()
        ) userAddress.setText(UserInfoSp.getVipGiftAddress())
        if (!UserInfoSp.getVipGiftPhone()
                .isNullOrEmpty()
        ) userPhone.setText(UserInfoSp.getVipGiftPhone())

        btClose.setOnClickListener {
            dismiss()
        }
        userName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                judgeText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        userAddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                judgeText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        userPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                judgeText()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    fun judgeText() {
        if (!userName.text.isNullOrEmpty() && !userAddress.text.isNullOrEmpty() && !userPhone.text.isNullOrEmpty()) {
            btnSubmit.background = ViewUtils.getDrawable(R.drawable.button_blue_background)
            btnSubmit.isClickable = true
            btnSubmit.setTextColor(ViewUtils.getColor(R.color.white))
            btnSubmit.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    UserInfoSp.putVipGiftUser(userName.text.toString())
                    UserInfoSp.putVipGiftAddress(userAddress.text.toString())
                    UserInfoSp.putVipGiftPhone(userPhone.text.toString())
                    mItemClickListener?.invoke(
                        userName.text.toString(),
                        userAddress.text.toString(),
                        userPhone.text.toString()
                    )
                }
            }
        } else {
            btnSubmit.background = ViewUtils.getDrawable(R.drawable.button_grey_background)
            btnSubmit.setOnClickListener(null)
            btnSubmit.isClickable = false
            btnSubmit.setTextColor(ViewUtils.getColor(R.color.color_999999))
        }
    }
}