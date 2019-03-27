package pullablelayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2019/3/26.
 * description: 自定义下拉刷新
 */

public class PullableLayoutFragment extends Fragment {
    View mView;
    private PullableLayout mPullableLayout;
    private ImageView mIvPullable;
    private boolean change =true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_pullable_layout,container,false);
        initView();
        return mView;
    }
    private void initView(){
        mPullableLayout = (PullableLayout)mView.findViewById(R.id.pl_main);
        mIvPullable = (ImageView)mView.findViewById(R.id.iv_pullable);
        setPullListener();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (change){
                mIvPullable.setBackgroundResource(R.drawable.bitmap2);
            } else {
                mIvPullable.setBackgroundResource(R.drawable.pic_default);
            }
            change=!change;
            mPullableLayout.refreshDone();
        }
    };
    private void setPullListener(){
        mPullableLayout.setRefreshListener(new onRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(3000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(0);
                    }
                }).start();
            }
        });
    }
}
