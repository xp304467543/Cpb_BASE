package com.mine
import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import com.customer.ApiRouter
import com.customer.component.dialog.*
import com.customer.data.*
import com.customer.data.login.LoginSuccess
import com.customer.data.mine.ChangeSkin
import com.customer.data.mine.UpDateUserPhoto
import com.customer.utils.RxPermissionHelper
import com.glide.GlideUtil
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.mine.children.*
import com.mine.children.movie.MineMovieActivity
import com.mine.children.noble.MineNobleActivity
import com.mine.children.skin.MineSkinAct
import com.mine.children.vip.MineVipGiftActivity
import com.mine.children.vip.MineVipPageActivity
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.Router
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_mine.*


@RouterAnno(host = "Mine", path = "main")
class MineFragment : BaseMvpFragment<MinePresenter>(), ITheme, IMode {

    var isInit = false

    //新消息
    var msg1 = ""
    var msg2 = ""
    var msg3 = ""
    var msg4 = ""

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = MinePresenter()

    override fun getLayoutResID() = R.layout.fragment_mine

    override fun isRegisterRxBus() = true


    @SuppressLint("SetTextI18n")
    override fun onSupportVisible() {
        if (UserInfoSp.getIsLogin()) {
            tvMineUserNickName.text = UserInfoSp.getUserNickName()
            GlideUtil.loadCircleImage(
                requireContext(),
                UserInfoSp.getUserPhoto(),
                imgMineUserAvatar,
                true
            )
            tvMineUserId.text = "ID: " + UserInfoSp.getUserUniqueId()
            setVisible(containerLogin)
            setGone(containerNoLogin)
//            mPresenter.getUserVip()
            mPresenter.getUserBalance()
            mPresenter.getUserDiamond()
            mPresenter.getNewMsg()
            mPresenter.getUserInfo()
//            if (!UserInfoSp.getIsSetPayPassWord()) mPresenter.getIsSetPayPassWord()
            setVisible(goPrise)
            visibilityView()
        } else {
            setGone(goPrise)
            imgMineUserAvatar.setBackgroundResource(R.mipmap.ic_base_user)
            setGone(containerLogin)
            setVisible(containerNoLogin)
            tvBalance.text = "0.00"
            tvDiamondBalance.text = "0.00"
            changeAttention()
        }
        isInit = true
    }

    override fun initContentView() {
        setSwipeBackEnable(false)
        setTheme(UserInfoSp.getThem())
        setMode(UserInfoSp.getAppMode())
    }

    override fun initData() {
        mPresenter.getCustomerUrl()
        if (!UserInfoSp.getMineGuide() && UserInfoSp.getAppMode() == AppMode.Normal) {
            showCurtain()
            UserInfoSp.putMineGuide(true)
        }
    }


    private fun showCurtain() {
//        Curtain(requireActivity()).withShape(tvMoneyChange, RoundShape(35f))
//            .setTopView(R.layout.guide_mine)
//            .withShape(tvChangeDiamond, RoundShape(35f))
//            .setCallBack(object : Curtain.CallBack {
//                override fun onDismiss(iGuide: IGuide?) {}
//                override fun onShow(iGuide: IGuide?) {
//                    iGuide?.findViewByIdInTopView<AppCompatImageView>(R.id.imgKnow)
//                        ?.setOnClickListener {
//                            iGuide.dismissGuide()
//                        }
//                }
//            })
//            .show()

    }

    override fun initEvent() {
        imgPersonal.setOnClickListener {

            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                Router.withApi(ApiRouter::class.java)
                    .toUserPage(UserInfoSp.getUserId().toString())
            }
        }

