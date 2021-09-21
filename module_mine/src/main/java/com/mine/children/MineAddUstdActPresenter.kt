package com.mine.children

import android.widget.TextView
import com.customer.utils.CountDownTimerUtils
import com.lib.basiclib.base.mvp.BaseMvpPresenter

/**
 *
 * @ Author  QinTian
 * @ Date  4/10/21
 * @ Describe
 *
 */
class MineAddUstdActPresenter : BaseMvpPresenter<MineAddUstdAct>() {

    fun time(textView: TextView) {
        val mCountDownTimerUtils = CountDownTimerUtils(textView, 600000, 1000,true)
        mCountDownTimerUtils.start()
    }
}