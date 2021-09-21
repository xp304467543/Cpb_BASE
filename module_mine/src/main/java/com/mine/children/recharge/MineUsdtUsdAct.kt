package com.mine.children.recharge

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.component.dialog.DialogAddUsdt
import com.customer.data.UseAddress
import com.customer.data.mine.MineApi
import com.customer.data.mine.UserBankCard
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.mine.R
import kotlinx.android.synthetic.main.act_use_ustd.*

/**
 *
 * @ Author  QinTian
 * @ Date  5/8/21
 * @ Describe
 *
 */
class MineUsdtUsdAct : BaseNavActivity() {

    var adapter: UstdAdapter? = null


    override fun isShowToolBar() = false

    override fun getContentResID() = R.layout.act_use_ustd

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false


    override fun initContentView() {
        adapter = UstdAdapter()
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ustdCard.layoutManager = layout
        ustdCard.adapter = adapter
    }

    override fun initData() {
        getList()
    }

    private fun getList() {
        MineApi.getUserBank(2) {
            onSuccess {
                if (it.isNullOrEmpty()) {
                    setVisible(tvEmpty)
                } else adapter?.refresh(it)
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
                adapter?.clear()
                setVisible(tvEmpty)
            }
        }
    }

    override fun initEvent() {
        closeBt.setOnClickListener {
            finish()
        }
        addAddress.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                val dialog = DialogAddUsdt(this, "")
                dialog.setOnSuccessListener {
                    getList()
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    inner class UstdAdapter : BaseRecyclerAdapter<UserBankCard>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_ustd_list

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: UserBankCard?) {

            holder.text(R.id.tvDes, data?.remark)
            holder.text(R.id.tvAddres, data?.no)
            if (data?.mark == "1") {
                holder.findViewById<AppCompatImageView>(R.id.imgFirst)
                    .setImageResource(R.drawable.ic_ustd_s)
                holder.findViewById<AppCompatImageView>(R.id.imgSecond)
                    .setImageResource(R.drawable.ic_ustd_c)
            } else {
                holder.findViewById<AppCompatImageView>(R.id.imgFirst)
                    .setImageResource(R.drawable.ic_usdt)
                holder.findViewById<AppCompatImageView>(R.id.imgSecond)
                    .setImageResource(R.drawable.ic_ustd_un_c)
            }
            holder.findView(R.id.btnDeleteU).setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    MineApi.delUserBankCard(data?.id.toString()) {
                        onSuccess {
                            adapter?.delete(position)
                        }
                        onFailed {
                            ToastUtils.showToast(it.getMsg())
                        }
                    }
                }

            }
            holder.click(R.id.allLayout) {
                if (!FastClickUtil.isFastClick()) {
                    showPageLoadingDialog()
                    MineApi.setUserBankCardUse(data?.id.toString(), 2) {
                        onSuccess {
                            hidePageLoadingDialog()
                            RxBus.get().post(UseAddress(data?.no.toString()))
                            this@MineUsdtUsdAct.finish()
                        }
                        onFailed {
                            hidePageLoadingDialog()
                            ToastUtils.showToast(it.getMsg())
                        }
                    }
                }
            }
        }
    }

}