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
 * @ Describe 10码
 *
 */
class TenCodeWindow(context: Context) : BasePopupWindow(context, R.layout.pop_ten_code) {
    
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
    var tvClose: RoundTextView? = null
    var goMark: AppCompatTextView? = null
    var tvClear: AppCompatTextView? = null
    var layoutTop :ConstraintLayout?=null
    var layoutMark:LinearLayout?=null
    var contentAdapter: ContentAdapter? = null
    var rvContent: RecyclerView? = null
    var selectCodeList = arrayListOf<String>()
    var lookMore:AppCompatTextView?=null
    var lotteryId:String?="-1"
    var isMark = false

    fun setId(name: String) {
        this.lotteryId = name
        getDataLottery(name)
    }

    init {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = false
        initViews()
        initAdapter()
        initEvent()
    }


    private fun initViews() {
        goMark =  findView(R.id.goMark)
        layoutTop = findView(R.id.layoutTop)
        tvClear = findView(R.id.tvClear)
        layoutMark = findView(R.id.layoutMark)
        rvContent = findView(R.id.rvContent)
        tvClose =  findView(R.id.tvClose)
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
        lookMore= findView(R.id.tvLookMore)
    }


    private var linearLayoutManager: LinearLayoutManager? = null
    private fun initAdapter() {
        contentAdapter = ContentAdapter()
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager?.stackFromEnd = true;//列表再底部开始展示，反转后由上面开始展示
        linearLayoutManager?.reverseLayout = true;//列表翻转
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


    private fun initEvent() {
        tvClose?.setOnClickListener { dismiss() }
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
            if (isMark){
                isMark = false
                goMark?.setCompoundDrawablesWithIntrinsicBounds(null,null,ViewUtils.getDrawable(R.mipmap.ic_arrow_bt),null)
                ViewUtils.setGone(layoutTop)
            }else{
                isMark = true
                goMark?.setCompoundDrawablesWithIntrinsicBounds(null,null,ViewUtils.getDrawable(R.mipmap.ic_arrow_top_small),null)
                ViewUtils.setVisible(layoutTop)
            }
        }
        tvClear?.setOnClickListener {
            clearAll()
        }
    }

    private fun setBgCode(boolean: Boolean, int: Int) {
        if (boolean) {
            when (int) {
                0 -> {
                    view_sel_0?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_0?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("1")
                }
                1 -> {
                    view_sel_1?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_1?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("2")
                }
                2 -> {
                    view_sel_2?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_2?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("3")
                }
                3 -> {
                    view_sel_3?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_3?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("4")
                }
                4 -> {
                    view_sel_4?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_4?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("5")
                }
                5 -> {
                    view_sel_5?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_5?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("6")
                }
                6 -> {
                    view_sel_6?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_6?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("7")
                }
                7 -> {
                    view_sel_7?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_7?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("8")
                }
                8 -> {
                    view_sel_8?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_8?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("9")
                }
                9 -> {
                    view_sel_9?.background = ViewUtils.getDrawable(R.drawable.shape_round_red)
                    view_sel_9?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    selectCodeList.add("10")
                }
                10 -> {
                    view_sel_da?.delegate?.backgroundColor =
                        ViewUtils.getColor(R.color.color_FFECE8)
                    view_sel_da?.delegate?.strokeColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    view_sel_da?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))

                    view_sel_xiao?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_xiao?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_xiao?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.add("11")
                    selectCodeList.remove("12")
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
                    selectCodeList.add("12")
                    selectCodeList.remove("11")
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
                    selectCodeList.add("13")
                    selectCodeList.remove("14")
                    codeSel_13 = false
                }
                13 -> {
                    view_sel_shuang?.delegate?.backgroundColor = ViewUtils.getColor(R.color.color_FFECE8)
                    view_sel_shuang?.delegate?.strokeColor =
                        ViewUtils.getColor(R.color.alivc_blue_1)
                    view_sel_shuang?.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))

