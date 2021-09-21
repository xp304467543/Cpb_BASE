package com.customer.data.mine

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2020-02-13
 * @ Describe
 *
 */

// 用户信息
data class MineUserInfoResponse(
    var username: String?,
    var nickname: String?,
    var profile: String?,
    var avatar: String?,
    var gender: Int = 0,
    var noble: Int = 0,
    var phone: String?,
    var market_code: String?,
    var free_watch_nums: String,
    var sum_watch_nums: String,
    var vip: Int,
    var following: String?, var followers: String?, var like: String?
)

data class UpDateUserPhoto(var img: String)

// 获取皮肤列表
data class MineThemSkinResponse(
    var id: String?, var type: String?,
    var cover: String?, var users: String?,
    var name: String?, var isSelect: Boolean
)

//用户银行卡里列表
data class MineUserBankList(
    var id: Int, var bank_id: Int, var realname: String, var card_num: String,
    var province: String, var city: String, var bank_img: String?, var bank_name: String,var bank_img2:String?
)

data class LotteryBetHistoryResponse(
    var play_bet_time: Long?, var play_bet_lottery_id: String?, var play_bet_lottery_name: String?,
    var play_bet_issue: String?, var play_sec_id: String?, var play_sec_name: String?,
    var play_class_name: String?, var play_bet_sum: String?, var play_odds: String?,
    var play_bet_score: String?
)

//关注取关
data class Attention(var isFollow: Boolean)

//获取皮肤详情
data class MineThemSkinInfoResponse(
    var id: String?, var name: String?,
    var type: String?, var cover: String?,
    var score: String?, var price: String?,
    var des: String?, var bg_image: String?, var images: List<String>? = null
)

//获取vip等级
data class MineUserVipType(var vip: String = "0")

//用户余额
data class MineUserBalance(var balance: BigDecimal = BigDecimal(0.00))

//密码输入错误此时
data class MinePassWordTime(var remain_times: Int)

//支付通道列表
@Parcelize
data class MinePayTypeList(
    var id: Int,
    var channels_type: String,
    var low_money: String,
    var fee: String?,
    var high_money: String,
    var icon: String,
    var apiroute: String,
    var pay_type: String?,
    var reparams: String?,
    var currency_type: String?,
    var quick_list: List<Int>? = null,
    var exchange_rate: BigDecimal?,
    var tx_exchange_rate: BigDecimal?,
    var cz_exchange_rate: BigDecimal?,
    var protocol: List<String>?
) : Parcelable

//银行卡列表
data class MineBankList(var name: String, var img: String, var code: String)

//更新用户选择的银行卡
data class MineUpDateBank(var isUpdate: Boolean)

//更新余额
data class MineUpDateMoney(var money: String, var isUpdate: Boolean, var isDiamond: Boolean = false)


//支付Url
data class MinePayUrl(var url: String, var type: String, var form: String)

data class Limit(var lowmoney:BigDecimal?= BigDecimal(50), var highmoney:BigDecimal?= BigDecimal(99999))

//关注 用户、主播 bean
data class MineUserAttentionBean(
    var id: String?,
    var type: String?,
    var nickname: String?,
    var anchor_id: String?,
    var user_id: String?,
    var live_status: String?,
    var lottery_id: String??,
    var avatar: String?,
    var sign: String?
)

//关注 专家 bean
data class MineExpertBean(
    var id: String?,
    var user_id: String?,
    var expert_id: String?,
    var nickname: String?,
    var avatar: String?,
    var profile: String?,
    var created: String?
)

//余额记录
data class MineBillResponse(val content: Array<MineBillBean>)

data class MineBillBean(
    var id: String? = "",
    var date: String = "",
    var time: String = "",
    var create_time: String = "",
    var amount: String = "",
    var type: String = "",
    var get_money: String = "",
    var nickname: String = "",
    val issue: String = "",
    var giftname: String = "",
    var gift_num: String = "",
    var avatar: String = "",
    var lottery_name: String = "",
    var method_name: String = "",
    var code: String = "",
    var itemType: Int = 0,
    var title: String? = ""

)

data class MineMessageCenter(
    var msg_id: String,
    var msg_type: String,
    var content: String,
    var create_time: String,
    var createtime_txt: String,
    var media: String,
    var dynamic_id: String,
    var apiType: String,
    var comment_id: String,
    var userType: String,
    var nickname: String,
    var avatar: String
) : Serializable

//新消息提醒
data class MineNewMsg(var msgCount: Int, var countList: MineNewBean)
data class MineNewBean(
    @SerializedName("0") var `_$0`: String,
    @SerializedName("2") var `_$2`: String,
    @SerializedName("3") var `_$3`: String,
    @SerializedName("5") var `_$5`: String
)

