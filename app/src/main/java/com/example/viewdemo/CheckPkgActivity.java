package com.example.viewdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckPkgActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLift;
    private EditText etInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift);
        btnLift = findViewById(R.id.btn_lift);
        etInput = findViewById(R.id.et_input);

        btnLift.setOnClickListener(this);

//        etInput.setText("qqnews://article_9527?nm=20180410A0DKOO00&from=jg");

    }

    public static boolean deepLinkLift(Context context, String targetUrl) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(targetUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isPackageInstall(Context context, String targetUrl) {
        try {
            if (!TextUtils.isEmpty(targetUrl)) {
                Intent intent = Intent.parseUri(targetUrl, 0);
                return intent.resolveActivity(context.getPackageManager()) != null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lift:
                String input = etInput.getText().toString();
                if (!TextUtils.isEmpty(input)) {
                    if (isPackageInstall(this, input)) {
                        if(!deepLinkLift(this,input)){
                            Toast.makeText(this,"地址不正确",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "地址不正确", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }
}
