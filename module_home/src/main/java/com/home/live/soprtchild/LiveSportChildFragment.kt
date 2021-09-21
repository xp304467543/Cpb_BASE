package com.home.live.soprtchild

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.ApiRouter
import com.customer.base.BaseNormalFragment
import com.customer.data.home.LiveTypeObject
import com.customer.data.home.SportBanner
import com.customer.data.home.SportLive
import com.customer.data.home.SportLiveInfo
import com.glide.GlideUtil
import com.home.R
import com.home.adapter.BannerImageAdapter
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.widget.banner.widget.banner.BannerItem
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import kotlinx.android.synthetic.main.fragment_home_live_child.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/15/21
 * @ Describe
 *
 */

class LiveSportChildFragment : BaseNormalFragment<LiveSportChildPresenter>() {

    var currentPage = 1

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveSportChildPresenter()


    override fun getLayoutRes() = R.layout.fragment_home_live_child


    override fun initData() {
        mPresenter.getSportBanner(arguments?.getParcelable<LiveTypeObject>("LiveTypeObject")?.type_name.toString())
        mPresenter.getSportLive(arguments?.getParcelable<LiveTypeObject>("LiveTypeObject")?.type_name.toString(), currentPage,true)
    }

    override fun initContentView() {
        liveRefresh?.setEnableRefresh(true)//是否启用下拉刷新功能
        liveRefresh?.setEnableLoadMore(false)//是否启用上拉加载功能
        liveRefresh?.setEnableOverScrollBounce(true)//是否启用越界回弹
        liveRefresh?.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        initSportAdapter()
    }

    override fun initEvent() {
        liveRefresh?.setOnRefreshListener {
            currentPage = 1
            mPresenter.getSportLive(arguments?.getParcelable<LiveTypeObject>("LiveTypeObject")?.type_name.toString(), currentPage,true)
        }
//        liveRefresh?.setOnLoadMoreListener {
//            currentPage++
//            mPresenter.getSportLive(arguments?.getParcelable<LiveTypeObject>("LiveTypeObject")?.type_name.toString(), currentPage,false)
//        }
    }

    fun upDateBanner(data: ArrayList<SportBanner>?) {
        val list = ArrayList<BannerItem>()
        if (!data.isNullOrEmpty()) {
            for (i in data) {
                val item = BannerItem()
                item.imgUrl = i.image_url
                item.title = i.url
                list.add(item)
            }
        }
        if (liveBanner != null) {
            liveBanner?.adapter = BannerImageAdapter(list)
            val indicator = RectangleIndicator(context)
            liveBanner?.indicator = indicator
            liveBanner?.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            liveBanner?.setIndicatorSelectedColor(ViewUtils.getColor(R.color.alivc_blue_1))
            liveBanner?.setIndicatorNormalColor(ViewUtils.getColor(R.color.color_DDDDDD))
            liveBanner?.addBannerLifecycleObserver(requireActivity())
        }
    }

    var sportLiveAdapter:AdapterSportLive?=null

    private fun initSportAdapter(){
        sportLiveAdapter = AdapterSportLive()
        rvLive.adapter = sportLiveAdapter
        rvLive.layoutManager = GridLayoutManager(context, 2)
    }

    fun initSportLive(data: SportLive?,isRefresh:Boolean) {
     if (isRefresh)   sportLiveAdapter?.refresh(data?.list) else sportLiveAdapter?.loadMore(data?.list)
    }


    inner class AdapterSportLive : BaseRecyclerAdapter<SportLiveInfo>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_soprt_live

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: SportLiveInfo?) {
            holder.text(R.id.tvSportDate,data?.race_config?.matchdate)
            holder.text(R.id.tvTime, TimeUtils.date2TimeStampOnlyTime(data?.race_config?.matchtime))
            holder.text(R.id.tvNameLeft, data?.race_config?.homesxname)
            holder.text(R.id.tvNameRight, data?.race_config?.awaysxname)
            holder.text(R.id.tvSportLiveIntro, data?.race_config?.simpleleague+"-"+data?.race_config?.stagename)
            holder.text(R.id.tvHotLiveTag,arguments?.getParcelable<LiveTypeObject>("LiveTypeObject")?.name_cn.toString())
            holder.text(R.id.tvSportLiveName,data?.nickname)
            holder.text(R.id.tvSportLiveNumber,data?.online)
            val ivHotLiveStatus = holder.getImageView(R.id.ivSportLiveStatus)
            try {
                if (data?.live_status == "1") {
                    GlideUtil.loadGifImage(R.drawable.ic_home_live_gif, ivHotLiveStatus)
                    ViewUtils.setVisible(ivHotLiveStatus)
                } else {
                    ViewUtils.setGone(ivHotLiveStatus)
                }
                context?.let { GlideUtil.loadImageBanner(it,data?.avatar,holder.getImageView(R.id.ivHotLiveLogo)) }
               //加载球队图标
                context?.let { GlideUtil.loadSportLiveIcon(it,data?.race_config?.homelogo,holder.getImageView(R.id.imgLeft)) }
                context?.let { GlideUtil.loadSportLiveIcon(it,data?.race_config?.awaylogo,holder.getImageView(R.id.imgRight)) }
            }catch (e:Exception){
                e.printStackTrace()
            }
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()){
                    data?.let { it1 -> Router.withApi(ApiRouter::class.java).toSportLive(it1) }
                }
            }
        }
    }

    companion object {
        fun newInstance(data: LiveTypeObject?): LiveSportChildFragment {
            val fragment = LiveSportChildFragment()
            val bundle = Bundle()
            bundle.putParcelable("LiveTypeObject", data)
            fragment.arguments = bundle
            return fragment
        }
    }

}