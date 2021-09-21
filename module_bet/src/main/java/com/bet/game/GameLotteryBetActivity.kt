package com.bet.game
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bet.R
import com.customer.component.dialog.lottery.Fc3DWindow
import com.customer.component.dialog.lottery.FiveCodeWindow
import com.customer.component.dialog.lottery.TenCodeWindow
import com.customer.component.dialog.lottery.XgcCodeWindow
import com.customer.data.*
import com.customer.data.lottery.LotteryCodeHistoryResponse
import com.customer.data.lottery.LotteryTypeResponse
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.base.xui.adapter.FragmentAdapter
import com.lib.basiclib.base.xui.widget.picker.widget.OptionsPickerView
import com.lib.basiclib.base.xui.widget.picker.widget.builder.OptionsPickerBuilder
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ViewUtils
import com.lib.basiclib.widget.tab.ViewPagerHelper
import com.lib.basiclib.widget.tab.buildins.commonnavigator.CommonNavigator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.IPagerIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.abs.IPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.indicators.LinePagerIndicator
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.ClipPagerTitleView
import com.lib.basiclib.widget.tab.buildins.commonnavigator.titles.badge.BadgePagerTitleView
import com.services.HomeService
import com.xiaojinzi.component.anno.RouterAnno
import com.xiaojinzi.component.impl.service.ServiceManager
import cuntomer.them.AppMode
import kotlinx.android.synthetic.main.act_game_lottery_bet.*

/**
 * @ Author  QinTian
 * @ Date  2020/9/14
 * @ Describe
 */
@RouterAnno(host = "BetMain", path = "gameLotteryBet")
class GameLotteryBetActivity : BaseMvpActivity<GameLotteryBetActivityPresenter>() {

    var lotteryId = "-1"

    var index = 0

    var isPlay = false

    private var gameOptions: OptionsPickerView<String>? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() =
        GameLotteryBetActivityPresenter()

    override fun isOverride() = true

    override fun isRegisterRxBus() = true

    override fun isSwipeBackEnable() = true

    override val layoutResID = R.layout.act_game_lottery_bet

    override fun initContentView() {
        StatusBarUtils.setStatusBarHeight(gameBetStateView)
        tvGameLotteryName.text = intent.getStringExtra("gameLotteryName")
        lotteryId = intent.getStringExtra("gameLotteryId") ?: "-1"
        initGameViewPager()
        if (UserInfoSp.getIsPlaySound()) {
            imgPlaySound?.setImageResource(R.mipmap.old_lb_hong)
        } else imgPlaySound?.setImageResource(R.mipmap.old_lb_hui)
        imgPlaySound?.setOnClickListener {
            if (UserInfoSp.getIsPlaySound()) {
                UserInfoSp.putIsPlaySound(false)
                imgPlaySound?.setImageResource(R.mipmap.old_lb_hui)
            } else {
                UserInfoSp.putIsPlaySound(true)
                imgPlaySound?.setImageResource(R.mipmap.old_lb_hong)
            }
        }

    }

    override fun initData() {
        mPresenter.getLottery()
        mPresenter.getLotteryOpenCode(lotteryId)
    }


