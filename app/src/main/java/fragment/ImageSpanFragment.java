package fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.admin.projecttest.R;


/**
 * Created by wuyue on 2018/5/24.
 * 文字图片混合
 */

public class ImageSpanFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_span_fragment, container, false);
        mView = view;
        TextView t = (TextView) mView.findViewById(R.id.tv_icon_behind);

        imageSpan();

        return view;
    }

    /**
     * 图片在文字最后面
     * 如果有多行 则在最后一行的后面
     */
    private void imageSpan() {
        ImageSpan is = new ImageSpan(BitmapFactory.decodeResource(getResources(), R.drawable.help_icon));

        String str = "ajefhhfuh weufhquiw ehfuq whef  hquwiadsfasdfasdfasdfasdfehf qwefqwe";
        String space = "";
        str = str + space;
        int strLength = str.length();
        SpannableString ss = new SpannableString(str);
        ss.setSpan(is, strLength - 1, strLength, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        ((TextView) mView.findViewById(R.id.tv_icon_behind)).setText(ss.subSequence(0, strLength));
    }


}
