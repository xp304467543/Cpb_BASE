package com.customer.data

import com.customer.component.dialog.GlobalDialog
import com.customer.data.mine.MineUserBankList
import com.customer.data.mine.USDTList
import com.customer.utils.JsonUtils
import com.lib.basiclib.utils.SpUtils
import cuntomer.constant.UserConstant
import cuntomer.them.AppMode
import cuntomer.them.Theme


/**
 *
 * @ Author  QinTian
 * @ Date  2020/6/5
 * @ Describe
 *
 */
object UserInfoSp {


    /**
     * 系统公告
     */
    fun putSystemNotice(type: String, index: Int) {
        SpUtils.putInt(type, index)
    }

    fun getSystemNotice(type: String): Int {
        return SpUtils.getInt(type, 0)
    }

    /**
     * 是否显示注册红包
     */
    fun putIsShowRegisterRed( index: Boolean) {
        SpUtils.putBoolean("IsShowRegisterRedPop", index)
    }

    fun getIsShowRegisterRed(): Boolean {
        return SpUtils.getBoolean("IsShowRegisterRedPop", true)
    }

    /**
     * 纯净版切换
     */
    fun getAppMode(): AppMode {
        return when (SpUtils.getInt("AppMode", 1)) {
            1 -> AppMode.Normal
            2 -> AppMode.Pure
            else -> AppMode.Normal
        }
    }

    fun putAppMode(mode: AppMode) {
        val modeNow = when (mode) {
            AppMode.Normal -> 1
            AppMode.Pure -> 2
        }
        SpUtils.putInt("AppMode", modeNow)
    }


    fun putFloatType(mode: Int) {
        SpUtils.putInt("putFloatType", mode)
    }

    fun getFloatType():Int {
       return SpUtils.getInt("putFloatType", 0)
    }

    /**
     * 是否显示纯净版切换
     */
    fun getIsShowFloat(): Boolean {
        return SpUtils.getBoolean("isShowAppModeView", true)
    }

    fun putIsShowFloat(isShow: Boolean) {
        SpUtils.putBoolean("isShowAppModeView", isShow)
    }

    /**
     * 会员礼包领取
     */
    fun getVipGiftUser(): String? {
        return SpUtils.getString("VipGiftUser")
    }

    fun putVipGiftUser(str: String) {
        SpUtils.putString("VipGiftUser", str)
    }

    fun getVipGiftAddress(): String? {
        return SpUtils.getString("VipGiftAddress")
    }

    fun putVipGiftAddress(str: String) {
        SpUtils.putString("VipGiftAddress", str)
    }

    fun getVipGiftPhone(): String? {
        return SpUtils.getString("VipGiftPhone")
    }

    fun putVipGiftPhone(str: String) {
        SpUtils.putString("VipGiftPhone", str)
    }

    /**
     * USDT协议
     */
    fun putUsdtXy(str:String){
        SpUtils.putString("UsdtXy", str)
    }

    fun getUsdtXy(): String? {
        return SpUtils.getString("UsdtXy","")
    }

    /**
     * 小视频初始大小
     */
    fun getVideoSize(): Int {
        return SpUtils.getInt("VideoSize", 1)
    }

    fun putVideoSize(size: Int) {
        SpUtils.putInt("VideoSize", size)
    }

    /**
     * 影视区广告
     */
    fun getMovieBanner(): String {
        return SpUtils.getString("MovieBanner").toString()
    }

    fun putMovieBanner(str: String) {
        SpUtils.putString("MovieBanner", str)
    }

    /**
     * 视频搜索tag存储
     */
    fun putVideoSearChTag(input: Set<String>) {
        SpUtils.setStringSet("VideoSearChTagSet", input)
    }

    fun clearVideoSearChTag() {
        SpUtils.clear("VideoSearChTagSet")
    }

    fun getVideoSearChTag(): List<String>? {
        return SpUtils.getStringSet("VideoSearChTagSet", emptySet())?.toList()
    }

    /**
     * 推荐红点
     */
    fun putIsShowRed(IsShow: Boolean) {
        SpUtils.putBoolean("renBall", IsShow)
    }

