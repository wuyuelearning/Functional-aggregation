package Common.ListViewAdapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wuyue on 2018/4/12.
 * <p>
 * 首先分析下ViewHolder的作用，通过convertView.setTag与convertView进行绑定，然后当convertView复用时，
 * 直接从与之对于的ViewHolder(getTag)中拿到convertView布局中的控件，省去了findViewById的时间~
 * <p>
 * 也就是说，实际上们每个convertView会绑定一个ViewHolder对象，这个viewHolder主要用于帮convertView存储布局中的控件。
 * <p>
 * 那么我们只要写出一个通用的ViewHolder，然后对于任意的convertView，提供一个对象让其setTag即可；
 * <p>
 * 既然是通用，那么我们这个ViewHolder就不可能含有各种控件的成员变量了，因为每个Item的布局是不同的，最好的方式是什么呢？
 * <p>
 * 提供一个容器，专门存每个Item布局中的所有控件，而且还要能够查找出来；既然需要查找，那么ListView肯定
 * 是不行了，需要一个键值对进行保存，键为控件的Id，值为控件的引用，相信大家立刻就能想到Map；但是我们
 * 不用Map，因为有更好的替代类，就是我们android提供的SparseArray这个类，和Map类似，但是比Map效
 * 率，不过键只能为Integer.
 * <p>
 * 使用了一个SparseArray<View>用于存储与之对于的convertView的所有的控件，当需要拿这些控件时，通过getView(id)进行获取
 * <p>
 * <p>
 * 摘自博客
 * https://blog.csdn.net/lmj623565791/article/details/38902805/
 */

public class ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private int mLayoutId;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mLayoutId =layoutId;
        // 放入mConvertView
        mConvertView.setTag(this);
    }


    //  得到ViewHolder
    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId, int positon) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, positon);
        }
        return (ViewHolder) convertView.getTag();
    }

    // 通过控件Id获得对应控件，如果没有则加入views
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            //  此时mConvertView 为对应的View（Item）布局
            view = mConvertView.findViewById(viewId);
            //  将viewId与view（或控件） 对应起来
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getLayoutId(){
        return mLayoutId;
    }



    // 可以进一步封装：将控件id和内容传入
    //  在covert的使用时,只要将原来的：
   // TextView textView = viewHolder.getView(R.id.xxxx);
     // textView.setText(Bean.yyyy);
    //改为
    // viewHolder.setText(R.id.id_tv_title,Bean.yyyy)

    //  返回值类型为ViewHolder
    public ViewHolder setText(int viewId,String text){
        TextView view =getView(viewId);
        view.setText(text);
        return this;
    }
}
