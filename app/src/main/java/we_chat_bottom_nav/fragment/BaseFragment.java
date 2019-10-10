package we_chat_bottom_nav.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2019/9/11.
 * description:
 */
public class BaseFragment extends Fragment {

    public static final String ARGS_TITLE = "args_title";
    private String mTitle;

    public static BaseFragment newInsatance (String title){
        Bundle args =new Bundle();
        args.putString(ARGS_TITLE,title);
        BaseFragment fragment =new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments =getArguments();
        if (arguments!=null){
            mTitle =arguments.getString(ARGS_TITLE,"");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_we_chat__bottom_nav,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView =(TextView) view.findViewById(R.id.fragment_tv);
        textView.setText(mTitle);
    }
}
