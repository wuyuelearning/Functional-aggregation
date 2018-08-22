package Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import java.util.List;

import Utils.BitmapUtils;

/**
 * Created by wuyue on 2018/8/21.
 */

public class BitmapFragment extends Fragment implements View.OnClickListener {

    View mView;
    TextView tvShowBitmap;
    TextView tvShowNextBitmap;
    EditText etReqWidth;
    EditText etReqHeight;
    ImageView ivShowBitmap;
    int clickCount = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bitmap_fragment, container, false);
        initView();
        return mView;
    }

    private void initView() {

        tvShowBitmap = (TextView) mView.findViewById(R.id.tv_show_bitmap);
        tvShowBitmap.setOnClickListener(this);
        tvShowNextBitmap = (TextView) mView.findViewById(R.id.tv_show_next_bitmap);
        tvShowNextBitmap.setOnClickListener(this);
        ivShowBitmap = (ImageView) mView.findViewById(R.id.iv_show_bitmap);
        etReqWidth = (EditText) mView.findViewById(R.id.et_reqwidth);
        etReqHeight = (EditText) mView.findViewById(R.id.et_reqheight);


    }

    private void showBitmap() {
        int reqWidth = 200;
        int reqHeight = 200;
        int res = R.drawable.bitmap1;
        if (!TextUtils.isEmpty(etReqWidth.getText().toString().trim())) {
            reqWidth = Integer.parseInt(etReqWidth.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(etReqHeight.getText().toString().trim())) {
            reqHeight = Integer.parseInt(etReqHeight.getText().toString().trim());
        }
        switch (clickCount % 4) {
            case 0:
                res = R.drawable.bitmap1;
                break;
            case 1:
                res = R.drawable.bitmap2;
                break;
            case 2:
                res = R.drawable.bitmap3;
                break;
            case 3:
                res = R.drawable.bitmap4;
                break;
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),res);
        Log.d("BitmapFragment","bitmap"+(clickCount%4+1)+"  byte1---"+bitmap1.getByteCount());
        Log.d("BitmapFragment","bitmap"+(clickCount%4+1)+"  w1---: "+bitmap1.getWidth());
        Log.d("BitmapFragment","bitmap"+(clickCount%4+1)+"  h1---: "+bitmap1.getHeight());

        Bitmap bitmap =BitmapUtils.decodeBitMapFromResource(getResources(), res, reqWidth, reqHeight);
        ivShowBitmap.setImageBitmap(bitmap);

        Log.d("BitmapFragment","bitmap"+(clickCount%4+1)+"  byte+++"+bitmap.getByteCount());
        Log.d("BitmapFragment","bitmap"+(clickCount%4+1)+"  w+++"+bitmap.getWidth());
        Log.d("BitmapFragment","bitmap"+(clickCount%4+1)+"  h+++"+bitmap.getHeight());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_show_bitmap:
                showBitmap();
                break;
            case R.id.tv_show_next_bitmap:
                clickCount++;
                showBitmap();
                break;
        }
    }
}
