package com.customer.component.dialog.lottery

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
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
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  3/10/21
 * @ Describe 5码
 *
 */
class FiveCodeWindow(context: Context) : BasePopupWindow(context, R.layout.pop_five_code) {


    var codeSel_0 = false
    var codeSel_1 = false
    var codeSel_2 = false
    var codeSel_3 = false
    var codeSel_4 = false
    var codeSel_5 = false
    var codeSel_6 = false
    var codeSel_7 = false
    var codeSel_8 = false
    var codeSel_9 = false
    var codeSel_10 = false
    var codeSel_11 = false
    var codeSel_12 = false
    var codeSel_13 = false

    var view_sel_0: AppCompatTextView? = null
    var view_sel_1: AppCompatTextView? = null
    var view_sel_2: AppCompatTextView? = null
    var view_sel_3: AppCompatTextView? = null
    var view_sel_4: AppCompatTextView? = null
    var view_sel_5: AppCompatTextView? = null
    var view_sel_6: AppCompatTextView? = null
    var view_sel_7: AppCompatTextView? = null
    var view_sel_8: AppCompatTextView? = null
    var view_sel_9: AppCompatTextView? = null
    var view_sel_da: RoundTextView? = null
    var view_sel_xiao: RoundTextView? = null
    var view_sel_dan: RoundTextView? = null
    var view_sel_shuang: RoundTextView? = null
    var tvClose:RoundTextView?=null
    var lookMore:AppCompatTextView?=null
    //    var view_sel_zhi: RoundTextView? = null
//    var view_sel_he: RoundTextView? = null
    var goMark: AppCompatTextView? = null
    var tvClear: AppCompatTextView? = null
    var layoutMark: LinearLayout? = null
    var selectCodeList = arrayListOf<String>()
    var layoutTop: ConstraintLayout? = null
    var contentAdapter: ContentAdapter? = null
    var rvContent: RecyclerView? = null

    var isMark = false

    var lotteryId:String?="-1"

    init {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = false
        initViews()
        initAdapter()
        initEvent()
    }


    fun setId(name: String) {
        this.lotteryId = name
        getDataLottery(name)
    }

    private fun initViews() {
        goMark = findView(R.id.goMark)
        layoutTop = findView(R.id.layoutTop)
        tvClear = findView(R.id.tvClear)
        layoutMark = findView(R.id.layoutMark)
        tvClose = findView(R.id.tvClose)
        rvContent = findView(R.id.rvContent)
        lookMore= findView(R.id.tvLookMore)
        view_sel_0 = findView(R.id.view_sel_0)
        view_sel_1 = findView(R.id.view_sel_1)
        view_sel_2 = findView(R.id.view_sel_2)
        view_sel_3 = findView(R.id.view_sel_3)
        view_sel_4 = findView(R.id.view_sel_4)
        view_sel_5 = findView(R.id.view_sel_5)
        view_sel_6 = findView(R.id.view_sel_6)
        view_sel_7 = findView(R.id.view_sel_7)
        view_sel_8 = findView(R.id.view_sel_8)
        view_sel_9 = findView(R.id.view_sel_9)
        view_sel_da = findView(R.id.view_sel_da)
        view_sel_xiao = findView(R.id.view_sel_xiao)
        view_sel_dan = findView(R.id.view_sel_dan)
        view_sel_shuang = findView(R.id.view_sel_shuang)
//        view_sel_zhi = findView(R.id.view_sel_zhi)
//        view_sel_he = findView(R.id.view_sel_he)
    }


