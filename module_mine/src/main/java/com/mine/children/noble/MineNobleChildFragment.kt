package com.mine.children.noble

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.base.BaseNormalFragment
import com.customer.data.mine.BannerBean
import com.customer.data.mine.GiftBean
import com.customer.data.mine.NobleGiftList
import com.customer.data.mine.NobleInfo
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_noble_child.*
import java.math.BigDecimal


/**
 *
 * @ Author  QinTian
 * @ Date  1/21/21
 * @ Describe
 *
 */
class MineNobleChildFragment : BaseNormalFragment<MineNobleChildFragmentPresenter>() {

    var data: NobleInfo? = null
    var currentPosition = 0
    var nobleCode = -1
    var adapter: NobleAdapter? = null
    var tipAdapter: NobleWordsAdapter? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineNobleChildFragmentPresenter()

    override fun getLayoutRes() = R.layout.fragment_noble_child


    override fun initContentView() {
        data = arguments?.getParcelable("nobleData")
        currentPosition = arguments?.getInt("noblePosition", 0) ?: 0
        nobleCode= arguments?.getInt("nobleCode", 0) ?: 0
        initNobleImg()
        adapter = NobleAdapter()
        tipAdapter = NobleWordsAdapter()
        rvRule.adapter = tipAdapter
        rvRule.layoutManager =
            object : LinearLayoutManager(requireActivity(), VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        nobleRv.adapter = adapter
        nobleRv.layoutManager = object : GridLayoutManager(requireActivity(), 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    override fun initEvent() {
        tvInfo.setOnClickListener {
            if (tvClickInfo.visibility == View.GONE) setVisible(tvClickInfo) else setGone(
                tvClickInfo
            )
        }
    }


    @SuppressLint("SetTextI18n")
    override fun initData() {
        try {
        initNobleText()
        val currentNoble = if (data?.user_growth?.noble == 0) 0 else (data?.user_growth?.noble ?: 1)
        tvNobleTitle?.text = data?.noble_list?.get(currentPosition)?.name + "会员权益"
        val n = data?.user_growth?.exp ?: BigDecimal(1.0)
        val x = data?.noble_list?.get(currentPosition)?.max_exp ?: BigDecimal(1.0)
        val stringExp = "$n/$x"
        val spannableString = SpannableString(stringExp)
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#EDC476")),
            0,
            n.toString().length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
      if (currentNoble <= nobleCode)  tvExp.text = spannableString else tvExp.text = "当前高于该等级"
            noble7.setTextString("每日抽奖"+data?.noble_list?.get(currentPosition)?.draws.toString()+"次")
        if (nobleCode == currentNoble) {
            setVisible(nobleLevel)
            setVisible(tvInfo)
            tvInfo.text = "?"
            val progress = (n.divide(x,4, BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal(100))
            nobleLevel.progress = progress.toInt()
            val currentData = data?.noble_list?.get(7-currentNoble)
            val string =
                "保级条件: " + currentData?.day + " 天内消费 " + currentData?.low_consume + " 钻石, " + "您当前已消费 " + data?.user_growth?.keep_consume + " 钻石"
            val spannableString2 = SpannableString(string)
            spannableString2.setSpan(
                ForegroundColorSpan(Color.parseColor("#FF513E")),
                "保级条件: ".length,
                "保级条件: ".length + currentData?.day.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString2.setSpan(
                ForegroundColorSpan(Color.parseColor("#FF513E")),
                ("保级条件: " + currentData?.day + " 天内消费 ").length,
                ("保级条件: " + currentData?.day + " 天内消费 ").length + currentData?.low_consume.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString2.setSpan(
                ForegroundColorSpan(Color.parseColor("#FF513E")),
                ("保级条件: " + currentData?.day + " 天内消费 " + currentData?.low_consume + " 钻石, " + "您当前已消费 ").length,
                ("保级条件: " + currentData?.day + " 天内消费 " + currentData?.low_consume + " 钻石, " + "您当前已消费 ").length + data?.user_growth?.keep_consume.toString().length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            tvNobleInfo.text = spannableString2
            tvClickInfo.text = "如未完成保级条件则扣除" + currentData?.loss + "经验"
        } else {
            setGone(nobleLevel)
        }

            tipAdapter?.refresh(data?.tips)
            unDateBanner(data?.gift_list)
        } catch (e: Exception) {
            ToastUtils.showToast("礼物数据异常")
        }

    }

    private fun initNobleText() {
        try {
            val data = data?.noble_list?.get(currentPosition)
            if (nobleCode!=7){
                noble4?.setTextString("影视区"+data?.film_discount+"折")
                noble5?.setTextString("每日免费观影"+data?.free_watch_nums+"部")
                when(nobleCode){
                    1 ->{
                        noble4?.setBackRes(R.drawable.ic_noble_z_9)
                        noble5?.setBackRes(R.drawable.ic_noble_d_3)
                    }
                    2 ->{
                        noble4?.setBackRes(R.drawable.ic_noble_z_8)
                        noble5?.setBackRes(R.drawable.ic_noble_d_5)
                    }
                    3 ->{
                        noble4?.setBackRes(R.drawable.ic_noble_z_7)
                        noble5?.setBackRes(R.drawable.ic_noble_d_10)
                    }
                    4 ->{
                        noble4?.setBackRes(R.drawable.ic_noble_z_6)
                        noble5?.setBackRes(R.drawable.ic_noble_d_20)
                    }
                    5 ->{
                        noble4?.setBackRes(R.drawable.ic_noble_z_3)
                        noble5?.setBackRes(R.drawable.ic_noble_d_50)
                    }
                    6 ->{
                        noble4?.setBackRes(R.drawable.ic_noble_z_1)
                        noble5?.setBackRes(R.drawable.ic_noble_d_100)
                    }
                }
            }
        }catch (e:Exception){
            ToastUtils.showToast("数据长度错误！")
        }

    }

    private fun unDateBanner(giftList: List<NobleGiftList>?) {
        if (giftList != null) {
            val list = arrayListOf<GiftBean>()
            for (item in giftList) {
                if (!item.list.isNullOrEmpty()) {
                    val childList = arrayListOf<BannerBean>()
                    for (child in item.list!!) {
                        childList.add(BannerBean(child.name, child.icon, child.price,""))
                    }
                    list.add(GiftBean(item.noble_name, childList))
                }
            }
            adapter?.refresh(list)
        }
    }


    private fun initNobleImg() {
        val currentNoble = if (data?.user_growth?.noble == 0) 0 else (data?.user_growth?.noble ?: 1)
        val img = when (nobleCode) {
            1 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_1 else R.drawable.ic_noble_1
            2 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_2 else R.drawable.ic_noble_2
            3 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_3 else R.drawable.ic_noble_3
            4 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_4 else R.drawable.ic_noble_4
            5 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_5 else R.drawable.ic_noble_5
            6 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_6 else R.drawable.ic_noble_6
            7 -> if (currentNoble >= nobleCode) R.drawable.ic_noble_s_7 else R.drawable.ic_noble_7
            else -> R.drawable.ic_noble_1
        }
        imgNoble.setImageResource(img)
        when (nobleCode) {
            1 -> {
                noble7.setBackRes(R.drawable.ic_nb_n_7)
                noble8.setBackRes(R.drawable.ic_nb_n_8)
                noble9.setBackRes(R.drawable.ic_nb_n_9)
                noble10.setBackRes(R.drawable.ic_nb_n_10)
                noble11.setBackRes(R.drawable.ic_nb_n_11)
            }
            2,3 -> {
                noble8.setBackRes(R.drawable.ic_nb_n_8)
                noble9.setBackRes(R.drawable.ic_nb_n_9)
                noble10.setBackRes(R.drawable.ic_nb_n_10)
                noble11.setBackRes(R.drawable.ic_nb_n_11)
            }
            4 -> {
                noble9.setBackRes(R.drawable.ic_nb_n_9)
                noble10.setBackRes(R.drawable.ic_nb_n_10)
                noble11.setBackRes(R.drawable.ic_nb_n_11)
            }
            5 -> {
                noble10.setBackRes(R.drawable.ic_nb_n_10)
                noble11.setBackRes(R.drawable.ic_nb_n_11)
            }
            6 -> {
                noble11.setBackRes(R.drawable.ic_nb_n_11)
            }
        }
    }

    inner class NobleAdapter : BaseRecyclerAdapter<GiftBean>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_noble_gift

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: GiftBean?) {
            val bannerView = holder.findViewById<Banner<BannerBean, ImageAdapter>>(R.id.nobleBanner)
            holder.text(R.id.tvNoble, data?.name)
            upDateBanner(bannerView, data?.banner)
        }


        private fun upDateBanner(
            banner: Banner<BannerBean, ImageAdapter>?,
            data: List<BannerBean>?
        ) {
            banner?.adapter = ImageAdapter(data)
            val indicator = CircleIndicator(context)
            if (data?.size ?: 0 > 1) {
                banner?.indicator = indicator
                banner?.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
                banner?.setIndicatorSelectedColor(ViewUtils.getColor(R.color.color_FF513E))
                banner?.setIndicatorNormalColor(ViewUtils.getColor(R.color.color_DDDDDD))
            } else banner?.setIndicator(indicator, false)
            banner?.addBannerLifecycleObserver(requireActivity())
        }
    }

    inner class NobleWordsAdapter : BaseRecyclerAdapter<String>() {
        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_item_noble_info

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: String?) {
            holder.text(R.id.tvContent, data)
        }
    }


    companion object {
        fun newInstance(data: NobleInfo, position: Int,code:Int?): MineNobleChildFragment {
            val fragment = MineNobleChildFragment()
            val bundle = Bundle()
            bundle.putParcelable("nobleData", data)
            bundle.putInt("noblePosition", position)
            code?.let { bundle.putInt("nobleCode", it) }
            fragment.arguments = bundle
            return fragment
        }
    }
}