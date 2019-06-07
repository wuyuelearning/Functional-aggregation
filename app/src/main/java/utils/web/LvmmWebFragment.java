//package utils.web;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.annotation.RequiresApi;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.webkit.DownloadListener;
//import android.webkit.SslErrorHandler;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebResourceResponse;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//
//
//import com.example.admin.projecttest.R;
//
//import java.io.InputStream;
//import java.lang.annotation.Target;
//import java.lang.ref.WeakReference;
//import java.util.List;
//
//
//import project_utils.NetworkUtil;
//import utils.baseactivity.BaseActivity;
//import utils.baseactivity.BaseFragment;
//import utils.bring.BuildConstant;
//import utils.bring.L;
//import utils.bring.StringUtil;
//
///**
// * Created by liuhuawei on 2016/4/5 WebView相关处理统一在此处理
// */
//@SuppressWarnings("deprecation")
//@SuppressLint("JavascriptInterface")
//@Target(path = "/hybrid/LvmmWebFragment")
//public class LvmmWebFragment extends BaseFragment  {
//    public static final String HOME_TAB3_H5_URL = "https://m.lvmama.com/seek";
//    private static final int MESSAGE_3 = 0x0003;
//    public boolean isErrorLoad = false;//无网络,或者网络差
//    protected WebView webView;
//    protected String mTitle;
//    private LvmmWebView lvmmWebView;
//    private boolean fromMain = false;
//    private HybridController hybridController;
//    private String originUrl = null;
//    private String sessionId;
//    // decodeURL:用H5打开PDF文件时,PDF地址必须encode，此参数用来避开decode
//    private boolean isZoom = false, /*isShowActionBar = false, */isShowCloseView = false,
//            needTitlePlugin = true, richText = false, decodeURL = true, shouldTransfer = true;
//    private boolean isCreateLogin;//onCreate时  是否登陆
//    private boolean isOrderDetail = false;
//    private boolean created = false;
//    private boolean lvtuKey = false;//792 lhw
//    // 记录页面是否加载完成，目的:作为低于7.0机型服务端重定向的判断,未来最低版本到达7.0可以删除，利用系统自带方法判断
//    private boolean mLoaded = false;
//    private WebChromeClient myWebChromeClient = new MyWebChromeClient();
//    private DownloadListener myDownLoadListener = new MyDownloadListener();
//    private Handler handler = new WeakReferenceHandler(this);
//    private MyWebViewClient webViewClient = new MyWebViewClient();
//    private WebEventDriver webEventDriver;
//    private boolean nearBy4ABTest = false;
//
//    /**
//     * 加工url
//     */
//    private void processURL() {
//        if (StringUtil.equalsNullOrEmpty(originUrl)) {
//            getActivity().finish();
//            return;
//        }
//        /*
//        //2018-09-04 8.1.5 去掉 decode，但trim()不能去掉，预防链接前后有空格
//        try {
//            originUrl = java.net.URLDecoder.decode(originUrl.trim(), "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//        originUrl = originUrl.trim();
//        if (originUrl.contains(HyBridUtils.LV_HOST_NAME) || BuildConstant.DEBUG) {
//            //火车票URL不需要默认参数 2015-04-22
//            if (!originUrl.contains("/train") && !originUrl.contains("/flight")) {
//                //792 lhw 设备名称可能有空格，如果WebView加载接口形式的链接时会报签名失败
//                String args;
//                HttpRequestParams params = HttpParamsUtils.initRequestParams(null);
//                if (lvtuKey && params.has("deviceName")) {
//                    params.remove("deviceName");
//                    args = params.toString();
//                } else {
//                    args = params.toString();
//                }
//                if (originUrl.contains("?")) {
//                    originUrl = originUrl + "&" + args;
//                } else {
//                    originUrl = originUrl + "?" + args;
//                }
//                if (lvtuKey) {//792 lhw add
//                    originUrl = lvtuKey792(originUrl);
//                }
//            }
//        }
//    }
//
//    //792 lhw WevView加载url是api3g2.lvmama.com时
//    private String lvtuKey792(String url) {
//        if (StringUtil.equalsNullOrEmpty(url)) {
//            return url;
//        }
//        String key = KeyUtils.getKey(url);
//        return url + "&lvtukey=" + key;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        JSCacheManager.getInstance().init(getContext());
//        this.sessionId = UserUtil.getSessionId(getContext());
//        initIntentExtra();
//        if (!richText && decodeURL) {
//            processURL();
//        }
//        //底部有X则不用再判断
//        if (!isShowCloseView && !richText && shouldTransfer) {
//            if (BusinessTransfer.startActivityUrl(getActivity(), originUrl, mTitle, true)) {
//                ((BaseActivity) getActivity()).finishDirectly();
//            }
//        }
//    }
//
//    /**
//     * 初始化Intent传递的值
//     * <p>url WebView需要加载的网页地址</>
//     * <p>isShowActionBar 是否显示ActionBar(true:显示 false:不显示) -- v8.2.60 由h5控制</>
//     * <p>isShowCloseView 是否显示底部的关闭图标</>
//     * <p>title ActionBar的标题</>
//     */
//    private void initIntentExtra() {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String isBundleValue = bundle.getString("isBundleValue");
//            if ("YES".equals(isBundleValue)) {
//                originUrl = bundle.getString("url");
//                mTitle = bundle.getString("title");
//                isZoom = bundle.getBoolean("isZoom", false);
//                //isShowActionBar = bundle.getBoolean("isShowActionBar", true);
//                isOrderDetail = bundle.getBoolean("isOrderDetail", false);
//                isShowCloseView = bundle.getBoolean("isShowCloseView", false);
//                fromMain = bundle.getBoolean("fromMain", false);
//                needTitlePlugin = bundle.getBoolean("needTitlePlugin", true);
//                richText = bundle.getBoolean("richText", false);
//                decodeURL = bundle.getBoolean("decodeURL", true);
//            }
//        } else {
//            Intent intent = getActivity().getIntent();
//            if (intent != null) {
//                originUrl = intent.getStringExtra("url");
//                mTitle = intent.getStringExtra("title");
//                isZoom = intent.getBooleanExtra("isZoom", false);
//                //isShowActionBar = intent.getBooleanExtra("isShowActionBar", true);
//                isOrderDetail = intent.getBooleanExtra("isOrderDetail", false);
//                isShowCloseView = intent.getBooleanExtra("isShowCloseView", false);
//                shouldTransfer = intent.getBooleanExtra("shouldTransfer", true);//是否需要验证跳转
//                fromMain = intent.getBooleanExtra("fromMain", false);
//                lvtuKey = intent.getBooleanExtra("LVTUKEY", false);
//                nearBy4ABTest = intent.getBooleanExtra("NearBy4ABTest", false);
//                needTitlePlugin = intent.getBooleanExtra("needTitlePlugin", true);
//                richText = intent.getBooleanExtra("richText", false);
//                decodeURL = intent.getBooleanExtra("decodeURL", true);
//            }
//        }
//        L.d("initIntentExtra() originUrl=" + originUrl + "\n,,richText=" + richText + "\n,,decodeURL=" + decodeURL);
//    }
//
//    private void webReload() {
//        if (!NetworkUtil.isNetworkAvailable(getActivity())) {
//            return;
//        }
//        lvmmWebView.webReload();
//        webEventDriver.onReLoadWeb();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (webEventDriver != null) {
//            webEventDriver.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    @Override
//    @SuppressLint("SetJavaScriptEnabled")
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.dt_web, container, false);
//        ProgressBar pb_loading = (ProgressBar) v.findViewById(R.id.topLoadingProcess);
//        View web_error = v.findViewById(R.id.web_error);
//        View web_buttom = v.findViewById(R.id.web_buttom);
//        LinearLayout layout_loading = (LinearLayout) web_buttom.findViewById(R.id.layout_loading);
//        layout_loading.setBackgroundColor(getResources().getColor(R.color.color_f8f8f8));
//        layout_loading.setVisibility(View.GONE);
//        ImageView anim_img = (ImageView) web_buttom.findViewById(R.id.anim_img);
//        //760 统一底部有X的页面
//        LinearLayout llClose = (LinearLayout) v.findViewById(R.id.llClose);
//        ImageView ivClose = (ImageView) v.findViewById(R.id.ivClose);
//        if (isShowCloseView) {
//            llClose.setVisibility(View.VISIBLE);
//            ivClose.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getActivity().finish();
//                    getActivity().overridePendingTransition(0, R.anim.push_bottom_out);
//                }
//            });
//        } else {
//            llClose.setVisibility(View.GONE);
//        }
//        webView = (WebView) v.findViewById(R.id.webView);
//        webView.setOnLongClickListener(new LongClickDownloadImageListener(webView, getActivity()));
//        Button retry = (Button) web_error.findViewById(R.id.retry);
//        retry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                webReload();
//            }
//        });
//        lvmmWebView = new LvmmWebView(getActivity(), webView);
//        lvmmWebView.lvmmWebInit(isZoom);
//        webView.setWebChromeClient(myWebChromeClient);
//        webView.setWebViewClient(webViewClient);
//        webView.setDownloadListener(myDownLoadListener);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WebView.setWebContentsDebuggingEnabled(BuildConstant.DEBUG);
//        }
//        lvmmWebView.enableCrossDomain();
//        webView.removeJavascriptInterface("searchBoxJavaBridge_");
//        webView.removeJavascriptInterface("accessibility");
//        webView.removeJavascriptInterface("accessibilityTraversal");
//
//        WebEventDriver.EventDriverConfig config = new WebEventDriver.EventDriverConfig();
//        config.needTitlePlugin = needTitlePlugin;
//        webEventDriver = new WebEventDriver(this, lvmmWebView, config, originUrl);
//        webEventDriver.onCreateView();
//        webEventDriver.setExitListener(webViewClient);
//        webEventDriver.setShowCloseView(isShowCloseView);
//        webEventDriver.setFromMain(fromMain);
//        webEventDriver.setErrorLayout(web_error, web_buttom);
//        webEventDriver.setLoadingLayout(layout_loading, anim_img, pb_loading);
//        webEventDriver.setOrderDetail(isOrderDetail);
//        webEventDriver.initTitleBar(null);
//        webEventDriver.setLvJsSetTitle(false);
//
//        webView.addJavascriptInterface(new JSCallback(webEventDriver), "lvmm");
//
//        SensorsApi.mShowUpWebView(webView);//打通 App 与 H5 神策v8.2.60 2019-02-21
//
//        webEventDriver.setTitle(TextUtils.isEmpty(mTitle) ? " " : mTitle);
//        //H5页面下单到登录成功回来后需要判断下
//        if ((!StringUtil.equalsNullOrEmpty(originUrl) && originUrl.contains("hideAppHeader=1"))
//                /*|| !isShowActionBar*/) {
//            webEventDriver.hideTitleBar();
//        }
//        if (!StringUtil.equalsNullOrEmpty(originUrl)) {
//            isCreateLogin = UserUtil.isLogin(getActivity());
//            lvmmWebView.cookieSyncManager(webView, sessionId, isCreateLogin);
//        }
//        if (richText) {
//            webView.getSettings().setUseWideViewPort(false);
//            webView.loadDataWithBaseURL("about:blank", originUrl,
//                    "text/html", "utf-8", null);
//        } else {
//            // control
//            hybridController = new HybridController(getContext());
//            hybridController.open(originUrl, this);
//        }
//
//        return v;
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser && created) {
//            webView.loadUrl("javascript:if(window.viewWillAppear){viewWillAppear()}");
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        lvmmWebView.webPauseTimers();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (webView != null) {
//            //解决Receiver not registered: android.widget.ZoomButtonsController 740 LHW 2015-11-18
//            if (isZoom) {
//                lvmmWebView.setWebVisibility(View.GONE);
//                long timeout = ViewConfiguration.getZoomControlsTimeout();
//                Message msg = handler.obtainMessage(MESSAGE_3);
//                handler.sendMessageDelayed(msg, timeout);
//            } else {
//                lvmmWebView.webDestroy();
//            }
//        }
//        if (webEventDriver != null) {
//            webEventDriver.onDestroy();
//        }
//    }
//
//    //不在hybrid控制范围内
////    private void notInHybrid() {  //  todo
////        lvmmWebView.webLoadUrl(originUrl);
////    }
////
////
////    public void onMiss() {
////        notInHybrid();
////    }
////
////    public void onHit(String url) {
////        this.originUrl = url;
////        lvmmWebView.webLoadUrl(url);
////    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (webEventDriver.onKeyDown(keyCode, event)) {
//                return true;
//            } else if (lvmmWebView.webCanGoBack()) {
//                lvmmWebView.webGoBack();
//                return true;
//            } else if (isErrorLoad) {
//                getActivity().finish();
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        // EasyPermissions handles the request result.
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    @Override
//    public void onPermissionsGranted(int i, List<String> list) {
//        if (i == MineBu.PERMISSION_READ_CONTACTS) {
//            webEventDriver.openMineContact();
//        } else if (i == ImageChoosePlugin.PERMISSION_CAMERA) {
//            webEventDriver.showImagePluginDialog();
//        } else if (i == PhoneBu.PERMISSION_READ_CONTACTS) {
//            webEventDriver.openPhoneBook();
//        } else if (i == PhoneBu.PERMISSION_CAMERA) {
//            webEventDriver.openCameraStatus();
//        }
//    }
//
//    @Override
//    public void onPermissionsDenied(int i, List<String> list) {
//        //当用户拒绝H5页面打开本地相机、相册时 无法重复调用WebChromeClient里的方法
//        if (i == ImageChoosePlugin.PERMISSION_CAMERA) {
//            webEventDriver.cancelReceiveValue();
//        } else if (i == PhoneBu.PERMISSION_READ_CONTACTS) {
//            webEventDriver.cancelPhoneBook();
//        } else if (i == PhoneBu.PERMISSION_CAMERA) {
//            webEventDriver.cancelCameraStatus();
//        }
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
//            new AppSettingsDialog.Builder(this).build().show();
//        }
//    }
//
//    public void requestShareState() {
//        webEventDriver.h5RequestShareGift();
//    }
//
//    private static class WeakReferenceHandler extends Handler {
//        private WeakReference<LvmmWebFragment> mOuter;
//
//        private WeakReferenceHandler(LvmmWebFragment fragment) {
//            mOuter = new WeakReference<>(fragment);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            int what = msg.what;
//            switch (what) {
//                case MESSAGE_3:
//                    LvmmWebFragment fragment = mOuter.get();
//                    if (fragment != null) {
//                        fragment.lvmmWebView.webDestroy();
//                    }
//                    break;
//            }
//        }
//    }
//
//    private class MyDownloadListener implements DownloadListener {
//        private void openWithBrowser(String url) {
//            Uri uri = Uri.parse(url);
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            startActivity(intent);
//        }
//
//        @Override
//        public void onDownloadStart(final String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
//            CommAlertDialog dialog = new CommAlertDialog(getActivity(), getString(R.string.dt_download_file) + ":\n" + url, new CommAlertDialog.MyListener() {
//                @Override
//                public void onConfirm() {
//                    openWithBrowser(url);
//                    getActivity().onBackPressed();
//                }
//
//                @Override
//                public void onCancel() {
//                }
//            });
//            dialog.getTitle().setText("下载");
//            dialog.getBt_cancel().setText("稍候再说");
//            dialog.getBt_confirm().setText("立即下载");
//            dialog.show();
//        }
//    }
//
//    private void openFileChooserImpl(ValueCallback<Uri> uploadMessage, ValueCallback<Uri[]> filePathCallbackArray) {
//        webEventDriver.setImagePluginValueCallback(uploadMessage, filePathCallbackArray);
//        webEventDriver.showImagePluginDialog();
//    }
//
//    private class MyWebChromeClient extends WebChromeClient {
//        @Override
//        public void onProgressChanged(WebView view, int newProgress) {
//            webEventDriver.onProgressChanged(newProgress);
//            super.onProgressChanged(view, newProgress);
//        }
//
//        @Override
//        public void onReceivedTitle(WebView view, String title) {
//            //单页面打开多个链接back时此方法不执行，低版本手机
//            if (StringUtil.equalsNullOrEmpty(title)) {
//                return;
//            }
//            super.onReceivedTitle(view, title);
//            webEventDriver.setTitle(title);//取H5页面Title标签 8.1.5
//        }
//
//        @SuppressWarnings("unused")
//        //For Android 4.1
//        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
//            openFileChooserImpl(uploadMsg, null);
//        }
//
//        //Android 5.0+
//        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
//                                         WebChromeClient.FileChooserParams fileChooserParams) {
//            openFileChooserImpl(null, filePathCallback);
//            return true;
//        }
//    }
//
//    class MyWebViewClient extends WebViewClient implements WebEventDriver.ExitListener {
//        private boolean exit = false;
//
//        @Override
//        public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            super.onPageStarted(view, url, favicon);
//            webEventDriver.onStartLoading();
//            webEventDriver.onPageStarted(url);
//        }
//
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            // 重定向发生在页面还未加载完
//            return dealOverrideUrlLoading(view, url, !mLoaded);
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.N)
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            return dealOverrideUrlLoading(view, request.getUrl().toString(), request.isRedirect());
//        }
//
//        /**
//         * 页面内跳转新URL时的处理方式
//         *
//         * @param view     打开链接的 WebView
//         * @param url      待打开的新 url
//         * @param redirect 是否是重定向
//         * @return 是否APP处理
//         */
//        private boolean dealOverrideUrlLoading(WebView view, String url, boolean redirect) {
//            L.d("LvmmWebFragment shouldOverrideUrlLoading url:" + url + "\n,, exit=" + exit
//                    + "\n,, originUrl=" + originUrl);
//            if (exit) {
//                return true;
//            }
//            if (originUrl.equals(url)) {
//                return false;
//            }
//            if (hybridController != null && hybridController.hit(url) != null) {
//                return false;
//            }
//            // URL 拦截处理
//            if (webEventDriver.interceptUrl(url)) {
//                return true;
//            }
//            // 服务端配置关闭多开
//            boolean closeMultiWebView = SharedPreferencesHelper.readBoolean(getActivity().getApplicationContext(),
//                    SharedPreferencesKey.CLOSE_MULTIPLE_WEBVIEW, false);
//            if (webEventDriver.closeMultiple(view.getUrl(), url) || closeMultiWebView) {
//                boolean newWebView = url.contains("newwebview=1");
//                boolean fromMainDestination = false;
//                if ((fromMain || nearBy4ABTest) && !url.equals(HOME_TAB3_H5_URL)) {
//                    fromMainDestination = true;
//                }
//                if (newWebView || fromMainDestination) {
//                    BusinessTransfer.startActivityUrl(getActivity(), url, "", false);
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            if (!redirect) {
//                // 2017/8/8 考虑删除，拦截器里也有尝试native打开的操作
//                boolean b = BusinessTransfer.startActivityUrl(getActivity(), url, "", false);
//                if (!b) {
//                    HyBridUtils.toWebView(getActivity(), url, mTitle, false);
//                }
//                return true;
//            } else {
//                return false;
//            }
//        }
//
//        @Override
//        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//            super.onReceivedError(view, errorCode, description, failingUrl);
//            if ((!StringUtil.equalsNullOrEmpty(failingUrl) && !failingUrl.equals(originUrl)) || errorCode < 0) {
//                if (errorCode < 0) {//2016-04-05 lhw 760
//                    isErrorLoad = true;
//                    webEventDriver.onLoadError();
//                }
//                return;
//            }
//            isErrorLoad = true;
//            webEventDriver.onLoadError();
//            webEventDriver.onStopLoading();
//        }
//
//        @Override
//        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//            handler.proceed();//接受任何证书
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//            webEventDriver.onPageFinished();
//            if (!NetworkUtil.isNetworkAvailable(getActivity())) {
//                isErrorLoad = true;
//                webEventDriver.onLoadError();
//            }
//            webEventDriver.onStopLoading();
//            mLoaded = true;
//            /*
//             * onReceivedTitle在某些Android版本单页面多次打开链接时不执行
//             * lvJSSetTitle
//             * H5 调用 true ；H5 未调用 false
//             */
//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M && !webEventDriver.isLvJsSetTitle()) {
//                webEventDriver.setTitle(view.getTitle());
//            }
//        }
//
//        @Override
//        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
//            super.doUpdateVisitedHistory(view, url, isReload);
//            webEventDriver.updateNavigateButtonState();
//        }
//
//        @Override
//        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//            if (JSCacheManager.matches(url)) {
//                return generateResponse(url);
//            } else {
//                return super.shouldInterceptRequest(view, url);
//            }
//        }
//
//        /**
//         * 优先本地获取js
//         */
//        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//        private WebResourceResponse generateResponse(String url) {
//            WebResourceResponse response = null;
//            InputStream is = JSCacheManager.getInstance().openJsStream(url);
//            if (is != null) {
//                response = new WebResourceResponse(url.contains(".css") ? "text/css" : "text/html", "UTF-8", is);
//            }
//            return response;//本地缓存策略全部失败返回null走原来js加载逻辑
//        }
//
//        @Override
//        public void onExit() {
//            exit = true;
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (!getUserVisibleHint()) {
//            return;
//        }
//        lvmmWebView.webResumeTimers();
//        boolean isUserLogin = UserUtil.isLogin(getActivity());
//        if (isUserLogin && !isCreateLogin) {
//            isCreateLogin = true;
//            sessionId = UserUtil.getSessionId(getActivity());
//            lvmmWebView.cookieSyncManager(webView, sessionId, isCreateLogin);
//        }
//        if (created) {
//            if (ScreenBroadcastReceiver.screenOn) {
//                webView.loadUrl("javascript:if(window.viewWillAppear){viewWillAppear()}");
//            }
//        }
//        created = true;
//    }
//}