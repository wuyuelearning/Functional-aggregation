package utils.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;


import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 防滑动干扰的ViewPager
 * Updated by J!nl!n 修改支持编辑预览布局
 */
public class BannerViewPager extends ViewPager {
    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode())
            preview(context, attrs);
    }

    public BannerViewPager(Context context) {
        super(context);
        if (isInEditMode())
            preview(context, null);
    }

    /**
     * 以后滑动不流畅冲突都可以把我复制过去
     */
    private int lastX;
    private int lastY;

    private VelocityTracker vTracker = null;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (getParent() != null) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    lastX = x;
                    lastY = y;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    if (vTracker == null) {
                        vTracker = VelocityTracker.obtain();
                    } else {
                        vTracker.clear();
                    }
                    vTracker.addMovement(event);
                    break;

                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    if (vTracker != null) {
                        vTracker.clear();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int deltaX = x - lastX;
                    int deltaY = y - lastY;
                    int minMove = 10;
                    vTracker.addMovement(event);
                    vTracker.computeCurrentVelocity(1000);
                    if (Math.abs(vTracker.getXVelocity()) >= Math.abs(vTracker
                            .getYVelocity()) && Math.abs(deltaX) >= (Math.abs(deltaY)) && Math.abs(deltaX) > minMove) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    private void preview(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
        List<View> viewList = new ArrayList<>();
        int layoutResId;
        if ((layoutResId = a.getResourceId(R.styleable.BannerViewPager_tools_layout0, 0)) != 0) {
            viewList.add(inflate(context, layoutResId, null));
        }
        a.recycle();

        setAdapter(new PreviewPagerAdapter(viewList));
    }


    /**
     * 这里传入一个list数组，从每个list中可以剥离一个view并显示出来
     */
    public static class PreviewPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        PreviewPagerAdapter(List<View> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mViewList.get(position) != null) {
                container.removeView(mViewList.get(position));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }
    }
}
