package com.home.live.soprtchild

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.adapter.HomeHotLiveAdapter
import com.customer.base.BaseNormalFragment
import com.customer.data.home.SportBanner
import com.home.R
import com.home.adapter.BannerImageAdapter
import com.lib.basiclib.base.xui.widget.banner.widget.banner.BannerItem
import com.lib.basiclib.utils.ViewUtils
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import kotlinx.android.synthetic.main.fragment_wonderful_child.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/25/21
 * @ Describe
 *
 */
class LiveWonderfulChildFragment : BaseNormalFragment<LiveWonderfulChildFragmentPresenter>() {

    var contentAdapter: HomeHotLiveAdapter? = null

    var currentPage = 1

    var type = "0"

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveWonderfulChildFragmentPresenter()


    override fun getLayoutRes() = R.layout.fragment_wonderful_child

    override fun initContentView() {
        type = arguments?.getString("wonderfulType") ?: "0"
        //内容
        contentAdapter = HomeHotLiveAdapter()
        rvLiveContent.layoutManager = GridLayoutManager(context, 2)
        rvLiveContent.adapter = contentAdapter

        smartRefreshLayoutWonderFul.setOnRefreshListener {
            currentPage = 1
            mPresenter.getAll(true)
        }
        smartRefreshLayoutWonderFul.setOnLoadMoreListener {
            currentPage++
            mPresenter.getAll(false)
        }
    }

    override fun initData() {
        mPresenter.getAll(true)
        val name = arguments?.getString("wonderfulName") ?: ""
        val type = arguments?.getString("wonderfulType") ?: "0"
        if (name == "全部" || type == "0") mPresenter.getBanner("") else mPresenter.getBanner(
            arguments?.getString("wonderfulName") ?: ""
        )

    }


    fun initBanner(data: ArrayList<SportBanner>) {
        val list = ArrayList<BannerItem>()
        if (!data.isNullOrEmpty()) {
            for (i in data) {
                val item = BannerItem()
                item.imgUrl = i.image_url
                item.title = i.url
                list.add(item)
            }
        }else setGone(liveWonderfulBanner)
        if (liveWonderfulBanner != null) {
            liveWonderfulBanner?.adapter = BannerImageAdapter(list)
            val indicator = RectangleIndicator(context)
            liveWonderfulBanner?.indicator = indicator
            liveWonderfulBanner?.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            liveWonderfulBanner?.setIndicatorSelectedColor(ViewUtils.getColor(R.color.alivc_blue_1))
            liveWonderfulBanner?.setIndicatorNormalColor(ViewUtils.getColor(R.color.color_DDDDDD))
            liveWonderfulBanner?.addBannerLifecycleObserver(requireActivity())
        }
    }


    companion object {
        fun newInstance(data: String?, name: String?): LiveWonderfulChildFragment {
            val fragment = LiveWonderfulChildFragment()
            val bundle = Bundle()
            bundle.putString("wonderfulType", data)
            bundle.putString("wonderfulName", name)
            fragment.arguments = bundle
            return fragment
        }
    }
}