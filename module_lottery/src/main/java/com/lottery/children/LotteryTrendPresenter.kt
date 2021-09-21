package com.lottery.children

import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ToastUtils
import com.customer.data.lottery.LotteryApi
import com.customer.data.lottery.LotteryCodeTrendResponse
import kotlinx.android.synthetic.main.child_fragment_trend.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/17
 * @ Describe
 *
 */
class LotteryTrendPresenter : BaseMvpPresenter<LotteryTrendFragment>() {
    fun getTrendData(lotteryId: String, num: String = "1", limit: String = "10", date: String = TimeUtils.getToday()) {
        LotteryApi.getLotteryTrend(lotteryId, num, limit, date) {
            if (mView.isActive()) {
                onSuccess {
                        if (!it.isNullOrEmpty()){
                            mView.setGone(mView.linTrendLoading)
                            mView.setGone(mView.tvHolder)
                            mView.initLineChart(it, null)
                        } else {
                            mView.setGone(mView.linTrendLoading)
                            mView.setVisible(mView.tvHolder)
                        }
                }
                onFailed {
                        ToastUtils.showToast(it.getMsg().toString())
                        mView.setGone(mView.linTrendLoading)
                        mView.setVisible(mView.tvHolder)
                        mView.hidePageLoadingDialog()
                }
            }

        }
    }

    //形态走势  形态
    fun getFormData(lotteryId: String, num: String = "7", limit: String = "10", date: String = TimeUtils.getToday(),options1:Int = 0) {
        LotteryApi.getLotteryTrend(lotteryId, num, limit, date) {
            if (mView.isActive()) {
                onSuccess {
                    if (!it.isNullOrEmpty()) {
                        when (num) {
                            "7" -> getComposeData(lotteryId, num = "10", limit = limit, date = date, result = it,options1 = options1)
                            "8" -> getComposeData(lotteryId, num = "11", limit = limit, date = date, result = it,options1 = options1)
                            "9" -> getComposeData(lotteryId, num = "12", limit = limit, date = date, result = it,options1 = options1)
                        }

                    }
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg().toString())
                    mView.setGone(mView.linTrendLoading)
                }
            }

        }
    }

    //形态走势  组合类型
    private fun getComposeData(lotteryId: String, options1:Int,num: String, limit: String, date: String, result: List<LotteryCodeTrendResponse>) {
        LotteryApi.getLotteryTrend(lotteryId, num, limit, date) {
            onSuccess {
                if (!it.isNullOrEmpty()) mView.initLineChart(result, it,options1)
            }
            onFailed {
                ToastUtils.showToast(it.getMsg().toString())
                mView.setGone(mView.linTrendLoading)
            }
        }
    }
}