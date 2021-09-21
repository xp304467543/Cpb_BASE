package com.home.live.room

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.Gravity
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.customer.base.BaseNormalMvpActivity
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.component.input.InputPopWindowHor
import com.customer.data.DanMu
import com.customer.data.EnterVip
import com.customer.data.SendDanMu
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.HomeLiveEnterRoomResponse
import com.customer.data.home.SportLiveInfo
import com.customer.utils.SvgaUtils
import com.customer.utils.WindowPermissionCheck
import com.glide.GlideUtil
import com.home.R
import com.home.live.children.util.LiveRoomVipEnterTask
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.adapter.BaseFragmentPageAdapter
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.anno.RouterAnno
import cuntomer.constant.ApiConstant
import kotlinx.android.synthetic.main.act_sport_live.*
import kotlinx.android.synthetic.main.act_sport_live.noAnchor
import kotlinx.android.synthetic.main.act_sport_live.noAnchorBack
import kotlinx.android.synthetic.main.act_sport_live.svgaImage

/**
 *
 * @ Author  QinTian
 * @ Date  6/17/21
 * @ Describe
 *
 */
@RouterAnno(host = "Home", path = "liveSport")
class LiveSportRoomAct : BaseNormalMvpActivity<LiveSportRoomActPresenter>() {


    lateinit var liveSportInfo: SportLiveInfo
    lateinit var anchorId: String

    private var inputPopWindow: InputPopWindowHor? = null

    lateinit var svgaUtils: SvgaUtils

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LiveSportRoomActPresenter()

    override fun getContentResID() = R.layout.act_sport_live

    override fun isRegisterRx() = true

    override fun initContentView() {
        liveSportInfo = intent.getParcelableExtra("liveSportInfo")
        anchorId = liveSportInfo.anchor_id ?: ""
        val fragments = arrayListOf<Fragment>(
            LiveSportRoomChildChat.newInstance(liveSportInfo),
            LiveSportRoomChildInfo.newInstance(liveSportInfo)
        )
        vpSportLive.adapter = BaseFragmentPageAdapter(supportFragmentManager, fragments)
        LiveSportRoomActHelper.initHelper(this, mSportVideoView)
        initSvga()
    }


    override fun initData() {
        showPageLoadingDialog()
        mPresenter.getAllData(anchorId)

    }