    var fc3DWindow: Fc3DWindow? = null
    var xgcCodeWindow: XgcCodeWindow? = null
    var tenCodeWindow: TenCodeWindow? = null
    var fiveCodeWindow: FiveCodeWindow? = null
    var isShowing = false
    override fun initEvent() {
        imgGameBack.setOnClickListener { finish() }
        tvGameLotteryName.setOnClickListener {
            gameOptions?.show()
        }
        imgGameTools.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                val intent = Intent(this, GameLotteryBetToolsActivity::class.java)
                intent.putExtra("gameLotteryToolsId", lotteryId)
                intent.putExtra("isRuler", false)
                startActivity(intent)
            }
        }
        dropLotteryWindow?.setOnClickListener {
            openInfoLottery()
        }
        linLotteryOpenCode?.setOnClickListener {
            openInfoLottery()
        }
    }


    private fun openInfoLottery() {
        when (lotteryId) {
            "5", "14" -> {
                if (fc3DWindow == null) fc3DWindow = Fc3DWindow(this)
                fc3DWindow?.setId(lotteryId)
                fc3DWindow?.setOnDismissListener { dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_bt) }
                isShowing = if (isShowing) {
                    fc3DWindow?.dismiss()
                    false
                } else {
                    fc3DWindow?.showAsDropDown(rvCodeLayout)
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_top)
                    true
                }
            }
            "8" -> {
                if (xgcCodeWindow == null) xgcCodeWindow = XgcCodeWindow(this)
                xgcCodeWindow?.getDataLottery()
                xgcCodeWindow?.setOnDismissListener {
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_bt)
                }
                isShowing = if (isShowing) {
                    xgcCodeWindow?.dismiss()
                    false
                } else {
                    xgcCodeWindow?.showAsDropDown(rvCodeLayout)
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_top)
                    true
                }

            }
            "9", "11", "26", "27", "29","32" -> {
                if (tenCodeWindow == null) tenCodeWindow = TenCodeWindow(this)
                tenCodeWindow?.setId(lotteryId)
                tenCodeWindow?.setOnDismissListener {
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_bt)
                }
                isShowing = if (isShowing) {
                    tenCodeWindow?.dismiss()
                    false
                } else {
                    tenCodeWindow?.showAsDropDown(rvCodeLayout)
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_top)
                    true
                }
            }
            "10", "28", "30", "31" -> {
                if (fiveCodeWindow == null) fiveCodeWindow = FiveCodeWindow(this)
                fiveCodeWindow?.setId(lotteryId)
                fiveCodeWindow?.setOnDismissListener {
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_bt)
                }
                isShowing = if (isShowing) {
                    fiveCodeWindow?.dismiss()
                    false
                } else {
                    fiveCodeWindow?.showAsDropDown(rvCodeLayout)
                    dropLotteryWindow.setImageResource(R.mipmap.ic_arrow_top)
                    true
                }
            }
        }

    }

    //最新开奖数据
    fun postNewCode(lotteryCodeHistoryResponse: LotteryCodeHistoryResponse){
        if (fc3DWindow!=null){
            fc3DWindow?.addNewOne(lotteryCodeHistoryResponse)
        }
        if (xgcCodeWindow!=null){
            xgcCodeWindow?.addNewOne(lotteryCodeHistoryResponse)
        }
        if (tenCodeWindow!=null){
            tenCodeWindow?.addNewOne(lotteryCodeHistoryResponse)
        }
        if (fiveCodeWindow!=null){
            fiveCodeWindow?.addNewOne(lotteryCodeHistoryResponse)
        }
    }


    var betFragment: GameLotteryBetFragment1? = null
    var orderFragment: Fragment? = null
    var hotFragment: GameLotteryBetFragment3? = null
    private fun initGameViewPager() {
        val dataList = when (UserInfoSp.getAppMode()) {
            AppMode.Normal -> arrayListOf("投注区", "我的注单", "热门直播")
            else -> arrayListOf("投注区", "我的注单")
        }

        if (betFragment == null) betFragment = GameLotteryBetFragment1.newInstance(lotteryId)
        if (orderFragment == null) orderFragment =
            ServiceManager.get(HomeService::class.java)?.getRecordFragment()
        if (hotFragment == null) hotFragment = GameLotteryBetFragment3.newInstance(lotteryId)
        val fragments = when (UserInfoSp.getAppMode()) {
            AppMode.Normal -> listOf(betFragment, orderFragment, hotFragment)
            else -> listOf(betFragment, orderFragment)
        }
        gameBetViewPager.offscreenPageLimit = fragments.size
        gameBetViewPager.adapter = FragmentAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragments
        )
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount() = dataList.size

            @SuppressLint("SetTextI18n")
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val badgePagerTitleView = BadgePagerTitleView(context)
                val clipPagerTitleView = ClipPagerTitleView(context)
                clipPagerTitleView.text = dataList[index]
                clipPagerTitleView.textSize = 35F
                clipPagerTitleView.textColor = ViewUtils.getColor(R.color.alivc_blue_6)
                clipPagerTitleView.clipColor = ViewUtils.getColor(R.color.alivc_blue_1)
                clipPagerTitleView.setOnClickListener { gameBetViewPager.currentItem = index }
                badgePagerTitleView.innerPagerTitleView = clipPagerTitleView
                return badgePagerTitleView
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                val navigatorHeight =
                    this@GameLotteryBetActivity.resources.getDimension(R.dimen.dp_40)
                val borderWidth: Float = ViewUtils.dp2px(1F)
                val lineHeight = navigatorHeight - 2 * borderWidth
                indicator.lineHeight = lineHeight
                indicator.yOffset = borderWidth
                indicator.setColors(Color.WHITE)
                return indicator
            }
        }
        gameIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(gameIndicator, gameBetViewPager)
    }


    //底部弹框
    fun showPickerView(list: List<LotteryTypeResponse>) {
        val final = arrayListOf<String>()
        for (res in list) {
            final.add(res.cname ?: "未知")
        }
        index = final.indexOf(intent.getStringExtra("gameLotteryName") ?: "")
        gameOptions = OptionsPickerBuilder(this) { _, options1, _, _ ->
            gameOptions?.dismiss()
            isPlay = false
            tvGameLotteryName.text = list[options1].cname
            lotteryId = list[options1].lottery_id ?: "-1"
            mPresenter.getLotteryOpenCode(lotteryId)
            index = options1
            RxBus.get().post(ChangeLottery(lotteryId))
            isShowing = false
            false
        }
            .setTitleText("")
            .setSelectOptions(index)
            .setContentTextSize(18)
            .setOutSideCancelable(false)
            .build()
        gameOptions?.setPicker(final)
    }

    //底部弹框
