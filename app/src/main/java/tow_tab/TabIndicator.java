package tow_tab;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by wuyue on 2018/11/8.
 * describe:
 */

public class TabIndicator extends LinearLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private int mMode;
    private int mTabPadding;
    private int mTextAppearance;
    private int tabBackground;
    private int mIndicatorOffset;
    private int mIndicatorWidth;
    private int mIndicatorHeight;
    private int mIndicatorMode;
    private int mUnderLineHeight;
    private Paint mPaint;
    private Paint mUnderLinePaint;
    public static final int MODE_SCROLL = 0;
    public static final int MODE_FIXED = 1;
    private int mSelectedPosition;
    private boolean mScrolling = false;
    private Runnable mTabAnimSelector;
    private ViewPager.OnPageChangeListener mListener;
    private static final int MATCH_PARENT = -1;
    private static final int WRAP_CONTENT = -2;

    public static final int[] TabIndicator = { 0x01010034, 0x7f0102f9, 0x7f0102fa, 0x7f0102fb, 0x7f0102fc, 0x7f0102fd, 0x7f0102fe, 0x7f0102ff, 0x7f010300 };
    public static final int TabIndicator_android_textAppearance = 0;
    public static final int TabIndicator_tpi_tabPadding = 1;
    public static final int TabIndicator_tpi_indicatorColor = 2;
    public static final int TabIndicator_tpi_indicatorHeight = 3;
    public static final int TabIndicator_tpi_underLineColor = 4;
    public static final int TabIndicator_tpi_underLineHeight = 5;
    public static final int TabIndicator_tpi_indicatorMode = 6;
    public static final int TabIndicator_tpi_tabBackground = 7;
    public static final int TabIndicator_tpi_mode = 8;

    private DataSetObserver mObserver = new DataSetObserver() {
        public void onChanged() {
            TabIndicator.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            TabIndicator.this.notifyDataSetInvalidated();
        }
    };

    public TabIndicator(Context context) {
        super(context);
        this.init(context, (AttributeSet)null, 0, 0);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0, 0);
    }

    @TargetApi(11)
    public TabIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public TabIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.setGravity(16);
        this.setHorizontalScrollBarEnabled(false);
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mUnderLinePaint = new Paint(1);
        this.mUnderLinePaint.setStyle(Paint.Style.FILL);
        this.applyStyle(context, attrs, defStyleAttr, defStyleRes);
        if(this.isInEditMode()) {
            this.addTemporaryTab();
        }

    }

    public void applyStyle(int resId) {
        this.applyStyle(this.getContext(), (AttributeSet)null, 0, resId);
    }

    private void applyStyle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs, TabIndicator, defStyleAttr, defStyleRes);

        int indicatorColor;
        int underLineColor;
        try {
            this.mTabPadding = a.getDimensionPixelSize(TabIndicator_tpi_tabPadding, 12);
            indicatorColor = a.getColor(TabIndicator_tpi_indicatorColor, -1);
            this.mIndicatorMode = a.getInt(TabIndicator_tpi_indicatorMode, -1);
            this.mIndicatorHeight = a.getDimensionPixelSize(TabIndicator_tpi_indicatorHeight, 2);
            underLineColor = a.getColor(TabIndicator_tpi_underLineColor, -3355444);
            this.mUnderLineHeight = a.getDimensionPixelSize(TabIndicator_tpi_underLineHeight, 1);
            this.mTextAppearance = a.getResourceId(TabIndicator_android_textAppearance, 0);
            this.tabBackground = a.getResourceId(TabIndicator_tpi_tabBackground, 0);
            this.mMode = a.getInteger(TabIndicator_tpi_mode, 0);
        } finally {
            a.recycle();
        }

        this.removeAllViews();
        this.mPaint.setColor(indicatorColor);
        this.mUnderLinePaint.setColor(underLineColor);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(this.mTabAnimSelector != null) {
            this.post(this.mTabAnimSelector);
        }

    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(this.mTabAnimSelector != null) {
            this.removeCallbacks(this.mTabAnimSelector);
        }

    }

    private TabView getTabView(int position) {
        return (TabView)this.getChildAt(position);
    }

    private void animateToTab(int position) {
        final TabView tv = this.getTabView(position);
        if(tv != null) {
            if(this.mTabAnimSelector != null) {
                this.removeCallbacks(this.mTabAnimSelector);
            }

            this.mTabAnimSelector = new Runnable() {
                public void run() {
                    if(!TabIndicator.this.mScrolling) {
                        switch(TabIndicator.this.mIndicatorMode) {
                            case -2:
                                int textWidth = TabIndicator.this.getTextWidth(tv);
                                TabIndicator.this.updateIndicator(tv.getLeft() + tv.getWidth() / 2 - textWidth / 2, textWidth);
                                break;
                            case -1:
                                TabIndicator.this.updateIndicator(tv.getLeft(), tv.getWidth());
                        }
                    }

                    TabIndicator.this.mTabAnimSelector = null;
                }
            };
            this.post(this.mTabAnimSelector);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        this.mListener = listener;
    }

    public void setViewPager(ViewPager view, boolean needClear) {
        if(this.mViewPager != view) {
            PagerAdapter adapter;
            if(this.mViewPager != null) {
                if(needClear) {
                    this.mViewPager.clearOnPageChangeListeners();
                }

                adapter = view.getAdapter();
                if(adapter != null) {
                    adapter.unregisterDataSetObserver(this.mObserver);
                }
            }

            adapter = view.getAdapter();
            if(adapter == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            } else {
                adapter.registerDataSetObserver(this.mObserver);
                this.mViewPager = view;
                view.addOnPageChangeListener(this);
                this.notifyDataSetChanged();
            }
        }
    }

    public void setViewPager(ViewPager view) {
        this.setViewPager(view, true);
    }

    public void setViewPager(ViewPager view, boolean removeAllPagerChangeListener, int initialPosition) {
        this.setViewPager(view, removeAllPagerChangeListener);
        this.setCurrentItem(initialPosition);
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        this.setViewPager(view, true, initialPosition);
    }

    public void updateIndicator(int offset, int width) {
        this.mIndicatorOffset = offset;
        this.mIndicatorWidth = width;
        this.invalidate();
    }

    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(0.0F, (float)(this.getHeight() - this.mUnderLineHeight), (float)this.getWidth(), (float)this.getHeight(), this.mUnderLinePaint);
        int x = this.mIndicatorOffset + this.getPaddingLeft();
        canvas.drawRect((float)x, (float)(this.getHeight() - this.mIndicatorHeight), (float)(x + this.mIndicatorWidth), (float)this.getHeight(), this.mPaint);
        if(this.isInEditMode()) {
            canvas.drawRect((float)this.getPaddingLeft(), (float)(this.getHeight() - this.mIndicatorHeight), (float)(this.getPaddingLeft() + this.getChildAt(0).getWidth()), (float)this.getHeight(), this.mPaint);
        }

    }

    public boolean isScrolling() {
        return this.mScrolling;
    }

    public void onPageScrollStateChanged(int state) {
        if(state == 0) {
            this.mScrolling = false;
            TextView tv = this.getTabView(this.mSelectedPosition);
            if(tv != null) {
                switch(this.mIndicatorMode) {
                    case -2:
                        int textWidth = this.getTextWidth(tv);
                        this.updateIndicator(tv.getLeft() + tv.getWidth() / 2 - textWidth / 2, textWidth);
                        break;
                    case -1:
                        this.updateIndicator(tv.getLeft(), tv.getMeasuredWidth());
                }
            }
        } else {
            this.mScrolling = true;
        }

        if(this.mListener != null) {
            this.mListener.onPageScrollStateChanged(state);
        }

    }

    public int getTextWidth(TextView tv) {
        return (int)tv.getPaint().measureText(tv.getText().toString());
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(this.mListener != null) {
            this.mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        TabView tv_scroll = this.getTabView(position);
        TabView tv_next = this.getTabView(position + 1);
        if(tv_scroll != null && tv_next != null) {
            int width_scroll = this.mIndicatorMode == -1?tv_scroll.getWidth():this.getTextWidth(tv_scroll);
            int width_next = this.mIndicatorMode == -1?tv_next.getWidth():this.getTextWidth(tv_next);
            float distance = this.mIndicatorMode == -1?(float)(width_scroll + width_next) / 2.0F:(float)(width_scroll / 2 + tv_scroll.getWidth() / 2 + tv_next.getWidth() / 2 - width_next / 2);
            int width = (int)((float)width_scroll + (float)(width_next - width_scroll) * positionOffset + 0.5F);
            int offset = this.mIndicatorMode == -1?(int)((float)tv_scroll.getLeft() + (float)width_scroll / 2.0F + distance * positionOffset - (float)width / 2.0F + 0.5F):(int)((float)(tv_scroll.getLeft() + tv_scroll.getWidth() / 2 - width_scroll / 2) + distance * positionOffset + 0.5F);
            this.updateIndicator(offset, width);
        }

    }

    public void onPageSelected(int position) {
        this.setCurrentItem(position);
        if(this.mListener != null) {
            this.mListener.onPageSelected(position);
        }

    }

    public void onClick(View v) {
        int position = ((Integer)v.getTag()).intValue();
        if(position == this.mSelectedPosition && this.mListener != null) {
            this.mListener.onPageSelected(position);
        }

        this.mViewPager.setCurrentItem(position, true);
    }

    public void setCurrentItem(int position) {
        TabView tv;
        if(this.mSelectedPosition != position) {
            tv = this.getTabView(this.mSelectedPosition);
            if(tv != null) {
                tv.setChecked(false);
            }
        }

        this.mSelectedPosition = position;
        tv = this.getTabView(this.mSelectedPosition);
        if(tv != null) {
            tv.setChecked(true);
        }

        this.animateToTab(position);
    }

    public int getCurrentIndex() {
        return this.mSelectedPosition;
    }

    private void notifyDataSetChanged() {
        this.removeAllViews();
        PagerAdapter adapter = this.mViewPager.getAdapter();
        int count = adapter.getCount();
        if(this.mSelectedPosition > count) {
            this.mSelectedPosition = count - 1;
        }

        for(int i = 0; i < count; ++i) {
            CharSequence title = adapter.getPageTitle(i);
            if(title == null) {
                title = "NULL";
            }

            TabView tv = new TabView(this.getContext());
            tv.setText((CharSequence)title);
            tv.setTag(Integer.valueOf(i));
            LayoutParams params;
            if(this.mMode == 0) {
                params = new LayoutParams(-2, -2);
                params.leftMargin = this.mTabPadding;
                params.rightMargin = this.mTabPadding;
                this.addView(tv, params);
            } else if(this.mMode == 1) {
                params = new LayoutParams(0, -1);
                params.weight = 1.0F;
                this.addView(tv, params);
            }
        }

        this.setCurrentItem(this.mSelectedPosition);
        this.requestLayout();
    }

    private void notifyDataSetInvalidated() {
        PagerAdapter adapter = this.mViewPager.getAdapter();
        int count = adapter.getCount();

        for(int i = 0; i < count; ++i) {
            TextView tv = this.getTabView(i);
            CharSequence title = adapter.getPageTitle(i);
            if(title == null) {
                title = "NULL";
            }

            tv.setText((CharSequence)title);
        }

        this.requestLayout();
    }

    private void addTemporaryTab() {
        for(int i = 0; i < 3; ++i) {
            CharSequence title = null;
            if(i == 0) {
                title = "流行新品";
            } else if(i == 1) {
                title = "最近上新";
            } else if(i == 2) {
                title = "人气热销";
            }

            TabView tv = new TabView(this.getContext());
            tv.setText(title);
            tv.setTag(Integer.valueOf(i));
            tv.setChecked(i == 0);
            if(this.mMode == 0) {
                tv.setPadding(this.mTabPadding, 0, this.mTabPadding, 0);
                this.addView(tv, new android.view.ViewGroup.LayoutParams(-2, -1));
            } else if(this.mMode == 1) {
                LayoutParams params = new LayoutParams(0, -1);
                params.weight = 1.0F;
                this.addView(tv, params);
            }
        }

    }

    class TabView extends android.support.v7.widget.AppCompatRadioButton {
        public TabView(Context context) {
            super(context, (AttributeSet)null, TabIndicator.this.mTextAppearance);
            this.init();
        }

        private void init() {
            this.setButtonDrawable(new ColorDrawable(0));
            this.setGravity(17);
            if(23 <= Build.VERSION.SDK_INT) {
                this.setTextAppearance(TabIndicator.this.mTextAppearance);
            } else {
                this.setTextAppearance(this.getContext(), TabIndicator.this.mTextAppearance);
            }

            if(0 != TabIndicator.this.tabBackground) {
                this.setBackgroundResource(TabIndicator.this.tabBackground);
            } else {
                setBackground(this, new ColorDrawable(0));
            }

            this.setSingleLine(true);
            this.setEllipsize(TextUtils.TruncateAt.END);
            this.setOnClickListener(TabIndicator.this);
        }

        public void setBackground(View view, Drawable drawable) {
            if(view != null) {
                int left = view.getPaddingLeft();
                int right = view.getPaddingRight();
                int top = view.getPaddingTop();
                int bottom = view.getPaddingBottom();
                if(Build.VERSION.SDK_INT >= 16) {
                    view.setBackground(drawable);
                } else {
                    view.setBackgroundDrawable(drawable);
                }

                view.setPadding(left, top, right, bottom);
            }

        }
    }


}

