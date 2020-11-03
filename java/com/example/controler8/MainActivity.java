package com.example.controler8;

import java.lang.Math;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.hardware.SensorEvent;
import android.os.Bundle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private TextView tvx;
    private TextView tvy;
    private TextView tvz;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        tvx = findViewById(R.id.acceleratorX);
        tvy = findViewById(R.id.acceleratorY);
        tvz = findViewById(R.id.acceleratorZ);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Do nothing!
    }

    @Override
    public final void onSensorChanged(SensorEvent event)
    {
        float[] values = event.values;//获取传感器XYZ方向上的数值
        tvx.setText("X: " + Float.toString(values[0]));
        tvy.setText("Y: " + Float.toString(values[1]));
        tvz.setText("Z: " + Float.toString(values[2]));

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
