package utils.banner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.admin.projecttest.R;


/**
 * 作者：HuChangfu on 2017/7/14.
 * 邮箱：changfu.hu@lvmama.com
 * 版本：7.9.6
 * 描述：左下角带有“广告”图片的ImageView
 */

@SuppressLint("AppCompatCustomView")
public class AdImageView extends ImageView {
    private Paint mPaint;
    private RectF mRectF;
    private Bitmap mBitmap = null;
    private int mWidth;
    private int mHeight;

    public AdImageView(Context context) {
        super(context);
        init();
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.comm_icon_advertisement);
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.set(0, getHeight() - mHeight, mWidth, getHeight());
        canvas.drawBitmap(mBitmap, null, mRectF, mPaint);
    }
}
