package com.home.old

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.customer.ApiRouter
import com.customer.adapter.HomeHotLiveAdapter
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.component.marquee.DisplayEntity
import com.customer.component.marquee.MarqueeTextView
import com.customer.data.AppChangeMode
import com.customer.data.OnLine
import com.customer.data.ToBetView
import com.customer.data.UserInfoSp
import com.customer.data.game.GameApi
import com.customer.data.home.*
import com.customer.data.login.LoginSuccess
import com.customer.data.mine.ChangeSkin
import com.customer.data.mine.LotteryToLiveRoom
import com.glide.GlideUtil
import com.home.R
import com.home.adapter.BannerImageAdapter
import com.home.children.MoreAnchorAct
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.widget.banner.widget.banner.BannerItem
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_home_recommend_new.*
import java.math.BigDecimal


/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/11
 * @ Describe
 *
 */
class HomeRecommendNewFragment : BaseMvpFragment<HomeRecommendNewPresenter>(), ITheme , IMode {

    var onLine = BigDecimal(-1)

    var gameRoomList: ArrayList<Array<HomeTypeListResponse>?>? = null

    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomeRecommendNewPresenter()

    override fun getLayoutResID() = R.layout.fragment_home_recommend_new

    override fun isRegisterRxBus() = true


    override fun initContentView() {
        setTheme(UserInfoSp.getThem())
        initRecycle()
    }

    private fun initRecycle() {
        upDateBanner(null)
//        upDateSystemNotice(null)
        upDateGame()
        initHotRecommend()
    }


    override fun initData() {
        mPresenter.getAllData()
        homeSmartRefreshLayout.setOnRefreshListener {
            mPresenter.getAllData()
//            mPresenter.getUserBalance()
            homeSmartRefreshLayout.finishRefresh()
        }
    }

