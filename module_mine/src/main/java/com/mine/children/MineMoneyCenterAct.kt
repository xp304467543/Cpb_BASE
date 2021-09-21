package com.mine.children

import android.graphics.Color
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.TypedValue.COMPLEX_UNIT_SP
import com.customer.ApiRouter
import com.customer.component.dialog.DialogGlobalTips
import com.customer.data.UserInfoSp
import com.customer.data.mine.Third
import com.customer.data.urlCustomer
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.xui.widget.picker.widget.OptionsPickerView
import com.lib.basiclib.base.xui.widget.picker.widget.builder.OptionsPickerBuilder
import com.lib.basiclib.base.xui.widget.popupwindow.ViewTooltip
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.impl.Router
import kotlinx.android.synthetic.main.act_mine_money_center.*
import kotlinx.android.synthetic.main.layout_grild_game_center.*
import java.math.BigDecimal


/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/27
 * @ Describe
 *
 */
class MineMoneyCenterAct : BaseMvpActivity<MineMoneyCenterActPresenter>() {


    var thirdList: ArrayList<Third>? = null

    var thirdChineseList: ArrayList<String>? = null

    private var pvOptionsLeft: OptionsPickerView<String>? = null

    private var pvOptionsRight: OptionsPickerView<String>? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineMoneyCenterActPresenter()

    override fun getContentResID() = R.layout.act_mine_money_center

    override fun isShowToolBar() = false

    override fun isShowBackIconWhite() = false

