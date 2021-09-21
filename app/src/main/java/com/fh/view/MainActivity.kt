package com.fh.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.customer.component.ActivityDialogSuccess
import com.customer.component.activity.DialogLotteryMoney
import com.customer.component.easyfloat.GlobalFloat
import com.customer.component.easyfloat.permission.PermissionUtils
import com.customer.data.*
import com.customer.data.home.AllSocket
import com.customer.data.home.DataRes
import com.customer.data.home.HomeApi
import com.customer.data.home.ParseNotify
import com.customer.data.login.LoginSuccess
import com.customer.data.lottery.PlaySecData
import com.customer.wsmanager.WsManager
import com.customer.wsmanager.listener.WsStatusListener
import com.google.gson.Gson
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.activity.BasePageActivity
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.SoftInputUtils
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ToastUtils
import com.rxnetgo.RxNetGo
import com.xiaojinzi.component.anno.RouterAnno
import cuntomer.PriseViewUtils
import cuntomer.api.WebUrlProvider
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.ByteString
import org.json.JSONObject
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.TimeUnit


@RouterAnno(host = "App", path = "main")
class MainActivity : BasePageActivity() {

    var clientId = "-1"

    override fun getPageFragment() = MainFragment()


    override fun isRegisterRxBus() = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.setStatusBarForegroundColor(this, true)
        showFloat()
    }


    /**
     * 退出时取消网络相关的请求
     */
    override fun onDestroy() {
        // 取消所有网络请求的订阅
        RxNetGo.getInstance().dispose()
        // 清除软键盘中过滤的View
        SoftInputUtils.clearFilterView()
        mWsManager?.stopConnect()
        GlobalFloat.closeAllFloat()
        super.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        mWsManager?.stopConnect()
    }


    /**
     * 全局socket
     */
    var mWsManager: WsManager? = null
    private var mWsStatusListener: WsStatusListener? = null
    private var isReconnect = false
    private var mTimer: Timer? = null
    private fun allSocket() {
        LogUtils.d("AllWsManager-----WebUrlProvider=" + WebUrlProvider.getALLBaseUrl())
        initStatusListener()
        mWsManager = WsManager.Builder(this)
            .client(
                OkHttpClient().newBuilder()
                    .pingInterval(1000 * 50, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()
            )
            .needReconnect(true).wsUrl(WebUrlProvider.getALLBaseUrl())
            .build()
        mWsManager?.setWsStatusListener(mWsStatusListener)
        mWsManager?.startConnect()
    }

    private fun initStatusListener() {
        mWsStatusListener = object : WsStatusListener() {
            override fun onOpen(response: Response) {
                super.onOpen(response)
                LogUtils.d("AllWsManager-----onOpen response=$response")

            }

            override fun onMessage(text: String) {
                super.onMessage(text)
                LogUtils.d("AllWsManager-----onOpen text=$text")
                socketMessage(text)
            }

            override fun onMessage(bytes: ByteString) {
                super.onMessage(bytes)
                LogUtils.d("AllWsManager-----onMessage$bytes")
            }

            override fun onReconnect() {
                super.onReconnect()
                isReconnect = true
                LogUtils.d("AllWsManager-----onReconnect——home")
            }

            override fun onClosing(code: Int, reason: String) {
                super.onClosing(code, reason)
                LogUtils.d("AllWsManager-----onClosing")
            }

            override fun onClosed(code: Int, reason: String) {
                super.onClosed(code, reason)
                LogUtils.d("AllWsManager-----onClosed")
            }

            override fun onFailure(t: Throwable?, response: Response?) {
                super.onFailure(t, response)
                LogUtils.d("AllWsManager---home--onFailure$response=$t")
                if (mTimer != null) {
                    mTimer?.cancel()
                    mTimer = null
                }
            }
        }
    }

    fun socketMessage(text: String) {
        if (text.isNotEmpty()) {
            val res = WebUrlProvider.getData<AllSocket>(text, AllSocket::class.java)
            when (res?.type) {
                "connected" -> {
                    mWsManager?.sendMessage(getLogin(res.client_id ?: "0"))
                    clientId = res.client_id ?: "-1"
                }
                "login" -> {
                    mWsManager?.sendMessage(ping(clientId))
                    if (mTimer == null) mTimer = Timer()
                    mTimer?.schedule(object : TimerTask() {
                        override fun run() {
                            mWsManager?.sendMessage(ping(clientId))
                            LogUtils.d("AllWsManager-----发送了心跳")
                        }
                    }, 0, 1000 * 54)
                }
                "ServerPush" -> {
                    when (res.dataType) {
                        "open_lottery_push" -> {
                            val result = WebUrlProvider.getData<DataRes>(
                                res.data.toString(),
                                DataRes::class.java
                            )
                            mWsManager?.sendMessage(makeSure(result?.msg_id))
                            systemDialog(result?.msg ?: "获取消息失败")
                        }
                        "pop_result_push" -> {
                            val result = WebUrlProvider.getData<DataRes>(
                                res.data.toString(),
                                DataRes::class.java
                            )
                            mWsManager?.sendMessage(makeSurePop(result?.msg_id))
                            val intent = Intent(this, ActivityDialogSuccess::class.java)
                            intent.putExtra("msgSuccess", result?.msg)
                            intent.putExtra("is_success", result?.is_success)
                            startActivity(intent)

                        }
                        "app_online_push" -> {
                            val result = WebUrlProvider.getData<DataRes>(
                                res.data.toString(),
                                DataRes::class.java
                            )
                            RxBus.get().post(OnLine(result?.online))


                        }
                        "lottery_closing_push" -> {
                            if (res.data?.isJsonNull == false) {
                                val result = res.data?.asJsonArray
                                val list = arrayListOf<DataRes>()
                                if (result?.isJsonNull == false) {
                                    for (bean in result) {
                                        val des = Gson().fromJson(bean, DataRes::class.java)
                                        list.add(des)
                                    }
                                    RxBus.get().post(CodeClose(list))
                                }
                            } else RxBus.get().post(CodeOpen("1"))
                        }
                        "jackpot_msg_push" -> {
                            val result = WebUrlProvider.getData<DataRes>(
                                res.data.toString(),
                                DataRes::class.java
                            )
                            val intent = Intent(this, DialogLotteryMoney::class.java)
                            startActivity(intent)
                            mWsManager?.sendMessage(makeSureLotteryMoney(result?.msg_id))
                        }
                        "turntable_msg_push" -> {

                        }
                        "platform_notice_msg_push" -> {
                            val result = res.data?.asJsonObject
                            val name = result?.get("name")?.asString
                            val num = result?.get("number")?.asString
                            val phone = result?.get("phone")?.asString
                            val str = "恭喜会员 $phone 抽奖获得$name x$num"
                            PriseViewUtils.showPriseView(this, str)
                            RxBus.get().post(ParseNotify(str))
                        }
                        "dragon_bet_push" -> {
//                            LogUtils.e("===>>dragon_bet_push---" + res.data.toString())
                            if (res.data?.isJsonNull == false) {
                                try {
                                    val result = res.data?.asJsonArray
                                    val list = arrayListOf<PlaySecData>()
                                    if (result?.isJsonNull == false) {
                                        for (bean in result) {
                                            val des = Gson().fromJson(bean, PlaySecData::class.java)
                                            list.add(des)
                                        }
                                        RxBus.get().post(LongPush(list))
                                    }
                                } catch (e: Exception) {
//                                    LogUtils.e("===>>dragon_bet_push---" + e.message)
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private fun systemDialog(msg: String) {
//        CookieBar.builder(this)
//            .setBackgroundColor(R.color.white)
//            .setMessage(msg)
//            .setMessageColor(R.color.color_333333)
//            .setIcon(R.mipmap.ic_live_star)
//            .show()
        ToastUtils.showToastView(msg)
    }

    private fun getLogin(client_id: String, user_id: Int = UserInfoSp.getUserId()): String {
        val jsonObject = JSONObject()
        jsonObject.put("type", "login")
        jsonObject.put("client_id", client_id)
        jsonObject.put("user_id", user_id)
        jsonObject.put("client_type", "ANDROID")
        return jsonObject.toString()
    }

    fun ping(client_id: String): String {
        val jsonObject = JSONObject()
        jsonObject.put("type", "ping")
        jsonObject.put("client_id", client_id)
        return jsonObject.toString()
    }

    private fun makeSure(msg_id: String?): String {
        val jsonObject = JSONObject()
        jsonObject.put("type", "confirm")
        jsonObject.put("event", "open_lottery_push")
        jsonObject.put("client_id", clientId)
        jsonObject.put("user_id", UserInfoSp.getUserId())
        jsonObject.put("msg_id", msg_id)
        return jsonObject.toString()
    }

    private fun makeSurePop(msg_id: String?): String {
        val jsonObject = JSONObject()
        jsonObject.put("type", "confirm")
        jsonObject.put("event", "pop_result_push")
        jsonObject.put("client_id", clientId)
        jsonObject.put("user_id", UserInfoSp.getUserId())
        jsonObject.put("msg_id", msg_id)
        return jsonObject.toString()
    }

    private fun makeSureLotteryMoney(msg_id: String?): String {
        val jsonObject = JSONObject()
        jsonObject.put("type", "confirm")
        jsonObject.put("event", "jackpot_msg_push")
        jsonObject.put("client_id", clientId)
        jsonObject.put("user_id", UserInfoSp.getUserId())
        jsonObject.put("msg_id", msg_id)
        return jsonObject.toString()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun login(eventBean: LoginSuccess) {
        mWsManager?.stopConnect()
        allSocket()
        mWsManager?.sendMessage(getLogin(clientId))
        showFloat()
    }

    private fun showFloat() {
        HomeApi.getIsShowRed {
            onSuccess {
                checkPermission(true, (it.red_end_time ?: BigDecimal.ZERO).subtract(BigDecimal(System.currentTimeMillis()/1000)))
            }
            onFailed {
                checkPermission(false)
            }
        }
    }

    /**
     * 检测浮窗权限是否开启，若没有给与申请提示框（非必须，申请依旧是EasyFloat内部内保进行）
     */
    private fun checkPermission(isRain: Boolean = false, time: BigDecimal = BigDecimal.ZERO) {
        if (PermissionUtils.checkPermission(this)) {
           if (UserInfoSp.getIsShowFloat()) openFloat(isRain,time)
        } else {
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("需要您授权悬浮窗权限。")
                .setPositiveButton("去开启") { _, _ ->
                    openFloat(isRain,time)
                }
                .setNegativeButton("取消") { _, _ -> }
                .show()
        }
    }


    private fun openFloat(isRain:Boolean, time: BigDecimal = BigDecimal.ZERO){
        if (isRain){
            GlobalFloat.closeGlobalFloat()
            Handler().postDelayed({ GlobalFloat.showGlobalFloatRain(this@MainActivity, time) }, 500)
        }else{
            GlobalFloat.closeGlobalFloatRain()
            Handler().postDelayed({ GlobalFloat.showGlobalFloat(this@MainActivity) }, 500)
        }
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginOut(eventBean: LoginOut) {
        mWsManager?.stopConnect()
        allSocket()
        GlobalFloat.closeGlobalFloatRain()
        Handler().postDelayed({ GlobalFloat.showGlobalFloat(this@MainActivity) }, 1500)
    }
}