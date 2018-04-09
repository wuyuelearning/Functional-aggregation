package Common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/4/9.
 * <p>
 * 下拉弹窗
 * 比较简单的弹窗
 * 需要在控件内进行填充控件的初始化和操作
 *
 */

public class PopupWindowDown extends PopupWindow implements View.OnClickListener {

    private TextView all_coupons;
    private TextView ordinary_coupons;
    private TextView invincible_coupons;
    private TextView rights_coupons;

    private Context mContext;



    public PopupWindowDown(Activity context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.popup_window_fill, null);
        // 设置弹窗填充布局
        setContentView(itemView);
        initItemView(itemView);
        initPopupWindowStyle(context);
    }



    private void initPopupWindowStyle(Activity context) {
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        int height = context.getWindowManager().getDefaultDisplay().getHeight();
        // 设置弹窗的宽
        setWidth(width/2-100);
        // 设置弹窗的宽
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

    private void initItemView(View itemView) {
        all_coupons = itemView.findViewById(R.id.all_coupons);
        ordinary_coupons = itemView.findViewById(R.id.ordinary_coupons);
        invincible_coupons = itemView.findViewById(R.id.invincible_coupons);
        rights_coupons = itemView.findViewById(R.id.rights_coupons);

        ordinary_coupons.setOnClickListener(this);
        invincible_coupons.setOnClickListener(this);
        rights_coupons.setOnClickListener(this);
    }

    public void showPopupWindow(View v) {
        this.showAsDropDown(v);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ordinary_coupons:
                Toast.makeText(mContext,"ordinary_coupons",Toast.LENGTH_SHORT).show();
                break;
            case R.id.invincible_coupons:
                Toast.makeText(mContext,"invincible_coupons",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rights_coupons:
                Toast.makeText(mContext,"rights_coupons",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
