package Common.RecycleViewAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.admin.projecttest.R;

import java.util.List;



/**
 * Created by wuyue on 2018/4/16.
 * <p>
 * 在使用多种Item的Adapter的时候还是封装了一层
 * 在这一层中仅仅查找Layout，选择Item布局，并对Item中控件操作
 */

public class ChatAdapter extends MultiItemCommonAdapter<ChatMessage> {

    public ChatAdapter(Context context, List<ChatMessage> datas) {

        super(context, datas, new MultiItemTypeSupport<ChatMessage>() {
            @Override
            public int getLayoutId(int itemType) {
                if (itemType == ChatMessage.RECIEVE_MSG) {
                    return R.layout.main_chat_from_msg;
                }
                return R.layout.main_chat_send_msg;
            }

//            //  第二种写法
////            @Override
////            public int getLayoutId(int itemType, ChatMessage item) {
////                if (item.isComMeg()){
////                    return R.layout.main_chat_from_msg;
////                }
////                return R.layout.main_chat_send_msg;
////            }

            @Override
            public int getItemViewType(int position, ChatMessage item) {
                if (item.isComMeg()) {
                    return ChatMessage.RECIEVE_MSG;
                }
                return ChatMessage.SEND_MSG;
            }
        });
    }

    /**
     * 绑定数据
     *
     * @param holder   对应Item布局，获得Item布局控件
     * @param itemInfo Item控件数据集：Bean
     */

    @Override
    public void convert(ViewHolder holder, ChatMessage itemInfo) {
        switch (holder.getLayoutId()) {
            case R.layout.main_chat_from_msg:
                holder.setText(R.id.chat_from_content, itemInfo.getContent());
                holder.setText(R.id.chat_from_name, itemInfo.getName());
                holder.setImageResource(R.id.chat_from_icon, itemInfo.getIcon());
                setListener1(holder);
                break;
            case R.layout.main_chat_send_msg:
                holder.setText(R.id.chat_send_content, itemInfo.getContent());
                holder.setText(R.id.chat_send_name, itemInfo.getName());
                holder.setImageResource(R.id.chat_send_icon, itemInfo.getIcon());
                setListener2(holder);
                break;
        }
    }
    /**
     * 不能将在没有筛选layoutId的情况下将所有的holder监听事件放在一个setListener1或setListener2中
     * 因为当将某一个layoutId的holder传过来时(例如：main_chat_send_msg),此时可以对main_chat_send_msg中
     * 控件设置点击监听，但是对其他layoutId中控件(例如：chat_send_name)，无法获得，不能进行监听，会有空指针错误
     */

    private void setListener1(ViewHolder holder) {
        holder.setOnClickListener(R.id.layout_msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("from", "chat_send_name");
            }
        });
        holder.setOnClickListener(R.id.chat_from_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("from", "chat_send_name");
            }
        });
        holder.setOnClickListener(R.id.chat_from_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("from", "chat_send_name");
            }
        });
    }

    /**
     * 第二种监听写法
     * holder.getView 得到控件id
     */
    private void setListener2(ViewHolder holder) {
        holder.getView(R.id.chat_send_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("from", "chat_send_content");
            }
        });
        holder.getView(R.id.chat_send_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("from", "chat_send_name");
            }
        });
        holder.getView(R.id.chat_send_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("from", "chat_send_icon");
            }
        });
    }


    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position) {
                if (view.getId() == R.id.chat_from_icon) {
                    Log.d("from", "chat_from_icon");
                } else if (view.getId() == R.id.chat_from_name) {
                    Log.d("from", "chat_from_name");
                } else if (view.getId() == R.id.chat_from_content) {
                    Log.d("from", "chat_from_content");
                }

                if (view.getId() == R.id.chat_send_content) {
                    Log.d("from", "chat_send_content");
                } else if (view.getId() == R.id.chat_send_name) {
                    Log.d("from", "chat_send_name");
                } else if (view.getId() == R.id.chat_send_icon) {
                    Log.d("from", "chat_send_icon");
                }

            }
        });
    }

}
