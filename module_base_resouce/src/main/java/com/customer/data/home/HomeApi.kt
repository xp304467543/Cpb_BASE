package com.customer.data.home

import android.text.TextUtils
import com.customer.AppConstant
import com.customer.data.UserInfoSp
import com.customer.data.moments.MomentsAnchorListResponse
import com.rxnetgo.rxcache.CacheMode
import cuntomer.api.AllEmptySubscriber
import cuntomer.api.AllSubscriber
import cuntomer.api.ApiSubscriber
import cuntomer.api.EmptySubscriber
import cuntomer.bean.BaseApiBean
import cuntomer.net.BaseApi
import cuntomer.them.AppMode

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/10
 * @ Describe
 *
 */

object HomeApi : BaseApi {

    //轮播图
    private const val HOME_BANNER_LIST = "api/v2/user/get_banner/"

    //首页游戏
    private const val HOME_GAME = "platform/game-list/"

    //系统公告
    private const val HOME_SYSTEM_NOTICE = "api/v2/user/system_notice/"

    //系统公告代理
    private const val HOME_SYSTEM_NOTICE_DL = "api/v2/user/system_notice_dl/"

    //游戏直播列表
    private const val HOME_GAME_LIST = "api/v2/live/get_game_list2/"

    //热门直播
    private const val HOME_HOT_LIVE = "api/v2/live/get_hot_list/"

    //直播预告
    private const val HOME_LIVE_PREVIEW = "api/v2/user/anchor_pop/"

    //主播预告
    private const val Home_LIVE_ADVANCE = "api/v2//user/anchor_advance/"

    //最新资讯
    private const val HOME_NEWS = "api/v2/info/getInfoList/"

    //广告图
    private const val HOME_AD = "api/v2/user/get_ad_banner/"

    //广告图2
    private const val HOME_AD2 = "api/v2/user/get_ad_banner2/"

    //主播推荐
    private const val HOME_ANCHOR_RECOMMEND = "api/v2/live/get_expert_list/"

    //专家红单
    private const val HOME_EXPERT_RED = "plan/expert-list/"

    //初始化直播间
    private const val HOME_INIT_LIVE_ROOM = "api/v2/live/get_live_room/"

    //初始20条消息
    private const val HOME_INIT_TWENTY_NEWS = "api/v2/live/initChat/"

    //初始直播间打赏排行
    private const val HOME_INIT_REWARD_LIST = "api/v2/live/get_reward_list/"

    //主播信息
    private const val HOME_LIVE_ANCHOR_INFO = "api/v2/live/get_anchor_info"

    //一键上热门
    private const val RECOMMEND_ANCHOR = "api/v2/live/anchor_hot/"

    //主播动态
    const val HOME_LIVE_ANCHOR_ANCHOR_DYNAMIC = "api/v2/live/get_anchor_dynamic/"

    //礼物列表
    private const val HOME_LIVE_GIFT_LIST = "api/v2/live/get_gift_list/"

    //资讯
    private const val NEWS_LIST = "api/v2/info/getInfoList/"

    //资讯详情
    private const val NEWS_INFO = "api/v2/info/getInfoDetail/"

    //搜索 主播推荐
    private const val HOME_SEARCH_POP = "api/v2/live/pop_search/"

    //搜索
    private const val HOME_SEARCH = "api/v2/live/search/"

    //管理员清屏
    private const val HOME_MANAGER_CLEAR = "api/v2/live/clear_chat/"

    //禁言
    private const val FORBIDDEN_WORDS = "api/v2/live/ban_words/"

    //发红包
    private const val HOME_LIVE_SEND_RED_ENVELOPE = "api/v2/user/send_red/"

    //直播间红包队列
    private const val HOME_LIVE_RED_RECEIVE_ROOM = "api/v2/live/get_room_red/"

    //抢红包
    private const val HOME_LIVE_RED_RECEIVE = "api/v2/user/receive_red/"

