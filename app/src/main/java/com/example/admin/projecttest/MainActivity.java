package com.example.admin.projecttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import static Utils.COMMONVALUE.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private List<String> mMenu =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initView(){
        RecyclerView mRecycleView =(RecyclerView)findViewById(R.id.rv_menu);
        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,3);
        mRecycleView.setLayoutManager(gridLayoutManager);
        MenuAdapter menuAdapter = new MenuAdapter(mMenu);
        mRecycleView.setAdapter(menuAdapter);
    }
    private void initData(){
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
    }
}
