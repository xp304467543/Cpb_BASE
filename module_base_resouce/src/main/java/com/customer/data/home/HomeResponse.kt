package com.customer.data.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject
import java.math.BigDecimal

/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/10
 * @ Describe
 *
 */
//服务器地址
data class SystemUrl(
    val live_api: String?, val user_api: String?, val forum_api: String?,
    val lottery_api: String?, val chat_url: String?, val notice_url: String?, val movie_api: String?
)

data class HomeTab(var name: String?, var code: String)

// 轮播图
data class HomeBannerResponse(
    val type: String?, val title: String?, val name: String?,
    val image_url: String?, val content: String?, val url: String?
)

@Parcelize
data class HomeGameResponse(
    val code: String?, val icon: String?, val name: String?,
    val list: ArrayList<HomeGameChildResponse>?, val icon1: String?
) : Parcelable

@Parcelize
data class HomeGameChildResponse(
    val name: String?,
    val name_cn: String?,
    val link: String?,
    val img: String?,
    val type: String?,
    val id: String?,
    var typePos:String = "",
    var game_type:String?,
    var platform:String?
) : Parcelable

// 公告信息
data class HomeSystemNoticeResponse(
    val msgtype: String?,
    val trgttype: String?,
    val trgtuid: String?,
    val content: String?,
    val action: String?,
    val gnrtime: String?,
    val id: String?
)

// 彩种列表
data class HomeLotteryTypeResponse(
    @SerializedName("1") var s1: List<HomeTypeListResponse>?,
    @SerializedName("2") var s2: List<HomeTypeListResponse>?,
    @SerializedName("3") var s3: List<HomeTypeListResponse>?,
    @SerializedName("4") var s4: List<HomeTypeListResponse>?
)

data class HomeTypeListResponse(
    var type: String?, var anchor_id: String?, var game_id: String?,
    var name: String?, var image: String?, var image_pc: String?,
    var live_status: String?, var live_intro: String?, var online: Int?,
    var lottery_id: String?, var live_status_txt: String?
)

// 热门直播
@Parcelize
data class HomeHotLiveResponse(
    var anchor_id: String? = "-1",
    var game_id: String? = "-1",
    var name: String?,
    var r_id: String? = "-1",
    var live_status: String? = "-1",
    var nickname: String?,
    var avatar: String? = "-1",
    var tags: ArrayList<SportLiveInfoTag>?=null,
    var live_intro: String? = "",
    var lottery_id: String? = "-1",
    var red_paper_num: Int,
    var online: Int?,
    var daxiu: Boolean?=false,
    var background: String? = "",
    var is_sport:String? = "0",
    var cover:String? = "",
    var room_type: String? = "",
    var race_config: SportLiveInfoRaceConfig?=null,
    var race_type: String? = "",
    var tagString: String? = "",
    var live_status_txt: String? = "",
    var ext_img: String? = "",
    var background_pc: String? = ""
): Parcelable

@Parcelize
data class SportLiveInfo(
    var anchor_id: String?,
    var nickname: String?,
    var avatar: String?,
    var cover:String?,
    var live_status: String?,
    var online: String?,
    var tags: ArrayList<SportLiveInfoTag>?,
    var live_intro: String?,
    var r_id: String?,
    var name: String?,
    var room_type: String?,
    var race_config: SportLiveInfoRaceConfig?,
    var race_type: String?,
    var lottery_id: String?,
    var tagString: String?,
    var live_status_txt: String?,
    var red_paper_num: String?,
    var daxiu: String?,
    var background: String?,
    var ext_img: String?,
    var background_pc: String?
): Parcelable

@Parcelize
data class HomeExpertTags(var icon: String?, var title: String?): Parcelable
data class HomeLiveAdvanceList(val title: String = "", val bean: Array<HomeLivePreResponse>)

//直播预告
data class HomeLivePreResponse(
    var aid: String? = "-1",
    var starttime: String? = "-1",
    var endtime: String? = "-1",
    var nickname: String? = "-1",
    var avatar: String? = "-1",
    var name: String? = "-1",
    var isFollow: Boolean = false,
    var livestatus: String? = "-1",
    var lottery_id: String? = "-1",
    var date: String? = "-1",
    var type: Int = 2
)

