package change_color;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ChangeColorLinearLayout extends LinearLayout {

    IChangeColorListener changeColorListener;

    public ChangeColorLinearLayout(Context context) {
        super(context);
    }

    public ChangeColorLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChangeColorLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setChangeColorListener(IChangeColorListener listener){
        changeColorListener =listener;
    }

    int lastX =0;
    int count =10;
    int colorX=0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (changeColorListener!=null){
            colorX =colorX+count;
            if (colorX>255){
                colorX =10;
            }
            changeColorListener.setColor(colorX);
        }

        Log.d("ChangeColor colorX :",""+colorX);
        return true;
    }
}
