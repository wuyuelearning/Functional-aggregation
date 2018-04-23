package Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Common.ListViewAdapter.ListViewCommonAdapter2;
import Common.ListViewAdapter.ListViewAdapter;
import Common.ListViewAdapter.ListViewAdapter2;
import Common.ListViewAdapter.ListViewAdapter3;
import Common.ListViewAdapter.ViewHolder;

import static Utils.COMMONVALUE.*;

/**
 * Created by wuyue on 2018/4/12.
 * <p>
 * 这一部分的Fragment主要实现ListView 练习Adapter 将博客
 * https://blog.csdn.net/lmj623565791/article/details/38902805/
 * 中的内容实现一下
 */

public class ListViewAdapterFragment extends Fragment {
    private Context mContext;
    private View mRootView;
    private ListView mListView;
    private BaseAdapter mListViewAdapter;
    private List<String> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.listviewadapter_fragment_list, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        mListView = mRootView.findViewById(R.id.id_lv_main);
        //  此处的数据是String类型，实际的可以用Bean类，对应不同的控价 ，可以用Bean.xxx取出数据
        mData = new ArrayList<>(Arrays.asList("hello", "world", "welcome"));
        setAdapter(SELECT_ADAPTER_1);
        mListView.setAdapter(mListViewAdapter);

    }

    private void setAdapter(int selectAdapter) {
        switch (selectAdapter) {
            case SELECT_ADAPTER_1: {
                mListViewAdapter = new ListViewAdapter(mContext, mData);
                break;
            }
            case SELECT_ADAPTER_2: {
                mListViewAdapter = new ListViewAdapter2(mContext, mData);
                break;
            }
            case SELECT_ADAPTER_3: {
                mListViewAdapter = new ListViewAdapter3<>(mContext, mData);
                break;
            }
            case SELECT_ADAPTER_4: {
                mListViewAdapter = new ListViewCommonAdapter2<String>(mContext, mData, R.layout.listviewadapter_list_item) {
                    @Override
                    public void covert(ViewHolder viewHolder, String item) {
                        TextView textView = viewHolder.getView(R.id.id_tv_title);
                        textView.setText(item);
//                        viewHolder.setText(R.id.id_tv_title,item);
                    }
                };
                break;
            }
        }
    }
}
