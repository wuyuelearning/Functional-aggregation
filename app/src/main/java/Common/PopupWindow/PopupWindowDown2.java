package Common.PopupWindow;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/4/9.
 * 下拉弹窗
 * 含有部分动画
 * 填充PopupWindow的内容可以在控件之外初始化和使用
 */

public class PopupWindowDown2 extends PopupWindow {

    private View rootView;
    private Animation inAnima, outAnima;
    private int height;
    private boolean openAlpha;
    private ValueAnimator valueAnimator;

    public PopupWindowDown2(Context context, View view, Animation inAnima, Animation outAnima) {
        super(context);
        this.inAnima = inAnima;
        this.outAnima = outAnima;
        this.height = ViewGroup.LayoutParams.MATCH_PARENT;
        initPopupWindowDown2(view);

    }

    public PopupWindowDown2(Context context, View view, int inAnima, int outAnima) {
        this.inAnima = AnimationUtils.loadAnimation(context, inAnima);
        this.outAnima = AnimationUtils.loadAnimation(context, outAnima);
        this.height = ViewGroup.LayoutParams.MATCH_PARENT;
        initPopupWindowDown2(view);

    }

    public PopupWindowDown2(Context context, View view) {
        this(context, view, R.anim.slide_in_from_top, R.anim.slide_out_to_top);
        initPopupWindowDown2(view);
    }

    private void initPopupWindowDown2(View view) {
        rootView = view;
        // 设置弹窗的宽
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹窗的高
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 窗体是否可以点击，填充布局点击与否不影响
        setFocusable(true);
        setOutsideTouchable(true);
        // 刷新
        update();
        // 设置背景透明度
        ColorDrawable dw = new ColorDrawable(000000000);
        setBackgroundDrawable(dw);
        //  如果不使用  rootView.startAnimation(inAnima);则可以适应这个方法 设置弹出和收起动画
        //  但是两种效果不同，这种是由最上方(（0,0）坐标之外)移动到目标位置（如果是向下弹）
        // 使用rootView.startAnimation(inAnima)可以计算出填充布局大小后，从触发控件下发弹出布局大小
        // 或是从触发控件处向上弹出
//        setAnimationStyle(R.style.AnimationPreview2);
        // 设置弹窗填充布局
        setContentView(view);
    }

    @Override
    public void showAsDropDown(View anchor) {
        startAlphaAnim();
        super.showAsDropDown(anchor);
        rootView.startAnimation(inAnima);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        startAlphaAnim();
        super.showAsDropDown(anchor,xoff,yoff);
        rootView.startAnimation(inAnima);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        startAlphaAnim();
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        rootView.startAnimation(inAnima);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        startAlphaAnim();
        super.showAtLocation(parent, gravity, x, y);
        rootView.startAnimation(inAnima);
    }

    private void startAlphaAnim(){
        if(!openAlpha){
            return ;
        }
        if(valueAnimator != null){
            valueAnimator.start();
        }

        valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(300);
        //  设置动画变化浮动值
        valueAnimator.setFloatValues(0,128);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                getBackground().setAlpha(alpha);
            }
        });
        valueAnimator.start();

    }

    // 是否打开设置背景色
    public void setOpenAlpha(boolean b){
        openAlpha = b;
        if(openAlpha){
            setBackgroundDrawable(new ColorDrawable(0xff000000));
        }
    }

    
    public void showPopupWindow(View v) {
        this.showAsDropDown(v);
        startAlphaAnim();
    }
    @Override
    public void dismiss() {
        super.dismiss();
        // 使动画消失
        getContentView().clearAnimation();
        //  弹窗收起动画
        getContentView().startAnimation(outAnima);
    }
}
