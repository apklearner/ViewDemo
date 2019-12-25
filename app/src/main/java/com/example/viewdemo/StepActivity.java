package com.example.viewdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class StepActivity extends AppCompatActivity {

    private TextView tvMsg;
    private TextView tvCur;
    private int curStep;
    private SensorEventListener countListner;
    private SensorManager sensorManager;

    /**
     *
     * 如果用户频繁修改系统时间何解？
     *
     * 1.获取当前服务器的时间
     * 2.第一次启动app，获取当前对应的步数
     * 3.计算当天的剩余时间
     * 4.记录当前系统初始时间 elapsedRealtime
     * 5.每次叠加步数前比对 当前 elapsedRealtime和上次记录的差值，如果小于剩余时间则叠加
     *
     *
     * 场景1
     * 1.当天启动过，统计过步数；手机重启
     * 2.
     *
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        SystemClock.currentThreadTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        tvMsg = findViewById(R.id.tv_msg);
        tvCur = findViewById(R.id.tv_cur);
        tvCur.setText("0");
        sensorManager = (SensorManager) this
                .getSystemService(SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        sensorManager.registerListener(countListner = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
//                Log.e("1234", event + "");
                tvMsg.setText((int) event.values[0] + "");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.e("1234", sensor + "  " + accuracy);

            }
        }, countSensor, SensorManager.SENSOR_DELAY_NORMAL);


        Sensor calSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                curStep++;
                tvCur.setText(curStep + "");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, calSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 19)
            sensorManager.flush(countListner);
    }


}
