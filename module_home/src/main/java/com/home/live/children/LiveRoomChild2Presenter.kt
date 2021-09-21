package com.home.live.children

import android.view.View
import com.customer.component.dialog.DialogGlobalTips
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import kotlinx.android.synthetic.main.fragmeent_live_child_2.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/26
 * @ Describe
 *
 */
class LiveRoomChild2Presenter : BaseMvpPresenter<LiveRoomChild2>() {
    fun getAnchorInfo(anchorID: String) {
        HomeApi.getLiveAnchorInfo(anchorID) {
            if (mView.isActive()) {
                onSuccess {
                    mView.initAnchorInfo(it)
                    if (UserInfoSp.getUserType() != "4"){
                        getAnchorDynamic(anchorID)
                    }else mView.setGone(mView.spLiveAnchorLoading)
                }
            }
        }
    }

    fun recommendAnchor(anchorID: String){
        HomeApi.recommendAnchor(anchorID){
            onSuccess { if (mView.isActive()){
                mView.context?.let { it1 -> DialogGlobalTips(it1, "推荐主播成功","确定","","").show() }
            } }
            onFailed { if (mView.isActive()){
                mView.context?.let { it1 -> DialogGlobalTips(it1,it.getMsg().toString(),"确定","","").show() }
            }
            }
        }
    }

    //主播动态
    private fun getAnchorDynamic(anchorID: String) {
        HomeApi.getAnchorDynamic(anchorId = anchorID) {
            onSuccess {
                if (mView.isActive()){
                    mView.spLiveAnchorLoading.visibility = View.GONE
                    mView.initAnchorNews(it)
                }
            }
        }
    }
}