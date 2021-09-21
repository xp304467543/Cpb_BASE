package com.mine.children.recharge

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.ApiRouter
import com.customer.component.dialog.DialogAddUsdt
import com.customer.component.dialog.DialogGlobalTips
import com.customer.data.UseAddress
import com.customer.data.UserInfoSp
import com.customer.data.mine.HandUsdt
import com.customer.data.mine.MineApi
import com.customer.data.mine.MinePayTypeList
import com.customer.data.mine.UserBankCard
import com.customer.data.urlCustomer
import com.customer.utils.MoneyInputFilter
import com.glide.GlideUtil
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.widget.picker.widget.builder.TimePickerBuilder
import com.lib.basiclib.base.xui.widget.picker.widget.configure.TimePickerType
import com.lib.basiclib.base.xui.widget.picker.widget.listener.OnTimeSelectListener
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_recharge_teach_usd.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * @ Author  QinTian
 * @ Date  4/9/21
 * @ Describe
 *
 */
class MineUsdRgRecharge : BaseMvpActivity<MineUsdRgRechargePresenter>() {

    var adapter: IconType? = null

    var isHaveUsdt = false

    override fun isRegisterRxBus() = true

    override fun isShowBackIconWhite() = false

    override fun isSwipeBackEnable() = true

    override fun getContentResID() = R.layout.act_recharge_teach_usd

    override fun getPageTitle() = "虚拟币充值"

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineUsdRgRechargePresenter()

    var cz_exchange_rate = BigDecimal(-1)

    var low = BigDecimal(0)
    var hight = BigDecimal(1000)
    var data: MinePayTypeList? = null

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
        val spannableString2 = SpannableString("预计到账: 0 CNY")
        spannableString2.setSpan(
            ForegroundColorSpan(Color.parseColor("#ff513e")),
            "预计到账: ".length,
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
                if (s != null) {
                    val tes = s.toString().trim()
                    val len = tes.length
                    if (len == 1 && tes.startsWith("0")) {
                        s.replace(0, 1, "")

                    }
                    if (tes != "" && tes != ".") {
                        if (BigDecimal(tes).compareTo(hight) == 1) {
                            ToastUtils.showToast("充值金额为 $low~$hight")
                            etChangeMoney.setText(hight.toBigInteger().toString())
                            etChangeMoney.setSelection(hight.toBigInteger().toString().length)
                        }
                        setTv()
                    } else {
                        val spannableString = SpannableString("预计到账: 0 CNY")
                        spannableString.setSpan(
                            ForegroundColorSpan(Color.parseColor("#ff513e")),
                            "预计到账: ".length,
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
            if (!FastClickUtil.isFastClick()) {
                if (usdAddress.text.toString().trim().isNotEmpty()) {
                    if (usdID.text.toString().trim().isNotEmpty()) {
                        if (usdTime.text.toString().trim().isNotEmpty()) {
                            if (etChangeMoney.text.toString().trim().isNotEmpty()) {
                                if (BigDecimal(etChangeMoney.text.toString()).compareTo(low) == -1 || BigDecimal(
                                        etChangeMoney.text.toString()
                                    ).compareTo(hight) == 1
                                ) {
                                    ToastUtils.showToast("充值金额为 $low~$hight")
                                } else {
                                    showPageLoadingDialog()
                                    MineApi.getHandUsdtByUser(
                                        currentType,
                                        usdAddress.text.toString().trim(),
                                        usdID.text.toString().trim(),
                                        etChangeMoney.text.toString().trim(),
                                        TimeUtils.date2TimeStamp(usdTime.text.toString())?:""
                                    ) {
                                        onSuccess {
                                            hidePageLoadingDialog()
                                            DialogGlobalTips(
                                                this@MineUsdRgRecharge,
                                                "提示",
                                                "确定",
                                                "",
                                                "您的订单已提交,我们会尽快为您充值，请稍等片刻"
                                            ).show()
                                            usdID.setText("")
                                            usdTime.text = ""
                                            etChangeMoney.setText("")
                                        }
                                        onFailed {
                                            hidePageLoadingDialog()
                                            ToastUtils.showToast(it.getMsg())
                                        }
                                    }
                                }
                            } else ToastUtils.showToast("请输入汇款金额")
                        } else ToastUtils.showToast("请输入汇款时间")
                    } else ToastUtils.showToast("请输入单号ID")
                } else ToastUtils.showToast("请输入代币地址")
            }
        }
        tvCopyEnd.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                if (tvAddress.text.isNotEmpty()){
                    ViewUtils.copyText(tvAddress.text.toString().trim())
                    ToastUtils.showToast("已复制到剪贴板")
                }else  ToastUtils.showToast("暂无可使地址")
            }
        }
    }

