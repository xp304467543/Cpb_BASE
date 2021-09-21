package com.customer.data.lottery

import android.os.Parcelable
import com.google.gson.JsonArray
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/16
 * @ Describe
 *
 */
data class LotteryTypeResponse(
    var lottery_id: String?,
    var cname: String?,
    var logo_url: String?,
    var video_url: String? = ""
)

data class LotteryCodeTrendResponse(
    var id: Int? = 0,
    var lottery_id: String? = "0",
    var property_id: String? = "0",
    var issue: String? = "",
    var open_code: String? = "",
    var created: String? = "",
    val trending: List<Int>? = null
)

data class LotteryCodeNewResponse(
    var lottery_id: String? = "",
    var issue: String? = "",
    var code: String? = "",
    var next_lottery_time: Long? = 0,
    var next_issue: String?,
    var input_time: String? = "",
    var next_lottery_end_time: Long?
)

data class LotteryCodeHistoryResponse(
    var issue: String? = "",
    var code: String? = "",
    var input_time: String? = "",
    var isSelect:Boolean = false
)

data class LotteryCodeChangLongResponse(
    var id: String? = "",
    var lottery_id: String? = "",
    var method_cname: String? = "",
    var nums: String? = "",
    var result_c: String? = "",
    var updated: String? = ""
)

data class LotteryCodeLuZhuResponse(
    var code: Int? = 0,
    var msg: String? = "",
    var data: List<List<List<String>>>? = null,
    var total: JsonArray? = null
)

data class LotteryExpertPaleyResponse(
    var id: String? = "0", var nickname: String? = "0", var expert_id: String? = "0",
    var lottery_id: String? = "0", var method: String? = "0", var issue: String? = "0",
    var code: String? = "0", var hit_rate: String? = "0", var is_right: String? = "0",
    var created: String? = "0", var avatar: String? = "0", var profit_rate: String? = "0",
    var winning: String? = "0", var last_10_games: List<String>? = null
)


data class LotteryBetRuleResponse(
    var play_rule_type_id: Int? = 0,
    var play_rule_type_name: String? = null,
    var play_rule_type_data: List<PlayRuleTypeDataBean>? = null
) : Serializable

data class PlayRuleTypeDataBean(
    var play_rule_lottery_id: String? = null,
    var play_rule_lottery_name: String? = null,
    var play_rule_content_id: Int? = 0,
    var play_rule_content: String? = null
) : Serializable


/**
 * 玩法列表
 */
@Parcelize
data class LotteryPlayListResponse(
    val play_unit_data: ArrayList<PlayUnitData>?,
    val play_unit_id: Int?,
    val play_unit_name: String?
) : Parcelable

@Parcelize
data class PlayUnitData(
    val play_sec_cname: String,
    val play_sec_combo: Int?,
    var play_sec_data: MutableList<PlaySecData>,
    val play_sec_info: MutableList<String>?,
    val play_sec_id: Int?,
    val play_sec_name: String?,
    val play_sec_merge_name: String?,
    var isSelected: Boolean = false,
    var play_sec_options: MutableList<PlayOptions>?
) : Parcelable

@Parcelize
data class PlaySecData(
    val lottery_id: String?="",
    val lottery_name:String?= "",
    val play_class_cname: String? = "",
    val play_class_id: Int? = 0,
    var play_sec_name: String? = "",
    var play_sec_cname: String? = "",
    val play_class_name: String? = "",
    var play_odds: String? = "",
    var playName: String? = "",
    var isSelected: Boolean = false,
    var isSelect_1 :Boolean = false,
    var isSelect_2: Boolean = false,
    var money: String? = "0",
    var play_sec_id: Int? = 0,
    var play_sec_data: MutableList<SecData>? = null,
    var title: String = "",
    var type: String = "",
    var play_sec_merge_name: String? = "",
    var selectPos: Int = 0,
    var next_issue:String? = "0",
    var count_down:Long?= 0,
    var result: String?="NULL",
    var nums:Int?= 0 ,
    var play_bet_issue:String?="",
    var isLong:Boolean = false,
    var pk:ArrayList<BigDecimal>?= arrayListOf(),
    var rzPosition: Int = -1,//二字定位 判断 左右 0左 1右
    var play_sec_options: MutableList<PlayOptions>? = null
) : Parcelable

@Parcelize
data class SecData(
    val play_class_id: Int? = 0,
    var play_sec_name: String? = "",
    var play_sec_cname: String? = "",
    var play_sec_id: Int? = 0,
    val play_class_name: String? = "",
    val play_class_cname: String? = "",
    val lottery_name:String?= "",
    var play_odds: String? = ""
) : Parcelable

@Parcelize
data class PlayOptions(
    val play_class_cname: String? = "",
    val play_class_id: Int? = 0,
    val play_sec_name: String? = "",
    var play_sec_cname: String? = "",
    val play_class_name: String? = "",
    val play_odds: String? = "",
    var playName: String? = "",
    var isSelected: Boolean = false,
    var money: String? = "0",
    var play_sec_id: Int? = 0,
    var title: String = "",
    var type: String = ""
) : Parcelable




//快捷数据
data class PlaySecDataKj(
    var title: String? = "null",
    var play_sec_id: Int? = 0,
    val play_sec_name: String? = "",
    val play_sec_cname: String? = "",
    val play_sec_merge_name: String? = "",
    val play_class_id: Int? = 0,
    val play_class_name: String? = "",
    val play_class_cname: String? = "",
    val play_odds: String? = "0",
    var isSelected: Boolean = false,
    var type: String = "",
    var currentData: PlaySecData? = null
)

data class PlayMoneyData(
    val play_sum_id: Int?,
    val play_sum_num: Int?,
    val play_sum_name: String?

)


data class BetBean(
    val play_sec_name: String?,
    val play_class_name: String?,
    val play_bet_sum: String?,
    val play_bet_issue:String?="",
    val play_bet_lottery_id:String?=""

)

data class BetShareBean(
    val play_sec_cname: String?,
    val play_bet_sum: String?,
    val play_class_cname: String?,
    val play_class_name: String?,
    val play_odds: String?,
    val play_sec_name: String?,
    var isShow: Boolean = false
)

data class LotteryBetHistoryResponse(
    var play_bet_time: Long?, var play_bet_lottery_id: String?, var play_bet_lottery_name: String?,
    var play_bet_issue: String?, var play_sec_id: String?, var play_sec_name: String?,
    var play_class_name: String?, var play_bet_sum: String?, var play_odds: String?,
    var play_bet_score: String?
)