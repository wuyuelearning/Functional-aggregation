package utils.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2019/4/28.
 * description:
 */

public class RatioImageView  extends ImageView {
    private int originalWidth;
    private int originalHeight;

    public RatioImageView(Context context) {
        this(context, (AttributeSet)null);
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);

        try {
            this.originalWidth = ta.getInt(R.styleable.RatioImageView_RatioImageView_originWidth, 0);
            this.originalHeight = ta.getInt(R.styleable.RatioImageView_RatioImageView_originHeight, 0);
        } finally {
            ta.recycle();
        }

    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(this.originalWidth > 0 && this.originalHeight > 0) {
            float ratio = (float)this.originalWidth / (float)this.originalHeight;
            int width = View.MeasureSpec.getSize(widthMeasureSpec);
            int height = View.MeasureSpec.getSize(heightMeasureSpec);
            if(width > 0) {
                height = (int)((float)width / ratio);
            }

            this.setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
