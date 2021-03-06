package com.home.live.children

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customer.ApiRouter
import com.customer.base.BaseNormalFragment
import com.customer.component.dialog.*
import com.customer.component.dialog.live.BottomGiftWindow
import com.customer.component.gift.RewardAdapter
import com.customer.component.gift.bean.SendGiftBean
import com.customer.component.input.InputPopWindow
import com.customer.component.panel.gif.GifManager
import com.customer.data.*
import com.customer.data.home.HomeLiveAnimatorBean
import com.customer.data.home.HomeLiveRedRoom
import com.customer.data.mine.MineApi
import com.customer.data.mine.MinePassWordTime
import com.customer.utils.JsonUtils
import com.customer.utils.ObjectAnimatorViw
import com.home.R
import com.home.live.bet.new.LiveRoomBetFragment
import com.home.live.bet.old.LotteryShareBet
import com.home.live.children.adapter.LiveRoomChatAdapter
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.player.ali.base.util.NetWatchdog
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.fragmeent_live_child_1.*
import java.util.*


/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/26
 * @ Describe
 *
 */
class LiveRoomChild1 : BaseNormalFragment<LiveRoomChild1Presenter>() {

    var anchorId = "-1"
    var lotteryId = "-1"
    var linearLayoutManager: LinearLayoutManager? = null

    //??????????????????
    private var mNetWatchdog: NetWatchdog? = null

    var rvChatAdapter: LiveRoomChatAdapter? = null

    private var inputPopWindow: InputPopWindow? = null

    var isShowPop: Boolean = false
    var popMenuManager: LiveRoomPop? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun isRegisterRxBus() = true

    override fun attachPresenter() =
        LiveRoomChild1Presenter(arguments?.getString("LIVE_ROOM_ANCHOR_ID", "-1") ?: "0")

    override fun getLayoutRes() = R.layout.fragmeent_live_child_1

    override fun initContentView() {
        initIntent()
        initSocket()
        initChat()
        initParams()
        if (UserInfoSp.getOpenWindow()) setVisible(floatButton) else setGone(floatButton)
        if (UserInfoSp.getNobleLevel()>=1){
            setVisible(tvInput)
            setGone(tvVipCan)
        } else {
            setVisible(tvVipCan)
            setGone(tvInput)
        }
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (UserInfoSp.getNobleLevel()>=1){
            setVisible(tvInput)
            setGone(tvVipCan)
        } else {
            setVisible(tvVipCan)
            setGone(tvInput)
        }
    }

    private fun initParams() {
        val pair2 = tvMoreInfo.layoutParams as ConstraintLayout.LayoutParams
        val height2 = (ViewUtils.getScreenHeight() - ViewUtils.dp2px(42)) / 2
        pair2.setMargins(0, height2, 0, 0)
        tvMoreInfo.layoutParams = pair2
    }

    private fun initIntent() {
        anchorId = arguments?.getString("LIVE_ROOM_ANCHOR_ID", "-1") ?: "0"
        lotteryId = arguments?.getString("LIVE_ROOM_LOTTERY_ID", "-1") ?: "0"
//        if ( arguments?.getString("LIVE_ROOM_ANCHOR_STATUE", "-1") ?: "0" == "1")
        setGone(tvNoInput)
        upDateSystemNotice()
    }

    override fun initData() {
        mPresenter.getAllData(anchorId)
        mPresenter.homeLiveRedList(anchorId, false)
    }


    // ========= ?????? =========
    @SuppressLint("SetTextI18n")
    private fun upDateSystemNotice() {
        val name = arguments?.getString("LIVE_ROOM_NICK_NAME") ?: "0"
        if (TextUtils.isEmpty(name)) mtvLiveRoom.text = "????????????" else mtvLiveRoom.text =
            "???????????? $name ????????????,????????????????????????"
        mtvLiveRoom.setTextColor(getColor(R.color.color_333333))
    }

