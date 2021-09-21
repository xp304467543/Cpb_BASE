package com.mine.children.vip

import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.component.dialog.DialogVipGiftGet
import com.customer.data.UserInfoSp
import com.customer.data.mine.MineApi
import com.customer.data.mine.VipGift
import com.customer.data.mine.VipGiftChild
import com.glide.GlideUtil
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.anno.RouterAnno
import cuntomer.them.AppMode
import kotlinx.android.synthetic.main.act_vip_gift.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/5/21
 * @ Describe
 *
 */
@RouterAnno(host = "Mine", path = "VipGift")
class MineVipGiftActivity : BaseMvpActivity<MineVipGiftPresenter>() {

    var current = 1

    var giftAdapter: GiftAdapter? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineVipGiftPresenter()

    override fun isSwipeBackEnable() = true

    override fun getPageTitle() = "会员礼包"

    override fun isShowBackIconWhite() = false

    override fun getContentResID() = R.layout.act_vip_gift


    override fun initContentView() {
        giftAdapter = GiftAdapter()
        vipGift.adapter = giftAdapter
        vipGift.layoutManager = GridLayoutManager(this, 2)
        if (UserInfoSp.getAppMode() == AppMode.Pure){
            setGone(tv_2)
            rvTopLay.delegate.backgroundColor = ViewUtils.getColor(R.color.white)
            val palm = tv_1.layoutParams as RelativeLayout.LayoutParams
            palm.addRule(RelativeLayout.CENTER_IN_PARENT)
            tv_1.layoutParams = palm
        }
    }

    override fun initData() {
        mPresenter.getGift()
    }

    override fun initEvent() {
        tv_1.setOnClickListener {
            if (dataVIP != null) {
                if (current == 2) {
                    current = 1
                    tv_1.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    tv_2.delegate.backgroundColor = 0
                    tv_1.setTextColor(ViewUtils.getColor(R.color.white))
                    tv_2.setTextColor(ViewUtils.getColor(R.color.color_333333))
                    setGone(tvHolder)
                    giftAdapter?.refresh(dataVIP?.vip)
                    if (dataVIP?.vip.isNullOrEmpty()) setVisible(tvHolder)
                }
            } else mPresenter.getGift()

        }
        tv_2.setOnClickListener {
            if (dataVIP != null) {
                if (current == 1) {
                    current = 2
                    tv_1.delegate.backgroundColor = ViewUtils.getColor(R.color.grey_f5f7fa)
                    tv_2.delegate.backgroundColor = ViewUtils.getColor(R.color.alivc_blue_1)
                    tv_2.setTextColor(ViewUtils.getColor(R.color.white))
                    tv_1.setTextColor(ViewUtils.getColor(R.color.color_333333))
                    setGone(tvHolder)
                    giftAdapter?.refresh(dataVIP?.noble)
                    if (dataVIP?.noble.isNullOrEmpty()) setVisible(tvHolder)
                }
            } else mPresenter.getGift()
        }
    }

    var dataVIP: VipGift? = null
    fun initDataThing(data: VipGift) {
        setGone(tvHolder)
        giftAdapter?.refresh(data.vip)
        if (data.vip.isNullOrEmpty()) setVisible(tvHolder)
        dataVIP = data

    }



    inner class GiftAdapter : BaseRecyclerAdapter<VipGiftChild>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_vip_gift

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: VipGiftChild?) {
            val tvGet = holder.findViewById<TextView>(R.id.tvGet)
            if (data?.status == 0) {
                tvGet.background = ViewUtils.getDrawable(R.drawable.button_blue_background)
                tvGet.setTextColor(ViewUtils.getColor(R.color.white))
                tvGet.text = "领取"
            } else {
                tvGet.background = ViewUtils.getDrawable(R.drawable.button_grey_background)
                tvGet.setTextColor(ViewUtils.getColor(R.color.grey_75))
                tvGet.text = "已领取"
            }
            holder.text(R.id.tvName, data?.name)
            if (data?.type == 2) {
                holder.text(R.id.tvTitle, "VIP" + data.code + "专享")
            } else holder.text(R.id.tvTitle, "贵族" + data?.code + "专享")
            GlideUtil.loadImage(
                this@MineVipGiftActivity,
                data?.icon,
                holder.findViewById<AppCompatImageView>(R.id.imgGift)
            )
            tvGet.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    if (data?.status == 0) {
                      if (giftDialog == null)  giftDialog = DialogVipGiftGet(this@MineVipGiftActivity)
                        giftDialog?.setSelectListener { name, address, phone ->
                            showPageLoadingDialog()
                            MineApi.getVipGift(data.pack_id?:0,name,address,phone) {
                                onSuccess {
                                    hidePageLoadingDialog()
                                    data.status = 1
                                    ToastUtils.showToast("领取成功")
                                    giftAdapter?.notifyItemChanged(position)
                                    giftDialog?.dismiss()
                                }

                                onFailed {
                                    hidePageLoadingDialog()
                                    ToastUtils.showToast(it.getMsg())
                                }
                            }

                        }
                        giftDialog?.show()
                    }
                }
            }
        }

    }

    var giftDialog :DialogVipGiftGet?=null

}