package callbacktest.callback;

/**
 * Created by wuyue on 2019/1/23.
 * describe:
 */

public class MyCallBackImpl/* implements ISetCallBack*/ {
    /*@Override*/
    public void setCallBack(IMyCallBack callBack) {
        Auther  auther =new Auther();
        callBack.onCallBack2();
        auther.tran(callBack,10);

    }

/*    @Override*/
    public void setCallBack(IMyCallBack callBack, int i) {
        if (i%2==0){
            callBack.onCallBack();
        } else {
            callBack.onCallBack2();
        }

    }
}