//新闻
data class HomeNewsResponse(
    var id: String? = "-1",
    var info_id: String? = "-1",
    var title: String?,
    var type: String? = "-1",
    var createtime: String? = "-1",
    var tag: String? = "-1",
    var timegap: String?,
    var type_txt: String?,
    var tag_txt: String? = "-1",
    var settype: String? = "-1",
    var cover_img: List<String>? = null
)

//新闻详情
data class HomeNesInfoResponse(
    var info_id: String?, var title: String?, var cover_img: List<String>? = null,
    var type: String?, var createtime: String?, var tag: String?,
    var detail: String?, var des: String?, var source: String?,
    var settype: String?, var timegap: String?, var type_txt: String?, var tag_txt: String?
)

//广告图
data class HomeAdResponse(
    var type: String?,
    var title: String?,
    var name: String?,
    var image_url: String?,
    var content: String?,
    var url: String?,
    var image_url_pc: String?
)

//专家列表
data class HomeExpertList(
    var id: String? = "",
    var nickname: String?,
    var avatar: String? = "",
    var win_rate: String?,
    var profit_rate: String?,
    var winning: String?,
    var last_10_games: List<String>? = null,
    var created: String? = "",
    var lottery_id: String? = "",
    var lottery_name: String?
)

//搜索推荐
data class HomeAnchorRecommend(
    var nickname: String?, var id: String?, var name: String?,
    var live_status: String?, var avatar: String?, var online: Int?, var lotteryId: Int?
)


//搜索推荐
data class HomeAnchorSearch(
    var result: List<HomeHotLiveResponse>?,
    var related: List<HomeHotLiveResponse>?
)


data class Tag(var title: String?, var icon: String?)

data class BetLotteryBean(
    var betting: String,
    var customer: String,
    var gameUrl: String,
    var protocol: String,
    var bettingArr: List<String>?,
    var chessArr: List<String>?,
    var app_start_banner: StartBanner?
)

data class StartBanner(var type: String?, var image_url: String?, var url: String?)


//新消息提醒
data class MineNewMsg(var msgCount: Int, var countList: MineNewBean?)
data class MineNewBean(
    @SerializedName("0") var `_$0`: String?,
    @SerializedName("2") var `_$2`: String?,
    @SerializedName("3") var `_$3`: String?,
    @SerializedName("5") var `_$5`: String?
)


/**
 * version_data : 版本信息
 * version_data.enforce : 是否强制更新 1是 0-否
 * version_data.version : 客户端版本号
 * version_data.newversion : 新版本号
 * version_data.downloadurl : 下载地址
 * version_data.packagesize : 包大小
 * version_data.upgradetext : 更新内容
 */

data class UpdateData(var version_data: Update?)

data class RedRain(var amount: BigDecimal?)

data class RedRainIsShow(var red_end_time: BigDecimal?,var red_start_timme: BigDecimal?,var time: BigDecimal?)

data class Online(var base_online: Long = 100, var online: Long = 100)

data class HomeTitle(
    var customer: String?, var index_nav: List<HomeTitleChild>?, var betting: String,
    var gameUrl: String,
    var protocol: String,
    var bettingArr: List<String>?,
    var chessArr: List<String>?,
    var app_start_banner: StartBanner?,
    var redpaper_pop: Boolean?,
    var default_skin: Skin?
)

data class Skin(val id: Int, val type: String)

data class HomeTitleChild(var code: String?, var name: String?)

data class RedTask(var prompt: Int?)

data class TaskGift(val gift_type: Int?, val amount: String?)

data class UserTask(
    var task_id: Int?,
    var title: String?,
    var status: Int?,
    var target: Int?,
    var jump: Int?,
    var archive: String?,
    var reward: String?,
    var gift_type:String
)

data class Update(
    var enforce: Int = 0,
    var version: String?,
    var newversion: String?,
    var downloadurl: String?,
    var packagesize: String?,
    var upgradetext: String?
)


//系统公告
data class SystemNotice(var name: String?, var data: List<SystemNoticeChild>?)
data class SystemNoticeChild(
    val msg_id: String?,
    val msg_type: String?,
    val ctype: String?,
    val title: String?,
    val content: String?,
    val create_time: String?
)

//首页游戏
data class Game(var id: String?, var name: String?, var img_url: String?, var type: String? = "0")

data class AnchorRank(
    var pop_rank: PopObject?,
    var income_rank: PopObject?,
    var devote_rank: PopObject?,
    var invitee: PopObject?,
    var brokerage: PopObject?,
    var win: PopObject?,
    var prize: PopObject?,
    var hot: PopObject?,
    var like: PopObject?,
    var collect: PopObject?
)

