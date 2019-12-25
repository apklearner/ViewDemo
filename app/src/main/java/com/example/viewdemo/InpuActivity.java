package com.example.viewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InpuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_input);
        final EditText et = findViewById(R.id.et_input);
        Button jump = findViewById(R.id.btn_jump);

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    Intent intent = new Intent(InpuActivity.this,WebActivity.class);
                    intent.putExtra("data", content);
                    startActivity(intent);
                }
            }
        });

    }
}