//更新关注
data class UpDatePre(val update: Boolean)

//Lottery视频跳直播间
data class LotteryToLiveRoom(var id: String)

//换肤
data class ChangeSkin(var id: Int)

//官方群
data class MineGroup(var title: String, var icon: String, var url: String)

//钻石充值联系方式
data class MineRechargeDiamond(
    val id: String,
    val name: String,
    val quota: String,
    val contact: List<MineRechargeDiamondChild>?
)

data class MineRechargeDiamondChild(val title: String?, val value: String?, val icon: String?)

//团队报表
data class MineTeamReport(
    val invitee_num: String?,
    val recharge_user_num: String?,
    val exchange: String?,
    val recharge: String?,
    val brokerage: String?,
    val sub_brokerage: String?,
    val reg_num: String?
)

data class MineTeamReportLast(
    val invitee_num: String?,
    val recharge: String?,
    val brokerage: String?
)

data class MineGameReport(
    val amount: BigDecimal?,
    val prize: BigDecimal?,
    val count: BigDecimal?,
    val bl_amount: BigDecimal?,
    val bl_prize: BigDecimal?,
    val bl_count: BigDecimal?,
    val profit: BigDecimal?,
    val bl_profit: BigDecimal?
)

data class MineGameReportInfo(
    val lottery_id: String?,
    val lottery_name: String?,
    val amount: BigDecimal?,
    val prize: BigDecimal?,
    val count: String?,
    val lottery_icon: String?,
    val profit: BigDecimal?
)

data class MineGameAgReportInfo(
    val game_id: String?="",
    val game_name: String?,
    val amount: BigDecimal?,
    val prize: BigDecimal?,
    val count: String?,
    val img_url: String? = "",
    val profit: BigDecimal?,
    val tag: String? = "",
    val status: String? = ""
)

data class RegisterCode(var code: String)

data class MineReportCode(
    val market_id: String?,
    val user_id: String?,
    val nickname: String?,
    val market_code: String?,
    val homepage_url: String?,
    val market_url: String?,
    val status: String?,
    val status_cn: String?,
    val created_at: String?
    ,
    val reviewed_at: String?,
    val level_id: String?,
    val level: String?,
    val level_name: String?
    ,
    val next_level: String?,
    val next_level_name: String?,
    val next_level_invitee_num: Double?,
    val next_level_diff: String?
    ,
    val invitee_num: Double?,
    val rebate: Double?
)

data class MineLevelList(
    val level_id: String?, val level: String?, val name: String?, val invitee_num: String?
    , val cost: Double?, val reward: Double?, val rebate: Double?, val sub_rebate: Double?
    , val created_at: String?
)

data class MineVipList(
    val avatar: String?, val nickname: String?, val created: Long?, val user_id: String?
    , val level_id: String?, val level: String?, val level_name: String?, val recharge: String?
    , val exchange: String?, val brokerage: String?, val invitee_num: String?
)


data class MomentsAnchorListResponse(
    var anchor_id: String?, var dynamic_id: String?, var media: MutableList<String>? = null,
    var text: String?, var zans: String?, var pls: String?,
    var shares: String?, var avatar: String?, var live_status: String?,
    var create_time: Long = 0, var nickname: String?, var is_zan: Boolean = false,
    var live_status_txt: String?, var isToLive: Boolean = true, var sex: Int?
) : Serializable

data class MomentsHotDiscussResponse(
    var id: String = "",
    var user_id: String = "",
    var title: String = "",
    var images: MutableList<String>? = null,
    var nickname: String = "",
    var avatar: String = "",
    var lottery_name: String = "",
    var issue: String = "",
    var like: String = "",
    var comment_nums: String = "",
    var created: Long = 0,
    var is_like: String = "",
    var url: String = "",
    var is_promote: String = "",
    var gender: Int?
) : Serializable


data class BetLotteryBean(
    var betting: String,
    var customer: String?,
    var gameUrl: String,
    var protocol: String,
    var bettingArr: List<String>?,
    var chessArr: List<String>?,
    var app_start_banner: StartBanner?
)


data class AgMoney(val bl: String?)

data class Third(
    val name: String?,
    val name_cn: String?,
    val transfer_out: String?,
    val transfer_in: String?
)

data class StartBanner(var type: String?, var image_url: String?, var url: String?)

data class BankCard(
    var bank_id: String,
    var bank: String,
    var name: String,
    var no: String,
    var openbank: String,
    var rate: String,
    var low_money: String,
    var high_money: String
)

data class UserBankCard(
    var id: String?,
    var name: String?,
    var no: String?,
    var mark: String?,
    var remark: String?,
    var protocol:String?
)

