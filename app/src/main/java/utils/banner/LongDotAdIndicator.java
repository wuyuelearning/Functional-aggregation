package utils.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by xns on 2017/12/5.
 */

public class LongDotAdIndicator extends View implements AdIndicator {

    private int mMinPageNum = 2;

    /**
     * 默认的圆点直径大小，单位dp
     */
    private static final float DEFAULT_DOT_WIDTH = 6.0f;

    /**
     * 默认的圆点间距大小，单位dp
     */
    private static final float DEFAULT_GAP_WIDTH = 3.0f;

    /**
     * 选中状态画笔的宽度
     */
    private static final float DEFAULT_PAINT_WIDTH = 3.0f;

    /**
     * 未选中圆点默认颜色
     */
    private static final int DEFAULT_NORMAL_COLOR = 0x80FFFFFF;

    /**
     * 选中圆点默认颜色
     */
    private static final int DEFAULT_CURRENT_COLOR = Color.WHITE;

    /**
     * 当前选中的圆点画笔
     */
    private Paint mPaintCurrent = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 未选中的圆点的画笔
     */
    private Paint mPaintNormal = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 圆点之间的间距大小，单位为px
     */
    private int mRawGapWidth;

    /**
     * 圆点的直径大小，单位px
     */
    private int mRawDotWidth;

    /**
     * 选中长条直径大小，单位px
     */
    private int mRawLongDotWidth;

    /**
     * 当前显示的广告页面的索引（从0开始）
     */
    private int mCurrentPage;

    /**
     * 页面的总数量
     */
    private int mPageCount;

    private float mLongItemRadius;

    /**
     * 用来确定所画圆点位置的RectF对象，仅仅为了画圆点方便
     */
    private RectF ovalRectF = new RectF();

    public LongDotAdIndicator(Context context) {
        super(context);
        init();
    }

