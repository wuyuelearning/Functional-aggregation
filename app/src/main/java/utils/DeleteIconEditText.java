package utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by huweiqiang on 2017/6/1.
 */

public class DeleteIconEditText extends EditText {
    public DeleteIconEditText(Context context) {
        this(context, null);
    }

    public DeleteIconEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);

        post(new Runnable() {
            @Override
            public void run() {
                DeleteIconEditText.this.addTextChangedListener(new DeleteIconTextWatcher(context, DeleteIconEditText.this));
                DeleteIconEditText.this.setOnTouchListener(new DeleteIconTouchListener(DeleteIconEditText.this));
            }
        });
    }


}
