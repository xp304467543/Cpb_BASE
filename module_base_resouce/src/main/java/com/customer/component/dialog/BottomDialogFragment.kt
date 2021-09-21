package com.customer.component.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/1
 * @ Describe
 *
 */

abstract class BottomDialogFragment : DialogFragment() {


    var rootView: View? = null

    var window: Window? = null

    abstract val layoutResId: Int

    /**
     * 初始化View和设置数据等操作的方法
     */
    abstract fun initView()

    abstract fun initData()

    abstract fun isShowTop(): Boolean

    abstract fun canceledOnTouchOutside(): Boolean

    abstract fun initFragment()

    abstract val resetHeight: Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(canceledOnTouchOutside())
        rootView = super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            rootView = inflater.inflate(layoutResId, container, false)
            initView()
            initData()
        } else initData()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    override fun onStart() {
        super.onStart()
        window = dialog?.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setWindowAnimations(R.style.bottomDialog)
        window?.setLayout(-1, -2)
        window?.setDimAmount(0f)
        val params = window?.attributes
        params?.gravity = Gravity.BOTTOM
        params?.width = resources.displayMetrics.widthPixels
        if (resetHeight == 0) {
            params?.height = if (isShowTop()) ViewUtils.getScreenHeight() * 2 / 3 else
                ViewUtils.getScreenHeight() * 2 / 3 - ViewUtils.dp2px(2)
        } else params?.height = resetHeight
        window?.attributes = params
    }

}