    public LongDotAdIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LongDotAdIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化设置画笔默认颜色与默认间距和圆点直径
     */
    public void init() {
        mPaintCurrent.setColor(DEFAULT_CURRENT_COLOR);
        mPaintCurrent.setStrokeWidth(DEFAULT_PAINT_WIDTH);
        mPaintNormal.setColor(DEFAULT_NORMAL_COLOR);
        mPaintNormal.setStyle(Paint.Style.FILL);
        mPaintNormal.setStrokeWidth(DEFAULT_PAINT_WIDTH);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final float density = displayMetrics.density;
        // 向上取整
        mRawDotWidth = (int) Math.ceil(density * DEFAULT_DOT_WIDTH);
        mRawGapWidth = (int) Math.ceil(density * DEFAULT_GAP_WIDTH);
        mRawLongDotWidth = (int) Math.ceil(density * 24.0f);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mLongItemRadius = 20;
        } else {
            mLongItemRadius = 8;
        }
    }

    public void setSelectedPaintStyle(Paint.Style style) {
        mPaintCurrent.setStyle(style);
    }

    public void setUnselectedPaintStyle(Paint.Style style) {
        mPaintNormal.setStyle(style);
    }

    public void setPaintWidth(int width) {
        mPaintCurrent.setStrokeWidth(width);
        requestLayout();
        invalidate();
    }

    public void setUnselectPaintWidth(int width) {
        mPaintNormal.setStrokeWidth(width);
    }


    public void setColor(int color) {
        mPaintCurrent.setColor(color);
        mPaintNormal.setColor(color);
        invalidate();
    }

    /**
     * 设置选中状态的圆点颜色
     *
     * @param color ARGB颜色值
     */
    public void setSelectedColor(int color) {
        mPaintCurrent.setColor(color);
        invalidate();
    }

    /**
     * 返回选中状态的圆点的颜色
     *
     * @return ARGB颜色值
     */
    public int getSelectedColor() {
        return mPaintCurrent.getColor();
    }

    /**
     * 设置未选中状态的圆点颜色
     *
     * @param color ARGB颜色值
     */
    public void setUnselectedColor(int color) {
        mPaintNormal.setColor(color);
        invalidate();
    }

    /**
     * 返回未选中状态的圆点的颜色
     *
     * @return ARGB颜色值
     */
    public int getUnselectedColor() {
        return mPaintNormal.getColor();
    }

    /**
     * 设置圆点之间的间距大小，单位dp
     *
     * @param width 间距大小
     */
    public void setGapWidth(float width) {
        final float density = getResources().getDisplayMetrics().density;
        mRawGapWidth = (int) Math.ceil(width * density);
        invalidate();
    }

    /**
     * 设置圆点直径的大小，单位dp
     *
     * @param width 圆点直径
     */
    public void setDotWidth(float width) {
        final float density = getResources().getDisplayMetrics().density;
        mRawDotWidth = (int) Math.ceil(width * density);
        invalidate();
    }

    public void setMinPageNumToDisplay(int num) {
        mMinPageNum = num;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int count;

        if (isInEditMode()) {
            count = 7;
            mCurrentPage = 2;
        } else {
            count = mPageCount;
        }

        if (count < mMinPageNum) {
            return;
        }

        final float normalDotWidthAndGap = mRawDotWidth + mRawGapWidth;
        final float indicatorWidth = (count - 1) * normalDotWidthAndGap + mRawLongDotWidth;

        // 计算出指示器相对左上角应当偏移的位置，稍后将画布移动到偏移位置
        float translateX = (getWidth() + getPaddingLeft() - getPaddingRight() - indicatorWidth) / 2.0f;
        float translateY = (getHeight() + getPaddingTop() - getPaddingBottom() - mRawDotWidth) / 2.0f;

        canvas.save();
        canvas.translate(translateX, translateY);

        float usedWidth = 0;
        for (int i = 0; i < count; ++i) {
            if (mCurrentPage == i) {
                ovalRectF.set(usedWidth, 0, usedWidth + mRawLongDotWidth, mRawDotWidth);
                canvas.drawRoundRect(ovalRectF, mLongItemRadius, mLongItemRadius, mPaintCurrent);
                usedWidth += mRawLongDotWidth;
            } else {
                ovalRectF.set(usedWidth, 0, usedWidth + mRawDotWidth, mRawDotWidth);
                canvas.drawOval(ovalRectF, mPaintNormal);
                usedWidth += mRawDotWidth;
            }
            usedWidth += mRawGapWidth;
        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec));
    }

    /**
     * 计算指示器View的宽度应该是多少像素
     *
     * @param measureSpec measureSpec
     * @return View的宽度
     */
    private int measureWidth(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (isInEditMode()) mPageCount = 7;
            if (mPageCount > 1) {
                result = (mPageCount - 1) * (mRawDotWidth + mRawGapWidth) + mRawLongDotWidth
                        + getPaddingLeft() + getPaddingRight();
            } else {
                result = getPaddingLeft() + getPaddingRight();
            }
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    /**
     * 计算指示器View的高度应该是多少像素
     *
     * @param measureSpec measureSpec
     * @return View的高度
     */
    private int measureHeight(int measureSpec) {
        float result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mRawDotWidth + mPaintCurrent.getStrokeWidth()
                    + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return (int) Math.ceil(result);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
        // TODO 保存状态
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        // TODO 恢复状态
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        if (BuildConfig.LOG_DEBUG) {
//            Debug.info(Debug_TAG, "position：" + position);
//            Debug.info(Debug_TAG, "positionOffset：" + positionOffset);
//            Debug.info(Debug_TAG, "positionOffsetPixels：" + positionOffsetPixels);
//        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mPageCount != 0) {
            mCurrentPage = position % mPageCount;
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        if (BuildConfig.LOG_DEBUG) {
//            Debug.info(Debug_TAG, "state：" + state);
//        }
    }

    @Override
    public void notifyPageCountChanged(int current, int pageCount) {
        if (pageCount != 0) {
            mCurrentPage = current % pageCount;
            mPageCount = pageCount;
            invalidate();
            requestLayout(); // INVISIBLE => VISIBLE不会重新测量，如果是GONE => VISIBLE则会重新测量
        }
    }

}