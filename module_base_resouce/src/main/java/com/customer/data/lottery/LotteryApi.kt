package com.customer.data.lottery

import com.customer.data.UserInfoSp
import com.rxnetgo.rxcache.CacheMode
import cuntomer.api.AllEmptySubscriber
import cuntomer.api.ApiSubscriber
import cuntomer.net.BaseApi
import io.reactivex.disposables.Disposable

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/16
 * @ Describe
 *
 */
object LotteryApi : BaseApi {

    //彩种
    private const val LOTTERY_TYPE = "idx/sort"

    //最新开奖号
    private const val LOTTERY_NEW_CODE = "ch/indexNewOne"

    //历史开奖号
    private const val LOTTERY_HISTORY_CODE = "idx/history"

    //露珠
    private const val LOTTERY_LU_ZHU = "lottery/dewdrop"

    //长龙
    private const val LOTTERY_CHANG_LONG = "lottery/changlong"

    //走势
    private const val LOTTERY_TREND = "lottery/trending"

    //专家计划
    private const val LOTTERY_EXPERT_PLAN = "plan/index"

    //竞彩彩种
    private const val LOTTERY_BET_TYPE = "ch/bet_sort"

    //竞彩彩种玩法
    private const val LOTTERY_BET_RULE_TYPE = "guess/play_rule"

    //投注记录
    private const val LOTTERY_BET_HISTORY = "guess/play_bet_history"

    //玩法列表
    private const val GUESS_PLAY_LIST = "guess/play_list"

    //长龙
    private const val GUESS_PLAY_LONG = "lottery/dragon-bet"

    //投注可选金额
    private const val LOTTERY_BET_COUNT_MONEY = "guess/play_sum_list"

    //投注
    const val LOTTERY_BET = "guess/play_bet"

    //长龙投注
    const val LONG_BET = "guess/dragon-play"


