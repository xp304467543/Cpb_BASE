package com.home.live.room

import com.customer.data.home.HomeApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_sport_info.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/18/21
 * @ Describe
 *
 */
class LiveSportRoomChildInfoPresenter : BaseMvpPresenter<LiveSportRoomChildInfo>() {


    fun getTeamInfo(type:String,teamid:String,ishome:Boolean){
        HomeApi.getTeamInfo(type,teamid){
            onSuccess {
                if (mView.isActive()){
                    if (ishome){
                        mView.homeTeamInfo = it
                    }else{
                        mView.awayTeamInfo = it
                    }
                    if (it.introduction.isNullOrEmpty()){
                        mView.tvInfo.text = "暂无信息"
                    }else{
                        mView.tvInfo.text = it.introduction
                    }
                }

            }
            onFailed {
//                ToastUtils.showToast(it.getMsg())
                mView.tvInfo.text = "暂无信息"
            }
        }
    }
}