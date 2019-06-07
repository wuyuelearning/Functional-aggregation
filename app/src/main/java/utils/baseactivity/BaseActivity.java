package utils.baseactivity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by J!nl!n on 2016/12/8.
 * Copyright © 1990-2016 J!nl!n™ Inc. All rights reserved.
 */
public abstract class BaseActivity extends KCBaseActivity implements UiDelegate {

    private ActivityDelegate mActivityDelegate;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseDelegate().onCreate(savedInstanceState);
        this.initInstanceState(savedInstanceState);
        handleIntent();
        setContentView(this.getLayoutId());
        this.initView();
        this.setListener();
    }

    @Override
    public void initInstanceState(Bundle savedInstanceState) {

    }

    public void handleIntent() {

    }

    protected abstract void initView();

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        getBaseDelegate().onStart();
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        getBaseDelegate().onResume();
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();
        getBaseDelegate().onPause();
    }

    @CallSuper
    @Override
    protected void onStop() {
        super.onStop();
        getBaseDelegate().onDestroy();
    }

    @CallSuper
    @Override
    protected void onRestart() {
        super.onRestart();
        getBaseDelegate().onRestart();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        getBaseDelegate().onDestroy();
        super.onDestroy();
    }

    @CallSuper
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        getBaseDelegate().onSaveInstanceState(bundle);
    }

    @CallSuper
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getBaseDelegate().onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public final View inflate(int layoutId, ViewGroup container) {
        return LayoutInflater.from(this).inflate(layoutId, container);
    }

    @Override
    public final void visible(@NonNull View view, int visibility) {
        view.setVisibility(visibility);
    }

    protected final <V extends View> V $(@NonNull View view, int resId) {
        return (V) view.findViewById(resId);
    }

    protected final <V extends View> V $(int resId) {
        return (V) findViewById(resId);
    }

    @NonNull
    protected final ActivityDelegate getBaseDelegate() {
        if (mActivityDelegate == null) {
            mActivityDelegate = ActivityDelegate.create(this);
        }
        return mActivityDelegate;
    }
}