data class MineMessageNew(
    var msg_id: String?,
    var title: String?,
    var content: String?,
    var create_time: Long?,
    var icon: String?,
    var readflag: Int?,
    var createtime_txt: String?,
    var media: String?,
    var dynamic_id: String?,
    var apiType: String?,
    var comment_id: String?,
    var userType: String?,
    var nickname: String?,
    var avatar: String?
)

data class VipInfo(
    var tips: List<String>?,
    var vip_list: List<VipObject>?,
    var gift_list: List<GiftObject>?
)

data class VipObject(
    val vip_id: Int?,
    val name: String?,
    val code: String?,
    val cz_total: String?,
    val flow_total: String?,
    val rg_flow: String?,
    val rg_day: String?,
    val upgrade_bonus: Double?,
    val birthday_gift: Double?,
    val month_hb: Double?,
    val gift: String?,
    val customer_service: String?,
    val rebate_ag: String?,
    val rebate_bg: String?
)

data class GiftObject(val name: String?, val list: List<ItemList>?,val vip_name: String?)

data class ItemList(val name: String?, val icon: String?, val price: String?)


data class VipCardInfo(var user_growth: VipCardObject?)
data class VipCardObject(
    var nickname: String?,
    var vip: Int?,
    var next_vip: Int?,
    var avatar: String?,
    var total_recharge: BigDecimal?,
    var total_flow: BigDecimal?,
    var next_cz_total: BigDecimal?,
    var next_flow_total: BigDecimal?,
    var vip_list: List<VipList>
)

data class VipGift(var vip: List<VipGiftChild>?, var noble: List<VipGiftChild>?)

data class VipGiftChild(
    var pack_id: Int?, var type: Int?, var code: String?,
    var name: String?, var icon: String?, var status: Int?
)

data class VipList(
    var vip_id: String?,
    var name: String?,
    var code: Int?,
    var cz_total: BigDecimal?,
    var flow_total: BigDecimal?,
    var rg_flow: BigDecimal?,
    var rg_day: Int?,
    var upgrade_bonus: Int,
    var birthday_gift: Int,
    var month_hbval: Int?,
    var customer_service: String?,
    var rebate_ag: String?,
    var rebate_bg: String?
)

data class VipBean(var isSelect: Boolean = false, var data: String)

@Parcelize
data class NobleInfo(
    var user_growth: NobleObject?,
    var noble_list: MutableList<NobleList>?,
    var gift_list: MutableList<NobleGiftList>,
    var tips: List<String>
) : Parcelable


data class QuBean(var id: String, var title: String, var is_check: Int)

data class Teach(
    var id: Int? = 0,
    var title: String? = "",
    var icon: String? = "",
    var images: String? = "",
    var isSelect: Boolean = false
)

data class USDCharge(
    var type: Int? = 0,
    var url: String? = "",
    var icon: String? = "",
    var form: String? = ""
)
@Parcelize
data class USDTList(
    var id: Int? = 0,
    var name: String? = "",
    var protocol: String? = "",
    var address: String? = "",
    var isSelect: Boolean = false
): Parcelable

data class Fee(val hand_fee:BigDecimal? = BigDecimal(1),val service_fee:BigDecimal? = BigDecimal(1))


data class HandUsdt(val id:String? = "",val title:String? = "",val address:String? = "",val code_image:String? = "",val protocol:String? = "")

data class WechatInfo(val id: String?,val min_sum: BigDecimal?,val max_sum: BigDecimal?,val code_image: String?)


@Parcelize
data class NobleObject(
    var exp: BigDecimal?,
    var keep_consume: BigDecimal?,
    var noble: Int?
) : Parcelable

@Parcelize
data class NobleList(
    var noble_id: String?,
    var name: String?,
    var code: Int?,
    var min_exp: BigDecimal?,
    var max_exp: BigDecimal?,
    var low_consume: BigDecimal?,
    var day: Int?,
    var loss: BigDecimal,
    var flag: Int,
    var seat: Int?,
    var welcome_msg: String?,
    var draws: Int?,
    var mount: Int?,
    var film_discount: String?,
    var barrage: Int?,
    var reward: Int?,
    var free_watch_nums: Int?,
    var put_hot: Int?
) : Parcelable

@Parcelize
data class NobleGiftList(val noble_name: String?, var list: List<NobleGiftListChild>?) : Parcelable

@Parcelize
data class NobleGiftListChild(var name: String?, var icon: String?, var price: String?) : Parcelable


data class GiftBean(var name: String?, var banner: List<BannerBean>)

data class BannerBean(var name: String?, var img: String?, var price: String?, var url: String)

