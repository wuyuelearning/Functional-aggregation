package project_utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static android.text.TextUtils.isEmpty;

public class MobileUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";


    private static String imei;
    private static String deviceId;
    private static int width;
    private static int height;
    private static String imsi;

    /**
     * 获取设备号
     */
    public static String getImei(Context context) {
        if (!StringUtil.equalsNullOrEmpty(imei)) {
            return imei;
        }
        imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (imei == null) {
            return "";
        }
        return imei;
    }

    public static String getDeviceId(Context context) {
        if (StringUtil.equalsNullOrEmpty(deviceId)) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    public static String getMobileModel(Context context) {
        return Build.MODEL;
    }

    /**
     * 获取系统版本
     */
    @SuppressWarnings("unused")
    public static String systemVersion() {
        String buf = android.os.Build.VERSION.RELEASE;
        if (buf == null) {
            return "";
        }
        return buf;
    }

    public static int getScreenWidth(Context context) {
        if (width != 0) {
            return width;
        }
        width = getDisplayMetrics(context.getApplicationContext()).widthPixels;
        return width;
    }

    public static int getScreenHeight(Context context) {
        if (height != 0) {
            return height;
        }
        height = getDisplayMetrics(context).heightPixels;
        return height;
    }

    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        // 判断sd卡是否存在
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();// 获取跟目录
        } else {
            return null;
        }
    }

    // 获取联系人姓名
    public static String getPhoneContacts(Context context, String contactId) {
        String name = "";
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, BaseColumns._ID
                    + "=?", new String[]{contactId}, null);
            if (null != cursor && cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            }
        } catch (Exception e) {
            name = "";
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return name;
    }

    // 获取联系人手机号码
    public static String getPhoneNumber(Context context, String contactId) {
        String number = "";
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            if (null != cursor && cursor.moveToFirst()) {
                do {
                    number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if (!StringUtil.equalsNullOrEmpty(number)) {
                        return number.trim().replaceAll(" ", "").replace("-", "");
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            number = "";
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return number;
    }

    public static String getPhoneNumber(Context context, Intent data) {
        String phoneNumber = "";

        if (data == null) {
            return phoneNumber;
        }

        Uri uri = data.getData();
        if (uri == null) {
            return phoneNumber;
        }

        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (phoneNumber != null) {
            phoneNumber = phoneNumber.replaceAll("-", "");
            phoneNumber = phoneNumber.replaceAll("\\s+", "");
        }
        return phoneNumber;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int getDisplayWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics d = new DisplayMetrics();
        display.getMetrics(d);
        return d.widthPixels;
    }

    public static int getDisplayHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics d = new DisplayMetrics();
        display.getMetrics(d);
        return d.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @Deprecated
    public static int dip2px(@Nullable @SuppressWarnings("unused") Context context, int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public static int dip2px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public static int dp2px(@Nullable @SuppressWarnings("unused") Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    @SuppressWarnings("unused")
    public static int px2sp(@Nullable @SuppressWarnings("unused") Context context, float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, Resources.getSystem().getDisplayMetrics());
    }

    public static void setPadding(View v, int l, int t, int r, int b, boolean toPixels) {
        if (toPixels) {
            v.setPadding(MobileUtil.dip2px(l), MobileUtil.dip2px(t), MobileUtil.dip2px(r), MobileUtil.dip2px(b));
        } else {
            v.setPadding(l, t, r, b);
        }
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    @SuppressWarnings("unused")
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float imageViewWidth(Context context, float dp_size, int count) {
        if (count <= 0) {
            count = 1;
        }
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return (dm.widthPixels - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp_size, dm)) / count;
    }

    /**
     * 获取当前状态栏，用于获取状态栏高度等等
     */
    public static Rect getStateBar(Context context) {
        Rect frame = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame;
    }

    public static int getSmartBarHeight(Context context) {
        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            return dip2px(48);
        }
    }

    /**
     * 判断是否是魅族系统
     */
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    //自动弹出键盘 alter 783 lhw
    public static void showKeyBoard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyBoard(Activity activity) {
        hideKeyBoard(activity, false);
    }

    public static void hideKeyBoard(Activity activity, boolean clearFocus) {
        if (activity != null && activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                    (activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            if (clearFocus) {
                activity.getCurrentFocus().clearFocus();
            }
        }
    }

    public static void hideInput(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showInput(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }

    public static void showInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static Context getContextFromView(View v) {
        if (null == v) return null;
        Context context = v.getContext();
        if (context instanceof Activity) return v.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static void filterChinese(TextView editView) {
        editView.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (null != source && StringUtil.isChinese(source.toString())) return "";
                return source;
            }
        }});
    }

    // 目的是避免谷歌警告：Do not concatenate text displayed with setText. Use resource string with placeholders.
    // 建议有警告的都调用此方法
    public static void setText(TextView tv, Object text) {
        tv.setText(String.format("%s", text));
    }


    /**
     * @param content     文本框内容
     * @param colorString 指定位置颜色值，颜色值的格式例如“#FF0000”
     * @param start       起始位置
     * @param end         截止位置
     */
    public static SpannableStringBuilder setSpanStringColor(String content, String colorString, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        ForegroundColorSpan fcp = new ForegroundColorSpan(Color.parseColor(colorString));
        ssb.setSpan(fcp, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        return ssb;
    }

    /**
     * @param isQi 是否末尾为"起"
     */

    public static SpannableStringBuilder setSpanStringSize(String content, int start, int end, boolean isQi) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(content);
        AbsoluteSizeSpan fcp = new AbsoluteSizeSpan(14, true);

        if (TextUtils.isEmpty(content)) {
            throw new NullPointerException("content 不能为空");
        }
        if (isQi) {
            AbsoluteSizeSpan fcp2 = new AbsoluteSizeSpan(14, true);
            ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#666666"));
            ssb.setSpan(fcp2, content.length() - 1, content.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
            ssb.setSpan(fcs, content.length() - 1, content.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            ssb.setSpan(fcp, start, end, SpannableStringBuilder.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return ssb;
    }

    public static GradientDrawable getGradientDrawable(int bgColor, int strokeWidth, int strokeColor, float cornerRadius) {
        GradientDrawable gd = new GradientDrawable();
        if (-1 != bgColor) {
            gd.setColor(bgColor);
        }
        if (-1 != strokeWidth) {
            gd.setStroke(strokeWidth, strokeColor);
        }
        if (-1 != cornerRadius) {
            gd.setCornerRadius(cornerRadius);
        }
        return gd;
    }

    public static GradientDrawable getGradientDrawable(int bgColor, int strokeWidth, int strokeColor) {
        return getGradientDrawable(bgColor, strokeWidth, strokeColor, -1);
    }

    /**
     * 设置下划线
     *
     * @param isCenterFlag true表明是中下划线，false表明是底部下划线
     */
    public static void setTextViewPaintFlag(TextView view, boolean isCenterFlag) {
        TextPaint mPaint = view.getPaint();
        if (isCenterFlag) {
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.UNDERLINE_TEXT_FLAG);
        }
    }


    /**
     * 根据包名跳转到第三方应用
     * huweiqiang
     *
     * @param packagename 包名
     * @return 是否安装了要跳转的应用
     */
    public static boolean doStartApplicationWithPackageName(Context context, String packagename) {
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("这里必须用 Activity Context");
        }

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return false;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            context.startActivity(intent);
        }
        return true;
    }


    public static String getImsi(Context context) {
        if (imsi == null) {
            imsi = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        }
        return imsi;
    }


    public static int getListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        return params.height;
        return totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }

    public static boolean isOpenLocation(Context context) {//判断手机是否打开定位
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return false;
        }
        boolean location_enable = true;
        boolean hasGPSDevice = false;
        List<String> providers = null;
        try {
            location_enable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            providers = locationManager.getAllProviders();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (providers != null && providers.contains(LocationManager.GPS_PROVIDER)) {
            hasGPSDevice = true;
        }
        if (hasGPSDevice && !location_enable) {//如果有gps设备，并且位置没有打开
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断app是否在后台
     */
    public static boolean isAppBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (CollectionUtils.notNullOrEmpty(appProcesses)) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
                }
            }
        }
        return true;
    }

    /**
     * 传入电话号码，跳转到打电话页面 huweiqiang
     *
     * @param context context
     * @param phoneNo phoneNo 如果 phoneNo 为空，则默认为我们的客服电话 4001570570
     */
    public static void callDial(Context context, String phoneNo, boolean useDefault) {
        Intent intent;
        if (!TextUtils.isEmpty(phoneNo)) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
        } else if (useDefault) {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:4001570570"));
        } else {
            return;
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (context instanceof Activity) {
                context.startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            Toast.makeText(context,"拨号应用异常，请稍后再试",Toast.LENGTH_SHORT).show();
//            T.s(context, R.drawable.comm_face_fail, "拨号应用异常，请稍后再试", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 直接拨打电话,如果没有权限则跳转打电话应用
     *
     * @param context
     * @param phoneNum
     */
    public static void callPhone(Context context, String phoneNum) {
        callPhone(context, phoneNum, false);
    }

    /**
     * 直接拨打电话,如果没有权限则跳转打电话应用
     *
     * @param context
     * @param phoneNum
     * @param useDefault phoneNum为空时使用默认的电话号码
     */
    public static void callPhone(Context context, String phoneNum, boolean useDefault) {
        if (context == null) return;
        int perm = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE);
        if (perm != PackageManager.PERMISSION_GRANTED) {
            callDial(context, phoneNum, useDefault);
        }
        if (TextUtils.isEmpty(phoneNum)) {
            if (useDefault) {
                phoneNum = "4001570570";
            } else {
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        if (context instanceof Activity) {
            context.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final Throwable e) {
            return false;
        }
    }

    /**
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName1(Context cxt) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == android.os.Process.myPid()) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 相对上面的效率更高点
     *
     * @return null may be returned if the specified process not found
     */
    public static String getProcessName2() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //据传微信是用的这种方式
    public static String getProgressName(Context context) {
        String name = getProcessName2();
        if (name == null) {
            return getProcessName1(context.getApplicationContext());
        } else {
            return name;
        }
    }

    /**
     * get app's version
     *
     * @param a           activity instance
     * @param packageName pck
     * @return version code
     */
    public static float getAppVer(Activity a, String packageName) {
        float ver = 4.0f;
        try {
            PackageInfo packageInfo = a.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo.packageName.equals(packageName)) {
                String version = packageInfo.versionName.trim();
                if (!TextUtils.isEmpty(version) && version.length() >= 2) {
                    ver = Float.parseFloat(version.substring(0, 1));
                }
            }
        } catch (Exception e) {
            ver = 4.0f;
        }
        return ver;
    }

    /**
     * check the intent is available ,check whether system have app can respond to the intent
     *
     * @param ctx    current context
     * @param intent intent to be checked
     * @return true:can be responded ,false : can not
     */
    public static boolean isIntentAvailable(Context ctx, Intent intent) {
        final PackageManager mgr = ctx.getPackageManager();
        List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 当前进程是否是app进程
     *
     * @param context
     * @return
     */
    public static boolean isInAppProcess(Context context) {
        context = context.getApplicationContext();

        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        String mainProcess = packageInfo.applicationInfo.processName;
        String processName = getProgressName(context);
        if (isEmpty(processName)) {

            return false;
        }
        return processName.equals(mainProcess);
    }

    /**
     * Created by huweiqiang on 2017/5/8.
     */

    private static final class BuildProperties {
        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
    }
}
