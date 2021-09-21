package com.fh.view

import android.Manifest
import android.annotation.SuppressLint
import com.customer.ApiRouter
import com.customer.AppConstant
import com.customer.component.dialog.*
import com.customer.data.*
import com.customer.data.home.HomeApi
import com.customer.data.login.LoginSuccess
import com.customer.data.login.RegisterSuccess
import com.customer.data.mine.ChangeSkin
import com.customer.utils.RxPermissionHelper
import com.customer.utils.TimeSp
import com.fh.R
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.fragment.BaseContentFragment
import com.lib.basiclib.base.fragment.BaseFragment
import com.lib.basiclib.base.fragment.PlaceholderFragment
import com.lib.basiclib.utils.SpUtils
import com.lib.basiclib.utils.TimeUtils
import com.services.*
import com.tbruyelle.rxpermissions2.RxPermissions
import com.xiaojinzi.component.impl.Router
import com.xiaojinzi.component.impl.service.ServiceManager
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @ Author  QinTian
 * @ Date  2020/9/4
 * @ Describe
 */
class MainFragment : BaseContentFragment(), ITheme, IMode {


    private var bottomWebSelect: BottomWebSelect? = null

    val mFragments = arrayListOf<BaseFragment>()


    override fun getContentResID() = R.layout.fragment_main


    override fun isSwipeBackEnable() = false

    override fun isRegisterRxBus() = true

    @SuppressLint("CheckResult")
    override fun initContentView() {
        setTheme(UserInfoSp.getThem())
        RxPermissions(this)
            .request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.SYSTEM_ALERT_WINDOW
            )
            .subscribe { granted ->
                if (granted) {
                    // 已获得所有权限
                } else {
                    // 部分或所有权限被拒绝
                }
            }
        initFragments()
        setMode(UserInfoSp.getAppMode())
    }


    private fun initFragments() {
        val homeFragment = ServiceManager.get(HomeService::class.java)?.getHomeFragment()
            ?: PlaceholderFragment.newInstance()
        val liveFragment = ServiceManager.get(HomeService::class.java)?.getLiveFragment()
            ?: PlaceholderFragment.newInstance()
        val customerFragment =
            ServiceManager.get(DiscountService::class.java)?.getCustomerFragment()
                ?: PlaceholderFragment.newInstance()
        val discountFragment =
            ServiceManager.get(DiscountService::class.java)?.getDiscountFragment()
                ?: PlaceholderFragment.newInstance()
        val mineFragment = ServiceManager.get(MineService::class.java)?.getMineFragment()
            ?: PlaceholderFragment.newInstance()
        //纯净版
//        val chunGame = ServiceManager.get(BetService::class.java)?.getGameFragment()
//            ?: PlaceholderFragment.newInstance()
//        val chunOpen = ServiceManager.get(LotteryService::class.java)?.getLotteryFragment()
//            ?: PlaceholderFragment.newInstance()
        mFragments.add(homeFragment)
        mFragments.add(liveFragment)
        mFragments.add(discountFragment)
        mFragments.add(customerFragment)
        mFragments.add(mineFragment)
//        //纯净版
//        mFragments.add(chunGame)
//        mFragments.add(chunOpen)
        loadMultipleRootFragment(
            R.id.mainContainer,
            0,
            mFragments[0],
            mFragments[1],
            mFragments[2],
            mFragments[3],
            mFragments[4]
//            mFragments[5],
//            mFragments[6]
        )
    }

    override fun initData() {
        checkDialog()
        getUpDate()
        getNotice()

    }


    private fun redRain() {
//        rainView.setIsIntercept(true)
//        //屏幕中最多显示item的数量
//        rainView?.setAmount(50);
//        //设置下落的次数。在保持密度不变的情况下，设置下落数量。例如：数量 = 50，下落次数 = 3，总共数量150。
//        // rainView.setTimes(2);
//        //设置无效循环
//        rainView?.setTimes(RainViewGroup.INFINITE)
//        rainView?.start()
    }

    override fun initEvent() {
        tabHome.setOnClickListener {
            showHideFragment(mFragments[0])
        }
        tabLive.setOnClickListener {
            showHideFragment(mFragments[1])
        }
        tabLotteryOpen.setOnClickListener {
            showHideFragment(mFragments[2])
        }
        tabDisCount.setOnClickListener {
            showHideFragment(mFragments[3])
        }
        tabMine.setOnClickListener {
            showHideFragment(mFragments[4])
        }
        tabChunGame.setOnClickListener {
            showHideFragment(mFragments[5])
        }
        tabChunOpen.setOnClickListener {
            showHideFragment(mFragments[6])
        }
//        rainView.setOnClickListener {
//            if (!FastClickUtil.isFastClick()) {
//                getRedRainAmount()
//            }
//        }
    }


    private fun getRedRainAmount() {
//        HomeApi.getRedRain {
//            onSuccess {
//                val dialog = context?.let { it1 -> DialogRedRain(it1,it.amount.toString()) }
//                dialog?.setOnDismissListener {
//                    DialogRegisterSuccess(requireActivity()).show()
//                }
//                dialog?.show()
////                rainView?.stop()
////                rainView?.setIsIntercept(false)
//            }
//            onFailed {
////                ToastUtils.showToast(it.getMsg())
////                rainView?.stop()
////                rainView?.setIsIntercept(false)
//            }
//        }
    }

    /***
     * 回到主页面弹出一些列的窗口
     */
    private fun checkDialog() {
        // 权限弹窗
        RxPermissionHelper.request(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        )
    }

    //系统公告
    private fun getNotice() {
        if (AppConstant.isMain) {
            HomeApi.getSystemNotice {
                onSuccess {
                    val dialog = DialogSystemNotice(requireActivity())
                    dialog.setContent(it)
                    dialog.setOnDismissListener {
                        showRedTips()
                    }
                    dialog.show()
                }
            }
        } else {
            HomeApi.getSystemNoticeDl {
                onSuccess {
                    if (!it.isNullOrEmpty()) {
                        val dialog = DialogSystemNotice(requireActivity())
                        dialog.setContent(it)
                        dialog.setOnDismissListener {
                            showRedTips()
                        }
                        dialog.show()
                    }
                }
            }
        }
    }

    private fun showRedTips() {
       if (UserInfoSp.getUserId() != 0){
           if (!TimeSp.getIsShow("TimeIsShow"+UserInfoSp.getUserId())){
                   getPageActivity()?.let { DialogMatch(it).show() }
                   TimeSp.putIsShow("TimeIsShow"+UserInfoSp.getUserId(),true)
                   TimeSp.putTimeBefore("Time"+UserInfoSp.getUserId(),System.currentTimeMillis())
           }else{
               if (!TimeUtils.isToday(TimeSp.getTimeBefore("Time"+UserInfoSp.getUserId()))){
                   getPageActivity()?.let { DialogMatch(it).show() }
                   TimeSp.putTimeBefore("Time"+UserInfoSp.getUserId(),System.currentTimeMillis())
               }
           }
       }
    }


    //更新
    private fun getUpDate() {
        HomeApi.getVersion {
            onSuccess {
                if (it.version_data != null) {
                    val dialog = DialogVersion(requireActivity())
                    dialog.setContent(it.version_data?.upgradetext!!)
                    if (it.version_data?.enforce == 1) {
                        dialog.setCanceledOnTouchOutside(false)
                        dialog.setCancelable(false)
                    } else {
                        dialog.setCanceledOnTouchOutside(true)
                        dialog.setCancelable(true)
                    }
                    dialog.setJum(it.version_data?.downloadurl!!)
                    dialog.show()
                }
            }
        }
    }


    //主题
    @SuppressLint("ResourceType")
    override fun setTheme(theme: Theme) {
        when (theme) {
//            Theme.Default -> {
//            }
//            Theme.NewYear -> {
//            }
//            Theme.MidAutumn -> {
//            }
//            Theme.LoverDay -> {
//            }
//            Theme.NationDay -> {
//            }
//            Theme.ChristmasDay -> {
//            }
//            Theme.OxYear -> {
//            }
//            Theme.Uefa -> {
//            }
        }
    }

    //换肤
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: ChangeSkin) {
        when (eventBean.id) {
//            1 -> setTheme(Theme.Default)
//            2 -> setTheme(Theme.NewYear)
//            3 -> setTheme(Theme.MidAutumn)
//            4 -> setTheme(Theme.LoverDay)
//            5 -> setTheme(Theme.NationDay)
//            6 -> setTheme(Theme.ChristmasDay)
//            7 -> setTheme(Theme.OxYear)
//            8 -> setTheme(Theme.Uefa)
        }

    }

    /**
     * 接收Home头像点击事件
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMine(clickMine: HomeJumpToMine) {
        if (clickMine.jump) {
            if (tabMine != null) tabMine?.isChecked = true
            showHideFragment(mFragments[4])
        }
    }

    /**
     * live去充值
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun homeJumpToMineCloseLive(clickMine: HomeJumpTo) {
        if (tabMine != null) tabMine?.isChecked = true
        showHideFragment(mFragments[4])
        if (clickMine.isOpenAct) Router.withApi(ApiRouter::class.java).toMineRecharge(0)
    }


    //注册成功后红包雨
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginInfoResponse(eventBean: RegisterSuccess) {
        if (eventBean.isShowDialog) {
//            redRain()
        }
    }

    //登录成功
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun login(eventBean: LoginSuccess) {
        if (isAdded) {
            showRedTips()
        }
    }


    /**
     * live去Bet
     */
