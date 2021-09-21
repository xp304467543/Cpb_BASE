package com.customer.component.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.customer.data.mine.MineApi
import com.fh.module_base_resouce.R
import com.lib.basiclib.base.activity.BaseNavActivity
import com.lib.basiclib.utils.LogUtils
import com.lib.basiclib.utils.ToastUtils
import com.lib.basiclib.widget.web.ByWebView
import com.lib.basiclib.widget.web.OnByWebClientCallback
import com.lib.basiclib.widget.web.OnTitleProgressCallback
import com.xiaojinzi.component.anno.RouterAnno
import kotlinx.android.synthetic.main.act_web.*


/**
 *
 * @ Author  QinTian
 * @ Date  2020/8/23
 * @ Describe
 *
 */
@RouterAnno(host = "Base", path = "web")
class GlobalWebAct : BaseNavActivity() {

    private var byWebView: ByWebView? = null

    override fun getContentResID() = R.layout.act_web

    override fun isSwipeBackEnable() = true

    override fun isShowBackIconWhite() = false

    override fun isShowBackIconClose()  = true

    override fun initContentView() {



        if(!intent.getBooleanExtra("isString",false)){
            if (!intent.getBooleanExtra("isNews",false)){
                byWebView = ByWebView
                    .with(this)
                    .setWebParent(rootWeb, LinearLayout.LayoutParams(-1, -1))
                    .useWebProgress(ContextCompat.getColor(this, R.color.alivc_blue_1))
                    .setOnTitleProgressCallback(object : OnTitleProgressCallback(){
                        override fun onReceivedTitle(title: String?) {
                          if (intent.getBooleanExtra("isSetTitle",false))  setPageTitle(title?:"")
                        }
                    })
                    .loadUrl(getUrl())
            }else initNewView()
        }else{
            setPageTitle(intent?.getStringExtra("title"))
            byWebView = ByWebView
                .with(this)
                .isOpenResize(intent.getBooleanExtra("isOpenResize",true))
                .setWebParent(rootWeb, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
                .useWebProgress(ContextCompat.getColor(this, R.color.alivc_blue_1))
                .loadForm(getForm())

        }


    }

    //-----更多资讯内容
    private fun initNewView(){
        setVisible(titleNews)
        setPageTitle("内容")
        getNewsInfo()
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    private fun getNewsInfo() {
        MineApi.getMessageInfoWeb(intent?.getStringExtra("infoId") ?: "") {
            onSuccess {
                        tvNewsTitle.text = it.title
                        web_copy.loadDataWithBaseURL(null, it.content, "text/html", "utf-8", null)
                        web_copy.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                        web_copy.settings.javaScriptEnabled = true
                        web_copy.setBackgroundColor(0)
            }
            onFailed {
                ToastUtils.showToast(it.getMsg().toString())
            }
        }
    }


    //===================WebChromeClient 和 WebViewClient===========================//
    /**
     * 页面空白，请检查scheme是否加上， scheme://host:port/path?query&query 。
     *
     * @return mUrl
     */
    private fun getUrl(): String? {
        return intent.getStringExtra("webActUrl")
    }

    private fun getForm(): String? {
        return intent.getStringExtra("webActForm")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        byWebView?.handleFileChooser(requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (byWebView?.handleKeyEvent(keyCode, event) == true) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }


    override fun onPause() {
        super.onPause()
        byWebView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        byWebView?.onResume()
    }

    override fun onDestroy() {
        byWebView?.onDestroy()
        super.onDestroy()
    }
}