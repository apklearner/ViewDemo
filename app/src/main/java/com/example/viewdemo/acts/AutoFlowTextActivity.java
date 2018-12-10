package com.example.viewdemo.acts;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viewdemo.R;
import com.example.viewdemo.view.AutoFlowLayout;
import com.r0adkll.slidr.Slidr;

public class AutoFlowTextActivity extends AppCompatActivity {

    private AutoFlowLayout autoFlowLayout;

    private String[] msgs = new String[]{"第1个文本", "第2个文本", "第3个文本", "第4个", "第5个", "第6个文本", "第7个", "第8个文本wefa", "第9个文本", "第10个文本fsafd", "第11个文本", "第12个文本"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        autoFlowLayout = findViewById(R.id.auto_flow_layout);

        inflateView();
        Slidr.attach(this);
    }

    private void inflateView() {
        for (int i = 0; i < msgs.length; i++) {
            View childView = LayoutInflater.from(this).inflate(R.layout.layout_txt_item, null);
            TextView textView = childView.findViewById(R.id.tv_content);
            textView.setText(msgs[i]);
            childView.setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            childView.setTag(false);
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView childTv = v.findViewById(R.id.tv_content);

                    if ((boolean) v.getTag()) {
                        childTv.setTextColor(Color.RED);
                    }else {
                        childTv.setTextColor(Color.BLUE);
                    }

                    v.setTag(!((boolean)v.getTag()));



                }
            });

            autoFlowLayout.addView(childView);
        }
    }


}
