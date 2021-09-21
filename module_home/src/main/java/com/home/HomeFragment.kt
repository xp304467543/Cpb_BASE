package com.home

import android.annotation.SuppressLint
import android.content.Intent
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customer.ApiRouter
import com.customer.component.dialog.DialogMatch
import com.customer.component.dialog.DialogTry
import com.customer.component.dialog.GlobalDialog
import com.customer.component.marquee.DisplayEntity
import com.customer.component.marquee.MarqueeTextView
import com.customer.data.*
import com.customer.data.home.HomeGameChildResponse
import com.customer.data.home.HomeGameResponse
import com.customer.data.home.HomeSystemNoticeResponse
import com.customer.data.login.LoginSuccess
import com.glide.GlideUtil
import com.home.adapter.BannerImageAdapter
import com.home.game.HomeGameAct
import com.home.game.HomeGameRecentActivity
import com.home.rank.HomeRankingActivity
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.thread.EventThread
import com.lib.basiclib.base.mvp.BaseMvpFragment
import com.lib.basiclib.base.recycle.BaseRecyclerAdapter
import com.lib.basiclib.base.recycle.RecyclerViewHolder
import com.lib.basiclib.base.xui.widget.banner.widget.banner.BannerItem
import com.lib.basiclib.utils.FastClickUtil
import com.lib.basiclib.utils.StatusBarUtils
import com.lib.basiclib.utils.ViewUtils
import com.xiaojinzi.component.impl.Router
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.RectangleIndicator
import cuntomer.them.AppMode
import cuntomer.them.IMode
import cuntomer.them.ITheme
import cuntomer.them.Theme
import kotlinx.android.synthetic.main.fragment_home.*


/**
 *
 * @ Author  QinTian
 * @ Date  6/8/21
 * @ Describe
 *
 */
class HomeFragment : BaseMvpFragment<HomePresenter>(), ITheme, IMode {

    //新消息
    var msg1 = ""
    var msg2 = ""
    var msg3 = ""
    var msg4 = ""
    var gameTypeAdapter: GameTypeAdapter? = null


    override fun attachView() = mPresenter.attachView(this)

    override fun attachPresenter() = HomePresenter()

    override fun getLayoutResID() = R.layout.fragment_home

    override fun isRegisterRxBus() = true

    @SuppressLint("SetTextI18n")
    override fun onSupportVisible() {
        super.onSupportVisible()
        if (UserInfoSp.getIsLogin()) {
            GlideUtil.loadCircleImage(requireContext(), UserInfoSp.getUserPhoto(), userAvatar, true)
            mPresenter.getNewMsg()
            userName.text = UserInfoSp.getUserNickName()
            userId.text = "ID: " + UserInfoSp.getUserUniqueId()
//            mPresenter.getRedTask()
        } else {
            userAvatar.setImageResource(R.mipmap.ic_base_user)
            userName.text = "您还未登陆"
            userId.text = "登录/注册后查看"
        }
//        if (UserInfoSp.getIsShowAppModeChange()) {
//            setVisible(homeAppSwitchMode)
//        } else setGone(homeAppSwitchMode)
        if (!isLoadGame) {
            mPresenter.getHomeGame()
        }
    }

    override fun initContentView() {
        setSwipeBackEnable(false)
        StatusBarUtils.setStatusBarHeight(statusViewHome)
        upDateBanner(null)
        rvGameType.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        gameTypeAdapter = GameTypeAdapter()
        rvGameType.adapter = gameTypeAdapter
        contentSmart.setEnableRefresh(false)//是否启用下拉刷新功能
        contentSmart.setEnableLoadMore(false)//是否启用上拉加载功能
        contentSmart.setEnableOverScrollBounce(true)//是否启用越界回弹
        contentSmart.setEnableOverScrollDrag(true)//是否启用越界拖动（仿苹果效果）
        vpContent.setItemViewCacheSize(30)

    }

    override fun initData() {
        mPresenter.getAllData()
    }

