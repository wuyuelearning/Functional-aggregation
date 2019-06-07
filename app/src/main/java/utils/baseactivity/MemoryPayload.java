package utils.baseactivity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Author:MrIcefox
 * <p>Email:extremetsa@gmail.com
 * <p>Description:
 * <p>Date:2017/4/25
 */

public class MemoryPayload {
    public static Class<?> currentActivity;
    public static int indexTab = -1;
    public static String checkIndate;
    public static boolean notReturnMainActivity;
    public static boolean showTheme;//行程助手显示主题游
    public static String mobileBindFlag;
    public static int HOLIDAY_TYPE = 0x00;// 判断线路类型
    public static String dfp = null;//邦盛设备指纹
    public static ArrayList<PersonItem> personItemList = new ArrayList<>(); //YTG 游玩人，考虑是否可以将该字段放到userinfo类中

    public static String selectCityName;
    public static String selectCityExtraPinyin;
    public static String selectCityPinyin;
    public static String selectCityId;

    public final static List<String> sDowngradeActivityClassName = new ArrayList<>();
    public final static List<String> sDowngradeActivityUrl = new ArrayList<>();
}
