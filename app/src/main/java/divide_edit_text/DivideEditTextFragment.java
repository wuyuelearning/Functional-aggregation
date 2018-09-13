package divide_edit_text;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.admin.projecttest.R;


/**
 * Created by wuyue on 2018/9/12.
 *
 *  编辑框输入字符串 自动分隔
 */

public class DivideEditTextFragment extends Fragment {
    View mView;


    private EditText et_divide;

    DivideEditTextChangeListener divideEditTextChangeListener;
    DivideEditTextChangeListener2 divideEditTextChangeListener2;
    int[] divideSize = {3,2,5};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_divide_edittext, container, false);
        initView();
        return mView;
    }

    private void initView() {
        et_divide = (EditText) mView.findViewById(R.id.et_divide_edit);
//        divide1();
        divide2();
    }

    private void divide1(){
        divideEditTextChangeListener = new DivideEditTextChangeListener(et_divide,divideSize, "-");
        et_divide.addTextChangedListener(divideEditTextChangeListener);
    }

    private void divide2(){
        divideEditTextChangeListener2 = new DivideEditTextChangeListener2(et_divide,divideSize, "-");
        et_divide.addTextChangedListener(divideEditTextChangeListener2);
    }

}

