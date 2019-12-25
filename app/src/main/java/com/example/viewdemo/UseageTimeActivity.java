package com.example.viewdemo;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class UseageTimeActivity extends AppCompatActivity {

    private EditText etPackageName;
    private TextView tvTime;
    private Button btnSure;
    private UsageStatsManager manager;
    private long startTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);
        init();
    }

    private void init() {
        startTime = System.currentTimeMillis();
        manager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);

        etPackageName = findViewById(R.id.et_input);
        tvTime = findViewById(R.id.tv_time);
        btnSure = findViewById(R.id.btn_sure);

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAppUseaTime();
            }
        });

        etPackageName.setText("com.ss.android.article.news");

        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(intent);
    }


    private void checkAppUseaTime() {
        String packageName = etPackageName.getText().toString();
        if (!TextUtils.isEmpty(packageName)) {
            List<UsageStats> stats = manager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, System.currentTimeMillis());
            Log.e("1234", "stats size  " + stats.size());
            for (int i = 0; i < stats.size(); i++) {
                UsageStats useage = stats.get(i);
                if (packageName.equals(useage.getPackageName())) {
                    long frontTime = useage.getTotalTimeInForeground();
                    Log.e("1234", "frontTime " + frontTime);
                    tvTime.setText(frontTime / 1000 + "");
                }
            }
        }
    }
}
