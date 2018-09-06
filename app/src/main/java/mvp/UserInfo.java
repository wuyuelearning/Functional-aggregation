package mvp;

/**
 * Created by wuyue on 2018/9/5.
 */

public class UserInfo implements IUserInfo{
    private ILoginView mILoginView ;
    public UserInfo(ILoginView iLoginView){
        this.mILoginView =iLoginView;
    }


    @Override
    public void requestHttp() {
        mILoginView.onLoginReslut();
    }
}
