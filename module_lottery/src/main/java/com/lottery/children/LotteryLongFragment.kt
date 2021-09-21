package com.lottery.children

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.base.BaseNormalFragment
import com.customer.data.lottery.LotteryApi
import com.customer.data.lottery.LotteryCodeChangLongResponse
import com.fh.module_lottery.R
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_long.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/13/21
 * @ Describe
 *
 */
class LotteryLongFragment : BaseNormalFragment<LotteryLongFragmentPresenter>() {

    var adapter:LongAdapter?=null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = LotteryLongFragmentPresenter()

    override fun getLayoutRes() = R.layout.fragment_long

    override fun initContentView() {
        adapter = LongAdapter()
        rvLong.adapter = adapter
        rvLong.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }

    override fun initData() {
        getLong()
    }


    fun getLong() {
        LotteryApi.getChangLong(arguments?.getString("lotteryId") ?: "8") {
            onSuccess {
                if (isActive()){
                    setGone(longLoad)
                    if (it.isNullOrEmpty()){
                        setVisible(holder_Long)
                        setGone(topLayout)
                    }else{
                        setGone(holder_Long)
                        adapter?.refresh(it)

                    }
                }
            }
            onFailed {
                if (isActive()){
                    setGone(longLoad)
                    setVisible(holder_Long)
                    setGone(topLayout)
                }
            }
        }
    }


    inner class LongAdapter : BaseRecyclerAdapter<LotteryCodeChangLongResponse>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_long
        override fun bindData(
            holder: RecyclerViewHolder,
            position: Int,
            data: LotteryCodeChangLongResponse?
        ) {
            holder.text(R.id.tvLong1, data?.method_cname + " " + data?.result_c)
            holder.text(R.id.tvLong2, data?.nums)
        }

    }

    companion object {
        fun newInstance(lotteryId: String, issue: String): LotteryLongFragment {
            val fragment = LotteryLongFragment()
            val bundle = Bundle()
            bundle.putString("lotteryId", lotteryId)
            bundle.putString("issue", issue)
            fragment.arguments = bundle
            return fragment
        }
    }

}