package com.mine.children.recharge

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.customer.component.dialog.DialogPassWord
import com.customer.component.dialog.DialogSuccess
import com.customer.data.UserInfoSp
import com.customer.data.mine.*
import com.customer.utils.JsonUtils
import com.customer.utils.StringReplaceUtil
import com.glide.GlideUtil
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpPresenter
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import kotlinx.android.synthetic.main.fragment_cash_out.*
import java.math.BigDecimal
import java.util.*

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/25
 * @ Describe
 *
 */
class MineRechargeActChild2Presenter : BaseMvpPresenter<MineRechargeActChild2>() {


    var mineUserBank: MineUserBankList? = null


    fun getLimit(){
        MineApi.getMoneyLimit {
            onSuccess {
                if (mView.isActive()){
                    mView.high = it.highmoney?: BigDecimal(99999)
                    mView.low = it.lowmoney?:BigDecimal(50)
                }
            }
            onFailed { ToastUtils.showToast(it.getMsg()) }
        }
    }

    @SuppressLint("SetTextI18n")
    fun getBankList() {
        mView.showPageLoadingDialog()
        MineApi.getUserBankList {
            onSuccess {
                if (mView.isActive()) {
                    if (it.isNotEmpty()) {
                        mView.setGone(R.id.rlAddBankItem)
                        mView.setVisibility(R.id.rlBankItem, true)
                        mineUserBank = UserInfoSp.getSelectBankCard()
                        if (mineUserBank != null) {
                            GlideUtil.loadImage(mineUserBank?.bank_img, mView.imgBankItem)
                            mView.tvBankNameItem?.text = mineUserBank?.bank_name
                            mView.tvBankCodeItem?.text = "??????" + mineUserBank?.card_num?.substring(mineUserBank?.card_num?.length!! - 4, mineUserBank?.card_num!!.length) + "?????????"
                            mView.tvBankCodeUser?.text = StringReplaceUtil.userNameReplaceWithStar(mineUserBank?.realname)+"   |"
                        } else {
                            GlideUtil.loadImage(it[0].bank_img, mView.imgBankItem)
                            mView.tvBankNameItem?.text = it[0].bank_name
                            mView.tvBankCodeItem?.text = "??????" + it[0].card_num.substring(
                                it[0].card_num.length - 4,
                                it[0].card_num.length
                            ) + "?????????"
                            mineUserBank = it[0]
                            mView.tvBankCodeUser?.text =StringReplaceUtil.userNameReplaceWithStar(it[0].realname)+"   |"
                        }
                        mView.rlBankItem.setOnClickListener {
                            if (!FastClickUtil.isFastClick()) {
                                val intent = Intent(
                                    mView.requireContext(),
                                    MineUserBankCardListAct::class.java
                                )
                                intent.putExtra("cardID", mineUserBank?.card_num)
                                mView.requireContext().startActivity(intent)
                            }
                        }
                    } else {
                        mView.setGone(R.id.rlBankItem)
                        mView.setVisibility(R.id.rlAddBankItem, true)
                    }
                }

                getHl()
            }
            onFailed {
                ToastUtils.showToast(it.getMsg().toString())
                mView.hidePageLoadingDialog()
                getHl()
            }
        }
    }

    fun getCashOutMoney(isAll: Int,type: Int) {
        if (!TextUtils.isEmpty(mView.etGetMoneyToBank.text)) {
            val money = BigDecimal(mView.etGetMoneyToBank.text.toString())
            val balance = BigDecimal(mView.arguments?.getString("balance") ?: "0")
            if (balance.compareTo(money) != -1) {
                if (money.compareTo(mView.low) != -1) {
                    initDialog(isAll,type)
                } else ToastUtils.showToast("????????????????????????"+mView.low+"???")
            } else ToastUtils.showToast("????????????")
        } else ToastUtils.showToast("?????????????????????")

    }

    lateinit var dialog: DialogPassWord

    //???????????????
    private fun initDialog(isAll: Int,type: Int) {
        dialog = DialogPassWord(mView.requireContext(), ViewUtils.getScreenWidth(), ViewUtils.dp2px(156))
        dialog.setTextWatchListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 6) {
                    //??????????????????
                    mView.showPageLoadingDialog()
                    verifyPayPassWord(s.toString(),isAll,type)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        dialog.show()
    }

