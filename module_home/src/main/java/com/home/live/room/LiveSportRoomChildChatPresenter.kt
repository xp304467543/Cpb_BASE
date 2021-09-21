package com.home.live.room

import android.annotation.SuppressLint
import android.view.View
import com.customer.data.DanMu
import com.customer.data.EnterVip
import com.customer.data.home.HomeApi
import com.customer.data.home.HomeLiveChatBeanNormal
import com.customer.data.home.HomeLiveTwentyNewsResponse
import com.customer.utils.ObjectAnimatorViw
import com.customer.wsmanager.WsManager
import com.customer.wsmanager.listener.WsStatusListener
import com.home.live.children.socket.SocketHelper
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ToastUtils
import cuntomer.api.WebUrlProvider
import kotlinx.android.synthetic.main.fragment_sport_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.ByteString
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * @ Author  QinTian
 * @ Date  6/18/21
 * @ Describe
 *
 */
class LiveSportRoomChildChatPresenter(private val anchorId: String) : BaseMvpPresenter<LiveSportRoomChildChat>() {


    fun enterRoom(anchorID: String) {
        HomeApi.enterLiveRoom(anchorId = anchorID) {
            onSuccess {
                if (mView.isActive()) {

//                    mView.initThings(it)
//                    mView.changeAttention(it.isFollow)
//                    mView.hidePageLoadingDialog()
//                    getIsFirstRecharge()
//                    getUserBalance()
//                    getUserVip()
                }
            }
            onFailed {
                if (mView.isActive()) {
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }
    private val uiScope = CoroutineScope(Dispatchers.Main)

    //获取直播间信息
    fun getAllData(anchorId: String) {
        if (mView.isActive()) {
            uiScope.launch {
                val getTwentyNews = async {
                    HomeApi.getTwentyNews(anchorId)
                }
                val getTwentyNewsResult = getTwentyNews.await()
                getTwentyNewsResult.onSuccess {
                    mView.rvChatAdapter?.refresh(it)
                    if (mView.isVisible && mView.rvChatAdapter != null) mView.scrollToBottom(
                        mView.rvChatAdapter!!
                    )
                }
            }
        }
    }

    var mWsManager: WsManager? = null
    private var mTimer: Timer? = null
    private var mWsStatusListener: WsStatusListener? = null
    private var isReconnect = false

    /**
     * 释放wobSocket
     */
    fun stopConnect() {
        if (null != mWsManager) {
            mWsManager?.stopConnect()
            mWsManager = null
        }
    }

    /**
     * 发送一条聊天消息
     */
    fun sendMessage(content: String) {
        mWsManager?.sendMessage(SocketHelper.getPublishParams(anchorId, content))
    }

    /**
     * 分享注单
     */
    fun shareOrder(content: JSONObject?) {
        mWsManager?.sendMessage(SocketHelper.getPublishParams(anchorId, "", true, content))
    }

    /**
     * 通知socket
     */
    fun notifySocket(content: String) {
        mWsManager?.sendMessage(content)
    }

    //初始化socket
    fun startWebSocketConnect() {
        if (mView.isActive()) {
            mView.showPageLoadingDialog("聊天室连接中..")
            initStatusListener()
            mWsManager = WsManager.Builder(mView.requireActivity())
                .client(
                    OkHttpClient().newBuilder()
                        .pingInterval(1000 * 50, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build()
                )
                .needReconnect(true).wsUrl(WebUrlProvider.getBaseUrl())
                .build()
            mWsManager?.setWsStatusListener(mWsStatusListener)
            mWsManager?.startConnect()
        }
    }

    private fun initStatusListener() {
        mWsStatusListener = object : WsStatusListener() {
            override fun onOpen(response: Response) {
                super.onOpen(response)
                mView.hidePageLoadingDialog()
                LogUtils.d("WsManager-----onOpen response=$response")
                mWsManager?.sendMessage(SocketHelper.getSubscribeParams(isReconnect, anchorId))
                if (mTimer == null) mTimer = Timer()
                mTimer?.schedule(object : TimerTask() {
                    override fun run() {
                        mWsManager?.sendMessage(SocketHelper.getPingParams(anchorId))
                        LogUtils.d("WsManager-----发送了心跳")
                    }
                }, 0, 1000 * 54)
            }

            override fun onMessage(text: String) {
                super.onMessage(text)
                if (mView.isActive()) initChatText(text)
                LogUtils.e("WsManager-----c$text")
            }

            override fun onMessage(bytes: ByteString) {
                super.onMessage(bytes)
                LogUtils.d("WsManager-----onMessage$bytes")
            }

            override fun onReconnect() {
                super.onReconnect()
                isReconnect = true
                LogUtils.d("WsManager-----onReconnect")

            }

            override fun onClosing(code: Int, reason: String) {
                super.onClosing(code, reason)
                LogUtils.d("WsManager-----onClosing")
            }

            override fun onClosed(code: Int, reason: String) {
                super.onClosed(code, reason)
                LogUtils.d("WsManager-----onClosed")
            }

            override fun onFailure(t: Throwable?, response: Response?) {
                super.onFailure(t, response)
                LogUtils.d("WsManager-----onFailure$response=$t")
                if (mTimer != null) {
                    mTimer?.cancel()
                    mTimer = null
                }
            }
        }
    }

    var showBean = HomeLiveTwentyNewsResponse()

    //信息接收
    @SuppressLint("SetTextI18n")
    private fun initChatText(text: String) {
        val data = WebUrlProvider.getData<HomeLiveChatBeanNormal>(text, HomeLiveChatBeanNormal::class.java)
        LogUtils.e("WsManager-----111onMessage${text}")
        if (data != null) {
            if (mView.isActive()) {
                when (data.type) {
                    //聊天内容
                    LiveRoomChatPresenterHelper.TYPE_PUBLISH -> {
                        if (data.event == "pushPlan") {
//                            val res = HomeLiveTwentyNewsResponse(
//                                data.type,
//                                data.room_id,
//                                data.user_id,
//                                "",
//                                data.text,
//                                data.vip,
//                                data.userType,
//                                data.avatar,
//                                data.sendTime,
//                                data.sendTimeTxt,
//                                event = data.event ?: "",
//                                orders = data.orders,
//                                canFollow = true
//                            )
//                            mView.rvChatAdapter?.add(res)
                        } else {
                            val res = HomeLiveTwentyNewsResponse(
                                data.type, data.room_id, data.user_id,
                                data.userName ?: "", data.text, data.vip,
                                data.userType, data.avatar, data.sendTime,
                                data.sendTimeTxt
                            )
                            mView.rvChatAdapter?.add(res)
                            RxBus.get().post(
                                DanMu(
                                    data.userName.toString(),
                                    data.userType.toString(),
                                    data.text.toString(),
                                    data.vip.toString(),
                                    data.isMe
                                )
                            )
                        }
                        scrollBottom()//滑动到底部
                    }
                    //管理员清屏
                    LiveRoomChatPresenterHelper.TYPE_COMMEND -> if (data.commend == "clear") mView.rvChatAdapter?.clear()

                    //进场提示
                    LiveRoomChatPresenterHelper.TYPE_SUBSCRIBE -> {
                        if (!data.isMe) {
                            //普通进场
                            if (data.vip == "0" || data.vip == "") {
                                val text = "欢迎 " + data.userName + " 进入直播间"
                                mView.tvEnterContent.text = text
//                                setVip(data.vip ?: "0", mView.imgEnterImg)
                                ObjectAnimatorViw.setShowAnimation(mView.linEnter, 1000)
                                Timer().schedule(object : TimerTask() {
                                    override fun run() {
                                        if (mView.isVisible && mView.linEnter != null) {
                                            mView.linEnter.post {
                                                ObjectAnimatorViw.setHideAnimation(
                                                    mView.linEnter,
                                                    1000
                                                )
                                            }
                                        }
                                        this.cancel()
                                    }
                                }, 5000)
                            } else {
                                RxBus.get().post(EnterVip(data.vip.toString(), data.avatar.toString()))
                            }
                        }
                    }
                    //礼物
                    LiveRoomChatPresenterHelper.TYPE_GIFT -> {

                    }
                    //主播长龙 队列
                    LiveRoomChatPresenterHelper.TYPE_SERVER_PUSH -> {

                    }
                    LiveRoomChatPresenterHelper.TYPE_ERROE -> {
                        ToastUtils.showToast(data.msg)
                    }
                }
            }
        }
    }

    private fun scrollBottom() {
        if (mView.isSlideToBottom(mView.sportChat)) {
            mView.rvChatAdapter?.let { mView.scrollToBottom(it) }
        } else {
            mView.tvMoreInfo.visibility = View.VISIBLE
        }
    }
}