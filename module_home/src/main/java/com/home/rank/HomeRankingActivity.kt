package com.home.rank

import androidx.fragment.app.Fragment
import com.customer.adapter.TabScaleAdapterBet
import com.customer.data.AppChangeMode
import com.customer.data.UserInfoSp
import com.customer.utils.WindowPermissionCheck
import com.home.R
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.activity_ranking.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/22/21
 * @ Describe
 *
 */
class HomeRankingActivity : BaseMvpActivity<HomeRankingPresenter>(), ITheme, IMode {
    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeRankingPresenter()

    override fun getPageTitle() = "排行"

    override fun isShowBackIconWhite() = false

    override fun isSwipeBackEnable() = true

    override fun isRegisterRxBus() = true

    override fun getContentResID() = R.layout.activity_ranking

    private var tabAdapter: TabScaleAdapterBet? = null
    private var commonNavigator: CommonNavigator? = null

    override fun initContentView() {
        setTheme(UserInfoSp.getThem())
        setMode(UserInfoSp.getAppMode())
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        val pos = if (UserInfoSp.getAppMode() == AppMode.Normal) {
            if (UserInfoSp.getNobleLevel() > 0) 4 else 3
        } else 2
        repeat(pos) {
            fragments.add(HomeRankChildFragment.newInstance(it))
        }
        val adapter = BaseFragmentPageAdapter(supportFragmentManager, fragments)
        vpRanking?.adapter = adapter
        vpRanking.offscreenPageLimit = fragments.size
        initTopTab()

    }


    private fun initTopTab() {
        val mDataList = if (UserInfoSp.getAppMode() == AppMode.Normal) {
            if (UserInfoSp.getNobleLevel() > 0) {
                arrayListOf("直播榜", "推广榜", "竞猜榜", "影片榜")
            } else arrayListOf("直播榜", "推广榜", "竞猜榜")
        } else {
            arrayListOf("推广榜", "竞猜榜")
        }


        commonNavigator = CommonNavigator(this)
        commonNavigator?.scrollPivotX = 0.65f
        tabAdapter = TabScaleAdapterBet(
            titleList = mDataList,
            viewPage = vpRanking,
            normalColor = ViewUtils.getColor(R.color.alivc_blue_2),
            selectedColor = ViewUtils.getColor(R.color.alivc_blue_1),
            colorLine = ViewUtils.getColor(R.color.alivc_blue_1),
            textSize = 14F,
            isChange = false
        )
        commonNavigator?.adapter = tabAdapter
        commonNavigator?.isAdjustMode = true
        vpRanking.offscreenPageLimit = 4
        vipSwitchTab.navigator = commonNavigator
        ViewPagerHelper.bind(vipSwitchTab, vpRanking)
    }

    override fun setMode(mode: AppMode) {

    }

    override fun setTheme(theme: Theme) {
    }

    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: AppChangeMode) {
        this@HomeRankingActivity.finish()
    }
}