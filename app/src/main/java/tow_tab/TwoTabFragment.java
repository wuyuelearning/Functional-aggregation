package tow_tab;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projecttest.R;


/**
 * Created by wuyue on 2018/11/8.
 * describe: 上下两层Tab
 */

public class TwoTabFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private TextView tvUsableCouponsTab;
    private TextView tvUsedCouponsTab;
    private TextView tvUnusableCouponsTab;
    private  AppBarLayout appBarLayout;

    //    private TextView selectedTab;
    private boolean[] selectedTab = new boolean[3];
    private CouponMainFragment usableCouponFragment, usedCouponFragemnt, unusableCouponFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_two_tab_container, container, false);
        initView();
        return mView;
    }

    private void initView() {

//        appBarLayout = (AppBarLayout) mView.findViewById(R.id.abl_holiday_list);
//        appBarLayout.setExpanded(true);

        tvUsableCouponsTab = (TextView) mView.findViewById(R.id.tv_usable_coupons);
        tvUsedCouponsTab = (TextView) mView.findViewById(R.id.tv_used_coupons);
        tvUnusableCouponsTab = (TextView) mView.findViewById(R.id.tv_unusable_coupons);
        setSelectedTabBg(CouponConstant.USABLE_COUPONS_TAB);
        tvUsableCouponsTab.setOnClickListener(this);
        tvUsedCouponsTab.setOnClickListener(this);
        tvUnusableCouponsTab.setOnClickListener(this);
    }

    private void switchFragment(String fragmentPos) {
        if (usableCouponFragment == null) {
            usableCouponFragment = new CouponMainFragment();
            usableCouponFragment.selectFragment(CouponConstant.USABLE_COUPONS_TAB);
        }
        if (usedCouponFragemnt == null) {
            usedCouponFragemnt = new CouponMainFragment();
            usedCouponFragemnt.selectFragment(CouponConstant.USED_COUPONS_TAB);
        }
        if (unusableCouponFragment == null) {
            unusableCouponFragment = new CouponMainFragment();
            unusableCouponFragment.selectFragment(CouponConstant.UNUSABLE_COUPONS_TAB);
        }
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        setAllFragmentUnselect(fragmentTransaction);
        setSelectedFragment(fragmentPos, fragmentTransaction);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void setSelectedFragment(String fragmentPos, FragmentTransaction fragmentTransaction) {
        switch (fragmentPos){
            case CouponConstant.USABLE_COUPONS_TAB:
                if (!usableCouponFragment.isAdded()) {
                    fragmentTransaction.add(R.id.fg_coupons_container, usableCouponFragment);
                }
                fragmentTransaction.show(usableCouponFragment);
                break;


            case CouponConstant.USED_COUPONS_TAB:
                if (!usedCouponFragemnt.isAdded()) {
                    fragmentTransaction.add(R.id.fg_coupons_container, usedCouponFragemnt);
                }
                fragmentTransaction.show(usedCouponFragemnt);
                break;

            case CouponConstant.UNUSABLE_COUPONS_TAB:
                if (!unusableCouponFragment.isAdded()) {
                    fragmentTransaction.add(R.id.fg_coupons_container, unusableCouponFragment);
                }
                fragmentTransaction.show(unusableCouponFragment);
                break;
                default:
                    break;

        }
    }

    private void setAllFragmentUnselect(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.hide(usableCouponFragment);
        fragmentTransaction.hide(usedCouponFragemnt);
        fragmentTransaction.hide(unusableCouponFragment);
    }

    private void setAllTabUnselect() {
        tvUsableCouponsTab.setBackgroundResource(R.color.white);
        tvUsableCouponsTab.setTextColor(getResources().getColor(R.color.color_666666));
        tvUsedCouponsTab.setBackgroundResource(R.color.white);
        tvUsedCouponsTab.setTextColor(getResources().getColor(R.color.color_666666));
        tvUnusableCouponsTab.setBackgroundResource(R.color.white);
        tvUnusableCouponsTab.setTextColor(getResources().getColor(R.color.color_666666));
    }

    private void setSelectedTabBg(String tabPos) {
        setAllTabUnselect();

        switch (tabPos){
            case CouponConstant.USABLE_COUPONS_TAB:
                tvUsableCouponsTab.setBackgroundResource(R.drawable.coupon_shape);
                tvUsableCouponsTab.setTextColor(getResources().getColor(R.color.default_page_bg));
                break;
            case CouponConstant.USED_COUPONS_TAB:
                tvUsedCouponsTab.setBackgroundResource(R.drawable.coupon_shape);
                tvUsedCouponsTab.setTextColor(getResources().getColor(R.color.default_page_bg));
                break;
            case  CouponConstant.UNUSABLE_COUPONS_TAB:
                tvUnusableCouponsTab.setBackgroundResource(R.drawable.coupon_shape);
                tvUnusableCouponsTab.setTextColor(getResources().getColor(R.color.default_page_bg));
                break;
                default:
                    break;
        }

        switchFragment(tabPos);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_usable_coupons:
                setSelectedTabBg(CouponConstant.USABLE_COUPONS_TAB);
                break;
            case R.id.tv_used_coupons:
                setSelectedTabBg(CouponConstant.USED_COUPONS_TAB);
                break;
            case R.id.tv_unusable_coupons:
                setSelectedTabBg(CouponConstant.UNUSABLE_COUPONS_TAB);
                break;
            default:
                break;
        }
    }

}
