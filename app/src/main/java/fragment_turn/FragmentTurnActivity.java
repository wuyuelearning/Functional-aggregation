package fragment_turn;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2019/6/7.
 * description: Framgent 之间的跳转，查看回调的问题
 */
public class FragmentTurnActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_turn);
        FragmentTurnA fragmentTurnA = new FragmentTurnA();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, fragmentTurnA);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment ft = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (keyCode == KeyEvent.KEYCODE_BACK && getSupportFragmentManager().popBackStackImmediate()) {
            if (ft instanceof FragmentTurnA) {
                return ((FragmentTurnA) ft).onKeyDown(keyCode, event);
            } else if (ft instanceof FragmentTurnB) {
                return ((FragmentTurnB) ft).onKeyDown(keyCode, event);
            } else if (ft instanceof FragmentTurnC) {
                return ((FragmentTurnC) ft).onKeyDown(keyCode, event);
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}
