package Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Common.RecycleViewAdapter.ChatAdapter;
import Common.RecycleViewAdapter.ChatMessage;
import Common.RecycleViewAdapter.RecycleViewCommonAdapter;
import Common.RecycleViewAdapter.ViewHolder;

import static Utils.COMMONVALUE.*;

/**
 * Created by wuyue on 2018/4/13.
 * <p>
 * 主要使用RecycleView，对RecycleView的Adapter进行封装
 * 在RecycleView中加载单一类型的Item时，可采用RecycleViewCommonAdapter
 * 加载多类型的Item时，可使用MultiItemCommonAdapter，但需要再封装一层例如：ChatAdapter
 * <p>
 * 主要是学习博客
 * https://blog.csdn.net/lmj623565791/article/details/51118836
 * 中的写法
 */

public class RecycleViewAdapterFragment extends Fragment {

    private Context mContext;
    private View mRootView;
    private List<String> mData;
    private RecyclerView mRecycleView;
    private ArrayList<ChatMessage> mDatas = new ArrayList<>();

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

        selectAdapter(MULTI_ITEM);
    }

    private void selectAdapter(String select) {
        switch (select) {
            case SINGLE_ITEM: {
                mData = new ArrayList<>(Arrays.asList("123", "234", "345"));
                mRecycleView.setAdapter(new RecycleViewCommonAdapter<String>(mContext, R.layout.listviewadapter_list_item, mData) {
                    @Override
                    public void convert(ViewHolder holder, String item) {
                        // 通过viewholder根据控件的id拿到控件，然后再进行数据绑定和事件操作，
//                TextView textView = holder.getView(R.id.id_tv_title);
//                textView.setText(item);
                        holder.setText(R.id.id_tv_title, item);
                    }
                });
                break;
            }
            case MULTI_ITEM:
                initDatas();
                final ChatAdapter adapter = new ChatAdapter(mContext, mDatas);

                // 对adapter监听 ，对象是LinearLayoutManager 布局
                adapter.setOnItemClickListener(new ChatAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, ViewHolder holder, int position) {
                        Toast.makeText(mContext, "out: 1111111111", Toast.LENGTH_SHORT).show();
                        // 用这种方法监听Item中控件  第一次点击监听会被adapter.setOnItemClickListener拦截
                        // 而且之后的监听，在控件的点击事件是由setItemListener()拦截，超出控件的点击事件
                        // 由adapter.setOnItemClickListener拦截
                        //  监听写在Fragment里
                        //  setItemListener(holder);

                        // 监听最好能写在ChatAdapter里面这样就不会有监听拦截
                    }
                });
                mRecycleView.setAdapter(adapter);
        }
    }

    private void initDatas() {

        ChatMessage msg = null;

        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            if (random.nextBoolean()) {
                msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
                        null, true);
            } else {
                msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
                        null, false);
            }
            mDatas.add(msg);
        }

    }

    //  点击事件监听
    private void setItemListener(ViewHolder holder) {

        if (holder.getLayoutId() == R.layout.main_chat_from_msg) {
            holder.setOnClickListener(R.id.chat_from_content, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "chat_from_content: 1111111111", Toast.LENGTH_SHORT).show();
                }
            });
            holder.setOnClickListener(R.id.chat_from_icon, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "chat_from_icon: 1111111111", Toast.LENGTH_SHORT).show();
                }
            });
            holder.setOnClickListener(R.id.chat_from_name, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "chat_from_name: 1111111111", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder.getLayoutId() == R.layout.main_chat_send_msg) {
            holder.setOnClickListener(R.id.chat_send_icon, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "main_chat_send_msg: 1111111111", Toast.LENGTH_SHORT).show();
                }
            });
            holder.setOnClickListener(R.id.chat_send_name, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "chat_send_name: 1111111111", Toast.LENGTH_SHORT).show();
                }
            });
            holder.setOnClickListener(R.id.chat_send_content, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "chat_send_content: 1111111111", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