    fun getIsShowRed(): Boolean {
        return SpUtils.getBoolean("renBall", true)
    }

    /**
     * 是否登录
     */
    fun putIsLogin(isLogin: Boolean) {
        SpUtils.putBoolean(UserConstant.USER_LOGIN, isLogin)
        if (!isLogin) GlobalDialog.spClear()
    }

    fun getIsLogin(): Boolean {
        return SpUtils.getBoolean(UserConstant.USER_LOGIN)
    }

    /**
     * 提示音
     */
    fun putIsPlaySound(isPlay: Boolean) {
        SpUtils.putBoolean(UserConstant.PLAY_SOUND, isPlay)
    }

    fun getIsPlaySound(): Boolean {
        return SpUtils.getBoolean(UserConstant.PLAY_SOUND, true)
    }

    /**
     * token
     */
    fun putToken(token: String?) {
        token?.let { SpUtils.putString(UserConstant.TOKEN, it) }
        SpUtils.putString(UserConstant.TOKEN_WITH_BEARER, "Bearer $token")
    }

    fun getToken(): String? {
        return SpUtils.getString(UserConstant.TOKEN)
    }

    fun getTokenWithBearer(): String? {
        return SpUtils.getString(UserConstant.TOKEN_WITH_BEARER)
    }

    /**
     * 用户ID
     */

    fun putUserId(id: Int) {
        SpUtils.putInt(UserConstant.USER_ID, id)
    }

    fun getUserId(): Int {
        return SpUtils.getInt(UserConstant.USER_ID, 0)
    }

    /**
     * 用户名称 nickName
     */
    fun putUserNickName(nickName: String) {
        SpUtils.putString(UserConstant.USER_NICK_NAME, nickName)
    }

    fun getUserNickName(): String? {
        return SpUtils.getString(UserConstant.USER_NICK_NAME)
    }

    /**
     * 客服地址
     */
    fun putCustomer(customer: String) {
        SpUtils.putString("customer", customer)
    }

    fun getCustomer(): String? {
        return SpUtils.getString("customer")
    }

    /**
     * 后台控制是否显示注册红包
     */
    fun putIsShowRegRed(customer: Boolean) {
        SpUtils.putBoolean("IsShowRegRed", customer)
    }

    fun getIsShowRegRed(): Boolean {
        return SpUtils.getBoolean("IsShowRegRed",false)
    }

    /**
     * 用户名 Name
     */
    fun putUserName(Name: String) {
        SpUtils.putString(UserConstant.USER_NAME, Name)
    }

    fun getUserName(): String? {
        return SpUtils.getString(UserConstant.USER_NAME)
    }

    /**
     * 用户名头像
     */
    fun putUserPhoto(url: String) {
        SpUtils.putString(UserConstant.USER_AVATAR, url)
    }

    fun getUserPhoto(): String? {
        return SpUtils.getString(UserConstant.USER_AVATAR)
    }

    /**
     * 手机号
     */
    fun putUserPhone(phone: String) {
        SpUtils.putString(UserConstant.USER_PHONE, phone)
    }

    fun getUserPhone(): String? {
        return SpUtils.getString(UserConstant.USER_PHONE)
    }


    /**
     * 性别
     */
    fun putUserSex(sex: Int) {
        SpUtils.putInt(UserConstant.USER_SEX, sex)
    }

    fun getUserSex(): Int {
        return SpUtils.getInt(UserConstant.USER_SEX, 1)
    }

    /**
     * 个性签名
     */
    fun putUserProfile(profile: String) {
        SpUtils.putString(UserConstant.USER_PROFILE, profile)
    }

    fun getUserProfile(): String? {
        return SpUtils.getString(UserConstant.USER_PROFILE)
    }

    /**
     * 用户粉丝 关注 点赞
     */
    fun putUserFans(fans: String) {
        SpUtils.putString("UserFans", fans)
    }

    fun getUserFans(): String? {
        return SpUtils.getString("UserFans")
    }

    /**
     * 用户粉丝 关注 点赞
     */
    fun putUserUniqueId(fans: String) {
        SpUtils.putString("UniqueId", fans)
    }

