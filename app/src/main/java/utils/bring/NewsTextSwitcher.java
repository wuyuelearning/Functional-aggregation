package utils.bring;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2019/4/29.
 * description: textview 轮播
 */

public class NewsTextSwitcher extends LinearLayout {
    private TextSwitcher mTsContent;
    private Runnable mRunnable;
    private int mPositon = 0;
    private int startY, endY;
    private boolean hasPostRunnable = false;
    private boolean isShow = false;
    private int offsetY = 100;
    private List<String> mList;
    private Handler mHandler;
    private boolean isFirstime;

    public NewsTextSwitcher(Context context) {
        super(context);
    }

    public NewsTextSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_news_text_switcher, this);
        mTsContent = (TextSwitcher) view.findViewById(R.id.ts_content);
        mTsContent.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setSingleLine();
                textView.setTextSize(20);
                return textView;
            }
        });

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
//                if (mPositon == mList.size() - 1) {
//                    mPositon = 0;
//                }
//                if (isFirstime) {
//                    isFirstime = false;
//                    mPositon++;
//                    mTsContent.setText(mList.get(mPositon));
//                } else {
//                    mTsContent.setText(mList.get(mPositon));
//                }
//                mPositon++;
//                mHandler.postDelayed(mRunnable, 3000);
                if (mPositon == mList.size() - 1) {
                    mPositon = 0;
                }
                if (isFirstime) {  // 因为首次进入的时候会默认展示第一条，所以第一次轮播要跳过第一条
                    mTsContent.setInAnimation(getContext(), R.anim.news_text_switcher_in);
                    mTsContent.setOutAnimation(getContext(), R.anim.news_text_switcher_out);
                    mPositon++;
                    isFirstime = false;
                }
                mTsContent.setText(mList.get(mPositon));
                mPositon++;
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

    public void startCarousel() {
//        mTsContent.setText(mList.get(0));
//        isFirstime = true;
//        if (mList.size() > 1) {
//            if (!hasPostRunnable) {
//                hasPostRunnable = true;
//                mHandler.postDelayed(mRunnable, 3000);
//            }
//        } else {
//            hasPostRunnable = false;
//        }

        isFirstime = true;
        mTsContent.setInAnimation(null); // 第一次进入 不做进入动画
        mTsContent.setOutAnimation(getContext(), R.anim.news_text_switcher_out);
        mTsContent.setText(mList.get(0)); // 为了进入页面的时候就有信息，否则要等待动画进入
        if (mList.size() > 1) {  //  多于一条信息  进行轮播
            mHandler.postDelayed(mRunnable, 3000);
        }
    }

    public List<String> getContentList() {
        return mList;
    }

    public void stopCarousel() {
        mHandler.removeCallbacks(mRunnable);
        hasPostRunnable = false;
    }
}
