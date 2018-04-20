package Fragment;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.projecttest.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by wuyue on 2018/4/18.
 *
 * Android 8 通知栏设置
 * 需要在系统版本是8.0 的手机上才可使用
 *
 * http://mp.weixin.qq.com/s/WxgHJ1stBjokPi6lTUd1Mg
 *
 * 额.... 没能实现
 *
 */

public class NotificationFragment extends Fragment implements View.OnClickListener{
    private Context mContext;
    private View mRootView;
    TextView textView1 ;
    TextView textView2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = inflater.inflate(R.layout.notification_fragment, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

        textView1= (TextView)mRootView.findViewById(R.id.text_chat);
        textView2= (TextView)mRootView.findViewById(R.id.text_Subscribe);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);

        return mRootView;
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    public void sendChatMsg(View view) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext)
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.renma)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.renma))
                .setAutoCancel(true)
                .setNumber(2)
                .build();
        manager.notify(2, notification);
    }
    public void sendSubscribeMsg(View view) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(mContext)
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.xiaohei)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.xiaohei))
                .setAutoCancel(true)
                .setNumber(2)
                .build();
        manager.notify(2, notification);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.text_chat){
            Toast.makeText(mContext,"text_chat",Toast.LENGTH_SHORT).show();
            sendChatMsg(v);
        }
        else if (v.getId() == R.id.text_Subscribe){
            Toast.makeText(mContext,"text_chat",Toast.LENGTH_SHORT).show();
            sendSubscribeMsg(v);
        }
    }
}
