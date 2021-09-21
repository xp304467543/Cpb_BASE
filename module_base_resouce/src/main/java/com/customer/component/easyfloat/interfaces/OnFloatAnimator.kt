package com.customer.component.easyfloat.interfaces

import android.animation.Animator
import android.view.View
import android.view.WindowManager
import com.customer.component.easyfloat.enums.SidePattern

/**
 * @function: 系统浮窗的出入动画
 */
interface OnFloatAnimator {

    fun enterAnim(
        view: View,
        params: WindowManager.LayoutParams,
        windowManager: WindowManager,
        sidePattern: SidePattern
    ): Animator? = null

    fun exitAnim(
        view: View,
        params: WindowManager.LayoutParams,
        windowManager: WindowManager,
        sidePattern: SidePattern
    ): Animator? = null

}