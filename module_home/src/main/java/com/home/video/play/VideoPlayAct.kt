package com.home.video.play

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.customer.ApiRouter
import com.customer.component.dialog.DialogDownLoad
import com.customer.component.dialog.DialogMovieTips
import com.customer.component.dialog.DialogReCharge
import com.customer.component.dialog.GlobalDialog
import com.customer.data.HomeJumpToMine
import com.customer.data.UserInfoSp
import com.customer.data.video.MovieApi
import com.customer.data.video.MovieZan
import com.customer.data.video.VideoPlay
import com.customer.player.video.controller.StandardVideoController
import com.customer.player.video.controller.VodTryView
import com.glide.GlideUtil
import com.home.R
import com.home.video.adapter.VideoSearchAdapter
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.xui.adapter.recyclerview.XLinearLayoutManager
import com.lib.basiclib.base.xui.widget.popupwindow.good.GoodView
import com.lib.basiclib.utils.*
import com.player.customize.ui.videocontroller.component.*
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_video_play.*


/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/31
 * @ Describe
 *
 */
@RouterAnno(host = "Home", path = "videoPlayer")
class VideoPlayAct : BaseMvpActivity<VideoPlayActPresenter>() {

    var adapter: VideoSearchAdapter? = null

    var url = "-1"

    var title = "-1"

    var isBuy = 0

    var canPlay = false

    var canPlayUrl ="-1"

    var canDiscountPrice = ""

    private var videoId: Int = -1

    private var videoTitle: String = "未知"

    private var thumb: ImageView? = null

    private var isRefreshAdapter: Boolean = true

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = VideoPlayActPresenter()

    override val layoutResID = R.layout.act_video_play

    override fun isOverride() = true

    override fun isSwipeBackEnable() = true

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(stateViewPlay)

