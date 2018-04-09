package com.example.admin.projecttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.text1:
                bundle.putString("choice","first");
        }
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}
