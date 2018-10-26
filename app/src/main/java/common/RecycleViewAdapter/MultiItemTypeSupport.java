package common.RecycleViewAdapter;

/**
 * Created by wuyue on 2018/4/16.
 * <p>
 * 1、复写getItemViewType，根据我们的bean去返回不同的类型
 * 2、onCreateViewHolder中根据itemView去生成不同的ViewHolder
 * <p>
 * 引入一个接口 MultiItemTypeSuppor
 * <p>
 * 这个接口实际就是完成上述的两1,2,工作
 * 用户在使用过程中，通过实现上面两个方法，
 * 指明不同的Bean返回什么itemViewType,
 * 不同的itemView所对应的layoutId
 * 生成viewholder通用adapter来做
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
//    int getLayoutId(int itemType,T itemInfo);  //  第二种写法

    int getItemViewType(int position, T itemInfo);

//    int getViewTypeCount();
}
