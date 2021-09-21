package com.customer.data.login

import com.customer.utils.AESUtils
import com.customer.utils.IpUtils
import com.google.gson.Gson
import com.lib.basiclib.utils.ViewUtils
import cuntomer.api.AllEmptySubscriber
import cuntomer.api.AllSubscriber
import cuntomer.api.ApiSubscriber
import cuntomer.bean.BaseApiBean
import com.customer.data.UserInfoSp
import cuntomer.net.BaseApi

/**
 *
 * @ Author  QinTian
 * @ Date  2020-02-15
 * @ Describe
 *
 */

object LoginApi : BaseApi {

    //登录
    const val LOGIN = "v2/login/"

    //登录信息，登录成功后获取
    private const val LOGIN_INFO = "index/index/"

    //获取验证码
    private const val GET_CODE = "reg/send-sms/"

    //注册
    const val REGISTER = "v2/reg/"

    //首冲
    private const val FIRST_RECHARGE = "api/v2/Recharge/IsFirst/"

    //找回登录密码
    private const val GET_LOGIN_PASS = "home/retrieve-password/"

    // 检测邀请码接口
    private const val GET_LOGIN_MARK_CODE = "market/check-market-code/"

    //试玩
    private const val TRY_PLAY = "v2/login/guest"

    /**
     * 密码登录
     */
    fun userLoginWithPassWord(
        userName: String,
        passWord: String,
        loadMode: String,
        function: AllSubscriber.() -> Unit
    ) {
        val subscriber = AllSubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["username"] = userName
        map["password"] = passWord
        map["mode"] = loadMode
        map["client_type"] = 3
        map["timestamp"] = System.currentTimeMillis()
        map["ip"] = IpUtils.getIPAddress(ViewUtils.getContext())
        AESUtils.encrypt(getBase64Key(), Gson().toJson(map))?.let {
            getApiOther().post<BaseApiBean>(LOGIN).isMultipart(true).params("datas", it)
                .subscribe(subscriber)
        }
    }



    /**
     * 试玩
     *
     */
    fun userLoginTry(function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["client_type"] = 3
        map["timestamp"] = System.currentTimeMillis()
        map["ip"] = IpUtils.getIPAddress(ViewUtils.getContext())
        AESUtils.encrypt(getBase64Key(), Gson().toJson(map))?.let {
            getApiOther().post<BaseApiBean>(TRY_PLAY).isMultipart(true).params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * 验证码登录
     */

    fun userLoginWithIdentify(
        phoneNum: String,
        captcha: String,
        isAutoLogin: Int,
        function: AllSubscriber.() -> Unit
    ) {
        val subscriber = AllSubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["phone"] = phoneNum
        map["captcha"] = captcha
        map["mode"] = 3
        map["client_type"] = 3
        map["ip"] = IpUtils.getIPAddress(ViewUtils.getContext())
        map["is_auto_login"] = isAutoLogin
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(getBase64Key(), Gson().toJson(map))?.let {
            getApiOther().post<BaseApiBean>(LOGIN).isMultipart(true).params("datas", it)
                .subscribe(subscriber)
        }

    }

    /**
     * 登录信息，登录成功后获取
     */
    fun getLoginInfo(token: String, function: ApiSubscriber<LoginInfoResponse>.() -> Unit) {
        val subscriber = object : ApiSubscriber<LoginInfoResponse>() {}
        subscriber.function()
        getApiOther().get<LoginInfoResponse>(LOGIN_INFO)
            .headers("Authorization", token)
            .subscribe(subscriber)
    }



    /**
     * 获取验证码
     */
    fun userGetCode(phone: String, type: String, function: ApiSubscriber<RegisterCode>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RegisterCode>() {}
        subscriber.function()
        getApiOther().post<RegisterCode>(GET_CODE)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("user_id", UserInfoSp.getUserId())
            .params("phone", phone)
            .params("type", type) //reg默认  login登录验证码
            .subscribe(subscriber)
    }

    /**
     * 注册
     */
    fun userRegister(
        phone: String,
        code: String,
        password: String,
        is_auto_login: String,
        market_code: String,
        function:  AllSubscriber.() -> Unit
    ) {
        val subscriber = AllSubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["phone"] = phone
        map["password"] = password
        map["captcha"] = code
        map["mode"] = 3
        map["client_type"] = 3
        map["ip"] = IpUtils.getIPAddress(ViewUtils.getContext())
        map["is_auto_login"] = is_auto_login
        map["market_code"] = market_code
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(LoginApi.getBase64Key(), Gson().toJson(map))?.let {
            getApiOther().post<BaseApiBean>(REGISTER).isMultipart(true)
                .params("datas", it)
                .subscribe(subscriber)
        }


    }

    /**
     * 找回登录密码
     */
    fun getLoginPass(
        phone: String,
        captcha: String,
        new_pwd: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(GET_LOGIN_PASS)
            .params("phone", phone)
            .params("captcha", captcha)
            .params("new_pwd", new_pwd)
            .subscribe(subscriber)
    }

    /**
     * 找回登录密码
     */
    fun checkMarkCode(datas: String, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(GET_LOGIN_MARK_CODE).isMultipart(true)
            .params("datas", datas)
            .subscribe(subscriber)
    }



    /**
     * 登录后获取是否首冲
     */
    fun getIsFirstRecharge(userID: Int, function: ApiSubscriber<LoginFirstRecharge>.() -> Unit) {
        val subscriber = object : ApiSubscriber<LoginFirstRecharge>() {}
        subscriber.function()
        getApi().post<LoginFirstRecharge>(FIRST_RECHARGE)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", userID)
            .subscribe(subscriber)

    }

}