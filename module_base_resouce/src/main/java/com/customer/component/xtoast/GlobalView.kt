package com.customer.component.xtoast

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.AppUtils
import com.lib.basiclib.utils.ViewUtils


/**
 *
 * @ Author  QinTian
 * @ Date  1/25/21
 * @ Describe
 *
 */
object GlobalView {
    fun showPrise(str: String) {
//        //自定义Toast控件
//        val toastView: View = LayoutInflater.from(AppUtils.getApplication()).inflate(R.layout.global_toast_view, null)
//        //动态设置toast控件的宽高度，宽高分别是130dp
//        //这里用了一个将dp转换为px的工具类PxUtil
//        val layoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
//            ViewUtils.dp2px(243),
//            ViewUtils.dp2px(43)
//        )
//        toastView.findViewById<RelativeLayout>(R.id.toast_linear).layoutParams = layoutParams
//        toastView.findViewById<TextView>(R.id.tv_toast_clear).text = str
//        val toast = Toast(AppUtils.getApplication())
//        toast.duration = Toast.LENGTH_SHORT
//        toast.setGravity(Gravity.CENTER, 0, 0)
//        toast.view = toastView
//        toast.duration = 5*1000
//        try {
//            val mTNField = toast.javaClass.getDeclaredField("mTN")
//            mTNField.isAccessible = true
//            val mTNObject = mTNField[toast]
//            val tnClass: Class<*> = mTNObject.javaClass
//            val paramsField = tnClass.getDeclaredField("mParams")
//            /**由于WindowManager.LayoutParams mParams的权限是private */
//            paramsField.isAccessible = true
//            val layoutParams = paramsField[mTNObject] as WindowManager.LayoutParams
//            layoutParams.windowAnimations = R.style.animToast
//        } catch (e: NoSuchFieldException) {
//            e.printStackTrace()
//        } catch (e: IllegalAccessException) {
//            e.printStackTrace()
//        }
//        toast.show()
//        ToastManager.getInstance(AppUtils.getContext()).makeToastSelfViewAnim(toastView,R.style.animToast)
    }
}