package com.home.live.room

import com.customer.data.EnterVip
import com.customer.data.EnterVipNormal
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.act_live.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/17/21
 * @ Describe
 *
 */
class LiveSportRoomActPresenter : BaseMvpPresenter<LiveSportRoomAct>() {

    fun getAllData(anchorID: String) {
        if (mView.isActive()) {
            HomeApi.enterLiveRoom(anchorId = anchorID) {
                onSuccess {
                    mView.initThings(it)
                    getUserVip()
                }
                onFailed {
                    mView.hidePageLoadingDialog()
                    ToastUtils.showToast(it.getMsg())
                }
            }
        }
    }

    //vip等级
    private fun getUserVip() {
        if (UserInfoSp.getNobleLevel() == 0 )  {
            RxBus.get().post(EnterVipNormal(UserInfoSp.getNobleLevel().toString()))
        }else{
            mView.enterVp(
                EnterVip(
                    UserInfoSp.getNobleLevel().toString(),
                    UserInfoSp.getUserPhoto().toString() )
            )
            when(UserInfoSp.getNobleLevel()){
                1 -> mView.svgaUtils.startAnimator("vip_1",mView.svgaImage)
                2 -> mView.svgaUtils.startAnimator("vip_2",mView.svgaImage)
                3 -> mView.svgaUtils.startAnimator("vip_3",mView.svgaImage)
                4 -> mView.svgaUtils.startAnimator("vip_4",mView.svgaImage)
                5 -> mView.svgaUtils.startAnimator("vip_5",mView.svgaImage)
                6 -> mView.svgaUtils.startAnimator("vip_6",mView.svgaImage)
                7 -> mView.svgaUtils.startAnimator("vip_7",mView.svgaImage)
            }

        }
    }
}