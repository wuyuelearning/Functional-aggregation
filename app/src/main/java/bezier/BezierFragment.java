package bezier;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.projecttest.MainActivity;
import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/9/7.
 */

public class BezierFragment extends Fragment {
    View mView;
    BezierLineView bezierLineView;

    BezierBallView bezierBallView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_bezier, container, false);
        bezierLineView =(BezierLineView)mView.findViewById(R.id.bezier_line_view);
        bezierBallView=(BezierBallView)mView.findViewById(R.id.bezier_ball_view);
        return mView;
    }
}