    override fun isSwipeBackEnable() = true


    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(centerView)
        val spannableString = SpannableString("如需帮助,请 联系客服")
        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#FF1486FF")),
            7,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        tvCustomer.text = spannableString
    }

    override fun initData() {
        mPresenter.getUserBalance()
        mPresenter.getThird()
        mPresenter.getIsAutoChange()
    }

    override fun initEvent() {
        tvShowMore.setOnClickListener {
                setGone(layoutNormal)
                setVisible(layoutMore)
        }
        tv_down.setOnClickListener {
            setVisible(layoutNormal)
            setGone(layoutMore)
        }
        imgUp.setOnClickListener {
            setVisible(layoutNormal)
            setGone(layoutMore)
        }
        tv_down.setOnClickListener {
            setVisible(layoutNormal)
            setGone(layoutMore)
        }
        imgBack.setOnClickListener {
            finish()
        }
        tvCustomer.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java)
                    .toGlobalWeb(UserInfoSp.getCustomer() ?: urlCustomer)
            }
        }
        tvMoneyCenter.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (thirdList.isNullOrEmpty()) mPresenter.getThird()
                showPickerViewLeft()
            }
        }
        tvMoneyGame.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (thirdList.isNullOrEmpty()) mPresenter.getThird()
                showPickerViewRight()
            }
        }


        etMoney.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    ViewUtils.setGone(tvMoneyTips)
                    return
                }
                if (s.length == 1 && s.toString() == ".") {
                    etMoney.setText("")
                    return
                }
                if (s.toString().contains(".")) {
                    if (s.length - 1 - s.toString().indexOf(".") > 2) {
                        val str = s.toString().subSequence(0, s.toString().indexOf(".") + 3)
                        etMoney.setText(str)
                        etMoney.setSelection(str.length)
                    }
                } else {
                    if (s.length > 8) {
                        etMoney.setText(s.subSequence(0, 8))
                        etMoney.setSelection(etMoney.text.length)
                    }
                }

                if (s.toString().trim().substring(0) == ".") {
                    val str = "0$s"
                    etMoney.setText(str)
                    etMoney.setSelection(2)
                }

                if (s.toString().startsWith("0") && s.toString().trim().length > 1) {
                    if (s.toString().substring(1, 2) != ".") {
                        etMoney.setText(s.subSequence(0, 1))
                        etMoney.setSelection(1)
                        return
                    }
                }
                val all = s.toString().trim()
                val t1 = tvCenterMoney.text.toString()
                val t2 = tv_qp_money.text.toString()
                val t3 = tv_ag_money.text.toString()
                val t4 = tv_bg_money.text.toString()
                val t5 = tv_money_4.text.toString()
                val t6 = tv_money_5.text.toString()
                val t7 = tv_money_6.text.toString()

                if (currentLeft == 0) {
                    if (BigDecimal(t1).compareTo(BigDecimal(all)) == -1) {
                        ViewUtils.setVisible(tvMoneyTips)
                    } else ViewUtils.setGone(tvMoneyTips)
                } else {
                    when (thirdList?.get(currentLeft)?.name) {
                        "fh_chess" -> {
                            if (BigDecimal(t2).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                            } else ViewUtils.setGone(tvMoneyTips)
                        }
                        "ag" -> {
                            if (t3 == "维护中") return
                            if (BigDecimal(t3).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                            } else ViewUtils.setGone(tvMoneyTips)
                        }
                        "bg" -> {
                            if (t4 == "维护中") return
                            if (BigDecimal(t4).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                            } else ViewUtils.setGone(tvMoneyTips)
                        }
                        "ky" -> {
                            if (t5 == "维护中") return
                            if (BigDecimal(t5).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                            } else ViewUtils.setGone(tvMoneyTips)
                        }
                        "ibc" -> {
                            if (t6 == "维护中") return
                            if (BigDecimal(t6).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                            } else ViewUtils.setGone(tvMoneyTips)
                        }
                        "im" -> {
                            if (t7 == "维护中") return
                            if (BigDecimal(t7).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                            } else ViewUtils.setGone(tvMoneyTips)
                        }
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        btSure.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (currentLeft == currentRight) {
                    ToastUtils.showToast("转出账户 和 转入账户 不能相同")
                    return@setOnClickListener
                }
                if (etMoney.text.isNullOrEmpty()) {
                    ToastUtils.showToast("请输入转账金额")
                    return@setOnClickListener
                }
                if (BigDecimal(etMoney.text.toString()).compareTo(BigDecimal(1)) == -1) {
                    ToastUtils.showToast("转账金额不能小于1")
                    return@setOnClickListener
                }
                toChangeMoney()
            }
        }
        tvAll.setOnClickListener {
            val t1 = tvCenterMoney.text.toString()
            val t2 = tv_qp_money.text.toString()
            val t3 = tv_ag_money.text.toString()
            val t4 = tv_bg_money.text.toString()
            val t5 = tv_money_4.text.toString()
            val t6 = tv_money_5.text.toString()
            val t7 = tv_money_6.text.toString()
            if (currentLeft == 0) {
                if (BigDecimal(t1) == BigDecimal.ZERO) {
                    ToastUtils.showToast("转账金额不能小于1")
                    return@setOnClickListener
                }
                etMoney.setText(t1)
            } else {
                when (thirdList?.get(currentLeft)?.name) {
                    "fh_chess" -> {
                        if (BigDecimal(t2) == BigDecimal.ZERO) {
                            ToastUtils.showToast("转账金额不能小于1")
                            return@setOnClickListener
                        }
                        etMoney.setText(t2)
                    }
                    "ag" -> {
                        if (t3 != "维护中") {
                            if (BigDecimal(t3) == BigDecimal.ZERO) {
                                ToastUtils.showToast("转账金额不能小于1")
                                return@setOnClickListener
                            }
                            etMoney.setText(t3)
                        }
                    }
                    "bg" -> {
                        if (t4 != "维护中") {
                            if (BigDecimal(t4) == BigDecimal.ZERO) {
                                ToastUtils.showToast("转账金额不能小于1")
                                return@setOnClickListener
                            }
                            etMoney.setText(t4)
                        }
                    }

                    "ky" -> {
                        if (t5 != "维护中") {
                            if (BigDecimal(t5) == BigDecimal.ZERO) {
                                ToastUtils.showToast("转账金额不能小于1")
                                return@setOnClickListener
                            }
                            etMoney.setText(t5)
                        }
                    }
                    "ibc" -> {
                        if (t6 != "维护中") {
                            if (BigDecimal(t6) == BigDecimal.ZERO) {
                                ToastUtils.showToast("转账金额不能小于1")
                                return@setOnClickListener
                            }
                            etMoney.setText(t6)
                        }
                    }
                    "im" -> {
                        if (t7 != "维护中") {
                            if (BigDecimal(t7) == BigDecimal.ZERO) {
                                ToastUtils.showToast("转账金额不能小于1")
                                return@setOnClickListener
                            }
                            etMoney.setText(t7)
                        }
                    }
                }
            }
            etMoney.setSelection(etMoney.text.length)
        }
        imgTips.setOnClickListener {
            ViewTooltip.on(imgTips)
                .color(ViewUtils.getColor(R.color.color_F1F0F6))
                .textColor(ViewUtils.getColor(R.color.color_666666))
                .position(ViewTooltip.Position.BOTTOM)
                .text(" 把游戏场馆的余额一键回收到中心钱包 ")
                .textSize(COMPLEX_UNIT_SP, 12f)
                .clickToHide(true)
                .autoHide(true, 5000)
                .animation(ViewTooltip.FadeTooltipAnimation(500))
                .show()
        }
        tvRecycle.setOnClickListener {
            if (!FastClickUtil.isTenFastClick()) {
                mPresenter.recycleAll()
            } else ToastUtils.showToast("点击过于频繁,请10秒后重试")
        }
        refreshMoney.setOnClickListener {
            if (!FastClickUtil.isTenFastClick2()) {
                mPresenter.getUserBalance()
            } else ToastUtils.showToast("点击过于频繁,请10秒后重试")
        }
    }

    /**
     * 转账
     */
    private fun toChangeMoney() {
        val all = etMoney.text.toString()
        val t1 = tvCenterMoney.text.toString()
        val t2 = tv_qp_money.text.toString()
        val t3 = tv_ag_money.text.toString()
        val t4 = tv_bg_money.text.toString()
        val t5 = tv_money_4.text.toString()
        val t6 = tv_money_5.text.toString()
        val t7 = tv_money_6.text.toString()
        val t8 = tv_money_7.text.toString()
        val t9 = tv_money_8.text.toString()
        val t10 = tv_money_9.text.toString()
        val t11 = tv_money_10.text.toString()
        val t12 = tv_money_11.text.toString()
        when {
            currentLeft == 0 -> {
                if (BigDecimal(t1).compareTo(BigDecimal(all)) == -1) {
                    ViewUtils.setVisible(tvMoneyTips)
                    ToastUtils.showToast("余额不足")
                } else {
                    when (thirdList?.get(currentRight)?.name) {
                        "fh_chess" -> mPresenter.upAndDownMoney(1, true, etMoney.text.toString())
                        "ag" -> {
                            if (t3 != "维护中") {
                                mPresenter.upAndDownMoney(2, true, etMoney.text.toString())
                            } else ToastUtils.showToast("AG平台维护中")
                        }
                        "bg" -> {
                            if (t4 != "维护中") {
                                mPresenter.upAndDownMoney(3, true, etMoney.text.toString())
                            } else ToastUtils.showToast("BG平台维护中")
                        }
                        "ky"->{
                            if (t5 != "维护中") {
                                mPresenter.upAndDownMoney(4, true, etMoney.text.toString())
                            } else ToastUtils.showToast("开元棋牌平台维护中")
                        }
                        "ibc" -> {
                            if (t6 != "维护中") {
                                mPresenter.upAndDownMoney(5, true, etMoney.text.toString())
                            } else ToastUtils.showToast("沙巴平台维护中")
                        }
                        "im" ->{
                            if (t7 != "维护中") {
                                mPresenter.upAndDownMoney(6, true, etMoney.text.toString())
                            } else ToastUtils.showToast("IM平台维护中")
                        }
                        "bbin" ->{
                            if (t8 != "维护中") {
                                mPresenter.upAndDownMoney(7, true, etMoney.text.toString())
                            } else ToastUtils.showToast("BBIN平台维护中")
                        }
                        "ae" ->{
                            if (t9 != "维护中") {
                                mPresenter.upAndDownMoney(8, true, etMoney.text.toString())
                            } else ToastUtils.showToast("AE平台维护中")
                        }
                        "mg" ->{
                            if (t10 != "维护中") {
                                mPresenter.upAndDownMoney(9, true, etMoney.text.toString())
                            } else ToastUtils.showToast("MG平台维护中")
                        }
                        "cmd" ->{
                            if (t11 != "维护中") {
                                mPresenter.upAndDownMoney(10, true, etMoney.text.toString())
                            } else ToastUtils.showToast("CMD平台维护中")
                        }
                        "sbo" ->{
                            if (t12 != "维护中") {
                                mPresenter.upAndDownMoney(11, true, etMoney.text.toString())
                            } else ToastUtils.showToast("SBO平台维护中")
                        }
                    }
                }
            }
            currentRight == 0 -> {
                when (thirdList?.get(currentLeft)?.name) {
                    "fh_chess" -> {
                        if (BigDecimal(t2).compareTo(BigDecimal(all)) == -1) {
                            ViewUtils.setVisible(tvMoneyTips)
                            ToastUtils.showToast("余额不足")
                        } else {
                            mPresenter.upAndDownMoney(1, false, etMoney.text.toString())
                        }

                    }
                    "ag" -> {
                        if (t3 != "维护中") {
                            if (BigDecimal(t3).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.upAndDownMoney(2, false, etMoney.text.toString())
                            }
                        } else ToastUtils.showToast("AG平台维护中")
                    }
                    "bg" -> {
                        if (t4 != "维护中") {
                            if (BigDecimal(t4).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.upAndDownMoney(3, false, etMoney.text.toString())
                            }
                        } else ToastUtils.showToast("BG平台维护中")
                    }
                    "ky"->{
                        if (t5 != "维护中") {
                            mPresenter.upAndDownMoney(4, false, etMoney.text.toString())
                        } else ToastUtils.showToast("开元棋牌平台维护中")
                    }
                    "ibc" -> {
                        if (t6 != "维护中") {
                            mPresenter.upAndDownMoney(5, false, etMoney.text.toString())
                        } else ToastUtils.showToast("沙巴平台维护中")
                    }
                    "im" -> {
                        if (t7 != "维护中") {
                            mPresenter.upAndDownMoney(6, false, etMoney.text.toString())
                        } else ToastUtils.showToast("IM平台维护中")
                    }
                    "bbin" -> {
                        if (t8 != "维护中") {
                            mPresenter.upAndDownMoney(7, false, etMoney.text.toString())
                        } else ToastUtils.showToast("BBIN平台维护中")
                    }
                    "ae" -> {
                        if (t9 != "维护中") {
                            mPresenter.upAndDownMoney(8, false, etMoney.text.toString())
                        } else ToastUtils.showToast("AE平台维护中")
                    }
                    "mg" -> {
                        if (t10 != "维护中") {
                            mPresenter.upAndDownMoney(9, false, etMoney.text.toString())
                        } else ToastUtils.showToast("MG平台维护中")
                    }
                    "cmd" -> {
                        if (t11 != "维护中") {
                            mPresenter.upAndDownMoney(10, false, etMoney.text.toString())
                        } else ToastUtils.showToast("CMD平台维护中")
                    }
                    "sbo" -> {
                        if (t12 != "维护中") {
                            mPresenter.upAndDownMoney(11, false, etMoney.text.toString())
                        } else ToastUtils.showToast("SBO平台维护中")
                    }
                }
            }
            else -> {
                val left = thirdList?.get(currentLeft)?.name
                val right = thirdList?.get(currentRight)?.name
                when (thirdList?.get(currentLeft)?.name) {
                    "fh_chess" -> {
                        if (t2 != "维护中") {
                            if (BigDecimal(t2).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        }else ToastUtils.showToast("乐购棋牌维护中")
                    }
                    "ag" -> {
                        if (t3 != "维护中") {
                            if (BigDecimal(t3).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        }else  ToastUtils.showToast("AG平台维护中")

                    }
                    "bg" -> {
                        if (t4 != "维护中") {
                            if (BigDecimal(t4).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        }else ToastUtils.showToast("BG平台维护中")
                    }
                    "ky"->{
                        if (t5 != "维护中") {
                            if (BigDecimal(t5).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("开元棋牌平台维护中")
                    }
                    "ibc" -> {
                        if (t6 != "维护中") {
                            if (BigDecimal(t6).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("沙巴平台维护中")
                    }
                    "im" -> {
                        if (t7 != "维护中") {
                            if (BigDecimal(t7).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("IM平台维护中")
                    }

                    "bbin" -> {
                        if (t8 != "维护中") {
                            if (BigDecimal(t8).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("BBIN平台维护中")
                    }
                    "ae" -> {
                        if (t9 != "维护中") {
                            if (BigDecimal(t9).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("AE平台维护中")
                    }
                    "mg" -> {
                        if (t10 != "维护中") {
                            if (BigDecimal(t10).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("MG平台维护中")
                    }
                    "cmd" -> {
                        if (t11 != "维护中") {
                            if (BigDecimal(t11).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("CMD平台维护中")
                    }
                    "sbo" -> {
                        if (t12 != "维护中") {
                            if (BigDecimal(t12).compareTo(BigDecimal(all)) == -1) {
                                ViewUtils.setVisible(tvMoneyTips)
                                ToastUtils.showToast("余额不足")
                            } else {
                                mPresenter.platformChange(all, left ?: "", right ?: "")
                            }
                        } else ToastUtils.showToast("SBO平台维护中")
                    }
                }
            }
        }
    }

    var currentLeft = 0
    private fun showPickerViewLeft() {
        if (!thirdChineseList.isNullOrEmpty()) {
            pvOptionsLeft = OptionsPickerBuilder(this) { _, options1, _, _ ->
                setGone(tvMoneyTips)
                currentLeft = options1
                tvMoneyCenter?.text = thirdChineseList?.get(currentLeft)
                false
            }
                .setTitleText("转出账户")
                .setTitleSize(13)
                .setTitleColor(R.color.grey_95)
                .setContentTextSize(18)
                .build()
            pvOptionsLeft?.setPicker(thirdChineseList)
            pvOptionsLeft?.show()
        }
    }

    var currentRight = 1
    private fun showPickerViewRight() {
        if (!thirdList.isNullOrEmpty()){
            pvOptionsRight = OptionsPickerBuilder(this) { _, options1, _, _ ->
                setGone(tvMoneyTips)
                currentRight = options1
                tvMoneyGame?.text = thirdChineseList?.get(currentRight)
                false
            }
                .setTitleText("转入账户")
                .setTitleSize(13)
                .setTitleColor(R.color.grey_95)
                .setContentTextSize(18)
                .build()
            pvOptionsRight?.setPicker(thirdChineseList)
            pvOptionsRight?.show()
        }

    }


    fun initCheck(boolean: Boolean) {
        moneySwitch.isChecked = boolean
        moneySwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                val dialog = DialogGlobalTips(this, "温馨提醒", "确定", "取消", "是否关闭自动转账功能！")
                dialog.setConfirmClickListener {
                    mPresenter.setPlatformChange(true)
                }
                dialog.setCanCalClickListener {
                    moneySwitch.setCheckedImmediatelyNoEvent(true)
                }
                dialog.setCanceledOnTouchOutside(false)
                dialog.setCancelable(false)
                dialog.show()
            } else {
                mPresenter.setPlatformChange(false)
            }
        }
    }

}