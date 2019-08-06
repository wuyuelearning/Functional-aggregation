package pullablelayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

import project_utils.MobileUtil;

/**
 * Created by wuyue on 2019/3/26.
 * description: 自定义下拉刷新
 */

public class PullableLayoutFragment extends Fragment {
    View mView;
    private PullableLayout mPullableLayout;
    private ImageView mIvPullable;
    private RecyclerView mRVPullable;
    private boolean change = true;
    private List<String> mData = new ArrayList<>();
    private List<String> addData = new ArrayList<>();
    Context mContext;
    private PullableAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_pullable_layout, container, false);
        mContext = getContext();
        initView();
        return mView;
    }

    private void initView() {
//        setImgView();
        setRvView();

    }

    //  布局没有滑动冲突
    private void setImgView() {
//        mIvPullable = (ImageView) mView.findViewById(R.id.iv_pullable);
    }

    //  布局存在滑动冲突
    private void setRvView() {
        mPullableLayout = (PullableLayout) mView.findViewById(R.id.pl_main);
        mRVPullable = (RecyclerView) mView.findViewById(R.id.rv_pullable);
        setAdaper();
        setPullListener();
    }

    private void setAdaper() {
        initData();
        addData();
        adapter = new PullableAdapter(mContext, mData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                final int width = MobileUtil.getScreenWidth(mContext) - MobileUtil.dip2px(340);
                return new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRVPullable.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = MobileUtil.dip2px(20);
                outRect.bottom = MobileUtil.dip2px(20);
            }
        });
        mRVPullable.setLayoutManager(linearLayoutManager);
        mRVPullable.setAdapter(adapter);

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            RefreshImgData(msg);  //  布局没有滑动冲突
            refreshRvData(msg);    // recyclerview 存在滑动冲突
        }
    };

    private void RefreshImgData(Message msg) {
        if (change) {
            mIvPullable.setBackgroundResource(R.drawable.bitmap2);
        } else {
//            mIvPullable.setBackgroundResource(R.drawable.pic_default);
        }
        change = !change;
    }

    private void refreshRvData(Message msg) {
        if (msg.what == 0) {
            mData.clear();
            initData();
            adapter.resetData(mData);
        } else if (msg.what == 1) {
            adapter.addData(addData);
        }
        mPullableLayout.refreshDone();
    }

    private void setPullListener() {
        mPullableLayout.setRefreshListener(new onRefreshListener() {
            @Override
            public void onPullDownRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(0);
                    }
                }).start();
            }

            @Override
            public void onPullUpRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
    }

    private void addData() {
        for (int i = 0; i < 5; i++) {
            addData.add("1");
        }
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mData.add("1");
        }
    }
}
