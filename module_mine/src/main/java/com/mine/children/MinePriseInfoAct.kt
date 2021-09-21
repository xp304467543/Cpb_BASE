package com.mine.children

import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.PanPriseLog
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import cuntomer.them.AppMode
import kotlinx.android.synthetic.main.act_prise_info.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/28/21
 * @ Describe
 *
 */
class MinePriseInfoAct : BaseNavActivity() {

    var currentPage = 1

    var currentSel = 1


    var priseAdapter: PriseAdapter? = null

    override fun getContentResID() = R.layout.act_prise_info

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = "我的中奖"


    override fun initContentView() {
        priseAdapter = PriseAdapter()
        rPrise.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rPrise.adapter = priseAdapter
        if (UserInfoSp.getAppMode() == AppMode.Pure) setGone(layTop)
    }

    override fun initData() {
        getLog(true)

        smartPrise.setOnRefreshListener {
            currentPage = 1
            getLog(true)
        }
        smartPrise.setOnLoadMoreListener {
            currentPage++
            getLog(false)
        }
        tvYecj.setOnClickListener {
            if (!FastClickUtil.isFastClickSmall()) {
                if (currentSel != 1) {
                    try {
                        currentSel = 1
                        tvYecj.setTextColor(ViewUtils.getColor(R.color.white))
                        tvYecj.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_1)
                        tvZscj.setTextColor(ViewUtils.getColor(R.color.color_333333))
                        tvZscj.delegate.backgroundColor = 0
                        currentPage = 1
                        priseAdapter?.clear()
                        getLog(true)
                    } catch (e: Exception) {
                    }
                }
            } else ToastUtils.showToast("请勿频繁点击")
        }
        tvZscj.setOnClickListener {
            if (!FastClickUtil.isFastClickSmall()) {
                if (currentSel != 0) {
                    try {
                        currentSel = 0
                        tvZscj.setTextColor(ViewUtils.getColor(R.color.white))
                        tvZscj.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_1)
                        tvYecj.setTextColor(ViewUtils.getColor(R.color.color_333333))
                        tvYecj.delegate.backgroundColor = 0
                        currentPage = 1
                        priseAdapter?.clear()
                        getLog(true)
                    } catch (e: Exception) {
                    }

                }
            } else ToastUtils.showToast("请勿频繁点击")
        }
    }


    private fun getLog(isRefresh: Boolean) {
        HomeApi.gePanPriseLog(currentSel, currentPage) {
            onSuccess {
                if (!isFinishing && !isDestroyed) {
                    if (isRefresh) {
                        if (it.isNullOrEmpty()) {
                            setVisible(holderView)
                        } else {
                            setGone(holderView)
                            priseAdapter?.refresh(it)
                        }
                        smartPrise.finishRefresh()
                    } else {
                        if (it.isNullOrEmpty()) {
                            smartPrise.finishLoadMoreWithNoMoreData()
                        } else {
                            priseAdapter?.loadMore(it)
                            smartPrise.finishLoadMore()
                        }
                    }
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

    inner class PriseAdapter : BaseRecyclerAdapter<PanPriseLog>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_prise_log

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: PanPriseLog?) {
            val layout = holder.findViewById<LinearLayout>(R.id.linHome)
            holder.text(R.id.tvStart, data?.created_time)
            holder.text(R.id.tvEnd, "抽中了  " + data?.name + "  * " + data?.number)
            if (position % 2 == 0) {
                layout.setBackgroundColor(ViewUtils.getColor(R.color.grey_f5f7fa))
            } else layout.setBackgroundColor(ViewUtils.getColor(R.color.white))
        }

    }

}