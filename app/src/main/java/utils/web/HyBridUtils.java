package utils.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;


import java.util.List;
import java.util.Random;

import utils.baseactivity.MemoryPayload;
import utils.bring.JsonUtil;
import utils.bring.SharedPreferencesHelper;

/**
 * Created by LHW on 2017/12/27.
 * V8.0.0 Hybrid工具类
 */
public class HyBridUtils {
    public static final String LV_HOST_NAME = "lvmama.com";
    public static final String PDF_PLUGIN = "https://m.lvmama.com/static/plugins/nativeJs/pdfViewer/index.html?url=";

    public static final String CATEGORY_TRAFFIC_TRAIN = "CATEGORY_TRAFFIC_TRAIN";
    public static final String CATEGORY_TRAFFIC_FLIGHT = "CATEGORY_TRAFFIC_FLIGHT";
    public static final String CATEGORY_TRAFFIC_BUS = "CATEGORY_TRAFFIC_BUS";
    public static final String CATEGORY_TRAFFIC_CAR = "CATEGORY_TRAFFIC_CAR";
    public static final String CATEGORY_TRAFFIC_VIP = "CATEGORY_TRAFFIC_VIP";
    public static final String CATEGORY_NS_ROUTE = "CATEGORY_NS_ROUTE";

    public static boolean returnCruise(String fatherCode) {
        return "CATEGORY_CRUISE".equals(fatherCode);
    }

    public static boolean returnNewRetail(String fatherCode) {
        return "NEWRETAIL".equals(fatherCode);
    }

    public static boolean returnHotel(String fatherCode) {
        return "CATEGORY_HOTEL".equals(fatherCode);
    }

    public static boolean returnTrain(String fatherCode) {
        return CATEGORY_TRAFFIC_TRAIN.equals(fatherCode);
    }

    public static boolean returnPlane(String fatherCode) {
        return CATEGORY_TRAFFIC_FLIGHT.equals(fatherCode);
    }

    public static boolean returnIntlAeroPlane(String fatherCode) {
        return "CATEGORY_TRAFFIC_INTL_AEROPLANE".equals(fatherCode);
    }

    public static boolean returnBus(String fatherCode) {
        return CATEGORY_TRAFFIC_BUS.equals(fatherCode);
    }

    public static boolean returnCar(String fatherCode) {
        return CATEGORY_TRAFFIC_CAR.equals(fatherCode);
    }

    public static boolean returnVip(String fatherCode) {
        return CATEGORY_TRAFFIC_VIP.equals(fatherCode);
    }

    public static boolean returnFinance(String fatherCode) {
        return "CATEGORY_FINANCE".equals(fatherCode);
    }

    //810 拼团 lhw 2018-06-13
    public static boolean returnPINTUAN(String fatherCode) {
        return "CATEGORY_PINTUAN".equals(fatherCode);
    }

    //813 租车订单 lhw 2018-08-02
    public static boolean returnRentCar(String fatherCode) {
        return "CATEGORY_TRAFFIC_DRIVE".equals(fatherCode);
    }

    //816 NS系统订单 lhw 2018-09-11
    public static boolean returnNSOrder(String queryTypes) {
        return "CATEGORY_TRAFFIC_NS".equals(queryTypes);
    }

    public static boolean returnNSRoute(String fatherCode){
        return CATEGORY_NS_ROUTE.equals(fatherCode);
    }

    public static void toLogin(Context context, String url, String title) {
        Intent loginIntent = new Intent();
        Bundle b = new Bundle();
        b.putString(CommTransferKeys.TRANSFER_FROM, "webview");
        b.putString("url", url);
        b.putString("title", title);
        loginIntent.putExtra(CommTransferKeys.TRANSFER_BUNDLE, b);
//        LvmmTransfer.startActivity(context, "account/LoginActivity", loginIntent);
    }

    public static void toWebView(Context context, String url, String title, boolean isShowBar) {
        Intent webIntent = new Intent();
        webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        webIntent.putExtra("url", url);
        webIntent.putExtra("title", title);
        webIntent.putExtra("isShowActionBar", isShowBar);
//        LvmmTransfer.startActivity(context, "hybrid/WebViewActivity", webIntent);
    }

    public static void intentToMain(Activity activity, int indexTab) {
        if (indexTab > 4 || indexTab < 0) {
            indexTab = 0;
        }
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        MemoryPayload.indexTab = indexTab;
//        LvmmTransfer.startActivity(activity, "app/MainActivity", intent);
    }

    public static String getIp() {
        String ip = "";
        Random random = new Random();// 创建random对象
        for (int i = 0; i < 4; i++) {
            int intNumber = random.nextInt(254);
            ip = ip + intNumber;
            if (i != 3) {
                ip = ip + ".";
            }
        }
        return ip;
    }

    /**
     * 匹配域名，在白名单中为true
     */
    public static boolean matchingDomainName(Context mContext, String mUrlHost) {
        String jsonModel = SharedPreferencesHelper.readString(mContext, CommTransferKeys.DOMAIN_NAME_MODEL);
        if (TextUtils.isEmpty(jsonModel)) {
            return false;
        }
        DomainNameModel domainNameModel = JsonUtil.parseJson(jsonModel, DomainNameModel.class);
        if (domainNameModel != null && domainNameModel.datas != null && domainNameModel.datas.list != null
                && domainNameModel.datas.list.size() > 0) {
            List<DomainNameModel.DomainName> list = domainNameModel.datas.list;
            for (DomainNameModel.DomainName domainName : list) {
                if (mUrlHost.contains(domainName.name)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String mSubString(String urlStr, String typeStr, int index) {
        int stateStart = urlStr.indexOf(typeStr);
        String subStr = urlStr.substring(stateStart);
        int subStrEnd = subStr.length();
        if (subStr.contains("&")) {
            subStrEnd = subStr.indexOf("&");
        }
        return subStr.substring(index, subStrEnd);
    }
}