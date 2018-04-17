package com.example.admin.projecttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String FRAGMENT_TYPE_1= "PopupWindowFragment";
    private static final String FRAGMENT_TYPE_2= "ListViewAdapterFragment";
    private static final String FRAGMENT_TYPE_3= "RecycleViewAdapterFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.text1:
                bundle.putString("choice",FRAGMENT_TYPE_1);
                break;
            case R.id.text2:
                bundle.putString("choice",FRAGMENT_TYPE_2);
                break;
            case R.id.text3:
                bundle.putString("choice",FRAGMENT_TYPE_3);
                break;
        }
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}