//    private var opt1SelectedPosition = 0
//    private var lotterySelectDialog:BottomLotterySelectDialog?=null
//    fun showPickerView(list: List<LotteryTypeResponse>) {
//        val final = arrayListOf<String>()
//        for (res in list) {
//            final.add(res.cname ?: "未知")
//        }
//       if (lotterySelectDialog==null)  lotterySelectDialog = BottomLotterySelectDialog(this, final)
//        lotterySelectDialog?.setCanceledOnTouchOutside(false)
//        lotterySelectDialog?.tvLotteryWheelSure?.setOnClickListener {
//            isPlay = false
//            tvGameLotteryName.text = lotterySelectDialog?.lotteryPickerView?.opt1SelectedData as String
//            opt1SelectedPosition = lotterySelectDialog?.lotteryPickerView?.opt1SelectedPosition?:0
//            lotteryId = list[opt1SelectedPosition].lottery_id ?: "-1"
//            mPresenter.getLotteryOpenCode(lotteryId)
//            index = opt1SelectedPosition
//            RxBus.get().post(ChangeLottery(lotteryId))
//            lotterySelectDialog?.dismiss()
//        }
//        lotterySelectDialog?.lotteryPickerView?.opt1SelectedPosition = opt1SelectedPosition
//
//    }

    /**
     * 跳转mine
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onClickMine(clickMine: HomeJumpToMine) {
        finish()
    }

    /**
     * live去充值
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun homeJumpToMineCloseLive(clickMine: HomeJumpToMineCloseLive) {
        finish()
    }

    /**
     * live去Bet
     */
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun toBetView(eventBean: ToBetView) {
        finish()
    }

    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeSkin(eventBean: AppChangeMode) {
        finish()
    }

}