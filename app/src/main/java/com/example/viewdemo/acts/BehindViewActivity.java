package com.example.viewdemo.acts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.viewdemo.R;
import com.example.viewdemo.view.BehindGroupView;
import com.r0adkll.slidr.Slidr;

public class BehindViewActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behind_view);

        final BehindGroupView behindGroupView = findViewById(R.id.beview);

        findViewById(R.id.tv_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behindGroupView.toggleRunner();
            }
        });

        findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BehindViewActivity.this,"click the behind ",Toast.LENGTH_SHORT).show();
            }
        });
        Slidr.attach(this);
    }
}
