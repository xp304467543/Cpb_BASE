package com.customer.component.activity

import com.fh.module_base_resouce.R
import com.lib.basiclib.base.mvp.BaseMvpActivity

import android.os.CountDownTimer
import com.customer.component.dialog.DialogRedRain
import com.customer.component.dialog.GlobalDialog
import com.customer.component.easyfloat.GlobalFloat
import com.customer.component.rain.RainViewGroup
import com.customer.data.LoginOut
import com.customer.data.home.HomeApi
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.activity_red_rain.*


/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/18
 * @ Describe
 *
 */
@RouterAnno(host = "Base", path = "RainAct")
class RedRainActivity : BaseMvpActivity<RedRainActivityPresenter>() {

    var isClickRed = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = RedRainActivityPresenter()

    override fun isSwipeBackEnable() = false

    override fun isShowToolBar() = false

    override fun getContentResID() =  R.layout.activity_red_rain


    var countDownTimer:CountDownTimer?=null
    override fun initData() {
        countDownTimer = object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                when ((millisUntilFinished / 1000).toInt()) {
                    5 -> {
                        imgCount.setImageResource(R.mipmap.redrain_5)
                    }
                    4 -> {
                        imgCount.setImageResource(R.mipmap.redrain_4)
                    }
                    3 -> {
                        imgCount.setImageResource(R.mipmap.redrain_3)
                    }
                    2 -> {
                        imgCount.setImageResource(R.mipmap.redrain_2)
                    }
                    1 -> {
                        imgCount.setImageResource(R.mipmap.redrain_1)
                    }
                }
            }

            override fun onFinish() {
                setGone(layoutClock)
                setGone(ImgLo)
                redRain()
            }
        }
        countDownTimer?.start()
    }


    override fun initEvent() {
        rainView.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
               if (isClickRed) getRedRainAmount()
            }
        }
        closeRain.setOnClickListener {
            countDownTimer?.cancel()
            finish()
        }
    }


    private fun redRain() {
        rainView.setIsIntercept(true)
        //屏幕中最多显示item的数量
        rainView?.setAmount(50);
        //设置下落的次数。在保持密度不变的情况下，设置下落数量。例如：数量 = 50，下落次数 = 3，总共数量150。
        // rainView.setTimes(2);
        //设置无效循环
        rainView?.setTimes(RainViewGroup.INFINITE)
        rainView?.start()
        isClickRed = true
    }

    private fun getRedRainAmount() {
        HomeApi.getRedRain {
            onSuccess {
                val dialog = DialogRedRain(this@RedRainActivity, it.amount)
                dialog.setOnDismissListener {
                    if (dialog.canContain){
                        getRedRainAmount()
                    }else{
                        GlobalFloat.closeGlobalFloatRain()
                        GlobalFloat.showGlobalFloat(this@RedRainActivity)
                        rainView?.stop()//
                        rainView?.setIsIntercept(false)
                        finish()
                    }

                }
                dialog.show()
                isClickRed = false
            }
            onFailed {
                ToastUtils.showToast(it.getMsg())
                rainView?.stop()
                rainView?.setIsIntercept(false)
                if (it.getCode() == 2003){
                    RxBus.get().post(LoginOut(true))
                }
                isClickRed = true
                finish()
            }
        }
    }
}

