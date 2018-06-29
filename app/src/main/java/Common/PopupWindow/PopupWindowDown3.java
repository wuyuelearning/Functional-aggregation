package Common.PopupWindow;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.admin.projecttest.R;

import Utils.GetScreenWidthHeight;

import static Utils.COMMONVALUE.*;


/**
 * Created by wuyue on 2018/4/9.
 * 在第一个控件PopupWindowDown的基础上参考PopupWindowDown2
 * 将填充布局的初始化与使用放在了控件之外
 */

public class PopupWindowDown3 extends PopupWindow {
    private boolean openAlpha;
    private ValueAnimator valueAnimator;
    // 屏幕宽高
    private int width;
    private int height;
    private Activity mContext;
    private GetScreenWidthHeight wh = new GetScreenWidthHeight();



    public PopupWindowDown3(Activity context, View view) {
        super(context);
        this.mContext = context;
        initPopupWindowStyle(context, view);
    }

    private void initPopupWindowStyle(Activity context, View view) {
        // 设置弹窗填充布局
        setContentView(view);
        // 计算屏幕宽高
        getScreenWidthHeight(context, GET_SCREEN_WH_3);
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
        // 设置弹窗背景颜色
        ColorDrawable dw = new ColorDrawable(0xff5899DB);
        setBackgroundDrawable(dw);
        // 设置弹窗动画
        setAnimationStyle(R.style.AnimationPreview);
    }

    public void showPopupWindow(View v) {
//        startAlphaAnim();
        setBackgroundAlpha(0.5f);
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
//        startAlphaAnim();
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
                int alpha = (int) ((float) animation.getAnimatedValue());
                getBackground().setAlpha(alpha);
            }
        });
        valueAnimator.start();
    }

    // 是否打开设置背景色
    public void setOpenAlpha(boolean b) {
        openAlpha = b;
        if (openAlpha) {
            setBackgroundDrawable(new ColorDrawable(0xff112233));
        }
    }


    /**
     * 当弹窗打开时，设置这个界面背景变暗
     *
     * @param alpha 设置透明度
     */
    private void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = alpha;
        mContext.getWindow().setAttributes(lp);
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

    }

    /**
     * @param context Activity 的context
     * @param i       选择第几种计算屏幕宽度的方式
     */
    private void getScreenWidthHeight(Activity context, int i) {
        wh.selectFunc(context, i);
        width = wh.getScreenWidth();
        height = wh.getScreenHeight();
    }

    @Override
    public void dismiss() {
        setBackgroundAlpha(1.0f);
        super.dismiss();
    }
}
