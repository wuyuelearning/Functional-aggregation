package utils.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;

import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.admin.projecttest.R;


/**
 * Created by wuyue on 2019/4/28.
 * description:
 */

public class RoundCornerLinearLayout extends LinearLayout {
    private Path path;
    private int leftTopCorner;
    private int rightTopCorner;
    private int leftBottomCorner;
    private int rightBottomCorner;

    public RoundCornerLinearLayout(Context context) {
        this(context, null);
    }

    public RoundCornerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = null;

        try {
            array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerLinearLayout);
            leftTopCorner = array.getDimensionPixelOffset(R.styleable.RoundCornerLinearLayout_left_top_corner, 4);
            rightTopCorner = array.getDimensionPixelOffset(R.styleable.RoundCornerLinearLayout_right_top_corner, 4);
            leftBottomCorner = array.getDimensionPixelOffset(R.styleable.RoundCornerLinearLayout_left_bottom_corner, 4);
            rightBottomCorner = array.getDimensionPixelOffset(R.styleable.RoundCornerLinearLayout_right_bottom_corner, 4);
        } finally {
            if (array != null) {
                array.recycle();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawPath(canvas);
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawPath(canvas);
        super.dispatchDraw(canvas);
    }

    private void drawPath(Canvas canvas) {
        if (path == null) {
            path = new Path();
            RectF rectF = new RectF(0, 0, getWidth(), getHeight());
            path.addRoundRect(rectF,
                    new float[]{leftTopCorner, leftBottomCorner,
                            rightTopCorner, rightTopCorner,
                            leftBottomCorner, leftBottomCorner,
                            rightBottomCorner, rightBottomCorner},
                    Path.Direction.CW);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG));
        }
        canvas.clipPath(path);
    }

    public void setCorner(int leftTop, int rightTop, int leftBottom, int rightBottom) {
        leftTopCorner = leftTop;
        rightTopCorner = rightTop;
        leftBottomCorner = leftBottom;
        rightBottomCorner = rightBottom;
    }
}