    //??????????????????
    fun verifyPayPassWord(passWord: String,isAll: Int,type: Int) {
        MineApi.verifyPayPass(passWord) {
            onSuccess {
                if (mView.isActive()) {
                    userCashOut(passWord,isAll,type)
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
                if (it.getCode() == 1002) {
                    dialog.showTipsText(
                        it.getMsg().toString() + "," + "?????????" + JsonUtils.fromJson(
                            it.getDataCode().toString(),
                            MinePassWordTime::class.java
                        ).remain_times.toString() + "?????????"
                    )
                    dialog.clearText()
                } else {
                    dialog.showTipsText(it.getMsg().toString())
                }
            }
        }
    }


    //?????? ??????
    private fun userCashOut(passWord: String,isAll:Int,type:Int) {
        if (mineUserBank != null) {
            val count = if (mView.current == 1)  mineUserBank?.card_num!! else  usdtList?.address
            LogUtils.e("====>"+count + "--"+ passWord +"--" + type +"--"+isAll)
            MineApi.userGetCashOut(
                mView.etGetMoneyToBank.text.toString().toDouble(),
                account = count?:"",
                passWord = passWord,
                type = type,
                is_all = isAll
            ) {
                onSuccess {
                    if (mView.isActive()) {
                        RxBus.get().post(
                            MineUpDateMoney(
                                "",
                                false
                            )
                        )
                        dialog.dismiss()
                        val diaSuc = mView.context?.let { it1 -> DialogSuccess(it1,"????????????",R.mipmap.ic_dialog_success,"?????????????????????????????????") }
                        diaSuc?.show()
                        val t = Timer()
                        t.schedule(object :TimerTask(){
                            override fun run() {
                                diaSuc?.dismiss()
                                t.cancel()
                            }

                        },2000)
                    }
                }
                onFailed {
                    ToastUtils.showToast(it.getMsg().toString())
                    dialog.dismiss()
                    mView.hidePageLoadingDialog()
                }
            }
        } else ToastUtils.showToast("??????????????????")
        mView.hidePageLoadingDialog()
    }

    var tx_exchange_rate = BigDecimal(1)
    var currentSd = ""
    fun getHl() {
        MineApi.getPayTypeList {
            onSuccess {
                if (mView.isActive()) {
                    mView.hidePageLoadingDialog()
                    for (item in it) {
                        if (item.pay_type == "usdtcz") {
                            tx_exchange_rate = item.tx_exchange_rate ?: BigDecimal(1)
                            mView.tv_ss_4.text = "????????????: 1" + item?.currency_type + "???" + item?.tx_exchange_rate + "CNY"
                            currentSd = item.currency_type ?: ""
                            mView.tv_ss_5.text = "????????????: 0 $currentSd"
                            break
                        }
                    }
                }
            }
            onFailed {
                mView.hidePageLoadingDialog()
                ToastUtils.showToast("??????????????????")
            }
        }
    }

    fun setTv() {
        if (mView.isActive()) {
            val num = mView.etGetMoneyToBank.text.toString()
            if(num.isNotEmpty()){
                val total = BigDecimal(num).divide(tx_exchange_rate, 4, BigDecimal.ROUND_HALF_DOWN)
                mView.tv_ss_5.text = "????????????: $total $currentSd"
            }

        }

    }

    var usdtList: USDTList? = null
    fun getUsdt() {
        MineApi.getUsdt {
            onSuccess {
                if (mView.isActive()) {
                    usdtList = UserInfoSp.getSelectUSDT()
                    if (it.isNullOrEmpty()) {
                        mView.setVisible(mView.rlAddBankItem_u1)
                        mView.setGone(mView.rlBankItem_u1)
                    } else {
                        mView.setGone(mView.rlAddBankItem_u1)
                        mView.setVisible(mView.rlBankItem_u1)
                        mView.tvBankNameItem_u1.text = it[0].name
                        usdtList = it[0]
                    }
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

    fun getFee(){
        MineApi.getDesportFee {
            onSuccess {
                if (mView.isActive()){
                    mView.handFee = it.hand_fee?: BigDecimal(1)
                    mView.serFee = it.service_fee?:BigDecimal(1)
                }
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
            }
        }
    }

}