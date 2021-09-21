package com.home.live.bet.old

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customer.data.lottery.LotteryBetRuleResponse
import com.customer.data.lottery.PlayRuleTypeDataBean
import com.home.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.round.RoundTextView
import com.lib.basiclib.base.xui.widget.tabbar.TabControlView
import com.lib.basiclib.utils.ViewUtils


/**
 *
 * @ Author  QinTian
 * @ Date  2020-04-16
 * @ Describe 规则
 *
 */

class LiveRoomBetToolsRulesChildFragment : BaseNormalFragment<Any?>() {

    private var tvBetRule: AppCompatTextView? = null
    private var wbView: WebView? = null
    private var tcv_select: TabControlView? = null
    private var singleText: RoundTextView? = null
    private var textAdapter: TypeAdapter? = null
    private var rvTypeRule: RecyclerView? = null

    override fun getLayoutRes() = R.layout.old_fragment_live_bet_rule

    override fun initView(rootView: View?) {
        tvBetRule = rootView?.findViewById(R.id.tvBetRule)
        wbView = rootView?.findViewById(R.id.wbView)
        val ws = wbView?.settings
        ws?.textZoom = 80
        tcv_select = rootView?.findViewById(R.id.tcv_select)
        singleText = rootView?.findViewById(R.id.singleText)
        rvTypeRule = rootView?.findViewById(R.id.rvTypeRule)
        textAdapter = TypeAdapter()
        rvTypeRule?.adapter = textAdapter
        rvTypeRule?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun initData() {
        val data = arguments?.getSerializable("RuleContent") as ArrayList<LotteryBetRuleResponse>
//        val item = arrayListOf<String>()
//        val content = arrayListOf<String>()
        val index = arguments?.getInt("RuleIndex") ?: 0

        if (!data[index].play_rule_type_data.isNullOrEmpty()) {
            textAdapter?.refresh(data[index].play_rule_type_data)
//            for ((pos, res) in data[index].play_rule_type_data?.withIndex()!!) {
//                item.add(res.play_rule_lottery_name ?: "null")
//                content.add(res.play_rule_content ?: "null")
//            }
        }
//        try {
//            if (item.size <= 1) {
//                ViewUtils.setVisible(singleText)
//                ViewUtils.setGone(tcv_select)
//                singleText?.text = item[0]
//            } else {
//                ViewUtils.setGone(singleText)
//                ViewUtils.setVisible(tcv_select)
//            }
//            tcv_select?.setItems(item, content)
//            tcv_select?.setDefaultSelection(0)
//            tvBetRule?.text = HtmlCompat.fromHtml(content[0], HtmlCompat.FROM_HTML_MODE_COMPACT)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        tcv_select?.setOnTabSelectionChangedListener { _, value ->
//            tvBetRule?.text = HtmlCompat.fromHtml(value, HtmlCompat.FROM_HTML_MODE_COMPACT)
//        }

    }

    var positionInt = 0

    inner class TypeAdapter : BaseRecyclerAdapter<PlayRuleTypeDataBean>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.rule_bg

        @SuppressLint("NotifyDataSetChanged")
        override fun bindData(
            holder: RecyclerViewHolder,
            position: Int,
            data: PlayRuleTypeDataBean?
        ) {
            val tvRule = holder.findViewById<RoundTextView>(R.id.tvRule)
            tvRule.text = data?.play_rule_lottery_name
            if (positionInt == position) {
                tvRule.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                tvRule.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_5)
//                tvBetRule?.text = HtmlCompat.fromHtml(data?.play_rule_content?:"暂无",FROM_HTML_OPTION_USE_CSS_COLORS)
                wbView?.loadDataWithBaseURL(
                    null,
                    data?.play_rule_content ?: "暂无",
                    "text/html",
                    "UTF-8",
                    null
                )
            } else {
                tvRule.setTextColor(ViewUtils.getColor(R.color.color_333333))
                tvRule.delegate.backgroundColor = ViewUtils.getColor(R.color.grey_f5f7fa)
            }
            holder.itemView.setOnClickListener {
                if (positionInt != position) {
                    notifyDataSetChanged()
                    positionInt = position
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        wbView?.destroy()
    }

    companion object {
        fun newInstance(
            data: ArrayList<LotteryBetRuleResponse>?,
            index: Int
        ): LiveRoomBetToolsRulesChildFragment {
            val fragment = LiveRoomBetToolsRulesChildFragment()
            val bundle = Bundle()
            bundle.putSerializable("RuleContent", data)
            bundle.putSerializable("RuleIndex", index)
            fragment.arguments = bundle
            return fragment
        }
    }

}       