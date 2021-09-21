package com.home.live.soprtchild
import com.customer.data.home.HomeApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_home_live_child.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/15/21
 * @ Describe
 *
 */
class LiveSportChildPresenter : BaseMvpPresenter<LiveSportChildFragment>() {

    fun getSportBanner(type:String) {
        HomeApi.getSportBanner(type) {
            onSuccess {
                if (mView.isActive()) {
                    mView.upDateBanner(it)
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }


    fun getSportLive(race_type: String, page: Int, isRefresh: Boolean) {
        HomeApi.getSportLive(race_type, page) {
            onSuccess {
                if (mView.isActive()) {
                    if (isRefresh) {
                        if (it.list.isNullOrEmpty()) {
                            ToastUtils.showToast("暂无直播")
                            mView.sportLiveAdapter?.clear()
                            mView.liveRefresh?.finishRefresh()
                            return@onSuccess
                        } else {
                            mView.liveRefresh?.finishRefresh()
                            mView.initSportLive(it, isRefresh)
                        }
                    } else {
                        if (it.list.isNullOrEmpty()) {
                            mView.liveRefresh?.finishRefreshWithNoMoreData()
                            return@onSuccess
                        } else {
                            mView.liveRefresh?.finishLoadMore()
                            mView.initSportLive(it, isRefresh)
                        }
                    }
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }
}