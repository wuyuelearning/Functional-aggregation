package com.example.admin.projecttest;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;

import Fragment.PopupWindowFragment;
import Fragment.ListViewAdapterFragment;
import Fragment.RecycleViewAdapterFragment;

import android.support.v4.app.Fragment;

/**
 * Created by wuyue on 2018/4/9.
 */

public class ContainerActivity extends AppCompatActivity {

    private static final String FRAGMENT_TYPE_1= "PopupWindowFragment";
    private static final String FRAGMENT_TYPE_2= "ListViewAdapterFragment";
    private static final String FRAGMENT_TYPE_3= "RecycleViewAdapterFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        String choice = getData();
        initFragment(choice);
    }

    private String getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        return bundle.getString("choice");
    }

    private void initFragment(String choice) {
        Fragment fragment = null;
        switch (choice) {
            case FRAGMENT_TYPE_1:
                fragment = new PopupWindowFragment();
                break;
            case FRAGMENT_TYPE_2:
                fragment = new ListViewAdapterFragment();
                break;
            case FRAGMENT_TYPE_3:
                fragment = new RecycleViewAdapterFragment();
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
