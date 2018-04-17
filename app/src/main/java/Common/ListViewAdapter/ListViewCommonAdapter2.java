package Common.ListViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wuyue on 2018/4/13.
 * Adapter一般需要保持一个List对象，存储一个Bean的集合，
 * 不同的ListView，Bean肯定是不同的，这个CommonAdapter肯定需要支持泛型，内部维持一个List<T>，就解决我们的问题了
 * 摘自博客
 * https://blog.csdn.net/lmj623565791/article/details/38902805/
 * 通用Adapter,可直接使用，不用再被继承
 */

public abstract class ListViewCommonAdapter2<T> extends BaseAdapter {
    //    private LayoutInflater mLayoutInflater;
    private List<T> mData;
    private Context mContext;
    private int mItemLayoutId;

    //为什么传入itemLayoutId，一部分原因是需要将itemLayoutId暴露给外部，还有就是在本地的getViewHolder方法中需要用到
    public ListViewCommonAdapter2(Context context, List<T> data, int itemLayoutId) {
        //  主要目的是为了获得Item，在通用的ViewHolder中用对其进行过滤 找到下一层布局
        //  已在ViewHolder类中实现，所以不需在这里进行初始化
        //  mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * 由于Apdaper 里面都需要覆写getView 而getView也是得到对应View的方法
     * 继承CommonAdapter的XXAdapter 也是要覆写个月View的
     * 第一行（ViewHolder viewHolder = getViewHolder(position, convertView,parent);）和
     * 最后一行：return viewHolder.getConvertView();最后一行：return viewHolder.getConvertView();
     * 把第一行和最后一行固定，把中间变化的部分抽取出来，OO的设计原则。
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder viewHolder =ViewHolder.getViewHolder(mContext,convertView,parent,mItemLayoutId,position);//  ---+++
        ViewHolder viewHolder = getViewHolder(position, convertView, parent);
        covert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    /**
     * 绑定数据
     * 将covert方法暴露给外部，并且把viewHolder 和本Item对应的Bean对象传出去
     * 在covert中需要通过ViewHolder把View找到，通过Item设置值
     *
     * @param viewHolder 对应Item布局，获得Item布局控件
     * @param itemInfo   Item控件数据集：Bean
     */
    public abstract void covert(ViewHolder viewHolder, T itemInfo);

    //  相当于在本地封装了一层  其实按---+++ 这样写法也是可以的
    // 由于需要使用Item对应的Layout，所以需要在构造方法中传入对应Layout
    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.getViewHolder(mContext, convertView, parent, mItemLayoutId, position);
    }
}
