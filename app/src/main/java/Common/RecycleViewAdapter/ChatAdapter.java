package Common.RecycleViewAdapter;

import android.content.Context;
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
                break;
            case R.layout.main_chat_send_msg:
                holder.setText(R.id.chat_send_content, itemInfo.getContent());
                holder.setText(R.id.chat_send_name, itemInfo.getName());
                holder.setImageResource(R.id.chat_send_icon, itemInfo.getIcon());
                break;
        }
    }
}
