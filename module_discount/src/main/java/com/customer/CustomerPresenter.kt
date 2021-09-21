package com.customer

import com.customer.data.discount.DiscountApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils

/**
 *
 * @ Author  QinTian
 * @ Date  6/9/21
 * @ Describe
 *
 */
class CustomerPresenter : BaseMvpPresenter<CustomerFragment>() {

    fun getInfo(type: Int) {
        DiscountApi.getCustomerQuestion(type) {
            onSuccess {
                if (mView.isActive()) {
                    when (type) {
                        1 -> {
                            mView.initM1(it)
                        }
                        2 -> {
                            mView.initM2(it)
                        }
                        3 -> {
                            mView.initM3(it)
                        }
                        4 -> {
                            mView.initM4(it)
                        }
                    }
                }
            }
            onFailed {
                if (mView.isActive()) {
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }
}