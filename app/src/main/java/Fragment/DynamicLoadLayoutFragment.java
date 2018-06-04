package Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.ArrayList;

import Bean.InvincibleCouponVo;

/**
 * Created by wuyue on 2018/6/4.
 * 动态添加布局 实现listView 功能 动态加载Item
 */

public class DynamicLoadLayoutFragment extends Fragment implements View.OnClickListener {
    View mRootView;
    //    LoadingLayout mLoadingLayout;
    Context mContext;
    // 主布局
    LinearLayout availableCoupons;  //  加载Item 布局
    EditText editCouponCode;   // 编辑券码
    TextView useCouponCode;  //  "使用"按钮
    TextView submitCoupon; //  "确定" 按钮
    //  Item 布局
    TextView discountAmount;
    TextView couponType;
    TextView couponName;
    TextView couponExpireDate;
    ImageView useCoupon;
    LinearLayout couponItemClick;

    ArrayList<InvincibleCouponVo> couponVoList = new ArrayList<>();   // 无敌券列表   InvincibleCouponModel中 获取整个list
    ArrayList<String> couponCodesList = new ArrayList<>();  //  券码列表，大小与couponVoList一致 保存对应位置的券码
    ArrayList<Boolean> isSelectedList = new ArrayList<>();   //  被选Item的列表，大小与couponVoList一致 保存哪个位置的Item被选了


    private int listSize; //  存放 couponVoList大小 要记得初始化

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.dynamic_load_layout_fragment, container, false);
        initData();
        initLayoutView();
        loadAvailableCoupons();
        return mRootView;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            InvincibleCouponVo vo = new InvincibleCouponVo();
            vo.isSelected = false;
            vo.couponName = "name:" + i;
            vo.couponType = "type:" + i;
            vo.discountAmount = "count:" + i;
            couponVoList.add(vo);
        }
        listSize = couponVoList.size();
    }

    private void initLayoutView() {
        availableCoupons = mRootView.findViewById(R.id.ll_available_coupon_item_fill);
        editCouponCode = mRootView.findViewById(R.id.et_edit_coupon_code);
        useCouponCode = mRootView.findViewById(R.id.tv_use_coupon_code);
        submitCoupon = mRootView.findViewById(R.id.tv_submit_invincible_coupon);
    }

    // 动态加载布局
    private void loadAvailableCoupons() {
        for (int i = 0; i < listSize; i++) {
            View mItemView = initItemView(couponVoList.get(i));
            availableCoupons.addView(mItemView);
//            operateItem();  // 可能放这里
        }
    }

    //  初始Item
    private View initItemView(InvincibleCouponVo info) {

        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        View mItemView = mLayoutInflater.inflate(R.layout.dynamic_load_layout_item, null);
        discountAmount = mItemView.findViewById(R.id.tv_discount_amount);
        couponType = mItemView.findViewById(R.id.tv_coupon_type);
        couponName = mItemView.findViewById(R.id.tv_coupon_name);
        couponExpireDate = mItemView.findViewById(R.id.tv_coupon_expired_date);
        useCoupon = mItemView.findViewById(R.id.iv_use_coupon);
        couponItemClick = mItemView.findViewById(R.id.coupon_item_left_layout);
        couponItemClick.setOnClickListener(this);


        initItemInfo(info);
        operateItem();  //  这个方法是不是放这里

        return mItemView;
    }

    // 将InvincibleCouponVo 的数据填入 Item中
    private void initItemInfo(InvincibleCouponVo vo) {
        discountAmount.setText(vo.discountAmount);
        couponType.setText(vo.couponType);
        couponName.setText(vo.couponName);
    }

    private void operateItem() {
        useCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 处理记录券码  刷新被选券 颜色变换等
            }
        });
    }

    /**
     * 通过输入券码选择无敌券，如果券码对应的券在列表中：若未被选则选上，若已选则不操作；
     * 如果不在列表中，则添加
     */
    private void checkItemByCode(String couponCode) {

        boolean isExist = false;
        int itemNum = 0;

        for (int i = 0; i < listSize; i++) {
            if (couponCodesList.get(i).equalsIgnoreCase(couponCode)) {
                isExist = true;
                itemNum = i;
            }
        }

        if (isExist) {
            if (!isSelectedList.get(itemNum)) {// 如果code存在于列表中 且没有被选上
                selectItemByCode(couponCode);
            } else {

            }
        }
    }

    // 设置无敌券的被选状态 ，颜色变化
    private void selectItemByCode(String couponCode) {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.coupon_item_left_layout) {
            // 无敌券详细弹窗
        }

    }
}
