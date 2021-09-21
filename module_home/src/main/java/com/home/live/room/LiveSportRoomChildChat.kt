package com.home.live.room

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customer.base.BaseNormalFragment
import com.customer.component.dialog.GlobalDialog
import com.customer.component.input.InputPopWindow
import com.customer.component.panel.gif.GifManager
import com.customer.data.SendDanMu
import com.customer.data.UserInfoSp
import com.customer.data.home.SportLiveInfo
import com.home.R
import com.home.live.children.adapter.LiveRoomChatAdapter
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.player.ali.base.util.NetWatchdog
import kotlinx.android.synthetic.main.fragmeent_live_child_1.*
import kotlinx.android.synthetic.main.fragment_sport_chat.*
import kotlinx.android.synthetic.main.fragment_sport_chat.inputLayout
import kotlinx.android.synthetic.main.fragment_sport_chat.tvMoreInfo
import kotlinx.android.synthetic.main.fragment_sport_chat.tvVipCan


/**
 *
 * @ Author  QinTian
 * @ Date  6/18/21
 * @ Describe
 *
 */
class LiveSportRoomChildChat : BaseNormalFragment<LiveSportRoomChildChatPresenter>() {


    var rvChatAdapter: LiveRoomChatAdapter? = null
    var linearLayoutManager: LinearLayoutManager? = null

    //网络状态监听
    private var mNetWatchdog: NetWatchdog? = null

    private var inputPopWindow: InputPopWindow? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveSportRoomChildChatPresenter(
        arguments?.getParcelable<SportLiveInfo>("SportLiveInfo")?.anchor_id ?: ""
    )

    override fun isRegisterRxBus() = true

    override fun getLayoutRes() = R.layout.fragment_sport_chat

    override fun initContentView() {
        initSocket()
        initChat()
        initRvScoldListen()
        if (UserInfoSp.getNobleLevel() >= 1) {
            setVisible(tvInput)
            setGone(tvVipCan)
            setVisible(tvTalk)
        } else {
            setVisible(tvVipCan)
            setGone(tvInput)
            setGone(tvTalk)
        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (UserInfoSp.getNobleLevel() >= 1) {
            setVisible(tvInput)
            setGone(tvVipCan)
        } else {
            setVisible(tvVipCan)
            setGone(tvInput)
        }
    }


    override fun initData() {
//        mPresenter.enterRoom(arguments?.getString("anchorId")?:"")
        val info = arguments?.getParcelable<SportLiveInfo>("SportLiveInfo")
        mPresenter.getAllData(info?.anchor_id ?: "")
    }

    override fun initEvent() {
        //键盘
        layoutInput.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getNobleLevel() >= 1) {
                showInput()
            }
        }

        //底部有新消息
        tvMoreInfo.setOnClickListener {
            rvChatAdapter?.let { it1 -> scrollToBottom(it1) }
        }
    }


    //输入框
    private fun showInput(text: String = "") {
        if (inputPopWindow == null) {
            inputPopWindow = this.activity?.let { it1 -> InputPopWindow(it1) }
            inputPopWindow?.setOnTextSendListener {
                mPresenter.sendMessage(it)
                inputPopWindow?.dismiss()
            }
        }
        if (!TextUtils.isEmpty(text)) inputPopWindow?.editTextPanel?.setText(text)
        inputPopWindow?.showAtLocation(
            this.activity?.window?.decorView?.rootView,
            Gravity.NO_GRAVITY,
            0,
            0
        )
        inputLayout.postDelayed(
            {
                if (inputPopWindow != null) {
                    setGone(inputLayout)
                    inputPopWindow?.showKeyboard()
                }
            }, 50
        )
        inputPopWindow?.setOnDismissListener {
            setVisible(inputLayout)
        }
    }


    //滑动监听
    private fun initRvScoldListen() {
        sportChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            var isSlidingToLast = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (tvMoreInfo.visibility == View.VISIBLE) setGone(tvMoreInfo)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                isSlidingToLast = dx > 0
            }

        })
    }

    /**
     * 初始化socket
     */
    private fun initSocket() {
        mNetWatchdog = NetWatchdog(context)
        mNetWatchdog?.setNetChangeListener(object : NetWatchdog.NetChangeListener {
            override fun on4GToWifi() {}
            override fun onNetDisconnected() {
                mPresenter.stopConnect()
            }

            override fun onWifiTo4G() {}

        })
        mNetWatchdog?.setNetConnectedListener(object : NetWatchdog.NetConnectedListener {
            override fun onReNetConnected(isReconnect: Boolean) {
                mPresenter.startWebSocketConnect()
            }

            override fun onNetUnConnected() {
                mPresenter.stopConnect()
            }
        })
        mNetWatchdog?.startWatch()
    }


    private fun initChat() {
        sportRefresh.setEnableRefresh(false)//是否启用下拉刷新功能
        sportRefresh.setEnableLoadMore(false)//是否启用上拉加载功能
        sportRefresh.setEnableOverScrollBounce(true)//是否启用越界回弹
        sportRefresh.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        context?.let { GifManager.initGifDrawable(it) }
        rvChatAdapter =
            context?.let { fragmentManager?.let { it1 -> LiveRoomChatAdapter(it, it1) } }
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        sportChat.layoutManager = linearLayoutManager
        sportChat.adapter = rvChatAdapter
    }


    /**
     * 滑动到最底部
     */
    fun scrollToBottom(multiTypeAdapter: LiveRoomChatAdapter) {
        linearLayoutManager?.scrollToPosition(multiTypeAdapter.itemCount - 1)
        setGone(tvMoreInfo)
    }


    /**
     * 是否在最底部
     */
    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
    }

    //横屏发送弹幕
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun sendDanMu(eventBean: SendDanMu) {
        mPresenter.sendMessage(eventBean.content)
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.stopConnect()
        mNetWatchdog?.stopWatch()
    }


    companion object {
        fun newInstance(info: SportLiveInfo): LiveSportRoomChildChat {
            val fragment = LiveSportRoomChildChat()
            val bundle = Bundle()
            bundle.putParcelable("SportLiveInfo", info)
            fragment.arguments = bundle
            return fragment
        }
    }
}