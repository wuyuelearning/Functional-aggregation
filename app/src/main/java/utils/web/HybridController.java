package utils.web;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;



import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import utils.baseactivity.CommLoadingDialog;
import utils.bring.AppComponentsHolder;
import utils.bring.JsonUtil;
import utils.bring.L;
import utils.bring.MobileUtil;
import utils.bring.SharedPreferencesHelper;

/**
 * Created by Steve on 2017/3/16.
 */

public class HybridController {
    public static final String HYBRID_CONFIG = "HYBRID_CONFIG";

    private Context context;
    private HybridConfig config;
    private HybridCallBack callBack;
    private Handler handler;
    private String originURL;
    //// TODO: 2017/3/17  需要一个新的加载动画
    private CommLoadingDialog commLoadingDialog = null;

    private OkHttpClient httpClient;

    public HybridController(Context context) {
        this.context = context;
        this.handler = new Handler(Looper.getMainLooper());
        loadConfig(context);
    }

    private OkHttpClient getHttpClient() {
        if (httpClient == null) {
//            httpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        }
        return httpClient;
    }


    /**
     * 加载配置内容
     */
    private void loadConfig(Context context) {
        String content = SharedPreferencesHelper.readString(context.getApplicationContext(), HYBRID_CONFIG);
        this.config = JsonUtil.parseJson(content, HybridConfig.class);
    }

    /**
     * 去配置表里查询URL是否符合hybrid 的规则
     *
     * @param url 待匹配的URL
     * @return 返回匹配上的规则
     */
    public HybridConfig.DatasEntity hit(String url) {
        if (url == null) {
            return null;
        }
        if (config == null) {
            return null;
        }
        List<HybridConfig.DatasEntity> datas = config.getDatas();
        if (datas == null) {
            return null;
        }
        for (HybridConfig.DatasEntity data : datas) {
            String pUrl = data.getPUrl();
            String contentURL = splitURL(url);

            if (pUrl == null || contentURL == null) {
                return null;
            }
            Pattern p = Pattern.compile(pUrl);
            Matcher matcher = p.matcher(contentURL);
            if (matcher.find()) {
                return data;
            }
        }
        return null;
    }

    /**
     * 去除 URL 中的协议和参数
     *
     * @param url 待去除协议和参数的URL
     * @return 返回去除参数和协议之后的URL
     */
    private String splitURL(String url) {
        if (url == null) {
            return null;
        }
        String scheme = "https://";
        if (url.contains(scheme)) {
            return url.replace("https:", "");
        }
        return url.replace("http:", "");
    }

    /**
     * 获取 URL 的path
     *
     * @param url 待处理的URL源
     * @return 返回 path
     */
    private String urlPath(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        String scheme = "https://";
        int index = url.indexOf(scheme);
        if (index < 0) {
            scheme = "http://";
            index = url.indexOf(scheme);
        }
        if (index >= 0) {
            url = url.replace(scheme, "");
        }
        int start = url.indexOf("/");
        int end = url.indexOf("?");
        if (start < 0) {
            return "";
        }
        if (end < 0) {
            end = url.length();
        }
        return url.substring(start, end);
    }

    /**
     * 获取 URL 的参数
     *
     * @param url 待处理的URL源
     * @return 返回参数
     */
    private String urlParams(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        int start = url.indexOf("?");
        if (start < 0) {
            return "";
        }
        return url.substring(start + 1, url.length());
    }

    /**
     * 尝试用hybrid 打开URL
     *
     * @param url      待匹配的 URL
     * @param callBack 回调函数对象
     */
    public void open(String url, HybridCallBack callBack) {
        this.originURL = url;
        this.callBack = callBack;
        dealURL(url);
    }

    /**
     * 获取本地已有包的版本
     *
     * @param bName 包名
     * @return 返回md5 版本号
     */
    private String getLocalVersion(String bName) {
        String versionMd5 = SharedPreferencesHelper.readString(context, bName + "VERSIONMD5");
        return versionMd5;
    }

    /**
     * 处理打开的URL，正式处理
     *
     * @param url 待处理的 URL
     */
    private void dealURL(String url) {
        HybridConfig.DatasEntity entity = hit(url);
        if (entity == null) {
            callBack.onMiss();
        } else {
            String openType = entity.getOpenType();
            if ("H5Online".equals(openType)) {
                h5Online();
            } else if ("H5Local".equals(openType)) {
                h5Local(entity);
            } else if ("RN".equals(openType)) {
                // TODO 目前不处理,不会走到这儿
                miss();
            } else {
                miss();
            }
        }
    }

    private void h5Local(HybridConfig.DatasEntity entity) {
        final String bName = entity.getBName();
        final String configVersion = entity.getMd5();
        String localVersion = getLocalVersion(bName);
        // 配置文件未获取到版本
        if (TextUtils.isEmpty(configVersion)) {
            h5Online();
        } else if (configVersion.equals(localVersion) && hasBundle(bName)) {
            // 版本一致，不需要更新，直接本地化打开
            h5Local(bName);
        } else {
            // 下载更新，打开
            downloadBundles(bName, entity.getBUrl(), new DownloadBundleRunnable.TaskListener() {
                @Override
                public void onComplete() {
                    finish();
                    SharedPreferencesHelper.saveString(context, bName + "VERSIONMD5", configVersion);
                    h5Local(bName);
                }

                @Override
                public void onFailed() {
                    finish();
                    h5Online();
                }
            }, false);
        }
    }

    public void dealFS(HybridConfig.DatasEntity entity, DownloadBundleRunnable.TaskListener taskListener) {
        final String bName = entity.getBName();
        final String configVersion = entity.getMd5();
        String localVersion = getLocalVersion(bName);
        if (TextUtils.isEmpty(bName) || TextUtils.isEmpty(configVersion)) {
            return;
        }
        if ("RN".equals(entity.getOpenType()) && configVersion.equals(localVersion) && hasBundleFile()) {//rn的处理
            return;
        } else if (configVersion.equals(localVersion) && hasBundle(bName)) {
            return;
        }
        downloadBundles(bName, entity.getBUrl(), taskListener, true);
    }

    /**
     * 下载本地化包结束
     */
    private void finish() {
        dialogDismiss();
    }

    /**
     * 未命中
     */
    private void miss() {
        callBack.onMiss();
    }

    /**
     * h5 online 的方式打开
     */
    private void h5Online() {
        callBack.onHit(originURL);
    }

    /**
     * H5 本地化打开
     */
    private void h5Local(String bName) {
        String bundlePath = getBundlePath(bName);
        if (TextUtils.isEmpty(bundlePath)) {
            h5Online();
        } else {
            String url = "file://" + bundlePath + File.separator + "index.html"
                    + "?" + urlParams(originURL)
                    + "&originPath=" + urlPath(originURL);
            callBack.onHit(url);
        }
    }


    /**
     * 下载本地化包，承担更新本地化包的功能
     *
     * @param bundleName   需要下载的 bundle 名称
     * @param bUrl         需要下载的 bundle url
     * @param taskListener 成功失败的回调
     * @param isFS         是否是首屏下载
     */
    private void downloadBundles(String bundleName, String bUrl, DownloadBundleRunnable.TaskListener taskListener, boolean isFS) {
        if (!isFS) {
            dialogShow(false);
        }
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new DownloadBundleRunnable(getHttpClient(), bundleName, bUrl, taskListener));
    }

