package Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import ProjectUtils.StringUtil;
import Utils.ZXingUtils;

/**
 * Created by wuyue on 2018/6/29.
 */

public class QRCodeFragment extends Fragment {

    View mView;
    EditText evQRCodeString;
    ImageView ivQRCode;
    Button btnCreateQRCode;
    String qrString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.qr_code_fragment, container, false);
        mView = view;
        initView();
        return mView;
    }

    private void initView() {

        evQRCodeString = (EditText) mView.findViewById(R.id.ev_qr_String);
        ivQRCode = (ImageView) mView.findViewById(R.id.iv_qr_code);
        btnCreateQRCode = (Button) mView.findViewById(R.id.btn_creat_qr_code);

        btnCreateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrString = evQRCodeString.getText().toString();
                if (!StringUtil.isEmpty(qrString)) {
                    createQRCode(qrString);
                } else {
                    Toast.makeText(getContext(), "输入为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createQRCode(String qrString) {
        Bitmap bitmap = ZXingUtils.createQRImage(qrString, ivQRCode.getWidth(), ivQRCode.getHeight());
        ivQRCode.setImageBitmap(bitmap);
    }
}
