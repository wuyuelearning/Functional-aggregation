package utils;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by huweiqiang on 2017/6/1.
 */

public class DeleteIconTouchListener implements View.OnTouchListener {
    private EditText mEditText;

    public DeleteIconTouchListener(EditText editText) {
        this.mEditText = editText;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Drawable[] drawables = mEditText.getCompoundDrawables();
        Drawable drawableRight = drawables[2];
        if (drawableRight != null) {
            if ((event.getX() > mEditText.getWidth() - drawableRight.getIntrinsicWidth() - mEditText.getPaddingRight()) &&
                    (event.getX() < mEditText.getWidth() - mEditText.getPaddingRight())) {
                mEditText.setText("");
                return false;
            }
        }
        return false;
    }
}