data class PopObject(
    var week: List<AnchorObject>?,
    var month: List<AnchorObject>?,
    var all: List<AnchorObject>?,
    var day: List<AnchorObject>?
)

data class AnchorObject(
    var noble: Int? = 0,
    var anchor_id: String?,
    var nickname: String?,
    var avatar: String?,
    var count: String?,
    var no: String?,
    var user_type: String?,
    var user_id: String?,
    var amount: String?,
    var num: String?,
    var prize: String?,
    var win_rate: BigDecimal?,
    var moviesid: Int?,
    var cover: String?,
    var title: String?,
    var tag: String?,
    var reads: String?,
    var play_time: Long?,
    var collectnum: String?,
    var watchmum: String?,
    var likenum: String?

)

data class PanGift(var data: List<PanObject>, var draws_num: Int?,var user_money:BigDecimal?)

data class PanObject(
    var id: String?,
    var name: String?,
    var icon: String?,
    var number: String?,
    var prize: String?
)

data class PanGiftObject(
    var phone: String?,
    var name: String?,
    var number: String?
)

data class PanSinglePrise(
    var unit_price: BigDecimal?
)

data class PanPriseLog(
    var name: String?,
    var phone: String?,
    var number: String?,
    var created_time: String?
)

data class PriseObject(
    var id: String?,
    var status: Int? = -1,
    var name: String?,
    var number: String?,
    var time_now: Int? = 0,
    var times_now: Int? = 0,
    var after_money:BigDecimal
)

@Parcelize
data class LiveTypeObject(
    var name_cn: String?,
    var name_en: String?,
    var type_name: String?
) : Parcelable

data class LiveBanner(var banner_sport_live: ArrayList<SportBanner>?)

data class SportBanner(
    var type: String?,
    var title: String?,
    var name: String?,
    var image_url: String?,
    var content: String?,
    var url: String?

)

data class TeamInfo(
    var countrygbname: String?,
    var city: String?,
    var teamsxname: String?,
    var teamgbname: String?,
    var teamcreatedate: String?,
    var teamaddress: String?,
    var introduction: String?,
    var homefieldcap: String?,
    var teamlogo: String?,
    var teamrank:ArrayList<TeamMark>

)

data class HotMatch(
    var simpleleague: String?,
    var homesxname: String?,
    var awaysxname: String?,
    var homelogo: String?,
    var awaylogo: String?,
    var sport: String?,
    var matchtime: Long?
)

data class moreGame(val url:String?)

data class TeamMark(
    var id: String?,
    var teamid: String?,
    var bet007name: String?,
    var year: String?,
    var month: String?,
    var rank: String?,
    var rankchange: String?,
    var point: String?,
    var pointchange: String?
)

data class ParseNotify(val str: String)


data class SportLive(var list: ArrayList<SportLiveInfo>?,var types:JSONObject?)



@Parcelize
data class SportLiveInfoTag(var title: String?, var icon: String?): Parcelable

@Parcelize
data class SportLiveInfoRaceConfig(var hstr: String?, var astr: String?, var video: String?,
                                   var seasonyear: String?, var stagemode: String?, var stageid: String?,
                                   var stagename: String?, var matchgroup: String?, var isbuy: String?,
                                   var is_cup: String?, var fid: String?, var homeid: String?,
                                   var awayid: String?, var status: String?, var status_desc: String?,
                                   var extra_statusid: String?, var simpleleague: String?, var homesxname: String?,
                                   var awaysxname: String?, var matchtime: String?, var matchdate: String?,
                                   var league_id: String?, var seasonid: String?, var homestanding: String?,
                                   var awaystanding: String?, var isright: String?, var matchround: String?,
                                   var homescore: String?, var awayscore: String?, var homehalfscore: String?,
                                   var awayhalfscore: String?, var rangqiu: String?, var zlc: String?
                                   , var homelogo: String?, var awaylogo: String?, var home_red_counts: String?
                                   , var away_red_counts: String?, var match_at: String?, var extra_time_score: String?
                                   , var spot_kick_score: String?, var extra_time_home_score: String?, var extra_time_away_score: String?
                                   , var spot_kick_home_score: String?, var spot_kick_away_score: String?, var extra_exist: String?
                                   , var isbought: String?, var isfocus: String?): Parcelable
