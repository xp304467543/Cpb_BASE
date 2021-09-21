package cuntomer.api

import android.text.TextUtils
import cuntomer.bean.BaseApiBean
import com.rxnetgo.convert.base.IConverter
import com.rxnetgo.exception.ApiException
import com.rxnetgo.exception.NetErrorEngine
import com.rxnetgo.utils.JsonUtils
import okhttp3.ResponseBody
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 数据转换
 */
class ApiConvert<T>(private var type: Type? = null,
                    private val clazz: Class<T>? = null) : IConverter<T> {

    override fun convertResponse(body: ResponseBody?): T? {
        val json = body?.string() ?: ""
        body?.close()
        if (TextUtils.isEmpty(json)) throw ApiException(code = NetErrorEngine.DATA_ERROR)
        val bean = JsonUtils.fromJson(json, BaseApiBean::class.java)
        if (bean.code == ErrorCode.SUCCESS || bean.status == 100) {
            // 后端返回的code是成功的，但是data会空的
            return return if (bean.data != null && !bean.data.isJsonNull) {
                JsonUtils.fromJson(bean.data, getType())
            } else JsonUtils.fromJson(json, getType())
        } else {
            // 根据服务端的code来分发消息
            throw ApiException(code = bean.code, msg = bean.msg, dataCode = bean.data,data = bean.data)
        }
    }

    override fun getType(): Type {
        val type = type
        return type ?: if (clazz == null) {
            val genType = javaClass.genericInterfaces[0]
            (genType as ParameterizedType).actualTypeArguments[0]
        } else clazz
    }


}