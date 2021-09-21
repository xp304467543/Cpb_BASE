package com.customer.data.discount

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * @ Author  QinTian
 * @ Date  4/7/21
 * @ Describe
 *
 */

data class DiscountTile(var type:Int?=0,var name:String?="",var icon:String?="")

@Parcelize
data class DiscountContent(var activity_id:Int?=0,var title:String?="",var cover:String?="",var ishot:Int?=0,var address:String?="",var content:String?="",
                           var type:Int?=0,var action:Int?=0,var type_text:String?="",var type_icon:String?="",var specialId:Int = 0): Parcelable


data class CustomerList(var id:String?,var title:String?,var image:String?,var value:String?,var type:String?,var code_image:String?)


data class CustomerQuestion(var id:String?,var title:String?,var image:String?,var value:String?,var type:String?,var code_image:String?)