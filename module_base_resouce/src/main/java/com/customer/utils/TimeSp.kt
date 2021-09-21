package com.customer.utils

import android.content.Context
import android.content.SharedPreferences
import com.lib.basiclib.utils.AppUtils

/**
 *
 * @ Author  QinTian
 * @ Date  2021/8/23
 * @ Describe
 *
 */
object TimeSp {

    private var sharedPreferences: SharedPreferences? = null
    val sp: SharedPreferences
        @Synchronized get() {
            if (sharedPreferences == null) {
                synchronized(TimeSp::class.java) {
                    if (sharedPreferences == null) {
                        sharedPreferences = AppUtils.getContext().getSharedPreferences("TimeSp", Context.MODE_PRIVATE)
                    }
                }
            }
            return sharedPreferences!!
        }


    fun getTimeBefore(key: String, defValue: Long = 0): Long {
        return sp.getLong(key, defValue)
    }


    fun putTimeBefore(key: String, defValue: Long = 0) {
         sp.edit().putLong(key,defValue).apply()
    }

    fun getIsShow(key: String): Boolean {
        return sp.getBoolean(key, false)
    }
    fun putIsShow(key: String, defValue: Boolean ) {
        sp.edit().putBoolean(key,defValue).apply()
    }
}