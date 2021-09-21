package com.home.live

import android.os.Bundle
import com.customer.base.BaseNormalFragment
import com.customer.data.home.LiveTypeObject
import com.home.R
import com.home.live.soprtchild.LiveSportChildFragment
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import kotlinx.android.synthetic.main.fragment_sport_live.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/15/21
 * @ Describe
 *
 */

class LiveSportFragment : BaseNormalFragment<LiveSportPresenter>() {

    var typeData: List<LiveTypeObject>? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveSportPresenter()


    override fun getLayoutRes() = R.layout.fragment_sport_live


    override fun initData() {
       mPresenter.getLiveType()
    }

    override fun initContentView() {

    }

    override fun initEvent() {

    }



    var topAdapter: LiveSportPresenter.TabScaleAdapter? = null
    fun initType(data: ArrayList<LiveTypeObject>?) {
        val fragmentList = arrayListOf<LiveSportChildFragment>()
        if (data.isNullOrEmpty())return
        for (item in data){
            fragmentList.add(LiveSportChildFragment.newInstance(item))
        }
        val adapter = BaseFragmentPageAdapter(childFragmentManager, fragmentList)
        vpSportLive.adapter = adapter
        val commonNavigator = CommonNavigator(context)
        commonNavigator.scrollPivotX = 0.65f
        topAdapter = data.let {
            LiveSportPresenter. TabScaleAdapter(
                tab =liveTab,
                vp = vpSportLive,
                titleList = it,
                normalColor = ViewUtils.getColor(R.color.alivc_blue_2),
                selectedColor = ViewUtils.getColor(R.color.alivc_blue_1),
                colorLine = ViewUtils.getColor(R.color.alivc_blue_1)
            )
        }
        commonNavigator.adapter = topAdapter
        liveTab.navigator = commonNavigator
        ViewPagerHelper.bind(liveTab, vpSportLive)
    }


    companion object {
        fun newInstance(data: LiveTypeObject?): LiveSportFragment {
            val fragment = LiveSportFragment()
            val bundle = Bundle()
            bundle.putParcelable("LiveTypeObject", data)
            fragment.arguments = bundle
            return fragment
        }
    }

}