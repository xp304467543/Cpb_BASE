package com.mine.children

import com.customer.data.mine.MineApi
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.mine.R
import kotlinx.android.synthetic.main.act_ustdd.*

/**
 *
 * @ Author  QinTian
 * @ Date  4/10/21
 * @ Describe
 *
 */
class MineAddUstdAct : BaseMvpActivity<MineAddUstdActPresenter>() {

    var current = "TCR20"

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MineAddUstdActPresenter()

    override fun isShowBackIconWhite() = false

    override fun getContentResID() = R.layout.act_ustdd

    override fun isSwipeBackEnable() = true

    override fun getPageTitle() = "添加虚拟币钱包地址"


    override fun initEvent() {
        tv_xy1.setOnClickListener {
            tv_xy1.background = ViewUtils.getDrawable(R.mipmap.ic_check)
            tv_xy2.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            tv_xy3.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            current = "TCR20"
        }
        tv_xy2.setOnClickListener {
            tv_xy1.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            tv_xy2.background = ViewUtils.getDrawable(R.mipmap.ic_check)
            tv_xy3.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            current = "ECR20"
        }
        tv_xy3.setOnClickListener {
            tv_xy1.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            tv_xy2.background = ViewUtils.getDrawable(R.mipmap.ic_uncheck)
            tv_xy3.background = ViewUtils.getDrawable(R.mipmap.ic_check)
            current = "OMNI"
        }

        btnSubmit.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!ed_address.text.isNullOrEmpty()) {
                    if (!ed_address2.text.isNullOrEmpty()) {
                        if (!ed_phone.text.isNullOrEmpty() && ed_phone.text.length == 11) {
                            if (!ed_sms.text.isNullOrEmpty()) {
                                showPageLoadingDialog()
                                MineApi.setUsdt(
                                    ed_address.text.toString(),
                                    current,
                                    ed_address2.text.toString(),
                                    ed_phone.text.toString(),
                                    ed_sms.text.toString()
                                ) {
                                    onSuccess {
                                        hidePageLoadingDialog()
                                        ToastUtils.showToast("添加成功")
                                        finish()
                                    }
                                    onFailed {
                                        hidePageLoadingDialog()
                                        ToastUtils.showToast(it.getMsg())
                                    }
                                }
                            } else ToastUtils.showToast("请输入手机验证码")
                        } else ToastUtils.showToast("请输入11位手机号码")
                    } else ToastUtils.showToast("请输入钱包地址")
                } else ToastUtils.showToast("请输入地址名称")
            }
        }

        get_sms.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!ed_phone.text.isNullOrEmpty() && ed_phone.text.length == 11) {
                    MineApi.userGetCode(ed_phone.text.toString(), "bind_usdt") {
                        onSuccess {
                            mPresenter.time(get_sms)
                            ToastUtils.showToast("验证码发送成功")
                        }
                        onFailed { ToastUtils.showToast(it.getMsg()) }
                    }
                } else ToastUtils.showToast("请输入11位手机号码")
            }
        }
    }
}