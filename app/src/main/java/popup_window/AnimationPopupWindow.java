package popup_window;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.example.admin.projecttest.R;


/**
 * Created by huchangfu on 2015/7/23. Email:huchangfu@lvmama.com. Desc:contentView带有动画的PopupWindow Version:7.2.0
 * <p>
 * 使用：
 * //    private AnimationPopupWindow mPopupWindow;
 * //    private View mPopupWindowView;
 *
 * //    private void showWindow(Data  data) {
 * //        if (data == null) {
 * //            return;
 * //        }
 * //        initPopupWindowView(mineCouponBean);   //  初始化弹窗内部控件
 * //        if (mPopupWindow == null) {
 * //            mPopupWindow = new AnimationPopupWindow(getActivity(), mPopupWindowView);
 * //        }
 * //        mPopupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
 * //    }
 *
 *    //  初始化弹窗内部控件,如果需要弹窗不内容不覆盖全部屏幕，只需在 内容xml文件（例子中的：R.layout.layout_coupon_detail）
 *   //   设置整个布局的高度
 *     private void initPopupWindowView(Data  data) {
 //        if (data == null) {
 //            return;
 //        }
 //
 //        if (mPopupWindowView == null) {
 //            mPopupWindowView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_coupon_detail, null);
 //            mPopupWindowView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
 //                @Override
 //                public void onClick(View v) {
 //                    mPopupWindow.dismiss();
 //                }
 //            });
 //
 //            tvCouponName = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_name);
 //        }
 //
 //        tvCouponName.setText(mineCouponBean.name);
 //
 */


public class AnimationPopupWindow extends PopupWindow {

    private View rootView;
    private Animation inAnim, outAnim;
    private int height;

//}


    public AnimationPopupWindow(Context context, Animation inAnim, Animation outAnim, View view) {
        super(context);
        this.inAnim = inAnim;
        this.outAnim = outAnim;
        this.height = ViewGroup.LayoutParams.MATCH_PARENT;
        initPop(view);
    }

    public AnimationPopupWindow(Context context, int inAnimRes, int outAnimRes, View view) {
        super(context);
        this.inAnim = AnimationUtils.loadAnimation(context, inAnimRes);
        this.outAnim = AnimationUtils.loadAnimation(context, outAnimRes);
        this.height = ViewGroup.LayoutParams.MATCH_PARENT;
        initPop(view);
    }

    public AnimationPopupWindow(Context context, View view) {
        this(context, R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom, view);
    }

    public AnimationPopupWindow(Context context, Animation inAnim, Animation outAnim, int height, View view) {
        this(context, inAnim, outAnim, view);
        this.height = height;
    }

    public AnimationPopupWindow(Context context, int inAnimRes, int outAnimRes, int height, View view) {
        super(context);
        this.inAnim = AnimationUtils.loadAnimation(context, inAnimRes);
        this.outAnim = AnimationUtils.loadAnimation(context, outAnimRes);
        this.height = ViewGroup.LayoutParams.MATCH_PARENT;
        initPop(view);
    }

    private void initPop(View view) {
        this.rootView = view;
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(height);
        this.setContentView(view);
        //by YTG v761，解决底部被导航栏遮挡问题
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        if (null != rootView && null != inAnim) {
            rootView.startAnimation(inAnim);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        if (null != rootView && null != inAnim) {
            rootView.startAnimation(inAnim);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        if (null != rootView && null != inAnim) {
            rootView.startAnimation(inAnim);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (null != rootView && null != inAnim) {
            rootView.startAnimation(inAnim);
        }
    }

    public void initPop(int popAnimationStyle, View view) {
        initPop(view);
        this.setAnimationStyle(popAnimationStyle);
    }

    @Override
    public void dismiss() {
        if (null != outAnim) {
            getContentView().clearAnimation();
            outAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    superDismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            getContentView().startAnimation(outAnim);
        }
    }

    public void superDismiss() {
        super.dismiss();
    }

}
