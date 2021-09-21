package com.customer.component.activity

import android.annotation.SuppressLint
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.activity.BaseNavActivity

import com.xiaojinzi.component.anno.RouterAnno


/**
 *
 * @ Author  QinTian
 * @ Date  7/1/21
 * @ Describe
 *
 */
@RouterAnno(host = "Base", path = "Usdtweb")
class RechargeUsdtWeb : BaseNavActivity() {


    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false

    override fun isShowBackIconClose()  = true

    override fun getContentResID() = R.layout.usdt_web


    override fun initContentView() {
        handleSetting()
//        webView.loadDataWithBaseURL(null,intent.getStringExtra("usdtForm"), "text/html; charset=UTF-8", "utf-8",null);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun handleSetting() {
//       val webSettings = webView.getSettings();
//
//
//        // 网页内容的宽度是否可大于WebView控件的宽度
//        //        webSettings.setUserAgentString(ua + "这里是增加的标识");
//
//        // 网页内容的宽度是否可大于WebView控件的宽度
//
//        webSettings.setSupportZoom(true)
//
//        webSettings.javaScriptEnabled = true
//        webSettings.setTextZoom(100)
//        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
//        //webView.setLayerType();
//
//        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
//        //webView.setLayerType();


    }

}