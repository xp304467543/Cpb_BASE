package com.mine.children.recharge

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import com.customer.component.dialog.GlobalDialog
import com.customer.data.MineSaveBank
import com.customer.data.UserInfoSp
import com.customer.data.mine.MineUpDateBank
import com.customer.data.mine.MineUpDateMoney
import com.customer.utils.MoneyValueFilter
import com.customer.utils.StringReplaceUtil
import com.glide.GlideUtil
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.base.xui.widget.popupwindow.ViewTooltip
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.mine.children.MineAddUstdAct
import kotlinx.android.synthetic.main.fragment_cash_out.*
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/25
 * @ Describe 提现
 *
 */

class MineRechargeActChild2 : BaseMvpFragment<MineRechargeActChild2Presenter>() {

    var balanceNow = "0"
    var current = 1

    var isAll = 0
    var type = 1
    var handFee = BigDecimal(1)
    var serFee = BigDecimal(1)

    var high = BigDecimal(99999)
    var low = BigDecimal(50)
    override fun getContentResID() = R.layout.fragment_cash_out

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineRechargeActChild2Presenter()

    override fun isRegisterRxBus() = true

    override fun initContentView() {
        balanceNow = arguments?.getString("balance") ?: "0"
    }

    override fun initData() {
        mPresenter.getBankList()
        mPresenter.getHl()
        mPresenter.getFee()
        mPresenter.getLimit()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (UserInfoSp.getSelectUSDT() == null) {
            mPresenter.getUsdt()
        } else {
            setGone(rlAddBankItem_u1)
            setVisible(rlBankItem_u1)
            tvBankNameItem_u1.text = UserInfoSp.getSelectUSDT()?.name
            mPresenter.usdtList = UserInfoSp.getSelectUSDT()
        }
    }

