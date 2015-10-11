package com.example.shraddha.cmpe277;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import static android.app.PendingIntent.getActivity;

public class SensorActivity extends AppCompatActivity  {//implements SensorEventListener
    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;
    private SensorEventListener sensorEventListener;
    TextView mTempSensorValue;
    TextView mPresSensorValue;
    private float mTempValue;
    private float mPressureValue;
    private long mTempTimeStamp;
    private long mPressureTimeStamp;
    private float mHumidityValue;
    private long mHumidityTimeStamp;
    private float mLightValue;
    private long mLightTimeStamp;
    private TextView mHumiditySensorValue;
    private TextView mLightSensorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        // Get the text fields to set the values from the sensors.
        mPresSensorValue = (TextView)findViewById(R.id.PressureSensorValue);
        mTempSensorValue = (TextView)findViewById(R.id.TemperatureSensorValue);
        mLightSensorValue =(TextView)findViewById(R.id.LightSensorValue);
        mHumiditySensorValue =(TextView)findViewById(R.id.HumiditySensorValue);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        // Informational : To check the sensors in a device.

        getListofSensors();
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if(sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE )
                {
                    mTempValue = event.values[0];
                    mTempTimeStamp = event.timestamp;

                }
                else if (sensor.getType()==Sensor.TYPE_PRESSURE){
                    mPressureValue = event.values[0];
                    mPressureTimeStamp = event.timestamp;
                    System.out.println("Pressure value" + mPressureValue);
                    mPresSensorValue.setText(Float.toString(mPressureValue));
                }
                else if (sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
                    mHumidityValue = event.values[0];
                    mHumidityTimeStamp = event.timestamp;
                }

                else if (sensor.getType()==Sensor.TYPE_LIGHT){
                    mLightValue = event.values[0];
                    mLightTimeStamp = event.timestamp;
                    System.out.println("Illumination" + mLightValue);
                    mLightSensorValue.setText(Float.toString(mLightValue));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void getListofSensors() {
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s : deviceSensors){
            System.out.println(s.getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE),SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener,mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);

    }
}