    override fun initData() {
        mPresenter.getList()
    }

    @SuppressLint("SetTextI18n")
    fun setTv() {
        try {
            val num = etChangeMoney.text.toString()
            if (cz_exchange_rate != BigDecimal.ZERO && num.isNotEmpty()) {
                val total = BigDecimal(num).multiply(cz_exchange_rate).setScale(2, BigDecimal.ROUND_HALF_DOWN)
                val spannableString = SpannableString("预计到账: $total CNY")
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#ff513e")),
                    "预计到账: ".length,
                    spannableString.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv_ss_2.text = spannableString
            }
        } catch (e: Exception) {
        }
    }

    var beanData: List<HandUsdt>? = null
    fun initCode(data: List<HandUsdt>?) {
        if (!data.isNullOrEmpty()) {
            try {
                beanData = data
                GlideUtil.loadImage(data[0].code_image, imgCodeScan)
                tvAddress.text = data[0].address
            } catch (e: Exception) {
            }
        } else ToastUtils.showToast("暂无USDT地址")
    }

    fun setUsedUsdt(it: List<UserBankCard>?) {
        if (!it.isNullOrEmpty()) {
            isHaveUsdt = true
            tvAddAddress.setTextColor(ViewUtils.getColor(R.color.alivc_blue))
            tvAddAddress.text = "常用地址"
            usdAddress.setText(it[it.lastIndex].no)
            for (item in it) {
                if (item.mark == "1") {
                    usdAddress.setText(item.no)
                    break
                }
            }
        }else{
            tvAddAddress.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
            tvAddAddress.text = "添加地址"
            usdAddress.setText("")
            isHaveUsdt = false
        }

        tvAddAddress.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (isHaveUsdt) {
                    val intent = Intent(this, MineUsdtUsdAct::class.java)
                    intent.putExtra("addressUsdt", usdAddress.text.toString())
                    startActivity(intent)
                } else {
                    val dialog = DialogAddUsdt(this, usdAddress.text.toString().trim())
                    dialog.setOnSuccessListener {
                        isHaveUsdt = true
                        tvAddAddress.setTextColor(ViewUtils.getColor(R.color.alivc_blue))
                        tvAddAddress.text = "常用地址"
                        val intent = Intent(this, MineUsdtUsdAct::class.java)
                        startActivity(intent)
                        dialog.dismiss()
                    }
                    dialog.show()
                }
            }
        }
        usdTime.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                timePicker()
            }
        }
    }

    private fun timePicker() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date()
        val mTimePicker = TimePickerBuilder(this,
            OnTimeSelectListener { date, v ->
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                val str: String = sdf.format(date)
                usdTime.text = str
            })
            .setType(TimePickerType.DATE)
            .setTitleText("时间选择")
            .isDialog(false)
            .setOutSideCancelable(true)
            .setDate(calendar)
            .build()
        mTimePicker.show()
    }

    var current = 0

    var currentType = ""

    var currentSd = ""


    inner class IconType : BaseRecyclerAdapter<String>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_usd

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: String?) {
            holder.text(R.id.childUsd, data)
            if (position == 0) currentType = data ?: ""
            val tv = holder.findViewById<TextView>(R.id.childUsd)
            if (current == position) {
                tv.background = ViewUtils.getDrawable(R.mipmap.ic_check)
            } else {
                tv.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            }
            holder.itemView.setOnClickListener {
                GlideUtil.loadImage(beanData?.get(position)?.code_image, imgCodeScan)
                tvAddress.text = beanData?.get(position)?.address
                current = position
                currentType = data ?: ""
                notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getUserAddress()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginOut(eventBean: UseAddress) {
        try {
            usdAddress?.setText(eventBean.address)
        } catch (e: Exception) {
        }

    }
}