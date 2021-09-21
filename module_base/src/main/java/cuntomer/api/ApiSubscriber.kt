package cuntomer.api

import com.rxnetgo.subscribe.JsonSubscriber
import okhttp3.ResponseBody

/**
 *
 * 统一Api接口订阅
 */
open class ApiSubscriber<T> : JsonSubscriber<T>() {

    override fun convertResponse(body: ResponseBody?): T? {
        return convertData(ApiConvert<T>(getType()).convertResponse(body))
    }
}