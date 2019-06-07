package utils.baseactivity;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by J!nl!n on 2016/9/14 13:38.
 * Copyright © 1990-2016 J!nl!n™ Inc. All rights reserved.
 */
public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter<V> {

    private Reference<V> mViewRef;
    private M mModel;

    public BasePresenter(M model) {
        mModel = model;
    }

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public V getView() {
        if (!isViewAttached()) {
            throw new IllegalStateException("V can not be null");
        }
        return mViewRef.get();
    }

    public M getModel() {
        return mModel;
    }

}
