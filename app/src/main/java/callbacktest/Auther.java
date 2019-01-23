package callbacktest;

/**
 * Created by wuyue on 2019/1/23.
 * describe:
 */

public class Auther {

    public void tran(IMyCallBack callBack, int i) {
        if (i % 2 == 0) {
            callBack.onCallBack();
        } else {
            callBack.onCallBack2();
        }
    }
}
