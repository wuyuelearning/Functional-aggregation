package Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wuyue on 2018/4/23.
 *
 * 使用 Toast 时，建议定义一个全局的 Toast 对象，这样可以避免连续显示
 * Toast 时不能取消上一次 Toast 消息的情况。即使需要连续弹出 Toast，也应避免直
 * 接调用 Toast#makeText。
 *
 *参考
 * https://blog.csdn.net/codekxx/article/details/70851532
 * https://blog.csdn.net/qq_23940659/article/details/50971043
 */

public class ToastUtil {
    private static Toast toast;
    public static void showToast(Context context ,String content,int during){
        if(toast!=null){
            toast.setText(content);
            toast.setDuration(during);
        } else {
            toast= Toast.makeText(context,content,during);
        }
        toast.show();
    }
}
