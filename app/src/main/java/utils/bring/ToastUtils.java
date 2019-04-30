package utils.bring;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;


/**
 * Created by lihong on 2016/2/4.
 * Toast utils
 */
public class ToastUtils {
    private static Toast mToast;

    public static void showSuccessShortToast(Context context, String msg) {
        if (StringUtil.notNullOrEmpty(msg)) {
            if (mToast == null) {
                View view = getToastLayoutView(context.getApplicationContext(), R.drawable.comm_face_success, msg);
                mToast = new Toast(context.getApplicationContext());
                mToast.setDuration(Toast.LENGTH_SHORT);
                mToast.setView(view);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                TextView tvMsg = (TextView) mToast.getView().findViewById(R.id.toast_tv);
                ImageView ivToast = (ImageView) mToast.getView().findViewById(R.id.toast_pic);
                ivToast.setImageResource(R.drawable.comm_face_success);
                tvMsg.setText(msg);
            }
            mToast.show();
        }
    }

    public static void showFailShortToast(Context context, String msg) {
        if (StringUtil.notNullOrEmpty(msg)) {
            if (mToast == null) {
                View view = getToastLayoutView(context.getApplicationContext(), R.drawable.comm_face_fail, msg);
                mToast = new Toast(context.getApplicationContext());
                mToast.setDuration(Toast.LENGTH_LONG);
                mToast.setView(view);
                mToast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                TextView tvMsg = (TextView) mToast.getView().findViewById(R.id.toast_tv);
                ImageView ivToast = (ImageView) mToast.getView().findViewById(R.id.toast_pic);
                ivToast.setImageResource(R.drawable.comm_face_fail);
                tvMsg.setText(msg);
            }
            mToast.show();
        }
    }

    private static View getToastLayoutView(Context context, int resourceId, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
        View view = inflater.inflate(R.layout.toast, null);
        ImageView ivMood = (ImageView) view.findViewById(R.id.toast_pic);
        ivMood.setImageResource(resourceId);
        TextView textView = (TextView) view.findViewById(R.id.toast_tv);
        textView.setText(msg);
        return view;
    }
}
