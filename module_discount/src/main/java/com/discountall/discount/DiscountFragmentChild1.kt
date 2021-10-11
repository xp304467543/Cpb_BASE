package com.discountall.discount

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.base.BaseNormalFragment
import com.customer.data.discount.DiscountContent
import com.fh.module_discount.R
import com.glide.GlideUtil
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.fragmen_child_discount.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */
class DiscountFragmentChild1 : BaseNormalFragment<DiscountFragmentChildPresenter1>() {

    var current = -1

    var discountAdapter: DiscountAdapter? = null

    var page = 1

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() =
        DiscountFragmentChildPresenter1()


    override fun getLayoutRes() = R.layout.fragmen_child_discount

    override fun initContentView() {
        discountAdapter = DiscountAdapter()
        rvDiscount.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvDiscount.adapter = discountAdapter

    }


    override fun initData() {
//        val data = arguments?.getParcelableArray("discountData")
        current = arguments?.getInt("index") ?: -1
//        if (current == 0) {
//            if (!data.isNullOrEmpty()) {
//                setGone(tvHolder)
//                val real = data as Array<DiscountContent>
//                discountAdapter?.refresh(real)
//            } else setVisible(tvHolder)
//        } else {
        mPresenter.getList(current)
//        }
    }

    override fun initEvent() {
        discountSmartRefreshLayout?.setOnRefreshListener {
            page = 0
            mPresenter.getList(current, page)
        }
        discountSmartRefreshLayout?.setOnLoadMoreListener {
//            page++
//            mPresenter.getList(current, page, false)
            discountSmartRefreshLayout?.finishLoadMoreWithNoMoreData()
        }
    }


    inner class DiscountAdapter : BaseRecyclerAdapter<DiscountContent>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_discount_list

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: DiscountContent?) {
                context?.let {
                    GlideUtil.loadImageBanner(
                        it,
                        data?.cover,
                        holder.findViewById(R.id.imgDiscount)
                    )
                }
                context?.let {
                    GlideUtil.loadImageBanner(
                        it,
                        data?.type_icon,
                        holder.findViewById(R.id.imgType)
                    )
                }
                holder.text(R.id.tvType, data?.type_text)
                when (data?.type) {
                    7 -> holder.textColorId(R.id.tvType, R.color.color_333333)
                    else -> holder.textColorId(R.id.tvType, R.color.white)
                }
                holder.itemView.setOnClickListener {
                    if (!FastClickUtil.isFastClick()) {
                        Router.withApi(ApiRouter::class.java).toGlobalWebSpecial(
                            isOpenResize = false,
                            isString = true,
                            webActForm = data?.content ?: "",
                            tile = "优惠详情",
                            Address =  data?.address ?: ""
                        )
//                        val intent =Intent(requireActivity(),WebPicAct::class.java)
//                          intent.putExtra("UrlWeb",data?.content ?: "")
//                        intent.putExtra("UrlAddress",data?.address ?: "")
//                        startActivity(intent)
                    }
                }
        }
    }


    companion object {
        fun newInstance(index: Int, data: Array<DiscountContent>): DiscountFragmentChild1 {
            val fragment = DiscountFragmentChild1()
            val bundle = Bundle()
            bundle.putInt("index", index)
            bundle.putParcelableArray("discountData", data)
            fragment.arguments = bundle
            return fragment
        }
    }
}