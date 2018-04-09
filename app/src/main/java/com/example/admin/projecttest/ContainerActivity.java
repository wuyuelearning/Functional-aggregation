package com.example.admin.projecttest;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;

import Fragment.FirstFragment;

/**
 * Created by admin on 2018/4/9.
 */

public class ContainerActivity extends AppCompatActivity {


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
        FirstFragment fragment = null;
        switch (choice) {
            case "first":
                fragment = new FirstFragment();
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }
}