    override fun initEvent() {
        initControllerEvent()
        vpSportLive.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeBak(position)
            }

        })
        tvChat.setOnClickListener {
            vpSportLive.currentItem = 0
            changeBak(0)
        }
        tvInfo.setOnClickListener {
            vpSportLive.currentItem = 1
            changeBak(1)
        }
        noAnchorBack?.setOnClickListener {
            LiveSportRoomActHelper.mPIPManager?.forceReset()
            finish()
        }
        LiveSportRoomActHelper.titleView.btAttention.setOnClickListener {
            if (UserInfoSp.getUserType() == "4") {
                LiveSportRoomActHelper.bottomController.toggleFullScreen()
                DialogTry(this).show()
                return@setOnClickListener
            }
            if (LiveSportRoomActHelper.isLogin(this)) goAttention()
        }
        LiveSportRoomActHelper.bottomController.tvInput.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(this)
                return@setOnClickListener
            }
            if (inputPopWindow == null) {
                inputPopWindow = InputPopWindowHor(this)
                inputPopWindow?.setOnTextSendListener {
                    RxBus.get().post(SendDanMu(it))
                    inputPopWindow?.dismiss()
                }
            }
            inputPopWindow?.showAtLocation(window?.decorView?.rootView, Gravity.NO_GRAVITY, 0, 0)
            window.decorView.rootView.postDelayed(
                {
                    if (inputPopWindow != null) {
                        inputPopWindow?.showKeyboard()
                    }
                }, 50
            )
            inputPopWindow?.setOnDismissListener {
            }
        }
    }

    //关注取关
    private fun goAttention() {
        HomeApi.attentionAnchorOrUser(liveSportInfo.anchor_id ?: "", "") {
            onSuccess { LiveSportRoomActHelper.titleView.setIsAttention(it.isFollow) }
            onFailed { GlobalDialog.showError(this@LiveSportRoomAct, it, isFullScreen()) }
        }
    }

    /**
     * 初始化svga
     */
    private fun initSvga() {
        svgaUtils = SvgaUtils(this, svgaImage)
        svgaUtils.initAnimator()
    }

    var mp4 = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8"

    @SuppressLint("SetTextI18n")
    fun initThings(data: HomeLiveEnterRoomResponse) {
        LiveSportRoomActHelper.titleView.setIsAttention(data.isFollow)
        LiveSportRoomActHelper.titleView.setInfo(
            data.nickname,
            data.online,
            data.avatar,
            data.anchor_id
        )
        LiveSportRoomActHelper.bottomController.setInfo(data.nickname, data.online, data.anchor_id)
        if (data.live_status == "1") {
            setGone(noAnchor)
            data.liveInfo?.get(2)?.liveUrl?.originPullUrl?.let {
                if (ApiConstant.isTest) {
                    LiveSportRoomActHelper.videoView?.setUrl(mp4)
                } else LiveSportRoomActHelper.videoView?.setUrl(it)
                LiveSportRoomActHelper.videoView?.start()
            }
        } else {
            setVisible(noAnchor)
            try {
                tvNameLeft.text = data.race_config?.homesxname + "(主)"
                tvNameRight.text = data.race_config?.awaysxname + "(客)"
                tvTeamInfo.text =
                    data.race_config?.simpleleague + " 【" + data.race_config?.matchtime + "】"
                GlideUtil.loadSportLiveIcon(this, data.race_config?.homelogo, imgLeft)
                GlideUtil.loadSportLiveIcon(this, data.race_config?.awaylogo, imgRight)
                tvSc2.text = data.race_config?.homescore + ":" + data.race_config?.awayscore
                if (data.race_config?.homehalfscore != null && data.race_config?.homehalfscore != "null") {
                    tvSc1.text = "(" + data.race_config?.homehalfscore + ":" + data.race_config?.awayhalfscore + ")"
                }else tvSc1.text = "(" + data.race_config?.homescore + ":" + data.race_config?.awayscore + ")"

            } catch (e: Exception) {
            }
        }
    }

    private fun initControllerEvent() {
        LiveSportRoomActHelper.titleView.backImg.setOnClickListener {
            backListener()
        }
        LiveSportRoomActHelper.prepareView.imgExit.setOnClickListener {
            backListener()
        }

        LiveSportRoomActHelper.bottomController.iv_danmu.setOnClickListener {
            if (LiveSportRoomActHelper.bottomController.iv_danmu.isSelected) {
                LiveSportRoomActHelper.bottomController.iv_danmu.isSelected = false
                LiveSportRoomActHelper.hideDanMu()
                UserInfoSp.putDanMuSwitch(false)
            } else {
                LiveSportRoomActHelper.bottomController.iv_danmu.isSelected = true
                LiveSportRoomActHelper.showDanMu()
                UserInfoSp.putDanMuSwitch(true)
            }
        }
    }


    private fun backListener() {
        if (LiveSportRoomActHelper.titleView.mControlWrapper.isFullScreen) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            LiveSportRoomActHelper.titleView.mControlWrapper.stopFullScreen()
        } else if (LiveSportRoomActHelper.videoView?.isPlaying == true) {
            if (!isDestroyed && WindowPermissionCheck.checkPermission(this)) {
                LiveSportRoomActHelper.mPIPManager?.startFloatWindow()
                LiveSportRoomActHelper.mPIPManager?.resume()
                finish()
            } else {
                LiveSportRoomActHelper.mPIPManager?.forceReset()
                finish()
            }
        } else {
            LiveSportRoomActHelper.mPIPManager?.forceReset()
            finish()
        }
    }

    fun changeBak(pos: Int) {
        when (pos) {
            0 -> {
                tvInfo.background = null
                tvChat.background = ViewUtils.getDrawable(R.mipmap.ic_sport_live)
                tvChat.setTextColor(ViewUtils.getColor(R.color.white))
                tvInfo.setTextColor(ViewUtils.getColor(R.color.color_666666))
            }

            1 -> {
                tvChat.background = null
                tvInfo.background = ViewUtils.getDrawable(R.mipmap.ic_sport_live)
                tvInfo.setTextColor(ViewUtils.getColor(R.color.white))
                tvChat.setTextColor(ViewUtils.getColor(R.color.color_666666))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        WindowPermissionCheck.onActivityResult(this, requestCode, resultCode, data, null)
    }

    //收弹幕
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun showRed(eventBean: DanMu) {
        if (isFullScreen() && UserInfoSp.getDanMuSwitch()) {
            LiveSportRoomActHelper.addDanMu(eventBean)
        }
    }

    /**
     * Vipjinc
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun enterVips(eventBean: EnterVip) {
        enterVp(eventBean)
    }

    fun enterVp(eventBean: EnterVip) {
        //vip进场 进入vip队列
        this.let {
            if (isActive()) {
                LiveRoomVipEnterTask(
                    it,
                    eventBean.vip + "," + eventBean.avatar,
                    tvVipEnter,
                    tvEnterText,
                    enterImg,
                    enterBak
                ).doTask()
            }
        }
    }

    override fun onBackPressed() {
        backListener()
        if (LiveSportRoomActHelper.mPIPManager != null && LiveSportRoomActHelper.mPIPManager?.onBackPress() == false) {
            if (LiveSportRoomActHelper.videoView?.isPlaying == true) {
                if (WindowPermissionCheck.checkPermission(this)) {
                    LiveSportRoomActHelper.mPIPManager?.startFloatWindow()
                    LiveSportRoomActHelper.mPIPManager?.resume()
                    finish()
                } else {
                    LiveSportRoomActHelper.mPIPManager?.forceReset()
                    finish()
                }
            } else {
                LiveSportRoomActHelper.mPIPManager?.forceReset()
                super.onBackPressed()
            }
        }
    }

}