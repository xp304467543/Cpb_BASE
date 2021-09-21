package com.mine.children

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Handler
import com.customer.ApiRouter
import com.customer.AppConstant
import com.customer.component.dialog.DialogGlobalTips
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.component.easyfloat.GlobalFloat
import com.customer.component.easyfloat.permission.PermissionUtils
import com.customer.data.AppChangeMode
import com.customer.data.CloseAppMode
import com.customer.data.LoginOut
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.mine.MineApi
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import kotlinx.android.synthetic.main.act_setting.*
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/23
 * @ Describe
 *
 */
@RouterAnno(host = "Mine", path = "setting")
class MineSettingAct : BaseNavActivity() {

    var mode = 0

    var code = 0 //等于10就是钱包冻结

    override fun getContentResID() = R.layout.act_setting

    override fun isSwipeBackEnable() = true

    override fun getPageTitle() = getString(R.string.ic_mine_setting)

    override fun isRegisterRxBus() = true

    override fun isShowBackIconWhite() = false


    @SuppressLint("SetTextI18n")
    override fun initContentView() {
        videoSwitch.isChecked = UserInfoSp.getOpenWindow()
        appModeSwitch.isChecked = UserInfoSp.getIsShowFloat()
        if (UserInfoSp.getAppMode() == AppMode.Pure){
            setGone(linLiveIcon)
        }
        tvVersion.text = "版本:"+AppConstant.version
    }

    override fun initData() {
        if (UserInfoSp.getUserType()!="4"){
            if (!UserInfoSp.getIsSetPayPassWord()) getIsSetPayPassWord() else tvPayPassWordSet.text = "支付密码修改"
        }
    }

    override fun initEvent() {
        btExitLogin.setOnClickListener {
            val dialog = DialogGlobalTips(this, "是否要退出登录!", "确定", "取消", "")
            dialog.setConfirmClickListener {
                GlobalDialog.spClear()
                RxBus.get().post(LoginOut(true))
                dialog.dismiss()

            }
            dialog.show()
        }

        //支付密码
        linSetPayPassWord.setOnClickListener {
            if (UserInfoSp.getUserType() == "4") {
                DialogTry(this).show()
                return@setOnClickListener
            }
            if (code != 10) {
                if (tvPayPassWordSet.text.toString().contains("设置")) {
                    Router.withApi(ApiRouter::class.java).toSetPassWord(1)
                } else Router.withApi(ApiRouter::class.java).toSetPassWord(0)
            } else ToastUtils.showToast("钱包已被冻结")
        }

        //密码修改
        setPassWord.setOnClickListener {
            if (UserInfoSp.getUserType() == "4") {
                DialogTry(this).show()
                return@setOnClickListener
            }
            Router.withApi(ApiRouter::class.java).toModifyPassWord()
        }

        videoSwitch.setOnCheckedChangeListener { _, isChecked ->
            UserInfoSp.putOpenWindow(isChecked)
        }

        appModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                showFloat()
                UserInfoSp.putIsShowFloat(true)
            }else {
                UserInfoSp.putIsShowFloat(false)
                GlobalFloat.closeAllFloat()
            }
        }
    }

    private fun showFloat() {
        HomeApi.getIsShowRed {
            onSuccess {
                checkPermission(true, it.red_end_time ?: BigDecimal.ZERO.subtract(it.red_start_timme ?: BigDecimal.ZERO))
            }
            onFailed {
                checkPermission(false)
            }
        }
    }

    /**
     * 检测浮窗权限是否开启，若没有给与申请提示框（非必须，申请依旧是EasyFloat内部内保进行）
     */
    private fun checkPermission(isRain: Boolean = false, time: BigDecimal = BigDecimal.ZERO) {
        if (PermissionUtils.checkPermission(this)) {
            openFloat(isRain,time)
        } else {
            AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage("需要您授权悬浮窗权限。")
                .setPositiveButton("去开启") { _, _ ->
                    openFloat(isRain,time)
                }
                .setNegativeButton("取消") { _, _ -> }
                .show()
        }
    }



    private fun openFloat(isRain:Boolean, time: BigDecimal = BigDecimal.ZERO){
        if (isRain){
            GlobalFloat.closeGlobalFloat()
            Handler().postDelayed({ GlobalFloat.showGlobalFloatRain(this@MineSettingAct, time) }, 500)
        }else{
            GlobalFloat.closeGlobalFloatRain()
            Handler().postDelayed({ GlobalFloat.showGlobalFloat(this@MineSettingAct) }, 500)
        }
    }


    override fun onResume() {
        super.onResume()
        if (UserInfoSp.getIsSetPayPassWord()) {
            tvPayPassWordSet.text = "支付密码修改"
            setGone(tvPayPassWordNotSet)
        }
    }

    //查询是否设置支付密码
    private fun getIsSetPayPassWord() {
        showPageLoadingDialog()
        MineApi.getIsSetPayPass {
            onSuccess {
                hidePageLoadingDialog()
                UserInfoSp.putIsSetPayPassWord(true)
                tvPayPassWordSet.text = "支付密码修改"
                setGone(tvPayPassWordNotSet)
            }
            onFailed {
                hidePageLoadingDialog()
                mode = 1
                if (tvPayPassWordNotSet != null) {
                    tvPayPassWordNotSet.text = it.getMsg()
                    tvPayPassWordNotSet.setTextColor(ViewUtils.getColor(R.color.alivc_blue_1))
                    code = it.getCode()
                }
            }
        }
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginOut(eventBean: LoginOut) {
        finish()
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun closeMode(eventBean: CloseAppMode) {
        appModeSwitch.isChecked = eventBean.isClose
    }

}