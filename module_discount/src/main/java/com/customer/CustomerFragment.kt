package com.customer

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.component.dialog.DialogCustomer
import com.customer.data.UserInfoSp
import com.customer.data.discount.CustomerQuestion
import com.customer.data.urlCustomer
import com.fh.module_discount.R
import com.glide.GlideUtil
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.StatusBarUtils
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_customer.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/9/21
 * @ Describe
 *
 */
@RouterAnno(host = "Discount", path = "customer")
class CustomerFragment : BaseMvpFragment<CustomerPresenter>(), ITheme, IMode {

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = CustomerPresenter()

    override fun isRegisterRxBus() = true

    override fun getLayoutResID() = R.layout.fragment_customer


    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(statusViewCus)
    }

    override fun initData() {
        mPresenter.getInfo(1)
        mPresenter.getInfo(2)
        mPresenter.getInfo(3)
        mPresenter.getInfo(4)
    }


    override fun initEvent() {
        imgToCustomer.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                Router.withApi(ApiRouter::class.java).toGlobalWeb(UserInfoSp.getCustomer() ?: urlCustomer)
            }
        }
    }

    var adapterM1: ContactAdapter? = null
    var adapterM2: ContactAdapter? = null
    fun initM1(data: ArrayList<CustomerQuestion>?) {
        adapterM1 = ContactAdapter()
        rvM1.layoutManager = GridLayoutManager(context, 2)
        rvM1.adapter = adapterM1
        adapterM1?.refresh(data)
    }


    fun initM2(data: ArrayList<CustomerQuestion>?) {
        adapterM2 = ContactAdapter()
        rvM2.layoutManager = GridLayoutManager(context, 2)
        rvM2.adapter = adapterM2
        adapterM2?.refresh(data)
    }

    var adapterM3: QuestionAdapter? = null
    fun initM3(data: ArrayList<CustomerQuestion>?) {
        adapterM3 = QuestionAdapter()
        rvM3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvM3.adapter = adapterM3
        adapterM3?.refresh(data)
    }

    var adapterM4: AnswerAdapter? = null
    fun initM4(data: ArrayList<CustomerQuestion>?) {
        adapterM4 = AnswerAdapter()
        rvM4.layoutManager = GridLayoutManager(context, 3)
        rvM4.adapter = adapterM4
        adapterM4?.refresh(data)
    }

    inner class ContactAdapter : BaseRecyclerAdapter<CustomerQuestion>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_customer

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: CustomerQuestion?) {
            try {
                context?.let {
                    GlideUtil.loadImage(
                        it,
                        data?.image,
                        holder.getImageView(R.id.icImg)
                    )
                }
                holder.text(R.id.icText, data?.title)
                holder.itemView.setOnClickListener {
                    if (!FastClickUtil.isFastClick()) {
                        context?.let { it1 -> DialogCustomer(data, it1).show() }
                    }
                }
            } catch (r: Exception) {
            }
        }

    }

    inner class QuestionAdapter : BaseRecyclerAdapter<CustomerQuestion>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_question

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: CustomerQuestion?) {
            holder.text(R.id.tvQuestion, (position + 1).toString() + "." + data?.title)
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClickSmall()) {
                    Router.withApi(ApiRouter::class.java)
                        .toGlobalWeb(data?.value.toString(), isClose = true,isSetTitle = false)
                }
            }
        }

    }

    inner class AnswerAdapter : BaseRecyclerAdapter<CustomerQuestion>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_answer

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: CustomerQuestion?) {
            holder.text(R.id.tvAnswer, (position + 1).toString() + "." + data?.title)
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClickSmall()) {
                    Router.withApi(ApiRouter::class.java)
                        .toGlobalWeb(data?.value.toString(), isClose = true,isSetTitle = false)
                }
            }
        }

    }


    override fun setTheme(theme: Theme) {}

    override fun setMode(mode: AppMode) {}
}
