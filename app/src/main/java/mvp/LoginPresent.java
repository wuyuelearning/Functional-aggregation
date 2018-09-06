package mvp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wuyue on 2018/9/5.
 */

public class LoginPresent implements ILoginPresent {
    private MVPFragment mILoginView;


    private IUserInfo mIUserInfo;
    private Context mContext;

    public LoginPresent(Context context, MVPFragment ILoginView) {
        this.mContext = context;
        this.mILoginView = ILoginView;
        this.mIUserInfo = new UserInfo(ILoginView);
    }

    @Override
    public void clear() {
        mILoginView.clearText();
    }

    @Override
    public void doLogin() {
        String password = mILoginView.getPassword();
        String name = mILoginView.getName();
        Toast.makeText(mContext, "name:" + name + "  password:" + password, Toast.LENGTH_SHORT).show();
//        mILoginView.onLoginReslut();
        mIUserInfo.requestHttp();
    }
}
