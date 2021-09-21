package com.home

import com.customer.ApiRouter
import com.customer.component.dialog.GlobalDialog
import com.customer.data.game.GameApi
import com.customer.data.home.HomeApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.base.xui.widget.banner.widget.banner.BannerItem
import com.lib.basiclib.utils.ToastUtils
import com.rxnetgo.rxcache.CacheMode
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 *
 * @ Author  QinTian
 * @ Date  6/8/21
 * @ Describe
 *
 */
class HomePresenter : BaseMvpPresenter<HomeFragment>() {


    fun getAllData() {
        if (mView.isActive()) {
            val uiScope = CoroutineScope(Dispatchers.Main)
            uiScope.launch {
                val getHomeBannerResult = async { HomeApi.getHomeBannerResult(CacheMode.NONE) }

                val getHomeSystemNoticeResult =
                    async { HomeApi.getHomeSystemNoticeResult(CacheMode.NONE) }

                val getHomeGame  = async { HomeApi.getHomeGameNewResult() }

                val resultGetHomeBannerResult = getHomeBannerResult.await()
                val resultGetHomeSystemNoticeResult = getHomeSystemNoticeResult.await()
                val resultHomeGame = getHomeGame.await()

                resultGetHomeBannerResult.onSuccess {
                    val list = ArrayList<BannerItem>()
                    for (i in it) {
                        val item = BannerItem()
                        item.imgUrl = i.image_url
                        item.title = i.url
                        list.add(item)
                    }
                    mView.upDateBanner(list)
                }

                resultGetHomeSystemNoticeResult.onSuccess { mView.upDateSystemNotice(it) }

                resultHomeGame.onSuccess {  mView.upDateGame(it)}
                resultHomeGame.onFailed { ToastUtils.showToast(it.getMsg()) }
            }
        }
    }


    fun getHomeGame(){
        val resultGetHomeBannerResult = HomeApi.getHomeGameNewResult()
        resultGetHomeBannerResult.onSuccess {  mView.upDateGame(it)}
        resultGetHomeBannerResult.onFailed { ToastUtils.showToast(it.getMsg()) }
    }

    //获取新消息
    fun getNewMsg() {
        HomeApi.getIsNewMessage {
            onSuccess {
                if (mView.isActive()) {
                    if (it.msgCount > 0) {
                        mView.setVisible(mView.topDian)
                    } else {
                        mView.setGone(mView.topDian)
                    }
                    mView.msg1 = it.countList?.`_$0`?:"0"
                    mView.msg2 = it.countList?.`_$2`?:"0"
                    mView.msg3 = it.countList?.`_$3`?:"0"
                    mView.msg4 = it.countList?.`_$5`?:"0"
                }
            }
            onFailed {
                if (it.getCode() == 2003){
                    GlobalDialog.otherLogin(mView.requireActivity())
                }
                mView.setGone(mView.topDian)
            }
        }
    }


    fun getChessGame(game_id: String) {
        GameApi.get060(game_id) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }

        }
    }

    fun getAg() {
        GameApi.getAg {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }

        }
    }

    fun getMg(type:String){
//        GameApi.getMgDz(type) {
//            if (mView.isAdded) {
//                onSuccess {
//                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
//                    mView.hidePageLoadingDialog()
//                }
//                onFailed {
//                    ToastUtils.showToast(it.getMsg())
//                    mView.hidePageLoadingDialog()
//                }
//            }
//
//        }
    }

    fun getAgDz() {
        GameApi.getAgDZ {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getAgBgSx() {
        GameApi.getBgSx {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getBgFish(game_id: String) {
        GameApi.getBgFish(game_id) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getSb(game_id: String) {
        GameApi.getSb(game_id) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getIm(game_id: String) {
        GameApi.getIM(game_id) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getBing(game_id: String,game_type:String){
        GameApi.getBing(game_type) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getAe(game_id: String,game_type:String){
        GameApi.getAe(game_id,game_type) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getCmd(game_id: String,game_type:String){
        GameApi.getCmd(game_id,game_type) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getSbo(game_id:String,game_type:String){
        GameApi.getSbo(game_id,game_type) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getKy(game_id: String) {
        GameApi.getKy(game_id) {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    fun getAgHunter() {
        GameApi.getAgHunter {
            if (mView.isAdded) {
                onSuccess {
                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                    mView.hidePageLoadingDialog()
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }
}