package com.home.video.more

import android.view.View
import com.customer.data.video.MovieApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import kotlinx.android.synthetic.main.fragment_video_more.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/30
 * @ Describe
 *
 */
class VideoMoreActivityChildPresenter : BaseMvpPresenter<VideoMoreActivityChildFragment>() {

    fun getMoreVideo(
        typeId: Int,
        cid: Int,
        num: Int,
        isMore: Boolean,
        column: String,
        page: Int,
        prePage: Int,
        tag:String
    ) {
        MovieApi.getVideoMore(typeId, cid, num, isMore, column, page, prePage,tag) {
            if (mView.isActive()) {
                onSuccess {
                    if (it.list.isNullOrEmpty()) {
                        if (mView.mPage == 1) {
                            //显示无数据页面
                           if (mView.videoHolder!=null) ViewUtils.setVisible(mView.videoHolder)
                            if (mView.smartRefreshLayoutVideoMore != null) mView.smartRefreshLayoutVideoMore.finishRefreshWithNoMoreData()
                        } else {
                            if (mView.smartRefreshLayoutVideoMore != null) mView.smartRefreshLayoutVideoMore.finishLoadMoreWithNoMoreData()
                        }
                    } else {
                        if (mView.videoHolder!=null)mView.videoHolder?.visibility = View.GONE
                        if (mView.mPage == 1){
                            mView.videoAdapter?.clear()
                            mView.videoAdapter?.refresh(it.list)
                        } else mView.videoAdapter?.loadMore(it.list)
                    }
                    if (mView.smartRefreshLayoutVideoMore != null) {
                        mView.smartRefreshLayoutVideoMore.finishRefresh()
                        mView.smartRefreshLayoutVideoMore.finishLoadMore()
                    }
                }
                onFailed {
                    ViewUtils.setVisible(mView.videoHolder)
                    ToastUtils.showToast(it.getMsg())
                    if (mView.smartRefreshLayoutVideoMore != null) {
                        mView.smartRefreshLayoutVideoMore.finishRefresh()
                        mView.smartRefreshLayoutVideoMore.finishLoadMore()
                    }
                }
            }
        }
    }
}