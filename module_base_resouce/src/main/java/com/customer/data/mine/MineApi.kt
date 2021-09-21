package com.customer.data.mine

import android.text.TextUtils
import com.customer.AppConstant
import com.customer.data.MineUserDiamond
import com.customer.data.UserInfoSp
import com.customer.utils.AESUtils
import com.google.gson.Gson
import com.rxnetgo.rxcache.CacheMode
import cuntomer.api.AllEmptySubscriber
import cuntomer.api.AllSubscriber
import cuntomer.api.ApiSubscriber
import cuntomer.api.EmptySubscriber
import cuntomer.bean.BaseApiBean
import cuntomer.net.BaseApi

/**
 *
 * @ Author  QinTian
 * @ Date  2020-02-13
 * @ Describe
 *
 */
object MineApi : BaseApi {


    //用户信息
    private const val USER_INFO = "index/index"

    //修改用户信息
    private const val USER_INFO_EDIT = "index/edit"

    //上传头像
    const val USER_UPLOAD_AVATAR = "index/upload-avatar"

    //意见反馈
    private const val MINE_FEED_BACK = "api/v2/user/user_feedback"

    //获取皮肤列表/详情
    private const val MINE_THEM_SKIN = "api/common/get_skin_list/"

    //查询Vip等级
//    private const val MINE_CHECK_VIP = "api/v2/user/vip_now/"

    //获取余额
    private const val USER_BALANCE = "index/balance"

    //获取钻石
    private const val USER_DIAMOND = "api/v2/user/diamond_now/"

    //是否设置支付密码
    private const val USER_IS_SET_PAY_PASS = "index/check-fund-password"

    //验证支付密码
    private const val MINE_VERIFY_PASS_WORD = "index/verify-fund-password"

    //兑换钻石
    private const val USER_EXCHANGE_DIAMOND = "api/v2/user/exchange_diamond/"

    //获取支付列表
    private const val PAY_TYPE_LIST = "api/v2/Recharge/getList"

    //银行卡列表
    private const val BANK_LIST = "index/bank-list"

    //绑定银行卡
    private const val USER_BIND_CARD = "index/bind-card/"

    //用户银行卡列表
    private const val USER_BANK_LIST = "index/user-card-list/"

    //用户提现
    private const val USER_DEPOAIT = "api/v2/withdraws/depositBalance"

    //提现限额
    private const val USER_DESPORT_LIMIT = "api/v2/withdraws/depositlimit/"

    //关注列表 用户 主播
    private const val USER_ATTENTION = "api/v2/user/User_follow_list/"

    //关注列表 专家
    private const val USER_ATTENTION_EXPERT = "plan/follow-list"

    //余额记录
    private const val USER_BALANCE_LIST = "api/v2/Recharge/accountChange"

    //打赏记录
    private const val USER_PAY_LIST = "api/v2/user/user_reward_list/"

    //兑换记录
    private const val USER_CHANGE_LIST = "api/v2/user/diamond_list/"

    //投注记录
    private const val USER_BET_LIST = "quiz/change_log"

    //消息中心
    private const val USER_MESSAGE_CENTER = "api/v2/live/get_notice/"

    //消息中心代理
    private const val USER_MESSAGE_CENTER_DL = " api/v2/live/get_notice_dl/"

    //消息列表
    private const val USER_MESSAGE_LIST = "/api/v2/live/get_message_list/"

    //消息详情
    private const val USER_MESSAGE_INFO = "api/v2/live/get_message/"

    //获取新消息通知
    private const val USER_MESSAGE_NEW = "/api/v2/live/get_message_count/"

    //获取某个主播动态
    private const val ANCHOR_LIST = "api/v2/live/get_dynamic/"

    //修改支付密码
    private const val HOME_LIVE_RED_SET_PASS = "index/set-fund-password/"

    //修改密码
    private const val USER_MODIFY_PASSWORD = "index/reset-password"

    // 验证码修改  登录/支付密码接口
    private const val SETTING_PASSWORD = "index/sms-reset-pwd"

    //热门讨论列表 - 单个
    private const val HOT_DISCUSS = "article/index"

    //官方联系
    private const val CONTENT_GROUP = "api/v2/live/contact/"

    //卡密充值
    private const val RECHARGE_CARD = "api/v2/Recharge/code_recharge"


    //获取卡商列表
    private const val CARD_LIST = "api/v2/Recharge/getcardlist"

    //团队报表
    private const val TEAM_REPORT = "market/team-count"

    //团队报表 最新数据
    private const val TEAM_REPORT_LAST = "market/latest-count"

    //游戏报表
    private const val GAME_REPORT_LAST = "guess/report"

    //彩票游戏详情
    private const val GAME_LOTTERY_INFO = "guess/lottery-detail-count"

    //彩票游戏
    private const val GAME_LOTTERY = "guess/lottery-count"

    //棋牌游戏
    private const val GAME_CHESS = "fhchess/count"

    //棋牌详情
    private const val GAME_CHESS_INFO = "fhchess/detail-count"

    //AG视讯
    private const val GAME_AGSX = "ag/live-count"

    //AG视讯详情
    private const val GAME_AGSX_INFO = "ag/live-detail-count"

    //BG视讯
    private const val GAME_BG_SX = "bg/live-count"

    //BG视讯详情
    private const val GAME_BG_SX_INFO = "bg/live-detail-count"

    //BG捕鱼
    private const val GAME_BG_FISH = "bg/fishing-count"

    //开源棋牌
    private const val GAME_KYQP = "ky/count"

    //沙巴体育
    private const val GAME_SBTY = "ibc/count"

    //IM体育
    private const val GAME_IMTY = "im/count"

    //BBIN体育
    private const val GAME_BBTY = "bbin/count"

    //BBIN视讯
    private const val GAME_BBSX = "bbin/count"

    //AE视讯
    private const val GAME_AESX = "ae/count"