    private fun initEvent() {
        view_sel_0?.setOnClickListener {
            codeSel_0 = !codeSel_0
            setBgCode(codeSel_0, 0)
        }
        view_sel_1?.setOnClickListener {
            codeSel_1 = !codeSel_1
            setBgCode(codeSel_1, 1)
        }
        view_sel_2?.setOnClickListener {
            codeSel_2 = !codeSel_2
            setBgCode(codeSel_2, 2)
        }
        view_sel_3?.setOnClickListener {
            codeSel_3 = !codeSel_3
            setBgCode(codeSel_3, 3)
        }
        view_sel_4?.setOnClickListener {
            codeSel_4 = !codeSel_4
            setBgCode(codeSel_4, 4)
        }
        view_sel_5?.setOnClickListener {
            codeSel_5 = !codeSel_5
            setBgCode(codeSel_5, 5)
        }
        view_sel_6?.setOnClickListener {
            codeSel_6 = !codeSel_6
            setBgCode(codeSel_6, 6)
        }
        view_sel_7?.setOnClickListener {
            codeSel_7 = !codeSel_7
            setBgCode(codeSel_7, 7)
        }
        view_sel_8?.setOnClickListener {
            codeSel_8 = !codeSel_8
            setBgCode(codeSel_8, 8)
        }
        view_sel_9?.setOnClickListener {
            codeSel_9 = !codeSel_9
            setBgCode(codeSel_9, 9)
        }
        view_sel_da?.setOnClickListener {
            codeSel_10 = !codeSel_10
            setBgCode(codeSel_10, 10)
        }
        view_sel_xiao?.setOnClickListener {
            codeSel_11 = !codeSel_11
            setBgCode(codeSel_11, 11)
        }
        view_sel_dan?.setOnClickListener {
            codeSel_12 = !codeSel_12
            setBgCode(codeSel_12, 12)
        }
        view_sel_shuang?.setOnClickListener {
            codeSel_13 = !codeSel_13
            setBgCode(codeSel_13, 13)
        }
        lookMore?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (lotteryId !="-1"){
                    Router.withApi(ApiRouter::class.java).toLotteryHis(lotteryId?:"-1",false)
                }else ToastUtils.showToast("未获取到ID")
            }
        }
        layoutMark?.setOnClickListener {
            if (isMark) {
                isMark = false
                goMark?.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ViewUtils.getDrawable(R.mipmap.ic_arrow_bt),
                    null
                )
                ViewUtils.setGone(layoutTop)
            } else {
                isMark = true
                goMark?.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ViewUtils.getDrawable(R.mipmap.ic_arrow_top_small),
                    null
                )
                ViewUtils.setVisible(layoutTop)
            }
        }
        tvClear?.setOnClickListener {
            clearAll()
        }
        tvClose?.setOnClickListener {
            dismiss()
        }
    }


    private fun setBgCode(boolean: Boolean, int: Int) {
        if (boolean) {
            when (int) {
                0 -> {
                    view_sel_0?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_0?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("0")
                }
                1 -> {
                    view_sel_1?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_1?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("1")
                }
                2 -> {
                    view_sel_2?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_2?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("2")
                }
                3 -> {
                    view_sel_3?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_3?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("3")
                }
                4 -> {
                    view_sel_4?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_4?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("4")
                }
                5 -> {
                    view_sel_5?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_5?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("5")
                }
                6 -> {
                    view_sel_6?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_6?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("6")
                }
                7 -> {
                    view_sel_7?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_7?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("7")
                }
                8 -> {
                    view_sel_8?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_8?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("8")
                }
                9 -> {
                    view_sel_9?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_9?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("9")
                }
                10 -> {
                    view_sel_da?.delegate?.backgroundColor =
                        ViewUtils.getColor(R.color.color_FFECE8)
                    view_sel_da?.delegate?.strokeColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    view_sel_da?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))

                    view_sel_xiao?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_xiao?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_xiao?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.add("10")
                    selectCodeList.remove("11")
                    codeSel_11 = false
                }
                11 -> {
                    view_sel_xiao?.delegate?.backgroundColor =
                        ViewUtils.getColor(R.color.color_FFECE8)
                    view_sel_xiao?.delegate?.strokeColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    view_sel_xiao?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))

                    view_sel_da?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_da?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_da?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.add("11")
                    selectCodeList.remove("10")
                    codeSel_10 = false
                }
                12 -> {
                    view_sel_dan?.delegate?.backgroundColor =
                        ViewUtils.getColor(R.color.color_FFECE8)
                    view_sel_dan?.delegate?.strokeColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    view_sel_dan?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))

                    view_sel_shuang?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_shuang?.delegate?.strokeColor =
                        ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_shuang?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.add("12")
                    selectCodeList.remove("13")
                    codeSel_13 = false
                }
                13 -> {
                    view_sel_shuang?.delegate?.backgroundColor =
                        ViewUtils.getColor(R.color.color_FFECE8)
                    view_sel_shuang?.delegate?.strokeColor =
                        ViewUtils.getColor(R.color.alivc_blue_1)
                    view_sel_shuang?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))

                    view_sel_dan?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_dan?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_dan?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.add("13")
                    selectCodeList.remove("12")
                    codeSel_12 = false
                }
            }
        } else {
            when (int) {
                0 -> {
                    view_sel_0?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_0?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("0")
                }
                1 -> {
                    view_sel_1?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_1?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("1")
                }
                2 -> {
                    view_sel_2?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_2?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("2")
                }
                3 -> {
                    view_sel_3?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_3?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("3")
                }
                4 -> {
                    view_sel_4?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_4?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("4")
                }
                5 -> {
                    view_sel_5?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_5?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("5")
                }
                6 -> {
                    view_sel_6?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_6?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("6")
                }
                7 -> {
                    view_sel_7?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_7?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("7")
                }
                8 -> {
                    view_sel_8?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_8?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("8")
                }
                9 -> {
                    view_sel_9?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_9?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("9")
                }
                10 -> {
                    view_sel_da?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_da?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_da?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("10")
                }
                11 -> {
                    view_sel_xiao?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_xiao?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_xiao?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("11")
                }
                12 -> {
                    view_sel_dan?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_dan?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_dan?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("12")
                }
                13 -> {
                    view_sel_shuang?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_shuang?.delegate?.strokeColor =
                        ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_shuang?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("13")
                }
            }
        }
        if (!selectCodeList.isNullOrEmpty()) {
            val str = StringBuffer("标记 ")
            for ((index, item) in selectCodeList.withIndex()) {
                val newStr = when (item) {
                    "10" -> "大"
                    "11" -> "小"
                    "12" -> "单"
                    "13" -> "双"
                    else -> item
                }
                if (index == 0) {
                    str.append(newStr)
                } else str.append(",$newStr")
            }
            goMark?.text = str
        } else goMark?.text = "标记"
        setCodeText()
        contentAdapter?.notifyDataSetChanged()
    }

    private fun setCodeText(){
        if (!selectCodeList.isNullOrEmpty()) {
            val str = StringBuffer("标记(")
             if (selectCodeList.contains("10") && selectCodeList.contains("12")) {
                //大 单 5 7 9
                str.append("5,7,9")
                if (selectCodeList.contains("0")) {
                    str.append(",0")
                }
                if (selectCodeList.contains("1")) {
                    str.append(",1")
                }
                if (selectCodeList.contains("2")) {
                    str.append(",2")
                }
                if (selectCodeList.contains("3")) {
                    str.append(",3")
                }
                if (selectCodeList.contains("4")) {
                    str.append(",4")
                }
                if (selectCodeList.contains("6")) {
                    str.append(",6")
                }
                if (selectCodeList.contains("8")) {
                    str.append(",8")
                }
            } else if (selectCodeList.contains("10") && selectCodeList.contains("13")) {
                //大 双 6 8
                str.append("6,8")
                if (selectCodeList.contains("0")) {
                    str.append(",0")
                }
                if (selectCodeList.contains("1")) {
                    str.append(",1")
                }
                if (selectCodeList.contains("2")) {
                    str.append(",2")
                }
                if (selectCodeList.contains("3")) {
                    str.append(",3")
                }
                if (selectCodeList.contains("4")) {
                    str.append(",4")
                }
                if (selectCodeList.contains("5")) {
                    str.append(",5")
                }
                if (selectCodeList.contains("7")) {
                    str.append(",7")
                }
                if (selectCodeList.contains("9")) {
                    str.append(",9")
                }
            } else if (selectCodeList.contains("11") && selectCodeList.contains("12")) {
                //小 单 1 3
                str.append("1,3")
                if (selectCodeList.contains("0")) {
                    str.append(",0")
                }
                if (selectCodeList.contains("2")) {
                    str.append(",2")
                }
                if (selectCodeList.contains("4")) {
                    str.append(",4")
                }
                if (selectCodeList.contains("5")) {
                    str.append(",5")
                }
                if (selectCodeList.contains("6")) {
                    str.append(",6")
                }
                if (selectCodeList.contains("7")) {
                    str.append(",7")
                }
                if (selectCodeList.contains("8")) {
                    str.append(",8")
                }
                if (selectCodeList.contains("9")) {
                    str.append(",9")
                }
            } else if (selectCodeList.contains("11") && selectCodeList.contains("13")) {
                //小 双 0 2 4
                str.append("0,2,4")
                if (selectCodeList.contains("1")) {
                    str.append(",1")
                }
                if (selectCodeList.contains("3")) {
                    str.append(",3")
                }
                if (selectCodeList.contains("5")) {
                    str.append(",5")
                }
                if (selectCodeList.contains("6")) {
                    str.append(",6")
                }
                if (selectCodeList.contains("7")) {
                    str.append(",7")
                }
                if (selectCodeList.contains("8")) {
                    str.append(",8")
                }
                if (selectCodeList.contains("9")) {
                    str.append(",9")
                }
            } else if (selectCodeList.contains("10")) {
                //大 5 6 7 8 9
                str.append("5,6,7,8,9")
                if (selectCodeList.contains("0")) {
                    str.append(",0")
                }
                if (selectCodeList.contains("1")) {
                    str.append(",1")
                }
                if (selectCodeList.contains("2")) {
                    str.append(",2")
                }
                if (selectCodeList.contains("3")) {
                    str.append(",3")
                }
                if (selectCodeList.contains("4")) {
                    str.append(",4")
                }
            } else if (selectCodeList.contains("11")) {
                //小 0 1 2 3 4
                str.append("0,1,2,3,4")
                if (selectCodeList.contains("5")) {
                    str.append(",5")
                }
                if (selectCodeList.contains("6")) {
                    str.append(",6")
                }
                if (selectCodeList.contains("7")) {
                    str.append(",7")
                }
                if (selectCodeList.contains("8")) {
                    str.append(",8")
                }
                if (selectCodeList.contains("9")) {
                    str.append(",9")
                }
            } else if (selectCodeList.contains("12")) {
                //单 1 3 5 7 9
                str.append("1,3,5,7,9")
                if (selectCodeList.contains("0")) {
                    str.append(",0")
                }
                if (selectCodeList.contains("2")) {
                    str.append(",2")
                }
                if (selectCodeList.contains("4")) {
                    str.append(",4")
                }
                if (selectCodeList.contains("6")) {
                    str.append(",6")
                }
                if (selectCodeList.contains("8")) {
                    str.append(",8")
                }
            } else if (selectCodeList.contains("13")) {
                //双 0 2 4 6 8
                str.append("0,2,4,6,8")
                if (selectCodeList.contains("1")) {
                    str.append(",1")
                }
                if (selectCodeList.contains("3")) {
                    str.append(",3")
                }
                if (selectCodeList.contains("5")) {
                    str.append(",5")
                }
                if (selectCodeList.contains("7")) {
                    str.append(",7")
                }
                if (selectCodeList.contains("9")) {
                    str.append(",9")
                }
            } else {
                for ((pos,to) in selectCodeList.withIndex()){
                    if (pos == 0) str.append(to) else str.append(",$to")
                }
            }
            goMark?.text = str.append(")")
        } else goMark?.text = "标记"
    }


    private var linearLayoutManager: LinearLayoutManager? = null
    private fun initAdapter() {
        contentAdapter = ContentAdapter()
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager?.stackFromEnd = true//列表再底部开始展示，反转后由上面开始展示
        linearLayoutManager?.reverseLayout = true//列表翻转
        rvContent?.layoutManager = linearLayoutManager
        rvContent?.adapter = contentAdapter
    }

    private fun getDataLottery(lotteryId: String) {
        LotteryApi.getLotteryHistoryCode(lotteryId, TimeUtils.getToday(), 1) {
            onSuccess {
                Collections.reverse(it)
                contentAdapter?.refresh(it)
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

    fun addNewOne(lotteryCodeHistoryResponse: LotteryCodeHistoryResponse) {
        contentAdapter?.add(lotteryCodeHistoryResponse)
        rvContent?.smoothScrollToPosition(contentAdapter?.data?.size ?: 0)
    }


    private fun setBg(
        appCompatImageView: AppCompatImageView,
        appCompatTextView: AppCompatTextView,
        code: String
    ) {

        appCompatTextView.setTextColor(ViewUtils.getColor(R.color.white))
        appCompatImageView.setImageResource(R.drawable.xcode_blue)
    }


    inner class ContentAdapter : BaseRecyclerAdapter<LotteryCodeHistoryResponse>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_code_5

        override fun bindData(
            holder: RecyclerViewHolder,
            position: Int,
            data: LotteryCodeHistoryResponse?
        ) {

            try {
                holder.text(R.id.tvIssueCode5, data?.issue)
                if (position % 2 != 0) {
                    holder.backgroundResId(R.id.linCode5, R.color.grey_f5f7fa)
                } else holder.backgroundResId(R.id.linCode5, R.color.white)
                val result = data?.code?.split(",")
                val total = (result?.get(0)?.toInt()?.plus(result[1].toInt()) ?: 0).plus(
                    result?.get(2)?.toInt() ?: 0
                )
                    .plus(result?.get(3)?.toInt() ?: 0).plus(result?.get(4)?.toInt() ?: 0)
                holder.text(R.id.code_1, result?.get(0))
                holder.text(R.id.code_2, result?.get(1))
                holder.text(R.id.code_3, result?.get(2))
                holder.text(R.id.code_4, result?.get(3))
                holder.text(R.id.code_5, result?.get(4))

                judgeBig(total, holder.findViewById(R.id.tv_1))
                judgeSingle(total, holder.findViewById(R.id.tv_2))
                judgeTiger(
                    result?.get(0)?.toInt() ?: 0,
                    result?.get(result.lastIndex)?.toInt() ?: 0,
                    holder.findViewById(R.id.tv_3)
                )

                changeSelect(holder, result)
            }catch (e:Exception){

            }

        }
    }


    private fun changeSelect(holder: RecyclerViewHolder, result: List<String>?) {
        if (!selectCodeList.isNullOrEmpty()) {
            if (!result.isNullOrEmpty()) {
                for ((index, item) in result.withIndex()) {
                    val op = item.toInt()

                    if (selectCodeList.contains("10") && selectCodeList.contains("12")) {
                        if (op > 4 && op % 2 != 0) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("0")) {
                                changeIndexTrue(true, result.indexOf("0"), holder)
                            }
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder)
                            }
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder)
                            }
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder)
                            }
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder)
                            }
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder)
                            }
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("10") && selectCodeList.contains("13")) {
                        if (op > 4 && op % 2 == 0) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("0")) {
                                changeIndexTrue(true, result.indexOf("0"), holder)
                            }
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder)
                            }
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder)
                            }
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder)
                            }
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder)
                            }
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder)
                            }
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder)
                            }
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("11") && selectCodeList.contains("12")) {
                        if (op < 5 && op % 2 != 0) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("0")) {
                                changeIndexTrue(true, result.indexOf("0"), holder)
                            }
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder)
                            }
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder)
                            }
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder)
                            }
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder)
                            }
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder)
                            }
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder)
                            }
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("11") && selectCodeList.contains("13")) {
                        if (op < 5 && op % 2 == 0) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder)
                            }
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder)
                            }
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder)
                            }
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder)
                            }
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder)
                            }
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder)
                            }
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("10")) {
                        if (op > 4) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("0")) {
                                changeIndexTrue(true, result.indexOf("0"), holder)
                            }
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder)
                            }
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder)
                            }
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder)
                            }
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("11")) {
                        if (op < 5) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder)
                            }
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder)
                            }
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder)
                            }
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder)
                            }
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("12")) {
                        if (op % 2 != 0) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("0")) {
                                changeIndexTrue(true, result.indexOf("0"), holder)
                            }
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder)
                            }
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder)
                            }
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder)
                            }
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder)
                            }
                        }
                    } else if (selectCodeList.contains("13")) {
                        if (op % 2 == 0) {
                            changeIndexTrue(true, index, holder)
                        } else {
                            changeIndexTrue(false, index, holder)
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder)
                            }
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder)
                            }
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder)
                            }
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder)
                            }
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder)
                            }
                        }
                    } else {
                        for ((pos, code) in result.withIndex()) {
                            if (selectCodeList.contains(code)) {
                                changeIndexTrue(true, pos, holder)
                            } else {
                                changeIndexTrue(false, pos, holder)
                            }
                        }
                    }
                }
            }
        } else {
            setBg(
                holder.findViewById(R.id.code_bg_1),
                holder.findViewById(R.id.code_1),
                result?.get(0) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_2),
                holder.findViewById(R.id.code_2),
                result?.get(1) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_3),
                holder.findViewById(R.id.code_3),
                result?.get(2) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_4),
                holder.findViewById(R.id.code_4),
                result?.get(3) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_5),
                holder.findViewById(R.id.code_5),
                result?.get(4) ?: "0"
            )
        }
    }

    fun changeIndexTrue(boolean: Boolean, index: Int, holder: RecyclerViewHolder) {
        if (index == -1) return
        if (boolean) {
            when (index) {
                0 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_1)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_1)
                        .setImageResource(R.drawable.xcode_blue)
                }
                1 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_2)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_2)
                        .setImageResource(R.drawable.xcode_blue)
                }
                2 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_3)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_3)
                        .setImageResource(R.drawable.xcode_blue)
                }
                3 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_4)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_4)
                        .setImageResource(R.drawable.xcode_blue)
                }
                4 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_5)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_5)
                        .setImageResource(R.drawable.xcode_blue)
                }

            }
        } else {
            when (index) {
                0 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_1)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_1).setImageResource(0)
                }
                1 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_2)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_2).setImageResource(0)
                }
                2 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_3)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_3).setImageResource(0)
                }
                3 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_4)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_4).setImageResource(0)
                }
                4 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_5)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_5).setImageResource(0)
                }
            }
        }
    }

    private fun judgeBig(string: Int, textView: TextView) {
        try {
            when {
                string < 23 -> {
                    textView.text = "小"
                    textView.setTextColor(ViewUtils.getColor(R.color.xui_config_color_blue))
                }
                string > 22 -> {
                    textView.text = "大"
                    textView.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
                }

            }

        } catch (e: Exception) {
            ToastUtils.showToast("开奖数据错误")
        }

    }

    private fun judgeSingle(string: Int, textView: TextView) {
        try {
            if (string % 2 == 0) {
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

    private fun judgeTiger(string: Int, string9: Int, textView: TextView) {
        when {
            string > string9 -> {
                textView.text = "龙"
                textView.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
            }
            string < string9 -> {
                textView.text = "虎"
                textView.setTextColor(ViewUtils.getColor(R.color.xui_config_color_blue))
            }
            else -> {
                textView.text = "和"
                textView.setTextColor(ViewUtils.getColor(R.color.color_333333))
            }
        }
    }


    private fun clearAll(isMarkBoolean: Boolean = false) {
        codeSel_0 = false
        codeSel_1 = false
        codeSel_2 = false
        codeSel_3 = false
        codeSel_4 = false
        codeSel_5 = false
        codeSel_6 = false
        codeSel_7 = false
        codeSel_8 = false
        codeSel_9 = false
        codeSel_10 = false
        codeSel_11 = false
        codeSel_12 = false
        codeSel_13 = false
        selectCodeList.clear()
        goMark?.text = "标记"
        view_sel_0?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_0?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_1?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_1?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_2?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_2?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_3?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_3?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_4?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_4?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_5?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_5?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_6?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_6?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_7?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_7?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_8?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_8?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_9?.background = ViewUtils.getDrawable(R.drawable.shape_round)
        view_sel_9?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_da?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
        view_sel_da?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
        view_sel_da?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_xiao?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
        view_sel_xiao?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
        view_sel_xiao?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_dan?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
        view_sel_dan?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
        view_sel_dan?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        view_sel_shuang?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
        view_sel_shuang?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
        view_sel_shuang?.setTextColor(ViewUtils.getColor(R.color.color_666666))
//        view_sel_zhi?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
//        view_sel_zhi?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
//        view_sel_zhi?.setTextColor(ViewUtils.getColor(R.color.color_666666))
//        view_sel_he?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
//        view_sel_he?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
//        view_sel_he?.setTextColor(ViewUtils.getColor(R.color.color_666666))
        contentAdapter?.notifyDataSetChanged()
        if (isMarkBoolean) isMark = false
    }


    override fun dismiss() {
        super.dismiss()
//        selectCodeList.clear()
//        isMark = false
    }
}