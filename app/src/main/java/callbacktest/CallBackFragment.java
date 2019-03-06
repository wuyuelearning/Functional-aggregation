package callbacktest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.projecttest.R;

import java.util.Random;

import callbacktest.callback.IMyCallBack;
import callbacktest.callback.MyCallBackImpl;
import callbacktest.callback2.CallBack2;
import callbacktest.callback2.ICallBack2;

/**
 * Created by wuyue on 2019/1/23.
 * describe: 回调测试
 * 具体说明在云笔记中
 */

public class CallBackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_call_back, container, false);
        Button button = (Button) view.findViewById(R.id.btn_call_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCallBackImpl m = new MyCallBackImpl();
                IMyCallBack im = new IMyCallBack() {
                    @Override
                    public void onCallBack() {
                        Log.d("CallBack...", "setCallBack(obj,i) ");
                    }

                    @Override
                    public void onCallBack2() {
                        Log.d("CallBack...", "setCallBack(obj,i) 2-----");
                    }
                };
                int i = new Random().nextInt(10);
                Log.d("CallBack...", "Random : " + i);
                m.setCallBack(im, i);
            }
        });


        Button button2 = (Button) view.findViewById(R.id.btn_call_back2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            invoke();
            }
        });


        return view;
    }

    private void invoke() {
        CallBack2 c = new CallBack2();
        // 网络请求
        // requrstData();
        c.setCallBack(new ICallBack2() {
            @Override
            public void callback() {
                Log.d("CallBack...", "callback-2");
            }
        });
    }
}
