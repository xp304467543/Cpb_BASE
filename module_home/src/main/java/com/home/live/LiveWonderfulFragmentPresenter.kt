package com.home.live

import com.customer.data.home.HomeApi
import com.customer.data.home.HomeLiveAnchor
import com.customer.utils.JsonUtils
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils

/**
 *
 * @ Author  QinTian
 * @ Date  6/24/21
 * @ Describe
 *
 */
class LiveWonderfulFragmentPresenter : BaseMvpPresenter<LiveWonderfulFragment>() {


    fun getAll() {
        HomeApi.getAllAnchor(1, "0") {
            onSuccess {
                if (mView.isActive()) {
                    if ( it.typeList.toString().length > 10) {
                        val bean = it.typeList?.let { it1 -> JsonUtils.fromJson(it1, Array<HomeLiveAnchor>::class.java) }
                        bean?.let { it1 -> mView.initTopTab(it1) }
                    }

                }
            }

            onFailed {
                if (mView.isActive()){
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }

}