    //MG电子
    private const val GAME_MGDZ = "mg/count"

    //cmd体育
    private const val GAME_CMD = "cmd/count"

    //sbo体育
    private const val GAME_SBO = "sbo/count"

    //BG捕鱼详情
    private const val GAME_BG_FISH_INFO = "bg/fishing-detail-count"

    //开源棋牌详情
    private const val GAME_KYQI_INFO = "ky/detail-count"

    //沙巴体育详情
    private const val GAME_SBTY_INFO = "ibc/detail-count"

    //AG 捕鱼
    private const val GAME_AGBY_INFO = "ag/hunter-detail-count"

    //IM体育
    private const val GAME_IM_INFO = "im/detail-count"

    //BBIN体育
    private const val GAME_IM_INFO_BBINTY = "bbin/detail-count"

    //BBIN视讯
    private const val GAME_IM_INFO_BBINSX = "bbin/detail-count"

    //AE视讯
    private const val GAME_IM_INFO_AE = "ae/detail-count"

    //MG电子
    private const val GAME_IM_INFO_MG = "mg/detail-count"

    //CMD体育
    private const val GAME_IM_INFO_CMD = "cmd/detail-count"

    //SBO体育
    private const val GAME_IM_INFO_SBO = "sbo/detail-count"

    //AG电子
    private const val GAME_AGDZ = "ag/slot-count"

    //AG捕鱼
    private const val GAME_AGBY = "ag/hunter-count"


    //AG电子详情
    private const val GAME_AGDZ_INFO = "ag/slot-detail-count"

    //推广码
    private const val GET_CODE = "market/index"

    //申请推广码
    private const val SUPPORT_REPORT_CODE = "market/apply"

    //推广级别列表
    private const val LEVEL_LIST = "market/level-list"

    //会员报表
    private const val VIP_LEVEL = "market/member-count"

    //获取验证码
    private const val GET_CODE_SMS = "reg/send-sms"

    //购彩
    private const val LOTTERY_URL = "api/v2/user/jump_to/"

    //投注记录
    private const val LOTTERY_BET_HISTORY = "guess/play_bet_history"

    //第三方平台
    private const val THIRD = "user/third-platform"

    //是否自动转账
    private const val AUTO_CHANGE = "platform/auto-transfer"

    //棋牌余额
    private const val CHESS_MONEY = "fhchess/balance"

    //AG余额
    private const val AG_MONEY = "ag/balance"

    //BG余额
    private const val BG_MONEY = "bg/balance"

    //开元棋牌余额
    private const val KYQP_MONEY = "ky/balance"

    //沙巴体育余额
    private const val SBTY_MONEY = "ibc/balance"

    //IM体育余额
    private const val IM_MONEY = "im/balance"

    //BBINN余额
    private const val BBIN_MONEY = "bbin/balance"

    //AE余额
    private const val AE_MONEY = "ae/balance"

    //mg余额
    private const val MG_MONEY = "mg/balance"

    //CMD余额
    private const val CMD_MONEY = "cmd/balance"

    //sbo余额
    private const val SBO_MONEY = "sbo/balance"

    //棋牌上分
    private const val CHESS_UP = "fhchess/transfer-out"

    //棋牌下分
    private const val CHESS_DOWN = "fhchess/transfer-in"

    //AG上分
    private const val AG_UP = "ag/transfer-out"

    //AG下分
    private const val AG_DOWN = "ag/transfer-in"

    //BG上分
    private const val BG_UP = "bg/transfer-out"

    //BG下分
    private const val BG_DOWN = "bg/transfer-in"

    //开元棋牌上分
    private const val KY_UP = "ky/transfer-out"

    //开元棋牌下分
    private const val KY_DOWN = "ky/transfer-in"

    //沙巴上分
    private const val SB_UP = "ibc/transfer-out"

    //沙巴下分
    private const val SB_DOWN = "ibc/transfer-in"

    //IM上分
    private const val IM_UP = "im/transfer-out"

    //IM下分
    private const val IM_DOWN = "im/transfer-in"

    //bin 上
    private const val BING_UP = "bbin/transfer-out"

    //bin 下
    private const val BING_DOWN = "bbin/transfer-in"

    //ae 上
    private const val AE_UP = "ae/transfer-out"

    //ae 下
    private const val AE_DOWN = "ae/transfer-in"


    //mg 上
    private const val MG_UP = "mg/transfer-out"

    //mg 下
    private const val MG_DOWN = "mg/transfer-in"


    //cmd 上
    private const val CMD_UP = "cmd/transfer-out"

    //cmd 下
    private const val CMD_DOWN = "cmd/transfer-in"

    //sbo 上
    private const val SBO_UP = "sbo/transfer-out"

    //sbo 下
    private const val SBO_DOWN = "sbo/transfer-in"

    //一键回收
    private const val RECYCLE_ALL = "platform/transfer-in-all"

    //银行卡充值列表
    private const val BANK_CARD = "api/v2/Recharge/bankList"

    //银行卡充值
    private const val BANK_CARD_RECHARGE = "api/v2/Recharge/bank_recharge"

    //获取常用卡号列表
    private const val USER_BANK_CARD = "api/v2/Recharge/get_commonno"

    //添加常用卡号
    private const val USER_ADD_BANK_CARD = "api/v2/Recharge/add_commonno"

    //删除常用卡号
    private const val USER_DEL_BANK_CARD = "api/v2/Recharge/del_commonno"

    //设置常用
    private const val USER_SET_CARD = "api/v2/Recharge/set_commonno"

    //平台间转账
    private const val PLAT_TRANSFER = "platform/transfer"

    //扫码登录(判断是否过期)
    private const val SCAN_LOGIN = "v2/login/scan"

    //VIP卡片
    private const val VIP_CARD_INFO = "api/v2/user/get_vip/"


    //VIP礼物
    private const val VIP_GIFT = "api/v2/user/get_gift_pack/"

