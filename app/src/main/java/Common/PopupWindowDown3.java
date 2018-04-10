package Common;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/4/9.
 * 在第一个控件PopupWindowDown的基础上参考PopupWindowDown2
 * 将填充布局的初始化与使用放在了控件之外
 */

public class PopupWindowDown3 extends PopupWindow {

    private boolean openAlpha;
    private ValueAnimator valueAnimator;

    private int width;
    private int height;

    public PopupWindowDown3(Activity context, View view) {
        super(context);
        initPopupWindowStyle(context, view);
    }

    private void initPopupWindowStyle(Activity context, View view) {
        // 设置弹窗填充布局
        setContentView(view);

        getScreenWidthHeight1(context);
//        getScreenWidthHeight2(context);

        // 设置弹窗的宽
        setWidth(width / 2 - 100);
        // 设置弹窗的高
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 窗体是否可以点击，填充布局点击与否不影响
        setFocusable(true);
        //这里设置显示PopuWindow之后在外面点击是否有效。如果为false的话，
        // 那么点击PopuWindow外面并不会关闭PopuWindow。当然这里很明显只能在Touchable下才能使用
        setOutsideTouchable(true);
        // 刷新
        update();
        // 设置背景透明度
        ColorDrawable dw = new ColorDrawable(0xff5899DB);
        setBackgroundDrawable(dw);
        // 设置弹窗动画
        setAnimationStyle(R.style.AnimationPreview);
    }

    public void showPopupWindow(View v) {
        startAlphaAnim();
        this.showAsDropDown(v);
    }

    /**
     * 设置弹窗的偏移量
     *
     * @param anchor 在anchor固定控件下弹出
     * @param xoff   相对x 轴的偏移量
     * @param yoff   相对y 轴的偏移量
     */

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }

    private void startAlphaAnim() {
        if (!openAlpha) {
            return;
        }
        if (valueAnimator != null) {
            valueAnimator.start();
        }

        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(300);
        //  设置动画变化浮动值
        valueAnimator.setFloatValues(0, 128);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                getBackground().setAlpha(alpha);
            }
        });
        valueAnimator.start();

    }

    // 是否打开设置背景色
    public void setOpenAlpha(boolean b) {
        openAlpha = b;
        if (openAlpha) {
            setBackgroundDrawable(new ColorDrawable(0xff000000));
        }
    }

    private void getScreenWidthHeight1(Activity context) {

        width = context.getWindowManager().getDefaultDisplay().getWidth();
        height = context.getWindowManager().getDefaultDisplay().getHeight();
    }

    private void getScreenWidthHeight2(Activity context) {
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
}
