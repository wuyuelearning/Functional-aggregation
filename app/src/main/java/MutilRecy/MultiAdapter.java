package MutilRecy;

import android.content.Context;
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

import static MutilRecy.Constract.*;

/**
 * Created by wuyue on 2018/12/3.
 * describe:
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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
                    @Override
                    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    }
                };
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mFirstViewHolder = ((HorizontalViewHolder) viewHolder);
                innerView = ((HorizontalViewHolder) viewHolder).mRv;
                innerView.setLayoutManager(linearLayoutManager);
                new PagerSnapHelper().attachToRecyclerView(innerView);
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
                        mFirstViewHolder.text11.setText("" + llm.findFirstVisibleItemPosition());
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
//           HorizontalViewHolder .text1.setText("1111");
//           HorizontalViewHolder .text2.setText("111_2222");
//
//           HorizontalViewHolder .text1.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   Toast.makeText(mContext,position+"  click 1_1",Toast.LENGTH_SHORT).show();
//               }
//           });
//           HorizontalViewHolder .text2.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View v) {
//                   Toast.makeText(mContext,position+"  click 1_2",Toast.LENGTH_SHORT).show();
//               }
//           });


            if (HorizontalViewHolder.mRv.getAdapter() == null) {
                InnerAdapter mAdapter = new InnerAdapter(mContext, mData, HorizontalViewHolder);
                HorizontalViewHolder.mRv.setAdapter(mAdapter);
            }

//           int  innerPostion=0;
//           if(0 == mAdapter.getPositon()){
//               innerPostion +=1;
//           }
//           HorizontalViewHolder.text11.setText(""+innerPostion);


//           HorizontalViewHolder.mViewPager.setAdapter();

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
            text11.setText("0");
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
