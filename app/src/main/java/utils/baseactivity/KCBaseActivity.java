package utils.baseactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.projecttest.R;
import com.google.gson.JsonObject;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import utils.bring.AppComponentsHolder;
import utils.bring.JsonUtil;
import utils.bring.L;


/**
 * Created by ltyangyang on 2016/5/5.
 * <p/>
 * modified by yantinggeng on 2016/5/12
 * <p/>
 * 目前已经抽出和具体模块无关的部分，进行拆分的时候，对于独立性比较强的activity可以直接继承自该类
 */
public class KCBaseActivity extends AppCompatActivity {
    private static final String TAG = "KCBaseActivity";
    private static final String TRANSFER_BUNDLE = "bundle";

    public CommLoadingDialog commLoadingDialog;
    protected boolean isStop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        compatWebp();
        super.onCreate(savedInstanceState);
        L.d(TAG, "Activity onCreate name:" + this.getClass().getName());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            StatusBarUtil.setPrimaryStatusBar(this);
//        }

        Bundle bundle = getIntent().getBundleExtra(TRANSFER_BUNDLE);
        String productId = null;
        if (bundle != null) {
            productId = bundle.getString("productId");
        }

//        Trace.enqueueRestMobileUserActionPost(getApplicationContext(), getClass().getSimpleName(), productId); //  todo
    }

    private void compatWebp() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
                @Override
                public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                    //system
                    AppCompatDelegate delegate = getDelegate();
                    View view = delegate.createView(parent, name, context, attrs);

                    //custom
                    if (view instanceof ImageView) {
                        ImageView imageView = (ImageView) view;
                        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.src});
                        int webpResourceID = a.getResourceId(0, 0);
                        if (webpResourceID == 0) {
                            return view;
                        }
                        TypedValue value = new TypedValue();
                        getResources().getValue(webpResourceID, value, true);

                        String resName = value.string.toString();
                        if (resName.endsWith(".webp")) {
                            InputStream rawImageStream = getResources().openRawResource(webpResourceID);
                            byte[] data = streamToBytes(rawImageStream);
                            final Bitmap webpBitmap = WebPDecoder.getInstance().decodeWebP(data);
                            imageView.setImageBitmap(webpBitmap);
                        }

                        a.recycle();
                    }

                    return view;
                }
            });
        }
    }

    private static byte[] streamToBytes(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = is.read(buffer)) >= 0) {
                os.write(buffer, 0, len);
            }
        } catch (java.io.IOException e) {
        }
        return os.toByteArray();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isStop = false;
