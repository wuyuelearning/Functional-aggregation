package com.example.admin.projecttest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

//import com.didichuxing.doraemonkit.DoraemonKit;

import static utils.COMMONVALUE.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity_Tag";

    private List<String> mMenu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DoraemonKit.install(getApplication());
        initData();
        initView();

        try {
            PackageManager pm = this.getPackageManager();
            @SuppressLint("WrongConstant")
            ApplicationInfo ai = pm.getApplicationInfo("com.example.admin.projecttest", PackageManager.GET_ACTIVITIES);
            Toast.makeText(this, Integer.toString(ai.uid, 10), Toast.LENGTH_SHORT).show();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void initView() {
        Log.d(TAG, "getApplicationContext " + getApplicationContext().toString());
        Log.d(TAG, "getApplication " + getApplication().toString());
        Log.d(TAG, "getBaseContext " + getBaseContext().toString());
        RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.rv_menu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecycleView.setLayoutManager(gridLayoutManager);
        MenuAdapter menuAdapter = new MenuAdapter(mMenu);
//        MenuAdapter menuAdapter = new MenuAdapter(mMenu,getApplicationContext());

        mRecycleView.setAdapter(menuAdapter);

    }

    private void initData() {
        mMenu.add(FRAGMENT_TYPE_1);
        mMenu.add(FRAGMENT_TYPE_2);
        mMenu.add(FRAGMENT_TYPE_3);
        mMenu.add(FRAGMENT_TYPE_4);
        mMenu.add(FRAGMENT_TYPE_5);
        mMenu.add(FRAGMENT_TYPE_6);
        mMenu.add(FRAGMENT_TYPE_7);
        mMenu.add(FRAGMENT_TYPE_8);
        mMenu.add(FRAGMENT_TYPE_9);
        mMenu.add(FRAGMENT_TYPE_10);
        mMenu.add(FRAGMENT_TYPE_11);
        mMenu.add(FRAGMENT_TYPE_12);
        mMenu.add(FRAGMENT_TYPE_13);
        mMenu.add(FRAGMENT_TYPE_14);
        mMenu.add(FRAGMENT_TYPE_15);
        mMenu.add(FRAGMENT_TYPE_16);
        mMenu.add(FRAGMENT_TYPE_17);
        mMenu.add(FRAGMENT_TYPE_18);
        mMenu.add(FRAGMENT_TYPE_19);
        mMenu.add(FRAGMENT_TYPE_20);
        mMenu.add(FRAGMENT_TYPE_21);
        mMenu.add(FRAGMENT_TYPE_22);
        mMenu.add(FRAGMENT_TYPE_23);
        mMenu.add(FRAGMENT_TYPE_24);
        mMenu.add(FRAGMENT_TYPE_25);
        mMenu.add(FRAGMENT_TYPE_26);
        mMenu.add(FRAGMENT_TYPE_27);
        mMenu.add(FRAGMENT_TYPE_28);
        mMenu.add(FRAGMENT_TYPE_29);
    }
}
