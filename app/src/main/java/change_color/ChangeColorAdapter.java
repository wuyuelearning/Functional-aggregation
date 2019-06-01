package change_color;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;



public class ChangeColorAdapter  extends RecyclerView.Adapter {

    private Context mContext ;
    private List<String> mList ;
    private LayoutInflater mLayoutInflater;
    public ChangeColorAdapter(Context context , List<String > list){
        mContext =context;
        mList =list;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         return new ColorViewHolder(mLayoutInflater.inflate(R.layout.item_change_color, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ColorViewHolder viewHolder = (ColorViewHolder) holder;
        viewHolder.text.setText("aaaaaa:  "+position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

  public   class  ColorViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
       public ColorViewHolder(View itemView){
           super(itemView);
            text =(TextView) itemView.findViewById(R.id.tv_change_color);
       }
    }

}
