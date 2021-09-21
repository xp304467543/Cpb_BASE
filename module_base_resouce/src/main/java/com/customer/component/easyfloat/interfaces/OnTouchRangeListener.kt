package com.customer.component.easyfloat.interfaces

import com.customer.component.easyfloat.widget.BaseSwitchView

/**
 * @Description: 区域触摸事件回调
 */
interface OnTouchRangeListener {

    /**
     * 手指触摸到指定区域
     */
    fun touchInRange(inRange: Boolean, view: BaseSwitchView)

    /**
     * 在指定区域抬起手指
     */
    fun touchUpInRange()

}