package com.mine.children.movie

import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.base.BaseNormalFragment
import com.customer.data.video.MovieApi
import com.customer.data.video.VideoHistory
import com.glide.GlideUtil
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.fragment_movie_child.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/23/21
 * @ Describe
 *
 */
class MineMovieChildFragment : BaseNormalFragment<MineMovieChildFragmentPresenter>() {

    var currentPosition = 0

    var page = 1

    var data: List<VideoHistory>? = null

    var adapter:VideoSearchAdapter?=null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineMovieChildFragmentPresenter()


    override fun getLayoutRes() = R.layout.fragment_movie_child

    override fun initContentView() {
        currentPosition = arguments?.getInt("moviePosition", 0) ?: 0
        rvHistory.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = context?.let { VideoSearchAdapter(it) }
        rvHistory.adapter = adapter
    }

    override fun initData() {
        getVideoData()
    }

    override fun initEvent() {
        movieSmartRefreshLayout.setOnRefreshListener {
            page = 1
            getVideoData()
        }
        movieSmartRefreshLayout.setOnLoadMoreListener {
            page++
            getVideoData(false)
        }
    }

    private fun getVideoData(isRefresh:Boolean = true) {
        when (currentPosition) {
            0 -> {
                getCollectDate(1,isRefresh)
            }
            1 -> {
                getCollectDate(0,isRefresh)
            }
            2 -> {
                getHistory(isRefresh)
            }
        }
    }

    //查询类型 0 已收藏 1 已购买
    private fun getCollectDate(isCollect: Int,isRefresh:Boolean) {
        MovieApi.getCollectVideo(isCollect, page) {
            onSuccess {
                if (it.isNullOrEmpty()) {
                    if (page == 1) {
                        adapter?.clear()
                        setVisible(tvMovieHistoryHolder)
                    } else movieSmartRefreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                  if (isRefresh)  adapter?.refresh(it) else adapter?.loadMore(it)
                    setGone(tvMovieHistoryHolder)
                }
                finishLoad()
            }
            onFailed { res ->
                ToastUtils.showToast(res.getMsg())
                finishLoad()
            }
        }
    }

    private fun getHistory(isRefresh:Boolean) {
        MovieApi.getHistoryWatchVideo(page) {
            onSuccess {
                if (it.isNullOrEmpty()) {
                    if (page == 1) {
                        adapter?.clear()
                        setVisible(tvMovieHistoryHolder)
                    } else movieSmartRefreshLayout.finishLoadMoreWithNoMoreData()
                } else {
                    if (isRefresh)  adapter?.refresh(it) else adapter?.loadMore(it)
                    setGone(tvMovieHistoryHolder)
                }
                finishLoad()
            }
            onFailed { res ->
                ToastUtils.showToast(res.getMsg())
                finishLoad()
            }
        }
    }

    private fun finishLoad(){
        movieSmartRefreshLayout?.finishRefresh()
        movieSmartRefreshLayout?.finishLoadMore()
    }

    inner class VideoSearchAdapter(val context: Context) : BaseRecyclerAdapter<VideoHistory>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_mine_video_child_item_search

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: VideoHistory?) {
            holder.text(R.id.title, data?.title)
            if ((data?.reads?.toInt()) ?: 0 > 10000) {
                holder.text(R.id.tvReds, (((data?.reads?.toInt()) ?: 0) / 10000).toString() + "万播放")
            } else holder.text(R.id.tvReds, data?.reads + "播放")
            GlideUtil.loadImage(context, data?.cover, holder.getImageView(R.id.imgCover))
            val tag = data?.tag?.split(",")
            val layoutContainer = holder.findViewById<LinearLayout>(R.id.layoutContainer)
            if (tag != null) {
                layoutContainer.removeAllViews()
                val index = if (tag.size > 2) 3 else 1
                repeat(index) {
                    val text = AppCompatTextView(context)
                    text.textSize = 9F
                    if (tag[it].length > 5) {
                        text.text = tag[it].substring(0, 5)
                    } else text.text = tag[it]
                    text.background = ViewUtils.getDrawable(R.drawable.button_grey_line_background)
                    text.setPadding(10, 5, 10, 5)
                    layoutContainer.addView(text)
                    val palms = text.layoutParams as LinearLayout.LayoutParams
                    palms.marginStart = 10
                    text.layoutParams = palms
                }

            } else layoutContainer.removeAllViews()

            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    Router.withApi(ApiRouter::class.java).toVideoPlay(
                        data?.moviesid ?: -1, data?.title ?: "未知"
                    )
                }
            }
            if (currentPosition == 1) setVisible(holder.findView(R.id.imgCollectVideo)) else setGone(holder.findView(R.id.imgCollectVideo))
            holder.click(R.id.imgCollectVideo) {
                if (!FastClickUtil.isFastClick()) {
                    MovieApi.getCollect(data?.moviesid?:0){
                        onSuccess {
                            if (it.iscollect == 0){
                                adapter?.delete(position)
                                adapter?.notifyDataSetChanged()
                                if (getData().isNullOrEmpty()){
                                    setVisible(tvMovieHistoryHolder)
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    companion object {
        fun newInstance(position: Int): MineMovieChildFragment {
            val fragment = MineMovieChildFragment()
            val bundle = Bundle()
            bundle.putInt("moviePosition", position)
            fragment.arguments = bundle
            return fragment
        }
    }
}