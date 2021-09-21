package com.discountall

import androidx.viewpager.widget.ViewPager
import com.customer.data.AppChangeMode
import com.customer.data.UserInfoSp
import com.discountall.discount.DiscountFragment1
import com.discountall.task.DiscountFragment2
import com.fh.module_discount.R
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.base.fragment.BaseFragment
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ViewUtils
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_discount_all.*

/**
 *
 * @ Author  QinTian
 * @ Date  8/12/21
 * @ Describe
 *
 */
class DiscountFragmentAll : BaseMvpFragment<DiscountFragmentAllPresenter>(), ITheme, IMode {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = DiscountFragmentAllPresenter()

    override fun getLayoutResID() = R.layout.fragment_discount_all

    override fun isRegisterRxBus() = true

    var adapterPage: BaseFragmentPageAdapter? = null

    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(statusViewDisAll)
        setTheme(UserInfoSp.getThem())
        setMode(UserInfoSp.getAppMode())
        val list = arrayListOf<BaseFragment>(DiscountFragment1(), DiscountFragment2())
        adapterPage = BaseFragmentPageAdapter(childFragmentManager, list)
        vpDisAll.adapter = adapterPage
    }

    override fun initEvent() {
        vpDisAll.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    tv1.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    tv1.setTextColor(ViewUtils.getColor(R.color.white))
                    tv2.delegate.backgroundColor = ViewUtils.getColor(R.color.white)
                    tv2.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                } else {
                    tv2.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    tv2.setTextColor(ViewUtils.getColor(R.color.white))
                    tv1.delegate.backgroundColor = ViewUtils.getColor(R.color.white)
                    tv1.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                }
            }

        })
        tv1.setOnClickListener {
            vpDisAll.currentItem = 0
        }
        tv2.setOnClickListener {
            vpDisAll.currentItem = 1
        }
    }


    override fun setTheme(theme: Theme) {
    }

    override fun setMode(mode: AppMode) {
        when (mode) {
            AppMode.Normal -> {
                setVisible(topSwitch)
                setGone(titleDis)
                vpDisAll?.currentItem = 0
            }
            AppMode.Pure -> {
                setGone(topSwitch)
                setVisible(titleDis)
                vpDisAll?.currentItem = 0
            }
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