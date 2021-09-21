package com.mine.children.recharge

import com.customer.data.mine.MineApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_cash_out.*
import kotlinx.android.synthetic.main.usdt_list_act.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/11/21
 * @ Describe
 *
 */
class MineUsdtListActPresenter : BaseMvpPresenter<MineUsdtListAct>() {



    fun getUsdt(){
        MineApi.getUsdt{
            onSuccess {
                if (mView.isActive()){
                    mView.hidePageLoadingDialog()
                    if (!it.isNullOrEmpty()){
                        mView.adapter?.refresh(it)
                    }
                    mView.usdtSmartRefreshLayout.finishRefresh()
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ToastUtils.showToast(it.getMsg())
                mView.usdtSmartRefreshLayout.finishRefresh()
            }
        }
    }

    fun delUsdt(id:String){

    }
}