package com.customer.data.game

import com.customer.data.UserInfoSp
import cuntomer.api.ApiSubscriber
import cuntomer.net.BaseApi

/**
 *
 * @ Author  QinTian
 * @ Date  2020/9/13
 * @ Describe
 *
 */
object GameApi : BaseApi {

    private const val GAME_ALL = "user/all-games"

    private const val GAME_060 = "fhchess/play"

    private const val GAME_AG = "ag/play"

    private const val GAME_AG_DZ = "ag/play-slot"

    private const val GAME_MG_DZ = "mg/play"

    private const val GAME_BG_SX = "bg/play-live"

    private const val GAME_BG_FISH = "bg/play-fishing"

    private const val GAME_SHA_BA = "ibc/play"

    private const val GAME_IM = "im/play"

    private const val BING_BING = "bbin/play"

    private const val AE = "ae/play"

    private const val CMD = "cmd/play"

    private const val SBO = "sbo/play"

    private const val GAME_KY = "ky/play"

    private const val GAME_AG_HUNTER = "ag/play-hunter"

    private const val GAME_CHESS_HISTORY = "fhchess/game-record"

    private const val GAME_AG_LIVE_HISTORY = "ag/game-record"

    private const val GAME_AG_FISH_HISTORY = "ag/hunter-record"

    private const val GAME_AG_HISTORY = "ag/slot-record"

    private const val GAME_BG_LIVE_HISTORY = "bg/live-record"

    private const val GAME_BG_FISH_HISTORY = "bg/fishing-record"

    private const val GAME_KYQI_HISTORY = "ky/game-record"

    private const val GAME_SBTY_HISTORY = "ibc/game-record"

    private const val GAME_IMTY_HISTORY = "im/game-record"

    private const val GAME_BBTY_HISTORY = "bbin/game-record"
    private const val GAME_BBSX_HISTORY = "bbin/game-record"

    private const val GAME_AESX_HISTORY = "ae/game-record"

    private const val GAME_MG_HISTORY = "mg/game-record"

    private const val GAME_CMD_HISTORY = "cmd/game-record"

    private const val GAME_SBO_HISTORY = "sbo/game-record"

    /**
     * 游戏列表
     */
    fun getAllGame(type:String="",style:String="0"): ApiSubscriber<ArrayList<GameAll>> {
        val subscriber = object : ApiSubscriber<ArrayList<GameAll>>() {}
        getApiOther()
            .get<ArrayList<GameAll>>(GAME_ALL)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("type",type)
            .params("style",style)
            .subscribe(subscriber)
        return subscriber
    }


    /**
     * 游戏列表
     */
    fun getAllGameNew(type:String="",style:String="0"): ApiSubscriber<GameAll> {
        val subscriber = object : ApiSubscriber<GameAll>() {}
        getApiOther()
            .get<GameAll>(GAME_ALL)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("type",type)
            .params("style",style)
            .subscribe(subscriber)
        return subscriber
    }


    /**
     * 060棋牌
     */
    fun get060(game_id: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .post<Game060>(GAME_060)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .subscribe(subscriber)
    }

    /**
     * AG视讯
     */
    fun getAg(function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_AG)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * AG电子
     */
    fun getAgDZ(function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_AG_DZ)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * MG电子
     */
    fun getMgDz(game_id: String, game_type: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_MG_DZ)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("game_type", game_type)
            .subscribe(subscriber)
    }

    /**
     * BG视讯
     */
    fun getBgSx(function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_BG_SX)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     * BG捕鱼
     */
    fun getBgFish(game_id: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_BG_FISH)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .subscribe(subscriber)
    }

    /**
     * 沙巴游戏
     */
    fun getSb(game_id: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_SHA_BA)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_mobile", 1)
            .params("game_id", game_id)
            .subscribe(subscriber)
    }

    /**
     * IM
     */
    fun getIM(game_id: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_IM)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_mobile", 1)
            .params("game_id", game_id)
            .subscribe(subscriber)
    }

    /**
     * BING BING
     */
    fun getBing(game_type: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(BING_BING)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_mobile", 1)
            .params("game_type", game_type)
            .subscribe(subscriber)
    }

    /**
     * AE
     */
    fun getAe(game_id: String, game_type: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(AE)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_mobile", 1)
            .params("game_id", game_id)
            .params("game_type", game_type)
            .subscribe(subscriber)
    }

    /**
     * Cmd
     */
    fun getCmd(game_id: String, game_type: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(CMD)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_mobile", 1)
            .params("game_id", game_id)
            .params("game_type", game_type)
            .subscribe(subscriber)
    }


    /**
     * Sbo
     */
    fun getSbo(game_id: String, game_type: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(SBO)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_mobile", 1)
            .params("game_id", game_id)
            .params("game_type", game_type)
            .subscribe(subscriber)
    }


    /**
     * 开元棋牌
     */
    fun getKy(game_id: String, function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_KY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .subscribe(subscriber)
    }

    /**
     * Ag捕鱼
     */
    fun getAgHunter(function: ApiSubscriber<Game060>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Game060>() {}
        subscriber.function()
        getApiOther()
            .get<Game060>(GAME_AG_HUNTER)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     * 棋牌历史 记录
     */
    fun getChessHis(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameChess>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameChess>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameChess>>(GAME_CHESS_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }

    /**
     * AG视讯 记录
     */
    fun getAgLive(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAgLive>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAgLive>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAgLive>>(GAME_AG_LIVE_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * AG游戏 记录
     */
    fun getAgGame(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_AG_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * AG游戏 记录
     */
    fun getAgFishGame(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_AG_FISH_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * BG视讯 记录
     */
    fun getBgLiveHistory(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_BG_LIVE_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * BG游戏 记录
     */
    fun getBgGameHistory(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_BG_FISH_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }

    /**
     * 开元棋牌游戏 记录
     */
    fun getKyqpGameHistory(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_KYQI_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }

    /**
     * 沙巴体育游戏 记录
     */
    fun getSbtyGameHistory(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_SBTY_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * Im体育游戏 记录
     */
    fun getImtyGameHistory(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        getApiOther()
            .get<List<GameAg>>(GAME_IMTY_HISTORY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_id", game_id)
            .params("st", st)
            .params("et", et)
            .params("page", page)
            .params("limit", 20)
            .subscribe(subscriber)
    }


    /**
     * 1 bbty 2 bbsx 3 ae 4 mg 5 cmd 6sbo
     */
    fun getNewGameHistory(
        game_id: String,
        st: String,
        et: String,
        page: Int,
        index: Int,
        type: String,
        game_name: String = "",
        tag: String = "",
        function: ApiSubscriber<List<GameAg>>.() -> Unit
    ) {
        val url = when (index) {
            1 -> GAME_BBTY_HISTORY
            2 -> GAME_BBSX_HISTORY
            3 -> GAME_AESX_HISTORY
            4 -> GAME_MG_HISTORY
            5 -> GAME_CMD_HISTORY
            6 -> GAME_SBO_HISTORY
            else -> GAME_BBTY_HISTORY
        }
        val subscriber = object : ApiSubscriber<List<GameAg>>() {}
        subscriber.function()
        val state = getApiOther().get<List<GameAg>>(url)
        state.headers("Authorization", UserInfoSp.getTokenWithBearer())
        state.params("game_id", game_id)
        if (index == 6) {
            state.params("game_name", game_name)
            state.params("tag", tag)
        }
        state.params("st", st)
        state.params("et", et)
        state.params("page", page)
        state.params("game_type", type)
        state.params("limit", 20)
        state.subscribe(subscriber)
    }
}