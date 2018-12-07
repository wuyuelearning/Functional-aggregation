package tow_tab.product_fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyue on 2018/12/5.
 * describe:
 */

public class FAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragment = new ArrayList<>();
    private List<String> mTitle = new ArrayList<>();



    public FAdapter(FragmentManager fm, List<Fragment> f ,List<String >title) {
        super(fm);
        mFragment =f;
        mTitle = title;
    }



    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
