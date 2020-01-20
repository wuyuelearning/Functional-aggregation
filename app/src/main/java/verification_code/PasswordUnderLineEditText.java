package verification_code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import com.example.admin.projecttest.R;

import utils.bring.MobileUtil;


/**
 * Created by SteveYan on 2017/11/2.
 */

public class PasswordUnderLineEditText extends AppCompatEditText {

    private TextIndexChangeListener mTextIndexChangeListener;
    private int lineCount;
    private int lineMargin;
    private int linePaddingBottom;
    private int textSize;
    private Paint mLinePaint;
    private TextPaint mTextPaint;
    private CharSequence txt = "";
    private boolean error = false;
    private Context context;

    public PasswordUnderLineEditText(Context context) {
        this(context, null);
    }

    public PasswordUnderLineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        int lineColor = getResources().getColor(R.color.color_d8d8d8);
        int changedTextColor = getResources().getColor(android.R.color.black);
        lineCount = 6;
        textSize = MobileUtil.dp2px(context, 18);
        lineMargin = MobileUtil.dp2px(context, 12);
        linePaddingBottom = MobileUtil.dp2px(context, 10);
        int lineHeight = MobileUtil.dp2px(context, 1);


        setGravity(Gravity.CENTER_VERTICAL);
        setTextColor(getResources().getColor(android.R.color.transparent));
        setBackgroundColor(Color.WHITE);
        setCursorVisible(false);
        setInputType(InputType.TYPE_CLASS_NUMBER);
        setFilters(new InputFilter[]{new InputFilter.LengthFilter(lineCount)});

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(lineColor);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(lineHeight);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(changedTextColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setStyle(Paint.Style.FILL);
    }

    public void setError(boolean error) {
        this.error = error;
        invalidate();
    }

    public void setTextIndexChangeListener(TextIndexChangeListener textIndexChangeListener) {
        this.mTextIndexChangeListener = textIndexChangeListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        canvas.save();

        int singleLineWidth = width / lineCount;
        int startX = 0;
        int stopX = 0;
        int startY = height - linePaddingBottom;


        for (int i = 0; i < lineCount; i++) {
            startX = i * singleLineWidth + lineMargin;
            stopX = (i + 1) * singleLineWidth - lineMargin;
            int txtLength = 0;
            if (!TextUtils.isEmpty(txt)) {
                txtLength = txt.length();
            }
//            if (error) {
//                if (i < txtLength) {
//                    mLinePaint.setColor(getResources().getColor(R.color.color_d8d8d8));
//                } else {
//                    mLinePaint.setColor(getResources().getColor(R.color.msgCodeErrorHintColor));
//                }
//            } else {
//                mLinePaint.setColor(getResources().getColor(R.color.color_d8d8d8));
//            }

            if (i < txtLength) {
                mLinePaint.setColor(getResources().getColor(R.color.msgCodeErrorHintColor));
            } else {
                mLinePaint.setColor(getResources().getColor(R.color.color_d8d8d8));
            }
            mLinePaint.setStyle(Paint.Style.STROKE);
            @SuppressLint("DrawAllocation")
            RectF rectF  =new RectF(startX, startY - MobileUtil.dp2px(context, 60), stopX, startY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(startX, startY - MobileUtil.dp2px(context, 60), stopX, startY,MobileUtil.dp2px(context, 6),MobileUtil.dp2px(context, 6), mLinePaint);
            } else {
                canvas.drawRoundRect(rectF,MobileUtil.dp2px(context, 6),MobileUtil.dp2px(context, 6), mLinePaint);
            }

//            canvas.drawLine(startX, startY - MobileUtil.dp2px(context, 40), stopX, startY - MobileUtil.dp2px(context, 40), mLinePaint);
//            canvas.drawLine(stopX, startY - MobileUtil.dp2px(context, 40), stopX, startY, mLinePaint);
//            canvas.drawLine(stopX, startY, startX, startY, mLinePaint);
//            canvas.drawLine(startX, startY, startX, startY - MobileUtil.dp2px(context, 40), mLinePaint);
        }
        for (int i = 0; i < txt.length(); i++) {
            String c = String.valueOf(txt.charAt(i));
            startX = i * singleLineWidth + lineMargin;
            stopX = (i + 1) * singleLineWidth - lineMargin;
            canvas.drawText(c, (startX + stopX) / 2 - textSize / 4, (height + textSize) / 2, mTextPaint);
        }
        canvas.restore();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        this.txt = text;
        int textLength = text.length();
        invalidate();
        if (mTextIndexChangeListener != null) {
            mTextIndexChangeListener.onTextIndexChange(text, textLength);
        }
    }

    public interface TextIndexChangeListener {

        void onTextIndexChange(CharSequence text, int length);
    }
}