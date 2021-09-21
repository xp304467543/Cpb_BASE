package com.mine.children.noble

import androidx.fragment.app.Fragment
import com.customer.adapter.TabScaleAdapterBet
import com.customer.data.mine.NobleInfo
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import com.mine.R
import kotlinx.android.synthetic.main.act_mine_noble.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/20/21
 * @ Describe
 *
 */
class MineNobleActivity : BaseMvpActivity<MineNobleActivityPresenter>() {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineNobleActivityPresenter()

    override fun isShowToolBar() = false

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_mine_noble

    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(noblePageStateView)
    }

    override fun initData() {
        showPageLoadingDialog()
        mPresenter.getNobleInfo()
    }

    override fun initEvent() {
        nobleBack.setOnClickListener {
            finish()
        }
    }

    fun initViewPager(data: NobleInfo) {
        if (vpNoble != null && !data.noble_list.isNullOrEmpty()) {
            val fragments = arrayListOf<Fragment>()
            for ((index, item) in data.noble_list?.withIndex()!!) {
                fragments.add(MineNobleChildFragment.newInstance(data,index,item.code))
            }
            val adapter = BaseFragmentPageAdapter(supportFragmentManager, fragments)
            vpNoble?.adapter = adapter
            vpNoble.offscreenPageLimit  = fragments.size
            val tabs = arrayListOf<String>()
            for (string in data.noble_list!!) {
                tabs.add(string.name ?: "未知")
            }
            initTopTab(tabs, data.user_growth?.noble ?: 0)
        }
    }

    private var tabAdapter: TabScaleAdapterBet? = null
    private var commonNavigator: CommonNavigator? = null
    private fun initTopTab(mDataList: ArrayList<String>, current: Int) {
        if (vpNoble != null) {
            commonNavigator = CommonNavigator(this)
            commonNavigator?.scrollPivotX = 0.65f
            tabAdapter = TabScaleAdapterBet(
                titleList = mDataList,
                viewPage = vpNoble,
                normalColor = ViewUtils.getColor(R.color.color_999999),
                selectedColor = ViewUtils.getColor(R.color.color_DBB564),
                colorLine = ViewUtils.getColor(R.color.color_DBB564),
                textSize = 15F,
                isChange = false
            )
            commonNavigator?.adapter = tabAdapter
            vpNoble.offscreenPageLimit = 7
            nobleTab.navigator = commonNavigator
            ViewPagerHelper.bind(nobleTab, vpNoble)
            vpNoble?.currentItem = mDataList.size - current
        }
    }
}