    /**
     * 获取彩种
     */
    fun getLotteryType(): ApiSubscriber<List<LotteryTypeResponse>> {
        val subscriber = object : ApiSubscriber<List<LotteryTypeResponse>>() {}
        getApiOpenLottery()
            .post<List<LotteryTypeResponse>>(LOTTERY_BET_TYPE)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 获取开奖号
     */
    fun getLotteryNewCode(lotteryId: String, function: ApiSubscriber<LotteryCodeNewResponse>.() -> Unit):Disposable {
        val subscriber = object : ApiSubscriber<LotteryCodeNewResponse>() {}
        subscriber.function()
        return getApiOpenLottery().post<LotteryCodeNewResponse>(LOTTERY_NEW_CODE)
           .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .subscribeWith(subscriber)
    }

    /**
     * 获取历史开奖号
     */
    fun getLotteryHistoryCode(lotteryId: String, date: String, page: Int,num:Int = 20, function: ApiSubscriber<List<LotteryCodeHistoryResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryCodeHistoryResponse>>() {}
        subscriber.function()
        getApiOpenLottery()
            .post<List<LotteryCodeHistoryResponse>>(LOTTERY_HISTORY_CODE)
            .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .params("belong_date", date)
            .params("page", page)
            .params("nums", num)
            .subscribe(subscriber)
    }

    /**
     * 长龙
     */
    fun getChangLong(lotteryId: String ,function: ApiSubscriber<List<LotteryCodeChangLongResponse>>.() -> Unit){
        val subscriber = object : ApiSubscriber<List<LotteryCodeChangLongResponse>>() {}
        subscriber.function()
        getApiLottery()
            .get<List<LotteryCodeChangLongResponse>>(LOTTERY_CHANG_LONG)
            .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .subscribe(subscriber)
    }

    /**
     * 获取 露珠
     */
    fun getLotteryLuZhu(lotteryId: String, type: String, date: String, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApiLottery()
            .get<String>(LOTTERY_LU_ZHU)
            .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .params("belong_date", date)
            .params("rows", 20)
            .params("type", type)
            .params("reverse", 1)
            .subscribe(subscriber)
    }

    /**
     * 获取 走势
     */
    fun getLotteryTrend(lotteryId: String, num: String, limit: String, date: String, function: ApiSubscriber<List<LotteryCodeTrendResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryCodeTrendResponse>>() {}
        subscriber.function()
        getApiLottery()
            .get<List<LotteryCodeTrendResponse>>(LOTTERY_TREND)
            .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .params("num", num)
            .params("belong_date", date)
            .params("limit", limit)
            .subscribe(subscriber)
    }

    /**
     * 获取 专家计划
     */
    fun getExpertPlan(lotteryId: String, issue: String, limit: String, page: Int, function: ApiSubscriber<List<LotteryExpertPaleyResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryExpertPaleyResponse>>() {}
        subscriber.function()
        getApiLottery()
            .get<List<LotteryExpertPaleyResponse>>(LOTTERY_EXPERT_PLAN)
            .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .params("issue", issue)
            .params("limit", limit)
            .params("page", page)
            .subscribe(subscriber)
    }

    /**
     * 获取 玩法列表
     */
    fun getGuessPlayList(lotteryId: String, function: ApiSubscriber<List<LotteryPlayListResponse>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<LotteryPlayListResponse>>() {}
        subscriber.function()
        getApiLottery()
            .get<List<LotteryPlayListResponse>>(GUESS_PLAY_LIST)
            .cacheMode(CacheMode.NONE)
            .params("lottery_id", lotteryId)
            .params("client_type", 4)
            .subscribe(subscriber)
    }

    /**
     * 获取 长龙
     */
    fun getGuessLongPlayList( function: ApiSubscriber<List<PlaySecData>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<PlaySecData>>() {}
        subscriber.function()
        getApiLottery()
            .get<List<PlaySecData>>(GUESS_PLAY_LONG)
            .cacheMode(CacheMode.NONE)
            .subscribe(subscriber)
    }

    /**
     * 获取竞彩彩种
     */
    fun getLotteryBetType(): ApiSubscriber<List<LotteryTypeResponse>> {
        val subscriber = object : ApiSubscriber<List<LotteryTypeResponse>>() {}
        getApiOpenLottery()
            .post<List<LotteryTypeResponse>>(LOTTERY_BET_TYPE)
            .subscribe(subscriber)
        return subscriber
    }


    /**
     * 获取竞彩彩种玩法
     */
    fun getLotteryBetRule(): ApiSubscriber<ArrayList<LotteryBetRuleResponse>> {
        val subscriber = object : ApiSubscriber<ArrayList<LotteryBetRuleResponse>>() {}
        getApiLottery()
            .get<ArrayList<LotteryBetRuleResponse>>(LOTTERY_BET_RULE_TYPE)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 投注记录
     */
    fun getLotteryBetHistory(play_bet_state: Int,is_bl_play:String="0", page: Int,lotteryId: String="0",st:String="",et:String=""): ApiSubscriber<ArrayList<LotteryBetHistoryResponse>> {
        val subscriber = object : ApiSubscriber<ArrayList<LotteryBetHistoryResponse>>() {}
        getApiLottery()
            .get<ArrayList<LotteryBetHistoryResponse>>(LOTTERY_BET_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("play_bet_state", play_bet_state)
            .params("limit", 20)
            .params("lottery_id", lotteryId)
            .params("st", st)
            .params("et", et)
            .params("is_bl_play", is_bl_play)
            .params("page", page)
            .subscribe(subscriber)
        return subscriber
    }

    /**
     * 投注金额
     */

    fun lotteryBetMoney(function: ApiSubscriber<List<PlayMoneyData>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<PlayMoneyData>>() {}
        subscriber.function()
        getApiLottery()
            .get<List<PlayMoneyData>>(LOTTERY_BET_COUNT_MONEY)
            .cacheMode(CacheMode.NONE)
            .subscribe(subscriber)
    }

    /**
     * 投注
     */
    fun toBet( date: String, function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApiLottery()
            .post<String>(LOTTERY_BET).isMultipart(true)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("datas", date)
            .subscribe(subscriber)
    }

}