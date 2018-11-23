package tow_tab.product_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.projecttest.R;


import java.util.ArrayList;
import java.util.List;

import tow_tab.AnimationPopupWindow;
import tow_tab.MineCouponInfo;

/**
 * Created by wuyue on 2018/11/9.
 * describe:
 */

public abstract class CouponsCommonFragment extends Fragment {

    private View mRootView;
    private String fragmentPos;
    private RecyclerView rvCoupons;
    private CouponsRvAdapter mAdapter;

    private AnimationPopupWindow mPopupWindow;
    private View mPopupWindowView;
    private List<MineCouponInfo.MineCouponBean> mData =new ArrayList<>();


    public abstract int getViewId();

    public abstract CouponsRvAdapter getAdapter();

    public abstract String getProductType();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getViewId(), container, false);
        initView();
        initData();
        return mRootView;
    }

    private void initView() {
        rvCoupons = (RecyclerView)mRootView.findViewById(R.id.rv_coupons);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvCoupons.setLayoutManager(linearLayoutManager);
        mAdapter = getAdapter();
        mAdapter.setmData(mData);
        rvCoupons.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CouponsRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                gotoCouponDetail(position);
            }
        });




    }
    private void initData(){

        MineCouponInfo.MineCouponBean bean =new MineCouponInfo.MineCouponBean();
        mData.add(bean);
        mData.add(bean);
        mData.add(bean);
        mData.add(bean);
        mData.add(bean);
        mData.add(bean);
        mData.add(bean);

    }

    public void selectFragment(String fragmentPos) {
        this.fragmentPos = fragmentPos;
    }

    private void gotoCouponDetail(int position) {
        List<MineCouponInfo.MineCouponBean> data = mAdapter.getmData();
        showWindow(data.get(position));
    }



    private void showWindow(MineCouponInfo.MineCouponBean mineCouponBean) {
        if (mineCouponBean == null) {
            return;
        }
        initPopupWindowView(mineCouponBean);
        if (mPopupWindow == null) {
            mPopupWindow = new AnimationPopupWindow(getActivity(), mPopupWindowView);
        }
        mPopupWindow.showAtLocation(mRootView, Gravity.BOTTOM, 0, 0);
    }

    private TextView tvCouponName = null;
    private TextView tvCouponPrice = null;
    private TextView tvCouponExpiredData = null;
    private TextView tvCouponUseScope = null;
    private TextView tvCouponPlatform = null;
    private TextView tvCouponCode = null;
    private LinearLayout llUseScope = null;

    private void initPopupWindowView(MineCouponInfo.MineCouponBean mineCouponBean) {
        if (mineCouponBean == null) {
            return;
        }
        if (mPopupWindowView == null) {
            mPopupWindowView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_coupon_detail, null);
            mPopupWindowView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
            tvCouponName = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_name);
            tvCouponPrice = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_price);
            tvCouponExpiredData = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_expired_date);
            tvCouponUseScope = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_use_scope);
            tvCouponPlatform = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_platform);
            tvCouponCode = (TextView) mPopupWindowView.findViewById(R.id.tv_coupon_code);
            llUseScope = (LinearLayout) mPopupWindowView.findViewById(R.id.ll_use_scope);
        }

        tvCouponName.setText(mineCouponBean.name);
        tvCouponPrice.setText(mineCouponBean.couponType);
        if (!TextUtils.isEmpty(mineCouponBean.maxCoupon)) {
            tvCouponPrice.setText(mineCouponBean.couponType + "(" + mineCouponBean.maxCoupon + ")");
        }
        tvCouponExpiredData.setText(mineCouponBean.expiredDate + mineCouponBean.useLimit);
        if (TextUtils.isEmpty(mineCouponBean.useScope)) {
            llUseScope.setVisibility(View.GONE);
        } else {
            llUseScope.setVisibility(View.VISIBLE);
            tvCouponUseScope.setText(mineCouponBean.useScope);
        }
        tvCouponPlatform.setText(mineCouponBean.platform);
        tvCouponCode.setText(mineCouponBean.code);
    }


}
