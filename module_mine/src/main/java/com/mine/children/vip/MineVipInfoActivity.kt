package com.mine.children.vip

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.data.mine.GiftObject
import com.customer.data.mine.VipInfo
import com.glide.GlideUtil
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ToastUtils
import com.mine.R
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.act_mine_vip_info.*

/**
 *
 * @ Author  QinTian
 * @ Date  1/15/21
 * @ Describe
 *
 */
@RouterAnno(host = "Mine", path = "VipInfo")
class MineVipInfoActivity : BaseMvpActivity<MineVipInfoPresenter>() {

    private var currentVIP = 1

    private var adapter: VipWordsAdapter? = null


    private var adapterGift: GiftAdapter? = null


    private var vipInfoData: VipInfo? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineVipInfoPresenter()

    override fun isShowToolBar() = false

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_mine_vip_info

    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(vipStateView)
        currentVIP = intent.getIntExtra("vipLevel", 1)
        if (currentVIP == 0) currentVIP++
    }

    override fun initData() {
        mPresenter.getInfo()
        adapter = VipWordsAdapter()
        rvWords?.adapter = adapter
        rvWords.layoutManager = object : LinearLayoutManager(this, VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        adapterGift= GiftAdapter()
        rvGift?.adapter = adapterGift
        rvGift.layoutManager = object : GridLayoutManager(this, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }

    }


    fun initDataInfo(vipInfo: VipInfo) {
        vipInfoData = vipInfo
        setVipData(currentVIP, false)
        if (!vipInfo.tips.isNullOrEmpty()) { adapter?.refresh(vipInfo.tips) }
        if (!vipInfoData?.gift_list.isNullOrEmpty()) {
            if (!isDestroyed) {
                adapterGift?.refresh(vipInfoData?.gift_list)
//                if (vipInfoData?.gift_list?.size ?: 0 >= 1) {
//                    GlideUtil.loadImage(this, vipInfoData?.gift_list?.get(0)?.list?.get(0)?.icon, imgV5)
//                    tvImg5.text = vipInfoData?.gift_list?.get(0)?.list?.get(0)?.name
//                }
//                if (vipInfoData?.gift_list?.size ?: 0 >= 2) {
//                    GlideUtil.loadImage(this, vipInfoData?.gift_list?.get(1)?.list?.get(0)?.icon, imgV6)
//                    tvImg6.text = vipInfoData?.gift_list?.get(1)?.list?.get(0)?.name
//                }
//                if (vipInfoData?.gift_list?.size ?: 0 >= 3) {
//                    GlideUtil.loadImage(
//                        this,
//                        vipInfoData?.gift_list?.get(2)?.list?.get(0)?.icon,
//                        imgV7
//                    )
//                    tvImg7.text = vipInfoData?.gift_list?.get(2)?.list?.get(0)?.name
//                }
//                if (vipInfoData?.gift_list?.size ?: 0 >= 4) {
//                    GlideUtil.loadImage(
//                        this,
//                        vipInfoData?.gift_list?.get(3)?.list?.get(0)?.icon,
//                        imgV8
//                    )
//                    tvImg8.text = vipInfoData?.gift_list?.get(3)?.list?.get(0)?.name
//                }
            }
        }
    }

    override fun initEvent() {
        vipBack.setOnClickListener {
            finish()
        }
        vip1.setOnClickListener {
            setVipData(1)
        }
        vip2.setOnClickListener {
            setVipData(2)
        }
        vip3.setOnClickListener {
            setVipData(3)
        }
        vip4.setOnClickListener {
            setVipData(4)
        }
        vip5.setOnClickListener {
            setVipData(5)
        }
        vip6.setOnClickListener {
            setVipData(6)
        }
        vip7.setOnClickListener {
            setVipData(7)
        }
        vip8.setOnClickListener {
            setVipData(8)
        }
    }


    @SuppressLint("SetTextI18n")
    fun setVipData(int: Int, isClick: Boolean = true) {
        if (vipInfoData == null) {
            ToastUtils.showToast("未获取到VIP信息,请重试")
            return
        }
        when (int) {
            1 -> vip1.setImageResource(R.mipmap.vip1)
            2 -> vip2.setImageResource(R.mipmap.vip2)
            3 -> vip3.setImageResource(R.mipmap.vip3)
            4 -> vip4.setImageResource(R.mipmap.vip4)
            5 -> vip5.setImageResource(R.mipmap.vip5)
            6 -> vip6.setImageResource(R.mipmap.vip6)
            7 -> vip7.setImageResource(R.mipmap.vip7)
            8 -> vip8.setImageResource(R.mipmap.vip8)
        }
        if (isClick && currentVIP != int) {
            when (currentVIP) {
                1 -> vip1.setImageResource(R.mipmap.normal_vip_1)
                2 -> vip2.setImageResource(R.mipmap.normal_vip_2)
                3 -> vip3.setImageResource(R.mipmap.normal_vip_3)
                4 -> vip4.setImageResource(R.mipmap.normal_vip_4)
                5 -> vip5.setImageResource(R.mipmap.normal_vip_5)
                6 -> vip6.setImageResource(R.mipmap.normal_vip_6)
                7 -> vip7.setImageResource(R.mipmap.normal_vip_7)
                8 -> vip8.setImageResource(R.mipmap.normal_vip_8)
            }
            currentVIP = int
        }
        if (vipInfoData?.vip_list?.size ?: 0 >= int) {
            val data = vipInfoData?.vip_list?.get(int-1)
            val spannableString = SpannableString("累计存款: " + data?.cz_total)
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#ff513e")),
                "累计存款: ".length,
                spannableString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tv1.text = spannableString
            val spannableString2 = SpannableString("累计流水: " + data?.flow_total)
            spannableString2.setSpan(
                ForegroundColorSpan(Color.parseColor("#ff513e")),
                "累计流水: ".length,
                spannableString2.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tv2.text = spannableString2
            val spannableString3 = SpannableString("流水(" + data?.rg_day + "天): " + data?.rg_flow)
            spannableString3.setSpan(
                ForegroundColorSpan(Color.parseColor("#ff513e")),
                ("流水(" + data?.rg_day + "天): ").length,
                spannableString3.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tv3.text = spannableString3
            tv4.text = if (data?.upgrade_bonus?:0.0>0)data?.upgrade_bonus.toString() else "x"
            tv5.text = if (data?.birthday_gift?:0.0>0)data?.birthday_gift.toString() else "x"
            tv6.text = if (data?.month_hb?:0.0>0)data?.month_hb.toString() else "x"
            if (data?.customer_service == null) tv7.text = "x" else tv7.text = data.customer_service
        } else {
            ToastUtils.showToast("未获取到VIP等级信息,请重试")
        }
    }

    inner class VipWordsAdapter : BaseRecyclerAdapter<String>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_item_vip_info

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: String?) {
            holder.text(R.id.tvNum, "" + (position + 1))
            holder.text(R.id.tvContent, data)
        }
    }

    inner class GiftAdapter : BaseRecyclerAdapter<GiftObject>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_vip_gift_info

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: GiftObject?) {
             GlideUtil.loadImage(this@MineVipInfoActivity, data?.list?.get(0)?.icon, holder.findViewById<AppCompatImageView>(R.id.imgGift))
            holder.text(R.id.tvName,data?.list?.get(0)?.name)
            holder.text(R.id.tvVipNum,data?.vip_name)
        }

    }

}