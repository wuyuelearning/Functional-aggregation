package MutilRecy;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

import project_utils.CollectionUtils;
import project_utils.MobileUtil;

import static MutilRecy.Constract.*;

/**
 * Created by wuyue on 2018/12/3.
 * describe:  需要横向滑动，在最后一项之前，露出左边的一部分item（10dp），最后一项，露出右边的一部分item（10dp）
 * 需要继承  PagerSnapHelper
 *
 * 如果要露出左右两边的 直接 generateDefaultLayoutParams设置Item大小，小一点，就能露出两边了
 */

public class MultiAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mLayoutInflater;
    private RecyclerView innerView;
    private HorizontalViewHolder mFirstViewHolder;

    public MultiAdapter(Context c, List<String> list) {

        mContext = c;
        mData = new ArrayList<>();
        if (!CollectionUtils.nullOrEmpty(list)) {
            mData.addAll(list);
        }
        mLayoutInflater = LayoutInflater.from(c);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("MultiAdapter", "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case FIRST_TYPE_VIEW:
                viewHolder = new HorizontalViewHolder(mLayoutInflater.inflate(R.layout.multi_recy_inner_rv, null, false));
                mFirstViewHolder = ((HorizontalViewHolder) viewHolder);
                innerView = ((HorizontalViewHolder) viewHolder).mRv;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
                    //  设置Item大小，addItemDecoration是设置间隙的
                    //   需要注意xml布局的宽高
                    @Override
                    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                        final int width = MobileUtil.getScreenWidth(mContext) - MobileUtil.dip2px(40);
                        return new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                };
                //  设置Item之间间隙的方式，如果是横向滑动，设置 outRect.right 和outRect.left
                //  需要注意xml布局的宽高
                innerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        RecyclerView.ViewHolder holder = parent.findContainingViewHolder(view);
                        if (!CollectionUtils.nullOrEmpty(mData) && mData.size() > 1) {
                            if (holder != null) {
                                int position = holder.getAdapterPosition();
                                if (position == parent.getAdapter().getItemCount() - 1) {  //   如果是最后一个，则要设置右边间距
                                    outRect.right = MobileUtil.dip2px(10);
                                } else {
                                    outRect.right = 0;
                                }
                                outRect.left = MobileUtil.dip2px(10);
                            }
                        } else if (!CollectionUtils.nullOrEmpty(mData) && mData.size() == 1) {  // 如果只有一个 居中展示
                            outRect.left = MobileUtil.dip2px(20);
                            outRect.right = MobileUtil.dip2px(20);
                        }
                    }
                });
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                innerView.setLayoutManager(linearLayoutManager);
                //  如果需要居中，直接使用PagerSnapHelper就可以了
//                new PagerSnapHelper().attachToRecyclerView(innerView);
                new DynamicPagerSnapHelper(innerView,MobileUtil.dip2px(15)).attachToRecyclerView(innerView);
                setInnerViewScrollListener(innerView);
                break;
            case SECOND_TYPE_VIEW:
                viewHolder = new VerticalViewHolder(mLayoutInflater.inflate(R.layout.item_multi_recy_2, null, false));
                break;
        }

        return viewHolder;
    }

    private void setInnerViewScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    Log.e(MultiAdapter.class.getSimpleName(), "position = " + llm.findFirstVisibleItemPosition());
                    if (null != mFirstViewHolder && null != mFirstViewHolder.text11) {
                        //    text11.setText("1"); 需要设置初始量，这是活动后监听，所以第一项就需要固定先写成1，
                        mFirstViewHolder.text11.setText(String.valueOf(llm.findLastCompletelyVisibleItemPosition() + 1));
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return FIRST_TYPE_VIEW;
        } else {
            return SECOND_TYPE_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d("MultiAdapter", "onBindViewHolder");
        if (holder.getItemViewType() == FIRST_TYPE_VIEW) {
            HorizontalViewHolder HorizontalViewHolder = (HorizontalViewHolder) holder;

            InnerAdapter mAdapter = new InnerAdapter(mContext, mData, HorizontalViewHolder);
            HorizontalViewHolder.mRv.setAdapter(mAdapter);

        } else {
            VerticalViewHolder VerticalViewHolder = (VerticalViewHolder) holder;
            VerticalViewHolder.text1.setText("type_2  " + position);
            VerticalViewHolder.text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "  click type_1  " + "positon: " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<String> list) {
        if (CollectionUtils.nullOrEmpty(list)) return;
        if (innerView == null) return;
        if (CollectionUtils.nullOrEmpty(mData)) {
            mData = new ArrayList<>();
        }
        mData.clear();
        mData.addAll(list);
        innerView.getAdapter().notifyDataSetChanged();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {
        //        private TextView text1 ;
//        private TextView text2 ;
        public TextView text11;
        private RecyclerView mRv;
        private ViewPager mViewPager;

        public HorizontalViewHolder(View itemView) {
            super(itemView);
//            text1 =(TextView)itemView.findViewById(R.id.tv_multi_1_1);
//            text2 =(TextView)itemView.findViewById(R.id.tv_multi_1_2);
            text11 = (TextView) itemView.findViewById(R.id.tv_inner_count);
            text11.setText("1"); // 设置初始量
            mRv = (RecyclerView) itemView.findViewById(R.id.rv_multi_inner);
            mViewPager = (ViewPager) itemView.findViewById(R.id.rv_coupons);
        }
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder {
        private TextView text1;

        public VerticalViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.tv_multi_2_1);
        }
    }

}
