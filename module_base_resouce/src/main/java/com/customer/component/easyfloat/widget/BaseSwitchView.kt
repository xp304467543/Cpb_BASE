package com.customer.component.easyfloat.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.customer.component.easyfloat.interfaces.OnTouchRangeListener

abstract class BaseSwitchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    abstract fun setTouchRangeListener(event: MotionEvent, listener: OnTouchRangeListener? = null)

}