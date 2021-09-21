package com.lib.basiclib.widget.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.fh.basemodle.R;
import com.lib.basiclib.utils.LogUtils;

/**
 * 网页可以处理:
 * 点击相应控件：
 * - 进度条显示
 * - 上传图片(版本兼容)
 * - 全屏播放网络视频
 * - 唤起微信支付宝
 * - 拨打电话、发送短信、发送邮件
 * - 返回网页上一层、显示网页标题
 * JS交互部分：
 * - 前端代码嵌入js(缺乏灵活性)
 * - 网页自带js跳转
 */
public class ByWebView {

    private WebView mWebView;
    private WebProgress mProgressBar;
    private View mErrorView;
    private int mErrorLayoutId;
    private String mErrorTitle;
    private Activity activity;
    private ByWebChromeClient mWebChromeClient;
    private ByLoadJsHolder byLoadJsHolder;


    private ByWebView(Builder builder) {
        this.activity = builder.mActivity;
        this.mErrorTitle = builder.mErrorTitle;
        this.mErrorLayoutId = builder.mErrorLayoutId;

        FrameLayout parentLayout = new FrameLayout(activity);
        // 设置WebView

        setWebView(builder.mCustomWebView);
        parentLayout.addView(mWebView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 进度条布局
        handleWebProgress(builder, parentLayout);
        if (builder.mIndex != -1) {
            builder.mWebContainer.addView(parentLayout, builder.mIndex, builder.mLayoutParams);
        } else {
            builder.mWebContainer.addView(parentLayout, builder.mLayoutParams);
        }
        // 配置
        handleSetting(builder.isOpenResize);
//        try {
//            if (mWebView.getX5WebViewExtension() != null) {
//                Bundle data = new Bundle();
//                data.putBoolean("standardFullScreen", false);
//                data.putBoolean("supportLiteWnd", false);
//                data.putInt("DefaultVideoScreen", 1);
//                mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        // 视频、照片、进度条
        mWebChromeClient = new ByWebChromeClient(activity, this);
        mWebChromeClient.setOnByWebChromeCallback(builder.mOnTitleProgressCallback);
        mWebView.setWebChromeClient(mWebChromeClient);
        // 错误页面、页面结束、处理DeepLink
        ByWebViewClient mByWebViewClient = new ByWebViewClient(activity, this);
        mByWebViewClient.setOnByWebClientCallback(builder.mOnByWebClientCallback);
        mWebView.setWebViewClient(mByWebViewClient);

        handleJsInterface(builder);
    }

    /**
     * 配置自定义的WebView
     */
    private void setWebView(WebView mCustomWebView) {
        if (mCustomWebView != null) {
            mWebView = mCustomWebView;
        } else {
            mWebView = new WebView(activity);
        }
    }

    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface"})
    private void handleJsInterface(Builder builder) {
        if (!TextUtils.isEmpty(builder.mInterfaceName) && builder.mInterfaceObj != null) {
            mWebView.addJavascriptInterface(builder.mInterfaceObj, builder.mInterfaceName);
        }
    }

    public ByLoadJsHolder getLoadJsHolder() {
        if (byLoadJsHolder == null) {
            byLoadJsHolder = new ByLoadJsHolder(mWebView);
        }
        return byLoadJsHolder;
    }



    @SuppressLint("SetJavaScriptEnabled")
    private void handleSetting(Boolean isOpenResize) {
        WebSettings ws = mWebView.getSettings();
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 网页内容的宽度自适应屏幕
        if (isOpenResize){
            ws.setLoadWithOverviewMode(true);
            ws.setUseWideViewPort(true);
        }
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
//        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setMediaPlaybackRequiresUserGesture(true);
        // 排版适应屏幕
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        } else {
                ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        // WebView是否新窗口打开(加了后可能打不开网页)
//        ws.setSupportMultipleWindows(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // WebView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
            ws.setMixedContentMode(1);
        }
    }

    /**
     * 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)
     *
     * @param textZoom 默认100
     */
    public void setTextZoom(int textZoom) {
        mWebView.getSettings().setTextZoom(textZoom);
    }

