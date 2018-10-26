package common.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2018/4/12.
 * <p>
 * 基本的ListView的Adapter方法
 * 通过ViewHolder进行Item的复用
 * <p>
 * 对于不同布局的ListView，我们会有一个对应的Adapter，在Adapter中又会有一个ViewHolder类来提高效率。
 * 摘自博客 https://blog.csdn.net/lmj623565791/article/details/38902805/
 */


public class ListViewAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;  // 用于加载Item
    private Context mContext;
    private List<String> mData;

    public ListViewAdapter(Context context, List<String> data) {
        mLayoutInflater = LayoutInflater.from(context);   //  为了使 ***--- 代码有效 ，在***---上进行（之前）赋值都可以
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
     * 获得Item布局
     * 使用本地ViewHolder
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            //convertView 获得item布局
            convertView = mLayoutInflater.inflate(R.layout.listviewadapter_list_item, parent, false);  // ***---
            viewHolder = new ViewHolder();
            //  viewholder 获得item中控件的引用
            viewHolder.text = convertView.findViewById(R.id.id_tv_title);
            //  如果convertView为空时 将ViewHolder即控件引用放入convertView
            //  尽放入一次，如果已经存在只需要用getTag获得控件引用集合viewholder
            // item的实现复用
            convertView.setTag(viewHolder);
        } else {
            //获得控件引用集合viewholder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 操作控件
        viewHolder.text.setText(mData.get(position));
        return convertView;
    }


//    /**
//     * 得到Item布局
//     * 与上面的方法一直，主要为了说明ViewHolder的作用，本方法中 Item如果仅有一个控件
//     * 或仅操作一个控件时，直接用控件TextView进行setTag，getTag也是可以的
//     * 但是运行时会在 1 处报错空指针
//     *
//     * @param position
//     * @param convertView
//     * @param parent
//     * @return
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView text = null;
//        if (convertView == null) {
//            text = convertView.findViewById(R.id.id_tv_title);  //  1
//            convertView.setTag(text);
//        } else {
//            text = (TextView) convertView.getTag();
//        }
//        text.setText(mData.get(position));
//        return convertView;
//    }

    //可以看做控件引用的集合
    private class ViewHolder {
        TextView text;
    }
}
