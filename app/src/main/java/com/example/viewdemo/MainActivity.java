package com.example.viewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.viewdemo.acts.AutoFlowTextActivity;
import com.example.viewdemo.acts.BehindViewActivity;
import com.example.viewdemo.acts.DragViewTestActivity;
import com.example.viewdemo.acts.FloatActivity;
import com.example.viewdemo.acts.SlideMenuActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

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
            case R.id.btn_3:
                startActivity(new Intent(this, SlideMenuActivity.class));
                break;
            case R.id.btn_4:
                startActivity(new Intent(this, FloatActivity.class));
                break;
            case R.id.btn_5:
                startActivity(new Intent(this, UseageTimeActivity.class));
                break;
        }
    }

}
