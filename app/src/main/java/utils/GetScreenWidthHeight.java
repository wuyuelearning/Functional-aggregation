package utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import static utils.COMMONVALUE.*;
/**
 * Created by wuyue on 2018/4/11.
 */

public class GetScreenWidthHeight {

    private int width;
    private int height;
    private Activity context;

    private void getScreenWidthHeight1() {
        WindowManager wm = context.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }


    private void getScreenWidthHeight2() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }


    private void getScreenWidthHeight3() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;
        float density = dm.density;
        float densityDpi = dm.densityDpi;

        int screenWidth = (int) (width / density);
        int screenHeight = (int) (height / density);

        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
    }

    private void getScreenWidthHeight4() {
        Resources resources = context.getResources();
        DisplayMetrics dc = resources.getDisplayMetrics();

        width = dc.widthPixels;
        height = dc.heightPixels;

        float density = dc.density;
        float densityDpi = dc.densityDpi;


        int screenWidth = (int) (width / density);
        int screenHeight = (int) (height / density);

        Log.d("h_bl", "屏幕宽度（像素）：" + dc.widthPixels);
        Log.d("h_bl", "屏幕高度（像素）：" + dc.heightPixels);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);

    }


    public int getScreenWidth() {
        return width;
    }

    public int getScreenHeight() {
        return height;
    }

    public void selectFunc(Activity context, int i) {
        this.context = context;
        switch (i) {
            case GET_SCREEN_WH_1:
                getScreenWidthHeight1();
                break;
            case GET_SCREEN_WH_2:
                getScreenWidthHeight2();
                break;
            case GET_SCREEN_WH_3:
                getScreenWidthHeight3();
                break;
            case GET_SCREEN_WH_4:
                getScreenWidthHeight4();
                break;
            default:
                getScreenWidthHeight1();
        }
    }

}