                    view_sel_dan?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_dan?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_dan?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.add("14")
                    selectCodeList.remove("13")
                    codeSel_12 = false
                }
            }
        } else {
            when (int) {
                0 -> {
                    view_sel_0?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_0?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("1")
                }
                1 -> {
                    view_sel_1?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_1?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("2")
                }
                2 -> {
                    view_sel_2?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_2?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("3")
                }
                3 -> {
                    view_sel_3?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_3?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("4")
                }
                4 -> {
                    view_sel_4?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_4?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("5")
                }
                5 -> {
                    view_sel_5?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_5?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("6")
                }
                6 -> {
                    view_sel_6?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_6?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("7")
                }
                7 -> {
                    view_sel_7?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_7?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("8")
                }
                8 -> {
                    view_sel_8?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_8?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("9")
                }
                9 -> {
                    view_sel_9?.background = ViewUtils.getDrawable(R.drawable.shape_round)
                    view_sel_9?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("10")
                }
                10 -> {
                    view_sel_da?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_da?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_da?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("11")
                }
                11 -> {
                    view_sel_xiao?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_xiao?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_xiao?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("12")
                }
                12 -> {
                    view_sel_dan?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_dan?.delegate?.strokeColor = ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_dan?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("13")
                }
                13 -> {
                    view_sel_shuang?.delegate?.backgroundColor = ViewUtils.getColor(R.color.white)
                    view_sel_shuang?.delegate?.strokeColor =
                        ViewUtils.getColor(R.color.color_bfbfbf)
                    view_sel_shuang?.setTextColor(ViewUtils.getColor(R.color.color_666666))
                    selectCodeList.remove("14")
                }
            }
        }
        setCodeText()
        contentAdapter?.notifyDataSetChanged()
    }

    private fun setCodeText(){
        if (!selectCodeList.isNullOrEmpty()) {
            val str = StringBuffer("标记(")
            if (selectCodeList.contains("11") && selectCodeList.contains("13")) {
                //大 单  7 9
                str.append("7,9")
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
                if (selectCodeList.contains("6")) {
                    str.append(",6")
                }
                if (selectCodeList.contains("8")) {
                    str.append(",8")
                }
                if (selectCodeList.contains("10")) {
                    str.append(",10")
                }
            } else if (selectCodeList.contains("11") && selectCodeList.contains("14")) {
                //大 双 6 8 10
                str.append("6,8，10")
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
                if (selectCodeList.contains("10")) {
                    str.append(",10")
                }
            } else if (selectCodeList.contains("12") && selectCodeList.contains("13")) {
                //小 单 1 3 5
                str.append("1,3,5")

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
                if (selectCodeList.contains("10")) {
                    str.append(",10")
                }
            } else if (selectCodeList.contains("12") && selectCodeList.contains("14")) {
                //小 双 0 2 4
                str.append("2,4")
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
                if (selectCodeList.contains("10")) {
                    str.append(",10")
                }
            } else if (selectCodeList.contains("11")) {
                //大  6 7 8 9 10
                str.append("6,7,8,9,10")
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
            } else if (selectCodeList.contains("12")) {
                //小  1 2 3 4 5
                str.append("1,2,3,4,5")
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
                if (selectCodeList.contains("10")) {
                    str.append(",10")
                }
            } else if (selectCodeList.contains("13")) {
                //单 1 3 5 7 9
                str.append("1,3,5,7,9")
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
                if (selectCodeList.contains("10")) {
                    str.append(",10")
                }
            } else if (selectCodeList.contains("14")) {
                //双  2 4 6 8 10
                str.append("2,4,6,8,10")
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

    inner class ContentAdapter : BaseRecyclerAdapter<LotteryCodeHistoryResponse>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_code_10
        override fun bindData(
            holder: RecyclerViewHolder,
            position: Int,
            data: LotteryCodeHistoryResponse?
        ) {
            try {
            holder.text(R.id.tvIssueCode10, data?.issue)
            if (position % 2 == 0) {
                holder.backgroundResId(R.id.lin10, R.color.grey_f5f7fa)
            } else holder.backgroundResId(R.id.lin10, R.color.white)
            val result = data?.code?.split(",")
            val total = (result?.get(0)?.toInt()?.plus(result[1].toInt()) ?: 0)
                holder.text(R.id.code_1, result?.get(0))
                holder.text(R.id.code_2, result?.get(1))
                holder.text(R.id.code_3, result?.get(2))
                holder.text(R.id.code_4, result?.get(3))
                holder.text(R.id.code_5, result?.get(4))
                holder.text(R.id.code_6, result?.get(5))
                holder.text(R.id.code_7, result?.get(6))
                holder.text(R.id.code_8, result?.get(7))
                holder.text(R.id.code_9, result?.get(8))
                holder.text(R.id.code_10, result?.get(9))
                judgeBig(total, holder.findViewById(R.id.t_1))
                judgeSingle(total, holder.findViewById(R.id.t_2))
                changeSelect(holder, result)
            }catch (e:Exception){}
        }
    }

    private fun changeSelect(holder: RecyclerViewHolder, result: List<String>?) {
        if (!selectCodeList.isNullOrEmpty()) {
            if (!result.isNullOrEmpty()) {
                for ((index, item) in result.withIndex()) {
                    val op = item.toInt()
                    if (selectCodeList.contains("11") && selectCodeList.contains("13")) {
                        if (op > 5 && op % 2 != 0) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder, "1")
                            } else changeIndexTrue(false, result.indexOf("1"), holder, "1")
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder, "2")
                            } else changeIndexTrue(false, result.indexOf("2"), holder, "2")
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder, "3")
                            } else changeIndexTrue(false, result.indexOf("3"), holder, "3")
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder, "4")
                            } else changeIndexTrue(false, result.indexOf("4"), holder, "4")
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder, "5")
                            } else changeIndexTrue(false, result.indexOf("5"), holder, "5")
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder, "6")
                            } else changeIndexTrue(false, result.indexOf("6"), holder, "6")
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder, "8")
                            } else changeIndexTrue(false, result.indexOf("8"), holder, "8")
                            if (selectCodeList.contains("10")) {
                                changeIndexTrue(true, result.indexOf("10"), holder, "10")
                            } else changeIndexTrue(false, result.indexOf("10"), holder, "10")

                        }
                    } else if (selectCodeList.contains("11") && selectCodeList.contains("14")) {
                        if (op > 5 && op % 2 == 0) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder, "1")
                            } else changeIndexTrue(false, result.indexOf("1"), holder, "1")
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder, "2")
                            } else changeIndexTrue(false, result.indexOf("2"), holder, "2")
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder, "3")
                            } else changeIndexTrue(false, result.indexOf("3"), holder, "3")
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder, "4")
                            } else changeIndexTrue(false, result.indexOf("4"), holder, "4")
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder, "5")
                            } else changeIndexTrue(false, result.indexOf("5"), holder, "5")
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder, "7")
                            } else changeIndexTrue(false, result.indexOf("7"), holder, "7")
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder, "9")
                            } else changeIndexTrue(false, result.indexOf("9"), holder, "9")
                        }
                    } else if (selectCodeList.contains("12") && selectCodeList.contains("13")) {
                        if (op < 6 && op % 2 != 0) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder, "2")
                            } else changeIndexTrue(false, result.indexOf("2"), holder, "2")
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder, "4")
                            } else changeIndexTrue(false, result.indexOf("4"), holder, "4")
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder, "6")
                            } else changeIndexTrue(false, result.indexOf("6"), holder, "6")
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder, "7")
                            } else changeIndexTrue(false, result.indexOf("7"), holder, "7")
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder, "8")
                            } else changeIndexTrue(false, result.indexOf("8"), holder, "8")
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder, "9")
                            } else changeIndexTrue(false, result.indexOf("9"), holder, "9")
                            if (selectCodeList.contains("10")) {
                                changeIndexTrue(true, result.indexOf("10"), holder, "10")
                            } else changeIndexTrue(false, result.indexOf("10"), holder, "10")
                        }
                    } else if (selectCodeList.contains("12") && selectCodeList.contains("14")) {
                        if (op < 6 && op % 2 == 0) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder, "1")
                            } else changeIndexTrue(false, result.indexOf("1"), holder, "1")
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder, "3")
                            } else changeIndexTrue(false, result.indexOf("3"), holder, "3")
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder, "5")
                            } else changeIndexTrue(false, result.indexOf("5"), holder, "5")
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder, "6")
                            } else changeIndexTrue(false, result.indexOf("6"), holder, "6")
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder, "7")
                            } else changeIndexTrue(false, result.indexOf("7"), holder, "7")
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder, "8")
                            } else changeIndexTrue(false, result.indexOf("8"), holder, "8")
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder, "9")
                            } else changeIndexTrue(false, result.indexOf("9"), holder, "9")
                            if (selectCodeList.contains("10")) {
                                changeIndexTrue(true, result.indexOf("10"), holder, "10")
                            } else changeIndexTrue(false, result.indexOf("10"), holder, "10")
                        }
                    } else if (selectCodeList.contains("11")) {
                        if (op > 5) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder, "1")
                            } else changeIndexTrue(false, result.indexOf("1"), holder, "1")
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder, "2")
                            } else changeIndexTrue(false, result.indexOf("2"), holder, "2")
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder, "3")
                            } else changeIndexTrue(false, result.indexOf("3"), holder, "3")
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder, "4")
                            } else changeIndexTrue(false, result.indexOf("4"), holder, "4")
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder, "5")
                            } else changeIndexTrue(false, result.indexOf("5"), holder, "5")
                        }
                    } else if (selectCodeList.contains("12")) {
                        if (op < 6) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder, "6")
                            } else changeIndexTrue(false, result.indexOf("6"), holder, "6")
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder, "7")
                            } else changeIndexTrue(false, result.indexOf("7"), holder, "7")
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder, "8")
                            } else changeIndexTrue(false, result.indexOf("8"), holder, "8")
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder, "9")
                            } else changeIndexTrue(false, result.indexOf("9"), holder, "9")
                            if (selectCodeList.contains("10")) {
                                changeIndexTrue(true, result.indexOf("10"), holder, "10")
                            } else changeIndexTrue(false, result.indexOf("10"), holder, "10")
                        }
                    } else if (selectCodeList.contains("13")) {
                        if (op % 2 != 0) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("2")) {
                                changeIndexTrue(true, result.indexOf("2"), holder, "2")
                            } else changeIndexTrue(false, result.indexOf("2"), holder, "2")
                            if (selectCodeList.contains("4")) {
                                changeIndexTrue(true, result.indexOf("4"), holder, "4")
                            } else changeIndexTrue(false, result.indexOf("4"), holder, "4")
                            if (selectCodeList.contains("6")) {
                                changeIndexTrue(true, result.indexOf("6"), holder, "6")
                            } else changeIndexTrue(false, result.indexOf("6"), holder, "6")
                            if (selectCodeList.contains("8")) {
                                changeIndexTrue(true, result.indexOf("8"), holder, "8")
                            } else changeIndexTrue(false, result.indexOf("8"), holder, "8")
                            if (selectCodeList.contains("10")) {
                                changeIndexTrue(true, result.indexOf("10"), holder, "10")
                            } else changeIndexTrue(false, result.indexOf("10"), holder, "10")
                        }
                    } else if (selectCodeList.contains("14")) {
                        if (op % 2 == 0) {
                            changeIndexTrue(true, index, holder, item)
                        } else {
                            if (selectCodeList.contains("1")) {
                                changeIndexTrue(true, result.indexOf("1"), holder, "1")
                            } else changeIndexTrue(false, result.indexOf("1"), holder, "1")
                            if (selectCodeList.contains("3")) {
                                changeIndexTrue(true, result.indexOf("3"), holder, "3")
                            } else changeIndexTrue(false, result.indexOf("3"), holder, "3")
                            if (selectCodeList.contains("5")) {
                                changeIndexTrue(true, result.indexOf("5"), holder, "5")
                            } else changeIndexTrue(false, result.indexOf("5"), holder, "5")
                            if (selectCodeList.contains("7")) {
                                changeIndexTrue(true, result.indexOf("7"), holder, "7")
                            } else changeIndexTrue(false, result.indexOf("7"), holder, "7")
                            if (selectCodeList.contains("9")) {
                                changeIndexTrue(true, result.indexOf("9"), holder, "9")
                            } else changeIndexTrue(false, result.indexOf("9"), holder, "9")
                        }
                    } else {
                        for ((pos, code) in result.withIndex()) {
                            if (selectCodeList.contains(code)) {
                                changeIndexTrue(true, pos, holder, code)
                            } else {
                                changeIndexTrue(false, pos, holder, code)
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
            setBg(
                holder.findViewById(R.id.code_bg_6),
                holder.findViewById(R.id.code_6),
                result?.get(5) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_7),
                holder.findViewById(R.id.code_7),
                result?.get(6) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_8),
                holder.findViewById(R.id.code_8),
                result?.get(7) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_9),
                holder.findViewById(R.id.code_9),
                result?.get(8) ?: "0"
            )
            setBg(
                holder.findViewById(R.id.code_bg_10),
                holder.findViewById(R.id.code_10),
                result?.get(9) ?: "0"
            )
        }
    }

    private fun setBg(
        appCompatImageView: AppCompatImageView,
        appCompatTextView: AppCompatTextView,
        code: String
    ) {

        appCompatTextView.setTextColor(ViewUtils.getColor(R.color.white))
        appCompatImageView.setImageResource(getIntDrw(code))
    }

    private fun getIntDrw(string: String): Int {
        return when (string) {
            "1" -> R.drawable.code_1
            "2" -> R.drawable.code_2
            "3" -> R.drawable.code_3
            "4" -> R.drawable.code_4
            "5" -> R.drawable.code_5
            "6" -> R.drawable.code_6
            "7" -> R.drawable.code_7
            "8" -> R.drawable.code_8
            "9" -> R.drawable.code_9
            "10" -> R.drawable.code_10
            else -> R.drawable.code_3
        }
    }

    private fun judgeBig(string: Int, textView: TextView) {
        try {
            when {
                string < 12 -> {
                    textView.text = "小"
                    textView.setTextColor(ViewUtils.getColor(R.color.xui_config_color_blue))
                }
                string > 11 -> {
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

    fun changeIndexTrue(boolean: Boolean, index: Int, holder: RecyclerViewHolder, code: String) {
        if (boolean) {
            when (index) {
                0 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_1)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_1)
                        .setImageResource(getIntDrw(code))
                }
                1 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_2)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_2)
                        .setImageResource(getIntDrw(code))
                }
                2 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_3)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_3)
                        .setImageResource(getIntDrw(code))
                }
                3 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_4)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_4)
                        .setImageResource(getIntDrw(code))
                }
                4 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_5)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_5)
                        .setImageResource(getIntDrw(code))
                }
                5 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_6)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_6)
                        .setImageResource(getIntDrw(code))
                }
                6 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_7)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_7)
                        .setImageResource(getIntDrw(code))
                }
                7 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_8)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_8)
                        .setImageResource(getIntDrw(code))
                }
                8 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_9)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_9)
                        .setImageResource(getIntDrw(code))
                }
                9 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_10)
                        .setTextColor(ViewUtils.getColor(R.color.white))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_10)
                        .setImageResource(getIntDrw(code))
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
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_2)
                        .setImageResource(0)
                }
                2 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_3)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_3)
                        .setImageResource(0)
                }
                3 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_4)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_4)
                        .setImageResource(0)
                }
                4 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_5)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_5)
                        .setImageResource(0)
                }
                5 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_6)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_6)
                        .setImageResource(0)
                }
                6 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_7)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_7)
                        .setImageResource(0)
                }
                7 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_8)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_8)
                        .setImageResource(0)
                }
                8 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_9)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_9)
                        .setImageResource(0)
                }
                9 -> {
                    holder.findViewById<AppCompatTextView>(R.id.code_10)
                        .setTextColor(ViewUtils.getColor(R.color.color_999999))
                    holder.findViewById<AppCompatImageView>(R.id.code_bg_10)
                        .setImageResource(0)
                }
            }
        }
    }

    private fun clearAll(isMarkBoolean:Boolean = false){
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
        contentAdapter?.notifyDataSetChanged()
        if (isMarkBoolean) isMark = false
    }

    override fun dismiss() {
        super.dismiss()
//        selectCodeList.clear()
//        isMark = false
    }

}