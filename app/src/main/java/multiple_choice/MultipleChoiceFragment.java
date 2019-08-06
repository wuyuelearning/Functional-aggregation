package multiple_choice;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyue on 2018/6/6.
 * <p>
 * RecyclerView 多选
 */

public class MultipleChoiceFragment extends Fragment {

    private Context mContext;
    private View mRootView;
    private RecyclerView mRecycleView;
    private List<MultipleChoiceBean> mData = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.recycleviewadapter_fragment_list, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mRecycleView = mRootView.findViewById(R.id.recycle_view);
        //  设置布局样式，不可少，缺少布局样式直接设置setAdapter是不行的
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(linearLayoutManager);
        setAdapter();
    }

    private void setAdapter() {
        initData();
        mRecycleView.setAdapter(new MultipleChioceAdapter(mContext,R.layout.multiple_choice_item,mData));
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            MultipleChoiceBean bean = new MultipleChoiceBean();
            bean.setCount("count: " + i);
            bean.setType("type: " + i);
            bean.setSelect(false);
            mData.add(bean);
        }
    }

}
