package com.mine.children

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.customer.ApiRouter
import com.customer.component.dialog.DialogGlobalTips
import com.customer.component.dialog.DialogPanInfo
import com.customer.component.dialog.DialogRoundRule
import com.customer.component.dialog.DialogRoundTimes
import com.customer.component.luckpan.LuckBean
import com.customer.component.luckpan.LuckItemInfo
import com.customer.component.luckpan.NewLuckView
import com.customer.data.UserInfoSp
import com.customer.data.home.HomeApi
import com.customer.data.home.PanGift
import com.customer.data.mine.MineApi
import com.customer.utils.JsonUtils
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import kotlinx.android.synthetic.main.act_prise.*
import java.math.BigDecimal
import java.math.BigInteger

/**
 *
 * @ Author  QinTian
 * @ Date  6/26/21
 * @ Describe
 *
 */
@RouterAnno(host = "Mine", path = "roundPrise")
class MinePriseAct : BaseMvpActivity<MinePriseActPresenter>() {

    var currentSel: Int = 1  // 1 余额  0 钻石
    var isClickZs = false //钻石盘 转的时候不能点击
    var isClickYe = false //余额盘 转的时候不能点击
    var currentTime = 0
    var singlePrise = BigDecimal(2) //单次抽奖价格
    var userBalance = BigDecimal(BigInteger.ZERO)
    var userDiamond = BigDecimal(BigInteger.ZERO)
    override fun getContentResID() = R.layout.act_prise

    override fun attachView()  = mPresenter.attachView(this)

    override fun attachPresenter() = MinePriseActPresenter()

    override fun isShowToolBar() = false