    override fun initEvent() {
        rlAddBankItem_u1.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                startActivity(Intent(getPageActivity(), MineAddUstdAct::class.java))
            }
        }
        rlBankItem_u1.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                startActivity(Intent(getPageActivity(), MineUsdtListAct::class.java))
            }
        }
        rlAddBankItem.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getIsSetPayPassWord()) {
                    startActivity(Intent(getPageActivity(), MineAddBankCardAct::class.java))
                } else GlobalDialog.noSetPassWord(requireActivity())
            }
        }
        tvGetMoneyAll.setOnClickListener {
            etGetMoneyToBank.setText(balanceNow)
        }
        btUserGetCash.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (etGetMoneyToBank.text.toString().trim().isEmpty()){
                    ToastUtils.showToast("请输入提现金额")
                    return@setOnClickListener
                }
                val q = BigDecimal(etGetMoneyToBank.text.toString().trim())
                val w = serFee.multiply(q)
                val e = handFee.add(w)
                val st4 = e.setScale(2, BigDecimal.ROUND_HALF_UP)
                val real =q.add(st4)

                if ( real.compareTo(BigDecimal(balanceNow)) == 1 ) {
                    ToastUtils.showToast("提现金额不能大于账户余额")
                    return@setOnClickListener
                }
                if (current == 1) {
                    mPresenter.getCashOutMoney(isAll, type)
                } else {
                    if (mPresenter.usdtList != null) {
                        mPresenter.getCashOutMoney(isAll, type)
                    } else ToastUtils.showToast("请添加虚拟钱包")
                }
            }

        }

        etGetMoneyToBank.filters = arrayOf<InputFilter>(MoneyValueFilter())
        etGetMoneyToBank.addTextChangedListener(object : TextWatcher {
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length!! > 0) {
                    isAll =
                        if (BigDecimal(p0.toString()).compareTo(BigDecimal(balanceNow)) == 0) 1 else 2

                    if (BigDecimal(p0.toString()).compareTo(high) == 1) {
                        ToastUtils.showToast("提现金额最大为:$high")
                        etGetMoneyToBank.setText(high.toString())
                        etGetMoneyToBank.setSelection(high.toString().length)
                    }
                    val q = BigDecimal(etGetMoneyToBank.text.toString().trim())
                    val w = serFee.multiply(q)
                    val e = handFee.add(w)
                    val st4 = e.setScale(2, BigDecimal.ROUND_HALF_UP)
                    val real =q.add(st4)
                    if ( real.compareTo(BigDecimal(balanceNow)) == 1 ) {
                        ToastUtils.showToast("提现金额不能大于账户余额")
                    }
                    val tes = p0.toString().trim()
                    val len = tes.length
                    if (len > 1 && tes.startsWith("0")) {
                        p0.replace(0, 1, "")

                    }
                    if (tes != "") {
                        mPresenter.setTv()
                    } else tv_ss_5.text = "预计支付: 0 ${mPresenter.currentSd}"
                    setFee(etGetMoneyToBank.text.toString().trim())
                } else {
                    if (current != 1) {
                        tv_ss_5.text = "预计支付: 0 ${mPresenter.currentSd}"
                    }
                    setGone(layout_Info)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        img_s_1.setOnClickListener {
            setGone(layouUstd)
            img_s_1.setImageResource(R.mipmap.ic_yes)
            img_s_2.setImageResource(R.mipmap.ic_no)
            current = 1
            type = 1
        }
        img_s_2.setOnClickListener {
            setVisible(layouUstd)
            img_s_1.setImageResource(R.mipmap.ic_no)
            img_s_2.setImageResource(R.mipmap.ic_yes)
            current = 2
            type = 2
            mPresenter.setTv()
        }

        layout_Info.setOnClickListener {
            val bai = serFee.multiply(BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP)
                .toString() + "%"
            val s1 = "固定费用:  "
            val s2 = "$handFee 元\n\n"
            val s3 = "转账手续续费:  "
            val s4 = serFee.multiply(BigDecimal(etGetMoneyToBank.text.toString()))
                .setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "(费率" + bai + ")\n\n"
            val s5 = "服务费共计:  "
            val s6 = handFee + serFee.multiply(BigDecimal(etGetMoneyToBank.text.toString()))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
            val spannableString = SpannableString(s1 + s2 + s3 + s4 + s5 + s6)
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#00a0e9")),
                s1.length,
                s1.length + s2.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#00a0e9")),
                s1.length + s2.length + s3.length,
                s1.length + s2.length + s3.length + s4.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#00a0e9")),
                s1.length + s2.length + s3.length + s4.length + s5.length,
                spannableString.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            ViewTooltip.on(imgInfo)
                .color(ViewUtils.getColor(R.color.grey_f5f7fa))
                .textColor(ViewUtils.getColor(R.color.color_333333))
                .position(ViewTooltip.Position.BOTTOM)
                .text(spannableString)
                .textSize(TypedValue.COMPLEX_UNIT_SP, 10f)
                .clickToHide(true)
                .animation(ViewTooltip.FadeTooltipAnimation(500))
                .show()
        }
    }


    fun setFee(string: String) {
        setVisible(layout_Info)
        val q = BigDecimal(string)
        val w = serFee.multiply(q)
        val e = handFee.add(w)
        val st1 = "(实际扣除:  "
        val st3 = ",手续费:  "
        val st4 = e.setScale(2, BigDecimal.ROUND_HALF_UP)
        val real = BigDecimal(string).add(st4)
        val spannableString = SpannableString("$st1$real$st3$st4)")
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#00a0e9")),
            st1.length,
            st1.length + real.toString().length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#00a0e9")),
            st1.length + real.toString().length + st3.length,
            spannableString.length - 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tv_Info.text = spannableString
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun saveUserBankSelect(event: MineSaveBank) {
        GlideUtil.loadImage(event.data.bank_img, imgBankItem)
        tvBankNameItem.text = event.data.bank_name
        tvBankCodeItem.text = "尾号" + event.data.card_num.substring(
            event.data.card_num.length - 4,
            event.data.card_num.length
        ) + "储蓄卡"
        tvBankCodeUser?.text = StringReplaceUtil.userNameReplaceWithStar(event.data.realname)+"   |"

        mPresenter.mineUserBank = event.data
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDateUserBankSelect(event: MineUpDateBank) {
        mPresenter.getBankList()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDateUserMoney(event: MineUpDateMoney) {
        if (event.isUpdate) {
            balanceNow = event.money
            hidePageLoadingDialog()
        }
    }

    companion object {
        fun newInstance(balance: String): MineRechargeActChild2 {
            val fragment = MineRechargeActChild2()
            val bundle = Bundle()
            bundle.putString("balance", balance)
            fragment.arguments = bundle
            return fragment
        }
    }


}