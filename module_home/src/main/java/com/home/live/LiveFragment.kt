package com.home.live

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.customer.ApiRouter
import com.customer.component.dialog.DialogGlobalTips
import com.customer.component.dialog.GlobalDialog
import com.customer.data.AppChangeMode
import com.customer.data.LoginOut
import com.customer.data.MineUserScanLoginOut
import com.customer.data.UserInfoSp
import com.customer.data.login.LoginSuccess
import com.customer.data.mine.MineApi
import com.home.R
import com.home.live.search.LiveSearchActivity
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_live.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/14/21
 * @ Describe
 *
 */
class LiveFragment : BaseMvpFragment<LivePresenter>(), ITheme, IMode {

    var currentSel = 0  // 0 体育 1 精彩播 2影视

    var isShowMovie = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LivePresenter()

    override fun getLayoutResID() = R.layout.fragment_live

    override fun isRegisterRxBus() = true

    override fun onSupportVisible() {
        super.onSupportVisible()
        getOther()
        if (UserInfoSp.getIsLogin()) {
            when (UserInfoSp.getNobleLevel()) {
                1 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v1)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                2 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v2)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                3 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v3)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                4 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v4)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                5 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v5)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                6 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v6)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                7 -> {
                    imgLevel.setImageResource(R.mipmap.ic_home_v7)
                    if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
                }
                else -> imgLevel.setImageResource(R.mipmap.ic_home_live_level)
            }
        } else {
            imgLevel.setImageResource(R.mipmap.ic_home_live_level)
            setGone(tvLiveMovie)
        }
    }


    override fun initContentView() {
        setSwipeBackEnable(false)
        StatusBarUtils.setStatusBarHeight(statusViewLive)
    }

    var vpAdapter: BaseFragmentPageAdapter? = null
    fun initViewPager(boolean: Boolean) {
        currentSel = 0
        isShowMovie = boolean
        val fragmentList = if (isShowMovie && UserInfoSp.getNobleLevel() > 0) {
            if (UserInfoSp.getAppMode() == AppMode.Normal) setVisible(tvLiveMovie)
            arrayListOf<Fragment>(
                LiveSportFragment(),
                LiveWonderfulFragment(),
                LiveVideoFragment()
            )
        } else {
            setGone(tvLiveMovie)
            arrayListOf<Fragment>(LiveSportFragment(), LiveWonderfulFragment())
        }
        vpAdapter = BaseFragmentPageAdapter(childFragmentManager, fragmentList)
        vpLive.adapter = vpAdapter
        vpLive.offscreenPageLimit = 5
        vpLive.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                currentSel = position
                changeTitle()
            }
        })
        changeTitle()
        setMode(UserInfoSp.getAppMode())
        setTheme(UserInfoSp.getThem())
    }


    override fun initData() {
        mPresenter.getNav()
    }

    override fun initEvent() {
        tvLiveSport.setOnClickListener {
            if (currentSel != 0) {
                currentSel = 0
                changeTitle()
                vpLive.currentItem = 0
            }
        }
        tvLiveHot.setOnClickListener {
            if (currentSel != 1) {
                currentSel = 1
                changeTitle()
                vpLive.currentItem = 1
            }
        }
        tvLiveMovie.setOnClickListener {
            if (currentSel != 2) {
                currentSel = 2
                changeTitle()
                vpLive.currentItem = 2
            }
        }

        imgSearch.setOnClickListener {
            if (currentSel != 2) {
                val intent = Intent(getPageActivity(), LiveSearchActivity::class.java)
                intent.putExtra("currentSel", currentSel)
                startActivity(intent)
            } else Router.withApi(ApiRouter::class.java).toVideoSearch()
        }
    }


    private fun changeTitle() {
        when (currentSel) {
            0 -> {
                tvLiveSport.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                tvLiveHot.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
                tvLiveMovie.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
                tvLiveSport.textSize = 15f
                tvLiveHot.textSize = 13f
                tvLiveMovie.textSize = 13f
                tvLiveSport.typeface = Typeface.DEFAULT_BOLD
                tvLiveHot.typeface = Typeface.DEFAULT
                tvLiveMovie.typeface = Typeface.DEFAULT
            }
            1 -> {
                tvLiveSport.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
                tvLiveHot.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                tvLiveMovie.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
                tvLiveSport.textSize = 13f
                tvLiveHot.textSize = 15f
                tvLiveMovie.textSize = 13f
                tvLiveSport.typeface = Typeface.DEFAULT
                tvLiveHot.typeface = Typeface.DEFAULT_BOLD
                tvLiveMovie.typeface = Typeface.DEFAULT
            }
            2 -> {
                tvLiveSport.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
                tvLiveHot.setTextColor(ViewUtils.getColor(R.color.alivc_blue_2))
                tvLiveMovie.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                tvLiveSport.textSize = 13f
                tvLiveHot.textSize = 13f
                tvLiveMovie.textSize = 15f
                tvLiveSport.typeface = Typeface.DEFAULT
                tvLiveHot.typeface = Typeface.DEFAULT
                tvLiveMovie.typeface = Typeface.DEFAULT_BOLD
            }
        }
    }


    override fun setTheme(theme: Theme) {
    }

    override fun setMode(mode: AppMode) {
        when (mode) {
            AppMode.Normal -> {
                setVisible( tvLiveHot)
                if (isShowMovie && UserInfoSp.getNobleLevel() > 0) {
                    setVisible(tvLiveMovie)
                }
            }
            AppMode.Pure -> {
                currentSel = 0
                changeTitle()
                vpLive?.currentItem = 0
                setGone(tvLiveHot)
                setGone(tvLiveMovie)
            }
        }
    }


    private fun getOther() {
        MineApi.getUserDiamond {
            onFailed {
                if (it.getCode() == 2003) {
                    if (isActive()) {
                        val dialog = context?.let { it1 ->
                            DialogGlobalTips(
                                it1,
                                "登录提醒",
                                "确定",
                                "",
                                "您的账号已在其他设备登录\n" + "如非本人请联系客服"
                            )
                        }
                        dialog?.setOnDismissListener {
                            if (tvLiveMovie.visibility == View.VISIBLE) initViewPager(false)
                        }
                    }

                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun login(eventBean: LoginSuccess) {
        if (UserInfoSp.getNobleLevel() > 0) setVisible(tvLiveMovie) else setGone(tvLiveMovie)
        initViewPager(true)
    }

    //退出登录
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginOut(eventBean: LoginOut) {
        if (isActive()) {
            if (tvLiveMovie.visibility == View.VISIBLE) initViewPager(false)
        }
    }

    //扫码登录后退出
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun scanLoginOut(eventBean: MineUserScanLoginOut) {
        if (isActive()) {
            if (tvLiveMovie.visibility == View.VISIBLE) initViewPager(false)
        }

    }


    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: AppChangeMode) {
        if (isAdded) {

            setMode(eventBean.mode)

        }
    }

}