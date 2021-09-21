package com.mine.children.recharge

import com.customer.data.mine.MineApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.act_recharge_teach_usd.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/9/21
 * @ Describe
 *
 */
class MineUsdRgRechargePresenter : BaseMvpPresenter<MineUsdRgRecharge>() {

    fun getList() {
        MineApi.getHandUsdtList {
            if (mView.isActive()) {
                onSuccess {
                    mView.initCode(it)
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }


    fun getUserAddress() {
        MineApi.getUserBank(2) {
            if (mView.isActive()) {
                onSuccess {
                    mView.setUsedUsdt(it)
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }
}