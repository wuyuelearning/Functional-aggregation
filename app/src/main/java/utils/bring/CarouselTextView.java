package utils.bring;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;


/**
 * Created by wuyue on 2019/4/29.
 * description: 上下轮播
 */

public class CarouselTextView extends LinearLayout {
    private TextView mTvContent;
    private Runnable mRunnable;
    private int mPositon = 0;
    private int startY, endY;
    private boolean hasPostRunnable = false;
    private boolean isShow = false;
    private int offsetY = 100;
    private List<String> mList;
    private Handler mHandler;
    private boolean isFirstime;

    public CarouselTextView(Context context) {
        this(context, null);
    }

    public CarouselTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_carousel_textview, this);
        mTvContent = (TextView) view.findViewById(R.id.tv_carousel_content);
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mPositon == mList.size() - 1) {
                    mPositon = 0;
                }
                if (isFirstime) {
                    isFirstime = false;
                    mPositon++;
                    mTvContent.setText(mList.get(mPositon));
                } else {
                    mTvContent.setText(mList.get(mPositon));
                }
                mPositon++;
                startY = offsetY;
                endY = 0;
                ObjectAnimator.ofFloat(mTvContent, "translationY", startY, endY).setDuration(300).start();
                mHandler.postDelayed(mRunnable, 3000);
            }
        };
    }


    public void setContentList(List<String> list) {
        this.mList = list;
        if (mList.size() > 1) {
            list.add(list.get(0));
        }
    }

    public List<String> getContentList() {
        return mList;
    }

    public void startCarousel() {
        mTvContent.setText(mList.get(0));
        isFirstime = true;
        if (mList.size() > 1) {
            if (!hasPostRunnable) {
                hasPostRunnable = true;
                mHandler.postDelayed(mRunnable, 3000);
            }
        } else {
            hasPostRunnable = false;
        }
    }

    public void stopCarousel() {
        mHandler.removeCallbacks(mRunnable);
        hasPostRunnable = false;
    }
}
