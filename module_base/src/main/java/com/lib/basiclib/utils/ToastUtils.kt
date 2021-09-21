package com.lib.basiclib.utils

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.fh.basemodle.R
import com.lib.basiclib.app.BaseApplication


/**
 * 土司工具类
 */
@SuppressLint("ShowToast")
object ToastUtils {
    var toast: Toast? = null //实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     */

    fun showToast(msg: String?) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }


    fun showEnterToast(msg: String?) {
       val toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT)
        toast?.setGravity(Gravity.BOTTOM, 0, ViewUtils.dp2px(50))
        toast?.view?.setBackgroundColor(ViewUtils.getColor(R.color.transparent))
        val text = toast?.view?.findViewById<View>(android.R.id.message) as TextView
        text.textSize = 12f
        text.setTextColor(ViewUtils.getColor(R.color.grey_75))
        toast.setText(msg)
        toast.show()
    }


    /**
     * 短时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     */
    fun showToastCenter(msg: String?) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }

    /**
     * 短时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     */
    fun showToastTop(msg: String?) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT)
            toast?.setGravity(Gravity.TOP, 0, 0)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }

    /**
     * 长时间显示Toast【居下】
     * @param msg 显示的内容-字符串
     */
    fun showLongToast(msg: String?) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }

    /**
     * 长时间显示Toast【居中】
     * @param msg 显示的内容-字符串
     */
    fun showLongToastCenter(msg: String?) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG)
            toast?.setGravity(Gravity.CENTER, 0, 0)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }

    /**
     * 长时间显示Toast【居上】
     * @param msg 显示的内容-字符串
     */
    fun showLongToastTop(msg: String?) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_LONG)
            toast?.setGravity(Gravity.TOP, 0, 0)
        } else {
            toast!!.setText(msg)
        }
        toast!!.show()
    }

    var toastView: Toast? = null //实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长
    fun showToastView( text: String) {
        val inflater = LayoutInflater.from(BaseApplication.getApplication())
        val params = LinearLayout.LayoutParams(ViewUtils.getScreenWidth(), ViewGroup.LayoutParams.MATCH_PARENT)
        val view: View = inflater.inflate(R.layout.toast_view, null)
        val imageView: ImageView = view.findViewById<View>(R.id.toast_image) as ImageView
        val t = view.findViewById<View>(R.id.toast_text) as TextView
        val toastLayout = view.findViewById<LinearLayout>(R.id.toastLayout)
        toastLayout.layoutParams = params
        t.text = text
        if (toastView != null) {
            toastView?.cancel()
        }
        toastView = Toast(BaseApplication.getApplication())
        toastView?.setGravity(Gravity.TOP, 0, 0)
        toastView?.duration = Toast.LENGTH_LONG
        toastView?.view = view
        toastView?.show()
    }

}