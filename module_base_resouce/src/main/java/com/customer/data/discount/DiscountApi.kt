package com.customer.data.discount

import com.customer.AppConstant
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import cuntomer.api.AllEmptySubscriber
import cuntomer.api.ApiSubscriber
import cuntomer.net.BaseApi

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
object DiscountApi : BaseApi {

    private const val DiscountList = "api/v2/live/get_activity_list"

    private const val CustomerList = "api/v2/user/user_customer_list/"


    /**
     * 优惠
     */
    fun getDisList(type: Int = 0, page: Int = 1, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApi()
            .get<String>(DiscountList)
            .headers("token", UserInfoSp.getToken())
            .params("type", type)//优惠类型，固定值0为全部优惠
            .params("client_type", 1)
            .params("api", if (AppConstant.isMain) 1 else 5) //	线路1官方5代理
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * 客服列表  1 在线客服  2代理客服 3 常见问题 4 更多回答
     */
    fun getCustomerQuestion(
        type: Int,
        function: ApiSubscriber<ArrayList<CustomerQuestion>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<ArrayList<CustomerQuestion>>() {}
        subscriber.function()
        HomeApi.getApi()
            .get<ArrayList<CustomerQuestion>>(CustomerList)
            .params("type", type)
            .subscribe(subscriber)
    }
}