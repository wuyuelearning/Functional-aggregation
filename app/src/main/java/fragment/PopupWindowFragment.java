package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import common.PopupWindow.PopupWindowDown;
import common.PopupWindow.PopupWindowDown2;
import common.PopupWindow.PopupWindowDown3;

import static android.view.View.inflate;

/**
 * Created by wuyue on 2018/4/9.
 */

public class PopupWindowFragment extends Fragment implements View.OnClickListener {
    private View mRootView;
    private Context mContext;

    //  弹窗分类
    TextView all_coupons_type;
    TextView coupon_states;

    // 弹窗填充布局--优惠券布局中控件
    TextView all_coupons;
    TextView ordinary_coupons;
    TextView invincible_coupons;
    TextView rights_coupons;

    //  不同弹窗控件 使用第二种、第三种较多
    PopupWindowDown popupWindowDown;
    PopupWindowDown2 popupWindowDown2;
    PopupWindowDown3 popupWindowDown3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.popupwindow_fragment, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        all_coupons_type = mRootView.findViewById(R.id.all_coupons_type);
        coupon_states = mRootView.findViewById(R.id.coupon_states);

        all_coupons_type.setOnClickListener(this);
        coupon_states.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        popupWindowClick(v);
        couponsTypeItemClick(v);
    }

    // 点击标题触发弹窗
    private void popupWindowClick(View v) {
        switch (v.getId()) {
            case R.id.all_coupons_type: {
//                usePopupwindow(v);
//                usePopupwindow2(v);
                usePopupwindow3(v);
            }
            break;
            case R.id.coupon_states: {
                usePopupwindow2(v);
                break;
            }
        }
    }

    // 弹窗中的填充布局点击监听
    private void couponsTypeItemClick(View v) {
        switch (v.getId()) {
            case R.id.all_coupons: {
                Toast.makeText(mContext, "all_coupons", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.ordinary_coupons: {
                Toast.makeText(mContext, "ordinary_coupons", Toast.LENGTH_SHORT).show();

                break;
            }
            case R.id.invincible_coupons: {
                Toast.makeText(mContext, "invincible_coupons", Toast.LENGTH_SHORT).show();

                break;
            }
            case R.id.rights_coupons: {
                Toast.makeText(mContext, "rights_coupons", Toast.LENGTH_SHORT).show();

                break;
            }
        }
    }

    private void usePopupwindow(View v) {
        popupWindowDown = new PopupWindowDown(getActivity());
        popupWindowDown.showPopupWindow(v); // 弹窗入口
        popupWindowDown.setOpenAlpha(true); //  设置颜色渐变
    }

    private void usePopupwindow2(View v) {
        View popView = inflate(getActivity(), R.layout.popupwindow_fill, null);
        popupWindowDown2 = new PopupWindowDown2(getActivity(), popView);
        popupWindowDown2.showPopupWindow(v);   // 弹窗入口
        popupWindowDown2.setOpenAlpha(true); //  设置颜色渐变
        initItemView(popView);
    }

    private void usePopupwindow3(View v) {
        View popView = inflate(getActivity(), R.layout.popupwindow_fill, null);
        popupWindowDown3 = new PopupWindowDown3(getActivity(), popView);
        popupWindowDown3.showPopupWindow(v); // 弹窗入口
        popupWindowDown3.setOpenAlpha(true);
        initItemView(popView);
    }

    private void initItemView(View itemView) {
        
        all_coupons = itemView.findViewById(R.id.all_coupons);
        ordinary_coupons = itemView.findViewById(R.id.ordinary_coupons);
        invincible_coupons = itemView.findViewById(R.id.invincible_coupons);
        rights_coupons = itemView.findViewById(R.id.rights_coupons);

        all_coupons.setOnClickListener(this);
        ordinary_coupons.setOnClickListener(this);
        invincible_coupons.setOnClickListener(this);
        rights_coupons.setOnClickListener(this);

    }


}
