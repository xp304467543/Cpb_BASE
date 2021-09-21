package com.home.old

import android.annotation.SuppressLint
import com.customer.data.UserInfoSp
import com.customer.data.mine.MineApi
import com.customer.data.video.MovieApi
import com.customer.data.video.MovieType
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_home_video.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/14
 * @ Describe
 *
 */
class HomeVideoPresenter : BaseMvpPresenter<HomeVideoFragment>() {

    fun getTitle() {
        MovieApi.getMovieType {
            if (mView.isActive()) {
                onSuccess {
                    mView.typeList = it
                    getBanner(it)
                    mView.setGone(mView.layoutHolder)
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.setVisible(mView.layoutHolder)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun getUserInfo() {
        MineApi.getUserInfo {
            onSuccess {
                if (mView.isActive()){
                    mView.tvTimeVideo?.text = "" + it.free_watch_nums + "/" + it.sum_watch_nums
                    if (it.free_watch_nums == "无限次"){
                        mView.tvTimeVideo?.text = "无限次"
                    }
                    UserInfoSp.setVipLevel(it.vip)
                    UserInfoSp.setNobleLevel(it.noble)
                }
            }

        }
    }
    private fun getBanner(list: List<MovieType>) {
        MovieApi.getMovieBanner {
            if (mView.isActive()){
                onSuccess {
                    UserInfoSp.putMovieBanner(it.banner_movie_play?.get(0)?.image_url.toString()+","+it.banner_movie_play?.get(0)?.url.toString())
                    mView.initTabViewData(list,it)
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg())
                    mView.initTabViewData(list,null)
                }
            }
        }
    }
}