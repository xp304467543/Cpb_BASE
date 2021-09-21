package com.customer.data.login

data class LoginResponse(
    var token: String?,
    var user_id: String?,
    var expire: Long,
    var avatar: String?,
    var password_not_set: Int = -1,
    var gender: Int = 0,
    var phone: String?,
    var nickname: String?,
    var user_type: String?,
    var username: String?,
    var profile: String?,
    var random_str: String
)

data class RegisterCode(var code: String,var second:Long=600)

data class LoginSuccess(var code: String)

data class RegisterSuccess(var isShowDialog: Boolean)

data class LoginInfoResponse(
    var user_id: Int? = 0,
    var id: String?,
    var username: String?,
    var email: String?,
    var phone: String?,
    var nickname: String?,
    var profile: String?,
    var birth: String?,
    var gender: Int = 0,
    var mobile: String?,
    var avatar: String?,
    var wechat: String?,
    var qq: String?,
    var address: String?,
    var following: String = "0",
    var followers: String = "0",
    var like: String = "0",
    var unique_id: String = "0",
    var token: String = "0",
    var market_code:String? = "",
    var free_watch_nums:String? = "",
    var sum_watch_nums:String? = "",
    var noble:Int = 0,
    var vip:Int = 0
)

data class LoginFirstRecharge(var isfirst: String = "0")