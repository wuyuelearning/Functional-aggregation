package pullablelayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.admin.projecttest.R;


/**
 * Created by wuyue on 2019/3/26.
 * description: 自定义下拉刷新
 * 参考： https://www.jianshu.com/p/d6a80e2c51dc
 */

/**
 * PullableLayout()--> initView()-->onFinishInflate  :初始化布局
 * -->onMeasure()-->onLayout() :测量并放置 头部视图和底部视图
 * --> onTouchEvent() : 点击事件，布局的移动,注意移动和抬起的处理，使用scroller.startScroll进行滑动，并且要覆写 computeScroll方法
 * --> onRefreshListener 滑动至最大滑动限制，触发事件
 */

public class PullableLayout extends ViewGroup {

    private View mHeader, mFooter;
    private ProgressBar mPbPullableHeader, mPbPullableFooter;
    private TextView mTvPullableHeader, mTvPullableFooter;
    private static final String KEY_TAG_PULLABLE = "TAG_PULLABLE";
    private Scroller mLayoutScroller;
    private int mLastChildIndex;

    public PullableLayout(Context context) {
        super(context);
        Log.d(KEY_TAG_PULLABLE, "...PullableLayout(context)");
        initView(context);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(KEY_TAG_PULLABLE, "...PullableLayout(context,attrs)");
        initView(context);
    }

    private void initView(Context context) {
        Log.d(KEY_TAG_PULLABLE, "...initView()");
        mLayoutScroller = new Scroller(context);
        mHeader = LayoutInflater.from(context).inflate(R.layout.fragment_pullable_layout_header, null);
        mFooter = LayoutInflater.from(context).inflate(R.layout.fragment_pullable_layout_footer, null);
        mTvPullableHeader = (TextView) mHeader.findViewById(R.id.tv_pullable_header);
        mTvPullableFooter = (TextView) mFooter.findViewById(R.id.tv_pullable_footer);
        mPbPullableHeader = (ProgressBar) mHeader.findViewById(R.id.pb_pullable_header);
        mPbPullableFooter = (ProgressBar) mFooter.findViewById(R.id.pb_pullable_footer);
    }

    @Override
    // 在构造函数后即调用，在onMeasure之前调用
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(KEY_TAG_PULLABLE, "...onFinishInflate()");
        mLastChildIndex = getChildCount() - 1;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mHeader.setLayoutParams(params);
        mFooter.setLayoutParams(params);
        addView(mHeader);
        addView(mFooter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(KEY_TAG_PULLABLE, "...onMeasure()");
        Log.d(KEY_TAG_PULLABLE, "...onMeasure()...getChildCount()= " + getChildCount());
        // 测量
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }


    private int mLayoutContentHeight;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //要在onLayout开头的地方将其置零，否则同样会因为重复累加得到错误的结果
        mLayoutContentHeight = 0;
        Log.d(KEY_TAG_PULLABLE, "...onLayout()");
        Log.d(KEY_TAG_PULLABLE, "...onLayout()...getChildCount()= " + getChildCount());
        // 置位
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeader) { // 头视图隐藏在顶端，layout(left,top,right,bottom),分别组成，头部view的左上和右下两个点坐标，(left,top),(right,bottom)
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...child == mHeader");
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...mLayoutContentHeight= " + mLayoutContentHeight);
                child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else if (child == mFooter) { // 底部视图隐藏在layoutcontent内容视图之后，同样是传入左上和右下两个点坐标，这里计算y轴部分的时候要考虑内容视图的高度
                // 是紧挨着内容视图的
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...child == mFooter");
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...mLayoutContentHeight= " + mLayoutContentHeight);
                child.layout(0, mLayoutContentHeight, getMeasuredWidth(), mLayoutContentHeight + getMeasuredHeight());
            } else { //  内容视图根据定义（插入）顺序，按由上到下的顺序在垂直方向进行排列，所以layout()的第二个传参，不能为固定为0，要使用mLayoutContentHeight
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...child == content");
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...mLayoutContentHeight= " + mLayoutContentHeight);
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + getMeasuredHeight());
                mLayoutContentHeight += child.getMeasuredHeight();
                Log.d(KEY_TAG_PULLABLE, "...onLayout()...mLayoutContentHeight= " + mLayoutContentHeight);
            }
        }
    }


    private int mLastMoveY; // 记录上一次时的纵坐标，用于计算纵轴的移动

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int headerEffectiveScrollY = mHeader.getMeasuredHeight() / 8;
        int footerEffectiveScrollY = mFooter.getMeasuredHeight() / 8;
        Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...y= " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_DOWN...y= " + y);
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_MOVE...y= " + y);
                Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_MOVE...mLastMoveY= " + mLastMoveY);
                int dy = mLastMoveY - y;  //  手向上滑，y值在减少，mLastMoveY为上一次纵坐标，mLastMoveY - y>0
                Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_MOVE...dy= " + dy);