    //VIP详情
    private const val VIP_INFO = "api/v2/user/get_vip_detail/"

    //贵族卡片
    private const val NOBLE_CARD = "api/v2/user/get_noble/"

    //用户回电
    private const val CALL_BACK = "api/v2/user/call_back/"

    // 问题反馈类型
    private const val QUESTION_TYPE = "api/v2/user/feedback_type_list/"

    //用户回电
    private const val LANGUAGE_TYPE = "api/v2/user/language_list/"

    //VIP礼包领取
    private const val VIP_GIFT_GET = "api/v2/user/rec_gift/"

    //充值教程
    private const val TEACH_RECHARGE = "api/v2/Recharge/get_teach_list"

    //USD充值
    private const val RECHARGE_USD = "api/v2/Recharge/index"

    //获取虚拟钱包
    private const val USDT = "api/v2/Recharge/get_vrwallet_list"

    //删除虚拟钱包
    private const val DEL_USDT = "api/v2/Recharge/del_vrwallet"

    //设置虚拟钱包
    private const val SET_USDT = "api/v2/Recharge/set_vr_wallet"

    //提现费用
    private const val DESPOT_FEE = "api/v2/withdraws/user_deposit_fee/"

    //手动充值USDT
    private const val HAND_RECHARGE = "api/v2/Recharge/get_usdt_list"

    //虚拟币手工充值
    private const val HAND_RECHARGE_BY_USER = "api/v2/Recharge/currency_recharge"

    //微信充值信息
    private const val WECHAT_CHARGE_INFO = "api/v2/recharge/get_wechat_list"

    //微信充值
    private const val WECHAT_CHARGE = "api/v2/Recharge/wechat_recharge"

