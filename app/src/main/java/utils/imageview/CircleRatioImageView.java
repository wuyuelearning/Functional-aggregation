package utils.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2019/4/28.
 * description:
 */

public class CircleRatioImageView extends RatioImageView {
    private float[] radiusArray = {0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
    private Path clipPath = new Path();
    private RectF rectF = new RectF();

    private PaintFlagsDrawFilter mDrawFilter;


    public CircleRatioImageView(Context context) {
        this(context, null, 0);
    }

    public CircleRatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleRatioImageView);
        try {
            float leftTop = ta.getFloat(R.styleable.CircleRatioImageView_radiusLeftTop, 20f);
            float rightTop = ta.getFloat(R.styleable.CircleRatioImageView_radiusRightTop, 20f);
            float leftBottom = ta.getFloat(R.styleable.CircleRatioImageView_radiusLeftBottom, 0f);
            float rightBottom = ta.getFloat(R.styleable.CircleRatioImageView_radiusRightBottom, 0f);
            setRadius(leftTop, rightTop, rightBottom, leftBottom);
        } finally {
            ta.recycle();
        }
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    }

    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {
        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;
        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth();
        int h = this.getHeight();
        rectF.set(0, 0, w, h);
        clipPath.addRoundRect(rectF, radiusArray, Path.Direction.CW);
        canvas.setDrawFilter(mDrawFilter);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}