        videoId = intent.getIntExtra("videoId", -1)
        videoTitle = intent.getStringExtra("videoTitle") ?: "未知"
        if (videoId != -1) {
            initVideo()
        } else ToastUtils.showToast("视频信息获取失败")

    }


    private fun initOther() {
        GlideUtil.loadImage(this, UserInfoSp.getMovieBanner().split(",")[0], imgBanner)
        if (UserInfoSp.getMovieBanner().contains(",")) {
            imgBanner.setOnClickListener {
                Router.withApi(ApiRouter::class.java)
                    .toGlobalWeb(UserInfoSp.getMovieBanner().split(",")[1])
            }
        } else {
            imgBanner.setOnClickListener {
                Router.withApi(ApiRouter::class.java).toGlobalWeb(UserInfoSp.getMovieBanner())
            }
        }

        smartRefreshLayoutPlay.setEnableRefresh(false)//是否启用下拉刷新功能
        smartRefreshLayoutPlay.setEnableLoadMore(false)//是否启用上拉加载功能
        smartRefreshLayoutPlay.setEnableOverScrollBounce(true)//是否启用越界回弹
        smartRefreshLayoutPlay.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        val layoutManager = XLinearLayoutManager(this)
        layoutManager.setScrollEnabled(false)
        rvPlay.layoutManager = layoutManager
        adapter = VideoSearchAdapter(this, true)
        rvPlay.adapter = adapter
        adapter?.setOnItemClickListener { itemView, item, position ->
            isRefreshAdapter = false
            titleViewController?.setTitle(item.title)
            item?.id?.let { mPresenter.getVideoAddress(it, true) }
        }
    }


    private var titleViewController: VodTitleView? = null
    private fun initVideo() {
        val controller = StandardVideoController(this)
        val completeView = VodCompleteView(this)
        val errorView = VodErrorView(this)
        val prepareView = VodPrepareView(this)
        val vodControlView = VodControlView(this)
        val gestureControlView = VodGestureView(this)
        titleViewController = VodTitleView(this)
        controller.addControlComponent(completeView)
        controller.addControlComponent(errorView)
        controller.addControlComponent(prepareView)
        controller.addControlComponent(titleViewController)
        controller.addControlComponent(vodControlView)
        controller.addControlComponent(gestureControlView)
        controller.addControlComponent(VodTryView(this))
        titleViewController?.setTitle(intent.getStringExtra("videoTitle"))
        thumb = prepareView.findViewById(R.id.thumb) //封面图
        controller.setCanChangePosition(true)
        controller.setEnableOrientation(false)
        controller.setEnableInNormal(true)
        mVideoPlayerView.setVideoController(controller)
        val playBtn = vodControlView.findViewById<ImageView>(R.id.iv_play)
        playBtn.setOnClickListener {
            if (canPlay){
                mVideoPlayerView.start()
            }else{
                bugDialog()
            }
        }
    }

    fun bugDialog(){
        val content = SpannableString("您今天观看次数已用完，观看本片需要支付188钻石，根据您当前的贵族等级只需要支付"+canDiscountPrice+"钻石即可")
        val start = "您今天观看次数已用完，观看本片需要支付188钻石，根据您当前的贵族等级只需要支付".length
        val end = ("您今天观看次数已用完，观看本片需要支付188钻石，根据您当前的贵族等级只需要支付$canDiscountPrice").length
        content.setSpan(ForegroundColorSpan(Color.parseColor("#ff513e")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val dialogGlobalTips = DialogMovieTips(this,"提示",content,"去兑换","钻石观看","建议先收藏哦~")
        dialogGlobalTips.setCanCalClickListener {
            if (!FastClickUtil.isFastClick()){
                RxBus.get().post(HomeJumpToMine(true))
               finish()
            }
        }
        dialogGlobalTips.setConfirmClickListener {
            if (!FastClickUtil.isFastClick()){
                MovieApi.getBuyVideo(videoId){
                    onSuccess {
                        if (isActive()){
                           isBuy = 1
                            mVideoPlayerView.setUrl(canPlayUrl)
                            if (mVideoPlayerView.isPlaying)mVideoPlayerView.replay(true)
                            mVideoPlayerView.start()
                        }
                    }
                    onFailed {ex->
                        if (isActive()){
                            if (ex.getCode() == 0){
                                dialogGlobalTips.dismiss()
                                val dialogRecharge = DialogReCharge(this@VideoPlayAct,true)
                                dialogRecharge.setOnSendClickListener {
                                    if (!FastClickUtil.isFastClick()){
                                        RxBus.get().post(HomeJumpToMine(true))
                                        finish()
                                    }
                                }
                                dialogRecharge.show()
                            }else ToastUtils.showToast(ex.getMsg())
                        }
                    }
                }
            }
        }
        dialogGlobalTips.show()
    }

    override fun initData() {
        initOther()
        mPresenter.getVideoAddress(videoId)
    }

    //
    override fun initEvent() {
        tvChange.setOnClickListener {
            if (!FastClickUtil.isFastClick()) mPresenter.getLike(videoId) else ToastUtils.showToast(
                "请勿重复点击"
            )
        }

        linZan.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(this)
                return@setOnClickListener
            }
            mPresenter.zan(videoId, 1)
        }
        linCai.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(this)
                return@setOnClickListener
            }
            mPresenter.zan(videoId, 0)
        }
        imgCollect.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(this)
                return@setOnClickListener
            }
            mPresenter.collect(videoId)
        }
        downloadVideo?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(this)
                    return@setOnClickListener
                }
                if (url != "-1") {
                    if (isBuy == 1) {
                        downLoadVideo()
                    } else {
                        val content = SpannableString("尊敬的会员,购买该影片才可以下载哦")
                        val dialogGlobalTips =
                            DialogMovieTips(this, "提示", content, "取消", "马上购买", "")
                        dialogGlobalTips.setConfirmClickListener {
                            if (!FastClickUtil.isFastClick()) {
                                MovieApi.getBuyVideo(videoId) {
                                    onSuccess {
                                        ToastUtils.showToast("购买成功")
                                        isBuy = 1
                                        downLoadVideo()
                                        dialogGlobalTips.dismiss()
                                    }
                                    onFailed {
                                        ToastUtils.showToast(it.getMsg())
                                    }
                                }
                            }
                        }
                        dialogGlobalTips.show()
                    }
                }
            }
        }
    }

    private fun downLoadVideo() {
        val dialog = DialogDownLoad(this,url,title,this)
        dialog.show()
    }


    fun initZan(data: MovieZan) {
        tvZan.text = data.praise.toString()
        tvCai.text = data.tread.toString()
        val mGoodView = GoodView(this)
        when (data.msg) {
            "点赞成功" -> {
                mGoodView.setText("+1")
                    .setTextColor(Color.parseColor("#f66467"))
                    .setTextSize(14)
                    .show(linZan)
                imgZan.setImageResource(R.mipmap.ic_movie_zan_red)
                imgCai.setImageResource(R.mipmap.ic_movie_down)
            }
            "取消点赞" -> {
                mGoodView.setText("-1")
                    .setTextColor(Color.parseColor("#AFAFAF"))
                    .setTextSize(14)
                    .show(linZan)
                imgZan.setImageResource(R.mipmap.ic_movie_zan)
            }
            "点踩成功" -> {
                mGoodView.setText("+1")
                    .setTextColor(Color.parseColor("#f66467"))
                    .setTextSize(14)
                    .show(linCai)
                imgCai.setImageResource(R.mipmap.ic_movie_down_red)
                imgZan.setImageResource(R.mipmap.ic_movie_zan)
            }
            "取消点踩" -> {
                mGoodView.setText("-1")
                    .setTextColor(Color.parseColor("#AFAFAF"))
                    .setTextSize(14)
                    .show(linCai)
                imgCai.setImageResource(R.mipmap.ic_movie_down)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun initVideoInfo(data: VideoPlay, isPlay: Boolean = true) {
        if (isActive()) {
            url = data.down_url ?: "-1"
            title = data.title ?: "未知"
            isBuy = data.isbuy ?: 0
            videoId = data.id ?:0
            canPlayUrl = data.play_url?:"-1"
            canDiscountPrice = data.discount_price
            when (data.state) {
                "1" -> {
                    imgZan.setImageResource(R.mipmap.ic_movie_zan_red)
                    imgCai.setImageResource(R.mipmap.ic_movie_down)
                }
                "2" -> {
                    imgZan.setImageResource(R.mipmap.ic_movie_zan)
                    imgCai.setImageResource(R.mipmap.ic_movie_down_red)
                }
                else -> {
                    imgZan.setImageResource(R.mipmap.ic_movie_zan)
                    imgCai.setImageResource(R.mipmap.ic_movie_down)
                }
            }

            if (data.iscollect == 0) imgCollect.setImageResource(R.mipmap.ic_movie_un_collect) else imgCollect.setImageResource(
                R.mipmap.ic_movie_collect
            )

            thumb?.let { Glide.with(this).load(data.cover).into(it) }
            if (isPlay) {
                mVideoPlayerView.setUrl(data.play_url)
                if (isRefreshAdapter) mVideoPlayerView.start() else mVideoPlayerView.replay(true)
            }
            tvVideoTitle.text = data.title
            tvTimeInfo.text = data.reads + "播放                " + TimeUtils.longToDateStringYYMMDD(
                data.shelftime ?: 0
            )
            tvZan.text = data.praise
            tvCai.text = data.tread
            if (isRefreshAdapter) adapter?.refresh(data.list)
        }
    }


    var isTrySee = true //标记试看
    override fun onResume() {
        super.onResume()
        if (mVideoPlayerView != null) {
            if (UserInfoSp.getIsLogin() || isTrySee) {
                mVideoPlayerView.resume()
                isTrySee = false
            }
        }
    }


    override fun onPause() {
        super.onPause()
        if (mVideoPlayerView != null) {
            mVideoPlayerView.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mVideoPlayerView != null) {
            mVideoPlayerView.release()
        }
    }

    override fun onBackPressed() {
        if (mVideoPlayerView == null || !mVideoPlayerView.onBackPressed()) {
            super.onBackPressed()
        }
    }
}