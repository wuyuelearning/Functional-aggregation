package rec_linkage;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import java.util.List;

import MutilRecy.InnerAdapter;
import project_utils.CollectionUtils;
import project_utils.MobileUtil;

/**
 * Created by wuyue on 2019/3/25.
 * description:
 */

public class RecLinkageRightAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mLayoutInflater;
    private int mPositon;
    private RecyclerView innerView;
    private RecyclerView mRecLinkageTop;
    private static final int INNER_TYPR_1 = 1;

    public RecLinkageRightAdapter(Context c, List<String> list, RecyclerView recyclerView) {
        mContext = c;
        mData = list;
        mRecLinkageTop = recyclerView;
        mLayoutInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
//        if (viewType == INNER_TYPR_1) {
            viewHolder = new InnerViewHolder(mLayoutInflater.inflate(R.layout.item_rec_linkage_right, null, false));

            innerView = ((InnerViewHolder) viewHolder).mRv;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext) {
                @Override
                public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                    final int width = MobileUtil.getScreenWidth(mContext) - MobileUtil.dip2px(340);
                    return new RecyclerView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            };
            innerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.left = MobileUtil.dip2px(20);
                    outRect.right = MobileUtil.dip2px(20);
                }
            });
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            innerView.setLayoutManager(linearLayoutManager);
            setInnerViewScrollListener(innerView);
//        }
        return viewHolder;
    }

    public RecyclerView getInnerRecycleView() {
        if (innerView != null)
            return innerView;
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position)
            return INNER_TYPR_1;
        return -1;

    }

    private void setInnerViewScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    mRecLinkageTop.scrollBy(dx, dy);
                    innerView.scrollBy(dx,dy);
                    Log.d("scroll...", "mRecLinkageTop..." + dx + "   " + dy);
                }
            }
        });


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setPositon(position);
//        if (getItemViewType(position) == INNER_TYPR_1) {
            InnerViewHolder innerViewHolder = (InnerViewHolder) holder;
            if (innerViewHolder.mRv.getAdapter() == null) {
                RecLinkageRightInnerAdapter mAdapter = new RecLinkageRightInnerAdapter(mContext, mData);
                innerViewHolder.mRv.setAdapter(mAdapter);
            }
//        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getPositon() {
        return mPositon;
    }

    public void setPositon(int mPositon) {
        this.mPositon = mPositon;
    }

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRv;

        public InnerViewHolder(View itemView) {
            super(itemView);
            mRv = (RecyclerView) itemView.findViewById(R.id.rec_right_inner);
        }
    }

}


