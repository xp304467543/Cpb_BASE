package com.lib.basiclib.widget.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.fh.basemodle.R
import com.lib.basiclib.utils.ViewUtils

/**
 *
 * 加载进度框
 */
class MaterialLoadingDialog private constructor(context: Context) : MaterialDialog(context) {

    class Builder(context: Context) : MaterialDialog.Builder(context) {

        @SuppressLint("InflateParams")
        fun show(msg: String?): AlertDialog {
            val dialog = show()
            val dialogView = LayoutInflater.from(context).inflate(R.layout.base_layout_loading_dialog, null)
            dialog.setContentView(dialogView)
            dialogView.findViewById<TextView>(R.id.baseTvLoadingMessage).text = msg
            dialog.setCanceledOnTouchOutside(false)

            val window = dialog.window
            val params = window?.attributes
            window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            params?.dimAmount = 0.5f
            params?.width = ViewUtils.dp2px(100)
            params?.height = ViewUtils.dp2px(100)
            window?.attributes = params
            window?.setDimAmount(0f)
            window?.setBackgroundDrawableResource(R.color.transparent)
            return dialog
        }
    }
}