    override fun initEvent() {
        tvGameMore?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) RxBus.get().post(ToBetView(1))
        }
        tvHotLiveMore?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) startActivity(
                Intent(
                    requireActivity(),
                    MoreAnchorAct::class.java
                )
            )
        }
    }


    fun upDateBanner(data: List<BannerItem>?) {
        val result: List<BannerItem>
        if (data == null) {
            result = ArrayList()
            for (index in 1..3) {
                result.add(BannerItem())
            }
        } else result = data
        if (homeBanner != null) {
            homeBanner?.adapter = BannerImageAdapter(result)
            val indicator = RectangleIndicator(context)
            homeBanner?.indicator = indicator
            homeBanner?.setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            homeBanner?.setIndicatorSelectedColor(ViewUtils.getColor(R.color.alivc_blue_1))
            homeBanner?.setIndicatorNormalColor(ViewUtils.getColor(R.color.color_DDDDDD))
            homeBanner?.addBannerLifecycleObserver(requireActivity())
        }
    }

    //========= 公告 =========
     fun upDateSystemNotice(data: List<HomeSystemNoticeResponse>?) {
        val result = ArrayList<String>()
        if (data != null && data.isNotEmpty()) {
            data.forEachIndexed { index, value ->
                result.add((index + 1).toString() + "." + value.content)
            }
        } else  result.add("暂无公告")
        tvNoticeMassages.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4"){
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                if (data != null && data.isNotEmpty()) {
                    Router.withApi(ApiRouter::class.java)
                        .toGlobalWeb("", true, data[tvNoticeMassages.currentIndex].id ?: "-1")
                }
            }
        }
        tvNoticeMassages.setOnMarqueeListener(object : MarqueeTextView.OnMarqueeListener {
            override fun onStartMarquee(displayEntity: DisplayEntity?, index: Int): DisplayEntity? {
                return displayEntity
            }

            override fun onMarqueeFinished(displayDatas: MutableList<DisplayEntity>): MutableList<DisplayEntity> {
                return displayDatas
            }

        })
        tvNoticeMassages.speed = 3
        tvNoticeMassages.startSimpleRoll(result)
    }

    //游戏列表
    var gameAdapter: HomeGameRvAdapter? = null
    private fun upDateGame() {
        val it: List<Game>
        it = ArrayList()
        for (index in 1..6) {
            it.add(Game(name = "加载中...", img_url = "", id = "-1", type = ""))
        }
        gameAdapter = HomeGameRvAdapter(context)
        val gridLayoutManager = object : GridLayoutManager(context, 4) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        rvHotGame.adapter = gameAdapter
        rvHotGame.layoutManager = gridLayoutManager
        gameAdapter?.refresh(it)
    }


    // 热门直播
    var hotLiveAdapter: HomeHotLiveAdapter? = null
    private fun initHotRecommend() {
        val it: List<HomeHotLiveResponse>
        it = ArrayList()
        for (index in 1..10) {
            it.add(
                HomeHotLiveResponse(
                    name = "加载中...",
                    nickname = "加载中...",
                    live_intro = "加载中...",
                    online = 0,
                    red_paper_num = 0,
                    daxiu = false
                )
            )
        }
        hotLiveAdapter = HomeHotLiveAdapter()
        val gridLayoutManager = object : GridLayoutManager(context, 2) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        rvHotLiveNew.adapter = hotLiveAdapter
        rvHotLiveNew.layoutManager = gridLayoutManager
        hotLiveAdapter?.refresh(it)
    }

    inner class HomeGameRvAdapter(var context: Context?) : BaseRecyclerAdapter<Game>() {
        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.adapter_lotteryview
        }

        override fun bindData(holder: RecyclerViewHolder, position: Int, item: Game?) {
            context?.let {
                GlideUtil.loadImage(
                    it,
                    item?.img_url,
                    holder.getImageView(R.id.imgLotteryType)
                )
            }
            holder.text(R.id.tvLotteryTypeName, item?.name)
            holder.itemView.setOnClickListener {
                if (!FastClickUtil.isFastClick()) {
                    if (!UserInfoSp.getIsLogin()) {
                        GlobalDialog.notLogged(requireActivity())
                        return@setOnClickListener
                    }
                    when (item?.type) {
                        "lott" -> {
                            Router.withApi(ApiRouter::class.java)
                                .toLotteryGame(item.id ?: "-1", item.name ?: "未知")
                        }
                        "fh_chess" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            showPageLoadingDialog()
                            GameApi.get060(item.id.toString()) {
                                onSuccess {
                                    hidePageLoadingDialog()
                                    Router.withApi(ApiRouter::class.java)
                                        .toGlobalWeb(it.url.toString())
                                }
                                onFailed {
                                    hidePageLoadingDialog()
                                    ToastUtils.showToast(it.getMsg())
                                }
                            }
                        }
                        "ag_live" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            showPageLoadingDialog()
                            GameApi.getAg {
                                onSuccess {
                                    hidePageLoadingDialog()
                                    Router.withApi(ApiRouter::class.java)
                                        .toGlobalWeb(it.url.toString())
                                }
                                onFailed {
                                    hidePageLoadingDialog()
                                    ToastUtils.showToast(it.getMsg())
                                }
                            }
                        }
                        "ag_slot" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            showPageLoadingDialog()
                            GameApi.getAgDZ {
                                onSuccess {
                                    hidePageLoadingDialog()
                                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                                }
                                onFailed {
                                    hidePageLoadingDialog()
                                    ToastUtils.showToast(it.getMsg())
                                }
                            }
                        }
                        "ky" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            showPageLoadingDialog()
                            GameApi.getKy(item.id?:"") {
                                    onSuccess {
                                        Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                                        hidePageLoadingDialog()
                                    }
                                    onFailed {
                                        ToastUtils.showToast(it.getMsg())
                                        hidePageLoadingDialog()
                                    }
                            }
                        }
                        "ibc" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            showPageLoadingDialog()
                            GameApi.getSb(item.id?:"") {
                                    onSuccess { game060 ->
                                        Router.withApi(ApiRouter::class.java).toGlobalWeb(game060.url.toString())
                                        hidePageLoadingDialog()
                                        }
                                onFailed {
                                    ToastUtils.showToast(it.getMsg())
                                    hidePageLoadingDialog()
                                }
                            }
                        }
                        "bg_live" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            GameApi.getBgSx {
                                    onSuccess {
                                        Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                                       hidePageLoadingDialog()
                                    }
                                    onFailed {
                                        ToastUtils.showToast(it.getMsg())
                                        hidePageLoadingDialog()
                                }
                            }
                        }
                        "bg_fish" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            GameApi.getBgFish(item.id?:"") {
                                    onSuccess {
                                        Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                                        hidePageLoadingDialog()
                                    }
                                    onFailed {
                                        ToastUtils.showToast(it.getMsg())
                                        hidePageLoadingDialog()
                                    }
                            }
                        }

                        "ag_hunter"-> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            GameApi.getAgHunter {
                                onSuccess {
                                    Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                                    hidePageLoadingDialog()
                                }
                                onFailed {
                                    ToastUtils.showToast(it.getMsg())
                                    hidePageLoadingDialog()
                                }
                            }
                        }

                        "im" -> {
                            if (UserInfoSp.getUserType() == "4"){
                                context?.let { it1 -> DialogTry(it1).show() }
                                return@setOnClickListener
                            }
                            GameApi.getIM("") {
                                    onSuccess {
                                        Router.withApi(ApiRouter::class.java).toGlobalWeb(it.url.toString())
                                        hidePageLoadingDialog()
                                    }
                                    onFailed {
                                        ToastUtils.showToast(it.getMsg())
                                        hidePageLoadingDialog()
                                    }
                            }
                        }
                    }
                }
            }
        }
    }

    //lottery页面打开直播间
    @Subscribe(thread = EventThread.HANDLER)
    fun lotteryToLiveRoom(eventBean: LotteryToLiveRoom) {
        if (isAdded) {
            if (gameRoomList != null && gameRoomList?.isNotEmpty()!!) {
                for ((pos, bean) in gameRoomList!!.withIndex()) {
                    if (!bean.isNullOrEmpty()) {
                        for ((index, item) in bean.withIndex()) {
                            if (eventBean.id == item.lottery_id && item.anchor_id != null) {
                                Router.withApi(ApiRouter::class.java).toLive(
                                    item.anchor_id ?: "1",
                                    item.lottery_id ?: "1",
                                    item.name ?: "未知",
                                    item.live_status ?: "0",
                                    item.online.toString(),
                                    item.game_id ?: "1",
                                    item.name ?: "未知",
                                    item.image ?: ""
                                )
                                return
                            }
                        }
                    }
                    if ((pos + 1) == gameRoomList?.size) {
                        ToastUtils.showToast("该彩种暂无直播")
                    }
                }

            }
        }
    }


    //主题
    override fun setTheme(theme: Theme) {
//        when (theme) {
//            Theme.Default -> {
//                desGame.setDesNew(title = "热门游戏", isShowLine = true)
//                desHotLive.setDesNew(title = "热门直播", isShowLine = true)
//            }
//            Theme.NewYear -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_them_newyear_5,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_them_newyear_1,
//                    isShowLine = false
//                )
//            }
//            Theme.MidAutumn -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_them_middle_7,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_them_middle_1,
//                    isShowLine = false
//                )
//            }
//            Theme.LoverDay -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_them_love_7,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_them_love_3,
//                    isShowLine = false
//                )
//            }
//            Theme.NationDay -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_them_gq_11,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_them_gq_12,
//                    isShowLine = false
//                )
//            }
//            Theme.ChristmasDay -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_them_sd_11,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_them_sd_12,
//                    isShowLine = false
//                )
//            }
//            Theme.OxYear -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_nn_home_1,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_nn_home_2,
//                    isShowLine = false
//                )
//            }
//            Theme.Uefa -> {
//                desGame.setDesNew(
//                    title = "热门游戏",
//                    image = R.drawable.ic_uefa_home_1,
//                    isShowLine = false
//                )
//                desHotLive.setDesNew(
//                    title = "热门直播",
//                    image = R.drawable.ic_uefa_home_2,
//                    isShowLine = false
//                )
//            }
//        }
//        when (theme) {
//            Theme.ChristmasDay -> {
//                arrow_1?.setImageDrawable(ViewUtils.getDrawable(R.mipmap.ic_arrow_double_sd))
//                arrow_2?.setImageDrawable(ViewUtils.getDrawable(R.mipmap.ic_arrow_double_sd))
//                textMore.setTextColor(ViewUtils.getColor(R.color.color_333333))
//                textMore1.setTextColor(ViewUtils.getColor(R.color.color_333333))
//                ivNotice.setImageResource(R.mipmap.ic_notice)
//            }
//            Theme.Uefa -> {
//                arrow_1?.setImageDrawable(ViewUtils.getDrawable(R.drawable.ic_arrow_double_blue))
//                arrow_2?.setImageDrawable(ViewUtils.getDrawable(R.drawable.ic_arrow_double_blue))
//                textMore.setTextColor(ViewUtils.getColor(R.color.alivc_blue_uefa))
//                textMore1.setTextColor(ViewUtils.getColor(R.color.alivc_blue_uefa))
//                ivNotice.setImageResource(R.mipmap.ic_notice_blue)
//            }
//            else -> {
//                arrow_1?.setImageDrawable(ViewUtils.getDrawable(R.mipmap.ic_arrow_double))
//                arrow_2?.setImageDrawable(ViewUtils.getDrawable(R.mipmap.ic_arrow_double))
//                textMore.setTextColor(ViewUtils.getColor(R.color.color_333333))
//                textMore1.setTextColor(ViewUtils.getColor(R.color.color_333333))
//                ivNotice.setImageResource(R.mipmap.ic_notice)
//            }
//        }
    }

    //纯净版切换
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun changeMode(eventBean: AppChangeMode) {
        if (isActive()) {
            setMode(eventBean.mode)
        }
    }

    override fun setMode(mode: AppMode) {
        if (mode == AppMode.Pure) {
//            setTheme(Theme.Default)
        } else {
            setTheme(UserInfoSp.getThem())
        }
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

    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun onLine(eventBean: OnLine) {
        val real = this.onLine.add(BigDecimal(eventBean.onLine ?: 0))
        tvOnline?.text = "在线人数: $real"
    }


    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun login(eventBean: LoginSuccess) {
        val res = HomeApi.getOnLine()
        res.onSuccess {
            this.onLine = BigDecimal(it.base_online)
            tvOnline?.visibility = View.VISIBLE
            tvOnline?.text = "在线人数: $onLine"
        }
    }
}