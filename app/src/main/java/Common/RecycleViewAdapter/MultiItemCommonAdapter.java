package Common.RecycleViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wuyue on 2018/4/16.
 * <p>
 * 1、复写getItemViewType，根据我们的bean去返回不同的类型
 * 2、onCreateViewHolder中根据itemView去生成不同的ViewHolder
 * <p>
 * ViewHolder是通用的，唯一依赖的就是个layoutId
 * 上述第二条就变成，根据不同的itemView告诉我用哪个layoutId即可，
 * 生成viewholder这种事我们通用adapter来做
 * 引入一个接口 MultiItemTypeSupport
 * <p>
 * MultiItemTypeSupport接口实际就是完成上述的1,2,工作
 * 用户在使用过程中，通过实现上面两个方法，
 * 指明不同的Bean返回什么itemViewType,
 * 不同的itemView所对应的layoutId
 * 生成viewholder通用adapter来做
 */


public abstract class MultiItemCommonAdapter<T> extends RecycleViewCommonAdapter<T> {
    private MultiItemTypeSupport<T> mMultiItemTypeSupport;
    private List<T> mData;
    private Context mContext;
    private int position;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MultiItemCommonAdapter(Context context, List<T> data,
                                  MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, -1, data);
        mContext = context;
        mData = data;
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    /**
     * 用户的传入的MultiItemTypeSupport.getItemViewType完成
     */
    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return mMultiItemTypeSupport.getItemViewType(position, mData.get(position));
    }

    /**
     * 生成ViewHolder
     * 根据MultiItemTypeSupport.getLayoutId返回layoutId
     * 根据layoutId生成不同的ViewHolder，MultiType 选择不同的布局Layout文件在于此步
     * <p>
     * 在基类RecycleViewCommonAdapter的onBindViewHolder中绑定数据，因为数据在covert已经暴露给外部
     * 所以实际是在外部进行控件初始化，例如： TextView textView = holder.getView(R.id.id_tv_title); ，
     * 不需要具体的写在onBindViewHolder中，所以onBindViewHolder不需要在子类MultiItemCommonAdapter中覆写
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
//        int layoutId =mMultiItemTypeSupport.getLayoutId(viewType,mData.get(position));   //  第二种写法
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, parent, layoutId);
        setClickListener(parent,viewHolder,viewType);
        setLongClickListener(parent,viewHolder,viewType);
        return viewHolder;
    }


    @Override
    public int getItemCount() {

        if (mMultiItemTypeSupport != null) {
            //return mMultiItemTypeSupport.getViewTypeCount();
        }
        return super.getItemCount();
    }

    /**
     * 添加点击事件
     *
     */
    public interface  OnItemClickListener {
        void onItemClick(View view , ViewHolder holder, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,ViewHolder holder ,int position);
    }

    public void setOnItemLongClickListener (OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private void setClickListener(ViewGroup parent , final ViewHolder holder , int viewType){
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    int position =holder.getAdapterPosition();
                    onItemClickListener.onItemClick(v,holder,position);
                }
            }
        });
    }

    private void setLongClickListener (ViewGroup parent, final ViewHolder holder , int viewType){
        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemLongClickListener !=null){
                    int position = holder.getAdapterPosition();
                    onItemLongClickListener.onItemLongClick(v,holder,position);
                }
                return false;
            }
        });
    }


}
