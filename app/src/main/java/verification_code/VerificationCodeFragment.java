package verification_code;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2020/1/19.
 * description:
 */
public class VerificationCodeFragment extends Fragment {
    private Context mContext;
    private PasswordUnderLineEditText mEitText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);
        mContext = getContext();
        initView(view);
        return view;
    }

    private void initView(View view) {
        mEitText = (PasswordUnderLineEditText) view.findViewById(R.id.et_verification_code);
        mEitText.setTextIndexChangeListener(new PasswordUnderLineEditText.TextIndexChangeListener() {
            @Override
            public void onTextIndexChange(CharSequence text, int length) {
                if (length == 6) {
                    Toast.makeText(mContext, "input ", Toast.LENGTH_SHORT).show();
                } }
        });
        mEitText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            String code = mEitText.getText().toString();
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

//                if (hasFocus) {
//                    mEitText.setError(false);
//                } else {
//                    if (code.length() != 6) {
//                        mEitText.setError(true);
//                    } else {
//                        mEitText.setError(false);
//                    }
//                }

            }
        });
    }


}
