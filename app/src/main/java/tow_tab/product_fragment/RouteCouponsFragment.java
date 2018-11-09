package tow_tab.product_fragment;

import com.example.admin.projecttest.R;

import tow_tab.CouponsAdapter;
import tow_tab.PRODUCT_TYPE_ENUM;

/**
 * Created by wuyue on 2018/11/9.
 * describe:
 */

public class RouteCouponsFragment extends CouponsCommonFragment {
    @Override
    public int getViewId() {
        return R.layout.fragment_route_coupons;
    }

    @Override
    public CouponsRvAdapter getAdapter() {
        return new CouponsRvAdapter(getActivity(),getProductType(),getViewId());
    }

    @Override
    public String getProductType() {
        return PRODUCT_TYPE_ENUM.ROUTE.getType();
    }
}