package cuntomer.api

import android.text.TextUtils
import cuntomer.bean.BaseApiBean
import com.rxnetgo.exception.ApiException
import com.rxnetgo.exception.NetErrorEngine
import com.rxnetgo.utils.JsonUtils
import okhttp3.ResponseBody

/**
 * 不需要关注订阅结果的订阅者
 */
open class EmptySubscriber : ApiSubscriber<String>() {

    override fun convertResponse(body: ResponseBody?): String? {
        val json = body?.string() ?: ""
        body?.close()
        if (TextUtils.isEmpty(json)) throw ApiException(code = NetErrorEngine.DATA_ERROR)
        val bean = JsonUtils.fromJson(json, BaseApiBean::class.java)
        if (bean.code == ErrorCode.SUCCESS) {
            // 后端返回的code是成功的，但是data会空的
            if (bean.data.toString() == "\"\"") {
                return bean.msg
            }
            return bean.data.toString()
        } else {
            // 根据服务端的code来分发消息
            throw ApiException(code = bean.code, msg = bean.msg, dataCode = bean.data)
        }
    }
}