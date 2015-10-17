package com.example.shraddha.cmpe277;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SensorActivity extends AppCompatActivity implements LocationListener {//implements SensorEventListener
    private static final Random RANDOM = new Random();
    TextView mTempSensorValue;
    TextView mPresSensorValue;
    Thread thread;

    Boolean threadRunning = false;
    private SensorManager sensorManager;
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
    private ArrayList<Sensor> registeredSensors;
    private float[] values;
    private HashMap<Integer, String> valueString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        setUpMap();
//        graphInit();

        valueString = new HashMap<Integer, String>();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorEventListener = getSensorEventListener();

        getListofSensors();

        registerSensors();
    }


    @NonNull
    private SensorEventListener getSensorEventListener() {
        return new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                Sensor sensor = event.sensor;
                values = event.values;

                if (values.length == 1) {
                    valueString.put(sensor.getType(), "" + values[0]);
                } else {
                    valueString.put(sensor.getType(), "X:" + values[0] + " Y:" + values[1] + " Z:" + values[2]);
                }

                if (sensor.getType() == Sensor.TYPE_HEART_RATE) {
                    mTempValue = event.values[0];
                    mTempTimeStamp = event.timestamp;
                } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    mPressureValue = event.values[0];
                    mPressureTimeStamp = event.timestamp;
                } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                    mHumidityValue = event.values[0];
                    mHumidityTimeStamp = event.timestamp;
                } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                    mLightValue = event.values[0];
                    mLightTimeStamp = event.timestamp;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void insertSensorDataInstance(String type, String value) {

        SensorData sensordata = new SensorData();
        sensordata.setSensortype(type);
        sensordata.setLatitude(latitude);
        sensordata.setLongitude(longitude);
        sensordata.setValue(value);

        new InsertSensorDataAsyncTask().execute(new Pair<Context, SensorData>(this, sensordata));
    }

    private void getListofSensors() {
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        registeredSensors = new ArrayList<Sensor>();
        for (Sensor s : deviceSensors) {
            if (sensorExists(s)) {
                registeredSensors.add(s);
            }
            System.out.println(s.getName() + ":" + s.getType());
        }
    }

    public boolean sensorExists(Sensor sensor) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            int name = sharedPreferences.getInt(sensor.getName(), -1);
            return name != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void registerSensors() {
        for (Sensor sensor : registeredSensors) {
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(sensor.getType()), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        threadRunning = true;

        getListofSensors();
        registerSensors();

        thread = new Thread(getUploadThreadRunnable());
        thread.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        threadRunning = false;
//        thread.
    }

    /**
     * Set up the map to get the current location of device
     */
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
    public void onLocationChanged(Location location) {
        setUpMap();
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


    /**
     * Thread Object to start uploading of the registered sensors in application
     *
     * @return Runnable
     */
    @NonNull
    public Runnable getUploadThreadRunnable() {
        return new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    // sleep 30 seconds to slow down the add of entries
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (threadRunning)
                        uploadSensorData();
                }

            }
        };
    }

    private void uploadSensorData() {
        for (Sensor sensor : registeredSensors) {
            insertSensorDataInstance(sensor.getName(), valueString.get(sensor.getType()));
            Log.d("Upload Data", "uploaded: " + sensor.getName());
        }
    }
}
