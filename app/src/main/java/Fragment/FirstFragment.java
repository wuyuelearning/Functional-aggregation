package Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.projecttest.R;

/**
 * Created by admin on 2018/4/9.
 */

public class FirstFragment  extends Fragment{
    View mRootView;
    Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.first_fragment,container,false);
        return mRootView;
    }


}
