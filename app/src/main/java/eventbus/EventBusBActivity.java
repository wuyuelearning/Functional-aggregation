package eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.admin.projecttest.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wuyue on 2019/11/5.
 * description: EventBus 事件发布者页面
 */
public class EventBusBActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_b);
        Button button =(Button)findViewById(R.id.btn_event_bus_publisher);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("事件发送，事件接收"));
                finish();
            }
        });

        Button button2 =(Button)findViewById(R.id.btn_event_bus_publisher2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("事件发送，事件接收 ......"));
                finish();
            }
        });

    }
}
