package eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.projecttest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wuyue on 2019/11/5.
 * description: EventBus 事件订阅者页面
 */
public class EventBusAActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private Button button;
    private Button button2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_a);
        button = (Button) findViewById(R.id.btn_eventbus_to_activity_b);
        button2 = (Button) findViewById(R.id.btn_eventbus_clear);
        textView = (TextView) findViewById(R.id.tv_eventbus_subscriber);
        textView2 = (TextView) findViewById(R.id.tv_eventbus_subscriber2);
        gotoActivityB();
        clear();
        EventBus.getDefault().register(this);


    }

    private void clear() {
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                textView2.setText("");
            }
        });
    }

    private void gotoActivityB() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventBusAActivity.this, EventBusBActivity.class);
                startActivity(i);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        textView.setText(messageEvent.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event2(MessageEvent messageEvent) {
        textView2.setText(messageEvent.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