//                scrollBy(0, dy);  //  让视图移动dy，但是仅是如此，视图可以一直拖动，所以需要设置，移动距离，到了距离后就不能再拖动视图了
                if (dy < 0) {  //  线下滑动，表示下拉
                    Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_MOVE...dy<0...Math.abs(getScrollY())= " + Math.abs(getScrollY()) + "...headerEffectiveScrollY= " + headerEffectiveScrollY);
                    if (Math.abs(getScrollY()) <= headerEffectiveScrollY) { // 设置头部视图最大拖动距离
                        mTvPullableHeader.setText("下拉刷新");
                        scrollBy(0, dy);
                        if (Math.abs(getScrollY()) >= headerEffectiveScrollY) {
                            mTvPullableHeader.setText("松开刷新");
                        }
                    }
                } else {
                    Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_MOVE...dy>0...Math.abs(getScrollY())= " + Math.abs(getScrollY()) + "...footerEffectiveScrollY= " + footerEffectiveScrollY);
                    if (Math.abs(getScrollY()) <= footerEffectiveScrollY) { // 设置头部视图最大拖动距离
                        mTvPullableFooter.setText("上拉加载");
                        scrollBy(0, dy);
                    }
                    if (Math.abs(getScrollY()) >= footerEffectiveScrollY) {
                        mTvPullableFooter.setText("松开加载");
                    }
                }
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(getScrollY()) >= headerEffectiveScrollY) {
                    Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_UP...getScrollY= " + getScrollY() + "...getScrollY()+headerEffectiveScrollY= " + (getScrollY() + headerEffectiveScrollY));
                    if (getScrollY() < 0) {  //  下拉
                        mTvPullableHeader.setVisibility(GONE);
                        mPbPullableHeader.setVisibility(VISIBLE);
                        mTvPullableFooter.setVisibility(VISIBLE);
                        mPbPullableFooter.setVisibility(GONE);
//                        mLayoutScroller.startScroll(0, getScrollY(), 0, -(getScrollY() + headerEffectiveScrollY), 650);
                        mLayoutScroller.startScroll(0, getScrollY(), 0, headerEffectiveScrollY - 60, 650);
                        mRefreshListener.onPullDownRefresh();//  调用PullableLayoutFragment中的onPullDownRefresh回调
                    } else {
                        mTvPullableHeader.setVisibility(VISIBLE);
                        mPbPullableHeader.setVisibility(GONE);
                        mTvPullableFooter.setVisibility(GONE);
                        mPbPullableFooter.setVisibility(VISIBLE);
//                        mLayoutScroller.startScroll(0, getScrollY(), 0, -(getScrollY() + headerEffectiveScrollY), 650);
                        mLayoutScroller.startScroll(0, getScrollY(), 0, 60 - headerEffectiveScrollY, 650);
                        mRefreshListener.onPullUpRefresh();//  调用PullableLayoutFragment中的onPullUPRefresh回调
                    }

                } else {
                    Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...ACTION_UP...getScrollY= " + getScrollY());
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 650);
                }
                break;
        }
        Log.d(KEY_TAG_PULLABLE, "...onTouchEvent()...mLastMoveY= " + mLastMoveY);
