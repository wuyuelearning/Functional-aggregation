package ProjectUtils;

import android.view.View;

public interface IToast {
    IToast setGravity(int gravity, int xOffset, int yOffset);

    IToast setDuration(long durationMillis);

    /**
     * 不能和{@link #setText(String)}一起使用
     */
    IToast setView(View view);

    IToast setMargin(float horizontalMargin, float verticalMargin);

    /**
     * 不能和{@link #setView(View)}一起使用
     */
    IToast setText(String text);

    void show();

    void cancel();

}
