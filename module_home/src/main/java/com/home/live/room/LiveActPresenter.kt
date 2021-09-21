package com.home.live.room
import android.annotation.SuppressLint
import com.customer.component.dialog.DialogPassWordHor
import com.customer.component.dialog.GlobalDialog
import com.customer.data.EnterVip
import com.customer.data.EnterVipNormal
import com.customer.data.IsFirstRecharge
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.HomeLiveGiftList
import com.customer.data.login.LoginApi
import com.customer.data.mine.MineApi
import com.customer.utils.JsonUtils
import com.google.gson.JsonParser
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.act_live.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/6/11
 * @ Describe
 *
 */
class LiveActPresenter : BaseMvpPresenter<LiveRoomActivity>() {


    fun getAllData(anchorID: String) {
        if (mView.isActive()) {
            HomeApi.enterLiveRoom(anchorId = anchorID) {
                onSuccess {
                    mView.initThings(it)
                    mView.changeAttention(it.isFollow)
                    mView.hidePageLoadingDialog()
                    getIsFirstRecharge()
                    getUserBalance()
                    getUserVip()
                }
                onFailed {
                    mView.hidePageLoadingDialog()
                }
            }
        }
    }

    /**
     * 获取首冲
     */
    private fun getIsFirstRecharge() {
        LoginApi.getIsFirstRecharge(UserInfoSp.getUserId()) {
            onSuccess {
                if (it.isfirst == "0") {
                    UserInfoSp.putIsFirstRecharge(true)
                    RxBus.get().post(IsFirstRecharge(true))
                } else {
                    UserInfoSp.putIsFirstRecharge(false)
                    RxBus.get().post(IsFirstRecharge(false))
                }
            }
            onFailed {
                if (mView.isActive()) {
                    RxBus.get().post(IsFirstRecharge(true))
                }
            }
        }
    }

    //获取余额
    @SuppressLint("SetTextI18n")
    fun getUserBalance() {
        if (mView.isActive()) {
            MineApi.getUserBalance {
                onFailed { error ->
                    GlobalDialog.showError(mView, error, horizontal = false)
                }
            }
        }
    }

    //vip等级
    private fun getUserVip() {
                  if (UserInfoSp.getNobleLevel() == 0 )  {
                      RxBus.get().post(EnterVipNormal(UserInfoSp.getNobleLevel().toString()))
                  }else{
                      mView.enterVp(EnterVip(UserInfoSp.getNobleLevel().toString(),UserInfoSp.getUserPhoto().toString() ))
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

    //发红包
    fun homeLiveSendRedEnvelope(
        anchorId: String,
        amount: String,
        num: String,
        text: String,
        password: String,
        passWordDialog: DialogPassWordHor
    ) {
        HomeApi.homeLiveSendRedEnvelope(anchorId, amount, num, text, password) {
            onSuccess {
                //通知socket
//                RxBus.get().post(Gift(LiveRoomChatPresenterHelper.getGifParams(anchorId, "4", it.rid, "", amount, num, text, "", "")))
                passWordDialog.showOrHideLoading()
                passWordDialog.dismiss()
                ToastUtils.showToast("红包发送成功")
            }
            onFailed {
                passWordDialog.showOrHideLoading()
                passWordDialog.dismiss()
                GlobalDialog.showError(mView, it, true)
            }
        }
    }

    //获取礼物列表
    fun getGiftList() {
        HomeApi.getGiftList {
            if (mView.isActive()) {
                onSuccess { result ->
                    val json = JsonParser.parseString(result).asJsonObject
                    val typeList = json.get("typeList").asJsonObject
                    val data = json.get("data").asJsonObject
                    val type = arrayListOf<String>()
                    val content = ArrayList<List<HomeLiveGiftList>>()
                    repeat(typeList.size()) {
                        val realPosition = (it + 1).toString()
                        type.add(typeList.get(realPosition).asString)
                        val res = data.get(realPosition).asJsonArray
                        val bean = arrayListOf<HomeLiveGiftList>()
                        for (op in res) {
                            val beanData = JsonUtils.fromJson(op, HomeLiveGiftList::class.java)
                            bean.add(beanData)
                        }
                        content.add(bean)
                    }
                    if (mView.bottomHorGiftWindow != null) {
                        mView.bottomHorGiftWindow?.setData(type, content)
                    }
                }
                onFailed {
                    GlobalDialog.showError(mView, it)
                }
            }
        }
    }

}