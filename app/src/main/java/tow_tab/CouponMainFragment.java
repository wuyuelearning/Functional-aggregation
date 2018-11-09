package tow_tab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

import tow_tab.product_fragment.AllCouponsFragment;
import tow_tab.product_fragment.HotelCouponsFragment;
import tow_tab.product_fragment.RouteCouponsFragment;
import tow_tab.product_fragment.TicketCouponsFragment;

/**
 * Created by wuyue on 2018/11/8.
 * describe:
 */

public class CouponMainFragment extends Fragment{

    private View mRootView;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private String fragmentPos ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_coupons_container, container, false);
        initFragments();
        initView();
        return mRootView;
    }

    public void selectFragment(String fragmentPos){
        this.fragmentPos =fragmentPos;
    }

    private void initFragments(){


        AllCouponsFragment allCouponsFragment =new AllCouponsFragment();
        TicketCouponsFragment ticketCouponsFragment =new TicketCouponsFragment();
        HotelCouponsFragment hotelCouponsFragment =new HotelCouponsFragment();
        RouteCouponsFragment routeCouponsFragment =new RouteCouponsFragment();

        allCouponsFragment.selectFragment(fragmentPos);
        ticketCouponsFragment.selectFragment(fragmentPos);
        hotelCouponsFragment.selectFragment(fragmentPos);
        routeCouponsFragment.selectFragment(fragmentPos);

        mFragments.add(allCouponsFragment);
        mFragments.add(ticketCouponsFragment);
        mFragments.add(hotelCouponsFragment);
        mFragments.add(routeCouponsFragment);



        PRODUCT_TYPE_ENUM [] enums =PRODUCT_TYPE_ENUM.values();
        for (PRODUCT_TYPE_ENUM e:enums){
            mTitles.add(e.getType());
        }

        mFragmentManager =getChildFragmentManager();
    }
    private void initView(){
        ViewPager viewPager =(ViewPager)mRootView.findViewById(R.id.vp_coupons);
        viewPager.setOffscreenPageLimit(2);
        CouponFragmentPagerAdapter couponFragmentPagerAdapter =new CouponFragmentPagerAdapter(mFragmentManager,mFragments,mTitles);
        viewPager.setAdapter(couponFragmentPagerAdapter);
        TabIndicator tabIndicator =(TabIndicator)mRootView.findViewById(R.id.ti_tab_layout);
        tabIndicator.setViewPager(viewPager);
    }
}
