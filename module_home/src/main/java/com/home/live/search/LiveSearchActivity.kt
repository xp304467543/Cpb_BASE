package com.home.live.search

import android.text.TextUtils
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.ApiRouter
import com.customer.data.home.*
import com.glide.GlideUtil
import com.home.R
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.round.RoundTextView
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_live_search.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/2
 * @ Describe
 *
 */
class LiveSearchActivity : BaseNavActivity() {

    private lateinit var resultAdapter: HomeHotLiveAdapter

    private lateinit var relatedAdapter: HomeHotLiveAdapter

    override fun getContentResID() = R.layout.act_live_search

    override fun isShowToolBar() = false


    override fun initContentView() {
        //查询结果
        resultAdapter = HomeHotLiveAdapter()
        rvResult.adapter = resultAdapter
        rvResult.layoutManager = GridLayoutManager(this, 2)

        //查询推荐
        relatedAdapter = HomeHotLiveAdapter()
        rvRelated.adapter = relatedAdapter
        rvRelated.layoutManager = GridLayoutManager(this, 2)
    }

    override fun initData() {
        getAnchorPop()
    }


    override fun initEvent() {
        imgBack.setOnClickListener {
            finish()
        }

        tvSearch.setOnClickListener {
            val text = etSearch.text.toString()
            if (!TextUtils.isEmpty(text)) {
                setGone(initRecommend)
                search(text)
            } else ToastUtils.showToast("请输入内容")

        }
    }

    private fun getAnchorPop() {
        HomeApi.getPopAnchor {
            onSuccess {
                    initAnchorPop(it)
            }
        }
    }

    //主播推荐
    private fun initAnchorPop(data: List<HomeAnchorRecommend>) {
        if (!data.isNullOrEmpty()){
            //往容器内添加TextView数据
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            layoutParams.setMargins(15, 20, 15, 5)
            if (flowLayout != null) {
                flowLayout.removeAllViews()
            }
            for (i in data) {
                val tv = RoundTextView(this)
                tv.setPadding(28, 10, 28, 10)
                tv.text = i.nickname
                tv.maxEms = 10
                tv.setSingleLine()
                tv.textSize = 12f
                tv.setTextColor(ViewUtils.getColor(R.color.color_333333))
                tv.delegate.cornerRadius = 15
                tv.delegate.backgroundColor = ViewUtils.getColor(R.color.color_F5F7FA)
                tv.layoutParams = layoutParams
                tv.setOnClickListener {
                    if (!FastClickUtil.isFastClick()) {
                        Router.withApi(ApiRouter::class.java).toLive(i.id?:"1","",
                                i.nickname?:"未知","-1","-1",
                                "-1","-1","-1")

                    }
                }
                flowLayout.addView(tv, layoutParams)
            }
        }
    }


    private fun search(search_content: String) {
        showPageLoadingDialog()
        HomeApi.getSearchAnchor(search_content) {
            onSuccess {
                hidePageLoadingDialog()
               upDateView(it)
            }
            onFailed {
                ToastUtils.showToast(it.getMsg().toString())
                hidePageLoadingDialog()
            }
        }
    }


    //搜索结果
    private fun upDateView(data: HomeAnchorSearch) {
        setVisible(resultSearch)
        if (!data.result.isNullOrEmpty()) {
            setGone(tvNoResult)
            setVisible(searchResult)
            rvResult.removeAllViews()
            resultAdapter.clear()
            resultAdapter.notifyDataSetChanged()
            resultAdapter.refresh(data.result)

        }else{
            setVisible(tvNoResult)
            setGone(searchResult)
        }
        if (!data.related.isNullOrEmpty()) {
            rvRelated.removeAllViews()
            relatedAdapter.clear()
            relatedAdapter.notifyDataSetChanged()
            relatedAdapter.refresh(data.related)
        }
    }


