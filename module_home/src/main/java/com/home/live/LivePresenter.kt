package com.home.live

import com.customer.data.home.HomeApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter

/**
 *
 * @ Author  QinTian
 * @ Date  6/14/21
 * @ Describe
 *
 */
class LivePresenter : BaseMvpPresenter<LiveFragment>() {


    fun getNav(){
        HomeApi.getLiveNav {
            onSuccess {
                if (it.isNullOrEmpty())return@onSuccess
                if (it.contains("影视") || it.contains("AV影视")) mView.initViewPager(true) else mView.initViewPager(false)
            }
            onFailed {

            }
        }
    }


}