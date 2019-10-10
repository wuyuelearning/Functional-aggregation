package com.example.admin.projecttest;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;

import MutilRecy.MutilRVFragment;
import callbacktest.CallBackFragment;
import change_color.ChangeColorFragement;
import fragment.ImageSpanFragment;
import fragment.*;
import bezier.BezierFragment;
import divide_edit_text.DivideEditTextFragment;
import draw_board.DrawBoardFragment;
import expandable_text_view.ExpandableTextViewFragment;
import fragment_turn.FragmentTurnFragment;
import mask_layer.MaskLayerFragment;
import memorandum.MemorandumFragment;
import multiple_choice.MultipleChoiceFragment;
import mvp.MVPFragment;
import pullablelayout.PullableLayoutFragment;
import rec_linkage.RecyclerViewLinkageFragment;
import tow_tab.TwoTabFragment;
import we_chat_bottom_nav.fragment.WeChatNavStartFragment;

import android.support.v4.app.Fragment;

import static utils.COMMONVALUE.*;

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
            case FRAGMENT_TYPE_13:
                fragment = new MVPFragment();
                break;
            case FRAGMENT_TYPE_14:
                fragment = new BezierFragment();
                break;
            case FRAGMENT_TYPE_15:
                fragment = new DivideEditTextFragment();
                break;
            case FRAGMENT_TYPE_16:
                fragment = new ExpandableTextViewFragment();
                break;
            case FRAGMENT_TYPE_17:
                fragment = new DrawBoardFragment();
                break;
            case FRAGMENT_TYPE_18:
                fragment = new MemorandumFragment();
                break;
            case FRAGMENT_TYPE_19:
                fragment = new TwoTabFragment();
                break;
            case FRAGMENT_TYPE_20:
                fragment = new MutilRVFragment();
                break;
            case FRAGMENT_TYPE_21:
                fragment = new MaskLayerFragment();
                break;
            case FRAGMENT_TYPE_22:
                fragment = new CallBackFragment();
                break;
            case FRAGMENT_TYPE_23:
                fragment = new RecyclerViewLinkageFragment();
                break;
            case FRAGMENT_TYPE_24:
                fragment = new PullableLayoutFragment();
                break;
            case FRAGMENT_TYPE_25:
                fragment = new ChangeColorFragement();
                break;
            case FRAGMENT_TYPE_26:
                fragment = new FragmentTurnFragment();
                break;
            case FRAGMENT_TYPE_27:
                fragment = new WeChatNavStartFragment();
                break;
            default:
                break;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
