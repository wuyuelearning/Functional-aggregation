package com.example.admin.projecttest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import utils.ToastUtil;

/**
 * Created by wuyue on 2018/9/14.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private static final String TAG = "MainActivity_Tag";
    private List<String> mMenu;
    private Context mContext;

    //  从Adapter获得Context  ：Context mContext =v.getContext();
    //  不需要flag标识
    public MenuAdapter(List<String> mList){
        mMenu =mList;
    }
    //  从Activity获得Context 需要flag表示
    // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
    // 否则 会报错
    public MenuAdapter(List<String> mList,Context context){
        mMenu =mList;
        mContext=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);
        final ViewHolder viewHolder= new ViewHolder(view);
        viewHolder.menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context mContext =v.getContext();   //   从Adapter获得Context需加
                Log.d(TAG,"context    "+mContext.toString());
                int position =viewHolder.getAdapterPosition();
                String s =mMenu.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("choice",s);
                Intent intent = new Intent(mContext, ContainerActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );   //   从activity获得Context需加
                intent.putExtra("data", bundle);
                //  Activity 间通过隐式 Intent 的跳转，在发出 Intent 之前必须通过 resolveActivity
                // 检查，避免找不到合适的调用组件，造成 ActivityNotFoundException 的异常。
                //  此处 ContainerActivity.class 为显示指定的类 可以不用这样写
                //  如果需要通过url打开的activity则需要这样写
                if (mContext.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                    mContext.startActivity(intent);
                } else {
                    // 如果一个按钮可以触发两次Toast，都用 ToastUtil.showToast 则只弹出一次,弹出最后执行的toast
                    // 如果 其中一次使用ToastUtil.showToast，另一次使用Toast.makeText 则会显示两次的内容
                    //Toast.makeText(this, "找不到Activiity", Toast.LENGTH_SHORT).show(); //  方法一
                    ToastUtil.showToast(mContext, "找不到Activiity", Toast.LENGTH_SHORT);  //  方法二
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String s =mMenu.get(position);
        holder.tv_fragment_name.setText(s);
    }


    @Override
    public int getItemCount() {
        return mMenu.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_fragment_name;
        View menuView;
        public ViewHolder(View itemView) {
            super(itemView);
            menuView =itemView;
            tv_fragment_name =(TextView)itemView.findViewById(R.id.tv_menu_item);
        }
    }
}
