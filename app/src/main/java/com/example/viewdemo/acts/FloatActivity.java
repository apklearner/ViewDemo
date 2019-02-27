package com.example.viewdemo.acts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewdemo.R;
import com.example.viewdemo.view.FloatLayout;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrInterface;


public class FloatActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView content;
    private FloatLayout floatLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);

        SlidrInterface slidrInterface = Slidr.attach(this);
        content = findViewById(R.id.content);
        floatLayout = findViewById(R.id.floatLayout);

        floatLayout.attachSlidr(slidrInterface);

        content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content:
                Toast.makeText(getApplicationContext(), "点击了Drag", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