    private fun initChat() {
        chartSmartRefreshLayout.setEnableRefresh(false)//??????????????????????????????
        chartSmartRefreshLayout.setEnableLoadMore(false)//??????????????????????????????
        chartSmartRefreshLayout.setEnableOverScrollBounce(true)//????????????????????????
        chartSmartRefreshLayout.setEnableOverScrollDrag(true)//?????????????????????????????????????????????
        context?.let { GifManager.initGifDrawable(it) }
        rvChatAdapter = context?.let { fragmentManager?.let { it1 -> LiveRoomChatAdapter(it, it1) } }
        linearLayoutManager =LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycleChat.layoutManager = linearLayoutManager
        recycleChat.adapter = rvChatAdapter
        initAnim()

    }

    override fun initEvent() {
        initRvScoldListen()
        //??????
        imgBuyLotteryQp.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
//            RxBus.get().post(ToBetView(2))
            if (UserInfoSp.getUserType() == "4"){
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()){
//                showPageLoadingDialog("?????????")
//                mPresenter.getPanGift()
                Router.withApi(ApiRouter::class.java).toRoundPrise()
            }
        }
        //??????
        imgBuyLottery.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            RxBus.get().post(ToBetView(1))
        }
        //??????
        imgRecharge.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4"){
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java)
                .toMineRecharge(0)
        }
        //??????
        imgGift.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4"){
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            initGitWidow()
        }
        //??????
        tvInput.setOnClickListener {
            showInput()
        }
        //??????????????????
        tvMoreInfo.setOnClickListener {
            rvChatAdapter?.let { it1 -> scrollToBottom(it1) }
        }
        //??????
        imgRed.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4"){
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!UserInfoSp.getIsSetPayPassWord()) {
                mPresenter.getIsSetPayPassWord()
            } else sendRed()
        }
        //????????????
        floatButton.setOnClickListener {
            showOrCloseFloatView()
        }

        imgShake.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                liveRoomBetFragment = LiveRoomBetFragment.newInstance(lotteryId)
                fragmentManager?.let { it1 ->
                    liveRoomBetFragment?.show(
                        it1,
                        "LiveRoomBottomBetFragment"
                    )
                }
            } else ToastUtils.showToast("??????????????????")
