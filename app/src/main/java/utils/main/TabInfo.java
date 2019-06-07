package utils.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.LinkedList;


/**
 * Created by xns on 2017/10/24.
 */

public class TabInfo {
    public static final int STATE_CHECKED = 1;
    public static final int STATE_UN_CHECKED = 0;
    public static final int STATE_NONE = -1;

    View tabView;

    private int mCheckState = STATE_NONE;

    String contentDesc;

    Fragment fragment;

    String tag;

    String fragmentClassName;

    Bundle args;

    private LinkedList<OnCheckedChangeListener> mOnCheckedChangeListeners = new LinkedList<>();


    public void removeOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener);
    }

    public void addOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener);
    }

    public Fragment getFragment() {
        return fragment;
    }

    public View getTabView() {
        return tabView;
    }

    void onSelectedChanged(boolean checked) {
        int oldState = mCheckState;
        int checkState = checked ? STATE_CHECKED : STATE_UN_CHECKED;
        if (mCheckState == checkState) {
            return;
        }
        mCheckState = checkState;
        if (oldState == STATE_NONE) {
            return;
        }
        for (OnCheckedChangeListener listener : mOnCheckedChangeListeners) {
            listener.onChanged(this, checked);
        }

    }

    public static TabInfo newTab(Context context, String tag, View view, String fragmentClassName, String contentDesc, Bundle args) {
        TabInfo tabInfo = new TabInfo();
        tabInfo.fragmentClassName = fragmentClassName;
        tabInfo.tag = tag;
        tabInfo.contentDesc = contentDesc;
        view.setContentDescription(contentDesc);
        tabInfo.tabView = view;
        tabInfo.args = args;
        return tabInfo;
    }

    public void setFragmentClassName(String fragmentClassName) {
        this.fragmentClassName = fragmentClassName;
    }

    public interface OnCheckedChangeListener {
        void onChanged(TabInfo tabInfo, boolean checked);
    }
}
