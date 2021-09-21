package com.mine.children.movie

import androidx.viewpager.widget.ViewPager
import com.customer.data.HomeJumpToMine
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.anno.RouterAnno
import cuntomer.PriseBean
import cuntomer.PriseViewUtils
import kotlinx.android.synthetic.main.act_movie_history.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/23/21
 * @ Describe
 *
 */
@RouterAnno(host = "Mine", path = "moviebuy")
class MineMovieActivity : BaseNavActivity() {

    var currentSelect = 0

    override fun getContentResID() = R.layout.act_movie_history

    override fun isSwipeBackEnable() = true

    override fun getPageTitle() = "我的观影"

    override fun isShowBackIconWhite() = false

    override fun isRegisterRxBus() = true


    override fun initContentView() {
        val fragments = arrayListOf(
            MineMovieChildFragment.newInstance(0), MineMovieChildFragment.newInstance(1)
            , MineMovieChildFragment.newInstance(2)
        )
        val adapter = BaseFragmentPageAdapter(supportFragmentManager, fragments)
        vpMovie?.adapter = adapter
        vpMovie.offscreenPageLimit = fragments.size
        vpMovie.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        tvMovie1.setBackgroundResource(R.drawable.button_blue_background)
                        tvMovie2.setBackgroundResource(R.drawable.button_grey_background)
                        tvMovie3.setBackgroundResource(R.drawable.button_grey_background)
                        tvMovie1.setTextColor(ViewUtils.getColor(R.color.white))
                        tvMovie2.setTextColor(ViewUtils.getColor(R.color.color_333333))
                        tvMovie3.setTextColor(ViewUtils.getColor(R.color.color_333333))
                    }
                    1 -> {
                        tvMovie1.setBackgroundResource(R.drawable.button_grey_background)
                        tvMovie2.setBackgroundResource(R.drawable.button_blue_background)
                        tvMovie3.setBackgroundResource(R.drawable.button_grey_background)
                        tvMovie1.setTextColor(ViewUtils.getColor(R.color.color_333333))
                        tvMovie2.setTextColor(ViewUtils.getColor(R.color.white))
                        tvMovie3.setTextColor(ViewUtils.getColor(R.color.color_333333))
                    }
                    2 -> {
                        tvMovie1.setBackgroundResource(R.drawable.button_grey_background)
                        tvMovie2.setBackgroundResource(R.drawable.button_grey_background)
                        tvMovie3.setBackgroundResource(R.drawable.button_blue_background)
                        tvMovie1.setTextColor(ViewUtils.getColor(R.color.color_333333))
                        tvMovie2.setTextColor(ViewUtils.getColor(R.color.color_333333))
                        tvMovie3.setTextColor(ViewUtils.getColor(R.color.white))
                    }
                }
            }

        })
    }

    override fun initData() {

    }

    override fun initEvent() {
        tvMovie1.setOnClickListener {
            currentSelect = 0
            vpMovie.currentItem = currentSelect

        }
        tvMovie2.setOnClickListener {
            currentSelect = 1
            vpMovie.currentItem = currentSelect
        }
        tvMovie3.setOnClickListener {
            currentSelect = 2
            vpMovie.currentItem = currentSelect
        }
    }


    /**
     * 跳转mine
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMine(clickMine: HomeJumpToMine) {
        finish()
    }
}