//    @Subscribe(thread = EventThread.MAIN_THREAD)
//    fun toBetView(eventBean: ToBetView) {
//        if (tabLive != null) tabLive?.isChecked = true
//        showHideFragment(mFragments[1])
//    }


    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: AppChangeMode) {
        if (isAdded) {
            setMode(eventBean.mode)
        }
    }

    /**
     * live去Bet
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun toBetView(eventBean: ToBetView) {
        showHideFragment(mFragments[0])
        if (tabHome != null) tabHome?.isChecked = true
    }


    override fun setMode(mode: AppMode) {
        when (mode) {
            AppMode.Normal -> {
//                showHideFragment(mFragments[0])
//                setVisible(tabHome)
//                setVisible(tabLive)
//                setVisible(tabDisCount)
//                setGone(tabChunOpen)
//                setGone(tabChunGame)
//                if (tabHome != null) tabHome?.isChecked = true
//                setTheme(UserInfoSp.getThem())
            }
            AppMode.Pure -> {
//                showHideFragment(mFragments[0])
//                if (tabHome != null) tabHome?.isChecked = true
//                setGone(tabHome)
//                setGone(tabLive)
//                setGone(tabDisCount)
//                setVisible(tabChunOpen)
//                setVisible(tabChunGame)
//                if (tabChunGame != null) tabChunGame?.isChecked = true
//                setTheme(Theme.Default)
            }
        }
    }


}