    fun getUserUniqueId(): String? {
        return SpUtils.getString("UniqueId")
    }

    /**
     * 是否提示送礼物信息
     */
    fun putSendGiftTips(boolean: Boolean) {
        SpUtils.putBoolean("GiftTips", boolean)
    }

    fun getSendGiftTips(): Boolean {
        return SpUtils.getBoolean("GiftTips", true)
    }

    /**
     * 是否设置支付密码
     */

    fun putIsSetPayPassWord(boolean: Boolean) {
        SpUtils.putBoolean("PayPassWord", boolean)
    }

    fun getIsSetPayPassWord(): Boolean {
        return SpUtils.getBoolean("PayPassWord", false)
    }

    /**
     * 是否首冲
     */
    fun putIsFirstRecharge(boolean: Boolean) {
        SpUtils.putBoolean("IsFirstRecharge", boolean)
    }

    fun getIsFirstRecharge(): Boolean {
        return SpUtils.getBoolean("IsFirstRecharge", true)
    }

    /**
     * 弹幕开关
     */
    fun putDanMuSwitch(boolean: Boolean) {
        SpUtils.putBoolean("DanMuSwitch", boolean)
    }

    fun getDanMuSwitch(): Boolean {
        return SpUtils.getBoolean("DanMuSwitch", true)
    }

    /**
     * 首页引导图
     */
    fun putMainGuide(boolean: Boolean) {
        SpUtils.putBoolean("MainGuide", boolean)
    }

    fun getMainGuide(): Boolean {
        return SpUtils.getBoolean("MainGuide", false)
    }

    /**
     * 开奖引导图
     */
    fun putOpenCodeGuide(boolean: Boolean) {
        SpUtils.putBoolean("OpenCodeGuide", boolean)
    }

    fun getOpenCodeGuide(): Boolean {
        return SpUtils.getBoolean("OpenCodeGuide", false)
    }

    /**
     * 我的界面引导图
     */
    fun putMineGuide(boolean: Boolean) {
        SpUtils.putBoolean("MineGuide", boolean)
    }

    fun getMineGuide(): Boolean {
        return SpUtils.getBoolean("MineGuide", false)
    }

    /**
     * 关注引导图
     */
    fun putAttentionGuide(boolean: Boolean) {
        SpUtils.putBoolean("AttentionGuide", boolean)
    }

    fun getAttentionGuide(): Boolean {
        return SpUtils.getBoolean("AttentionGuide", false)
    }

    /**
     * random_str
     */
    fun putRandomStr(random_str: String?) {
        random_str?.let { SpUtils.putString("random_str", it) }
    }

    fun getRandomStr(): String? {
        return SpUtils.getString("random_str", "")
    }

    /**
     * 打赏引导图
     */
    fun putRewardnGuide(boolean: Boolean) {
        SpUtils.putBoolean("RewardnGuide", boolean)
    }

    fun getRewardnGuide(): Boolean {
        return SpUtils.getBoolean("RewardnGuide", false)
    }

    /**
     * 直播引导图
     */
    fun putLiveGuide(boolean: Boolean) {
        SpUtils.putBoolean("MineGuide", boolean)
    }

    fun getLiveeGuide(): Boolean {
        return SpUtils.getBoolean("MineGuide", false)
    }

    /**
     * 是否悬浮
     */
    fun isAboveLive(boolean: Boolean) {
        SpUtils.putBoolean("AboveLive", boolean)
    }

    fun getIsAboveLive(): Boolean {
        return SpUtils.getBoolean("AboveLive", false)
    }

    /**
     * 记录Vip等级
     */
    fun setVipLevel(str: Int) {
        SpUtils.putInt("UserVipLevel", str)
    }

    fun getVipLevel(): Int {
        return SpUtils.getInt("UserVipLevel", 0)
    }


    /**
     * 记录贵族等级
     */
    fun setNobleLevel(int: Int) {
        SpUtils.putInt("noble", int)
    }

    fun getNobleLevel(): Int {
        return SpUtils.getInt("noble", 0)
    }

