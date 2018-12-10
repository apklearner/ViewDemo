package com.example.viewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.viewdemo.acts.AutoFlowTextActivity;
import com.example.viewdemo.acts.BehindViewActivity;
import com.example.viewdemo.view.AutoFlowLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                startActivity(new Intent(this, BehindViewActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this, AutoFlowTextActivity.class));

                break;
        }
    }

}
