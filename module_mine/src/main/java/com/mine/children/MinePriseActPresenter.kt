package com.mine.children

import android.annotation.SuppressLint
import android.os.Handler
import com.customer.component.dialog.DialogGlobalTips
import com.customer.component.luckpan.LuckItemInfo
import com.customer.data.home.HomeApi
import com.customer.data.home.PanGift
import com.customer.data.mine.MineApi
import com.customer.utils.JsonUtils
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.act_prise.*
import java.math.BigDecimal
import java.math.BigInteger

/**
 *
 * @ Author  QinTian
 * @ Date  7/1/21
 * @ Describe
 *
 */
class MinePriseActPresenter : BaseMvpPresenter<MinePriseAct>() {


    //获取钻石
    @SuppressLint("SetTextI18n")
    fun getUserDiamond() {
        MineApi.getUserDiamond {
            onSuccess {
                if (mView.isActive()) {
                    mView.userDiamond = BigDecimal(it.diamond)
                    if (mView.currentSel == 0) mView.tvInfo6.text = mView.userDiamond.toString()
                }
            }
            onFailed {
//                getUserDiamondFailedListener?.invoke(it)
            }
        }
    }


    fun getPrise() {
        HomeApi.getPrise(mView.currentSel) {
            onSuccess {
                if (mView.isActive()) {
                    if (mView.currentSel == 0) {
                        //钻石盘
                        if (it.status == 1) {
                            if (mView.isActive() && !mView.isFinishing) {
                                mView.luckViewZs?.postDelayed({
                                    mView.luckViewZs.setStop(mView.idListZs?.indexOf(it.id) ?: 0)
                                    val text =
                                        "恭喜您获得 " + it.name + " *" + it.number + ",稍后发放奖品请耐心等待"
                                   val dialog = DialogGlobalTips(
                                        mView,
                                        "中奖啦!",
                                        "知道了",
                                        "",
                                        text
                                    )
                                    if (mView.isActive())dialog.show()
                                }, 3000)
                            }
                        } else {
                            if (mView.isActive() && !mView.isFinishing) {
                                mView.luckViewZs?.postDelayed({
                                    mView.luckViewZs.setStop(mView.idListZs?.indexOf(it.id) ?: 0)
                                  val dialog =   DialogGlobalTips(
                                        mView,
                                        "很遗憾!",
                                        "知道了",
                                        "",
                                        "此次没有中奖再接再厉"
                                    )
                                    if (mView.isActive())dialog.show()
                                }, 3000)
                            }
                        }
                        if (mView.isActive() && !mView.isFinishing) {
                            mView.currentTime = it.time_now ?: 0
                            mView.setTimes()
                            getUserDiamond()
                        }
                    } else {
                        if (it.status == 1) {
                            if (mView.isActive() && !mView.isFinishing) {
                                Handler().postDelayed({
                                    mView.luckViewYe.setStop(mView.idListYe?.indexOf(it.id) ?: 0)
                                    val text =
                                        "恭喜您获得 " + it.name + " *" + it.number + ",稍后发放奖品请耐心等待"
                                  val dialog =  DialogGlobalTips(
                                        mView,
                                        "中奖啦!",
                                        "知道了",
                                        "",
                                        text
                                    )
                                    if (mView.isActive())dialog.show()
                                }, 3000)
                            }
                        } else {
                            if (mView.isActive() && !mView.isFinishing) {
                                Handler().postDelayed({
                                    mView.luckViewYe.setStop(mView.idListYe?.indexOf(it.id) ?: 0)
                                   val dialog  = DialogGlobalTips(
                                        mView,
                                        "很遗憾!",
                                        "知道了",
                                        "",
                                        "此次没有中奖再接再厉"
                                    )
                                    if (mView.isActive())dialog.show()
                                }, 3000)
                            }
                        }
                        if (mView.isActive() && !mView.isFinishing) {
                            mView.userBalance = it.after_money
                            mView.setTimes()
                        }
                    }
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

    fun getSinglePrise() {
        HomeApi.gePanSinglePrise {
            onSuccess {
                if (!mView.isActive()) {
                    mView.singlePrise = it.unit_price ?: BigDecimal(2)
                }

            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

    fun updateNotice() {
        HomeApi.getPanGiftMessage {
            onSuccess { data ->
                if (mView.isActive()) {
                    val daNotice = arrayListOf<String>()
                    if (data.isNotEmpty()) {
                        for (item in data) {
                            daNotice.add(item.phone + " 抽中 " + item.name + " x" + item.number)
                        }
                    } else {
                        daNotice.add("暂无中奖信息")
                    }
                    mView.tvNoticeMassages?.speed = 3
                    mView.tvNoticeMassages?.startSimpleRoll(daNotice)
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }


    fun getPanGiftYe() {
        //转盘礼物
        HomeApi.gePanGift(1) {
            onSuccess {
                if (mView.isActive()) {
                    val result = JsonUtils.fromJson(it, PanGift::class.java)
                    mView.total = result.data.size
                    mView.itemsYe = arrayListOf()
                    mView.idListYe = arrayListOf()
                    val urlList = arrayListOf<String>()
                    for (i in result.data) {
                        val luckItem = LuckItemInfo()
                        luckItem.prize_name = i.name
                        luckItem.prize_num = i.number ?: i.prize
                        mView.itemsYe?.add(luckItem)
                        mView.idListYe?.add(i.id ?: "")
                        urlList.add(i.icon ?: "")
                    }
                    mView.initBitMapYe(urlList)
                    mView.userBalance = result.user_money ?: BigDecimal(BigInteger.ZERO)
                    mView.setTimes()
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }


    fun getPanGiftZs() {
        //转盘礼物
        HomeApi.gePanGift(0) {
            onSuccess {
                if (mView.isActive()) {
                    val result = JsonUtils.fromJson(it, PanGift::class.java)
                    mView.total = result.data.size
                    mView.itemsZs = arrayListOf()
                    mView.idListZs = arrayListOf()
                    val urlList = arrayListOf<String>()
                    for (i in result.data) {
                        val luckItem = LuckItemInfo()
                        luckItem.prize_name = i.name
                        luckItem.prize_num = i.number ?: i.prize
                        mView.itemsZs?.add(luckItem)
                        mView.idListZs?.add(i.id ?: "")
                        urlList.add(i.icon ?: "")
                    }
                    mView.initBitMapZs(urlList)
                    mView.currentTime = result.draws_num ?: 0
                    mView.setTimes()
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }
}