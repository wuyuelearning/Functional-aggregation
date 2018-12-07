package MutilRecy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsic;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projecttest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyue on 2018/12/3.
 * describe: 多布局，第一行是横向滑动，第二行开始竖着滑动
 */

public class MutilRVFragment extends Fragment {

    private View mView;
    private Context mContent;
    private List<String> mData = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_mulit_recy, container, false);
        mContent = getContext();
        initView();
        return mView;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView() {

        initData();
        RecyclerView mRv = (RecyclerView) mView.findViewById(R.id.rv_multi);
        MultiAdapter mAdapter = new MultiAdapter(mContent, mData);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(linearLayoutManager);
        mRv.setAdapter(mAdapter);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coupon_check);
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript renderScript =RenderScript.create(mContent);
        ScriptIntrinsicBlur s = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));

        Allocation tmpIn = Allocation.createFromBitmap(renderScript,inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript,outputBitmap);

        s.setRadius(15);
        s.setInput(tmpIn);
        s.forEach(tmpOut);


    }

    private void initData() {
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
        mData.add("1");
    }
}