    /**
     * 获取本地化包的地址
     *
     * @param bName 包名
     * @return 本地化包的地址，null 表示加载失败
     */
    private String getBundlePath(String bName) {
        String dir = MobileUtil.getSDPath();
        if (TextUtils.isEmpty(dir)) {
            File cacheDir = AppComponentsHolder.inst().getApplication().getCacheDir();
            dir = cacheDir.getAbsolutePath();
        }
        if (!TextUtils.isEmpty(dir)) {
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir = dir + "lvmama" + File.separator + bName;
        }
        L.d("h5 getH5UrlPath() last dir:" + dir);
        return dir;
    }

    /**
     * 是否存在bundleName
     *
     * @param bName 包名
     * @return true表示存在，反之不存在
     */
    private boolean hasBundle(String bName) {
        String dir = MobileUtil.getSDPath();
        if (TextUtils.isEmpty(dir)) {
            File cacheDir = AppComponentsHolder.inst().getApplication().getCacheDir();
            dir = cacheDir.getAbsolutePath();
        }
        if (!TextUtils.isEmpty(dir)) {
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir = dir + "lvmama" + File.separator + bName + File.separator + "index.html";
        }
        File file = new File(dir);
        return file.exists();
    }

    /**
     * 是否存在.bundle文件
     *
     * @return true表示存在，反之不存在
     */
    private boolean hasBundleFile() {
        String dir = MobileUtil.getSDPath();
        if (TextUtils.isEmpty(dir)) {
            File cacheDir = AppComponentsHolder.inst().getApplication().getCacheDir();
            dir = cacheDir.getAbsolutePath();
        }
        if (!TextUtils.isEmpty(dir)) {
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir = dir + "lvmama" + File.separator + "index.android.bundle";
        }
        File file = new File(dir);
        return file.exists();
    }


    private void dialogDismiss() {
        if (context == null) {
            commLoadingDialog = null;
            return;
        }
        if (commLoadingDialog != null && commLoadingDialog.isShowing()) {
            commLoadingDialog.stopAnimation();
            commLoadingDialog.dismiss();
        }
    }

    private void dialogShow(boolean flag) {
        if (context == null) {
            return;
        }
        if (commLoadingDialog == null) {
            commLoadingDialog = new CommLoadingDialog(context);
        }
        commLoadingDialog.setCanceledOnTouchOutside(flag);
        commLoadingDialog.startAnimation();
        if (commLoadingDialog.isShowing()) {
            return;
        }
        if (context != null) {
            commLoadingDialog.show();
        }
    }

    public interface HybridCallBack {

        void onMiss();

        void onHit(String url);
    }

}
