/*
 * Tencent is pleased to support the open source community by making VasSonic available.
 *
 * Copyright (C) 2017 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *
 */

package com.lib.basiclib.web.sonic

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.webkit.CookieManager
import android.webkit.WebResourceResponse
import com.lib.basiclib.utils.LogUtils
import com.tencent.sonic.sdk.SonicRuntime
import com.tencent.sonic.sdk.SonicSessionClient
import java.io.InputStream

/**
 * the sonic host application must implement SonicRuntime to do right things.
 */

class SonicRuntimeImpl(context: Context) : SonicRuntime(context) {

    /**
     * 获取用户UA信息
     *
     * @return
     */
    override fun getUserAgent(): String {
        return "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Mobile Safari/537.36"
    }

    /**
     * 获取用户ID信息
     */
    override fun getCurrentUserAccount(): String {
        return ""
    }

    override fun getCookie(url: String?): String? {
        val cookieManager: CookieManager? = CookieManager.getInstance()
        return cookieManager?.getCookie(url)
    }

    override fun log(tag: String, level: Int, message: String) {
        LogUtils.e(message)
    }

    override fun createWebResourceResponse(mimeType: String, encoding: String, data: InputStream, headers: Map<String, String>): Any {
        val resourceResponse = WebResourceResponse(mimeType, encoding, data)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.responseHeaders = headers
        }
        return resourceResponse
    }

    override fun showToast(text: CharSequence, duration: Int) {

    }

    override fun postTaskToThread(task: Runnable, delayMillis: Long) {
        Thread(task).start()
    }

    override fun notifyError(client: SonicSessionClient, url: String, errorCode: Int) {

    }

    override fun isSonicUrl(url: String): Boolean {
        return true
    }

    override fun setCookie(url: String, cookies: List<String>?): Boolean {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.isNotEmpty()) {
            val cookieManager = CookieManager.getInstance()
            for (cookie in cookies) {
                cookieManager.setCookie(url, cookie)
            }
            return true
        }
        return false
    }

    override fun isNetworkValid(): Boolean {
        return true
    }


    override fun getHostDirectAddress(url: String?): String? {
        return null
    }
}
