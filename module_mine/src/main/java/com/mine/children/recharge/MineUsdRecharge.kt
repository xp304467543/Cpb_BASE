package com.mine.children.recharge

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.ApiRouter
import com.customer.data.UserInfoSp
import com.customer.data.mine.MineApi
import com.customer.data.mine.MinePayTypeList
import com.customer.data.urlCustomer
import com.customer.utils.MoneyInputFilter
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_recharge_teach_usd.*
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.*
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.bCoin
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.bt_go_recharge
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.etChangeMoney
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.rvTypeCoin
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.tvHelp
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.tv_ss_1
import kotlinx.android.synthetic.main.act_recharge_teach_usd_1.tv_ss_2
import java.lang.Exception
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  5/9/21
 * @ Describe
 *
 */
class MineUsdRecharge : BaseMvpActivity<MineUsdRechargePresenter>() {

    var adapter :IconType?=null

    override fun isShowBackIconWhite() = false

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_recharge_teach_usd_1

    override fun getPageTitle() = "虚拟币充值"

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineUsdRechargePresenter()

    var cz_exchange_rate = BigDecimal(-1)

    var low = BigDecimal(0)
    var hight = BigDecimal(1000)
    var data: MinePayTypeList?=null

    @SuppressLint("SetTextI18n")
    override fun initContentView() {
        val spannableString = SpannableString("如需帮助,请 联系客服")
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#ff513e")),
            7,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvHelp.text = spannableString

        etChangeMoney.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        data = intent.getParcelableExtra("usdData")
        bCoin.text = data?.currency_type
        currentType = data?.protocol?.get(0) ?: ""
        cz_exchange_rate = data?.cz_exchange_rate ?: BigDecimal(-1)
        currentSd = data?.currency_type ?: ""
        val spannableString1 =
            SpannableString("参考汇率：1" + data?.currency_type + "≈" + data?.cz_exchange_rate + "CNY")
        spannableString1.setSpan(
            ForegroundColorSpan(Color.parseColor("#ff513e")),
            "参考汇率：".length,
            spannableString1.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_ss_1.text = spannableString1
        val spannableString2 = SpannableString("预计支付: 0 $currentSd")
        spannableString2.setSpan(
            ForegroundColorSpan(Color.parseColor("#ff513e")),
            "预计支付: ".length,
            spannableString2.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_ss_2.text = spannableString2
        low = BigDecimal(data?.low_money ?: "0")
        hight = BigDecimal(data?.high_money ?: "1000")
        adapter = IconType()
        rvTypeCoin.adapter = adapter
        rvTypeCoin.layoutManager = GridLayoutManager(this, 3)
        adapter?.refresh(data?.protocol)

        val filter = MoneyInputFilter()
        filter.setDecimalLength(2) //保留小数点后3位
        val filters = arrayOf<InputFilter>(filter)
        etChangeMoney.filters = filters
    }

    override fun initEvent() {
        tvHelp.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java)
                    .toGlobalWeb(UserInfoSp.getCustomer() ?: urlCustomer)
            }
        }
        etChangeMoney.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(s: Editable?) {
                if (s!=null){
                    val tes = s.toString().trim()
                    val len = tes.length
                    if (len  == 1 && tes.startsWith("0")){
                        s.replace(0,1,"")

                    }
                    if (tes!=""&& tes != "."){
                        if (BigDecimal(tes).compareTo(hight) == 1){
                            ToastUtils.showToast("充值金额为 $low~$hight")
                            etChangeMoney.setText(hight.toBigInteger().toString())
                            etChangeMoney.setSelection(hight.toBigInteger().toString().length)
                        }
                        setTv()
                    }else  {
                        val spannableString = SpannableString("预计支付: 0 $currentSd")
                        spannableString.setSpan(
                            ForegroundColorSpan(Color.parseColor("#ff513e")),
                            "预计支付: ".length,
                            spannableString.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        tv_ss_2.text = spannableString
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        bt_go_recharge.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                if (etChangeMoney.text.toString().trim()!=""){
                    if (BigDecimal(etChangeMoney.text.toString()).compareTo(low) == -1 || BigDecimal(etChangeMoney.text.toString()).compareTo(hight) == 1){
                        ToastUtils.showToast("充值金额为 $low~$hight")
                    }else{
                        showPageLoadingDialog()
                        MineApi.rechargeUsd(etChangeMoney.text.toString().trim(),data?.id.toString(),data?.currency_type?:"",currentType){
                            onSuccess {
                                hidePageLoadingDialog()
                                if (it.type == 2){
                                    Router.withApi(ApiRouter::class.java).toGlobalWebSpecial(isString = true,webActForm = it.form?:"")
                                }else{
                                    Router.withApi(ApiRouter::class.java).toGlobalWeb(url = it.url?:"")
                                }
                            }
                            onFailed {
                                hidePageLoadingDialog()
                                ToastUtils.showToast(it.getMsg()) }
                        }
                    }
                }else ToastUtils.showToast("请输入金额")
            }
        }
    }

    fun setTv() {
        try {
            val num = etChangeMoney.text.toString()
            if (cz_exchange_rate != BigDecimal.ZERO && num.isNotEmpty()) {
                val total = BigDecimal(num).divide(cz_exchange_rate, 4, BigDecimal.ROUND_HALF_DOWN)
                val spannableString = SpannableString("预计支付: $total $currentSd")
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#ff513e")),
                    "预计支付: ".length,
                    spannableString.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv_ss_2.text = spannableString
            }
        }catch (e:Exception){}

    }


    var current = 0

    var currentType = ""

    var currentSd = ""


    inner class IconType : BaseRecyclerAdapter<String>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_usd

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: String?) {
            holder.text(R.id.childUsd, data)
            val tv = holder.findViewById<TextView>(R.id.childUsd)
            if (current == position) {
                tv.background = ViewUtils.getDrawable(R.mipmap.ic_check)
            } else {
                tv.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            }
            holder.itemView.setOnClickListener {
                current = position
                currentType = data?:""
                notifyDataSetChanged()
            }
        }

    }
}