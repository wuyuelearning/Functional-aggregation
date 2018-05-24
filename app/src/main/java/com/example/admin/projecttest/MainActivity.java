package com.example.admin.projecttest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Utils.ToastUtil;

import static Utils.COMMONVALUE.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {

        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);
        TextView text4 = (TextView) findViewById(R.id.text4);
        TextView text5 = (TextView) findViewById(R.id.text5);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.text1:
                bundle.putString("choice", FRAGMENT_TYPE_1);
//                ToastUtil.showToast(this,"找不到Activiity1",Toast.LENGTH_SHORT);
                break;
            case R.id.text2:
                bundle.putString("choice", FRAGMENT_TYPE_2);
//                ToastUtil.showToast(this,"找不到Activiity2",Toast.LENGTH_SHORT);
                break;
            case R.id.text3:
                bundle.putString("choice", FRAGMENT_TYPE_3);
//                ToastUtil.showToast(this,"找不到Activiity3",Toast.LENGTH_SHORT);
                break;
            case R.id.text4:
                bundle.putString("choice", FRAGMENT_TYPE_4);
//                ToastUtil.showToast(this,"找不到Activiity4",Toast.LENGTH_SHORT);
                break;
            case R.id.text5:
                bundle.putString("choice", FRAGMENT_TYPE_5);
//                ToastUtil.showToast(this,"找不到Activiity5",Toast.LENGTH_SHORT);
                break;
        }

        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("data", bundle);

        //  Activity 间通过隐式 Intent 的跳转，在发出 Intent 之前必须通过 resolveActivity
        // 检查，避免找不到合适的调用组件，造成 ActivityNotFoundException 的异常。

        //  此处 ContainerActivity.class 为显示指定的类 可以不用这样写

        //  如果需要通过url打开的activity则需要这样写

        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            startActivity(intent);
        } else {

            // 如果一个按钮可以触发两次Toast，都用 ToastUtil.showToast 则只弹出一次,弹出最后执行的toast
            // 如果 其中一次使用ToastUtil.showToast，另一次使用Toast.makeText 则会显示两次的内容
            //Toast.makeText(this, "找不到Activiity", Toast.LENGTH_SHORT).show(); //  方法一
            ToastUtil.showToast(this,"找不到Activiity",Toast.LENGTH_SHORT);  //  方法二

        }


    }

}
