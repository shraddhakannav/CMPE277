package com.example.shraddha.cmpe277;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;
import java.util.Random;

public class SensorActivity extends AppCompatActivity implements LocationListener {//implements SensorEventListener
    private static final Random RANDOM = new Random();
    TextView mTempSensorValue;
    TextView mPresSensorValue;
    Thread thread;
    Boolean threadRunning = false;
    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;
    private SensorEventListener sensorEventListener;
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
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private LineGraphSeries<DataPoint> lightSeries;
    private int lastX = 0;
    private Viewport viewport;
    private LineGraphSeries<DataPoint> pressureSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        setUpMap();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        pressureSeries = new LineGraphSeries<DataPoint>();

        graph.addSeries(pressureSeries);

        lightSeries = new LineGraphSeries<DataPoint>();
        lightSeries.setCustomPaint(new Paint(Color.CYAN));
        graph.addSeries(lightSeries);

        // customize a little bit viewport
        viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(1200);
        viewport.setScrollable(true);


        // Get the text fields to set the values from the sensors.
        mPresSensorValue = (TextView) findViewById(R.id.PressureSensorValue);
        mTempSensorValue = (TextView) findViewById(R.id.TemperatureSensorValue);
        mLightSensorValue = (TextView) findViewById(R.id.LightSensorValue);
        mHumiditySensorValue = (TextView) findViewById(R.id.HumiditySensorValue);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Informational : To check the sensors in a device.
        getListofSensors();

        sensorEventListener = getSensorEventListener();
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);

    }

    @NonNull
    private SensorEventListener getSensorEventListener() {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_HEART_RATE) {
                    mTempValue = event.values[0];
                    mTempTimeStamp = event.timestamp;

                } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    mPressureValue = event.values[0];
                    mPressureTimeStamp = event.timestamp;
                    System.out.println("Pressure value" + mPressureValue);
                    mPresSensorValue.setText(Float.toString(mPressureValue));
                } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                    mHumidityValue = event.values[0];
                    mHumidityTimeStamp = event.timestamp;
                } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
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
    }


    public void uploadSensorData(View view) {

        insertSensorDataInstance("LIGHT", mLightValue);
        insertSensorDataInstance("PRESSURE", mPressureValue);
    }

    private void insertSensorDataInstance(String type, float value) {

        SensorData sensordata = new SensorData();
        sensordata.setSensortype(type);
        sensordata.setLatitude(latitude);
        sensordata.setLongitude(longitude);
        sensordata.setValue(value);

        new InsertSensorDataAsyncTask().execute(new Pair<Context, SensorData>(this, sensordata));
    }

    private String getCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return null;
    }


    private void getListofSensors() {
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : deviceSensors) {
            System.out.println(s.getName());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        threadRunning = true;
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }

                    if (i % 20 == 0 && threadRunning) {
                        insertSensorDataInstance("LIGHT", mLightValue);
                        insertSensorDataInstance("PRESSURE", mPressureValue);
                    }
                }
            }
        });

        thread.start();
    }


    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        lightSeries.appendData(new DataPoint(lastX++, mLightValue), true, 10);
        pressureSeries.appendData(new DataPoint(lastX++, mPressureValue), true, 10);
    }


    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
        threadRunning = false;
    }

    @Override
    public void onLocationChanged(Location location) {

        setUpMap();

    }

    private void setUpMap() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location myLocation = locationManager.getLastKnownLocation(provider);

        if (myLocation != null) {

            latitude = myLocation.getLatitude();
            longitude = myLocation.getLongitude();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }


}
