package utils.baseactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;


/**
 * Created by J!nl!n on 2016/9/14 13:29.
 * Copyright © 1990-2016 J!nl!n™ Inc. All rights reserved.
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements IView {

    protected P mPresenter;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            mPresenter.start();
        }
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroy();
    }

    // 实例化presenter
    @Nullable
    public abstract P initPresenter();

    public P getPresenter() {
        return mPresenter;
    }

    @CallSuper
    @Override
    public Context getContext() {
        return this;
    }
}