    override fun initEvent() {
//        homeRefresh.setOnRefreshListener {
//            mPresenter.getAllData()
//            homeRefresh.finishRefresh()
//        }
        imgHotMatch?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                context?.let { it1 -> DialogMatch(it1).show() }
            }

        }
        tvRank.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                startActivity(Intent(context, HomeRankingActivity::class.java))
            }
        }
        tvVip.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                Router.withApi(ApiRouter::class.java).toVipPage(UserInfoSp.getVipLevel())
            }
        }
        tvCk.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (!UserInfoSp.getIsLogin()) {
                    GlobalDialog.notLogged(requireActivity())
                    return@setOnClickListener
                }
                if (UserInfoSp.getUserType() == "4") {
                    context?.let { it1 -> DialogTry(it1).show() }
                    return@setOnClickListener
                }
                Router.withApi(ApiRouter::class.java).toMineRecharge(0)
            }
        }
        tvQk.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4") {
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            Router.withApi(ApiRouter::class.java).toMineRecharge(1)
        }

        userAvatar.setOnClickListener {
            if (UserInfoSp.getIsLogin()) RxBus.get().post(HomeJumpToMine(true))
            else Router.withApi(ApiRouter::class.java).toLogin()
        }
        rlMessage.setOnClickListener {
            if (!UserInfoSp.getIsLogin()) {
                GlobalDialog.notLogged(requireActivity())
                return@setOnClickListener
            }
            if (UserInfoSp.getUserType() == "4") {
                context?.let { it1 -> DialogTry(it1).show() }
                return@setOnClickListener
            }
            Router.withApi(ApiRouter::class.java).toMineMessage(msg1, msg2, msg3, msg4)
        }

        homeAppSwitchMode?.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                val anim = AnimationUtils.loadAnimation(
                    context,
                    R.anim.left_out
                ) as AnimationSet
                anim.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        if (UserInfoSp.getAppMode() == AppMode.Pure) {
                            tvAppMode.text = "直播版"
                            UserInfoSp.putAppMode(AppMode.Normal)
                            RxBus.get().post(AppChangeMode(AppMode.Normal))
                        } else {
                            tvAppMode.text = "纯净版"
                            UserInfoSp.putAppMode(AppMode.Pure)
                            RxBus.get().post(AppChangeMode(AppMode.Pure))
//                            RxBus.get().post(ChangeSkin(1))
                        }
                    }
                })
                homeAppSwitchMode?.startAnimation(anim)
            }
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
        } else result.add("暂无公告")
        tvNoticeMassages.setOnClickListener {
            if (!FastClickUtil.isFastClick()) {
                if (UserInfoSp.getUserType() == "4") {
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


    var isLoadGame = false
    var gameContentAdapter: GameContentAdapter? = null
    var gameContentLayoutManager: LinearLayoutManager? = null
    fun upDateGame(data: List<HomeGameResponse>?) {
        initRight()
        isLoadGame = true
        gameTypeAdapter?.refresh(data)
        if (data != null) {
            gameContentAdapter = GameContentAdapter()
            vpContent.adapter = gameContentAdapter
            gameContentLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            vpContent.layoutManager = gameContentLayoutManager
            val contentData = arrayListOf<HomeGameChildResponse>()
            for ((_, item) in data.withIndex()) {
                if (!item.list.isNullOrEmpty()) {
                    for ((index, child) in item.list!!.withIndex()) {
                        child.typePos = item.code ?: ""
                        contentData.add(child)
                    }
                }
            }
            gameContentAdapter?.refresh(contentData)
        }
    }

    private fun initRight() {
        vpContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 第一个可见位置
                val firstItemPosition =
                    recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0))
                val firstTimePositionTypeId: String =
                    gameContentAdapter?.data?.get(firstItemPosition)?.typePos ?: ""
                if (!TextUtils.isEmpty(firstTimePositionTypeId)) {
                    if (!gameTypeAdapter?.data.isNullOrEmpty()) {
                        for ((index,item) in gameTypeAdapter?.data!!.withIndex()) {
                            if (firstTimePositionTypeId == item.code){
                                currentSel = index
                                gameTypeAdapter?.notifyDataSetChanged()
                                break
                            }
                        }
                    }
                }
            }
        })
    }



    private fun smoothMoveToPosition(position: Int) {
        gameContentLayoutManager?.scrollToPositionWithOffset(position,0)
    }


    var currentSel = 0

    inner class GameTypeAdapter : BaseRecyclerAdapter<HomeGameResponse>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_home_game_type

        override fun bindData(holder: RecyclerViewHolder, position: Int, data: HomeGameResponse?) {
            try {
                if (position == currentSel) {
                    context?.let {
                        GlideUtil.loadImageGameType(
                            it,
                            data?.icon1,
                            holder.findViewById(R.id.imgGameType)
                        )
                    }
                } else {
                    context?.let {
                        GlideUtil.loadImageGameType(
                            it,
                            data?.icon,
                            holder.findViewById(R.id.imgGameType)
                        )
                    }
                }
                holder.itemView.setOnClickListener {
                    currentSel = position
                    if (!gameContentAdapter?.data.isNullOrEmpty()) {
                        for ((index, child) in gameContentAdapter?.data!!.withIndex()) {
                            if (data?.code == child.typePos) {
                                smoothMoveToPosition(index)
                                break
                            }
                        }
                    }
                    gameTypeAdapter?.notifyDataSetChanged()
                }
            } catch (e: Exception) {
            }
        }
    }


    inner class GameContentAdapter : BaseRecyclerAdapter<HomeGameChildResponse>() {

        override fun getItemLayoutId(viewType: Int) = R.layout.adapter_home_game_content

        override fun bindData(
            holder: RecyclerViewHolder,
            position: Int,
            data: HomeGameChildResponse?
        ) {
//            holder.itemView.animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.my_anim)
            try {
                context?.let {
                    GlideUtil.loadImageBanner(
                        it,
                        data?.img,
                        holder.findViewById(R.id.imgGameContent)
                    )
                }
                holder.itemView.setOnClickListener {
                    if (!FastClickUtil.isFastClick()) {
                        if (!UserInfoSp.getIsLogin()) {
                            GlobalDialog.notLogged(requireActivity())
                            return@setOnClickListener
                        }
                        showPageLoadingDialog("加载中...")
                        when (data?.platform) {
                            "ibc" -> {
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                mPresenter.getSb(data.id ?: "")
                            }

                            "im" -> {
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                mPresenter.getIm(data.id ?: "")
                            }
                            "bbin" ->{
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                mPresenter.getBing(data.id ?: "",data.game_type?:"sport")
                            }

                            "lg" -> {
                                hidePageLoadingDialog()
                                val intent = Intent(context, HomeGameRecentActivity::class.java)
                                intent.putExtra("gameType", data.type)
                                context?.startActivity(intent)
//                                Router.withApi(ApiRouter::class.java)
//                                    .toLotteryGame(data.id ?: "-1", data.name ?: "未知")
                            }
                            "fh_chess" -> {
                                hidePageLoadingDialog()
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    return@setOnClickListener
                                }
                                val intent = Intent(context, HomeGameRecentActivity::class.java)
                                intent.putExtra("gameType", data.type)
                                context?.startActivity(intent)
//                                mPresenter.getChessGame(data.id.toString())
                            }

                            "ky" -> {
                                hidePageLoadingDialog()
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    return@setOnClickListener
                                }
                                val intent = Intent(context, HomeGameAct::class.java)
                                intent.putExtra("gameType", data.platform)
                                context?.startActivity(intent)
//                                mPresenter.getKy(data.id ?: "")
                            }

                            "ag" -> {
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                when(data.game_type){
                                    "live"-> mPresenter.getAg()
                                    "slot"-> mPresenter.getAgDz()
                                    "hunter"-> mPresenter.getAgHunter()
                                }
                            }

                            "bg"->{
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                when(data.game_type){
                                    "live"-> mPresenter.getAgBgSx()
                                    "bg_fish"-> mPresenter.getBgFish(data.id ?: "")
                                }
                            }

                            "ae" ->{
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                mPresenter.getAe(data.id ?: "",data.game_type?:"live")
                            }
                            "cmd" ->{
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                mPresenter.getCmd(data.id ?: "",data.game_type?:"sport")
                            }
                            "sbo" ->{
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    hidePageLoadingDialog()
                                    return@setOnClickListener
                                }
                                mPresenter.getSbo(data.id ?: "",data.game_type?:"sport")
                            }
                            "mg"->{
                                hidePageLoadingDialog()
                                if (UserInfoSp.getUserType() == "4") {
                                    context?.let { it1 -> DialogTry(it1).show() }
                                    return@setOnClickListener
                                }
//                                mPresenter.getMg(data.game_type?:"slot")
                                val intent = Intent(context, HomeGameAct::class.java)
                                intent.putExtra("gameType", data.platform)
                                context?.startActivity(intent)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }


    override fun setTheme(theme: Theme) {

    }

    override fun setMode(mode: AppMode) {

    }

    @SuppressLint("SetTextI18n")
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun login(eventBean: LoginSuccess) {

    }

    //退出登录
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun loginOut(eventBean: LoginOut) {
        if (isActive() && userAvatar != null) userAvatar?.setImageResource(R.mipmap.ic_base_user)
        userName.text = "您还未登陆"
        userId.text = "登录/注册后查看"
        setGone(topDian)
    }

    //扫码登录后退出
    @Subscribe(thread = EventThread.MAIN_THREAD)
    fun scanLoginOut(eventBean: MineUserScanLoginOut) {
        if (isActive()) {
            if (userAvatar != null) userAvatar?.setImageResource(R.mipmap.ic_base_user)
            userName.text = "您还未登陆"
            userId.text = "登录/注册后查看"
            setGone(topDian)
        }

    }
}