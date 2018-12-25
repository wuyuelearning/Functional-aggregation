package utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.admin.projecttest.R;


/**
 * Created by huweiqiang on 2017/6/1.
 */

public class DeleteIconTextWatcher implements TextWatcher {
    private final Drawable mDeleteDrawable;
    private EditText mEditText;
    private Context mContext;

    public DeleteIconTextWatcher(Context context, EditText editText) {
        this.mContext = context;
        this.mEditText = editText;
        this.mDeleteDrawable = convertDrawable(R.drawable.comm_ic_edit_clear);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String content = mEditText.getText().toString();
        if (mEditText.isFocused() && !TextUtils.isEmpty(content) && !TextUtils.isEmpty(content.trim())) {
            mEditText.setCompoundDrawables(null, null, mDeleteDrawable, null);
        } else {
            mEditText.setCompoundDrawables(null, null, null, null);
        }
        mEditText.setSelection(mEditText.getSelectionStart());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private Drawable convertDrawable(int resId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        }
        return drawable;
    }
}
