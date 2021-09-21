package com.customer.component.dialog.lottery

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customer.ApiRouter
import com.customer.data.lottery.LotteryApi
import com.customer.data.lottery.LotteryCodeHistoryResponse
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.round.RoundTextView
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.popup.BasePopupWindow
import com.xiaojinzi.component.impl.Router

/**
 *
 * @ Author  QinTian
 * @ Date  3/10/21
 * @ Describe 香港彩
 *
 */
class XgcCodeWindow(context: Context) : BasePopupWindow(context, R.layout.pop_xgc_code) {
    var contentAdapter: ContentAdapter? = null
    var rvContent: RecyclerView? = null
    var tvClose: RoundTextView? = null
    var lookMore: AppCompatTextView? = null

    init {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = false
        initViews()
        initAdapter()
    }


    private fun initViews() {
        rvContent = findView(R.id.rvXgcContent)
        tvClose = findView(R.id.tvClose)
        lookMore = findView(R.id.tvLookMore)
        tvClose?.setOnClickListener {
            dismiss()
        }
        lookMore?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java).toLotteryHis("8", false)
            }
        }
    }

    private fun initAdapter() {
        contentAdapter = ContentAdapter()
        rvContent?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvContent?.adapter = contentAdapter
    }

    fun getDataLottery() {
        LotteryApi.getLotteryHistoryCode("8", TimeUtils.getToday(), 1, num = 20) {
            onSuccess {
                contentAdapter?.refresh(it)
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

    fun addNewOne(lotteryCodeHistoryResponse: LotteryCodeHistoryResponse) {
        contentAdapter?.add(0, lotteryCodeHistoryResponse)
    }

    inner class ContentAdapter : BaseRecyclerAdapter<LotteryCodeHistoryResponse>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_xgc

        override fun bindData(
            holder: RecyclerViewHolder,
            position: Int,
            data: LotteryCodeHistoryResponse?
        ) {
            try {
                holder.text(R.id.tvIssueXgc, data?.issue)
                if (position % 2 != 0) {
                    holder.backgroundResId(R.id.layoutLin, R.color.grey_f5f7fa)
                } else holder.backgroundResId(R.id.layoutLin, R.color.white)
                val result = data?.code?.split(",")
                holder.text(R.id.code_1, result?.get(0))
                holder.text(R.id.code_2, result?.get(1))
                holder.text(R.id.code_3, result?.get(2))
                holder.text(R.id.code_4, result?.get(3))
                holder.text(R.id.code_5, result?.get(4))
                holder.text(R.id.code_6, result?.get(5))
                holder.text(R.id.code_7, result?.get(6))
                judgeBig(result?.get(6) ?: "1", holder.findViewById(R.id.code_big))
                judgeSingle(result?.get(6) ?: "-1", holder.findViewById(R.id.code_single))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_1)
                    .setImageResource(getDra(result?.get(0) ?: "1"))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_2)
                    .setImageResource(getDra(result?.get(1) ?: "1"))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_3)
                    .setImageResource(getDra(result?.get(2) ?: "1"))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_4)
                    .setImageResource(getDra(result?.get(3) ?: "1"))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_5)
                    .setImageResource(getDra(result?.get(4) ?: "1"))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_6)
                    .setImageResource(getDra(result?.get(5) ?: "1"))
                holder.findViewById<AppCompatImageView>(R.id.code_bg_7)
                    .setImageResource(getDra(result?.get(6) ?: "1"))
            } catch (e: Exception) {
            }

        }
    }

    private fun judgeBig(string: String, textView: TextView) {
        try {
            when {
                string.toInt() in 1..24 -> {
                    textView.text = "小"
                    textView.setTextColor(ViewUtils.getColor(R.color.xui_config_color_blue))
                }
                string.toInt() in 25..48 -> {
                    textView.text = "大"
                    textView.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
                }
                string.toInt() == 49 -> {
                    textView.text = "和"
                    textView.setTextColor(ViewUtils.getColor(R.color.alivc_green))
                }
            }

        } catch (e: Exception) {
            ToastUtils.showToast("开奖数据错误")
        }

    }

    private fun judgeSingle(string: String, textView: TextView) {
        try {
            if (string.toInt() % 2 == 0) {
                textView.text = "双"
                textView.setTextColor(ViewUtils.getColor(R.color.xui_config_color_blue))
            } else {
                textView.text = "单"
                textView.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
            }

        } catch (e: Exception) {
            ToastUtils.showToast("开奖数据错误")
        }

    }

    private fun getDra(code: String): Int {
        return when (code) {
            "1" -> R.drawable.xcode_red
            "2" -> R.drawable.xcode_red
            "3" -> R.drawable.xcode_blue
            "4" -> R.drawable.xcode_blue
            "5" -> R.drawable.xcode_green
            "6" -> R.drawable.xcode_green
            "7" -> R.drawable.xcode_red
            "8" -> R.drawable.xcode_red
            "9" -> R.drawable.xcode_blue
            "10" -> R.drawable.xcode_blue

            "11" -> R.drawable.xcode_green
            "12" -> R.drawable.xcode_red
            "13" -> R.drawable.xcode_red
            "14" -> R.drawable.xcode_blue
            "15" -> R.drawable.xcode_blue
            "16" -> R.drawable.xcode_green
            "17" -> R.drawable.xcode_green
            "18" -> R.drawable.xcode_red
            "19" -> R.drawable.xcode_red
            "20" -> R.drawable.xcode_blue

            "21" -> R.drawable.xcode_green
            "22" -> R.drawable.xcode_green
            "23" -> R.drawable.xcode_red
            "24" -> R.drawable.xcode_red
            "25" -> R.drawable.xcode_blue
            "26" -> R.drawable.xcode_blue
            "27" -> R.drawable.xcode_green
            "28" -> R.drawable.xcode_green
            "29" -> R.drawable.xcode_red
            "30" -> R.drawable.xcode_red

            "31" -> R.drawable.xcode_blue
            "32" -> R.drawable.xcode_green
            "33" -> R.drawable.xcode_green
            "34" -> R.drawable.xcode_red
            "35" -> R.drawable.xcode_red
            "36" -> R.drawable.xcode_blue
            "37" -> R.drawable.xcode_blue
            "38" -> R.drawable.xcode_green
            "39" -> R.drawable.xcode_green
            "40" -> R.drawable.xcode_red

            "41" -> R.drawable.xcode_blue
            "42" -> R.drawable.xcode_blue
            "43" -> R.drawable.xcode_green
            "44" -> R.drawable.xcode_green
            "45" -> R.drawable.xcode_red
            "46" -> R.drawable.xcode_red
            "47" -> R.drawable.xcode_blue
            "48" -> R.drawable.xcode_blue
            "49" -> R.drawable.xcode_green
            else -> R.drawable.xcode_red
        }
    }
}