    override fun isSwipeBackEnable() = true

    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(topState)
         when(UserInfoSp.getAppMode()){
            AppMode.Normal -> {
                setVisible(modeChange)
            }
            AppMode.Pure -> {
                setGone(modeChange)
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        closePage.setOnClickListener {
            finish()
        }
        tvMyPrise.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                startActivity(Intent(this, MinePriseInfoAct::class.java))
            }
        }
        tvYecj.setOnClickListener {
            if (currentSel != 1) {
                currentSel = 1
                tvYecj.setTextColor(ViewUtils.getColor(R.color.white))
                tvZscj.setTextColor(ViewUtils.getColor(R.color.color_333333))
                tvYecj.delegate.backgroundColor = ViewUtils.getColor(R.color.puple_pan)
                tvZscj.delegate.backgroundColor = 0
                layoutBuy.setBackgroundResource(R.mipmap.ic_ye_bottom_bg)
                imgBackGround.setBackgroundResource(R.mipmap.ic_bg_ye)
                linTopV.background = ViewUtils.getDrawable(R.mipmap.ic_bg_ye1)
                imgInfo4.setImageResource(R.mipmap.ic_icon_ye)
                setTimes()
                setGone(luckViewZs)
                setGone(imgRoundPan2)
                setVisible(luckViewYe)
                setVisible(imgRoundPan)
                luckViewYe.postInvalidateDelayed(300)
            }
        }
        tvZscj.setOnClickListener {
            if (currentSel != 0) {
                currentSel = 0
                tvYecj.setTextColor(ViewUtils.getColor(R.color.color_333333))
                tvZscj.setTextColor(ViewUtils.getColor(R.color.white))
                tvYecj.delegate.backgroundColor = 0
                layoutBuy.setBackgroundResource(R.mipmap.ic_zs_bottom_bg)
                tvZscj.delegate.backgroundColor = ViewUtils.getColor(R.color.color_FF513E)
                imgBackGround.setBackgroundResource(R.mipmap.ic_bg_zs)
                linTopV.background = ViewUtils.getDrawable(R.mipmap.ic_bg_zs1)
                imgInfo4.setImageResource(R.mipmap.ic_icon_zs)
                setTimes()
                luckViewZs.currentSel = currentSel
                setGone(luckViewYe)
                setGone(imgRoundPan)
                setVisible(luckViewZs)
                setVisible(imgRoundPan2)
                luckViewZs.postInvalidateDelayed(300)
            }
        }
        tvBtBuy.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (currentSel == 1) {
                    Router.withApi(ApiRouter::class.java).toMineRecharge(0)
                } else {
                    val dialog = DialogRoundTimes(this)
                    dialog.getUserDiamondSuccessListener {
                        currentTime = it
                        setTimes()
                    }
                    dialog.getUserDiamondFailedListener {
                        dialog.dismiss()
                        val dialog2 = DialogPanInfo(this@MinePriseAct, 0)
                        dialog2.setConfirmClickListener {
                            dialog2.dismiss()
                            finish()
                        }
                        dialog2.show()
                    }
                    dialog.show()
                }

            }
        }
        imgRule.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                DialogRoundRule(this@MinePriseAct).show()
            }
        }
    }

    override fun initData() {
        mPresenter.getUserDiamond()
        mPresenter.updateNotice()
        mPresenter.getPanGiftYe()
        mPresenter.getPanGiftZs()
        mPresenter.getSinglePrise()
    }

    var itemsZs: ArrayList<LuckItemInfo>? = null
    var itemsYe: ArrayList<LuckItemInfo>? = null
    var idListZs: ArrayList<String>? = null
    var idListYe: ArrayList<String>? = null
    var total = 0



     fun initBitMapYe(urlList: ArrayList<String>) {
        initLucyYe(itemsYe, urlList)
    }

     fun initBitMapZs(urlList: ArrayList<String>) {
        initLucyZs(itemsZs, urlList)
    }


    private fun initLucyZs(items: ArrayList<LuckItemInfo>?, bitmapsZs: ArrayList<String>) {
        val luck = LuckBean()
        luck.details = items
        val operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        luckViewZs?.loadData(luck, bitmapsZs)
        luckViewZs?.setLuckViewListener(object : NewLuckView.LuckViewListener {
            override fun onClick() {
                    if (!isClickZs) {
                        if (currentTime > 0) {
                            isClickZs = true
                            luckViewZs?.startRolling()
                            mPresenter.getPrise()
                        } else {
                            DialogGlobalTips(
                                this@MinePriseAct,
                                "提示",
                                "知道了",
                                "",
                                "今日的抽奖次数已用完,您可以提高会员等级增加每日抽奖次数也可以单独购买抽奖次数"
                            ).show()
                        }
                    }
            }

            override fun onStart() {
                imgRoundPan2?.startAnimation(operatingAnim)
            }

            override fun onStop(index: Int) {
                imgRoundPan2?.clearAnimation()
                isClickZs = false
            }
        })
        luckViewZs.postInvalidateDelayed(2000)
    }


    private fun initLucyYe(items: ArrayList<LuckItemInfo>?, bitmapsZs: ArrayList<String>) {
        val luck = LuckBean()
        luck.details = items
        val operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        luckViewYe?.loadData(luck, bitmapsZs)
        luckViewYe?.setLuckViewListener(object : NewLuckView.LuckViewListener {
            override fun onClick() {
                if (!isClickYe) {
                    if (userBalance.compareTo(singlePrise) > -1) {
                        isClickYe = true
                        luckViewYe?.startRolling()
                        mPresenter.getPrise()
                    } else {
                        val dialog = DialogPanInfo(this@MinePriseAct, 1)
                        dialog.setConfirmClickListener {
                            dialog.dismiss()
                            finish()
                        }
                        dialog.show()
                    }
                }
            }

            override fun onStart() {
                imgRoundPan.startAnimation(operatingAnim)
            }

            override fun onStop(index: Int) {
                imgRoundPan?.clearAnimation()
                isClickYe = false
            }
        })
        luckViewYe.postInvalidateDelayed(2000)
    }



    @SuppressLint("SetTextI18n")
     fun setTimes() {
        if (currentSel == 0) {
            tvInfo5.text = "钻石:  "
            tvInfo1.text = "剩余 "
            tvInfo2.text = " $currentTime "
            tvInfo3.text = " 次抽奖"
            tvBtBuy.text = "购买抽奖次数"
            tvInfo6.text = userDiamond.toString()
        } else {
            tvInfo5.text = "余额:  "
            tvInfo1.text = "抽奖 "
            tvInfo2.text = " $singlePrise "
            tvInfo3.text = " 元/次"
            tvBtBuy.text = "充值"
            tvInfo6.text = userBalance.toString()
        }

    }



}