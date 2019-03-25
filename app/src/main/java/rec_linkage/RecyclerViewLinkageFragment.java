package rec_linkage;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;
import project_utils.MobileUtil;


/**
 * Created by wuyue on 2019/3/25.
 * description: RecyclerView联动
 */

public class RecyclerViewLinkageFragment extends Fragment {
    RecyclerView mRecLinkageLeft;
    RecyclerView mRecLinkageRight;
    RecyclerView mRecLinkageTop;
    Context mContext;
    private List<String> mData = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_linkage, container, false);
        mContext = getContext();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecLinkageLeft = (RecyclerView) view.findViewById(R.id.rec_left);
        mRecLinkageRight = (RecyclerView) view.findViewById(R.id.rec_right);
        mRecLinkageTop = (RecyclerView) view.findViewById(R.id.rec_top);
        initData();
        setAdapter();
    }

    private void setAdapter() {

        RecLinkageTopAdapter adapterTop = new RecLinkageTopAdapter(mContext, mData);
        LinearLayoutManager linearLayoutManagerTop = new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                final int width = MobileUtil.getScreenWidth(mContext) - MobileUtil.dip2px(340);
                return new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        linearLayoutManagerTop.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecLinkageTop.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = MobileUtil.dip2px(20);
                outRect.right = MobileUtil.dip2px(20);
            }
        });
        mRecLinkageTop.setLayoutManager(linearLayoutManagerTop);
        mRecLinkageTop.setAdapter(adapterTop);



        RecLinkageLeftAdapter adapterLeft = new RecLinkageLeftAdapter(mContext, mData);
        LinearLayoutManager linearLayoutManagerLeft = new LinearLayoutManager(getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                final int width = MobileUtil.getScreenWidth(mContext) - MobileUtil.dip2px(340);
                return new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        linearLayoutManagerLeft.setOrientation(LinearLayoutManager.VERTICAL);
        mRecLinkageLeft.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = MobileUtil.dip2px(20);
                outRect.bottom = MobileUtil.dip2px(20);
            }
        });
        mRecLinkageLeft.setLayoutManager(linearLayoutManagerLeft);
        mRecLinkageLeft.setAdapter(adapterLeft);


        RecLinkageRightAdapter adapterRight = new RecLinkageRightAdapter(mContext, mData,mRecLinkageTop);
        LinearLayoutManager linearLayoutManagerRight = new LinearLayoutManager(getContext());
        linearLayoutManagerRight.setOrientation(LinearLayoutManager.VERTICAL);
        mRecLinkageRight.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = MobileUtil.dip2px(20);
                outRect.bottom = MobileUtil.dip2px(20);
            }
        });
        mRecLinkageRight.setLayoutManager(linearLayoutManagerRight);
        mRecLinkageRight.setAdapter(adapterRight);

        mRecLinkageTop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(recyclerView.getScrollState()!=RecyclerView.SCROLL_STATE_IDLE){
                   if (adapterRight!=null && adapterRight.getInnerRecycleView()!=null){
                       adapterRight.getInnerRecycleView().scrollBy(dx,dy);
                   }
                    Log.d("scroll...","mRecLinkageTop..."+dx+"   "+dy);
                }
            }
        });
        mRecLinkageLeft.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(recyclerView.getScrollState()!=RecyclerView.SCROLL_STATE_IDLE){
                    mRecLinkageRight.scrollBy(dx,dy);
                    Log.d("scroll...","mRecLinkageLeft..."+dx+"   "+dy);
                }
            }
        });

        mRecLinkageRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(recyclerView.getScrollState()!=RecyclerView.SCROLL_STATE_IDLE){
                    mRecLinkageLeft.scrollBy(dx,dy);
                    Log.d("scroll...","mRecLinkageRight..."+dx+"   "+dy);
                }
            }
        });
    }

    



    private void initData() {
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
    }
}
