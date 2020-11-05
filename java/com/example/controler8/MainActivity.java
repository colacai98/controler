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

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private TextView tvx;
    private TextView tvy;
    private TextView tvz;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        tvx = findViewById(R.id.acceleratorX);
        tvy = findViewById(R.id.acceleratorY);
        tvz = findViewById(R.id.acceleratorZ);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // Do nothing!
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mAccelerometer != null)
        {
            mSensorManager.registerListener(this, mAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        Sensor mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(mMagneticField != null)
        {
            mSensorManager.registerListener(this, mMagneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.length);
        }
        else if ( event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.length);
        }
        mSensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading);
        mSensorManager.getOrientation(rotationMatrix, orientationAngles);

        float angleZ = (float) Math.toDegrees(orientationAngles[0]);
        float angleX = (float) Math.toDegrees(orientationAngles[1]);
        float anglyY = (float) Math.toDegrees(orientationAngles[2]);

        tvx.setText("left-right: " + String.format("%.02f", angleX));
        tvy.setText("bottom-up: " + String.format("%.02f", anglyY));
        tvz.setText("not used: " + String.format("%.02f", angleZ));
    }
}
