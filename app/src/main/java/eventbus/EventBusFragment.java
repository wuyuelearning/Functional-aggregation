package eventbus;


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



/**
 * Created by wuyue on 2019/11/5.
 * description:
 */
public class EventBusFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_eventbus,container,false);

        Button button =(Button)v.findViewById(R.id.btn_start_event_bus);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(),EventBusAActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}