package common.ListViewAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by admin on 2018/4/12.
 * <p>
 * <p>
 * Adapter一般需要保持一个List对象，存储一个Bean的集合，
 * 不同的ListView，Bean肯定是不同的，这个CommonAdapter肯定需要支持泛型，内部维持一个List<T>，就解决我们的问题了
 * <p>
 * <p>
 * <p>
 * <p>
 * 摘自博客
 * https://blog.csdn.net/lmj623565791/article/details/38902805/
 * <p>
 * 通用Adapter
 */

public abstract class ListViewCommonAdapter<T> extends BaseAdapter {
//    private LayoutInflater mLayoutInflater;
    private List<T> mData;
//    private Context mContext;

    public ListViewCommonAdapter(List<T> data) {
        //  主要目的是为了获得Item，在通用的ViewHolder中用对其进行过滤 找到下一层布局
        //  已在ViewHolder类中实现，所以不需在这里进行初始化
//        mLayoutInflater = LayoutInflater.from(context);
        //  mContext 的使用主要在其继承类XXAdapter中，CommonAdapter中主要是基本的，通用的Item的处理：getItem、getCount等
        // 所以这里不需要获取context
//        mContext = context;
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
