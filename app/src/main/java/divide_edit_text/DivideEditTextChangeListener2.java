package divide_edit_text;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import Utils.DivideEditTextUtil;

/**
 * Created by wuyue on 2018/9/13.
 *
 *  整体实现都写在同一个类中
 */

public class DivideEditTextChangeListener2 implements TextWatcher{

    private String divideStyle = " ";
    private int[] divideCount;
    private EditText et_divide;


    public DivideEditTextChangeListener2(EditText et_divide ,int[] divideSize, String divideStyle) {

        this.divideCount = divideSize;
        this.divideStyle = divideStyle;
        this.et_divide =et_divide;
    }

    public DivideEditTextChangeListener2(String divideStyle) {
        this.divideStyle = divideStyle;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        this.et_divide.removeTextChangedListener(this);
        s.replace(0, s.length(), DivideEditTextUtil.divideString(this.et_divide.getText().toString(),this.divideCount,this.divideStyle));
//        s.replace(0, s.length(), DivideEditTextUtil.divideString2(this.et_divide.getText().toString(),this.divideStyle));
//            s.replace(0, s.length(), divideString(this.divideStyle));
        this.et_divide.addTextChangedListener(this);

    }

}
