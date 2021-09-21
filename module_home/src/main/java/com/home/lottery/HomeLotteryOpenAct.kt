package com.home.lottery
import androidx.recyclerview.widget.LinearLayoutManager
import com.customer.ApiRouter
import com.customer.data.UserInfoSp
import com.customer.data.lottery.LotteryTypeResponse
import com.customer.data.mine.LotteryToLiveRoom
import com.home.R
import com.hwangjr.rxbus.RxBus
import com.lib.basiclib.base.mvp.BaseMvpActivity
import com.lib.basiclib.utils.FastClickUtil
import com.services.LotteryService
import com.xiaojinzi.component.impl.Router
import com.xiaojinzi.component.impl.service.ServiceManager
import cuntomer.them.AppMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.act_lottery_open.*

/**
 *
 * @ Author  QinTian
 * @ Date  6/16/21
 * @ Describe
 */
class HomeLotteryOpenAct : BaseMvpActivity<HomeLotteryOpenPresenter>(), ITheme {

    var currentLotteryId = "1"

    var isInitBottom = false

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeLotteryOpenPresenter()

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false

    override fun getPageTitle() = "开奖中心"

    override fun getContentResID() = R.layout.act_lottery_open


    override fun initContentView() {
        setRightText("玩法规则")
        smartLottery.setEnableRefresh(false)//是否启用下拉刷新功能
        smartLottery.setEnableLoadMore(false)//是否启用上拉加载功能
        smartLottery.setEnableOverScrollBounce(true)//是否启用越界回弹
        smartLottery.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        setTheme(UserInfoSp.getThem())
        //加载底部
        ServiceManager.get(LotteryService::class.java)?.getLotteryBaseFragment()?.let {
            loadRootFragment(R.id.childContainer,
                it
            )
        }
        if (UserInfoSp.getAppMode() == AppMode.Pure) {
//            setGone(imgVideo)
//            setTheme(Theme.Default)
        } else {
//            setVisible(imgVideo)
            setTheme(UserInfoSp.getThem())
        }
    }


    override fun initData() {
        mPresenter.getLotteryType()
    }


    override fun initEvent() {
//        imgVideo.setOnClickListener {
//            if (!FastClickUtil.isFastClick()) RxBus.get().post(LotteryToLiveRoom(currentLotteryId))
//        }
        getRightText().setOnClickListener {
            if (!FastClickUtil.isFastClick()){
                Router.withApi(ApiRouter::class.java).toLotteryBetTools("5",true)
            }
        }
    }
    // ===== 彩种类型 =====
    var lotteryTypeAdapter: LotteryTypeAdapter? = null
    fun initLotteryType(it: List<LotteryTypeResponse>) {
        lotteryTypeAdapter = LotteryTypeAdapter()
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvLotteryType.layoutManager = layoutManager
        rvLotteryType.adapter = lotteryTypeAdapter
        lotteryTypeAdapter?.refresh(it)
        currentLotteryId = it[0].lottery_id ?: "1"
        mPresenter.getLotteryOpenCode(currentLotteryId)
        lotteryTypeAdapter?.setOnItemClickListener { itemView, item, position ->
            currentLotteryId = item.lottery_id ?: "1"
//            topText.text = item.cname
            lotteryTypeAdapter?.changeBackground(position)
            smoothToCenter(layoutManager, position)
            isInitBottom = false
            mPresenter.getLotteryOpenCode(item.lottery_id ?: "1")
        }
    }

    private fun smoothToCenter(layoutManager: LinearLayoutManager, position: Int) {
        val firstPosition: Int = layoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = layoutManager.findLastVisibleItemPosition()
        val left: Int = rvLotteryType.getChildAt(position - firstPosition).left
        val right: Int = rvLotteryType.getChildAt(lastPosition - position).left
        rvLotteryType.smoothScrollBy((left - right) / 2, 0)
    }

    //主题
    override fun setTheme(theme: Theme) {
//        when (theme) {
//            Theme.Default -> {
//                imgLotteryBg.setImageResource(0)
//                topText.setTextColor(ViewUtils.getColor(R.color.black))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.black))
//            }
//            Theme.NewYear -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_them_newyear_top)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.MidAutumn -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_them_middle_top)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.LoverDay -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_them_love_top)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.NationDay -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_them_gq_top)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.ChristmasDay -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_them_sd_top)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.OxYear -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_nn_bg)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//            Theme.Uefa -> {
//                imgLotteryBg.setImageResource(R.drawable.ic_bg_uefa)
//                topText.setTextColor(ViewUtils.getColor(R.color.white))
//                topTextRule.setTextColor(ViewUtils.getColor(R.color.white))
//            }
//        }
//        lotteryTypeAdapter?.notifyDataSetChanged()

    }

//    //换肤
//    @Subscribe(thread = EventThread.MAIN_THREAD)
//    fun changeSkin(eventBean: ChangeSkin) {
////        when (eventBean.id) {
////            1 -> setTheme(Theme.Default)
////            2 -> setTheme(Theme.NewYear)
////            3 -> setTheme(Theme.MidAutumn)
////            4 -> setTheme(Theme.LoverDay)
////            5 -> setTheme(Theme.NationDay)
////            6 -> setTheme(Theme.ChristmasDay)
////            7 -> setTheme(Theme.OxYear)
////            8 -> setTheme(Theme.Uefa)
////        }
//    }
//
//
//
//    //纯净版切换
//    @Subscribe(thread = EventThread.MAIN_THREAD)
//    fun changeMode(eventBean: AppChangeMode) {
//        if (isActive()) {
////            if (eventBean.mode == AppMode.Normal) {
////                setVisible(imgVideo)
////                setTheme(UserInfoSp.getThem())
////            } else {
////                setGone(imgVideo)
////            }
//        }
//
//    }

}