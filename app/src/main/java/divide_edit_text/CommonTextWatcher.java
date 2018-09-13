package divide_edit_text;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by wuyue on 2018/9/13.
 */

public abstract class CommonTextWatcher implements TextWatcher{

    EditText mEditText ;
    public CommonTextWatcher (EditText editText){
        mEditText = editText;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mEditText.removeTextChangedListener(this);
        cover(s);
        mEditText.addTextChangedListener(this);
    }

    abstract void cover(Editable s);
}
