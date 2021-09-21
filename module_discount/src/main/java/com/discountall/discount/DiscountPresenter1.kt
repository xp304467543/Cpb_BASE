package com.discountall.discount

import com.customer.data.discount.DiscountApi
import com.customer.data.discount.DiscountContent
import com.customer.data.discount.DiscountTile
import com.customer.utils.JsonUtils
import com.google.gson.JsonParser
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_discount.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
class DiscountPresenter1 : BaseMvpPresenter<DiscountFragment1>() {


    fun getList(type:Int = 0,page:Int = 1){
        DiscountApi.getDisList(type,page) {
            onSuccess {
                if (mView.isActive()){
                    mView.setGone(mView.tHolder)
                    val json = JsonParser.parseString(it).asJsonObject
                    val data = json.getAsJsonArray("data")
                    val dataTitle = json.getAsJsonArray("typeList")
                    val beanTitle = JsonUtils.fromJson(dataTitle.toString(), Array<DiscountTile>::class.java)
                    val beanData = JsonUtils.fromJson(data.toString(), Array<DiscountContent>::class.java)
                    mView.initViewPager(beanTitle,beanData)
                }

            }
            onFailed {
                if (mView.isActive()){
                    mView.setVisible(mView.tHolder)
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }


}