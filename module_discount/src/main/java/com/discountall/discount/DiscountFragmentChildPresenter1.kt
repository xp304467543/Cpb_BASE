package com.discountall.discount

import com.customer.data.discount.DiscountApi
import com.customer.data.discount.DiscountContent
import com.customer.utils.JsonUtils
import com.google.gson.JsonParser
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragmen_child_discount.*
import kotlinx.android.synthetic.main.fragment_discount.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
class DiscountFragmentChildPresenter1 : BaseMvpPresenter<DiscountFragmentChild1>() {

    fun getList(type:Int = 0,page:Int = 1,isRefresh:Boolean = true){
        DiscountApi.getDisList(type,page) {
            onSuccess {
                if (mView.isActive()){
                    mView.setGone(mView.tHolder)
                    val json = JsonParser.parseString(it).asJsonObject
                    val data = json.getAsJsonArray("data")
                    val beanData = (JsonUtils.fromJson(data.toString(), Array<DiscountContent>::class.java)).toMutableList()
                    if (!beanData.isNullOrEmpty()){
                        mView.setGone(mView.tvHolder)
                      if (isRefresh) {
                          if (type == 0){
                              beanData.add(0,DiscountContent(specialId = 1))
                              beanData.add(1,DiscountContent(specialId = 2))
                              beanData.add(2,DiscountContent(specialId = 3))
                              beanData.add(3,DiscountContent(specialId = 4))
                          }
                          mView.discountAdapter?.refresh(beanData)
                          mView.discountSmartRefreshLayout?.finishRefresh()
                      } else  {
                          mView.discountAdapter?.loadMore(beanData)
                          mView.discountSmartRefreshLayout?.finishLoadMore()
                      }
                    }else{
                        if (isRefresh){
                            mView.discountAdapter?.clear()
                            mView.setVisible(mView.tvHolder)
                            mView.discountSmartRefreshLayout?.finishRefresh()
                        }else{
                            mView.discountSmartRefreshLayout?.finishLoadMoreWithNoMoreData()
                            mView.setGone(mView.tvHolder)
                        }

                    }
                }

            }
            onFailed {
                if (mView.isActive()){
                    mView.setVisible(mView.tHolder)
                    ToastUtils.showToast(it.getMsg())
                    mView.discountSmartRefreshLayout?.finishRefresh()
                    mView.discountSmartRefreshLayout?.finishLoadMore()
                }
            }
        }
    }

}