        tvLogin.setOnClickListener {
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java).toLogin()
        }
        imgMineUserAvatar.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(
                    Intent(
                        getPageActivity(),
                        MinePersonalAct::class.java
                    )
                )
            }
        }
        tvEditUser?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(
                    Intent(
                        getPageActivity(),
                        MinePersonalAct::class.java
                    )
                )
            }
        }
        tvMoneyChange.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(activity, MineMoneyCenterAct::class.java))
        }

        tvChangeDiamond.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(requireActivity())
                    return@setOnClickListener
                }
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                if (!UserInfoSp.getIsSetPayPassWord()) {
                    mPresenter.getIsSetPayPassWord(true)
                    return@setOnClickListener
                }
                if (tvBalance.text.toString() != "加载中") {
                    val dialog = DialogDiamond(requireContext(), tvBalance.text.toString())
                    dialog.show()
                } else ToastUtils.showToast("请等待加载完成")
            }
        }

        tvBalance.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(requireActivity())
                    return@setOnClickListener
                }
                if (UserInfoSp.getUserType() == "4") {
                    tvBalance.text = "加载中"
                    mPresenter.getUserBalance()
                    return@setOnClickListener
                }
                tvBalance.text = "加载中"
                mPresenter.getUserBalance()
                mPresenter.getUserDiamond()
            }
        }

        tvDiamondBalance.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(requireActivity())
                    return@setOnClickListener
                }
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                tvDiamondBalance.text = "加载中"
                mPresenter.getUserBalance()
                mPresenter.getUserDiamond()
            }
        }

        tvDepositMoney.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                Router.withApi(ApiRouter::class.java).toMineRecharge(0)
            }
        }

        tvAttention.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(getPageActivity(), MineAttentionAct::class.java))
            }
        }
        containerMessageCenter.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            containerMessageCenter.showNewMessage(false)
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                Router.withApi(ApiRouter::class.java).toMineMessage(msg1, msg2, msg3, msg4)
            }
        }
        containerMineCheck.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(getPageActivity(), MineBillAct::class.java))
            }

        }
        containerMainSkin.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                startActivity(Intent(getPageActivity(), MineSkinAct::class.java))
            }
        }

        containerAnchorGet.setOnClickListener {
            if (!FastClickUtil.isFastClick()) startActivity(
                Intent(
                    getPageActivity(),
                    MineAnchorRecruitAct::class.java
                )
            )
        }

        containerGameReport.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4") {
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java).toReportGame()
        }

        containerTuiReport.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4") {
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java).toReport(3)
        }

        containerFeedBack.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java).toFeedBack()
        }
        containerContactCustomer.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java)
                .toGlobalWeb(UserInfoSp.getCustomer() ?: urlCustomer)
        }
        containerGroup.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java).toContentGroup()
        }
        containerSetting.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) Router.withApi(ApiRouter::class.java).toSetting()
        }
        containerScanLogin.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                // 权限弹窗
                RxPermissionHelper.request(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
                ).let {
                    if (it.isDisposed) {
                        startActivity(Intent(getPageActivity(), MineScanAct::class.java))
                    } else {
                        ToastUtils.showToast("请打在设置中开启应用相机权限")
                    }
                }
            }
        }
        containerPhoneReport?.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                context?.let { it1 -> DialogPhoneContact(it1).show() }
            }
        }
        tvVip?.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(getPageActivity(), MineVipPageActivity::class.java))
            }
        }
        vipContainer?.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(getPageActivity(), MineNobleActivity::class.java))
            }
        }
        movieContainer.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(getPageActivity(), MineMovieActivity::class.java))
            }
        }
        containerVipGift.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                startActivity(Intent(getPageActivity(), MineVipGiftActivity::class.java))
            }
        }

        goPrise.setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                startActivity(Intent(getPageActivity(), MinePriseAct::class.java))
            }
        }
    }

    //主题
    override fun setTheme(theme: Theme) {
//        when (theme) {
//            Theme.Default -> {
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
////                imgMineBg.setImageResource(R.drawable.ic_them_default_top)
//                vipContainer.setBackRes(R.drawable.ic_mine_default_12)
//                containerMessageCenter.setBackRes(R.drawable.ic_mine_default_1)
//                containerMineCheck.setBackRes(R.drawable.ic_mine_default_2)
//                containerGameReport.setBackRes(R.drawable.ic_mine_default_5)
//                containerMainSkin.setBackRes(R.drawable.ic_mine_default_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_mine_default_4)
//                movieContainer.setBackRes(R.drawable.ic_mine_default_13)
//                containerScanLogin.setBackRes(R.drawable.ic_mine_default_11)
//                containerVipGift.setBackRes(R.drawable.ic_mine_default_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_mine_default_8)
//                containerFeedBack.setLeftIcon(R.drawable.ic_mine_default_7)
//                containerGroup.setLeftIcon(R.drawable.ic_mine_default_9)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_mine_default_6)
//                containerSetting.setLeftIcon(R.drawable.ic_mine_default_10)
//            }
//            Theme.NewYear -> {
////                tvLogin.setTextColor(ViewUtils.getColor(R.color.color_FF513E))
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
////                imgMineBg.setImageResource(R.drawable.ic_them_newyear_top)
//                vipContainer.setBackRes(R.drawable.ic_mine_newyear_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_mine_newyear_1)
//                containerMineCheck.setBackRes(R.drawable.ic_mine_newyear_2)
//                containerGameReport.setBackRes(R.drawable.ic_mine_newyear_5)
//                containerMainSkin.setBackRes(R.drawable.ic_mine_newyear_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_mine_newyear_4)
//                movieContainer.setBackRes(R.drawable.ic_mine_newyear_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_mine_newyear_11)
//                containerVipGift.setBackRes(R.drawable.ic_mine_newyear_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_mine_newyear_8)
//                containerFeedBack.setLeftIcon(R.drawable.ic_mine_newyear_7)
//                containerGroup.setLeftIcon(R.drawable.ic_mine_newyear_9)
//                containerSetting.setLeftIcon(R.drawable.ic_mine_newyear_10)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_mine_newyear_phone)
//            }
//            Theme.MidAutumn -> {
////                tvLogin.setTextColor(ViewUtils.getColor(R.color.colorGreenPrimary))
////                imgMineBg.setImageResource(R.drawable.ic_them_middle_top)
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
//                vipContainer.setBackRes(R.drawable.ic_mine_d5_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_mine_d5_1)
//                containerMineCheck.setBackRes(R.drawable.ic_mine_d5_2)
//                containerGameReport.setBackRes(R.drawable.ic_mine_d5_5)
//                containerMainSkin.setBackRes(R.drawable.ic_mine_d5_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_mine_d5_4)
//                movieContainer.setBackRes(R.drawable.ic_mine_d5_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_mine_d5_11)
//                containerVipGift.setBackRes(R.drawable.ic_mine_d5_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_mine_d5_8)
//                containerFeedBack.setLeftIcon(R.drawable.ic_mine_d5_7)
//                containerGroup.setLeftIcon(R.drawable.ic_mine_d5_9)
//                containerSetting.setLeftIcon(R.drawable.ic_mine_d5_10)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_mine_d5_phone)
//
//            }
//            Theme.LoverDay -> {
////                tvLogin.setTextColor(ViewUtils.getColor(R.color.purple))
////                imgMineBg.setImageResource(R.drawable.ic_them_love_top)
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
//                vipContainer.setBackRes(R.drawable.ic_mine_love_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_mine_love_1)
//                containerMineCheck.setBackRes(R.drawable.ic_mine_love_2)
//                containerGameReport.setBackRes(R.drawable.ic_mine_love_8)
//                containerMainSkin.setBackRes(R.drawable.ic_mine_love_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_mine_love_4)
//                movieContainer.setBackRes(R.drawable.ic_mine_love_my_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_mine_love_11)
//                containerVipGift.setBackRes(R.drawable.ic_mine_love_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_mine_love_6)
//                containerFeedBack.setLeftIcon(R.drawable.ic_mine_love_5)
//                containerGroup.setLeftIcon(R.drawable.ic_mine_love_7)
//                containerSetting.setLeftIcon(R.drawable.ic_mine_love_10)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_mine_love_phone)
//            }
//            Theme.NationDay -> {
////                tvLogin.setTextColor(ViewUtils.getColor(R.color.color_EF7E12))
////                imgMineBg.setImageResource(R.drawable.ic_them_gq_top)
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
//                vipContainer.setBackRes(R.drawable.ic_them_gq_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_them_gq_1)
//                containerMineCheck.setBackRes(R.drawable.ic_them_gq_2)
//                containerGameReport.setBackRes(R.drawable.ic_them_gq_5)
//                containerMainSkin.setBackRes(R.drawable.ic_them_gq_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_them_gq_4)
//                movieContainer.setBackRes(R.drawable.ic_them_gq_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_them_gq_13)
//                containerVipGift.setBackRes(R.drawable.ic_them_gq_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_them_gq_8)
//                containerFeedBack.setLeftIcon(R.drawable.ic_them_gq_7)
//                containerGroup.setLeftIcon(R.drawable.ic_them_gq_9)
//                containerSetting.setLeftIcon(R.drawable.ic_them_gq_10)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_them_gq_phone)
//            }
//            Theme.ChristmasDay -> {
////                tvLogin.setTextColor(ViewUtils.getColor(R.color.color_SD))
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
//                vipContainer.setBackRes(R.drawable.ic_them_sd_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_them_sd_1)
//                containerMineCheck.setBackRes(R.drawable.ic_them_sd_2)
//                containerGameReport.setBackRes(R.drawable.ic_them_sd_6)
//                containerMainSkin.setBackRes(R.drawable.ic_them_sd_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_them_sd_4)
//                movieContainer.setBackRes(R.drawable.ic_them_sd_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_them_sd_5)
//                containerVipGift.setBackRes(R.drawable.ic_them_sd_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_them_sd_9)
//                containerFeedBack.setLeftIcon(R.drawable.ic_them_sd_8)
//                containerGroup.setLeftIcon(R.drawable.ic_them_sd_10)
//                containerSetting.setLeftIcon(R.drawable.ic_them_sd_13)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_them_sd_phone)
//            }
//            Theme.OxYear -> {
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
//                vipContainer.setBackRes(R.drawable.ic_them_nn_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_them_nn_1)
//                containerMineCheck.setBackRes(R.drawable.ic_them_nn_2)
//                containerGameReport.setBackRes(R.drawable.ic_them_nn_5)
//                containerMainSkin.setBackRes(R.drawable.ic_them_nn_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_them_nn_4)
//                movieContainer.setBackRes(R.drawable.ic_them_nn_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_them_nn_13)
//                containerVipGift.setBackRes(R.drawable.ic_them_nn_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_them_nn_8)
//                containerFeedBack.setLeftIcon(R.drawable.ic_them_nn_7)
//                containerGroup.setLeftIcon(R.drawable.ic_them_nn_9)
//                containerSetting.setLeftIcon(R.drawable.ic_them_nn_10)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_them_nn_phone)
//
//            }
//            Theme.Uefa -> {
//                tvLogin.setTextColor(ViewUtils.getColor(R.color.white))
//                vipContainer.setBackRes(R.drawable.ic_them_ufea_gz)
//                containerMessageCenter.setBackRes(R.drawable.ic_them_ufea_1)
//                containerMineCheck.setBackRes(R.drawable.ic_them_ufea_2)
//                containerGameReport.setBackRes(R.drawable.ic_them_ufea_5)
//                containerMainSkin.setBackRes(R.drawable.ic_them_ufea_3)
//                containerAnchorGet.setBackRes(R.drawable.ic_them_ufea_4)
//                movieContainer.setBackRes(R.drawable.ic_them_ufea_movie)
//                containerScanLogin.setBackRes(R.drawable.ic_them_ufea_13)
//                containerVipGift.setBackRes(R.drawable.ic_them_ufea_15)
//                containerContactCustomer.setLeftIcon(R.drawable.ic_them_ufea_8)
//                containerFeedBack.setLeftIcon(R.drawable.ic_them_ufea_7)
//                containerGroup.setLeftIcon(R.drawable.ic_them_ufea_9)
//                containerSetting.setLeftIcon(R.drawable.ic_them_ufea_10)
//                containerPhoneReport.setLeftIcon(R.drawable.ic_them_ufea_phone)
//
//            }
//        }
    }

    //换肤
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: ChangeSkin) {
//        when (eventBean.id) {
//            1 -> setTheme(Theme.Default)
//            2 -> setTheme(Theme.NewYear)
//            3 -> setTheme(Theme.MidAutumn)
//            4 -> setTheme(Theme.LoverDay)
//            5 -> setTheme(Theme.NationDay)
//            6 -> setTheme(Theme.ChristmasDay)
//            7 -> setTheme(Theme.OxYear)
//            8 -> setTheme(Theme.Uefa)
//        }

    }


    //退出登录
    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginOut(eventBean: LoginOut) {
        if (isActive()) {
            setGone(containerLogin)
            setGone(containerSetting)
            setVisible(containerNoLogin)
            tvBalance?.text = "0.00"
            tvDiamondBalance?.text = "0.00"
            changeAttention()
            setGone(goPrise)
        }
    }

    //更新用户头像
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDataUserAvatar(eventBean: UpDateUserPhoto) {
        context?.let { GlideUtil.loadCircleImage(it, eventBean.img, imgMineUserAvatar, true) }
        UserInfoSp.putUserPhoto(eventBean.img)
    }

    //更新钻石 顺便更新余额
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun upDataMineUserDiamond(eventBean: MineUserDiamond) {
        if (isSupportVisible) {
            if (!eventBean.isRest) {
                tvDiamondBalance?.text = if (eventBean.diamond == "0") "0.00" else eventBean.diamond
                mPresenter.getUserBalance()
            } else mPresenter.getUserBalance()
        }
    }

    //扫码登录后退出
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun scanLoginOut(eventBean: MineUserScanLoginOut) {
        val dialog = DialogGlobalTips(requireContext(), "登录提醒", "确定", "", "您的账号已在其他设备登录\n" + "如非本人请联系客服")
        dialog.setConfirmClickListener {
            dialog.dismiss()
        }
        dialog.setOnDismissListener {
            GlobalDialog.spClear()
            dialog.setCanceledOnTouchOutside(false)
            setGone(containerLogin)
            setGone(containerSetting)
            setVisible(containerNoLogin)
            tvBalance?.text = "0.00"
            tvDiamondBalance?.text = "0.00"
            Router.withApi(ApiRouter::class.java).toLogin()
            changeAttention()
        }
        dialog.show()
        GlobalDialog.clearAllLog()
        setGone(goPrise)
    }

    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: AppChangeMode) {
        if (isAdded) {
            setMode(eventBean.mode)
        }
    }

    override fun setMode(mode: AppMode) {
        when (mode) {
            AppMode.Normal -> {
                tvAttention?.visibility = View.VISIBLE
                containerMainSkin?.visibility = View.VISIBLE
                containerAnchorGet?.visibility = View.VISIBLE
                lContainer2?.visibility = View.VISIBLE
                imgMineLevel?.visibility = View.VISIBLE
                containerAttention?.visibility = View.VISIBLE
                containerOtherThing?.visibility = View.VISIBLE
                imgPersonal?.visibility = View.VISIBLE
                setTheme(UserInfoSp.getThem())
                if (UserInfoSp.getIsLogin()){
                    vipContainer?.visibility = View.VISIBLE
                    movieContainer?.visibility = View.VISIBLE
                }
            }
            AppMode.Pure -> {
                tvAttention?.visibility = View.GONE
                containerMainSkin?.visibility = View.GONE
                containerAnchorGet?.visibility = View.GONE
                lContainer2?.visibility = View.GONE
                vipContainer?.visibility = View.GONE
                movieContainer?.visibility = View.GONE
                imgMineLevel?.visibility = View.GONE
                containerAttention?.visibility = View.GONE
                containerOtherThing?.visibility = View.GONE
                imgPersonal?.visibility = View.GONE
//                setTheme(Theme.Default)
            }
        }
    }


    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMine(clickMine: HomeJumpToMine) {
        initMine()
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun login(eventBean: LoginSuccess) {
        if (isAdded) {
            tvMineUserNickName?.text = UserInfoSp.getUserNickName()
            GlideUtil.loadCircleImage(
                requireContext(),
                UserInfoSp.getUserPhoto(),
                imgMineUserAvatar,
                true
            )
            tvMineUserId?.text = "ID: " + UserInfoSp.getUserUniqueId()
            setVisible(containerLogin)
            setGone(containerNoLogin)
//            mPresenter.getUserVip()
            mPresenter.getUserBalance()
            mPresenter.getUserDiamond()
            mPresenter.getNewMsg()
            mPresenter.getUserInfo()
            visibilityView()
            setVisible(goPrise)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initMine() {
        if (!isInit) {
            if (UserInfoSp.getIsLogin()) {
                tvMineUserNickName.text = UserInfoSp.getUserNickName()
                GlideUtil.loadCircleImage(
                    requireContext(),
                    UserInfoSp.getUserPhoto(),
                    imgMineUserAvatar,
                    true
                )
                tvMineUserId.text = "ID: " + UserInfoSp.getUserUniqueId()
                setVisible(containerLogin)
                setGone(containerNoLogin)
//                mPresenter.getUserVip()
                mPresenter.getUserBalance()
                mPresenter.getUserDiamond()
                mPresenter.getNewMsg()
                mPresenter.getUserInfo()
                setVisible(containerSetting)
            } else {
                imgMineUserAvatar.setBackgroundResource(R.mipmap.ic_base_user)
                setGone(containerLogin)
                setGone(containerSetting)
                setVisible(containerNoLogin)
                tvBalance.text = "0.00"
                tvDiamondBalance.text = "0.00"
                changeAttention()
            }
        }
    }

    private fun changeAttention() {
        spText1?.setCenterTopString("0")
        spText2?.setCenterTopString("0")
        spText3?.setCenterTopString("0")
        setGone(containerScanLogin)
        setGone(containerPhoneReport)
        setGone(containerSetting)
        setGone(vipContainer)
        setGone(movieContainer)
        setGone(containerVipGift)
        containerMessageCenter.showNewMessage(false)
    }

    private fun visibilityView() {
        setVisible(containerScanLogin)
        setVisible(containerPhoneReport)
        setVisible(containerSetting)
        if (UserInfoSp.getAppMode() == AppMode.Normal){
            setVisible(vipContainer)
            setVisible(movieContainer)
            setVisible(containerVipGift)
        }
    }
}
