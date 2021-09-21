package com.home.old

import android.content.Intent
import com.customer.ApiRouter
import com.customer.component.dialog.GlobalDialog
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.HomeTab
import com.home.rank.HomeRankingActivity
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.fragment_home_old.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/5/22
 * @ Describe
 *
 */
class HomePresenter : BaseMvpPresenter<HomeFragment>() {

    //获取新消息
    fun getNewMsg() {
        HomeApi.getIsNewMessage {
            onSuccess {
                if (mView.isActive()) {
                    if (it.msgCount > 0) {
                        mView.setVisible(mView.topDian)
                    } else {
                        mView.setGone(mView.topDian)
                    }
                    mView.msg1 = it.countList?.`_$0`?:"0"
                    mView.msg2 = it.countList?.`_$2`?:"0"
                    mView.msg3 = it.countList?.`_$3`?:"0"
                    mView.msg4 = it.countList?.`_$5`?:"0"
                }
            }
            onFailed {
                if (it.getCode() == 2003){
                    GlobalDialog.otherLogin(mView.requireActivity())
                }
            }
        }
    }

    fun getRedTask() {
        HomeApi.getRedTask {
            onSuccess {
                if (mView.isActive()) {
                    if (it.prompt == 1) {
                        if (mView.topDianTask!=null)mView.setVisible(mView.topDianTask)
                    } else {
                        if (mView.topDianTask!=null)mView.setGone(mView.topDianTask)
                    }
                }
            }
        }
    }


    fun getHomeTitle(){
        HomeApi.getHomeTitle{
            onSuccess {
                if (mView.isActive()){
                    UserInfoSp.putCustomer(it.customer?:"")
                    if (!it.index_nav.isNullOrEmpty() && it.index_nav!!.size>1){
                        mView.homeSwitchViewPager?.setScroll(true)
                        mView.titleList.clear()
                        for ( item in it.index_nav!!){
                            mView.titleList.add(HomeTab(item.name?:"null",item.code?:"null") )
                        }
                        if (mView.titleList.size > 1 && mView.titleList[1].code == "movie") {
                            mView.homeSwitchViewPager?.setScroll(true)
                        } else mView.homeSwitchViewPager?.setScroll(false)
                    }
                    mView.topAdapter?.notifyDataSetChanged()
                    mView.topAdapter?.setConfirmClickListener {res->
                        if (!FastClickUtil.isFastClick()){
                            if (res.code == "vip"){
                            Router.withApi(ApiRouter::class.java).toVipPage(UserInfoSp.getVipLevel())
                            }else if (res.code == "ranking"){
                                if (!UserInfoSp.getIsLogin()) {
                                    GlobalDialog.notLogged(mView.requireActivity())
                                }else{
                                    mView.startActivity(Intent(mView.requireContext(),HomeRankingActivity::class.java))
                                }
                            }
                        }
                    }
                }
            }
            onFailed {
                ToastUtils.showToast("Tab获取失败,请退出重试")
            }
        }
    }
}