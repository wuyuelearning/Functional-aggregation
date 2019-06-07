package fragment_turn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.projecttest.R;

/**
 * Created by wuyue on 2019/6/7.
 * description:
 */
public class FragmentTurnFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_turn_fragment, container, false);
        TextView view1 =(TextView)view.findViewById(R.id.tv_start_turn);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(),FragmentTurnActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

}
