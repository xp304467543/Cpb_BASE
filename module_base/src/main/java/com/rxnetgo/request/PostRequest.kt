package com.rxnetgo.request

import com.rxnetgo.request.base.ApiService
import com.rxnetgo.request.base.BodyRequest

import io.reactivex.Flowable

/**
 * 构造Post请求，请求体封装请查看父类[BodyRequest]
 */
class PostRequest<T>(url: String, service: ApiService?, flowable: Flowable<T>?) : BodyRequest<T>(url, service, flowable) {

    override fun getMethod(): RequestType {
        return RequestType.POST
    }
}
