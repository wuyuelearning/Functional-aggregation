package utils.banner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.admin.projecttest.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import project_utils.BuildConstant;
import project_utils.CollectionUtils;
import project_utils.MobileUtil;
import utils.imageview.CircleRatioImageView;


/**
 * Created by Jinlin on 2015年10月16日14:37:48 修改支持自定义比例,由于首页与其他页面高度显示比例不同,则需要更加具体情况自定义
 */
public class BannerView extends RelativeLayout implements OnPageChangeListener,
        View.OnClickListener {

    /**
     * 默认自动切换广告的间隔时间秒数
     */
    private static final int DEFAULT_INTERVAL = 5 * 1000;

    /**
     * 默认的广告牌长宽比（高度除以宽度）
     */
    private static final float DEFAULT_ASPECT_RATIO = 0.5f;

    private int mScrollInterval = DEFAULT_INTERVAL;

    private static final int MSG_AUTO_SCROLL = 0;

    /**
     * 显示广告的ViewPager
     */
    protected BannerViewPager mPager;

    private float mAspectRatio = DEFAULT_ASPECT_RATIO;

    private boolean mScrollable = true;

    /**
     * 广告页面位置指示器
     */
    private AdIndicator mIndicator;

    private OnPageChangeListener mOnPageChangeListener;

    /**
     * 底部线
     */
    private int divider;
    private Paint divierPaint;

    /**
     * 广告被点击时的回调函数
     */
    private OnItemClickListener mOnItemClickListener;

    private boolean mRunning = false;

    /**
     * 内部Handler类引起内存泄露
     */
    private Handler mHandler = new MyHandler(this);
    private int mResourceId;
    private ImageView mImageView;


    private boolean isCircle;

    public void setCircle(boolean circle) {
        isCircle = circle;
    }

    private static class MyHandler extends Handler {
        private final WeakReference<BannerView> mViewGroup;

        MyHandler(BannerView view) {
            mViewGroup = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            BannerView adView = mViewGroup.get();
            if (msg.what == MSG_AUTO_SCROLL && adView != null && adView.mRunning) {
                adView.showNext();
                adView.startScrolling();
            }
        }
    }

    /**
     * 设置颜色，（选中和未选中颜色相同，默认当前点为空心）
     *
     * @param color 颜色值
     */
    public void setColor(int color) {
        mIndicator.setColor(color);
    }

    /**
     * 设置选中的颜色
     *
     * @param color 选中颜色值
     */
    @SuppressWarnings("unused")
    public void setSelectedDotColor(int color) {
        mIndicator.setSelectedColor(color);
    }

    /**
     * 设置未选中的颜色
     *
     * @param color 未选中颜色值
     */
    @SuppressWarnings("unused")
    public void setUnselectedDotColor(int color) {
        mIndicator.setUnselectedColor(color);
    }

    @SuppressWarnings("unused")
    public void setSelectedPaintStyle(Paint.Style style) {
        mIndicator.setSelectedPaintStyle(style);
    }

    @SuppressWarnings("unused")
    public boolean isScrollable() {
        return mScrollable;
    }

    @SuppressWarnings("unused")
    public void setScrollable(boolean scrollable) {
        this.mScrollable = scrollable;
    }

    /**
     * 显示的广告列表
     */
    private List<AdEntity> mAdList = new ArrayList<>();

    public BannerView(Context context) {
        super(context);
        init(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context 上下文对象
     * @param attrs   属性数组
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BannerView, 0, 0);
        int indicatorStyle = 0;
        int indicatorMarginBottom = -1;
        try {
            mScrollInterval = ta.getInt(R.styleable.BannerView_interval, DEFAULT_INTERVAL);
            mResourceId = ta.getResourceId(R.styleable.BannerView_defaultResId, 0);
            mAspectRatio = ta.getFloat(R.styleable.BannerView_ratio, DEFAULT_ASPECT_RATIO);
            divider = ta.getResourceId(R.styleable.BannerView_divider, 0);
            indicatorStyle = ta.getInt(R.styleable.BannerView_indicatorStyle, 0);
            indicatorMarginBottom = (int) ta.getDimension(R.styleable.BannerView_indicatorMarginBottom, -1);
        } finally {
            ta.recycle();
        }
        if (indicatorStyle == 1) {
            inflate(context, R.layout.layout_banner, this);
            mIndicator = (AdIndicator) findViewById(R.id.indicator);
            LayoutParams layoutParams =
                    new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.setMargins(0, 0, MobileUtil.dip2px(6), MobileUtil.dip2px(20));
            ((DotAdIndicator) mIndicator).setLayoutParams(layoutParams);
        } else if (indicatorStyle == 2) {//选中点为长点
            inflate(context, R.layout.layout_banner_long_dot, this);
            mIndicator = (AdIndicator) findViewById(R.id.indicator);
        } else { //默认样式
            inflate(context, R.layout.layout_banner, this);
            mIndicator = (AdIndicator) findViewById(R.id.indicator);
        }
        if (indicatorMarginBottom != -1) {
            LayoutParams layoutParams =
                    new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.setMargins(0, 0, 0, indicatorMarginBottom);
            ((View) mIndicator).setLayoutParams(layoutParams);
        }


        mPager = (BannerViewPager) findViewById(R.id.pager);
        mPager.addOnPageChangeListener(this);

        mImageView = new ImageView(context);
        mImageView.setScaleType(ScaleType.CENTER_CROP);
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mImageView.setImageResource(mResourceId);
        addView(mImageView);
    }


    /**
     * 设置广告列表，并从列表中第一张开始显示。
     *
     * @param list 广告列表
     */
    public void setAdList(List<AdEntity> list) {
        setAdList(list, true);
    }

    public void setAdList(List<AdEntity> list, boolean noDataGone) {
        mAdList.clear();
        if (list != null) {
            mAdList.addAll(list);
        }
        PagerAdapter adapter = mPager.getAdapter();
        if (adapter == null) {
            adapter = new BillboardPagerAdapter();
            mPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        int size = mAdList.size();
        if (size > 0) {
            setVisibility(View.VISIBLE);
            removeView(mImageView);
            mPager.setCurrentItem(BillboardPagerAdapter.MAX_COUNT / 2 + size - (BillboardPagerAdapter.MAX_COUNT / 2) % size);
            mIndicator.notifyPageCountChanged(mPager.getCurrentItem(), size);
            startScrolling();
        } else {
            mIndicator.notifyPageCountChanged(0, 1);
            stopScrolling();
            if (noDataGone) {
                setVisibility(View.GONE);
            }
        }
    }

    /**
     * 播放下一张广告，如果已经到了列表末尾，则回到头部重新播放。
     */
    public void showNext() {
        if (hasAdapter() && mPager.getAdapter().getCount() > 0) {
            final int item = (mPager.getCurrentItem() + 1) % mPager.getAdapter().getCount();
            mPager.setCurrentItem(item);
        }
    }

    /**
     * 播放上一张广告，如果已经到了列表开头，则切换到列表末尾。
     */
    @SuppressWarnings("unused")
    public void showPrevious() {
        if (hasAdapter() && mPager.getAdapter().getCount() > 0) {
            int item = (mPager.getCurrentItem() - 1 + mPager.getAdapter().getCount()) % mPager.getAdapter().getCount();
            mPager.setCurrentItem(item);
        }
    }

    /**
     * mPager是否有设定Adapter
     *
     * @return 有真无假
     */
    private boolean hasAdapter() {
        return mPager.getAdapter() != null;
    }

    /**
     * 得到当前正在显示的广告的坐标位置
     *
     * @return 当前广告位置
     */
    public int getCurrentItem() {
        return mPager.getCurrentItem();
    }

    /**
     * 设置页面状态变化时的回调。请参见{@link OnPageChangeListener}。
     *
     * @param listener 设定的回调
     */
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    /**
     * 自动切换到下一张广告所要等待的时间间隔（毫秒）
     *
     * @param milliseconds 时间间隔
     */
    public void setInterval(int milliseconds) {
        mScrollInterval = milliseconds;
    }

    public void startScrolling() {
        if (mScrollable) {
            mRunning = canRunning();
            if (mHandler != null) {
                mHandler.removeMessages(MSG_AUTO_SCROLL);
                if (mRunning) {
                    mHandler.sendEmptyMessageDelayed(MSG_AUTO_SCROLL, mScrollInterval);
                }
            }
        }
    }

    public boolean canRunning() {
        return getVisibility() == View.VISIBLE && mPager != null
                && mPager.getAdapter() != null
                && mPager.getAdapter().getCount() > 1;
    }

    public void stopScrolling() {
        mRunning = false;
        if (mHandler != null) {
            mHandler.removeMessages(MSG_AUTO_SCROLL);
        }
    }

    public void onResume() {
        startScrolling();
    }

    public void onPause() {
        stopScrolling();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * mAspectRatio);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImageView != null) {
            mImageView.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setAspectRatio(float ratio) {
        mAspectRatio = ratio;
        requestLayout();
    }

    /**
     * 设置当广告被点击时的回调
     *
     * @param listener 回调
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 当子页面被点击时回调的接口定义。
     */
    public interface OnItemClickListener {
        /**
         * 当子页面被点击时调用
         *
         * @param position 被点击的页面的索引值
         */
        void onItemClick(int position);
    }

    @Override
    public void onClick(View v) {
        if (CollectionUtils.notNullOrEmpty(mAdList)) {
            int index = mPager.getCurrentItem() % mAdList.size();
            AdEntity adEntity = mAdList.get(index);
            adEntity.responseClick();
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(index);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mIndicator.onPageScrollStateChanged(state);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                stopScrolling();
                break;
            default:
                if (!mRunning) {
                    startScrolling();
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        mIndicator.onPageSelected(position);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    private class BillboardPagerAdapter extends PagerAdapter {

        final static int MAX_COUNT = 2000;

        @Override
        public int getCount() {
            return mAdList == null ? 0 : (mAdList.size() > 1 ? MAX_COUNT : mAdList.size());
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            if (CollectionUtils.nullOrEmpty(mAdList)) {
                return null;
            } else {
                position = position % mAdList.size();
                ImageView img ;
                if (isCircle){
                    CircleRatioImageView circleRatioImageView = new CircleRatioImageView(getContext());
                    circleRatioImageView.setRadius(20,20,20,20);
                    img =circleRatioImageView;
                } else {
                    img =new ImageView(getContext());
                }
                img.setScaleType(ScaleType.CENTER_CROP);
                AdEntity e = mAdList.get(position);
                if (!TextUtils.isEmpty(e.getUrl())) {
                    Glide.with(getContext()).load(e.getUrl()).centerCrop().dontAnimate()
                            .placeholder(mResourceId).error(mResourceId).diskCacheStrategy(DiskCacheStrategy.RESULT).into(img);
                }
                img.setOnClickListener(BannerView.this);
                container.addView(img);
                return img;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return CollectionUtils.nullOrEmpty(mAdList) ? POSITION_NONE : super.getItemPosition(object);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startScrolling();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScrolling();
    }

    Paint paint = new Paint();

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean rect = super.drawChild(canvas, child, drawingTime);
        if (BuildConstant.DEBUG) {
            canvas.save();
            paint.setColor(0x7fff0000);
            paint.setTextSize(DensityUtil.sp2px(8.f));
            paint.setAntiAlias(true);
            canvas.drawText(new StringBuilder(String.valueOf(getContext().getPackageName())).append("-pid:").append(Process.myPid()).toString(), DensityUtil.dp2px(5.f), DensityUtil.dp2px(10.f), paint);
            canvas.drawText(String.format("像素密度:%s", Resources.getSystem().getDisplayMetrics().densityDpi), DensityUtil.dp2px(5.f), DensityUtil.dp2px(20.f), paint);
            canvas.drawText(String.format("分辨率:%sX%s", Resources.getSystem().getDisplayMetrics().widthPixels, Resources.getSystem().getDisplayMetrics().heightPixels), DensityUtil.dp2px(5.f), DensityUtil.dp2px(30.f), paint);
            canvas.drawText(Build.MANUFACTURER + "-" + Build.MODEL, DensityUtil.dp2px(5.f), DensityUtil.dp2px(40.f), paint);
        }
        return rect;
    }

    private void initPaint() {
        if (divider == 0) return;
        if (divierPaint != null) return;
        divierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        divierPaint.setColor(getResources().getColor(divider));
        divierPaint.setStrokeWidth(1);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (divider == 0) return;
        initPaint();
        canvas.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1, divierPaint);
    }

    private static final class DensityUtil {

        public static int dp2px(float dp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
        }

        public static int sp2px(float sp) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
        }

    }
}
