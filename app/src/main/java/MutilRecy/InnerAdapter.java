package MutilRecy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import java.util.List;

/**
 * Created by wuyue on 2018/12/3.
 * describe:
 */

public class InnerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mLayoutInflater;
    private int mPositon;
    MultiAdapter.HorizontalViewHolder mFirstViewHolder;

    public InnerAdapter(Context c, List<String> list, MultiAdapter.HorizontalViewHolder holder) {
        mContext = c;
        mData = list;
        mFirstViewHolder = holder;
        mLayoutInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerViewHolder(mLayoutInflater.inflate(R.layout.item_multi_recy_1, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        setPositon(position);
        InnerViewHolder innerViewHolder = (InnerViewHolder) holder;
        innerViewHolder.text1.setText("type_1  " + position + "   1");
        innerViewHolder.text2.setText("type_1  " + position + "   2");
        innerViewHolder.getAdapterPosition();
        Log.d("XXXXX AdapterPosition-",""+innerViewHolder.getAdapterPosition());
        Log.d("XXXXX LayoutPosition-",""+innerViewHolder.getLayoutPosition());
        Log.d("XXXXX OldPosition-",""+innerViewHolder.getOldPosition());
        Log.d("XXXXX position-",""+position);

//        mFirstViewHolder.text11.setText(""+position);

        innerViewHolder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "  click type_2  " + "positon: " + position + "  text 1", Toast.LENGTH_SHORT).show();
            }
        });
        innerViewHolder.text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "  click type_2  " + "positon: " + position + "  text 2", Toast.LENGTH_SHORT).show();
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

    public class InnerViewHolder extends RecyclerView.ViewHolder {
        private TextView text1;
        private TextView text2;

        public InnerViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.tv_multi_1_1);
            text2 = (TextView) itemView.findViewById(R.id.tv_multi_1_2);
        }
    }
}
