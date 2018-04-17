package Common.ListViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2018/4/12.
 * <p>
 * ListViewCommonAdapter依然是一个抽象类
 * 除了getView以外把其他的代码都实现了，这样的话，在使用Adapter只要实现一个getView，然后getView里面再使用
 * 打造的通用的ViewHolder
 * <p>
 * 但是，在getView中写有关具体控件例如：id_tv_title，布局例如;R.layout.adapter_item
 * 在个Adapter仍然是和具体控件布局绑定在一起，耦合太大
 * 每个ListView中布局Item，需要写不同的Adapter
 * 在ListViewCommonAdapter2中的改进使得在外部可以直接使用ListViewCommonAdapter2
 */

public class ListViewAdapter3<T> extends ListViewCommonAdapter<T> {
    private Context mContext;
    private List<T> mData;

    public ListViewAdapter3(Context context, List<T> data) {
        super( data);
        mContext = context;
        mData = data;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, R.layout.adapter_item, position);
        TextView textView = viewHolder.getView(R.id.id_tv_title);
        textView.setText((String) mData.get(position));

        return viewHolder.getConvertView();
    }
}
