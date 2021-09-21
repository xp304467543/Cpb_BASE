package com.customer.component.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.customer.data.discount.CustomerQuestion
import com.fh.module_base_resouce.R
import com.glide.GlideUtil
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.dialog_customer.*


/**
 *
 * @ Author  QinTian
 * @ Date  6/26/21
 * @ Describe
 *
 */
class DialogCustomer(var data: CustomerQuestion?, context: Context) : Dialog(context) {


    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_customer)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        try {
            tvTitle.text = "客服二维码"
            GlideUtil.loadImageBanner(context, data?.code_image, qrCode)
            tvWx.text = "客服账号: " + data?.value
            imgClose.setOnClickListener { dismiss() }
            btOpen.setOnClickListener {
                if (!FastClickUtil.isFastClick()){
                    ViewUtils.copyText(data?.value.toString())
                    ToastUtils.showToast("已复制到剪贴板")
//                    val intent = Intent()
//                    val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
//                    intent.action = Intent.ACTION_MAIN
//                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                    intent.component = cmp
//                    context.startActivity(intent)
                    dismiss()
                }
            }
        } catch (e: Exception) {
        }

    }


}