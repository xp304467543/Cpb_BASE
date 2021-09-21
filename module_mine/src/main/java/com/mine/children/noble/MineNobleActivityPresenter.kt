package com.mine.children.noble

import com.customer.data.mine.MineApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils

/**
 *
 * @ Author  QinTian
 * @ Date  1/20/21
 * @ Describe
 *
 */
class MineNobleActivityPresenter : BaseMvpPresenter<MineNobleActivity>() {

    fun getNobleInfo() {
        MineApi.getNoble {
            onSuccess {
                if (mView.isActive()){
                    mView.hidePageLoadingDialog()
                    mView.initViewPager(it)
                }
            }
            onFailed {
                if (mView.isActive()){
                    mView.hidePageLoadingDialog()
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }
}