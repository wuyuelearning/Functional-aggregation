package utils.baseactivity;

import android.support.annotation.UiThread;

/**
 * Created by J!nl!n on 2016/10/13 18:22.
 * Copyright © 1990-2016 J!nl!n™ Inc. All rights reserved.
 */
interface IPresenter<V extends IView> {

    @UiThread
    void attachView(V view);

    void start();

    @UiThread
    void detachView();

}
