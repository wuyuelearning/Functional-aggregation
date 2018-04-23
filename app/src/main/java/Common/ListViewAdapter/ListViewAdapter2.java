package Common.ListViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2018/4/12.
 * <p>
 * 实现与ListViewAdapter基本相同
 * 不同点在于使用了通用的ViewHolder
 * <p>
 * 但是不同的ListView需要不同的Adapter
 * 没有实现Adapter的复用
 */

public class ListViewAdapter2 extends BaseAdapter {

    //    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mData;

    public ListViewAdapter2(Context context, List<String> data) {
        //  主要目的是为了获得Item，在通用的ViewHolder中用对其进行过滤 找到下一层布局
        //  已在ViewHolder类中实现，所以不需在这里进行初始化
//        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 首先调用ViewHolder的get方法，如果convertView为null，new一个ViewHolder实
     * 例，通过使用mInflater.inflate加载布局，然后new一个SparseArray用于存储View，最后setTag(this)；
     * <p>
     * 如果存在那么直接getTag
     * <p>
     * 最后通过getView(id)获取控件，如果存在则直接返回，否则调用findViewById，返回存储，返回。
     *
     *
     * 使用 Adapter 的时候，如果你使用了 ViewHolder 做缓存，在 getView()的
     * 方法中无论这项 convertView 的每个子控件是否需要设置属性(比如某个 TextView
     * 设置的文本可能为 null，某个按钮的背景色为透明，某控件的颜色为透明等)，都需
     * 要为其显式设置属性(Textview 的文本为空也需要设置 setText("")，背景透明也需要
     * 设置)，否则在滑动的过程中，因为 adapter item 复用的原因，会出现内容的显示错乱。
     *
     *
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //  viewHolder 获取Item对应的控件
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, convertView, parent, R.layout.listviewadapter_list_item, position);
        //  通过ViewHolder得到控件，还是从Viewholder拿控件
        TextView textView = viewHolder.getView(R.id.id_tv_title);
        textView.setText(mData.get(position));
        return viewHolder.getConvertView();
    }
}
