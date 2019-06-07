package utils;

import android.support.v4.app.Fragment;
import android.view.KeyEvent;

/**
 * Created by wuyue on 2019/6/7.
 * description:
 */
public class MyBaseFragment extends Fragment {

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
