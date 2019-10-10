package we_chat_bottom_nav.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.projecttest.R;

import we_chat_bottom_nav.WeChatBottomNavActivity;

/**
 * Created by wuyue on 2019/9/11.
 * description:
 */
public class WeChatNavStartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_we_chat_nav_start,container,false);
        Button btn =(Button) view.findViewById(R.id.btn_we_chat_nav_start);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), WeChatBottomNavActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
}
