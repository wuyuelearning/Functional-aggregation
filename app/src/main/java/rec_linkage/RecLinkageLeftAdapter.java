package rec_linkage;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import java.util.List;


/**
 * Created by wuyue on 2019/3/25.
 * description:
 */

public class RecLinkageLeftAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mLayoutInflater;
    private int mPositon;

    public RecLinkageLeftAdapter(Context c, List<String> list) {
        mContext = c;
        mData = list;
        mLayoutInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecLinkageLeftAdapter.LeftViewHolder(mLayoutInflater.inflate(R.layout.item_rec_linkage_left, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setPositon(position);
        RecLinkageLeftAdapter.LeftViewHolder viewHolder = (RecLinkageLeftAdapter.LeftViewHolder) holder;
        viewHolder.text.setText("left  " + position);
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "  click  " + "positon: " + position + "  text ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecScrollListener(){

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

    public class LeftViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public LeftViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_rec_linkage_left);
        }
    }
}