//        mLastInterceptY =0;
        postInvalidate();//刷新界面
        return true;
    }

    @Override
    public void computeScroll() { // 如果不覆写，startScroll无效
        super.computeScroll();
        if (mLayoutScroller.computeScrollOffset()) {
            scrollTo(0, mLayoutScroller.getCurrY());
        }
        postInvalidate(); //刷新界面
    }

    private onRefreshListener mRefreshListener;

    public void setRefreshListener(onRefreshListener mRefreshListener) {
        this.mRefreshListener = mRefreshListener;
    }

    public void refreshDone() {
        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 650);
        mTvPullableHeader.setVisibility(VISIBLE);
        mTvPullableHeader.setText("下拉刷新");
        mPbPullableHeader.setVisibility(GONE);
        mTvPullableFooter.setVisibility(VISIBLE);
        mTvPullableFooter.setText("上拉加载");
        mPbPullableFooter.setVisibility(GONE);
    }

    private int mLastInterceptY;
    /**
     * 滑动冲突,拦截判断
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y > mLastMoveY) { //  下滑操作
                    View child = getChildAt(0);
                    if (child instanceof AdapterView) {
                        intercept = lvDownIntercept(child);
                    } else if (child instanceof RecyclerView) {
                        intercept = rvDownIntercept(child);
                    }
                } else if (y < mLastMoveY) { // 上滑操作
                    View child = getChildAt(mLastChildIndex);
                    Log.d(KEY_TAG_PULLABLE, "...onInterceptTouchEvent...mLastChildIndex= " + mLastChildIndex);
                    if (child instanceof AdapterView) {
                        intercept = lvUpIntercept(child);
                    } else if (child instanceof RecyclerView) {
                        intercept = rvUpIntercept(child);
                    }
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mLastMoveY = y;
        return intercept;
    }

    //RecyclerView  下拉拦截，判断是否
    private boolean rvDownIntercept(View child) {
        boolean intercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollOffset() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    /**
     * RecyclerView  上拉拦截，判断是否到达底部
     * <p>
     * computeVerticalScrollExtent()是当前屏幕显示的区域高度，
     * computeVerticalScrollOffset() 是当前屏幕之前滑过的距离，
     * computeVerticalScrollRange()是整个View控件的高度。
     */
    private boolean rvUpIntercept(View child) {
        boolean intercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        Log.d(KEY_TAG_PULLABLE, "...onInterceptTouchEvent...computeVerticalScrollExtent=" + recyclerView.computeVerticalScrollExtent()
                + "...computeVerticalScrollOffset=" + recyclerView.computeVerticalScrollOffset()
                + "...computeVerticalScrollRange=" + recyclerView.computeVerticalScrollRange());
        // 超出下边界，拦截
        if (recyclerView.computeVerticalScrollExtent()
                + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
            intercept = true;
        }
        return intercept;
    }

    //listView  下拉拦截
    private boolean lvDownIntercept(View child) {
        boolean intercept;
        AdapterView adapter = (AdapterView) child;
        if (adapter.getFirstVisiblePosition() != 0 || adapter.getChildAt(0).getTop() != 0) {
            intercept = false;
        } else {
            intercept = true;
        }
        return intercept;
    }

    //listView  上拉拦截
    private boolean lvUpIntercept(View child) {
        boolean intercept = false;
        AdapterView adapter = (AdapterView) child;
        Log.d(KEY_TAG_PULLABLE, "...onInterceptTouchEvent...adapter.getCount()= " + adapter.getCount()
                + "...adapter.getChildCount()= " + adapter.getChildCount() + "...mLastChildIndex= " + mLastChildIndex);
        if (adapter.getLastVisiblePosition() == adapter.getCount() - 1
                || adapter.getChildAt(adapter.getChildCount() - 1).getBottom() == getMeasuredHeight()) {
            intercept = true;
        }
        return intercept;
    }
}
