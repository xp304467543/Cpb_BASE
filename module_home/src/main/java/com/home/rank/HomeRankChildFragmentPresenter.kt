package com.home.rank

import com.customer.data.home.HomeApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils

/**
 *
 * @ Author  QinTian
 * @ Date  1/22/21
 * @ Describe
 *
 */
class HomeRankChildFragmentPresenter : BaseMvpPresenter<HomeRankChildFragment>() {

    fun getAnchorData() {
        HomeApi.getAnchorRank {
            onSuccess {
                if (mView.isActive()){
                    mView.initAnchor(it)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

    fun getReportRank(){
        HomeApi.getReportRank {
            onSuccess {
                if (mView.isActive()){
                    mView.initReport(it)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

    fun getBetRank(){
        HomeApi.getBetRank {
            onSuccess {
                if (mView.isActive()){
                    mView.initBetRank(it)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }
    fun getVideoRank(){
        HomeApi.geVideoRank {
            onSuccess {
                if (mView.isActive()){
                    mView.initVideoRank(it)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

}