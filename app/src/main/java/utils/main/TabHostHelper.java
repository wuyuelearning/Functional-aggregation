package utils.main;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import utils.bring.L;



/**
 * Created by xns on 2017/10/24. 借鉴fragmenttabhost
 */
public class TabHostHelper {
    private static final java.lang.String TAG = "TabHostHelper";
    private List<TabInfo> mTabInfos = new ArrayList<>(2);
    private BottomTabWidget mBottomTabWidget;

    private FragmentManager mFragmentManager;
    private int mCurrentIndex = -1;
    private TabInfo mLastTabInfo;
    private Context mContext;
    private int mFragmentContainer;

    private OnSelectedChangeListener mOnSelectedChangeListener;
    private boolean mAttached;

    public TabHostHelper(FragmentActivity activity, BottomTabWidget tabLayout, @IdRes int fragmentContainer) {
        mContext = activity;
        mFragmentManager = activity.getSupportFragmentManager();
        mBottomTabWidget = tabLayout;
//        mBottomTabWidget.setMTabHostHelper(this);
        mBottomTabWidget =new BottomTabWidget(mContext);
        mFragmentContainer = fragmentContainer;
        mBottomTabWidget.setTabSelectionListener(new BottomTabWidget.OnTabSelectionChanged() {
            @Override
            public void onTabSelectionChanged(int tabIndex, boolean clicked) {
                select(tabIndex);
            }
        });
//        Flutter.addFlutter(activity, activity.getWindowManager());
    }

    public void setupTabs(List<TabInfo> tabInfoList) {
        if (mTabInfos.size() > 0) {
            mBottomTabWidget.removeAllViews();
            mTabInfos.clear();
        }
        mTabInfos.addAll(tabInfoList);
        List<TabInfo> tabInfos = mTabInfos;
        for (int i = 0, len = tabInfos.size(); i < len; i++) {
            addTab(tabInfos.get(i));
        }
    }

    private void addTab(final TabInfo tabInfo) {
        if (mAttached) {
            // If we are already attached to the window, then check to make
            // sure this tab's fragment is inactive if it exists.  This shouldn't
            // normally happen.
            tabInfo.fragment = mFragmentManager.findFragmentByTag(tabInfo.tag);
            if (tabInfo.fragment != null && !tabInfo.fragment.isHidden()) {
                final FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.hide(tabInfo.fragment);
                ft.commitAllowingStateLoss();
            }
        }
        View tabView = tabInfo.tabView;
        mBottomTabWidget.addView(tabView);

        if (mCurrentIndex == -1) {
            select(0);
        }
    }

    public void replaceTab(int index, TabInfo tabInfo) {
        if (mTabInfos.size() > index) {
            mTabInfos.set(index, tabInfo);
            mCurrentIndex = -1;
            select(index);
        }
    }

    public void select(@IntRange(from = 0) int index) {
        if (index < 0 || index >= mTabInfos.size()) {
            return;
        }
        if (mCurrentIndex == index) {
            return;
        }
        mBottomTabWidget.focusCurrentTab(index);
        mCurrentIndex = index;
        if (mAttached) {
            FragmentTransaction ft = doTabChanged(index, null);
            if (ft != null) {
                ft.commitAllowingStateLoss();
            }
        }
        if (mOnSelectedChangeListener != null) {
            mOnSelectedChangeListener.onSelected(index, mTabInfos.get(index));
        }

    }

    private FragmentTransaction doTabChanged(int index, FragmentTransaction ft) {
        TabInfo newTab = findTab(index);
        if (newTab != mLastTabInfo) {
            if (ft == null) {
                ft = mFragmentManager.beginTransaction();
            }
            if (mLastTabInfo != null && mLastTabInfo.fragment != null) {
                ft.hide(mLastTabInfo.fragment);
                mLastTabInfo.onSelectedChanged(false);
            }

            if (newTab != null) {
                if (newTab.fragment == null) {
                    if (index == 1 && newTab.fragmentClassName.equals("com.lvmama.android.hybrid.fragment.LvmmWebFragment")) {
//                        if (StringUtil.equalsNullOrEmpty(SharedPreferencesHelper.readString(mContext, SharedPreferencesKey.FlUTTER_URL_SWITCH))) {
//                            newTab.fragment = Flutter.createFragment("", StatusBarUtil.getStatusBarHeight(mContext));
//                        } else {
                            newTab.fragment = Fragment.instantiate(mContext, newTab.fragmentClassName, newTab.args);
//                        }
                    } else {
                        newTab.fragment = Fragment.instantiate(mContext,
                                newTab.fragmentClassName, newTab.args);
                    }
                    ft.add(mFragmentContainer, newTab.fragment, newTab.tag);
                } else {
                    ft.show(newTab.fragment);
                }
                newTab.onSelectedChanged(true);
            }
            mLastTabInfo = newTab;
        }
        return ft;
    }

    public void selectByTag(String tag) {
        if (tag == null) {
            return;
        }
        for (int i = 0, len = mTabInfos.size(); i < len; i++) {
            TabInfo tabInfo = mTabInfos.get(i);
            if (tag.equals(tabInfo.tag)) {
                select(i);
                break;
            }
        }
    }

    public Fragment findFragment(@IntRange(from = 0) int index) {
        if (index < 0 || index >= mTabInfos.size()) {
            return null;
        }
        TabInfo tabInfo = mTabInfos.get(index);
        return tabInfo.fragment;
    }

    public TabInfo findTab(int index) {
        if (index < 0 || index >= mTabInfos.size()) {
            return null;
        }
        return mTabInfos.get(index);
    }


    public void setOnSelectedChangeListener(OnSelectedChangeListener listener) {
        mOnSelectedChangeListener = listener;
    }

    void onAttachedToWindow() {
        L.d(TAG, "onAttachedToWindow() called");
        final String currentTag = getCurrentTab() == null ? null : getCurrentTab().tag;

        // Go through all tabs and make sure their fragments match
        // the correct state.
        FragmentTransaction ft = null;
        for (int i = 0, count = mTabInfos.size(); i < count; i++) {
            final TabInfo tab = mTabInfos.get(i);
            tab.fragment = mFragmentManager.findFragmentByTag(tab.tag);
            if (tab.fragment != null && !tab.fragment.isHidden()) {
                if (tab.tag.equals(currentTag)) {
                    // The fragment for this tab is already there and
                    // active, and it is what we really want to have
                    // as the current tab.  Nothing to do.
                    mLastTabInfo = tab;
                } else {
                    // This fragment was restored in the active state,
                    // but is not the current tab.  Deactivate it.
                    if (ft == null) {
                        ft = mFragmentManager.beginTransaction();
                    }
                    ft.hide(tab.fragment);
                }
            }
        }

        // We are now ready to go.  Make sure we are switched to the
        // correct tab.
        mAttached = true;
        ft = doTabChanged(mCurrentIndex, ft);
        if (ft != null) {
            ft.commitAllowingStateLoss();
            mFragmentManager.executePendingTransactions();
        }
    }

    void onDetachedFromWindow() {
        mAttached = false;
    }

    public interface OnSelectedChangeListener {
        void onSelected(int index, TabInfo tabInfo);
    }

    public TabInfo getCurrentTab() {
        if (mCurrentIndex < 0 || mCurrentIndex >= mTabInfos.size()) {
            return null;
        }
        return mTabInfos.get(mCurrentIndex);
    }

}
