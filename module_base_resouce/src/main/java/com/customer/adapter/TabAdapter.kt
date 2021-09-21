package com.customer.adapter

import android.content.Context
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import com.customer.data.home.HomeTab
import com.fh.module_base_resouce.R
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.IPagerIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.IPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.indicators.LinePagerIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.ColorFlipPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.ScaleTransitionPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.SimplePagerTitleView


/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/14
 * @ Describe
 *
 */
class TabScaleAdapter(
    private var titleList: ArrayList<HomeTab>,
    private var viewPage: ViewPager?,
    private var normalColor: Int,
    private var selectedColor: Int,
    private var colorLine: Int,
    private var textSize: Float = 18F,
    private var isChange: Boolean = true
) : CommonNavigatorAdapter() {
    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
        simplePagerTitleView.text = titleList[index].name
        simplePagerTitleView.textSize = textSize
        simplePagerTitleView.normalColor = normalColor
        simplePagerTitleView.selectedColor = selectedColor
        simplePagerTitleView.setOnClickListener {
            mListener?.invoke(titleList[index])
            if (titleList[index].code == "movie" || titleList[index].code == "hot") viewPage?.currentItem = index

        }
        return simplePagerTitleView
    }

    override fun getCount() = titleList.size

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_EXACTLY
        indicator.lineHeight = ViewUtils.dp2px(4F)
        indicator.lineWidth = ViewUtils.dp2px(15F)
        indicator.roundRadius = ViewUtils.dp2px(3F)
        indicator.startInterpolator = AccelerateInterpolator()
        indicator.endInterpolator = DecelerateInterpolator(2F)
        if (isChange) {
            indicator.setColors(TabThem.getTabSelect())
        } else indicator.setColors(colorLine)

        return indicator
    }

    private var mListener: ((str: HomeTab) -> Unit)? = null
    fun setConfirmClickListener(listener: (str: HomeTab) -> Unit) {
        mListener = listener
    }

    fun setNormalColor(color:Int){
        this.normalColor = color
    }
    fun setSelectColor(color:Int){
        this.selectedColor = color
    }
    fun setLineColor(color:Int){
        this.colorLine = color
    }
}

class TabScaleAdapterBet(
    private var titleList: ArrayList<String>,
    private var viewPage: ViewPager?,
    private var normalColor: Int,
    private var selectedColor: Int,
    private var colorLine: Int,
    private var textSize: Float = 18F,
    private var isChange: Boolean = true
) : CommonNavigatorAdapter() {
    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val simplePagerTitleView: SimplePagerTitleView = ScaleTransitionPagerTitleView(context)
        simplePagerTitleView.text = titleList[index]
        simplePagerTitleView.textSize = textSize
        simplePagerTitleView.normalColor = normalColor
        simplePagerTitleView.selectedColor = if (isChange)TabThem.getTabSelect() else selectedColor
        simplePagerTitleView.setOnClickListener {
            viewPage?.currentItem = index
        }
        return simplePagerTitleView
    }

    override fun getCount() = titleList.size

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_EXACTLY
        indicator.lineHeight = ViewUtils.dp2px(4F)
        indicator.lineWidth = ViewUtils.dp2px(15F)
        indicator.roundRadius = ViewUtils.dp2px(3F)
        indicator.startInterpolator = AccelerateInterpolator()
        indicator.endInterpolator = DecelerateInterpolator(2F)
        if (isChange) {
            indicator.setColors(TabThem.getTabSelect())
        } else indicator.setColors(colorLine)

        return indicator
    }
}


class TabNormalAdapter(
    private var titleList: ArrayList<String>,
    private var viewPage: ViewPager,
    private var colorTextNormal: Int,
    private var colorTextSelected: Int,
    private var colorLine: Int,
    private var textSize: Float = 14F
) : CommonNavigatorAdapter() {
    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val colorFlipPagerTitleView = ColorFlipPagerTitleView(context)
        colorFlipPagerTitleView.text = titleList[index]
        colorFlipPagerTitleView.textSize = textSize
        colorFlipPagerTitleView.normalColor = colorTextNormal
        colorFlipPagerTitleView.selectedColor = colorTextSelected
        colorFlipPagerTitleView.setOnClickListener {
            viewPage.currentItem = index
        }
        return colorFlipPagerTitleView
    }

    override fun getCount() = titleList.size

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_EXACTLY
        indicator.lineHeight = ViewUtils.dp2px(4F)
        indicator.lineWidth = ViewUtils.dp2px(15F)
        indicator.roundRadius = ViewUtils.dp2px(3F)
        indicator.startInterpolator = AccelerateInterpolator()
        indicator.endInterpolator = DecelerateInterpolator(2F)
        indicator.setColors(colorLine)
        return indicator
    }

    fun setNormalColor(color:Int){
        this.colorTextNormal = color
    }
    fun setSelectColor(color:Int){
        this.colorTextSelected = color
    }
    fun setLineColor(color:Int){
        this.colorLine = color
    }
}

class TabThemAdapter(
    private var titleList: ArrayList<String>,
    private var viewPage: ViewPager,
    private var colorTextNormal: Int,
    private var colorTextSelected: Int,
    private var textSize: Float = 14F
) : CommonNavigatorAdapter() {
    override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
        val colorFlipPagerTitleView = ColorFlipPagerTitleView(context)
        colorFlipPagerTitleView.text = titleList[index]
        colorFlipPagerTitleView.textSize = textSize
        colorFlipPagerTitleView.normalColor = colorTextNormal
        colorFlipPagerTitleView.selectedColor = colorTextSelected
        colorFlipPagerTitleView.setOnClickListener {
            viewPage.currentItem = index
        }
        return colorFlipPagerTitleView
    }

    override fun getCount() = titleList.size

    override fun getIndicator(context: Context?): IPagerIndicator {
        val indicator = LinePagerIndicator(context)
        indicator.mode = LinePagerIndicator.MODE_EXACTLY
        indicator.lineHeight = ViewUtils.dp2px(4F)
        indicator.lineWidth = ViewUtils.dp2px(15F)
        indicator.roundRadius = ViewUtils.dp2px(3F)
        indicator.startInterpolator = AccelerateInterpolator()
        indicator.endInterpolator = DecelerateInterpolator(2F)
        indicator.setColors(TabThem.getTabSelect())
        return indicator
    }
}

object TabThem {
    fun getTabSelect(): Int {
        return ViewUtils.getColor(R.color.alivc_blue_uefa)
//
//        if (UserInfoSp.getAppMode() == AppMode.Normal){
//            when (UserInfoSp.getThemInt()) {
//                1 -> ViewUtils.getColor(R.color.color_FF513E)
//                2 -> ViewUtils.getColor(R.color.color_FF513E)
//                3 -> ViewUtils.getColor(R.color.colorGreenPrimary)
//                4 -> ViewUtils.getColor(R.color.purple)
//                5 -> ViewUtils.getColor(R.color.color_EF7E12)
//                6 -> ViewUtils.getColor(R.color.color_SD)
//                7 -> ViewUtils.getColor(R.color.color_FF513E)
//                8 -> ViewUtils.getColor(R.color.alivc_blue_uefa)
//                else -> ViewUtils.getColor(R.color.color_FF513E)
//            }
//        }else ViewUtils.getColor(R.color.color_FF513E)
    }
}