    /**
     * 获取用户信息
     */
    fun getUserInfo(function: ApiSubscriber<MineUserInfoResponse>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserInfoResponse>() {}
        subscriber.function()
        getApiOther().get<MineUserInfoResponse>(USER_INFO)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     * 修改个人资料
     */
    fun upLoadPersonalInfo(
        nickname: String,
        gender: Int,
        profile: String,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(USER_INFO_EDIT).isMultipart(true)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("nickname", nickname)
            .params("gender", gender)
            .params("profile", profile)
            .subscribe(subscriber)
    }

    /**
     * 反馈意见
     */

    fun feedBack(
        text: String,
        phone: String,
        qq: String,
        email: String,
        function: ApiSubscriber<BaseApiBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().get<BaseApiBean>(MINE_FEED_BACK)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("text", text)
            .params("phone", phone)
            .params("qq", qq)
            .params("email", email)
            .subscribe(subscriber)
    }

    /**
     * 获取皮肤列表
     */
    fun getThemSKin(
        id: String = "",
        page: Int = 1,
        limit: String = "10",
        function: ApiSubscriber<List<MineThemSkinResponse>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineThemSkinResponse>>() {}
        subscriber.function()
        getApi().get<List<MineThemSkinResponse>>(MINE_THEM_SKIN)
            .params("id", id)
            .params("page", page)
            .params("limit", limit)
            .subscribe(subscriber)
    }

    /**
     * 获取皮肤详情
     */
    fun getThemSKinInfo(
        id: String = "",
        function: ApiSubscriber<MineThemSkinInfoResponse>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineThemSkinInfoResponse>() {}
        subscriber.function()
        getApi().get<MineThemSkinInfoResponse>(MINE_THEM_SKIN)
            .params("id", id)
            .subscribe(subscriber)
    }

    /**
     * 查询Vip等级
     */
//    fun getUserVip(function: ApiSubscriber<MineUserVipType>.() -> Unit) {
//        val subscriber = object : ApiSubscriber<MineUserVipType>() {}
//        subscriber.function()
//        getApi().get<MineUserVipType>(MINE_CHECK_VIP)
//            .headers("token", UserInfoSp.getToken())
//            .params("user_id", UserInfoSp.getUserId())
//            .subscribe(subscriber)
//    }

    /**
     * 查询余额
     */
    fun getUserBalance(function: ApiSubscriber<MineUserBalance>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserBalance>() {}
        subscriber.function()
        getApiOther().get<MineUserBalance>(USER_BALANCE)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     * 查询钻石
     */
    fun getUserDiamond(function: ApiSubscriber<MineUserDiamond>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineUserDiamond>() {}
        subscriber.function()
        getApi().get<MineUserDiamond>(USER_DIAMOND)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .subscribe(subscriber)
    }

    /**
     * 是否设置支付密码
     */
    fun getIsSetPayPass(function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().get<String>(USER_IS_SET_PAY_PASS)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 验证支付密码
     */
    fun verifyPayPass(passWord: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(MINE_VERIFY_PASS_WORD)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("password", passWord)
            .subscribe(subscriber)
    }

    /**
     * 兑换钻石
     */
    fun getUserChangeDiamond(
        diamond: String,
        password: String,
        function: ApiSubscriber<MineUserDiamond>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineUserDiamond>() {}
        subscriber.function()
        getApi().post<MineUserDiamond>(USER_EXCHANGE_DIAMOND)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("diamond", diamond)
            .params("password", password)
            .subscribe(subscriber)
    }

    /**
     * 获取 支付通道列表
     */
    fun getPayTypeList(function: ApiSubscriber<List<MinePayTypeList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MinePayTypeList>>() {}
        subscriber.function()
        getApi().post<List<MinePayTypeList>>(PAY_TYPE_LIST)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * 获取 银行卡列表
     */
    fun getBankList(function: ApiSubscriber<List<MineBankList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineBankList>>() {}
        subscriber.function()
        getApiOther().get<List<MineBankList>>(BANK_LIST)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     *绑定银行卡
     */
    fun bingBankCard(
        bank_code: String,
        province: String,
        city: String,
        branch: String,
        realname: String,
        card_num: String,
        fund_password: String,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(USER_BIND_CARD)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("bank_code", bank_code)
            .params("province", province)
            .params("city", city)
            .params("branch", branch)
            .params("realname", realname)
            .params("card_num", card_num)
            .params("fund_password", fund_password)
            .subscribe(subscriber)
    }


    /**
     * 获取 用户绑定的银行卡列表
     */
    fun getUserBankList(function: ApiSubscriber<List<MineUserBankList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineUserBankList>>() {}
        subscriber.function()
        getApiOther().get<List<MineUserBankList>>(USER_BANK_LIST)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     * 用户 提现
     */
    fun userGetCashOut(
        amount: Double,
        account: String,
        passWord: String,
        type: Int,
        is_all: Int,
        function: AllSubscriber.() -> Unit
    ) {
        val subscriber = AllSubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["pass"] = passWord.trim()
        map["type"] = type
        map["is_all"] = is_all
        map["account"] = account.trim()
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApi()
                .post<BaseApiBean>(USER_DEPOAIT).isMultipart(true)
                .headers("token", UserInfoSp.getToken())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }


    fun getMoneyLimit(function: ApiSubscriber<Limit>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Limit>() {}
        subscriber.function()
        getApi().post<Limit>(USER_DESPORT_LIMIT)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * 获取 Tpay充值订单URL
     */
    fun getToPayUrl(
        amount: Float,
        channels_id: Int,
        url: String,
        function: ApiSubscriber<MinePayUrl>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MinePayUrl>() {}
        subscriber.function()
        getApi().post<MinePayUrl>(url)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("amount", amount)
            .params("channels_id", channels_id)
            .params("returnurl", "t1")
            .subscribe(subscriber)
    }

    /**
     * 获取 关注列表  用户 主播
     */
    fun getAttentionList(
        type: String,
        page: Int,
        function: ApiSubscriber<List<MineUserAttentionBean>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineUserAttentionBean>>() {}
        subscriber.function()
        getApi().get<List<MineUserAttentionBean>>(USER_ATTENTION)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("type", type)
            .params("page", page)
            .params("limit", "10")
            .subscribe(subscriber)
    }

    /**
     * 获取 关注列表  专家
     */
    fun getAttentionList(page: Int, function: ApiSubscriber<List<MineExpertBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineExpertBean>>() {}
        subscriber.function()
        getApiLottery().get<List<MineExpertBean>>(USER_ATTENTION_EXPERT)
            .headers("token", UserInfoSp.getTokenWithBearer())
            .params("user_id", UserInfoSp.getUserId())
            .params("page", page)
            .params("limit", "10")
            .subscribe(subscriber)
    }

    /**
     * 余额记录
     */
    fun getBalance(page: Int, function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        getApi().get<BaseApiBean>(USER_BALANCE_LIST)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("page", page)
            .params("is_bl_play", 1)
            .params("limit", "30")
            .subscribe(subscriber)
    }

    /**
     * 获取某个主播动态
     */
    fun getAnchorMoments(
        dynamic_id: String,
        function: ApiSubscriber<List<MomentsAnchorListResponse>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MomentsAnchorListResponse>>() {}
        subscriber.function()
        getApi()
            .get<List<MomentsAnchorListResponse>>(ANCHOR_LIST)
            .cacheMode(CacheMode.NONE)
            .params("user_id", UserInfoSp.getUserId())
            .params("dynamic_id", dynamic_id)
            .subscribe(subscriber)
    }

    /**
     * 获取热门讨论列表
     */
    fun getHotDiscussSingle(
        article_id: String,
        function: ApiSubscriber<MomentsHotDiscussResponse>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MomentsHotDiscussResponse>() {}
        subscriber.function()
        getApiLottery()
            .get<MomentsHotDiscussResponse>(HOT_DISCUSS)
            .cacheMode(CacheMode.NONE)
            .params("user_id", UserInfoSp.getUserId())
            .params("article_id", article_id)
            .subscribe(subscriber)
    }

    /**
     * 打赏记录
     */
    fun getReward(page: Int, function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        getApi().get<BaseApiBean>(USER_PAY_LIST)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("page", page)
            .params("limit", "100")
            .subscribe(subscriber)
    }

    /**
     * 兑换记录
     */
    fun getChange(page: Int, function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        getApi().get<BaseApiBean>(USER_CHANGE_LIST)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("page", page)
            .params("limit", "100")
            .subscribe(subscriber)
    }


    /**
     * 投注记录
     */
    fun betRecord(page: Int, is_bl_play: String = "0", function: AllSubscriber.() -> Unit) {
        val subscriber = AllSubscriber()
        subscriber.function()
        getApiLottery().get<BaseApiBean>(USER_BET_LIST)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("page", page)
            .params("limit", "100")
            .params("is_bl_play", is_bl_play)
            .subscribe(subscriber)
    }


    /**
     * 消息记录
     */
    fun getMessageTips(
        msg_type: String,
        function: ApiSubscriber<List<MineMessageCenter>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineMessageCenter>>() {}
        subscriber.function()
        getApi().get<List<MineMessageCenter>>(if (AppConstant.isMain) USER_MESSAGE_CENTER else USER_MESSAGE_CENTER_DL)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("msg_type", msg_type)

            .subscribe(subscriber)
    }

    /**
     * 消息列表
     */
    fun getMessageList(
        msg_type: String,
        page: Int,
        function: ApiSubscriber<List<MineMessageNew>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineMessageNew>>() {}
        subscriber.function()
        getApi().get<List<MineMessageNew>>(USER_MESSAGE_LIST)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("msg_type", msg_type)
            .params("client_type", 1)
            .params("api", if (AppConstant.isMain) 1 else 5)
            .params("page", page)
            .params("limit", 10)
            .subscribe(subscriber)
    }

    /**
     * 消息详情
     */
    fun getMessageInfo(
        msg_id: String,
        isDel: Boolean = false,
        function: ApiSubscriber<List<MineMessageNew>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineMessageNew>>() {}
        subscriber.function()
        val param = getApi().get<List<MineMessageNew>>(USER_MESSAGE_INFO)
        param.headers("token", UserInfoSp.getToken())
        param.params("msg_id", msg_id)
        if (isDel) param.params("del", 1)
        param.subscribe(subscriber)
    }

    /**
     * 消息详情
     */
    fun getMessageInfoWeb(
        msg_id: String,
        isDel: Boolean = false,
        function: ApiSubscriber<MineMessageNew>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineMessageNew>() {}
        subscriber.function()
        val param = getApi().get<MineMessageNew>(USER_MESSAGE_INFO)
        param.headers("token", UserInfoSp.getToken())
        param.params("msg_id", msg_id)
        if (isDel) param.params("del", 1)
        param.subscribe(subscriber)
    }

    /**
     * 是否有新消息
     */
    fun getIsNewMessage(function: ApiSubscriber<MineNewMsg>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineNewMsg>() {}
        subscriber.function()
        getApi().get<MineNewMsg>(USER_MESSAGE_NEW)
            .headers("token", UserInfoSp.getToken())
            .params("user_id", UserInfoSp.getUserId())
            .params("client_type", 1)
            .params("api", if (AppConstant.isMain) 1 else 5)
            .subscribe(subscriber)
    }


    /**
     * 官方联系
     */
    fun getContentGroup(function: ApiSubscriber<List<MineGroup>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineGroup>>() {}
        subscriber.function()
        getApi().get<List<MineGroup>>(CONTENT_GROUP)
            .subscribe(subscriber)
    }


    /**
     * 设置支付密码
     * oldPassword= 老密码（选填） newPassword=新密码  newPasswordRepeat=确认新密码
     */
    fun getSettingPayPassword(
        oldPassword: String,
        newPassword: String,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(HOME_LIVE_RED_SET_PASS)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("old_password", oldPassword)
            .params("new_password", newPassword)
//                .params("new_password_repeat", newPasswordRepeat)
            .subscribe(subscriber)
    }


    /**
     * 修改密码
     */

    fun modifyPassWord(
        old_password: String,
        new_password: String,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(USER_MODIFY_PASSWORD)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("old_password", old_password)
            .params("new_password", new_password)
            .subscribe(subscriber)
    }


    /**
     * 验证码修改  登录/支付密码接口
     */
    fun modifyPassWord(
        phone: String,
        captcha: String,
        new_pwd: String,
        type: Int,
        function: EmptySubscriber.() -> Unit
    ) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(SETTING_PASSWORD)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("phone", phone)
            .params("captcha", captcha)
            .params("new_pwd", new_pwd)
            .params("type", type)
            .subscribe(subscriber)
    }

    /**
     * 卡密充值
     */
    fun cardRecharge(code: String, pass: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().post<BaseApiBean>(RECHARGE_CARD)
            .headers("token", UserInfoSp.getToken())
            .params("code", code)
            .params("pass", pass)
            .subscribe(subscriber)
    }

    /**
     * 获取卡商列表
     */
    fun cardList(function: ApiSubscriber<List<MineRechargeDiamond>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineRechargeDiamond>>() {}
        subscriber.function()
        getApi().get<List<MineRechargeDiamond>>(CARD_LIST)
            .subscribe(subscriber)
    }

    /**
     * 团队报表
     */
    fun getTeamReport(
        start: String = "",
        end: String = "",
        function: ApiSubscriber<MineTeamReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineTeamReport>() {}
        subscriber.function()
        getApiOther().get<MineTeamReport>(TEAM_REPORT)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 团队报表最新数据
     */
    fun getTeamReportLast(range: String, function: ApiSubscriber<MineTeamReportLast>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineTeamReportLast>() {}
        subscriber.function()
        getApiOther().get<MineTeamReportLast>(TEAM_REPORT_LAST)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("range", range)
            .subscribe(subscriber)
    }

    /**
     * 推广码
     */
    fun getCode(function: ApiSubscriber<MineReportCode>.() -> Unit) {
        val subscriber = object : ApiSubscriber<MineReportCode>() {}
        subscriber.function()
        getApiOther().get<MineReportCode>(GET_CODE)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 申请推广码
     */
    fun supportCode(qq: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["qq"] = qq
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr() ?: "", Gson().toJson(map))?.let {
            getApiOther().post<String>(SUPPORT_REPORT_CODE).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * 推广级别列表
     */
    fun getLevelList(function: ApiSubscriber<List<MineLevelList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<MineLevelList>>() {}
        subscriber.function()
        getApiOther().get<List<MineLevelList>>(LEVEL_LIST)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 会员报表
     */
    fun getVipLevel(
        sub_user_id: String = "",
        sub_nickname: String = "",
        is_sub: Int = 0,
        page: Int,
        function: ApiSubscriber<List<MineVipList>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineVipList>>() {}
        subscriber.function()
        getApiOther().get<List<MineVipList>>(VIP_LEVEL)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("sub_user_id", sub_user_id)
            .params("sub_nickname", sub_nickname)
            .params("page", page)
            .params("is_sub", is_sub)
            .params("limit", 10)
            .subscribe(subscriber)
    }

    /**
     * 游戏报表
     */
    fun getGameReportLast(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiLottery().get<MineGameReport>(GAME_REPORT_LAST)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 彩票游戏详情
     */
    fun getGameLottery(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiLottery().get<MineGameReport>(GAME_LOTTERY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 棋牌
     */
    fun getGamecChess(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_CHESS)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * AG视讯
     */
    fun getGameAgSx(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_AGSX)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * AG电子
     */
    fun getGameAgDz(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_AGDZ)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * AG捕鱼
     */
    fun getGameAgBy(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_AGBY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * Bg视讯
     */
    fun getGameBgSx(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_BG_SX)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * Bg捕鱼
     */
    fun getGameBgFish(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_BG_FISH)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 开元棋牌
     */
    fun getGameKyqp(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_KYQP)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 沙巴体育
     */
    fun getGameSbty(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_SBTY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }


    /**
     * IM体育
     */
    fun getGameImty(
        start: String,
        end: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(GAME_IMTY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 1 BBIN 2 BBin 视讯 3 AE 4 MG 5 CMD 6 SBO
     */
    fun getGameNew(
        start: String,
        end: String,
        index: Int,
        type: String,
        function: ApiSubscriber<MineGameReport>.() -> Unit
    ) {
        val url = when (index) {
            1 -> GAME_BBTY
            2 -> GAME_BBSX
            3 -> GAME_AESX
            4 -> GAME_MGDZ
            5 -> GAME_CMD
            6 -> GAME_SBO
            else -> GAME_BBTY
        }
        val subscriber = object : ApiSubscriber<MineGameReport>() {}
        subscriber.function()
        getApiOther().get<MineGameReport>(url)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("game_type", type)
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }


    /**
     * 彩票游戏详情
     */
    fun getGameLotteryInfo(
        is_bl_play: String = "0",
        start: String,
        end: String,
        function: ApiSubscriber<List<MineGameReportInfo>>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<List<MineGameReportInfo>>() {}
        subscriber.function()
        getApiLottery().get<List<MineGameReportInfo>>(GAME_LOTTERY_INFO)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("is_bl_play", is_bl_play)
            .params("st", start)
            .params("et", end)
            .subscribe(subscriber)
    }

    /**
     * 游戏详情 2 棋牌 3 AG视讯 4 AG电子 5 BG视讯 6 BG捕鱼
     */
    fun getGameInfo(
        index: Int,
        start: String,
        end: String,
        game_type: String = "",
        function: ApiSubscriber<List<MineGameAgReportInfo>>.() -> Unit
    ) {
        val url = when (index) {
            2 -> GAME_CHESS_INFO
            3 -> GAME_AGSX_INFO
            4 -> GAME_AGDZ_INFO
            5 -> GAME_BG_SX_INFO
            6 -> GAME_BG_FISH_INFO
            7 -> GAME_KYQI_INFO
            8 -> GAME_SBTY_INFO
            9 -> GAME_AGBY_INFO
            10 -> GAME_IM_INFO
            11 -> GAME_IM_INFO_BBINTY
            12 -> GAME_IM_INFO_BBINSX
            13 -> GAME_IM_INFO_AE
            14 -> GAME_IM_INFO_MG
            15 -> GAME_IM_INFO_CMD
            16 -> GAME_IM_INFO_SBO
            else -> GAME_CHESS_INFO
        }
        val subscriber = object : ApiSubscriber<List<MineGameAgReportInfo>>() {}
        subscriber.function()
        val op = getApiOther().get<List<MineGameAgReportInfo>>(url)
        op.headers("Authorization", UserInfoSp.getTokenWithBearer())
        op.params("st", start)
        op.params("et", end)
        if (game_type.isNotEmpty()) op.params("game_type", game_type)
        op.subscribe(subscriber)
    }


    /**
     * 获取验证码
     */
    fun userGetCode(phone: String, type: String, function: ApiSubscriber<RegisterCode>.() -> Unit) {
        val subscriber = object : ApiSubscriber<RegisterCode>() {}
        subscriber.function()
        getApiOther().post<RegisterCode>(GET_CODE_SMS)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("user_id", UserInfoSp.getUserId())
            .params("phone", phone)
            .params("type", type)
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
     * 投注记录
     */
    fun getLotteryBetHistory(
        play_bet_state: Int,
        is_bl_play: String = "0",
        page: Int,
        lotteryId: String = "0",
        st: String = "",
        et: String = ""
    ): ApiSubscriber<ArrayList<LotteryBetHistoryResponse>> {
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


    //关注
    private const val HOME_ATTENTION_ANCHCOR = "api/v2/live/follow/"

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


    //关注专家
    private const val FOLLOW_EXPERT = "plan/follow/"

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
     * 上传头像
     */
    fun uploadPhoto(str: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApiOther().post<BaseApiBean>(USER_UPLOAD_AVATAR)
            .isMultipart(true)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("avatar", "data:image/png;base64,$str")
            .subscribe(subscriber)
    }


    /**
     * 第三方平台
     */
    fun getThird(function: ApiSubscriber<ArrayList<Third>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<Third>>() {}
        subscriber.function()
        getApiOther().get<ArrayList<Third>>(THIRD)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 棋牌余额
     */
    fun getChessMoney(function: ApiSubscriber<AgMoney>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(CHESS_MONEY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * AG余额
     */
    fun getAgMoney(function: ApiSubscriber<AgMoney>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(AG_MONEY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * BG余额
     */
    fun getBgMoney(function: ApiSubscriber<AgMoney>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(BG_MONEY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 开元棋牌余额
     */
    fun getKyMoney(function: ApiSubscriber<AgMoney>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(KYQP_MONEY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 沙巴体育余额
     */
    fun getSbMoney(function: ApiSubscriber<AgMoney>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(SBTY_MONEY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * IM体育余额
     */
    fun getImMoney(function: ApiSubscriber<AgMoney>.() -> Unit) {
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(IM_MONEY)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     *  余额   1 BBIN  2 AE  3 MG  4 CMD  5 SBO
     */
    fun getNewMoney(index: Int, function: ApiSubscriber<AgMoney>.() -> Unit) {
        val url: String = when (index) {
            1 -> BBIN_MONEY
            2 -> AE_MONEY
            3 -> MG_MONEY
            4 -> CMD_MONEY
            5 -> SBO_MONEY
            else -> BBIN_MONEY
        }
        val subscriber = object : ApiSubscriber<AgMoney>() {}
        subscriber.function()
        getApiOther().get<AgMoney>(url)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }


    /**
     * AG 上分 下分
     */
    fun getAgMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(if (upOrDown) AG_UP else AG_DOWN).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * BG 上分 下分
     */
    fun getBgMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(if (upOrDown) BG_UP else BG_DOWN).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * 开元棋牌 上分 下分
     */
    fun getKyMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(if (upOrDown) KY_UP else KY_DOWN).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }


    /**
     * 沙巴游戏 上分 下分
     */
    fun getSbMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(if (upOrDown) SB_UP else SB_DOWN).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * Im游戏 上分 下分
     */
    fun getImMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(if (upOrDown) IM_UP else IM_DOWN).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     *  7 bbin 8 ae 9 mg 10 cmd 11 sbo
     */
    fun getNewMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        index: Int,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val url = if (upOrDown) {
            when (index) {
                7 -> BING_UP
                8 -> AE_UP
                9 -> MG_UP
                10 -> CMD_UP
                11 -> SBO_UP
                else -> ""
            }
        } else {
            when (index) {
                7 -> BING_DOWN
                8 -> AE_DOWN
                9 -> MG_DOWN
                10 -> CMD_DOWN
                11 -> SBO_DOWN
                else -> ""
            }
        }
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(url).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * 棋牌 上分 下分
     */
    fun getChessMoneyUpOrDown(
        upOrDown: Boolean,
        amount: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(if (upOrDown) CHESS_UP else CHESS_DOWN).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }
    }

    /**
     * 一键回收
     */
    fun recycleAll(function: AllEmptySubscriber.() -> Unit) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(RECYCLE_ALL)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 平台间转账
     */
    fun platFormTrans(
        amount: String,
        plateOut: String,
        plateIn: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        val map = hashMapOf<String, Any>()
        map["amount"] = amount
        map["out"] = plateOut
        map["in"] = plateIn
        map["timestamp"] = System.currentTimeMillis()
        AESUtils.encrypt(UserInfoSp.getRandomStr(), Gson().toJson(map))?.let {
            getApiOther().post<String>(PLAT_TRANSFER).isMultipart(true)
                .headers("Authorization", UserInfoSp.getTokenWithBearer())
                .params("datas", it)
                .subscribe(subscriber)
        }


    }

    /**
     * 银行卡充值列表
     */
    fun getBankCard(function: ApiSubscriber<List<BankCard>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<BankCard>>() {}
        subscriber.function()
        getApi().post<List<BankCard>>(BANK_CARD)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)

    }

    /**
     * 银行卡充值
     */
    fun getBankCardRecharge(
        bank_id: String,
        pay_user: String,
        pay_no: String,
        pay_amount: String,
        pay_time: String,
        function: AllEmptySubscriber.() -> Unit
    ) {
        val subscriber = AllEmptySubscriber()
        subscriber.function()
        getApi().post<String>(BANK_CARD_RECHARGE)
            .headers("token", UserInfoSp.getToken())
            .params("bank_id", bank_id)
            .params("pay_user", pay_user)
            .params("pay_no", pay_no)
            .params("pay_amount", pay_amount)
            .params("pay_time", pay_time)
            .subscribe(subscriber)

    }

    /**
     * 常用卡号
     */
    fun getUserBank(type: Int = 1, function: ApiSubscriber<List<UserBankCard>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<UserBankCard>>() {}
        subscriber.function()
        getApi().get<List<UserBankCard>>(USER_BANK_CARD)
            .headers("token", UserInfoSp.getToken())
            .params("type", type)
            .subscribe(subscriber)
    }

    /**
     * 添加常用卡号
     */
    fun addUserBankCard(
        name: String,
        no: String,
        remark: String,
        type: Int = 1,
        function: ApiSubscriber<BaseApiBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().post<BaseApiBean>(USER_ADD_BANK_CARD)
            .headers("token", UserInfoSp.getToken())
            .params("name", name)
            .params("no", no)
            .params("remark", remark)
            .params("type", type)
            .subscribe(subscriber)
    }

    /**
     * 设置常用卡号
     * type = 1 银行卡
     * type = 2 虚拟钱包地址
     */
    fun setUserBankCardUse(
        id: String,
        type: Int,
        function: ApiSubscriber<BaseApiBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().post<BaseApiBean>(USER_SET_CARD)
            .headers("token", UserInfoSp.getToken())
            .params("id", id)
            .params("type", type)
            .subscribe(subscriber)
    }


    /**
     * 删除常用卡号
     */
    fun delUserBankCard(id: String, function: ApiSubscriber<BaseApiBean>.() -> Unit) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().post<BaseApiBean>(USER_DEL_BANK_CARD)
            .headers("token", UserInfoSp.getToken())
            .params("id", id)
            .subscribe(subscriber)
    }


    /**
     * 获取是否自动转账
     */
    fun getAutoChange(function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().get<String>(AUTO_CHANGE)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 获取是否自动转账
     */
    fun setAutoChange(function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(AUTO_CHANGE)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .subscribe(subscriber)
    }

    /**
     * 扫码登录
     */
    fun scanLogin(code: String, is_chk_exp: String = "0", function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApiOther().post<String>(SCAN_LOGIN)
            .headers("Authorization", UserInfoSp.getTokenWithBearer())
            .params("qrid", code)
            .params("is_chk_exp", is_chk_exp)
            .subscribe(subscriber)
    }

    /**
     * vip卡片页
     */
    fun getVipCardInfo(function: ApiSubscriber<VipCardInfo>.() -> Unit) {
        val subscriber = object : ApiSubscriber<VipCardInfo>() {}
        subscriber.function()
        getApi().get<VipCardInfo>(VIP_CARD_INFO)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }


    /**
     *  会员专属礼品列表
     */
    fun getVipGift(function: ApiSubscriber<VipGift>.() -> Unit) {
        val subscriber = object : ApiSubscriber<VipGift>() {}
        subscriber.function()
        getApi().get<VipGift>(VIP_GIFT)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * vip详情
     */
    fun getVipInfo(function: ApiSubscriber<VipInfo>.() -> Unit) {
        val subscriber = object : ApiSubscriber<VipInfo>() {}
        subscriber.function()
        getApi().get<VipInfo>(VIP_INFO)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * 贵族卡片
     */
    fun getNoble(function: ApiSubscriber<NobleInfo>.() -> Unit) {
        val subscriber = object : ApiSubscriber<NobleInfo>() {}
        subscriber.function()
        getApi().get<NobleInfo>(NOBLE_CARD)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    /**
     * 问题列表
     */
    fun getQuestion(function: ApiSubscriber<List<QuBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<QuBean>>() {}
        subscriber.function()
        getApi().get<List<QuBean>>(QUESTION_TYPE)
            .subscribe(subscriber)
    }

    /**
     * 语言列表
     */
    fun getLanguage(function: ApiSubscriber<List<QuBean>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<QuBean>>() {}
        subscriber.function()
        getApi().get<List<QuBean>>(LANGUAGE_TYPE)
            .subscribe(subscriber)
    }

    /**
     * 用户回电
     */
    fun getCallBack(
        str: String,
        str1: String,
        str2: String,
        str3: String,
        function: ApiSubscriber<QuBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<QuBean>() {}
        subscriber.function()
        getApi().get<QuBean>(CALL_BACK)
            .params("type_id", str)
            .params("lang_id", str1)
            .params("phone", str2)
            .params("issue_phone", str2)
            .subscribe(subscriber)
    }


    /**
     * vip礼包领取
     */
    fun getVipGift(
        pack_id: Int,
        rec_name: String,
        rec_address: String,
        rec_phone: String,
        function: ApiSubscriber<QuBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<QuBean>() {}
        subscriber.function()
        getApi().post<QuBean>(VIP_GIFT_GET)
            .headers("token", UserInfoSp.getToken())
            .params("pack_id", pack_id)
            .params("rec_name", rec_name)
            .params("rec_address", rec_address)
            .params("rec_phone", rec_phone)
            .subscribe(subscriber)
    }

    /**
     * 充值教程
     */
    fun rechargeTeach(function: ApiSubscriber<List<Teach>>.() -> Unit){
        val subscriber = object : ApiSubscriber<List<Teach>>() {}
        subscriber.function()
        getApi().get<List<Teach>>(TEACH_RECHARGE)
            .params("client_type", 1)
            .subscribe(subscriber)
    }


    /**
     * USD充值
     */
    fun rechargeUsd(
        amount: String,
        channels_id: String,
        currency_type: String,
        protocl: String, function: ApiSubscriber<USDCharge>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<USDCharge>() {}
        subscriber.function()
        getApi().post<USDCharge>(RECHARGE_USD)
            .headers("token", UserInfoSp.getToken())
            .params("amount", amount)
            .params("channels_id", channels_id)
            .params("currency_type", currency_type)
            .params("protocl", protocl)
            .subscribe(subscriber)
    }

    fun getUsdt(function: ApiSubscriber<List<USDTList>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<USDTList>>() {}
        subscriber.function()
        getApi().get<List<USDTList>>(USDT)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }


    fun delUsdt(id: String, function: EmptySubscriber.() -> Unit) {
        val subscriber = EmptySubscriber()
        subscriber.function()
        getApi().post<String>(DEL_USDT)
            .headers("token", UserInfoSp.getToken())
            .params("id", id)
            .subscribe(subscriber)
    }


    fun setUsdt(
        name: String,
        protocol: String,
        address: String,
        phone: String, code: String, function: ApiSubscriber<String>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<String>() {}
        subscriber.function()
        getApi().post<String>(SET_USDT)
            .headers("token", UserInfoSp.getToken())
            .params("name", name)
            .params("protocol", protocol)
            .params("address", address)
            .params("phone", phone)
            .params("code", code)
            .subscribe(subscriber)
    }

    fun getDesportFee(function: ApiSubscriber<Fee>.() -> Unit) {
        val subscriber = object : ApiSubscriber<Fee>() {}
        subscriber.function()
        getApi().post<Fee>(DESPOT_FEE)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    fun getHandUsdtList(function: ApiSubscriber<List<HandUsdt>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<List<HandUsdt>>() {}
        subscriber.function()
        getApi().get<List<HandUsdt>>(HAND_RECHARGE)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    fun getHandUsdtByUser(
        protocol: String,
        address: String, trade_order: String,
        amount: String, pay_time: String,
        function: ApiSubscriber<BaseApiBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().post<BaseApiBean>(HAND_RECHARGE_BY_USER)
            .headers("token", UserInfoSp.getToken())
            .params("protocol", protocol)
            .params("address", address)
            .params("trade_order", trade_order)
            .params("amount", amount)
            .params("pay_time", pay_time)
            .subscribe(subscriber)
    }

    fun getWechatInfo(function: ApiSubscriber<ArrayList<WechatInfo>>.() -> Unit) {
        val subscriber = object : ApiSubscriber<ArrayList<WechatInfo>>() {}
        subscriber.function()
        getApi().get<ArrayList<WechatInfo>>(WECHAT_CHARGE_INFO)
            .headers("token", UserInfoSp.getToken())
            .subscribe(subscriber)
    }

    fun getWechatCharge(
        wechat_id: String,
        pay_user: String,
        amount: String,
        pay_time: Long,
        function:ApiSubscriber<BaseApiBean>.() -> Unit
    ) {
        val subscriber = object : ApiSubscriber<BaseApiBean>() {}
        subscriber.function()
        getApi().post<BaseApiBean>(WECHAT_CHARGE)
            .headers("token", UserInfoSp.getToken())
            .params("wechat_id", wechat_id)
            .params("pay_user", pay_user)
            .params("amount", amount)
            .params("pay_time", pay_time)
            .subscribe(subscriber)
    }

}