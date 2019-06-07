package utils.baseactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.example.admin.projecttest.R;


public class KCBaseFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    private CommLoadingDialog commLoadingDialog;
    public boolean isStop = false;
    public FragmentActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isStop = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public void dialogShow() {
        dialogShow(true);
    }

    public void dialogDismiss() {
        if (getActivity() instanceof KCBaseActivity) {
            ((KCBaseActivity) getActivity()).dialogDismiss();
            return;
        }
        if (commLoadingDialog != null && commLoadingDialog.isShowing()) {
            commLoadingDialog.stopAnimation();
            commLoadingDialog.dismiss();
        }
    }

    public void dialogShow(boolean flag) {
        if (getActivity() instanceof KCBaseActivity) {
            ((KCBaseActivity) getActivity()).dialogShow(flag);
            return;
        }
        if (commLoadingDialog == null) {
            commLoadingDialog = new CommLoadingDialog(getActivity());
        }
        commLoadingDialog.setCanceledOnTouchOutside(flag);
        commLoadingDialog.startAnimation();
        if (commLoadingDialog.isShowing()) {
            return;
        }
        // java.lang.IllegalArgumentException: View not attached to window manager
        if (getActivity().getApplicationContext() != null && !getActivity().isFinishing()) {
            commLoadingDialog.show();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (getActivity() != null) {
            getActivity().overridePendingTransition(R.anim.dt_in_from_right, R.anim.dt_out_to_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (getActivity() != null) {
            getActivity().overridePendingTransition(R.anim.dt_in_from_right, R.anim.dt_out_to_left);
        }
    }

    /**
     * @param call
     * @return 是否调用成功
     */
    public boolean callParentActivity(KCBaseActivity.Call call) {
        FragmentActivity activity = getActivity();
        if (activity != null && activity instanceof KCBaseActivity) {
            return ((KCBaseActivity) activity).handleChildFragmentCall(call);
        } else {
            return false;
        }
    }

    public boolean handleParentActivityCall(KCBaseActivity.Call call) {
        return false;
    }
}