//            context?.let { it1 -> DialogLiveRoomBet(it1).show() }
        }

        imgMouse.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                startActivity(Intent(activity, LiveRoomPostCardAct::class.java))
            }
        }

        imgGame.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(requireActivity())
                    return@setOnClickListener
                }
                RxBus.get().post(ToBetView(1))
            }
        }
    }

    private var liveRoomBetFragment: LiveRoomBetFragment? = null

    //????????????
    private fun initRvScoldListen() {
        recycleChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            //?????????????????????????????????????????????
            var isSlidingToLast = false

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)) {
                    if (tvMoreInfo.visibility == View.VISIBLE) setGone(tvMoreInfo)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dx?????????????????????????????????dy??????????????????????????????
                isSlidingToLast = dx > 0
            }

        })
    }

    /**
     * ???????????????
     */
    fun sendRed() {
        val dialog = DialogSendRed(requireActivity(), false)
        dialog.setOnSendClickListener { total, redNumber, redContent ->
            dialog.dismiss()
            initPassWordDialog(total, redNumber, redContent)
        }
        dialog.show()
    }

    /**
     * ???????????????
     */
    private fun initPassWordDialog(total: String, redNumber: String, redContent: String) {
        val passWordDialog =
            context?.let { DialogPassWord(it, ViewUtils.getScreenWidth(), ViewUtils.dp2px(156)) }
        passWordDialog?.setTextWatchListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 6) {
                    passWordDialog.showOrHideLoading()
                    //??????????????????
                    MineApi.verifyPayPass(s.toString()) {
                        onSuccess {
                            //?????????
                            mPresenter.homeLiveSendRedEnvelope(
                                anchorId,
                                total,
                                redNumber,
                                redContent,
                                s.toString(),
                                passWordDialog
                            )
                        }
                        onFailed {
                            passWordDialog.showOrHideLoading()
                            if (it.getCode() == 1002) {
                                passWordDialog.showTipsText(
                                    it.getMsg().toString() + "," + "?????????" +
                                            JsonUtils.fromJson(
                                                it.getDataCode().toString(),
                                                MinePassWordTime::class.java
                                            ).remain_times.toString() +
                                            "?????????"
                                )
                                passWordDialog.clearText()
                            } else {
                                passWordDialog.showTipsText(it.getMsg().toString())
                            }
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        passWordDialog?.show()
    }


    /**
     * ????????????
     */
    var bottomGiftWindow: BottomGiftWindow? = null
    private fun initGitWidow() {
        if (bottomGiftWindow == null) {
            bottomGiftWindow = BottomGiftWindow(requireActivity())
            bottomGiftWindow?.show()
            mPresenter.getGiftList()
        } else bottomGiftWindow?.show()
        MineApi.getUserDiamond {
            onSuccess { bottomGiftWindow?.setDiamond(it.diamond) }
            onFailed { GlobalDialog.showError(this@LiveRoomChild1.requireActivity(), it) }
        }
    }

    //????????????
    fun startRedAnimation() {
        if (liveRedTips != null) {
            setVisible(liveRedTips)
            val animator = ObjectAnimatorViw.nope(liveRedTips, 3f)
            animator.repeatCount = ValueAnimator.INFINITE
            animator.start()
            RxBus.get().post(RedTips(true))
        }
    }

    //????????????????????????
    fun stopRedAnimation() {
        if (liveRedTips != null && liveRedTips.animation != null) {
            liveRedTips.clearAnimation()
        }
        setGone(liveRedTips)
        RxBus.get().post(RedTips(false))
    }


    /**
     * ?????????
     */
    private var showIt: Boolean = false
    private var homeLiveRedRoomBean: HomeLiveRedRoom? = null
    private var mOpenRedPopup: DialogRedPaper? = null
    fun initRedDialog(homeLiveRedRoom: HomeLiveRedRoom, isOpen: Boolean) {
        if (isActive()) {
            homeLiveRedRoomBean = homeLiveRedRoom
            if (mOpenRedPopup == null) {
                mOpenRedPopup = context?.let { DialogRedPaper(it) }
            }
            //?????????
            mOpenRedPopup?.setOnOpenClickListener {
                showIt = true
                mPresenter.getRed(homeLiveRedRoom.id.toString(), mOpenRedPopup!!)
            }
            mOpenRedPopup?.setOnDismissListener {
                if (showIt) {
                    mPresenter.homeLiveRedList(anchorId, true)
                    showIt = false
                }
//            if (isFullScreen()) {
//                val decorView = ScreenUtils.getDecorView(getPageActivity())
//                if (decorView != null) ScreenUtils.hideSysBar(getPageActivity(), decorView)
//            }
            }
            //???????????????
            liveRedTips?.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    if (!UserInfoSp.getIsLogin()) {
                        GlobalDialog.notLogged(requireActivity())
                        return@setOnClickListener
                    }
                    try {
                        if (UserInfoSp.getNobleLevel() > 0) {
                            mOpenRedPopup?.show()
                        } else {
                            val dialog = context?.let { it1 -> DialogVipTips(it1) }
                            dialog?.setCanCalClickListener {
                                if (!FastClickUtil.isFastClickSmall()) {
                                    Router.withApi(ApiRouter::class.java).toMineRecharge(0)
                                }
                            }
                            dialog?.setConfirmClickListener {
                                if (!FastClickUtil.isFastClickSmall()) {
                                    RxBus.get().post(HomeJumpToMineCloseLive(true))
                                }
                            }
                            dialog?.show()
                        }
                    } catch (e: Exception) {

                    }

                }
            }
            if (isOpen) {
                if (mOpenRedPopup?.isShowing == false) mOpenRedPopup?.show()
            }
        }
    }

    /**
     * ?????????socket
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

    //?????????
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
                    setGone(imgThree)
                    setGone(inputLayout)
                    inputPopWindow?.showKeyboard()
                }
            }, 50
        )
        inputPopWindow?.setOnDismissListener {
            setVisible(imgThree)
            setVisible(inputLayout)
        }
    }

    //????????????
    private fun showOrCloseFloatView() {
        if (popMenuManager == null) {
            popMenuManager = context?.let { LiveRoomPop(it) }
            popMenuManager?.setSendClickListener {
                if (it) {
                    //??????
                    RxBus.get().post(LiveVideoClose(it))
                } else {
                    //??????
                    RxBus.get().post(LiveVideoClose(it))
                }
            }
            //??????
            popMenuManager?.setClearClickListener {
                rvChatAdapter?.clear()
                rvChatAdapter?.notifyDataSetChanged()
            }
            //????????????
            popMenuManager?.setManagerClearClickListener {
                mPresenter.managerClear(anchorId)
            }

        }
        isShowPop = if (!isShowPop) {
            popMenuManager!!.showAtLocationBottom(findView(R.id.floatButton), 5f)
            true
        } else {
            popMenuManager!!.dismiss()
            false
        }


    }


    //??????
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun isFirstRecharge(eventBean: IsFirstRecharge) {
        UserInfoSp.putIsFirstRecharge(eventBean.res)
        val par = imgRecharge.layoutParams as LinearLayout.LayoutParams
        if (eventBean.res) {
            imgRecharge.setBackgroundResource(R.mipmap.ic_live_first_recharge)
            par.height = ViewUtils.dp2px(25)
            imgRecharge.layoutParams = par
            val animator = ObjectAnimatorViw.tada(imgRecharge, 1f)
            animator.repeatCount = ValueAnimator.INFINITE
            animator.start()
        } else {
            imgRecharge.clearAnimation()
            imgRecharge.setBackgroundResource(R.mipmap.ic_live_chat_recharge)
            par.height = ViewUtils.dp2px(25)
            par.width = ViewUtils.dp2px(20)
            imgRecharge.layoutParams = par
        }
    }

    /**
     * ??????????????????
     */
    fun scrollToBottom(multiTypeAdapter: LiveRoomChatAdapter) {
        linearLayoutManager?.scrollToPosition(multiTypeAdapter.itemCount - 1)
        setGone(tvMoreInfo)
    }

    fun showToast() {
        if (!getScreenFull()) {
            ToastUtils.showToast("????????????")
        }
        if (bottomGiftWindow != null) {
            MineApi.getUserDiamond {
                onSuccess {
                    bottomGiftWindow?.setDiamond(it.diamond)
                    bottomGiftWindow?.hideLoading()
                }
            }
        }
    }

    /**
     * ??????????????????
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDataMineUserDiamond(eventBean: HomeLiveAnimatorBean) {
        mPresenter.homeLiveSendGift(anchorId, eventBean.gift_id, eventBean.giftCount, eventBean)
    }


    private fun initAnim() {
        //?????????????????????
        rewardLayout.setGiftItemRes(R.layout.gift_animation_item)
        rewardLayout.setGiftAdapter(RewardAdapter(requireActivity()))
    }

    /**
     * ??????????????????
     */
    fun showAnim(eventBean: HomeLiveAnimatorBean) {
        val time: Long
        //??????3???
        when (eventBean.gift_id) {
            "18",
            "19",
            "20",
            "22",
            "24",
            "25",
            "26",
            "30",
            "31",
            "32",
            "33",
            "47",
            "48",
            "49",
            "50" -> time = 3000
            "16",     //????????????
            "17",    //????????????
            "21",   //??????
            "28",  //?????????
            "29"  //??????
            -> time = 5000

            else -> time = 8000
        }
        try {
            val bean = SendGiftBean(
                eventBean.user_id.toInt(), eventBean.gift_id.toInt(),
                eventBean.user_name, eventBean.git_name, eventBean.gift_icon,
                eventBean.user_icon, eventBean.giftCount, time
            )
            rewardLayout.put(bean)
        } catch (e: Exception) {
            e.printStackTrace()
            ToastUtils.showToast(e.toString())
        }
    }


    /**
     * ??????????????????
     */
    fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
        if (recyclerView == null) return false
        return (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
    }

    //??????
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun gift(eventBean: Gift) {
        eventBean.gift?.let { mPresenter.notifySocket(it) }
    }

    //??????????????????
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun sendDanMu(eventBean: SendDanMu) {
        mPresenter.sendMessage(eventBean.content)
    }

    // @?????????
    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun call(eventBean: LiveCallPersonal) {
        if (!UserInfoSp.getIsLogin()) {
            GlobalDialog.notLogged(requireActivity())
        } else showInput("@" + eventBean.name + " ")

    }

    //????????????
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun shareBet(eventBean: LotteryShareBet) {
        if (eventBean.reset) {
            mPresenter.shareOrder(eventBean.order)
            RxBus.get().post(LotteryResetDiamond(true))
        }
    }

    override fun onPause() {
        super.onPause()
        if (rewardLayout != null) {
            rewardLayout.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rewardLayout != null) {
            rewardLayout.onResume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.stopConnect()
        mNetWatchdog?.stopWatch()
    }

    fun getScreenFull(): Boolean {
        return this.activity?.requestedOrientation == Configuration.ORIENTATION_PORTRAIT
    }

    /**
     * ?????? vip ??????????????????
     */

    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun enter(eventBean: EnterVipNormal) {
        mPresenter.setVip(eventBean.vip, imgEnterImg)
        if (eventBean.vip == "0") {
            tvEnterContent.text = "?????? " + UserInfoSp.getUserNickName() + " ???????????????"
            ObjectAnimatorViw.setShowAnimation(linEnter, 1000)
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (isVisible && linEnter != null) {
                        linEnter.post { ObjectAnimatorViw.setHideAnimation(linEnter, 1000) }
                    }
                    this.cancel()
                }
            }, 3000)
        } else {
//            context?.let { GlideUtil.loadCircleImage(it,UserInfoSp.getUserPhoto(),enterImg,true) }
//            when(eventBean.vip){
//                "1","2","3" -> tvEnterText?.setTextColor(ViewUtils.getColor(R.color.white))
//                "4" -> tvEnterText?.setTextColor(Color.parseColor("#FFBCD7FA"))
//                "5" -> tvEnterText?.setTextColor(Color.parseColor("#FF7EFCEC"))
//                "6" -> tvEnterText?.setTextColor(Color.parseColor("#FFFD41FB"))
//                "7" -> tvEnterText?.setTextColor(Color.parseColor("#FFEBFE4D"))
//            }
//            when(eventBean.vip){
//                "1" -> {
//                    tvEnterText?.text = "???????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_1)
//                }
//                "2" -> {
//                    tvEnterText?.text = "???????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_2)
//                }
//                "3" -> {
//                    tvEnterText?.text = "???????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_3)
//                }
//                "4" -> {
//                    tvEnterText?.text = "????????????????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_4)
//                }
//                "5" -> {
//                    tvEnterText?.text = "????????????????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_5)
//                }
//                "6" -> {
//                    tvEnterText?.text = "????????????????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_6)
//                }
//                "7" -> {
//                    tvEnterText?.text = "????????????????????????????????????"
//                    enterBak.setImageResource(R.mipmap.ic_enter_7)
//                }
//            }
//
//            AnimUtils.getInAnimation(context!!, tvVipEnter)
//            Timer().schedule(object : TimerTask() {
//                override fun run() {
//                    if (view != null && tvVipEnter != null) {
//                        tvVipEnter?.post { AnimUtils.getOutAnimation(context!!, tvVipEnter) }
//                        this.cancel()
//                    }
//                }
//            }, 5000)
        }
    }


    companion object {
        fun newInstance(
            anchorId: String,
            liveState: String,
            name: String,
            id: String,
            type: String
        ): LiveRoomChild1 {
            val fragment = LiveRoomChild1()
            val bundle = Bundle()
            bundle.putString("LIVE_ROOM_ANCHOR_ID", anchorId)
            bundle.putString("LIVE_ROOM_ANCHOR_STATUE", liveState)
            bundle.putString("LIVE_ROOM_NICK_NAME", name)
            bundle.putString("LIVE_ROOM_LOTTERY_ID", id)
            bundle.putString("LIVE_ROOM_LOTTERY_TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }


}