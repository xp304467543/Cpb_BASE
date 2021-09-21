package com.discountall.discount

import androidx.fragment.app.Fragment
import com.customer.ApiRouter
import com.customer.adapter.TabScaleAdapterBet
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.data.AppChangeMode
import com.customer.data.UserInfoSp
import com.customer.data.discount.DiscountContent
import com.customer.data.discount.DiscountTile
import com.customer.data.mine.ChangeSkin
import com.fh.module_discount.R
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_discount.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
@RouterAnno(host = "Discount", path = "main")
class DiscountFragment1 : BaseMvpFragment<DiscountPresenter1>(), ITheme, IMode {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() =
        DiscountPresenter1()

    override fun isRegisterRxBus() = true

    override fun getLayoutResID() = R.layout.fragment_discount


    override fun initContentView() {
        setMode(UserInfoSp.getAppMode())
    }

    override fun initData() {
        mPresenter.getList()
    }

    override fun initEvent() {
        tHolder.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                mPresenter.getList()
            }
        }
        userTask.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(requireActivity())
                    return@setOnClickListener
                }
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                Router.withApi(ApiRouter::class.java).toUserTask()
            }
        }
    }

    fun initViewPager(data: Array<DiscountTile>, content: Array<DiscountContent>) {
        if (vpDiscount != null) {
            val fragments = arrayListOf<Fragment>()
            for (item in data) {
                fragments.add(DiscountFragmentChild1.newInstance(item.type ?: 0, content))
            }
            val adapter = BaseFragmentPageAdapter(childFragmentManager, fragments)
            vpDiscount?.adapter = adapter
            val list = arrayListOf<String>()
            for (item in data) {
                list.add(item.name ?: "未知")
            }
            initTab(list)
        }
    }

    private var tabAdapter: TabScaleAdapterBet? = null
    private var commonNavigator: CommonNavigator? = null
    private fun initTab(mDataList: ArrayList<String>) {
        if (vpDiscount != null) {
            commonNavigator = CommonNavigator(context)
            commonNavigator?.scrollPivotX = 0.65f
            tabAdapter = TabScaleAdapterBet(
                titleList = mDataList,
                viewPage = vpDiscount,
                normalColor = ViewUtils.getColor(R.color.alivc_blue_2),
                selectedColor = ViewUtils.getColor(R.color.alivc_blue_1),
                colorLine = ViewUtils.getColor(R.color.color_333333),
                textSize = 14F
            )
            commonNavigator?.adapter = tabAdapter
            vpDiscount.offscreenPageLimit = 7
            discountSwitchVideoTab.navigator = commonNavigator
            ViewPagerHelper.bind(discountSwitchVideoTab, vpDiscount)
        }
    }

    override fun setTheme(theme: Theme) {
//        when (theme) {
//            Theme.Default -> {
//                disBg.setImageResource(0)
//                names.setTextColor(ViewUtils.getColor(R.color.color_333333))
//            }
//            Theme.NewYear -> {
//                disBg.setImageResource(R.drawable.ic_them_newyear_top)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.MidAutumn -> {
//                disBg.setImageResource(R.drawable.ic_them_middle_top)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//
//            }
//            Theme.LoverDay -> {
//                disBg.setImageResource(R.drawable.ic_them_love_top)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.NationDay -> {
//                disBg.setImageResource(R.drawable.ic_them_gq_top)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.ChristmasDay -> {
//                disBg.setImageResource(R.drawable.ic_them_sd_top)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.OxYear -> {
//                disBg.setImageResource(R.drawable.ic_nn_bg)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.Uefa -> {
//                disBg.setImageResource(R.drawable.ic_bg_uefa)
//                names.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//
//        }
//        tabAdapter?.notifyDataSetChanged()
    }

    override fun setMode(mode: AppMode) {
        if (mode == AppMode.Pure) {
//            setTheme(Theme.Default)
        } else {
            setTheme(UserInfoSp.getThem())
        }
    }

    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeMode(eventBean: AppChangeMode) {
        if (isActive()) {
            setMode(eventBean.mode)
        }
    }

    //换肤
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: ChangeSkin) {
//        when (eventBean.id) {
//            1 -> setTheme(Theme.Default)
//            2 -> setTheme(Theme.NewYear)
//            3 -> setTheme(Theme.MidAutumn)
//            4 -> setTheme(Theme.LoverDay)
//            5 -> setTheme(Theme.NationDay)
//            6 -> setTheme(Theme.ChristmasDay)
//            7 -> setTheme(Theme.OxYear)
//            8 -> setTheme(Theme.Uefa)
//        }

    }

}