package com.home.video.play

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.customer.component.dialog.DialogMovieTips
import com.customer.component.dialog.DialogReCharge
import com.customer.data.HomeJumpToMine
import com.customer.data.video.MovieApi
import com.customer.data.video.VideoPlay
import com.customer.utils.JsonUtils
import com.home.R
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.act_video_play.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/31
 * @ Describe
 *
 */
class VideoPlayActPresenter : BaseMvpPresenter<VideoPlayAct>() {


    fun getVideoAddress(id: Int,isAdapter:Boolean = false) {
        MovieApi.getPlayInfo(id) {
            if (mView.isActive()) {
                onSuccess {
                    if (mView.isActive()){
                        mView.setGone(mView.tvHolder)
                        mView.canPlay = true
                        mView.initVideoInfo(it)
                    }
                }
                onFailed {
                    if (it.getCode() == -1){
                        if (mView.isActive()){
                            mView.url = "-1"
                            mView.title = "-1"
                            mView.canPlay = false
                          val result = it.getData()?.let { it1 -> JsonUtils.fromJson(it1,VideoPlay::class.java) }
//                            if (!isAdapter)
                            result?.let { it1 -> mView.initVideoInfo(it1,false) }
                            val content = SpannableString("您今天观看次数已用完，观看本片需要支付188钻石，根据您当前的贵族等级只需要支付"+result?.discount_price+"钻石即可")
                            val start = "您今天观看次数已用完，观看本片需要支付188钻石，根据您当前的贵族等级只需要支付".length
                            val end = ("您今天观看次数已用完，观看本片需要支付188钻石，根据您当前的贵族等级只需要支付"+result?.discount_price).length
                            content.setSpan(ForegroundColorSpan(Color.parseColor("#ff513e")), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                            val dialogGlobalTips = DialogMovieTips(mView,"提示",content,"去兑换","钻石观看","建议先收藏哦~")
                            dialogGlobalTips.setCanCalClickListener {
                                if (!FastClickUtil.isFastClick()){
                                    RxBus.get().post(HomeJumpToMine(true))
                                    mView.finish()
                                }
                            }
                            dialogGlobalTips.setConfirmClickListener {
                                if (!FastClickUtil.isFastClick()){
                                        MovieApi.getBuyVideo(result?.id?:0){
                                            onSuccess {
                                                if (mView.isActive()){
//                                                    result?.let { it1 -> mView.initVideoInfo(it1,false) }
                                                    mView.isBuy = 1
                                                    mView.mVideoPlayerView.setUrl(result?.play_url)
                                                    if (mView.mVideoPlayerView.isPlaying)mView.mVideoPlayerView.replay(true)
                                                    mView.mVideoPlayerView.start()
                                                }
                                            }
                                            onFailed {ex->
                                                if (mView.isActive()){
                                                    if (ex.getCode() == 0){
                                                        dialogGlobalTips.dismiss()
                                                        val dialogRecharge = DialogReCharge(mView,true)
                                                        dialogRecharge.setOnSendClickListener {
                                                            if (!FastClickUtil.isFastClick()){
                                                                RxBus.get().post(HomeJumpToMine(true))
                                                                mView.finish()
                                                            }
                                                        }
                                                        dialogRecharge.show()
                                                    }else ToastUtils.showToast(it.getMsg())
                                                }
                                            }
                                        }
                                }
                            }
                            dialogGlobalTips.show()
                        }
                    }else ToastUtils.showToast(it.getMsg())
                }
            }

        }
    }

    fun getLike(id: Int) {
        MovieApi.getYouLike(id) {
            if (mView.isActive()) {
                onSuccess { if (!it.isNullOrEmpty()) mView.adapter?.refresh(it) else ToastUtils.showToast("暂无数据") }
                onFailed { ToastUtils.showToast(it.getMsg())  }
            }
        }
    }

    fun zan(id: Int,isZan:Int){
        MovieApi.getZan(id,isZan){
            if (mView.isActive()) {
                onSuccess {
                    mView.initZan(it)
                }
                onFailed {ToastUtils.showToast(it.getMsg()) }
            }
        }
    }

    fun collect(id: Int){
        MovieApi.getCollect(id){
            if (mView.isActive()) {
                onSuccess {
                    if (mView.isActive()){
                        if (it.iscollect == 1){
                            mView.imgCollect.setImageResource(R.mipmap.ic_movie_collect)
                        }else  mView.imgCollect.setImageResource(R.mipmap.ic_movie_un_collect)
                    }
                }
                onFailed {ToastUtils.showToast(it.getMsg()) }
            }
        }
    }
}