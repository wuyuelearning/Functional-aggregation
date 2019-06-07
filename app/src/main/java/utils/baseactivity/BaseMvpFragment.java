package utils.baseactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements IView {

    protected P mPresenter;

    public void initParams(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParams();
        mPresenter = initPresenter();
        if (mPresenter != null) mPresenter.attachView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPresenter != null) mPresenter.start();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroy();
    }

    // 实例化presenter
    public abstract P initPresenter();

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
