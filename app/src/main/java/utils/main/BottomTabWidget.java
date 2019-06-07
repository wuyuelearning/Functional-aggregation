package utils.main;

/**
 * Created by wuyue on 2019/5/5.
 * description:
 */

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import utils.bring.L;


/**
 * Created by xns on 2017/10/24.
 */

public class BottomTabWidget extends LinearLayout {
    private static final String TAG = "BottomTabWidget";

    private OnTabSelectionChanged mSelectionChangedListener;
    private int mRealHeight;
    private int mSelectedTab = -1;
    TabHostHelper mTabHostHelper;


    public BottomTabWidget(Context context) {
        this(context, null);
    }

    public BottomTabWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutTransition(new LayoutTransition());
        setOrientation(HORIZONTAL);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int height = displayMetrics.widthPixels * 92 / (160 * 5);
        mRealHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mRealHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public void addView(View child) {
        if (child.getLayoutParams() == null) {
            final LinearLayout.LayoutParams lp = new LayoutParams(
                    0, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(0, 0, 0, 0);
            child.setLayoutParams(lp);
        }

        // Ensure you can navigate to the tab with the keyboard, and you can touch it
        child.setFocusable(true);
        child.setClickable(true);

        super.addView(child);

        // TODO: detect this via geometry with a tabwidget listener rather
        // than potentially interfere with the view's listener
        child.setOnClickListener(new TabClickListener(getTabCount() - 1));
    }

    public void setTabSelectionListener(OnTabSelectionChanged listener) {
        mSelectionChangedListener = listener;
    }

    public View getChildTabViewAt(int index) {
        return getChildAt(index);
    }

    /**
     * Returns the number of tab indicator views.
     *
     * @return the number of tab indicator views
     */
    public int getTabCount() {
        return getChildCount();
    }

    public void focusCurrentTab(int index) {
        final int oldTab = mSelectedTab;

        // set the tab
        setCurrentTab(index);

        // change the focus if applicable.
        if (oldTab != index) {
            getChildTabViewAt(index).requestFocus();
        }
    }

    private void setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount() || index == mSelectedTab) {
            return;
        }

        if (mSelectedTab != -1) {
            getChildTabViewAt(mSelectedTab).setSelected(false);
        }
        mSelectedTab = index;
        getChildTabViewAt(mSelectedTab).setSelected(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabHostHelper != null) {
            mTabHostHelper.onAttachedToWindow();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabHostHelper != null) {
            mTabHostHelper.onDetachedFromWindow();
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        if (mTabHostHelper != null) {
            L.d(TAG, "onSaveInstanceState() called");
            Parcelable superState = super.onSaveInstanceState();
            SavedState ss = new SavedState(superState);
            ss.curTab = mTabHostHelper.getCurrentTab() == null ? null : mTabHostHelper.getCurrentTab().tag;
            return ss;
        } else {
            return super.onSaveInstanceState();
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        if (mTabHostHelper != null) {
            L.d(TAG, "onRestoreInstanceState() called with: state = [" + state + "]");
            mTabHostHelper.selectByTag(ss.curTab);
        }
    }

    static class SavedState extends View.BaseSavedState {
        String curTab;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            curTab = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(curTab);
        }

        @Override
        public String toString() {
            return "BottomTabWidget.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " curTab=" + curTab + "}";
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    private class TabClickListener implements OnClickListener {
        private final int mTabIndex;

        private TabClickListener(int tabIndex) {
            mTabIndex = tabIndex;
        }

        public void onClick(View v) {
            mSelectionChangedListener.onTabSelectionChanged(mTabIndex, true);
        }
    }

    public interface OnTabSelectionChanged {
        /**
         * Informs the TabHost which tab was selected. It also indicates
         * if the tab was clicked/pressed or just focused into.
         *
         * @param tabIndex index of the tab that was selected
         * @param clicked  whether the selection changed due to a touch/click or
         *                 due to focus entering the tab through navigation.
         *                 {@code true} if it was due to a press/click and
         *                 {@code false} otherwise.
         */
        void onTabSelectionChanged(int tabIndex, boolean clicked);
    }
}

