package divide_edit_text;

import android.text.Editable;

import android.widget.EditText;

import Utils.DivideEditTextUtil;

/**
 * Created by wuyue on 2018/9/13.
 *
 *  集成CommonTextWatcher 将部分具体实现业务抽离 到cover中实现
 */

public class DivideEditTextChangeListener extends CommonTextWatcher {
    private String divideStyle = " ";
    private int[] divideCount;
    private EditText et_divide;


    public DivideEditTextChangeListener(EditText et_divide ,int[] divideSize, String divideStyle) {
        super(et_divide);
        this.divideCount = divideSize;
        this.divideStyle = divideStyle;
        this.et_divide =et_divide;
    }

    @Override
    void cover(Editable s) {
        s.replace(0, s.length(), DivideEditTextUtil.divideString(this.et_divide.getText().toString(),this.divideCount,this.divideStyle));
    }
}