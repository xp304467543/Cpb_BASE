package com.home.game

import com.customer.ApiRouter
import com.customer.data.game.GameApi
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import com.xiaojinzi.component.impl.Router

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/19
 * @ Describe
 *
 */

class HomeGameRecentFragmentPresenter : BaseMvpPresenter<HomeGameRecentFragment>(){
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

    fun getBing(game_type:String){
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

    fun getMg(id:String) {
        GameApi.getMgDz(id,"slot") {
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