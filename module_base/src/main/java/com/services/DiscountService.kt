package com.services

import com.lib.basiclib.base.fragment.BaseFragment

/**
 *
 * @ Author  QinTian
 * @ Date  2020/5/22
 * @ Describe
 *
 */

interface DiscountService{

   fun getDiscountFragment(): BaseFragment

   fun getCustomerFragment(): BaseFragment

}