    private void handleWebProgress(Builder builder, FrameLayout parentLayout) {
        if (builder.mUseWebProgress) {
            mProgressBar = new WebProgress(activity);
            if (builder.mProgressStartColor != 0 && builder.mProgressEndColor != 0) {
                mProgressBar.setColor(builder.mProgressStartColor, builder.mProgressEndColor);
            } else if (builder.mProgressStartColor != 0) {
                mProgressBar.setColor(builder.mProgressStartColor, builder.mProgressStartColor);
            } else if (!TextUtils.isEmpty(builder.mProgressStartColorString)
                    && !TextUtils.isEmpty(builder.mProgressEndColorString)) {
                mProgressBar.setColor(builder.mProgressStartColorString, builder.mProgressEndColorString);
            } else if (!TextUtils.isEmpty(builder.mProgressStartColorString)
                    && TextUtils.isEmpty(builder.mProgressEndColorString)) {
                mProgressBar.setColor(builder.mProgressStartColorString, builder.mProgressStartColorString);
            }
            int progressHeight = ByWebTools.dip2px(parentLayout.getContext(), WebProgress.WEB_PROGRESS_DEFAULT_HEIGHT);
            if (builder.mProgressHeightDp != 0) {
                mProgressBar.setHeight(builder.mProgressHeightDp);
                progressHeight = ByWebTools.dip2px(parentLayout.getContext(), builder.mProgressHeightDp);
            }
            mProgressBar.setVisibility(View.GONE);
            parentLayout.addView(mProgressBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, progressHeight));
        }
    }

    public void loadUrl(String url) {
        LogUtils.INSTANCE.e("-------->>>"+url);
        Log.e("-------->>>",url);
        if (!TextUtils.isEmpty(url) && url.endsWith("mp4") && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mWebView.loadData(ByWebTools.getVideoHtmlBody(url), "text/html", "UTF-8");
        } else {
            mWebView.loadUrl(url);
        }
        if (mProgressBar != null) {
            mProgressBar.show();
        }
        hideErrorView();
    }

    public  void loadForm(String form){
        mWebView.loadDataWithBaseURL(null,form, "text/html; charset=UTF-8", "utf-8",null);
        if (mProgressBar != null) {
            mProgressBar.show();
        }
        hideErrorView();
    }

    public void reload() {
        hideErrorView();
        mWebView.reload();
    }

    public void onResume() {
        mWebView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        mWebView.resumeTimers();
    }

    public void onPause() {
        mWebView.onPause();
        mWebView.resumeTimers();
    }

    public void onDestroy() {
        if (mWebChromeClient != null && mWebChromeClient.getVideoFullView() != null) {
            mWebChromeClient.getVideoFullView().removeAllViews();
        }
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
    }

    /**
     * 选择图片之后的回调，在Activity里onActivityResult调用
     */
    public void handleFileChooser(int requestCode, int resultCode, Intent intent) {
        if (mWebChromeClient != null) {
            mWebChromeClient.handleFileChooser(requestCode, resultCode, intent);
        }
    }

    public boolean handleKeyEvent(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return isBack();
        }
        return false;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public boolean isBack() {
        // 全屏播放退出全屏
        if (mWebChromeClient.inCustomView()) {
            mWebChromeClient.onHideCustomView();
            return true;

            // 返回网页上一页
        } else if (mWebView.canGoBack()) {
            hideErrorView();
            mWebView.goBack();
            return true;
        }
        return false;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public WebProgress getProgressBar() {
        return mProgressBar;
    }

    /**
     * 显示错误布局
     */
    public void showErrorView() {
        try {
            if (mErrorView == null) {
                FrameLayout parent = (FrameLayout) mWebView.getParent();
                mErrorView = LayoutInflater.from(parent.getContext()).inflate((mErrorLayoutId == 0) ? R.layout.by_load_url_error : mErrorLayoutId, null);
                mErrorView.setOnClickListener(v -> reload());
                parent.addView(mErrorView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                mErrorView.setVisibility(View.VISIBLE);
            }
            mWebView.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏错误布局
     */
    public void hideErrorView() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }
    }

    public View getErrorView() {
        return mErrorView;
    }

    String getErrorTitle() {
        return mErrorTitle;
    }

    public static Builder with(@NonNull Activity activity) {
        if (activity == null) {
            throw new NullPointerException("activity can not be null .");
        }
        return new Builder(activity);
    }


    public static class Builder {
        private Activity mActivity;
        private Fragment mFragment;
        // 默认使用进度条
        private boolean mUseWebProgress = true;
        // 进度条 开始颜色
        private int mProgressStartColor;
        private String mProgressStartColorString;
        // 进度条 结束颜色
        private int mProgressEndColor;
        private String mProgressEndColorString;
        // 进度条 高度
        private int mProgressHeightDp;
        private int mErrorLayoutId;
        private int mIndex = -1;
        private String mErrorTitle;
        private WebView mCustomWebView;
        private String mInterfaceName;
        private Object mInterfaceObj;
        private ViewGroup mWebContainer;
        private ViewGroup.LayoutParams mLayoutParams;
        private OnTitleProgressCallback mOnTitleProgressCallback;
        private OnByWebClientCallback mOnByWebClientCallback;

        public Builder(Activity activity) {
            this.mActivity = activity;
        }

        public Builder(Activity activity, Fragment fragment) {
            this.mActivity = activity;
            this.mFragment = fragment;
        }

        private Boolean isOpenResize = true;
        public  Builder isOpenResize(Boolean open){
            this.isOpenResize = open;
            return this;
        }

        /**
         * WebView容器
         */
        public Builder setWebParent(@NonNull ViewGroup webContainer, ViewGroup.LayoutParams layoutParams) {
            this.mWebContainer = webContainer;
            this.mLayoutParams = layoutParams;
            return this;
        }

        /**
         * WebView容器
         *
         * @param webContainer 外部WebView容器
         * @param index        加入的位置
         * @param layoutParams 对应的LayoutParams
         */
        public Builder setWebParent(@NonNull ViewGroup webContainer, int index, ViewGroup.LayoutParams layoutParams) {
            this.mWebContainer = webContainer;
            this.mIndex = index;
            this.mLayoutParams = layoutParams;
            return this;
        }

        /**
         * @param isUse 是否使用进度条，默认true
         */
        public Builder useWebProgress(boolean isUse) {
            this.mUseWebProgress = isUse;
            return this;
        }

        /**
         * 设置进度条颜色
         *
         * @param color 示例：ContextCompat.getColor(this, R.color.red)
         */
        public Builder useWebProgress(int color) {
            return useWebProgress(color, color, 3);
        }

        /**
         * 设置进度条颜色
         *
         * @param color 示例："#FF0000"
         */
        public Builder useWebProgress(String color) {
            return useWebProgress(color, color, 3);
        }

        /**
         * 设置进度条渐变色颜色
         *
         * @param startColor 开始颜色
         * @param endColor   结束颜色
         * @param heightDp   进度条高度，单位dp
         */
        public Builder useWebProgress(int startColor, int endColor, int heightDp) {
            mProgressStartColor = startColor;
            mProgressEndColor = endColor;
            mProgressHeightDp = heightDp;
            return this;
        }

        public Builder useWebProgress(String startColor, String endColor, int heightDp) {
            mProgressStartColorString = startColor;
            mProgressEndColorString = endColor;
            mProgressHeightDp = heightDp;
            return this;
        }

        /**
         * @param customWebView 自定义的WebView
         */
        public Builder setCustomWebView(WebView customWebView) {
            mCustomWebView = customWebView;
            return this;
        }

        /**
         * @param errorLayoutId 错误页面布局，标题默认“网页打开失败”
         */
        public Builder setErrorLayout(@LayoutRes int errorLayoutId) {
            mErrorLayoutId = errorLayoutId;
            return this;
        }

        /**
         * @param errorLayoutId 错误页面布局
         * @param errorTitle    错误页面标题
         */
        public Builder setErrorLayout(@LayoutRes int errorLayoutId, String errorTitle) {
            mErrorLayoutId = errorLayoutId;
            mErrorTitle = errorTitle;
            return this;
        }

        /**
         * 添加Js监听
         */
        public Builder addJavascriptInterface(String interfaceName, Object interfaceObj) {
            this.mInterfaceName = interfaceName;
            this.mInterfaceObj = interfaceObj;
            return this;
        }

        /**
         * @param onTitleProgressCallback 返回Title 和 Progress
         */
        public Builder setOnTitleProgressCallback(OnTitleProgressCallback onTitleProgressCallback) {
            this.mOnTitleProgressCallback = onTitleProgressCallback;
            return this;
        }

        /**
         * 页面加载结束监听 和 处理三方跳转链接
         */
        public Builder setOnByWebClientCallback(OnByWebClientCallback onByWebClientCallback) {
            this.mOnByWebClientCallback = onByWebClientCallback;
            return this;
        }

        public ByWebView getWeb(){
            return new ByWebView(this);
        }



        public ByWebView loadUrl(String url) {
            ByWebView byWebView = new ByWebView(this);
            byWebView.loadUrl(url);
            return byWebView;
        }

        public ByWebView loadForm(String form) {
            ByWebView byWebView = new ByWebView(this);
            byWebView.loadForm(form);
            return byWebView;
        }
    }

}
