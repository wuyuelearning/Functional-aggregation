package mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/9/5.
 */

public class MVPFragment extends Fragment implements ILoginView, View.OnClickListener {

    private View mView;
    private ILoginPresent mILoginPresent;
    private Context mContext;
    EditText et_user_name;
    EditText et_password;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_login, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mILoginPresent = new LoginPresent(mContext,this);
        TextView tv_user_name = (TextView) mView.findViewById(R.id.tv_user_name);
        et_user_name = (EditText) mView.findViewById(R.id.et_password);
        TextView tv_password = (TextView) mView.findViewById(R.id.tv_password);
        et_password = (EditText) mView.findViewById(R.id.et_password);
        Button btn_clear = (Button) mView.findViewById(R.id.btn_clear);
        Button btn_login = (Button) mView.findViewById(R.id.btn_login);

        btn_clear.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    @Override
    public void clearText() {
        et_user_name.setText("");
        et_password.setText("");
        Toast.makeText(mContext, "clear", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginReslut() {
        Toast.makeText(mContext, "login", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String  getName() {
        String name =null;
        if (!TextUtils.isEmpty(et_user_name.getText().toString().trim())) {
            name = et_user_name.getText().toString().trim();
        } else {
            Toast.makeText(mContext, "user name is empty", Toast.LENGTH_SHORT).show();
        }
        return name;
    }

    @Override
    public String getPassword() {
        String password =null;
        if (!TextUtils.isEmpty(et_password.getText().toString().trim())) {
            password = et_password.getText().toString().trim();
        } else {
            Toast.makeText(mContext, "password  is empty", Toast.LENGTH_SHORT).show();
        }
        return password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                mILoginPresent.clear();
                break;
            case R.id.btn_login:
                mILoginPresent.doLogin();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
