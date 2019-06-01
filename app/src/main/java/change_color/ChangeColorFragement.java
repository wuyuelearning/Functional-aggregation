package change_color;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

import utils.bring.MobileUtil;

public class ChangeColorFragement extends Fragment implements IChangeColorListener{
    private View mView;
    private ImageView mIvChangeColor;
    private ChangeColorLinearLayout mLLayout;

    private RecyclerView  mRvChangeColor;
    private ImageView mIvChangeColor2;
    private ChangeColorAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_change_color, container, false);
        mView = inflater.inflate(R.layout.fragment_change_color, container, false);
        initView();
//        initView2();
        return mView ;
    }

    private void initView(){
        mLLayout=(ChangeColorLinearLayout)mView.findViewById(R.id.ccll_change_color);
        mIvChangeColor =(ImageView)mView.findViewById(R.id.iv_change_color);

        mLLayout.setChangeColorListener(this);
    }

    private void initView2(){
        mRvChangeColor =(RecyclerView)mView.findViewById(R.id.rv_change_color);
        mIvChangeColor2 =(ImageView)mView.findViewById(R.id.iv_change_color2);

        mRvChangeColor.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.top = MobileUtil.dip2px(20);
            }
        });


        mAdapter =new ChangeColorAdapter(getContext(),getData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvChangeColor.setLayoutManager(linearLayoutManager);
        mRvChangeColor.setAdapter(mAdapter);

        mRvChangeColor.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int position = layoutManager.findFirstVisibleItemPosition();
//
//                if (position ==recyclerView.getAdapter().getItemCount()-10){
//                    mIvChangeColor2.setBackgroundResource(R.color.color_d20674);
//                }

            }
        });

    }

    private List<String>  getData(){
        List<String > strings =new ArrayList<>();
        for(int i=0;i<20;i++){
            strings.add("asdasd");
        }

        return strings;
    }

    @Override
    public void setColor(int color) {

    }
}
