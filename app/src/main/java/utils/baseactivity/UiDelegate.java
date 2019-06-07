package utils.baseactivity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by J!nl!n on 2016/12/8.
 * Copyright © 1990-2016 J!nl!n™ Inc. All rights reserved.
*/

interface UiDelegate {

    void initInstanceState(@Nullable Bundle savedInstanceState);

    @LayoutRes
    int getLayoutId();

    void setListener();

    @UiThread
    void visible(@NonNull View view, int visibility);

    View inflate(@LayoutRes int layoutId, ViewGroup container);

}
