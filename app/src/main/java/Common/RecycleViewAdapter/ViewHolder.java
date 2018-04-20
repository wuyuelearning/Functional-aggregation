package Common.RecycleViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by admin on 2018/4/13.
 * <p>
 * RecyclerView要求必须使用ViewHolder模式
 * 在使用过程中，都需要去建立一个新的ViewHolder然后作为泛型传入Adapter。
 * 那么想要建立通用的Adapter，必须有个通用的ViewHolder。
 * <p>
 * ViewHolder的主要的作用，实际上是通过成员变量存储对应的convertView中需要操作的字View，
 * 避免每次findViewById，从而提升运行的效率。
 * <p>
 * 通用的View，那么对于不同的ItemType肯定没有办法确定创建哪些成员变量View，
 * 取而代之的只能是个集合来存储了。
 * <p>
 * ViewHolder继承自RecyclerView.ViewHolder
 * 内部通过SparseArray来缓存我们itemView内部的子View，
 * 从而得到一个通用的ViewHolder。每次需要创建ViewHolder只需要传入
 * layoutId即可
 * <p>
 * 通过viewholder根据控件的id拿到控件，然后再进行数据绑定和事件操作，
 * <p>
 * 摘自博客
 * https://blog.csdn.net/lmj623565791/article/details/51118836
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    private Context mContext;
    private SparseArray<View> mViews;
    private View mConvertView;

    private int mPosition;
    // 十分关键的一步，需要将LayoutId传出去，特别是有多种LayoutId的时候
    private int mLayoutId;


    public ViewHolder(Context context, View itemView, ViewGroup parent, int layoutId) {
        super(itemView);
        mContext = context;
         //  非常关键，一开始没有取到layoutId，在CharAdapter中getLayoutId无法得到具体LayoutId
        // 无法分辨出那种LayoutId 加载不出正确的布局，也无法进行 holder.settext 设置点击监听等操作
        mLayoutId=layoutId;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }


    public static ViewHolder getViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView, parent,layoutId);
        return holder;
    }


    // 通过viewId获取控件

    public <T extends View> T getView(int layoutId) {
        View view = mViews.get(layoutId);
        if (view == null) {
            view = mConvertView.findViewById(layoutId);
            mViews.put(layoutId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    /**
     * 一些控件操作
     */

    public ViewHolder setText(int viewId, String text) {

        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView in = getView(viewId);
        in.setImageResource(resId);
        return this;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }




}
