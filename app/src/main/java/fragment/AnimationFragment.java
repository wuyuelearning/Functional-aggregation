package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.admin.projecttest.R;

import utils.GetScreenWidthHeight;

/**
 * Created by wuyue on 2018/6/28.
 */

public class AnimationFragment extends Fragment {

    private View mView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private Button button ;

    private long width;
    private long height;

    private int span;
    private int span2;

    boolean move = true;

    private GetScreenWidthHeight wh = new GetScreenWidthHeight();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animation_fragment, container, false);
        mView = view;

        button = (Button) mView.findViewById(R.id.btn_start_animation);
        imageView1 = (ImageView) mView.findViewById(R.id.imageView1);
        imageView2 = (ImageView) mView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) mView.findViewById(R.id.imageView3);


        getScreenWidthHeight1();
        getScreenWidthHeight2();
        getScreenWidthHeight3();
        getScreenWidthHeight4();

        setSpan();

        resetXY();

        setAnimation();
        return view;
    }

    private void setSpan() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaohei);
        int pic_width = bitmap.getWidth();
        int pic_height = bitmap.getHeight();

        span = (int) (width - 2 * 50 - 3 * pic_width) / 2;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.xiaohei, opts);
        opts.inSampleSize = 1;
        opts.inJustDecodeBounds = false;
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaohei, opts);
        int pic_width2 = opts.outWidth;
        int pic_height2 = opts.outHeight;

        span2 = (int) (width - 2 * 50 - 3 * pic_width) / 2;
    }

    private void resetXY() {
        imageView1.setTranslationX(50);
        imageView1.setTranslationY(height - 500);
        imageView2.setTranslationX(50 + span);
        imageView2.setTranslationY(height - 500);
        imageView3.setTranslationX(50 + 2 * span);
        imageView3.setTranslationY(height - 500);
    }


    private void setAnimation() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (move) {
                    startAnimation();
                    move = !move;
                } else {
                    resetXY();
                    move = !move;
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "imageView", Toast.LENGTH_SHORT).show();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "imageView2", Toast.LENGTH_SHORT).show();
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "imageView3", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startAnimation() {
//        imageView1.animate().translationX(MyUtils.dpToPixel(200));
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageView1.animate().translationY(height - 1000);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imageView2.animate().translationY(height - 1000);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                imageView3.animate().translationY(height - 1000);
            }
        }).start();
    }


    private void getScreenWidthHeight(Activity context, int i) {
        wh.selectFunc(context, i);
        width = wh.getScreenWidth();
        height = wh.getScreenHeight();
        Log.d("tag", "" + width);
        Log.d("tag", "" + height);
    }

    private void getScreenWidthHeight1() {
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        Log.d("h_bl", "屏幕宽度：" + width);
        Log.d("h_bl", "屏幕高度：" + height);
    }


    private void getScreenWidthHeight2() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        Log.d("h_bl", "++屏幕宽度：" + width);
        Log.d("h_bl", "++屏幕高度：" + height);
    }

    private void getScreenWidthHeight3() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;
        float density = dm.density;
        float densityDpi = dm.densityDpi;

        int screenWidth = (int) (width / density);
        int screenHeight = (int) (height / density);

        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
    }

    private void getScreenWidthHeight4() {
        Resources resources = getContext().getResources();
        DisplayMetrics dc = resources.getDisplayMetrics();

        width = dc.widthPixels;
        height = dc.heightPixels;

        float density = dc.density;
        float densityDpi = dc.densityDpi;


        int screenWidth = (int) (width / density);
        int screenHeight = (int) (height / density);

        Log.d("h_bl", "--屏幕宽度（像素）：" + dc.widthPixels);
        Log.d("h_bl", "--屏幕高度（像素）：" + dc.heightPixels);
        Log.d("h_bl", "--屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "--屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "--屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "--屏幕高度（dp）：" + screenHeight);

    }
}