    //直播预告标题
    private const val HOME_LIVE_ADVANCE = "api/v2/live/get_room_list/"

    //关注
    private const val HOME_ATTENTION_ANCHCOR = "api/v2/live/follow/"

    //所有主播
    private const val HOME_ALL_ANCHOR = "api/v2/live/get_all_list/"

    //送礼物
    private const val HOME_LIVE_SEND_GIFT = "api/v2/live/send_gift/"

    //购彩
    private const val LOTTERY_URL = "api/v2/user/jump_to/"

    //关注专家
    private const val FOLLOW_EXPERT = "plan/follow/"

    //版本更新
    private const val VERSION_UPDATE = "api/common/init/"

    //系统公告
    private const val SYSTEM_NOTICE = "api/v2/live/get_pop_notice/"

    //系统公告 代理
    private const val SYSTEM_NOTICE_DL = "api/v2/live/get_pop_notice_dl/"


    //获取新消息通知
    private const val USER_MESSAGE_NEW = "api/v2/live/get_message_count/"


    //热门游戏
    private const val HOT_GAME = "user/hot-games"

    //新手任务红点
    private const val TASK_RED = "user/task-prompt"

    //新手任务
    private const val NEW_HAND_RED = "user/task-list"

    //新手任务领取
    private const val GET_NEW_TASK = "api/v2/live/task_reward/"

    //红包雨领取
    private const val GET_RED_RAIN = "api/v2/user/user_red_rain/"

    //是否展示红包雨
    private const val GET_IS_SHOW_RED = "api/v2/user/is_red_rain/"

    //在线人数
    private const val GET_ONLINE = "api/v2/user/user_online/"

    //导航栏
    private const val HOME_TITLE = "api/common/siteConfig/"

    //主播排行榜
    private const val ANCHOR_RANK = "api/v2/live/anchor_rank/"

    //推广排行榜
    private const val REPORT_RANK = "market/rank/"

    //竞猜排行榜
    private const val BET_RANK = "guess/rank/"

    //影片排行榜
    private const val VIDEO_RANK = "VideoAPI/getRankList/"

    //转盘礼物
    private const val PAN_GIFT = "api/v2/user/turntable_list/"

    //中奖纪录
    private const val PRISE_LOG = "api/v2/user/turntable_log/"

    //单次抽奖价格
    private const val PAN_SINGLE_PRISE = "api/v2/user/get_turntable_money/"

    //转盘得将信息
    private const val GIFT_MESSAG = "api/v2/user/turntable_log/"

    //抽奖
    private const val GET_PRISE = "api/v2/user/get_gift/"

    //购买次数
    private const val GET_ROUND_TIMES = "api/v2/user/get_turntable_chance/"

    //直播Type
    private const val HOME_LIVE_TYPE = "api/v2/live/get_sport_type/"

    //体育banner
    private const val SPORT_BANNER = "api/v2/user/get_sport_banner/"

    //精彩直播banner
    private const val WONDERFUL_BANNER = "api/v2/user/get_good_live/"

    //体育直播
    private const val SPORT_LIVE = "api/v2/live/get_sport_list/"

    //直播页面NAV
    private const val LIVE_NAV = "api/v2/user/get_page_nav/"

    //队伍信息
    private const val TEAM_INFO = "odds/get_teaminfo/"

    //热门赛事
    private const val HOT_MATCH = "api/v2/live/get_hot_match/"

    //来一注
    private const val ONE_MORE_MATCH = "platform/rand-sport-play"

    /**
     * 配置服务器地址
     *
     *   key 2f7029b2f5ae72e0f39675db34bca71c
     */
    fun getSystemUrl(function: ApiSubscriber<SystemUrl>.() -> Unit) {
        val subscriber = object : ApiSubscriber<SystemUrl>() {}
        subscriber.function()
        getSystemApi().post<SystemUrl>("ApiService.list")
            .params("key", "2f7029b2f5ae72e0f39675db34bca71c")
            .subscribe(subscriber)
    }


