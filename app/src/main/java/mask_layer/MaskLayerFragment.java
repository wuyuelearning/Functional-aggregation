package mask_layer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2018/12/19.
 * describe: 蒙层
 *
 * 在xml 文件中 需要注意蒙层的层级，需要和被覆盖的内容的LinearLayout在一个层级，在同一个RelativeLayout中
 *
 * 注意 蒙层在那些地方需要 收起，那些地方需要展示
 */

public class MaskLayerFragment extends Fragment implements View.OnClickListener{
    View mView;

    View mMaskLayer;
    EditText mEtinput;
    TextView mTvUse;

    private static final int ALPHA_128 = 128;
    private boolean isMaskLayerGone = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_mask_layer,container,false);
        initView();
        return mView;
    }

    private void initView(){
        mEtinput = (EditText)mView.findViewById(R.id.mask_layer_et_input);
//      mEtinput需在外层设置  android:focusableInTouchMode="true"
        mEtinput.setCursorVisible(false);
        mEtinput.setOnClickListener(this);
        mTvUse = (TextView)mView.findViewById(R.id.mask_layer_tv_use);
        mTvUse.getBackground().setAlpha(ALPHA_128);
        mTvUse.setClickable(false);
        mMaskLayer = (View)mView.findViewById(R.id.mask_layer_mask_view);
        mMaskLayer.setOnClickListener(this);
        setListener();

        mTvUse.setOnClickListener(this);
    }


    private void setListener(){

        mEtinput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mEtinput.addTextChangedListener(new EditTextWatcher(getContext()));
                if (isMaskLayerGone){  //  用作第一次弹起，之后的由mEtInputCode.setCursorVisible(true); 监听
                    showMaskLayer(true);
                }
            }
        });


        SoftKeyboardStateHelper helper =new SoftKeyboardStateHelper(mView);
        helper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int var1) {

            }

            @Override
            public void onSoftKeyboardClosed() {
                showMaskLayer(false);
            }
        });

    }

    private void showMaskLayer(boolean isMaskLayerShow){
        if(isMaskLayerShow){
            isMaskLayerGone =false;
            mEtinput.setCursorVisible(true);
            mMaskLayer.setVisibility(View.VISIBLE);
            mMaskLayer.getBackground().setAlpha(ALPHA_128);
        } else {
            isMaskLayerGone =true;
            mMaskLayer.setVisibility(View.GONE);
            mEtinput.setCursorVisible(false);
        }
    }

    private class EditTextWatcher implements TextWatcher {
        private Context context;

        EditTextWatcher(Context context) {
            this.context = context;
        }

        @Override
        public void afterTextChanged(Editable s) {
            String editString = mEtinput.getText().toString();
            if (TextUtils.isEmpty(editString)) {
                mTvUse.getBackground().setAlpha(ALPHA_128);
                mTvUse.setClickable(false);
            } else {
                mTvUse.getBackground().setAlpha(255);
                mTvUse.setText("使用");
                mTvUse.setClickable(true);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.mask_layer_tv_use ){

        }else if (v.getId() == R.id.mask_layer_et_input) {
            if (isMaskLayerGone){
                showMaskLayer(true);
            }
        } else if(v.getId() ==R.id.mask_layer_mask_view){
            showMaskLayer(false);
            // 隐藏键盘
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
        }

    }
}
