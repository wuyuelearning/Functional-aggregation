package pullablelayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2019/3/27.
 * description:
 */

public class PullableAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mLayoutInflater;
    private int mPositon;


    public PullableAdapter(Context c, List<String> list) {
        mContext = c;
        mData = list;
        mLayoutInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PullableAdapter.PullableViewHolder(mLayoutInflater.inflate(R.layout.item_pullable_rec, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setPositon(position);
        PullableAdapter.PullableViewHolder viewHolder = (PullableAdapter.PullableViewHolder) holder;
        viewHolder.text.setText(" " + position);
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "  click  " + "positon: " + position + "  text ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getPositon() {
        return mPositon;
    }

    public void setPositon(int mPositon) {
        this.mPositon = mPositon;
    }

    public class PullableViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public PullableViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_pullable_item);
        }
    }

    public void addData(List<String> data) {
        if (mData != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public void resetData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