    /**
     * 获取首页轮播图列表
     */
    fun getHomeBannerResult(cacheMode: CacheMode): ApiSubscriber<List<HomeBannerResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeBannerResponse>>() {}
        getApi()
            .get<List<HomeBannerResponse>>(HOME_BANNER_LIST)
            .cacheMode(cacheMode)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 首页游戏
     */
    fun getHomeGameNewResult(): ApiSubscriber<List<HomeGameResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeGameResponse>>() {}
        getApiOther()
            .get<List<HomeGameResponse>>(HOME_GAME)
            .cacheMode(CacheMode.NONE)
            .params("is_pc", "0")
            .params("is_official",if (AppConstant.isMain) "1" else "0")
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取公告
     */
    fun getHomeSystemNoticeResult(cacheMode: CacheMode): ApiSubscriber<List<HomeSystemNoticeResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeSystemNoticeResponse>>() {}
        getApi()
            .get<List<HomeSystemNoticeResponse>>(if (AppConstant.isMain) HOME_SYSTEM_NOTICE else HOME_SYSTEM_NOTICE_DL)
            .cacheMode(cacheMode)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取彩票类型列表  1.彩票 2.红包
     */
    fun getHomeLotteryTypeResult(cacheMode: CacheMode): AllSubscriber {
        val subscriber = AllSubscriber()
        getApi()
            .get<BaseApiBean>(HOME_GAME_LIST)
            .cacheMode(cacheMode)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取热门推荐
     */
    fun getHomeHotLive(cacheMode: CacheMode): ApiSubscriber<List<HomeHotLiveResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeHotLiveResponse>>() {}
        getApi()
            .get<List<HomeHotLiveResponse>>(HOME_HOT_LIVE)
            .cacheMode(cacheMode)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取直播预告
     */
    fun getHomeLivePreView(cacheMode: CacheMode): EmptySubscriber {
        val subscriber = EmptySubscriber()
        getApi().get<String>(HOME_LIVE_PREVIEW)
            .headers("token", UserInfoSp.getToken())
            .cacheMode(cacheMode)
            .params("user_id", UserInfoSp.getUserId())
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取资讯
     */
    fun getNews(): ApiSubscriber<List<HomeNewsResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeNewsResponse>>() {}
        getApi()
            .get<List<HomeNewsResponse>>(HOME_NEWS)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取广告图
     */
    fun getAd(): ApiSubscriber<List<HomeAdResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeAdResponse>>() {}
        getApi()
            .get<List<HomeAdResponse>>(HOME_AD)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取广告图2
     */
    fun getAd2(): ApiSubscriber<List<HomeAdResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeAdResponse>>() {}
        getApi()
            .get<List<HomeAdResponse>>(HOME_AD2)
            .subscribe(subscriber)
        return subscriber
    }


    /**
     * 主播推荐
     */
    fun getHomeAnchorRecommend(cacheMode: CacheMode): ApiSubscriber<List<HomeHotLiveResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeHotLiveResponse>>() {}
        getApi()
            .get<List<HomeHotLiveResponse>>(HOME_ANCHOR_RECOMMEND)
            .cacheMode(cacheMode)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
        return subscriber
    }


    /**
     * 是否有新消息
     */
    fun getIsNewMessage(function: ApiSubscriber<MineNewMsg>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineNewMsg>() {}
        subscriber.function()
        getApi().get<MineNewMsg>(USER_MESSAGE_NEW)
            .headers("token", UserInfoSp.getToken())
            .params("client_type", 1)
            .params("api", if (AppConstant.isMain) 1 else 5)
            .params("user_id", UserInfoSp.getUserId())
            .subscribe(subscriber)
    }