    /**
     * 记录Vip等级 0-用户 1-主播 2-管理 4-游客
     */
    fun setUserType(boolean: String) {
        SpUtils.putString("UserType", boolean)
    }

    fun getUserType(): String? {
        return SpUtils.getString("UserType", "0")
    }

    /**
     * 悬浮按钮
     */
    fun putOpenWindow(boolean: Boolean) {
        SpUtils.putBoolean("openWindow", boolean)
    }

    fun getOpenWindow(): Boolean {
        return SpUtils.getBoolean("openWindow", true)
    }

    /**
     * 记住账号
     */
    fun putRememberCount(boolean: Boolean){
        SpUtils.putBoolean("putRememberCount", boolean)
    }

    fun getRememberCount(): Boolean {
        return SpUtils.getBoolean("putRememberCount", false)
    }

    /**
     * 已登录账号
     */
    fun putRememberUserCount(boolean: String){
        SpUtils.putString("putRememberUserCount", boolean)
    }

    fun getRememberUserCount(): String {
        return SpUtils.getString("putRememberUserCount")?:""
    }


    /**
     * 已登录密码
     */
    fun putRememberUserSecret(boolean: String){
        SpUtils.putString("putRememberUserSecret", boolean)
    }

    fun getRememberUserSecret(): String {
        return SpUtils.getString("putRememberUserSecret")?:""
    }


    /**
     * 主题 1-默认 2-春节 3-端午 4-情人节 5-国庆 6-圣诞 7-牛年 8-欧冠
     */
    fun putThem(int: Int) {
        SpUtils.putInt("ThemSelect", int)
    }

    fun getThem(): Theme {
        return Theme.Default
//
//        when (SpUtils.getInt("ThemSelect", 7)) {
//            1 -> {
//                Theme.Default
//            }
//            2 -> {
//                Theme.NewYear
//            }
//            3 -> {
//                Theme.MidAutumn
//            }
//            4 -> {
//                Theme.LoverDay
//            }
//            5 -> {
//                Theme.NationDay
//            }
//            6 -> {
//                Theme.ChristmasDay
//            }
//            7 -> {
//                Theme.OxYear
//            }
//            8 -> {
//                Theme.Uefa
//            }
//            else -> Theme.Default
//        }
    }

    fun getThemInt(): Int {
        return (SpUtils.getInt("ThemSelect", 1))
    }

    /**
     * 记录是否选择过皮肤
     */
    fun getIsSetSkin():Boolean{
      return  SpUtils.getBoolean("UserThemSelect", false)
    }

    fun setIsSetSkin(boolean: Boolean){
        SpUtils.putBoolean("UserThemSelect", boolean)
    }

    /**
     * 银行卡信息记录
     */
    fun putSelectBankCard(mineBank: MineUserBankList) {
        SpUtils.putString(UserConstant.USER_BANK_SELECT, JsonUtils.toJson(mineBank))
    }

    fun getSelectBankCard(): MineUserBankList? {
        return if (SpUtils.getString(UserConstant.USER_BANK_SELECT) != "") {
            JsonUtils.fromJson(
                SpUtils.getString(UserConstant.USER_BANK_SELECT).toString(),
                MineUserBankList::class.java
            )
        } else null

    }

    /**
     * USDT信息记录
     */
    fun putSelectUSDT(mineBank: USDTList?) {
        if (mineBank!=null){
            SpUtils.putString(UserConstant.USDT, JsonUtils.toJson(mineBank))
        }else SpUtils.clear(UserConstant.USDT)

    }

    fun getSelectUSDT(): USDTList? {
        return if (SpUtils.getString(UserConstant.USDT) != "") {
            JsonUtils.fromJson(
                SpUtils.getString(UserConstant.USDT).toString(),
                USDTList::class.java
            )
        } else null

    }

    /**
     * 显示动画效果
     */

    fun putIsShowAnim(boolean: Boolean) {
        SpUtils.putBoolean("IsShowAnim", boolean)
    }

    fun getIsShowAnim(): Boolean {
        return SpUtils.getBoolean("IsShowAnim", true)
    }

}