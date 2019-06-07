//package utils.baseactivity;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.content.res.Resources;
//import android.os.Build;
//import android.support.annotation.NonNull;
//import android.util.Base64;
//
//import com.google.gson.GsonBuilder;
//
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.Locale;
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.Executor;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.zip.GZIPOutputStream;
//
///**
// * <p>Author:MrIcefox
// * <p>Email:extremetsa@gmail.com
// * <p>Description:
// * <p>Date:2018/6/6
// */
//public class Trace {
//    private Trace() {
//    }
//
//    public static void restAppActionPost(Context context) {
//        Map<String, Object> info = new LinkedHashMap<>();
//        info.put("bdcsessionid", "AD" + System.currentTimeMillis() + UUID.randomUUID().toString());
//        info.put("sessionid", UUID.randomUUID().toString());
//        info.put("userid", getUserId(context));
//        info.put("userno", getUserNo(context));
//        info.put("username", getUserName(context));
//        info.put("deviceid", HttpParamsUtils.uidId(context));
//        info.put("appid", context.getPackageName());
//        info.put("appversion", AppUtil.getAppVersionName(context, false));
//        info.put("hxsdkversion", "1.0.0");
//        info.put("appstatus", 1);
//        info.put("createtime", new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date()));
//        info.put("isfirststart", isFirstStart(context) ? 1 : 0);
//        info.put("isfirstlogin", SharedPreferencesHelper.readBoolean(context, UserUtil.SP_IS_FIRST_LOGIN) ? 1 : 0);
//        info.put("logindate", getLoginDate(context));
//        info.put("ua", HttpParamsUtils.getUserAgent(context));
//        info.put("bsfit_deviceid", MemoryPayload.dfp);
//        info.put("bundleid", context.getPackageName());
//
//        String gpsCity = LocationUtil.getLocationInfo(context) == null ? "" : LocationUtil.getLocationInfo(context).city;
//        CitySelectedModel csm = LocationUtil.getSelectedCity(context);
//        String stationCity = csm.getStationName();
//        String stationCode = csm.getStationCode();
//        info.put("station_city_id", stationCode);
//        info.put("station_city_name", stationCity);
//        info.put("ip_city_name", gpsCity);
//
//        String byteValue = buildByteValue(info);
//        ApiProvider.postData(context, Urls.UrlEnum.BDC_REST_APP_ACTION_POST, new HttpRequestParams
//                ("byteValue", byteValue), new HttpCallback(false) {
//            @Override
//            public void onSuccess(String response) {
//                L.d("bdc_trace", "restAppActionPost onSuccess resp:" + response);
//            }
//
//            @Override
//            public void onFailure(int status, Throwable error) {
//                L.d("bdc_trace", "restAppActionPost onFailure status:" + status
//                        + " error:" + error);
//            }
//
//            @Override
//            public void onIntercept() {
//                L.d("bdc_trace", "restAppActionPost onIntercept");
//            }
//        });
//    }
//
//    public static void restMobileInfoPost(Context context) {
//        SharedPreferences sp = context.getSharedPreferences("bdc_sp_file", 0);
//        PackageInfo packageInfo;
//        try {
//            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException ignored) {
//            return;
//        }
//        //首次安装
//        if (packageInfo.lastUpdateTime == packageInfo.firstInstallTime) {
//            if (sp.getBoolean("app_first_install_started", false)) {
//                return;//启动过了
//            }
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putBoolean("app_first_install_started", true);
//            editor.apply();
//        } else {//升级安装
//            if (sp.getBoolean("app_update_install_started", false)) {
//                return;//启动过了
//            }
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putBoolean("app_update_install_started", true);
//            editor.apply();
//        }
//        Map<String, Object> info = new LinkedHashMap<>();
//        info.put("bdcsessionid", "AD" + System.currentTimeMillis() + UUID.randomUUID().toString());
//        info.put("deviceid", HttpParamsUtils.uidId(context));
//        info.put("appid", context.getPackageName());
//        info.put("appversion", AppUtil.getAppVersionName(context, false));
//        info.put("hxsdkversion", "1.0.0");
//        info.put("channel", ChannelUtil.getChannel(context));
//        info.put("installtime", getApkInstallTime(context));
//        info.put("screen", Resources.getSystem().getDisplayMetrics().widthPixels + " * " +
//                Resources.getSystem().getDisplayMetrics().heightPixels);
//        info.put("platform", "Android");
//        info.put("osversion", Build.VERSION.RELEASE);
//        info.put("model", Build.MODEL);
//        info.put("manufacturer", Build.BRAND);
//        info.put("ua", HttpParamsUtils.getUserAgent(context));
//
//        String gpsCity = LocationUtil.getLocationInfo(context) == null ? "" : LocationUtil.getLocationInfo(context).city;
//        CitySelectedModel csm = LocationUtil.getSelectedCity(context);
//        String stationCity = csm.getStationName();
//        String stationCode = csm.getStationCode();
//        info.put("station_city_id", stationCode);
//        info.put("station_city_name", stationCity);
//        info.put("ip_city_name", gpsCity);
//
//        String byteValue = buildByteValue(info);
//        ApiProvider.postData(context, Urls.UrlEnum.BDC_REST_MOBILE_INFO_POST,
//                new HttpRequestParams("byteValue", byteValue), new HttpCallback(false) {
//            @Override
//            public void onSuccess(String response) {
//                L.d("bdc_trace", "restMobileInfoPost onSuccess resp:" + response);
//            }
//
//            @Override
//            public void onFailure(int status, Throwable error) {
//                L.d("bdc_trace", "restMobileInfoPost onFailure status:" + status
//                        + " error:" + error);
//            }
//
//            @Override
//            public void onIntercept() {
//                L.d("bdc_trace", "restMobileInfoPost onIntercept");
//            }
//        });
//    }
//
//    private static final Executor exec = new ThreadPoolExecutor(1, 1,
//            10, TimeUnit.SECONDS,
//            new LinkedBlockingQueue<Runnable>(5), new ThreadFactory() {
//        private final AtomicInteger index = new AtomicInteger();
//
//        @Override
//        public Thread newThread(@NonNull Runnable r) {
//            return new Thread(r, "bdc" + "-" + index.getAndIncrement());
//        }
//    });
//
//
//    public static void enqueueRestMobileUserActionPost(final Context context, final String pageName,
//                                                       final String productId) {
//        exec.execute(new Runnable() {
//            @Override
//            public void run() {
//                restMobileUserActionPost(context, pageName, productId);
//            }
//        });
//    }
//
//    private static void restMobileUserActionPost(Context context, String pageName, String productId) {
//        Map<String, Object> info = new LinkedHashMap<>();
//        info.put("bdcsessionid", "AD" + System.currentTimeMillis() + UUID.randomUUID().toString());
//        info.put("sessionid", "AD" + System.currentTimeMillis() + UUID.randomUUID().toString());
//        info.put("userid", getUserId(context));
//        info.put("username", getUserName(context));
//        info.put("userno", getUserNo(context));
//        info.put("deviceid", HttpParamsUtils.uidId(context));
//        info.put("appid", context.getPackageName());
//        info.put("appversion", AppUtil.getAppVersionName(context, false));
//        info.put("hxsdkversion", "1.0.0");
//        info.put("pagename", pageName);
//        info.put("opentime", new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date()));
//        LocationInfoModel locationInfo = LocationUtil.getLocationInfo(context, false);
//        info.put("latitude", locationInfo.latitude);
//        info.put("longitude", locationInfo.longitude);
//        info.put("createtime", new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(new Date()));
//        info.put("bdcprod_id", productId);
//        info.put("bdcgood_ids", null);
//        info.put("first_refer_code", null);
//
//        String gpsCity = LocationUtil.getLocationInfo(context) == null ? "" : LocationUtil.getLocationInfo(context).city;
//        CitySelectedModel csm = LocationUtil.getSelectedCity(context);
//        String stationCity = csm.getStationName();
//        String stationCode = csm.getStationCode();
//        info.put("station_city_id", stationCode);
//        info.put("station_city_name", stationCity);
//        info.put("ip_city_name", gpsCity);
//
//        String byteValue = buildByteValue(info);
//        ApiProvider.postData(context, Urls.UrlEnum.BDC_REST_MOBILE_USER_ACTION_POST,
//                new HttpRequestParams("byteValue", byteValue), new HttpCallback(false) {
//            @Override
//            public void onSuccess(String response) {
//                L.d("bdc_trace", "restMobileUserActionPost onSuccess resp:" + response);
//            }
//
//            @Override
//            public void onFailure(int status, Throwable error) {
//                L.d("bdc_trace", "restMobileUserActionPost onFailure status:" + status
//                        + " error:" + error);
//            }
//
//            @Override
//            public void onIntercept() {
//                L.d("bdc_trace", "restMobileUserActionPost onIntercept");
//            }
//        });
//    }
//
//    private static boolean isFirstStart(Context context) {
//        SharedPreferences sp = context.getSharedPreferences("bdc_sp_file", 0);
//        boolean r = sp.getBoolean("bdc_isfirststart", true);
//        if (r) {
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putBoolean("bdc_isfirststart", false);
//            editor.apply();
//        }
//        return r;
//    }
//
//    private static String buildByteValue(Map<String, Object> src) {
//        String json = new GsonBuilder().serializeNulls().create().toJson(src);
//        try {
//            return gzip(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String gzip(String source) throws IOException {
//        if (source == null || source.length() == 0) {
//            return source;
//        }
//        ByteArrayOutputStream bout = new ByteArrayOutputStream();
//        GZIPOutputStream gout = new GZIPOutputStream(bout);
//        gout.write(source.getBytes());
//        gout.flush();
//        gout.close();
//        return Base64.encodeToString(bout.toByteArray(), Base64.NO_WRAP);
//    }
//
//    private static String getUserId(Context context) {
//        UserInfo userInfo = UserUtil.getUserInfo(context);
//        if (userInfo != null && userInfo.loginData != null) {
//            if (!StringUtil.equalsNullOrEmpty(userInfo.loginData.id)) {
//                return userInfo.loginData.id;
//            }
//            return userInfo.loginData.userId;
//        }
//        return null;
//    }
//
//    private static String getUserName(Context context) {
//        UserInfo userInfo = UserUtil.getUserInfo(context);
//        if (userInfo != null && userInfo.loginData != null) {
//            if (!StringUtil.equalsNullOrEmpty(userInfo.loginData.userName)) {
//                return userInfo.loginData.userName;
//            }
//            return userInfo.loginData.userName;
//        }
//        return null;
//    }
//
//    private static String getUserNo(Context context) {
//        UserInfo userInfo = UserUtil.getUserInfo(context);
//        if (userInfo != null && userInfo.loginData != null) {
//            if (!StringUtil.equalsNullOrEmpty(userInfo.loginData.userNo)) {
//                return userInfo.loginData.userNo;
//            }
//            return userInfo.loginData.userNo;
//        }
//        return null;
//    }
//
//    private static String getLoginDate(Context context) {
//        String s = SharedPreferencesHelper.read(context, UserUtil.SP_KEY_LOGIN_SUCCESS_DATE);
//        if (s == null || s.trim().isEmpty()) {
//            return null;
//        }
//        try {
//            return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(
//                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(s));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static String getApkInstallTime(Context context) {
//        PackageInfo packageInfo;
//        try {
//            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            return null;
//        }
//        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.US).format(packageInfo.lastUpdateTime);
//    }
//}