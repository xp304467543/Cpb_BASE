package com.home.live.soprtchild

import com.customer.data.home.HomeApi
import com.customer.data.home.HomeHotLiveResponse
import com.customer.utils.JsonUtils
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_wonderful_child.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/25/21
 * @ Describe
 *
 */
class LiveWonderfulChildFragmentPresenter : BaseMvpPresenter<LiveWonderfulChildFragment>() {

    fun getBanner(type: String) {
        HomeApi.getWonderfulBanner(type) {
            onSuccess {
                if (mView.isActive()) {
                    mView.initBanner(it)
                }
            }
            onFailed {
                if (mView.isActive()) {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }

    fun getAll(isRefresh: Boolean) {
        HomeApi.getAllAnchor(mView.currentPage, mView.type) {
            onSuccess {
                if (mView.isActive()) {
                    try {
                        if (it.data.toString().isNotEmpty()) {
                            val content = it.data?.let { it1 ->
                                JsonUtils.fromJson(
                                    it1,
                                    Array<HomeHotLiveResponse>::class.java
                                )
                            }
                            if (isRefresh) {
                                if (content.isNullOrEmpty()) {
                                    ToastUtils.showToast("暂无直播")
                                    mView.setVisible(mView.tvHolder)
                                    mView.smartRefreshLayoutWonderFul?.finishRefresh()
                                    return@onSuccess
                                }
                                mView.setGone(mView.tvHolder)
                                mView.contentAdapter?.refresh(content)
                                mView.smartRefreshLayoutWonderFul?.finishRefresh()
                            } else {
                                mView.setGone(mView.tvHolder)
                                if (content.isNullOrEmpty()) {
                                    mView.smartRefreshLayoutWonderFul?.finishLoadMoreWithNoMoreData()
                                } else {
                                    mView.contentAdapter?.loadMore(content)
                                    mView.smartRefreshLayoutWonderFul?.finishLoadMore()
                                }
                            }
                        }
                    }catch (e:Exception){}

                }
            }
            onFailed {
                if (mView.isActive()) {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }

}