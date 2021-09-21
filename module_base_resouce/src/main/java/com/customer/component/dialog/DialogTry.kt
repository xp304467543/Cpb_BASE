package com.customer.component.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager
import com.customer.ApiRouter
import com.customer.data.LoginOut
import com.fh.module_base_resouce.R
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.utils.ViewUtils.isPort
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.dialog_login_try.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/9/21
 * @ Describe
 *
 */
class DialogTry(context: Context, val horizontal: Boolean = false) : Dialog(context) {


    init {
        window?.setWindowAnimations(R.style.BaseDialogAnim)
        setContentView(R.layout.dialog_login_try)
        val lp = window?.attributes
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        lp?.height = ViewUtils.dp2px(280)
        window?.attributes = lp
        initEvent()
    }

    fun initEvent() {
        close.setOnClickListener {
            dismiss()
        }
        tLogin.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!isPort()) {
                    scanForActivity(context)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    setFullScreen(scanForActivity(context), false)
                }
                GlobalDialog.spClear()
                RxBus.get().post(LoginOut(true))
                Router.withApi(ApiRouter::class.java).toLogin(1)
                dismiss()
            }
        }
        tReg.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!isPort()) {
                    scanForActivity(context)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    setFullScreen(scanForActivity(context), false)
                }
                GlobalDialog.spClear()
                RxBus.get().post(LoginOut(true))
                Router.withApi(ApiRouter::class.java).toLogin(2)
                dismiss()
            }
        }
    }

    private fun scanForActivity(cont: Context?): Activity? {
        return when (cont) {
            null -> null
            is Activity -> cont
            is ContextWrapper -> scanForActivity(cont.baseContext)
            else -> null
        }

    }

    private fun setFullScreen(activity: Activity?, isFull: Boolean) {
        val orientation = if (isFull) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        activity?.requestedOrientation = orientation
    }

}