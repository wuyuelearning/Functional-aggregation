package Common;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/4/9.
 * 在第一个控件PopupWindowDown的基础上参考PopupWindowDown2
 * 将填充布局的初始化与使用放在了控件之外
 */

public class PopupWindowDown3 extends PopupWindow {

    public PopupWindowDown3(Activity context, View view) {
        super(context);
        initPopupWindowStyle(context, view);
    }

    private void initPopupWindowStyle(Activity context, View view) {
        // 设置弹窗填充布局
        setContentView(view);
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        int height = context.getWindowManager().getDefaultDisplay().getHeight();
        // 设置弹窗的宽
        setWidth(width / 2 - 100);
        // 设置弹窗的高
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 窗体是否可以点击，填充布局点击与否不影响
        setFocusable(true);
        setOutsideTouchable(true);
        // 刷新
        update();
        // 设置背景透明度
        ColorDrawable dw = new ColorDrawable(000000000);
        setBackgroundDrawable(dw);
        // 设置弹窗动画
        setAnimationStyle(R.style.AnimationPreview);
    }

    public void showPopupWindow(View v) {
        this.showAsDropDown(v);
    }
}
