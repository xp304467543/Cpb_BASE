package com.customer.component.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.HomeLiveTwentyNewsResponse
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_forbidden_words.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/26
 * @ Describe
 *
 */
class DialogForbiddenWords(context: Context, var data: HomeLiveTwentyNewsResponse) :
    Dialog(context) {


    private var banTime = "30"

    private var banRoom = data.room_id

    init {
        setContentView(R.layout.dialog_forbidden_words)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setGravity(Gravity.CENTER or Gravity.CENTER)
        val lp = window?.attributes
        lp?.width = ViewUtils.dp2px(316) // 宽度
//      lp.alpha = 0.7f // 透明度
        window?.attributes = lp
        setCanceledOnTouchOutside(false)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        imgForbiddenClose.setOnClickListener {
            dismiss()
        }
        tvForbiddenName.text = "禁言 “" + data.userName + "”"

        cbForbidden30Min.setOnClickListener {
            cbForbidden30Min.isChecked = true
            cbForbiddenForever.isChecked = false
            banTime = "30"
        }
        cbForbiddenForever.setOnClickListener {
            cbForbidden30Min.isChecked = false
            cbForbiddenForever.isChecked = true
            banTime = "0"
        }
        tvForbiddenConfirm.setOnClickListener {
            forbidden()
        }

        cbForbiddenRoom.setOnClickListener {
            banRoom = if (cbForbiddenRoom.isChecked) {
                ""
            } else data.room_id
        }
    }


    private fun forbidden() {
        val dialog = LoadingDialog(context)
        dialog.show()
        HomeApi.forBiddenWords(
            UserInfoSp.getUserId(),
            data.user_id.toString(), banRoom.toString(), banTime
        ) {
            onSuccess {
                val ban = if (banTime == "0") "永久禁言" else "禁言30分钟"
                ToastUtils.showToast(data.userName + " 成功 " + ban)
                dialog.dismiss()
                dismiss()
            }
            onFailed {
                ToastUtils.showToast(it.getMsg()!!)
                dialog.dismiss()
            }
        }
    }
}