  inner  class HomeHotLiveAdapter : BaseRecyclerAdapter<HomeHotLiveResponse>() {
        override fun getItemLayoutId(viewType: Int) = com.fh.module_base_resouce.R.layout.adapter_home_hot_live

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: HomeHotLiveResponse?) {
            try {
                holder.text(com.fh.module_base_resouce.R.id.tvHotLiveTitle, data?.name)
                holder.text(com.fh.module_base_resouce.R.id.tvHotLiveIntro, data?.live_intro)
                holder.text(com.fh.module_base_resouce.R.id.tvHotLiveName, data?.nickname)
                holder.text(com.fh.module_base_resouce.R.id.tvHotLiveNumber, data?.online.toString())
                GlideUtil.loadImage(data?.background, holder.getImageView(com.fh.module_base_resouce.R.id.tvHotLiveTitleBg))
                if (data?.tags != null && data.tags?.isNotEmpty()!!) {
                    holder.text(com.fh.module_base_resouce.R.id.tvHotLiveTag, data.tags?.get(0)?.title)
                    GlideUtil.loadImage(data.tags?.get(0)?.icon, holder.getImageView(com.fh.module_base_resouce.R.id.ivHotLiveTag))
                }
                GlideUtil.loadImage(data?.avatar, holder.getImageView(com.fh.module_base_resouce.R.id.ivHotLiveLogo))
                val ivHotLiveStatus = holder.getImageView(com.fh.module_base_resouce.R.id.ivHotLiveStatus)
                if (data?.live_status == "1") {
                    GlideUtil.loadGifImage(com.fh.module_base_resouce.R.drawable.ic_home_live_gif, ivHotLiveStatus)
                    ViewUtils.setVisible(ivHotLiveStatus)
                } else {
                    ViewUtils.setGone(ivHotLiveStatus)
                }
                holder.findView(com.fh.module_base_resouce.R.id.cardViewItem).setOnClickListener {
                    if (!FastClickUtil.isFastClick()) {
                        if (data?.is_sport == "0" ) {
                            Router.withApi(ApiRouter::class.java).toLive(
                                anchorId = data?.anchor_id ?: "-1",
                                lottery_id = data?.lottery_id ?: "-1",
                                nickname = data?.nickname.toString(),
                                live_status = data?.live_status ?: "-1",
                                online = data?.online.toString(),
                                r_id = data?.r_id ?: "-1",
                                name = data?.name.toString(),
                                avatar = data?.avatar.toString()
                            )
                        } else {
                            val sport = SportLiveInfo(
                                anchor_id = data?.anchor_id,
                                nickname = data?.nickname,
                                avatar = data?.avatar,
                                cover = data?.cover,
                                live_status = data?.live_status,
                                online = data?.online.toString(),
                                tags = data?.tags,
                                live_intro = data?.live_intro,
                                r_id = data?.r_id,
                                name = data?.name,
                                room_type = data?.room_type,
                                race_config = data?.race_config,
                                race_type = data?.race_type,
                                lottery_id = data?.lottery_id,
                                tagString = data?.tagString,
                                live_status_txt = data?.live_status_txt,
                                red_paper_num = data?.red_paper_num.toString(),
                                daxiu = data?.daxiu.toString(),
                                background = data?.background,
                                ext_img = data?.ext_img,
                                background_pc = data?.background_pc
                            )
                            Router.withApi(ApiRouter::class.java).toSportLive(sport)
                        }

                    }
                }
                if (data?.red_paper_num ?: 0 > 0) {
                    ViewUtils.setVisible(holder.findView(com.fh.module_base_resouce.R.id.imgTag))
                } else ViewUtils.setGone(holder.findView(com.fh.module_base_resouce.R.id.imgTag))
                if (data?.daxiu == true) {
                    ViewUtils.setVisible(holder.findView(com.fh.module_base_resouce.R.id.tiaodan))
                } else ViewUtils.setGone(holder.findView(com.fh.module_base_resouce.R.id.tiaodan))
            } catch (e: Exception) {
            }
        }


    }
}