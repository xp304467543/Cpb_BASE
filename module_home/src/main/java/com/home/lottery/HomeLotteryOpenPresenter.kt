package com.home.lottery

import android.annotation.SuppressLint
import android.os.Handler
import com.customer.data.LotteryTypeSelect
import com.customer.data.lottery.LotteryApi
import com.customer.utils.countdowntimer.CountDownTimerSupport
import com.customer.utils.countdowntimer.OnCountDownTimerListener
import com.customer.utils.countdowntimer.lotter.LotteryTypeSelectUtil
import com.home.R
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import kotlinx.android.synthetic.main.act_lottery_open.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 *
 * @ Author  QinTian
 * @ Date  6/16/21
 * @ Describe
 *
 */
class HomeLotteryOpenPresenter : BaseMvpPresenter<HomeLotteryOpenAct>() {

    //获取彩种
    fun getLotteryType() {
        if (mView.isActive()) {
            val uiScope = CoroutineScope(Dispatchers.Main)

            uiScope.launch {

                val getLotteryType = async { LotteryApi.getLotteryType() }

                val resultGetLotteryType = getLotteryType.await()

                resultGetLotteryType.onSuccess {
                    if (it.isNotEmpty()) {
                        mView.initLotteryType(it)
                    }
                }
            }
        }
    }
    var mTimer: CountDownTimerSupport? = null
    var handler: Handler? = null
    var runnable: Runnable? = null
    var lotteryCurrent = "0"
    @SuppressLint("SetTextI18n")
    fun getLotteryOpenCode(lotteryId: String) {
        lotteryCurrent = lotteryId
        handler?.removeCallbacks(runnable)
        LotteryApi.getLotteryNewCode(lotteryId) {
            onSuccess {
                if (mView.isActive()) {
                    if (!mView.isInitBottom) {
                        RxBus.get().post(LotteryTypeSelect(lotteryCurrent, it.issue))
                        mView.isInitBottom = true
                    }
                    if (it.next_lottery_time?.toInt() ?: 0 > 1) {
                        if (mTimer == null) {
                            mTimer = CountDownTimerSupport()
                            mTimer?.setOnCountDownTimerListener(object : OnCountDownTimerListener {
                                override fun onTick(millisUntilFinished: Long) {
                                    setTime(millisUntilFinished)
                                }

                                override fun onFinish() {
                                    mTimer?.stop()
                                    if (mView.tvOpenTime != null) mView.tvOpenTime.text = "开奖中..."
                                    getLotteryOpenCode(lotteryCurrent)
                                    mView.setVisible(mView.tvOpenCodePlaceHolder)
                                }

                                override fun onCancel() {

                                }
                            })
                        } else mTimer?.stop()
                        mView.tvOpenCount.text = it.issue + " 期开奖结果"
                        //总时长 间隔时间
                        mTimer?.setMillisInFuture((it.next_lottery_time ?: 0) * 1000)

                        mTimer?.start()
                        setContainerCode(lotteryCurrent, it.code)
                        mView.setGone(mView.tvOpenCodePlaceHolder)
                    } else {
                        if (mView.isActive()) {
                            mView.tvOpenTime.text = "开奖中..."
                            mView.tvAtNext.text = mView.getString(R.string.lottery_next)
                            mView.tvOpenCount.text = it.issue + " 期开奖结果"
                            mView.setVisible(mView.tvOpenCodePlaceHolder)
                        }
                        handler = Handler()
                        runnable = Runnable { getLotteryOpenCode(lotteryCurrent) }
//                        if (!isPost) RxBus.get().post(LotteryExpertPlay(it))
                        handler?.postDelayed(runnable, 5000)
                    }
                }
            }
        }
    }

    private fun setContainerCode(lotteryId: String?, code: String?) {
        when (lotteryId) {
            "8" -> {
                val tbList = code?.split(",") as ArrayList<String>
                tbList.add(6, "+")
                LotteryTypeSelectUtil.addOpenCode(
                    mView,
                    mView.linLotteryOpenCode,
                    tbList,
                    lotteryId,
                    mView.linLotteryOpenCodeName
                )
            }
            else -> {
                LotteryTypeSelectUtil.addOpenCode(
                    mView,
                    mView.linLotteryOpenCode,
                    code?.split(","),
                    lotteryId,
                    mView.linLotteryOpenCodeName
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setTime(millisUntilFinished: Long) {
        val day: Long = millisUntilFinished / (1000 * 60 * 60 * 24)/*单位 天*/
        val hour: Long = (millisUntilFinished - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)/*单位 时*/
        val minute: Long =
            (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60)/*单位 分*/
        val second: Long =
            (millisUntilFinished - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * (1000 * 60)) / 1000 /*单位 秒*/
        if (mView.tvOpenTime != null) {
            when {
                day > 0 -> mView.tvOpenTime.text = dataLong(day) + "天" + dataLong(hour) + ":" + dataLong(minute) + ":" + dataLong(second)
                hour > 0 -> mView.tvOpenTime.text = dataLong(hour) + ":" + dataLong(minute) + ":" + dataLong(second)
                else -> mView.tvOpenTime.text = dataLong(minute) + ":" + dataLong(second)
            }
        }
    }

    // 这个方法是保证时间两位数据显示，如果为1点时，就为01
    private fun dataLong(c: Long): String {
        return if (c >= 10)
            c.toString()
        else "0$c"
    }
}