//        ImageCache.getInstance().resume(this);  //  todo
    }

    //不能删除，否则内存重启时，会产生ANR，可能和fragmentManger有关系。后续理解清楚总结
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
    }

    @Override
    protected void onPause() {
        super.onPause();

//        ImageCache.getInstance().pause(this); //  todo
    }

    @Override
    public void finish() {
        // 在extends SsoActivity.java时，如果参数有空，则会报NullPointer // TODO: 2017/4/19 ??
        List<WeakReference<Activity>> activities = AppComponentsHolder.inst().getRunningActivities();
        final String curActivityName = getClass().getName();

        L.d(TAG, String.format(Locale.US, "Total running activity num=%d topActivity:%s", activities.size(), curActivityName));
        if (!activities.isEmpty()) {
            Activity topActivity = activities.get(0) != null ? activities.get(0).get() : null;
            String topActivityName = topActivity == null ? null : topActivity.getClass().getName();

            L.d(TAG, "currentActivityName:" + curActivityName + " topActivityName:" + topActivityName);
            if (curActivityName.equals(topActivityName)) {//当前activity位于顶部
                if (curActivityName.contains("MainActivity")) {//当前activity是首页
                    L.d(TAG, "finish MainActivity");
                    //退出app防止引用残留
                    activities.clear();
                } else {
                    if (activities.size() == 1) {
                        L.d(TAG, "finish then jump to main");
                        //启动首页
                        Intent intent = new Intent();
//                        LvmmTransfer.startActivity(this, "app/MainActivity", intent);  todo
                        overridePendingTransition(R.anim.dt_in_from_left, R.anim.dt_out_to_right);
                    }
                }
                finishDirectly();
            } else {
                finishDirectly();
                overridePendingTransition(R.anim.dt_in_from_left, R.anim.dt_out_to_right);
            }
        }
        clearCurrentActivityImageCache();
    }

    //771 add 杨洋 直接finish
    public void finishDirectly() {
        super.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.d(TAG, "onKeyDown()");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void startActivity(Intent intent, int enterAnim, int exitAnim) {
        redirectStartActivity(intent);
        super.startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);
    }

    private void redirectStartActivity(Intent origin) {
        ComponentName cmp = origin.getComponent();

        if (cmp == null) {
            return;
        }

        int index = MemoryPayload.sDowngradeActivityClassName.indexOf(cmp.getClassName());
        if (index != -1) {
            String url = createRedirectUrl(origin, MemoryPayload.sDowngradeActivityUrl.get(index)
                    , MemoryPayload.sDowngradeActivityClassName.get(index));
//            if (origin.getExtras() != null) {
//                origin.getExtras().clear();
//            }
            origin.putExtra("url", url);
            origin.setClassName(this, "com.lvmama.android.hybrid.activity.WebViewActivity");  //  todo
        }
    }

    private String createRedirectUrl(Intent src, String urlPrefix, String action) {
        //https://m.lvmama.com/appDegrade/transfer
        StringBuilder url = new StringBuilder(urlPrefix);
        url.append("?action=").append(action);
        String noData = url.toString();

        if (src.getExtras() == null) {
            return noData;
        }

        Map<String, Object> outArgs = new HashMap<>();
        Map<String, Object> bMap = null;

        for (String key : src.getExtras().keySet()) {
            if (TRANSFER_BUNDLE.equals(key)) {
                Bundle bundle = src.getBundleExtra(key);

                if (bundle != null) {
                    bMap = new HashMap<>();
                    for (String bkey : bundle.keySet()) {
                        Object bval = bundle.get(bkey);
                        //只包含基础类型
                        if (bval != null && (bval.getClass().isPrimitive() || bval.getClass() == String.class)) {
                            bMap.put(bkey, bval);
                        }
                    }
                }
            } else {
                Object val = src.getExtras().get(key);
                //只包含基础类型
                if (val != null && (val.getClass().isPrimitive() || val.getClass() == String.class)) {
                    outArgs.put(key, val);
                }
            }
        }

        String dataJson = null;

        if (bMap != null) {
            JsonObject outJo = new JsonObject();
            JsonObject bJo = new JsonObject();

            for (Map.Entry<String, Object> e : bMap.entrySet()) {
                bJo.addProperty(e.getKey(), String.valueOf(e.getValue()));
            }
            for (Map.Entry<String, Object> e : outArgs.entrySet()) {
                outJo.addProperty(e.getKey(), String.valueOf(e.getValue()));
            }

            outJo.add(TRANSFER_BUNDLE, bJo);
            dataJson = outJo.toString();
        } else {
            dataJson = JsonUtil.toJson(outArgs);
        }

        if (dataJson != null) {
            url.append("&data=")
                    .append(Base64.encodeToString(dataJson.getBytes(), Base64.DEFAULT));
            return url.toString();
        } else {
            return noData;
        }
    }

    //----------------------- 重写startActivity系列16个api start -----------------------

    @Override
    public void startActivity(Intent intent) {
        redirectStartActivity(intent);
        super.startActivity(intent);
        overridePendingTransition(R.anim.dt_in_from_right, R.anim.dt_out_to_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        redirectStartActivity(intent);
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.dt_in_from_right, R.anim.dt_out_to_left);
    }

    @Override
    public void startActivityFromChild(Activity child, Intent intent, int requestCode) {
        redirectStartActivity(intent);
        super.startActivityFromChild(child, intent, requestCode);
        overridePendingTransition(R.anim.dt_in_from_right, R.anim.dt_out_to_left);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        redirectStartActivity(intent);
        super.startActivityFromFragment(fragment, intent, requestCode);
        overridePendingTransition(R.anim.dt_in_from_right, R.anim.dt_out_to_left);
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        redirectStartActivity(intent);
        super.startActivity(intent, options);
    }

    @Override
    public boolean startActivityIfNeeded(@NonNull Intent intent, int requestCode) {
        redirectStartActivity(intent);
        return super.startActivityIfNeeded(intent, requestCode);
    }

    @Override
    public boolean startActivityIfNeeded(@NonNull Intent intent, int requestCode, @Nullable Bundle options) {
        redirectStartActivity(intent);
        return super.startActivityIfNeeded(intent, requestCode, options);
    }

    @Override
    public boolean startNextMatchingActivity(@NonNull Intent intent) {
        redirectStartActivity(intent);
        return super.startNextMatchingActivity(intent);
    }

    @Override
    public boolean startNextMatchingActivity(@NonNull Intent intent, @Nullable Bundle options) {
        redirectStartActivity(intent);
        return super.startNextMatchingActivity(intent, options);
    }

    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
    }

    @Override
    public void startActivities(Intent[] intents, @Nullable Bundle options) {
        super.startActivities(intents, options);
    }

    @Override
    public void startActivityFromChild(@NonNull Activity child, Intent intent, int requestCode, @Nullable Bundle options) {
        redirectStartActivity(intent);
        super.startActivityFromChild(child, intent, requestCode, options);
    }

    @Override
    public void startActivityFromFragment(@NonNull android.app.Fragment fragment, Intent intent, int requestCode) {
        redirectStartActivity(intent);
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    @Override
    public void startActivityFromFragment(@NonNull android.app.Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        redirectStartActivity(intent);
        super.startActivityFromFragment(fragment, intent, requestCode, options);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        redirectStartActivity(intent);
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        redirectStartActivity(intent);
        super.startActivityFromFragment(fragment, intent, requestCode, options);
    }

    //----------------------- 重写startActivity系列api end -----------------------

    @Override
    protected void onDestroy() {
        L.d("KCBaseActivity onDestroy()...");
        super.onDestroy();
        isStop = true;
      //  ApiProvider.cancel(this);  //  todo
    }

    @Override
    public void onBackPressed() {
        L.d("KCBaseActivity onBackPressed()...");
        super.onBackPressed();
        overridePendingTransition(R.anim.dt_in_from_left, R.anim.dt_out_to_right);
    }

    //直接结束当前的activity
    public void finishDirect() {
        super.finish();
        AppComponentsHolder.inst().removeFromRunningActivities(this);
        overridePendingTransition(R.anim.dt_in_from_left, R.anim.dt_out_to_right);
    }

    /**
     * 当前Activity finish的时候，清空该Activity下面的所有缓存Bitmap
     */
    private void clearCurrentActivityImageCache() {
//        ImageCache.clearImageMemoryCache(this); //  todo
    }

    public void dialogDismiss() {
        if (commLoadingDialog != null && commLoadingDialog.isShowing()) {
            commLoadingDialog.stopAnimation();
            commLoadingDialog.dismiss();
        }
    }

    public void dialogShow(boolean flag) {
        if (commLoadingDialog == null) {
            commLoadingDialog = new CommLoadingDialog(this);
        }
        commLoadingDialog.setCanceledOnTouchOutside(flag);
        commLoadingDialog.startAnimation();
        if (commLoadingDialog.isShowing()) {
            return;
        }
        commLoadingDialog.show();
    }

    protected boolean handleChildFragmentCall(Call call) {
        return false;
    }

    //与KCBaseFragment通信
    public static class Call {
        private String method = "";
        private Object data;
        private Object result;
        private boolean isSuccess;

        public Call(String method) {
            this.method = method;
        }

        public Call(String method, Object data) {
            this.method = method;
            this.data = data;
        }

        public String getMethod() {
            return method;
        }

        public Object getData() {
            return data;
        }

//    public void setData(Object data) {
//        this.data = data;
//    }

        public void setMethod(String method) {
            this.method = method;
        }

        public Object getResult() {
            return result;
        }

        public void returnResult(boolean isSuccess, Object result) {
            this.isSuccess = isSuccess;
            this.result = result;
        }

        public boolean isSuccess() {
            return isSuccess;
        }
    }
}