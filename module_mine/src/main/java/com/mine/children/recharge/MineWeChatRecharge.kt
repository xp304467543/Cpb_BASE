package com.mine.children.recharge

import android.annotation.SuppressLint
import com.customer.component.dialog.DialogGlobalTips
import com.customer.data.mine.MineApi
import com.glide.GlideUtil
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.xui.widget.picker.widget.builder.TimePickerBuilder
import com.lib.basiclib.base.xui.widget.picker.widget.configure.TimePickerType
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.TimeUtils
import com.lib.basiclib.utils.ToastUtils
import com.mine.R
import kotlinx.android.synthetic.main.act_wechat_recharge.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/20
 * @ Describe
 *
 */
class MineWeChatRecharge : BaseNavActivity() {

    var id = "-1"
    var max = BigDecimal(1000)
    var min = BigDecimal.ZERO

    override fun getContentResID() = R.layout.act_wechat_recharge

    override fun isSwipeBackEnable() = true

    override fun getPageTitle() = "微信扫码充值"

    override fun isShowBackIconWhite() = false


    override fun initData() {
        getInfo()
    }

    override fun initEvent() {
        tvTime.setOnClickListener { timePicker() }
        btGoRecharge.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (editName.text.toString().isNotEmpty()) {
                    if (editMoney.text.toString().isNotEmpty()) {
                        if (tvTime.text.toString().isNotEmpty()) {
                            if (BigDecimal(editMoney.text.toString()).compareTo(max) < 1 && BigDecimal(
                                    editMoney.text.toString()
                                ).compareTo(min) > -1
                            ) {
                                if (id != "-1") {
                                    MineApi.getWechatCharge(
                                        id,
                                        editName.text.toString().trim(),
                                        editMoney.text.toString().trim(),
                                        TimeUtils.getMillisecondByDate2(tvTime.text.toString())/1000
                                    ) {
                                        onSuccess {
                                            DialogGlobalTips(
                                                this@MineWeChatRecharge,
                                                "",
                                                "确定",
                                                "",
                                                it.msg
                                            ).show()
                                        }
                                        onFailed {
                                            DialogGlobalTips(
                                                this@MineWeChatRecharge,
                                                "",
                                                "确定",
                                                "",
                                                it.getMsg().toString()
                                            ).show()
                                        }
                                    }
                                } else ToastUtils.showToast("ID异常")
                            } else ToastUtils.showToast("存款金额为" + min.toInt() + "-" + max.toInt())
                        } else ToastUtils.showToast("请选择转账时间")
                    } else ToastUtils.showToast("请输入充值金额")
                } else ToastUtils.showToast("请输入存款人姓名")

            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun getInfo() {
        MineApi.getWechatInfo {
            onSuccess {
                if (!isDestroyed) {
                    try {
                        id = it[0].id ?: "-1"
                        GlideUtil.loadImage(it[0].code_image, weChatCode)
                        max = it[0].max_sum ?: BigDecimal(1000)
                        min = it[0].min_sum ?: BigDecimal(1000)
                        tvLimit.text = "备注:存款金额" + min.toInt() + "-" + max.toInt()
                    } catch (e: Exception) {
                    }
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

    private fun timePicker() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = Date()
        val mTimePicker = TimePickerBuilder(
            this
        ) { date, v ->
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val str: String = sdf.format(date)
            tvTime.text = str
        }
            .setType(TimePickerType.DATE)
            .setTitleText("时间选择")
            .isDialog(false)
            .setOutSideCancelable(true)
            .setDate(calendar)
            .build()
        mTimePicker.show()
    }

}