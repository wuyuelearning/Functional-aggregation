package fragment_turn;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import utils.MyBaseFragment;
import utils.bring.L;

/**
 * Created by wuyue on 2019/6/7.
 * description:
 */
public class FragmentTurnC extends MyBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_turn_c, container, false);
        return view;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("FragmentTurn ","FragmentTurnC onKeyDown");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
