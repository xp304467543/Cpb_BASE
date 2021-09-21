package com.mine.children.recharge

import android.content.Intent
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.component.SideslipDeleteLayout
import com.customer.data.UserInfoSp
import com.customer.data.mine.MineApi
import com.customer.data.mine.USDTList
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.mine.R
import com.mine.children.MineAddUstdAct
import kotlinx.android.synthetic.main.usdt_list_act.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/11/21
 * @ Describe
 *
 */
class MineUsdtListAct : BaseMvpActivity<MineUsdtListActPresenter>() {

    var adapter: UsdtAdapter? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineUsdtListActPresenter()

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = "虚拟币列表"

    override fun getContentResID() = R.layout.usdt_list_act

    override fun initContentView() {
        adapter = UsdtAdapter()
        usd_list.adapter = adapter
        usd_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        usdtSmartRefreshLayout.setOnRefreshListener {
            mPresenter.getUsdt()
        }
    }

    override fun initData() {
        showPageLoadingDialog()

    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUsdt()
    }

    override fun initEvent() {
        rlAddBankItem_u1.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                startActivity(Intent(this@MineUsdtListAct, MineAddUstdAct::class.java))
            }
        }
    }


    inner class UsdtAdapter : BaseRecyclerAdapter<USDTList>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_usdt

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: USDTList?) {
            holder.text(R.id.tvCardBank, data?.name)
            if (UserInfoSp.getSelectUSDT()!=null){
                if (UserInfoSp.getSelectUSDT() == data){
                    holder.findViewById<AppCompatImageView>(R.id.imgSel).setImageResource(R.mipmap.ic_yes)
                }else  holder.findViewById<AppCompatImageView>(R.id.imgSel).setImageResource(R.mipmap.ic_no)
            }else{
                if (position == 0){
                    holder.findViewById<AppCompatImageView>(R.id.imgSel).setImageResource(R.mipmap.ic_yes)
                }else  holder.findViewById<AppCompatImageView>(R.id.imgSel).setImageResource(R.mipmap.ic_no)
            }

            holder.findViewById<Button>(R.id.btnDeleteU).setOnClickListener {
                MineApi.delUsdt(data?.id.toString()){
                    onSuccess {
                        if (UserInfoSp.getSelectUSDT() == data){
                            if (!getData().isNullOrEmpty()){
                                UserInfoSp.putSelectUSDT(getData()[0])
                            }else  UserInfoSp.putSelectUSDT(null)
                        }
                        adapter?.delete(position)
                    }
                    onFailed { ToastUtils.showToast(it.getMsg()) }
                }
            }
            holder.click(R.id.itemAll){
                data?.let { it1 -> UserInfoSp.putSelectUSDT(it1) }
                this@MineUsdtListAct.finish()
            }

        }

    }

}