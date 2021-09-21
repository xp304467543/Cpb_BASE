package com.service

import com.customer.CustomerFragment
import com.discountall.DiscountFragmentAll
import com.discountall.discount.DiscountFragment1
import com.lib.basiclib.base.fragment.BaseFragment
import com.services.DiscountService
import com.xiaojinzi.component.anno.ServiceAnno

/**
 *
 * @ Author  QinTian
 * @ Date  2020/5/22
 * @ Describe
 *
 */
@ServiceAnno(value = [DiscountService::class], singleTon = true)
open class DiscountServiceImp : DiscountService {

    override fun getDiscountFragment(): BaseFragment {
        return DiscountFragmentAll()
    }

    override fun getCustomerFragment(): BaseFragment {
        return  CustomerFragment()
    }
}