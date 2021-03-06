package com.customer.component.dialog

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.data.LotteryResetDiamond
import com.customer.data.UserInfoSp
import com.customer.data.lottery.BetBean
import com.customer.data.lottery.LotteryApi
import com.customer.data.lottery.PlaySecData
import com.customer.data.mine.MineApi
import com.customer.data.mine.UpDateUserPhoto
import com.customer.utils.AESUtils
import com.customer.utils.okutil.BaseCallBack
import com.customer.utils.okutil.BaseOkHttpClient
import com.fh.module_base_resouce.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ToastUtils
import kotlinx.android.synthetic.main.dialog_bottom_bet_access.*
import okhttp3.*
import java.io.IOException


/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/25
 * @ Describe
 *
 */
class BottomBetAccessDialog(
    context: Context,
    var lotteryId: String,
    var rightTop: String,
    var nextIssue: String,
    var is_bal: Int,
    var totalMoney: String,
    var dataList: MutableList<PlaySecData>
) : BottomSheetDialog(context) {

    private var singleMoney = 0L
    private var liveRoomBetAccessAdapter: LiveRoomBetAccessAdapter? = null

    init {
        setContentView(R.layout.dialog_bottom_bet_access)
        val root = this.delegate?.findViewById<View>(R.id.design_bottom_sheet)
        val behavior = root?.let { BottomSheetBehavior.from(it) }
        behavior?.isHideable = false
        this.delegate?.findViewById<View>(R.id.design_bottom_sheet)
            ?.setBackgroundColor(Color.TRANSPARENT)
        initContent()
        initLoading()
        initEvent()
    }

    private fun initContent() {
        singleMoney = if (totalMoney.isNotEmpty()) totalMoney.toLong() / dataList.size else 0
        for (item in dataList) {
            item.money = singleMoney.toString()
        }
        if (is_bal == 0) {
            tvTotalDiamond?.text = HtmlCompat.fromHtml(
                "???????????????: <font color=\"#FF513E\">$totalMoney</font>",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        } else {
            tvTotalDiamond?.text = HtmlCompat.fromHtml(
                "???????????????: <font color=\"#FF513E\">$totalMoney</font>",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
        liveRoomBetAccessAdapter = LiveRoomBetAccessAdapter()
        rvBetAccess?.adapter = liveRoomBetAccessAdapter
        rvBetAccess?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        liveRoomBetAccessAdapter?.refresh(dataList)
    }

    inner class LiveRoomBetAccessAdapter : BaseRecyclerAdapter<PlaySecData>() {

        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.adapter_bet_access
        }

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: PlaySecData?) {
            val edit = holder.findViewById<EditText>(R.id.tvBetPlayMoney)
            holder.text(R.id.tvBetPlayName, data?.play_sec_cname)
            holder.text(R.id.tvBetPlayType, data?.play_class_cname)
            holder.text(R.id.tvBetPlayOdds, data?.play_odds)
            //1???????????????TextWatcher??????2????????????????????????????????????
            if (edit.tag is TextWatcher) {
                edit.removeTextChangedListener(edit.tag as TextWatcher)
            }
            // ???2????????????TextWatcher???????????????EditText???Text???
            edit.setText(data?.money)

            val watcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                override fun afterTextChanged(editable: Editable) {
                    if (editable.isNotEmpty()) {
                        if (editable.toString().toLong() < 10) {
                            if (is_bal == 0) {
                                ToastUtils.showToast("????????????10?????????")
                            }
                            if (is_bal == 1) {
                                if (editable.toString().toLong() < 1) {
                                    ToastUtils.showToast("????????????1?????????")
                                }
                            }
                        }
                        val now = if (editable.length > 9) {
                            edit.setText(editable.substring(0, 9)) //?????????x???
                            edit.requestFocus()
                            edit.setSelection(edit.length()) //?????????????????????
                            editable.substring(0, 9)
                        } else {
                            editable.toString()
                        }
                        if (now == "") {
                            data?.money = "0"
                        } else data?.money = now
                        setMoney()
                    }else{
                        data?.money = "0"
                        setMoney()
                    }
                }
            }
            edit.addTextChangedListener(watcher)
            edit.tag = watcher
            holder.findViewById<TextView>(R.id.tvBetDelete).setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    liveRoomBetAccessAdapter?.delete(position)
                    setMoney()
                } else ToastUtils.showToast("??????????????????")
            }
        }
    }


    private fun setMoney() {
        var total = 0L
        for (it in liveRoomBetAccessAdapter?.data!!) {
            if (it.money != "") {
                total += it.money?.toInt() ?: 0
            }
        }
        totalMoney = total.toString()
        if (is_bal == 0) {
            tvTotalDiamond?.text = HtmlCompat.fromHtml(
                "???????????????: <font color=\"#FF513E\">$total</font>",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        } else {
            tvTotalDiamond?.text = HtmlCompat.fromHtml(
                "???????????????: <font color=\"#FF513E\">$total</font>",
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }

    }

    private fun initLoading() {
        loadingDialog = LoadingDialog(context)
        loadingDialog?.setCanceledOnTouchOutside(false)
    }


    private fun initEvent() {
        tvBetAccessSubmit.setOnClickListener {
            //??????
            loadingDialog?.show()
            try {
                val id = lotteryId
                val issue = nextIssue
                val bean = arrayListOf<BetBean>()
                if (!dataList.isNullOrEmpty()) {
                    if (!liveRoomBetAccessAdapter?.data.isNullOrEmpty()) {
                        for (result in liveRoomBetAccessAdapter?.data!!) {
//                                val money = if (result.money == "0"){
//                                    ((totalMoney.toInt())/(liveRoomBetAccessAdapter?.data?.size?:1)).toString()
//                                }else  result.money
                            if (result.money.isNullOrEmpty() || result.money == "0"){
                                ToastUtils.showToast("???????????????,?????????????????????")
                                loadingDialog?.dismiss()
                                return@setOnClickListener
                            }
                            bean.add(
                                BetBean(
                                    result.play_sec_name,
                                    result.play_class_name,
                                    result.money,
                                    result.play_bet_issue,
                                    if (result.isLong) result.lottery_id else id
                                )
                            )
                        }
                        lotteryToBet(id, issue, bean,dataList[0].isLong)
                    } else ToastUtils.showToast("??????????????????????????????????????????")
                }
            } catch (e: Exception) {
                ToastUtils.showToast(e.message.toString())
            }
        }
    }


    /**
     * ??????
     */
    private var loadingDialog: LoadingDialog? = null

    private fun lotteryToBet(
        play_bet_lottery_id: String,
        play_bet_issue: String,
        order_detail: ArrayList<BetBean>,
        isLong:Boolean
    ) {
        val orderMap = hashMapOf<String, Any>()
        val goon = GsonBuilder().disableHtmlEscaping().create()
        val orderString = goon.toJson(order_detail).toString()
        if (isLong)orderMap["orders"] = orderString else{
            orderMap["play_bet_lottery_id"] = play_bet_lottery_id
            orderMap["play_bet_issue"] = play_bet_issue
            orderMap["play_bet_follow_user"] = "0"
            orderMap["order_detail"] = orderString
            orderMap["is_bl_play"] = is_bal
            orderMap["timestamp"] = System.currentTimeMillis()
        }

        AESUtils.encrypt(UserInfoSp.getRandomStr() ?: "", goon.toJson(orderMap))?.let {
            val param = HashMap<String, String>()
            param["datas"] = it
            val builder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)//????????????
                .addFormDataPart("datas", it)
            val requestBody = builder.build()
            val url = if (!isLong) MineApi.getBaseUrlMoments() + LotteryApi.LOTTERY_BET else MineApi.getBaseUrlMoments() + LotteryApi.LONG_BET
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", UserInfoSp.getTokenWithBearer().toString())
                .post(requestBody)
                .build()
            val client = OkHttpClient.Builder().build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Looper.prepare()
                    loadingDialog?.dismiss()
                    ToastUtils.showToast(e.toString())
                    Looper.loop()
                }
                override fun onResponse(call: Call, response: Response) {
                    if (isShowing) {
                        Looper.prepare()
                        try {
                            val json = JsonParser.parseString(response.body?.string()!!).asJsonObject
                            if (json.get("code").asInt == 1) {
                                //????????????
                                loadingDialog?.dismiss()
                                context.let { it1 ->
                                    DialogGlobalTips(
                                        it1,
                                        "????????????",
                                        "??????",
                                        "",
                                        ""
                                    ).show()
                                }
                                RxBus.get().post(LotteryResetDiamond(true))
                                dismiss()
                            } else {
                                loadingDialog?.dismiss()
                                if (json.get("code").asInt == 1012) {
                                    if (is_bal == 1) {
                                        ToastUtils.showToast("???????????????")
                                    } else {
                                        ToastUtils.showToast("????????????????????????")
                                    }
                                } else ToastUtils.showToast(json.get("msg").asString)
                            }
                        } catch (e: Exception) {
                            loadingDialog?.dismiss()
                            e.printStackTrace()
                            ToastUtils.showToast("????????????,????????????")
                        }
                        Looper.loop()
                    }
                }
            })

//            LotteryApi.toBet(it) {
//                onSuccess {
//                    //????????????
//                    loadingDialog?.dismiss()
//                    context.let { it1 -> DialogGlobalTips(it1, "????????????", "??????", "", "").show() }
//                    RxBus.get().post(LotteryResetDiamond(true))
//                    dismiss()
//                }
//                onFailed { err ->
//                    loadingDialog?.dismiss()
//                    if (err.getCode() == 1012) {
//                        if (is_bal == 1) {
//                            ToastUtils.showToast("???????????????")
//                        } else {
//                            ToastUtils.showToast("????????????????????????")
//                        }
//                    } else ToastUtils.showToast(err.getMsg())
//                }
//            }
        }
    }
}