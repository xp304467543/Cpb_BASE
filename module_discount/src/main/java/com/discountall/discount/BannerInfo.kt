package com.discountall.discount

import com.fh.module_discount.R
import com.lib.basiclib.base.activity.BaseNavActivity
import kotlinx.android.synthetic.main.act_banner_info.*

/**
 *
 * @ Author  QinTian
 * @ Date  2021/9/20
 * @ Describe
 *
 */
class BannerInfo : BaseNavActivity() {

    var pageId = 0

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_banner_info

    override fun getPageTitle() = "优惠详情"

    override fun isShowBackIconWhite() = false

    override fun initContentView() {
        pageId = intent.getIntExtra("pageId",0)
        when(pageId){
            0 ->{
                setVisible(info1)
            }
            1 ->{
                setVisible(info2)
            }
            2 ->{
                setVisible(info3)
            }
            3 ->{
                setVisible(info4)
            }
        }
    }
}