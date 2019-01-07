package com.example.viewdemo.acts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewdemo.R;

/**
 * Created by ly on 2019/1/1.
 */

public class SlideMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvContent;
    private TextView tvExpand;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);
        initRes();
    }

    private void initRes() {
        tvContent = findViewById(R.id.tv_content);
        tvExpand = findViewById(R.id.tv_expand);

        tvExpand.setOnClickListener(this);
        tvContent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                Toast.makeText(getApplicationContext(), "content click", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_expand:
                Toast.makeText(getApplicationContext(), "expand click", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


