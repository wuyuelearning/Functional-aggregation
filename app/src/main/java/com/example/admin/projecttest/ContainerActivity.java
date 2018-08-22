package com.example.admin.projecttest;

import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;

import Fragment.ImageSpanFragment;
import Fragment.*;
import android.support.v4.app.Fragment;

import static Utils.COMMONVALUE.*;

/**
 * Created by wuyue on 2018/4/9.
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
            case FRAGMENT_TYPE_4:
                fragment = new NotificationFragment();
                break;
            case FRAGMENT_TYPE_5:
                fragment = new ImageSpanFragment();
                break;
            case FRAGMENT_TYPE_6:
                fragment = new DynamicLoadLayoutFragment();
                break;
            case FRAGMENT_TYPE_7:
                fragment = new MultipleChoiceFragment();
                break;
            case FRAGMENT_TYPE_8:
                fragment = new AnimationFragment();
                break;
            case FRAGMENT_TYPE_9:
                fragment = new QRCodeFragment();
                break;
            case FRAGMENT_TYPE_10:
                fragment = new ScreenShotFragment();
                break;
            case FRAGMENT_TYPE_11:
                fragment = new BitmapFragment();
                break;
            case FRAGMENT_TYPE_12:
                fragment = new BitmapWallFragment();
                break;
                default:
                    break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
