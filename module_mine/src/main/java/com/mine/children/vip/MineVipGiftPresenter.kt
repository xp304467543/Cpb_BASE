package com.mine.children.vip

import com.customer.data.mine.MineApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils

/**
 *
 * @ Author  QinTian
 * @ Date  4/5/21
 * @ Describe
 *
 */
class MineVipGiftPresenter : BaseMvpPresenter<MineVipGiftActivity>() {


    fun getGift() {
        MineApi.getVipGift {
            onSuccess {
                if (mView.isActive()) {
                    mView.initDataThing(it)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }
}