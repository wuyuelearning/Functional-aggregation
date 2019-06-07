package utils.baseactivity;

import android.content.Context;
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
public abstract class BaseFragment extends KCBaseFragment implements UiDelegate {

    private View mRootView;
    protected Context mContext;

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(this.getLayoutId(), null);
        }
        // 缓存的rootView需要判断是否已经被加过parent， 如果有parent则从parent删除，防止发生这个rootview已经有parent的错误。
        ViewGroup mViewGroup = (ViewGroup) mRootView.getParent();
        if (mViewGroup != null) {
            mViewGroup.removeView(mRootView);
        }
        return mRootView;
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initInstanceState(savedInstanceState);
        this.initView(view);
        setListener();
    }

    @Override
    public void initInstanceState(Bundle savedInstanceState) {

    }

    protected abstract void initView(@NonNull View view);

    @Override
    public final View inflate(int layoutId, ViewGroup container) {
        return LayoutInflater.from(mContext).inflate(layoutId, container);
    }

    @Override
    public final void visible(@NonNull View view, int visibility) {
        view.setVisibility(visibility);
    }

    protected final <V extends View> V $(@NonNull View view, int resId) {
        return (V) view.findViewById(resId);
    }

}
