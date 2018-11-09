package tow_tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;


import common.ListViewAdapter.ViewHolder;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by wuyue on 2018/11/9.
 * describe:
 */

public class CouponsAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<MineCouponInfo.MineCouponBean> mData = new ArrayList<>();
    private String productType;
    private Context mContext;

    public CouponsAdapter(Context context ,String productType){
        super();
        mInflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.mContext =context;
        this.productType= productType;

    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public MineCouponInfo.MineCouponBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        MineCouponInfo.MineCouponBean  data =getItem(position) ;
        if(convertView == null){
            convertView =mInflater.inflate(R.layout.item_coupons,null);
            holder =new ViewHolder();
            holder.mTvCouponName = (TextView) convertView.findViewById(R.id.tv_coupon_name);
            holder.mTvCouponExpiredDate = (TextView) convertView.findViewById(R.id.tv_coupon_expired_date);
            holder.mTvCouponPrice = (TextView) convertView.findViewById(R.id.tv_coupon_price);
            holder.mTvCouponType = (TextView) convertView.findViewById(R.id.tv_coupon_type);
            holder.mTvCouponCode = (TextView) convertView.findViewById(R.id.tv_coupon_code);
            holder.mCouponTitleLayout = convertView.findViewById(R.id.ll_coupon_title);
            holder.mCouponStatusIv = (ImageView) convertView.findViewById(R.id.iv_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }



        return null;
    }

    static class ViewHolder {
        TextView mTvCouponName;
        TextView mTvCouponExpiredDate;
        TextView mTvCouponPrice;
        TextView mTvCouponType;
        TextView mTvCouponCode;
        View mCouponTitleLayout;
        ImageView mCouponStatusIv;
    }

    public void setData(List<MineCouponInfo.MineCouponBean> data) {
        mData = data;
    }
    public List<MineCouponInfo.MineCouponBean> getDataList(){
        return mData;
    }
}