    /**
     * 初始化直播间(进入直播间)
     */
    fun enterLiveRoom(
        anchorId: String = "",
        client_ip: String = "",
        function: ApiSubscriber<HomeLiveEnterRoomResponse>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<HomeLiveEnterRoomResponse>() {}
        subscriber.function()
        getApi()
            .get<HomeLiveEnterRoomResponse>(HOME_INIT_LIVE_ROOM)
            .params("anchor_id", anchorId)
            .params("user_id", UserInfoSp.getUserId())
            .params("client_ip", client_ip)
            .subscribe(subscriber)
    }

    /**
     * 专家红单
     */
    fun expertRed(): ApiSubscriber<List<HomeExpertList>> {
        val subscriber = object : ApiSubscriber<List<HomeExpertList>>() {}
        getApiLottery()
            .get<List<HomeExpertList>>(HOME_EXPERT_RED)
            .params("limit", "5")
            .params("page", "1")
            .params("is_recommend", "10")
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取20条消息
     */
    fun getTwentyNews(anchorId: String = ""): ApiSubscriber<List<HomeLiveTwentyNewsResponse>> {
        val subscriber = object : ApiSubscriber<List<HomeLiveTwentyNewsResponse>>() {}
        getApi().get<List<HomeLiveTwentyNewsResponse>>(HOME_INIT_TWENTY_NEWS)
            .params("anchor_id", anchorId)
            .params("user_id", UserInfoSp.getUserId())
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 初始直播间打赏排行
     */
    fun getRankList(
        anchorId: String = "",
        function: ApiSubscriber<List<HomeLiveRankList>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<HomeLiveRankList>>() {}
        subscriber.function()
        getApi().get<List<HomeLiveRankList>>(HOME_INIT_REWARD_LIST)
            .params("anchor_id", anchorId)
            .subscribe(subscriber)
    }

    /**
     * 初始直播间预告
     */
    fun getLiveAdvanceList(type: String = "", function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        getApi().get<BaseApiBean>(Home_LIVE_ADVANCE)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("type", type)
            .subscribe(subscriber)
    }

    /**
     * 初始主播信息
     */
    fun getLiveAnchorInfo(
        anchorId: String = "",
        function: ApiSubscriber<HomeLiveAnchorInfoBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<HomeLiveAnchorInfoBean>() {}
        subscriber.function()
        getApi().get<HomeLiveAnchorInfoBean>(
            HOME_LIVE_ANCHOR_INFO
        )
            .params("anchor_id", anchorId)
            .subscribe(subscriber)
    }

    /**
     * 获取主播动态
     */
    fun getAnchorDynamic(
        anchorId: String = "",
        page: String = "1",
        limit: String = "10",
        function: ApiSubscriber<List<MomentsAnchorListResponse>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MomentsAnchorListResponse>>() {}
        subscriber.function()
        getApi().get<List<MomentsAnchorListResponse>>(
            HOME_LIVE_ANCHOR_ANCHOR_DYNAMIC
        )
            .params("anchor_id", anchorId)
            .params("user_id", UserInfoSp.getUserId())
            .params("page", page)
            .params("limit", limit)
            .subscribe(subscriber)
    }

    /**
     * 获取礼物列表
     */
    fun getGiftList(function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApi().get<String>(HOME_LIVE_GIFT_LIST)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * 资讯列表
     */
    fun getNewsList(
        type: String = "",
        neednew: String = "",
        page: String = "1",
        limit: String = "10",
        function: ApiSubscriber<List<HomeNewsResponse>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<HomeNewsResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeNewsResponse>>(
            NEWS_LIST
        )
            .params("type", type)
            .params("neednew", neednew)
            .params("page", page)
            .params("limit", limit)
            .subscribe(subscriber)
    }

    /**
     * 资讯详情
     */
    fun getNewsInfo(
        info_id: String,
        function: ApiSubscriber<List<HomeNesInfoResponse>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<HomeNesInfoResponse>>() {}
        subscriber.function()
        getApi().get<List<HomeNesInfoResponse>>(
            NEWS_INFO
        )
            .params("info_id", info_id)
            .subscribe(subscriber)
    }


    /**
     * 搜索主播推荐
     */
    fun getPopAnchor(function: ApiSubscriber<List<HomeAnchorRecommend>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HomeAnchorRecommend>>() {}
        subscriber.function()
        getApi().get<List<HomeAnchorRecommend>>(
            HOME_SEARCH_POP
        )
            .params("limit", "10")
            .subscribe(subscriber)
    }

    /**
     * 搜索主播
     */
    fun getSearchAnchor(
        search_content: String,
        function: ApiSubscriber<HomeAnchorSearch>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<HomeAnchorSearch>() {}
        subscriber.function()
        getApi().get<HomeAnchorSearch>(HOME_SEARCH)
            .params("search_content", search_content)
            .subscribe(subscriber)
    }

    /**
     * 管理员清屏
     */
    fun managerClear(anchor_id: String, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApi().post<String>(HOME_MANAGER_CLEAR)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("anchor_id", anchor_id)
            .subscribe(subscriber)
    }


    /**
     * 禁言  禁言时间 单位分钟-不传使用后台配置时间 0-永久禁言
     */
    fun forBiddenWords(
        opertate_user: Int,
        ban_user: String,
        room_id: String,
        ban_time: String,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        val request = getApi().post<String>(FORBIDDEN_WORDS)
        request.headers("token", UserInfoSp.getToken())
            .params("opertate_user", opertate_user)
            .params("ban_user", ban_user)
            .params("ban_time", ban_time)
        if (!TextUtils.isEmpty(room_id)) request.params("room_id", room_id)
        request.subscribe(subscriber)
    }

    /**
     * 发红包
     */
    fun homeLiveSendRedEnvelope(
        anchorId: String,
        amount: String,
        num: String,
        text: String,
        password: String,
        function: ApiSubscriber<HomeLiveRedEnvelopeBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<HomeLiveRedEnvelopeBean>() {}
        subscriber.function()
        getApi().post<HomeLiveRedEnvelopeBean>(
            HOME_LIVE_SEND_RED_ENVELOPE
        )
            .headers("token", UserInfoSp.getToken())
            .params("anchor_id", anchorId)
            .params("user_id", UserInfoSp.getUserId())
            .params("amount", amount)
            .params("num", num)
            .params("version", "v2")
            .params("text", text)
            .params("password", password)
            .subscribe(subscriber)
    }

    /**
     * 直播间红包队列
     */
    fun homeLiveRedList(
        anchorId: String,
        function: ApiSubscriber<List<HomeLiveRedRoom>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<HomeLiveRedRoom>>() {}
        subscriber.function()
        val request = getApi().get<List<HomeLiveRedRoom>>(
            HOME_LIVE_RED_RECEIVE_ROOM
        )
            .headers("token", UserInfoSp.getToken())
        if (UserInfoSp.getUserId() != 0) request.params("user_id", UserInfoSp.getUserId())
        request.params("anchor_id", anchorId)
        request.subscribe(subscriber)
    }

    /**
     * 抢红包
     */
    fun homeGetRed(rid: String, function: ApiSubscriber<HomeLiveRedReceiveBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeLiveRedReceiveBean>() {}
        subscriber.function()
        getApi().post<HomeLiveRedReceiveBean>(
            HOME_LIVE_RED_RECEIVE
        )
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("rid", rid)
            .subscribe(subscriber)
    }

    /**
     * 直播预告标题
     */
    fun getAdvanceTitle(function: ApiSubscriber<MutableList<HomeLiveAdvance>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MutableList<HomeLiveAdvance>>() {}
        subscriber.function()
        getApi().post<MutableList<HomeLiveAdvance>>(
            HOME_LIVE_ADVANCE
        )
            .subscribe(subscriber)
    }

    /**
     * 主播关注or取关 增加用户关注
     */
    fun attentionAnchorOrUser(
        anchor_id: String,
        follow_id: String,
        function: ApiSubscriber<Attention>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<Attention>() {}
        subscriber.function()
        val request = getApi().post<Attention>(
            HOME_ATTENTION_ANCHCOR
        )
        request.headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
        if (!TextUtils.isEmpty(anchor_id)) request.params("anchor_id", anchor_id)
        if (!TextUtils.isEmpty(follow_id)) request.params("follow_id", follow_id)
        request.subscribe(subscriber)
    }

    /**
     * 所有主播
     */
    fun getAllAnchor(page: Int, type: String, function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        getApi().get<BaseApiBean>(HOME_ALL_ANCHOR)
            .headers("token", UserInfoSp.getToken())
            .params("page", page)
            .params("type", type)
            .params("limit", "10")
            .subscribe(subscriber)
    }


    /**
     * 送礼物
     */
    fun setGift(
        userId: Int,
        anchorId: String,
        gift_id: String,
        gift_num: String,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(HOME_LIVE_SEND_GIFT)
            .headers("token", UserInfoSp.getToken())
            .params("anchor_id", anchorId)
            .params("user_id", userId)
            .params("version", "v2")
            .params("gift_id", gift_id)
            .params("gift_num", gift_num)
            .subscribe(subscriber)
    }


    /**
     * 购彩网址
     */
    fun getLotteryUrl(function: ApiSubscriber<BetLotteryBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BetLotteryBean>() {}
        subscriber.function()
        getApi().get<BetLotteryBean>(LOTTERY_URL)
            .subscribe(subscriber)

    }


    /**
     * 专家关注
     */
    fun attentionExpert(expert_id: String, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApiLottery().post<String>(FOLLOW_EXPERT)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("user_id", UserInfoSp.getUserId())
            .params("expert_id", expert_id)
            .subscribe(subscriber)

    }


    /**
     * 获取版本
     */
    fun getVersion(function: ApiSubscriber<UpdateData>.() -> Unit) {
        val subscriber = object : ApiSubscriber<UpdateData>() {}
        subscriber.function()
        getApi()
            .get<UpdateData>(VERSION_UPDATE)
            .params("client_type", "android")
            .params("version", AppConstant.version)
            .subscribe(subscriber)
    }

    /**
     * 系统公告
     */
    fun getSystemNotice(function: ApiSubscriber<List<SystemNotice>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<SystemNotice>>() {}
        subscriber.function()
        getApi()
            .get<List<SystemNotice>>(if (UserInfoSp.getAppMode() == AppMode.Normal) SYSTEM_NOTICE else SYSTEM_NOTICE_DL)
            .headers("token", UserInfoSp.getToken())
            .params("page", 1)
            .params("limit", 1)
            .subscribe(subscriber)
    }

    /**
     * 系统公告
     */
    fun getSystemNoticeDl(function: ApiSubscriber<List<SystemNotice>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<SystemNotice>>() {}
        subscriber.function()
        getApi()
            .get<List<SystemNotice>>(SYSTEM_NOTICE_DL)
            .headers("token", UserInfoSp.getToken())
            .params("page", 1)
            .params("limit", 1)
            .subscribe(subscriber)
    }

    /**
     * 首页游戏
     */
    fun getHomeGame(): ApiSubscriber<List<Game>> {
        val subscriber = object : ApiSubscriber<List<Game>>() {}
        getApiOther()
            .get<List<Game>>(HOT_GAME)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 新手任务红点
     */
    fun getRedTask(function: ApiSubscriber<RedTask>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RedTask>() {}
        subscriber.function()
        getApiOther()
            .get<RedTask>(TASK_RED)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 新手任务
     */
    fun userTask(function: ApiSubscriber<ArrayList<UserTask>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<UserTask>>() {}
        subscriber.function()
        getApiOther()
            .get<ArrayList<UserTask>>(NEW_HAND_RED)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 新手任务领取
     */
    fun getNewTask(task_id: String, function: ApiSubscriber<TaskGift>.() -> Unit) {
        val subscriber = object : ApiSubscriber<TaskGift>() {}
        subscriber.function()
        getApi()
            .post<TaskGift>(GET_NEW_TASK)
            .headers("token", UserInfoSp.getToken())
            .params("task_id", task_id)
            .subscribe(subscriber)
    }


    /**
     * 新手任务领取
     */
    fun getRedRain(function: ApiSubscriber<RedRain>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RedRain>() {}
        subscriber.function()
        getApi()
            .post<RedRain>(GET_RED_RAIN)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }


    /**
     * 是否展示红包雨
     */
    fun getIsShowRed(function: ApiSubscriber<RedRainIsShow>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RedRainIsShow>() {}
        subscriber.function()
        getApi()
            .get<RedRainIsShow>(GET_IS_SHOW_RED)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * 在线人数
     */
    fun getOnLine(): ApiSubscriber<Online> {
        val subscriber = object : ApiSubscriber<Online>() {}
        getApi().get<Online>(GET_ONLINE)
            .subscribe(subscriber)
        return subscriber

    }

    /**
     * 首页导航
     */
    fun getHomeTitle(function: ApiSubscriber<HomeTitle>.() -> Unit) {
        val subscriber = object : ApiSubscriber<HomeTitle>() {}
        subscriber.function()
        getApi().get<HomeTitle>(HOME_TITLE)
            .headers("token", UserInfoSp.getToken())
            .params("client", 3)
            .params("version", AppConstant.version)
            .subscribe(subscriber)
    }


    /**
     * 主播排行榜
     */
    fun getAnchorRank(function: ApiSubscriber<AnchorRank>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AnchorRank>() {}
        subscriber.function()
        getApi().get<AnchorRank>(ANCHOR_RANK)
            .subscribe(subscriber)
    }


    /**
     * 推广排行榜
     */
    fun getReportRank(function: ApiSubscriber<AnchorRank>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AnchorRank>() {}
        subscriber.function()
        getApiOther()
            .get<AnchorRank>(REPORT_RANK)
            .subscribe(subscriber)
    }

    /**
     * 推广排行榜
     */
    fun getBetRank(function: ApiSubscriber<AnchorRank>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AnchorRank>() {}
        subscriber.function()
        getApiLottery()
            .get<AnchorRank>(BET_RANK)
            .subscribe(subscriber)
    }


    /**
     * 推广排行榜
     */
    fun geVideoRank(function: ApiSubscriber<AnchorRank>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AnchorRank>() {}
        subscriber.function()
        getApiMovie()
            .get<AnchorRank>(VIDEO_RANK)
            .subscribe(subscriber)
    }

    fun recommendAnchor(id: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi()
            .get<String>(RECOMMEND_ANCHOR)
            .headers("token", UserInfoSp.getToken())
            .params("anchor_id", id)
            .subscribe(subscriber)
    }


    fun gePanGift(get_type: Int = 0, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApi()
            .get<String>(PAN_GIFT)
            .headers("token", UserInfoSp.getToken())
            .params("get_type", get_type)
            .subscribe(subscriber)
    }

    fun gePanPriseLog(get_type: Int, page: Int, function: ApiSubscriber<ArrayList<PanPriseLog>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<PanPriseLog>>() {}
        subscriber.function()
        getApi()
            .get<ArrayList<PanPriseLog>>(PRISE_LOG)
            .headers("token", UserInfoSp.getToken())
            .params("type", get_type)
            .params("limit", 20)
            .params("page", page)
            .subscribe(subscriber)
    }


    fun gePanSinglePrise(get_type: Int = 0, function: ApiSubscriber<PanSinglePrise>.() -> Unit) {
        val subscriber = object : ApiSubscriber<PanSinglePrise>() {}
        subscriber.function()
        getApi()
            .get<PanSinglePrise>(PAN_SINGLE_PRISE)
            .headers("token", UserInfoSp.getToken())
            .params("get_type", get_type)
            .subscribe(subscriber)
    }


    fun getPanGiftMessage(function: ApiSubscriber<List<PanGiftObject>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<PanGiftObject>>() {}
        subscriber.function()
        getApi()
            .get<List<PanGiftObject>>(GIFT_MESSAG)
            .headers("token", UserInfoSp.getToken())
            .params("limit", 100)
            .subscribe(subscriber)
    }

    fun getPrise(get_type: Int = 0, function: ApiSubscriber<PriseObject>.() -> Unit) {
        val subscriber = object : ApiSubscriber<PriseObject>() {}
        subscriber.function()
        getApi()
            .post<PriseObject>(GET_PRISE)
            .headers("token", UserInfoSp.getToken())
            .params("get_type", get_type)
            .subscribe(subscriber)

    }

    fun getRoundTimes(str: Int, function: ApiSubscriber<PriseObject>.() -> Unit) {
        val subscriber = object : ApiSubscriber<PriseObject>() {}
        subscriber.function()
        getApi()
            .post<PriseObject>(GET_ROUND_TIMES)
            .headers("token", UserInfoSp.getToken())
            .params("times", str)
            .subscribe(subscriber)

    }

    fun getLiveType(function: ApiSubscriber<ArrayList<LiveTypeObject>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<LiveTypeObject>>() {}
        subscriber.function()
        getApi()
            .post<ArrayList<LiveTypeObject>>(HOME_LIVE_TYPE)
            .subscribe(subscriber)

    }

    fun getSportBanner(type: String, function: ApiSubscriber<ArrayList<SportBanner>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<SportBanner>>() {}
        subscriber.function()
        getApi()
            .get<ArrayList<SportBanner>>(SPORT_BANNER)
            .params("race_type", type)
            .params("client_type", "1")
            .subscribe(subscriber)

    }


    fun getWonderfulBanner(
        type: String,
        function: ApiSubscriber<ArrayList<SportBanner>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<ArrayList<SportBanner>>() {}
        subscriber.function()
        getApi()
            .get<ArrayList<SportBanner>>(WONDERFUL_BANNER)
            .params("lottery", type)
            .subscribe(subscriber)
    }

    fun getSportLive(
        race_type: String,
        page: Int = 1,
        function: ApiSubscriber<SportLive>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<SportLive>() {}
        subscriber.function()
        getApi()
            .get<SportLive>(SPORT_LIVE)
            .params("page", page)
            .params("race_type", race_type)
            .params("times", 10)
            .subscribe(subscriber)
    }

    fun getLiveNav(function: ApiSubscriber<List<String>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<String>>() {}
        subscriber.function()
        getApi()
            .get<List<String>>(LIVE_NAV)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    fun getTeamInfo( type: String, teamid:String,function: ApiSubscriber<TeamInfo>.() -> Unit ){
        val subscriber = object : ApiSubscriber<TeamInfo>() {}
        subscriber.function()
        getApi()
            .get<TeamInfo>(TEAM_INFO)
            .params("type", type)
            .params("teamid", teamid)
            .subscribe(subscriber)
    }


    fun getHotMatch(function: ApiSubscriber<ArrayList<HotMatch>>.() -> Unit ){
        val subscriber = object : ApiSubscriber<ArrayList<HotMatch>>() {}
        subscriber.function()
        getApi()
            .get<ArrayList<HotMatch>>(HOT_MATCH)
            .subscribe(subscriber)
    }

    fun moreGame(function: ApiSubscriber<moreGame>.() -> Unit ){
        val subscriber = object : ApiSubscriber<moreGame>() {}
        subscriber.function()
        getApiOther()
            .get<moreGame>(ONE_MORE_MATCH)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_type", "sport")
            .params("is_mobile", "1")
            .subscribe(subscriber)
    }

}