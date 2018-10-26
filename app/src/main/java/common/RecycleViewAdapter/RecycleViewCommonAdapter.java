package common.RecycleViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wuyue on 2018/4/13.
 * <p>
 * 每次使用过程中，针对的数据类型Bean肯定是不同的，那么这里肯定要引入泛型代表
 * 的Bean，内部通过一个List代表数据
 */

public abstract class RecycleViewCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {


    private Context mContext;
    private List<T> mData;
    private LayoutInflater mLayoutInflater;
    private int mLayoutId;  //  布局Id
    private MultiItemTypeSupport<T> mMultiItemTypeSupport;
    public RecycleViewCommonAdapter(Context context, int layoutId, List<T> data) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        mLayoutId = layoutId;
    }


    //通过layoutId即可利用通用的ViewHolder生成实例
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(mContext, parent, mLayoutId);
        Log.d("ViewHolder", "onCreateViewHolder: " + viewHolder);
        return viewHolder;
    }

    /**
     * 在onCreateViewHolder得到viewHolder之后，在onBindViewHolder中进行数据、事件绑定
     * 可以直接抽象出去，让用户去操作。
     * 外部可以拿到当前Item所需要的对象和viewHolder去操作。
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("ViewHolder", "onBindViewHolder: " + holder);
        convert(holder, mData.get(position));
    }

    /**
     * 绑定数据
     *
     * @param holder   对应Item布局，获得Item布局控件
     * @param itemInfo Item控件数据集：Bean
     */

    public abstract void convert(ViewHolder holder, T itemInfo);

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
