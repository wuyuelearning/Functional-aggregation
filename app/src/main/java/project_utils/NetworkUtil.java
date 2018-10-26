package project_utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetworkUtil {
    //根据网络Type获取对应的网络信息
    public static NetworkInfo getNetworkInfo(Context context, int netWorkType) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manger.getNetworkInfo(netWorkType);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isWifiEnabled(Context context) {
        try {
            ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mgrConn != null && mgrTel != null && mgrConn.getActiveNetworkInfo() != null) {
                NetworkInfo networkINfo = mgrConn.getActiveNetworkInfo();
                if (networkINfo.getState() == NetworkInfo.State.CONNECTED
                        || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean is3rd(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null && cm.getActiveNetworkInfo() != null) {
                NetworkInfo networkINfo = cm.getActiveNetworkInfo();
                if (networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null && cm.getActiveNetworkInfo() != null) {
                NetworkInfo networkINfo = cm.getActiveNetworkInfo();
                if (networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    // 是否联网网络
    public static boolean isNetworkAvailable(final Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manger != null && manger.getActiveNetworkInfo() != null) {
                NetworkInfo info = manger.getActiveNetworkInfo();
                if (info.isConnected()) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 获取运营商
     *
     * @param context
     * @return WIFI或者数字 46000中国移动TD 46001中国联通 46002中国移动GSM 46003中国电信CDMA
     */
    public static String getNetworkOperator(Context context) {
        if (context != context.getApplicationContext()) {
            context = context.getApplicationContext();
        }
        String networkOperator = "WIFI";

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI);
        if (wifiInfo == null || wifiInfo.getState() != NetworkInfo.State.CONNECTED) {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            networkOperator = telephonyManager.getNetworkOperator();
        }
        return networkOperator;
    }

    public static String getNetWorkOperatorStr(Context context) {
        String operator = getNetworkOperator(context);
        switch (operator) {
            case "46000":
            case "46002":
            case "46007":
                return "CM"; //中国移动
            case "46001":
            case "46006":
            case "46020":
                return "CU"; // 中国联通
            case "46003":
            case "46005":
            case "46011":
                return "CT"; //中国电信
            default:
                return "NONE"; //其他
        }
    }


    //没有网络连接
    public static final int NETWORK_NONE = 0;
    //wifi连接
    public static final int NETWORK_WIFI = 1;
    //手机网络数据连接类型
    public static final int NETWORK_2G = 2;
    public static final int NETWORK_3G = 3;
    public static final int NETWORK_4G = 4;
    public static final int NETWORK_MOBILE = 5;

    public static String getNetWorkStateStr(Context context) {
        int state = getNetworkState(context);
        String stateStr = "";
        switch (state) {
            case NETWORK_NONE:
                stateStr = "NONE";
                break;
            case NETWORK_WIFI:
                stateStr = "WIFI";
                break;
            case NETWORK_2G:
                stateStr = "2G";
                break;
            case NETWORK_3G:
                stateStr = "3G";
                break;
            case NETWORK_4G:
                stateStr = "4G";
                break;
            case NETWORK_MOBILE:
                stateStr = "1G";
                break;
            default:
                stateStr = "NONE";
                break;
        }
        return stateStr;
    }

    /**
     * 获取当前网络连接类型
     *
     * @param context
     * @return
     */
    private static int getNetworkState(Context context) {
        //获取系统的网络服务
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //如果当前没有网络
        if (null == connManager)
            return NETWORK_NONE;

        //获取当前网络类型，如果为空，返回无网络
        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORK_NONE;
        }

        // 判断是不是连接的是不是wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI;
                }
        }

        // 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        //如果是2g类型
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT://中国电信
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_2G;
                        //如果是3g类型
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS: //联通3g
                        case TelephonyManager.NETWORK_TYPE_EVDO_0: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_HSDPA: //联通3g
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B: //电信3g
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_3G;
                        //如果是4g类型
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_4G;
                        default:
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORK_3G;
                            } else {
                                return NETWORK_MOBILE;
                            }
                    }
                }
        }
        return NETWORK_NONE;
    }

    /**
     * 返回运营商 需要加入权限 <uses-permission android:name="android.permission.READ_PHONE_STATE"/> <BR>
     *
     * @return 1, 代表中国移动，2，代表中国联通，3，代表中国电信，0，代表未知  仅作参考
     * @author youzc@yiche.com
     */
    public static int getOperators(Context context) {
        // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）（也称为“MCC /
        // MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
        int OperatorsName = 0;
        String IMSI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            OperatorsName = 1;
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            OperatorsName = 2;
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            OperatorsName = 3;
        }
        return OperatorsName;
    }

    /**
     * 获取本机 ip
     *
     * @return ip 地址
     */
    public static String getIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}