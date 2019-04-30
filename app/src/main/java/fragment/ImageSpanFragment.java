package fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.List;

import utils.bring.NewsTextSwitcher;
import utils.bring.CarouselTextView;


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
        carouselTextView();
        textSwitcher();

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

    private void carouselTextView() {
        CarouselTextView carouselTextView = (CarouselTextView) mView.findViewById(R.id.ctv_carousel_content);
        List<String> content = new ArrayList<>();
        content.add("111111111");
        content.add("2222222222222");
        content.add("3333333333");
        content.add("4444444444");
        if (content.size() > 0) {
            carouselTextView.setContentList(content);
            carouselTextView.startCarousel();
        }
    }


    private Handler handler;
    private Runnable task;
    private int marker;

    private void textSwitcher() {
        NewsTextSwitcher textSwitcher = (NewsTextSwitcher) mView.findViewById(R.id.nts_content);
        List<String> content1 = new ArrayList<>();
        content1.add("111111111---");
        content1.add("2222222222222---");
        content1.add("3333333333---");
        content1.add("4444444444---");
        if (content1.size() > 0) {
            textSwitcher.setContentList(content1);
            textSwitcher.startCarousel();
        }
//        textSwitcher.setText(content1.get(0));


    }


}
