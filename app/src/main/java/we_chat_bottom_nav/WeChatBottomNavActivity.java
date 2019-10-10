package we_chat_bottom_nav;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.View;


import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

import we_chat_bottom_nav.fragment.ChatFragment;
import we_chat_bottom_nav.fragment.ContactFragment;
import we_chat_bottom_nav.fragment.FindFragment;
import we_chat_bottom_nav.fragment.ProfileFragment;

/**
 *   仿微信滑动
 *   https://juejin.im/user/577e6c37165abd00554b2245
 */
public class WeChatBottomNavActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager mViewPager;
    String[] mTabTitles ={"微信","联系人","发现","我"};
    TabView mTabWeixin;
    TabView mTabContact;
    TabView mTabFind;
    TabView mTabProfile;
    private List<TabView> mTabViews = new ArrayList<>();
    private static final int INDEX_WEIXIN = 0;
    private static final int INDEX_CONTACT = 1;
    private static final int INDEX_FIND = 2;
    private static final int INDEX_PROFILE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_bottom_nav);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabWeixin = (TabView) findViewById(R.id.tab_weixin);
        mTabContact = (TabView) findViewById(R.id.tab_contact);
        mTabFind = (TabView) findViewById(R.id.tab_find);
        mTabProfile = (TabView) findViewById(R.id.tab_profile);

        mTabWeixin.setOnClickListener(this);
        mTabContact.setOnClickListener(this);
        mTabFind.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);

        mTabViews.add(mTabWeixin);
        mTabViews.add(mTabContact);
        mTabViews.add(mTabFind);
        mTabViews.add(mTabProfile);



        mViewPager.setOffscreenPageLimit(mTabTitles.length - 1);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            /**
             * @param position 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param positionOffset 左边View位移的比例
             * @param positionOffsetPixels 左边View位移的像素
             */
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 左边View进行动画
                mTabViews.get(position).setXPercentage(1-positionOffset);
                // 如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset >0){
                    mTabViews.get(position+1).setXPercentage(positionOffset);
                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateCurrentTab (int index){
        for (int i = 0 ;i<mTabViews.size();i++){
            if (index == i){
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_weixin:
                mViewPager.setCurrentItem(INDEX_WEIXIN ,false);
                updateCurrentTab(INDEX_WEIXIN);
                break;
            case R.id.tab_contact:
                mViewPager.setCurrentItem(INDEX_CONTACT ,false);
                updateCurrentTab(INDEX_CONTACT);
                break;
            case R.id.tab_find:
                mViewPager.setCurrentItem(INDEX_FIND ,false);
                updateCurrentTab(INDEX_FIND);
                break;
            case R.id.tab_profile:
                mViewPager.setCurrentItem(INDEX_PROFILE,false);
                updateCurrentTab(INDEX_PROFILE);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getTabFragment(position,mTabTitles[position]);
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }
    }

    private Fragment getTabFragment(int index,String title){
        Fragment fragment = null;

        switch (index){
            case INDEX_WEIXIN:
                fragment = ChatFragment.newInsatance(title);
                break;
            case INDEX_CONTACT:
                fragment = ContactFragment.newInsatance(title);
                break;
            case INDEX_FIND:
                fragment = FindFragment.newInsatance(title);
                break;
            case INDEX_PROFILE:
                fragment = ProfileFragment.newInsatance(title);
                break;
        }
        return fragment;
    }


}
