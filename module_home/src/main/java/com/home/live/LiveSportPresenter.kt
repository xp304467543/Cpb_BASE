package com.home.live
import android.content.Context
import android.graphics.Typeface
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import com.customer.data.home.HomeApi
import com.customer.data.home.LiveTypeObject
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.MagicIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.IPagerIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.IPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.indicators.LinePagerIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.SimplePagerTitleView
import kotlinx.android.synthetic.main.fragment_home_live_child.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/15/21
 * @ Describe
 *
 */
class LiveSportPresenter : BaseMvpPresenter<LiveSportFragment>() {

    fun getLiveType() {
        HomeApi.getLiveType {
            onSuccess {
                if (mView.isActive()) {
                    mView.typeData = it
                    mView.initType(it)
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }


    class TabScaleAdapter(
        private var tab: MagicIndicator,
        private var vp:ViewPager,
        private var titleList: ArrayList<LiveTypeObject>,
        private var normalColor: Int,
        private var selectedColor: Int,
        private var colorLine: Int
    ) : CommonNavigatorAdapter() {
        override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
            val simplePagerTitleView: SimplePagerTitleView =
                object : ScaleTransitionPagerTitleView(context) {
                    override fun onSelected(index: Int, totalCount: Int) {
                        super.onSelected(index, totalCount)
                        typeface = Typeface.DEFAULT_BOLD
                        textSize = 14f
                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        super.onDeselected(index, totalCount)

                        typeface = Typeface.SERIF
                        textSize = 17f
                    }
                }
            simplePagerTitleView.text = titleList[index].name_cn

            simplePagerTitleView.normalColor = normalColor
            simplePagerTitleView.selectedColor = selectedColor
            simplePagerTitleView.textSize = 13f
            simplePagerTitleView.setOnClickListener {
                vp.currentItem = index
                tab.onPageSelected(index)
                tab.onPageScrolled(index, 0.0F, 0)
                mListener?.invoke(titleList[index])
            }

            return simplePagerTitleView
        }


        override fun getCount() = titleList.size

        override fun getIndicator(context: Context?): IPagerIndicator {
            val indicator = LinePagerIndicator(context)
            indicator.mode = LinePagerIndicator.MODE_EXACTLY
            indicator.lineHeight = ViewUtils.dp2px(2F)
            indicator.lineWidth = ViewUtils.dp2px(15F)
            indicator.roundRadius = ViewUtils.dp2px(3F)
            indicator.startInterpolator = AccelerateInterpolator()
            indicator.endInterpolator = DecelerateInterpolator(2F)
            indicator.setColors(colorLine)
            return indicator
        }

        private var mListener: ((str: LiveTypeObject) -> Unit)? = null
        fun setConfirmClickListener(listener: (str: LiveTypeObject) -> Unit) {
            mListener = listener
        }

        fun setNormalColor(color: Int) {
            this.normalColor = color
        }

        fun setSelectColor(color: Int) {
            this.selectedColor = color
        }

        fun setLineColor(color: Int) {
            this.colorLine = color
        }
    }


}