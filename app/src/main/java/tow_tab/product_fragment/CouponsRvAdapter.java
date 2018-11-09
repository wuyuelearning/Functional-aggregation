package tow_tab.product_fragment;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;

import common.RecycleViewAdapter.MultiItemCommonAdapter;
import tow_tab.MineCouponInfo;

/**
 * Created by wuyue on 2018/11/9.
 * describe:
 */

public class CouponsRvAdapter extends RecyclerView.Adapter<CouponsRvAdapter.ViewHolder> {

    private Context mContext;
    private List<MineCouponInfo.MineCouponBean> mData;
    private LayoutInflater mLayoutInflater;
    private String productType;
    private int mLayoutId;  //  布局Id
    private OnItemClickListener mLinstener;

    public CouponsRvAdapter(Context context, String productType, int layoutId) {
        this.mContext = context;
        this.productType = productType;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mLayoutId = layoutId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        ViewHolder holder = new ViewHolder(view,mLinstener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MineCouponInfo.MineCouponBean data = mData.get(position);
//        holder.mCouponStatusIv
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<MineCouponInfo.MineCouponBean> getmData() {
        return mData;
    }

    public void setmData(List<MineCouponInfo.MineCouponBean> mData) {
        this.mData = mData;
    }

    public void setOnItemClickListener(OnItemClickListener mLinstener) {
        this.mLinstener = mLinstener;
    }

    public interface OnItemClickListener{
         void onItemClick(View view, int position);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvCouponName;
        TextView mTvCouponExpiredDate;
        TextView mTvCouponPrice;
        TextView mTvCouponType;
        TextView mTvCouponCode;
        View mCouponTitleLayout;
        ImageView mCouponStatusIv;
        OnItemClickListener mListener;


        public ViewHolder(View itemView,OnItemClickListener mListener) {
            super(itemView);
            //  为对应布局ItemView添加点击事件
            itemView.setOnClickListener(this);
            mTvCouponName = (TextView) itemView.findViewById(R.id.tv_coupon_name);
            mTvCouponExpiredDate = (TextView) itemView.findViewById(R.id.tv_coupon_expired_date);
            mTvCouponPrice = (TextView) itemView.findViewById(R.id.tv_coupon_price);
            mTvCouponType = (TextView) itemView.findViewById(R.id.tv_coupon_type);
            mTvCouponCode = (TextView) itemView.findViewById(R.id.tv_coupon_code);
            mCouponTitleLayout = itemView.findViewById(R.id.ll_coupon_title);
            mCouponStatusIv = (ImageView) itemView.findViewById(R.id.iv_status);

            this.mListener= mListener;
        }


        @Override
        public void onClick(View v) {
            // getAdapterPosition()，获得当前RecycleView的位置，将此作为参数传出去
            mListener.onItemClick(v,getAdapterPosition());
        }
    }


}
