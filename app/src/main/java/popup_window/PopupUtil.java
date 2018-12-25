package popup_window;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Description ${Desc}
 * Author Zhaolizhi
 * Date 2017/6/13.
 */

public class PopupUtil {

    public static void showAsDropDown(PopupWindow popupWindow, View anchor) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, x, y + anchor.getHeight());
        } else {
            popupWindow.showAsDropDown(anchor);
        }
    }

    /**
     * PopupWindow的高度为MATCH_PARENT
     *
     * @param context
     * @param popupWindow
     * @param parentView
     */
    public static void showDownward(Context context, PopupWindow popupWindow, View parentView) {

        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(parentView);
        } else {
            int[] a = new int[2];
            parentView.getLocationInWindow(a);
            int screenHeight = MobileUtil.getScreenHeight(context);
            if (Build.VERSION.SDK_INT >= 25) {
                int tempheight = popupWindow.getHeight();
                if (tempheight == WindowManager.LayoutParams.MATCH_PARENT || screenHeight <= tempheight) {
                    popupWindow.setHeight(screenHeight - a[1] - parentView.getHeight());
                }
            }
            popupWindow.showAtLocation(parentView, Gravity.NO_GRAVITY, 0, parentView.getHeight() + a[1]);
            popupWindow.update();
        }
    }
}
