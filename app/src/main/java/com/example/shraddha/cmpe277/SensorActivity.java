package com.example.shraddha.cmpe277;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorActivity extends AppCompatActivity implements LocationListener {//implements SensorEventListener

    Thread thread;

    Boolean threadRunning = false;
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private ArrayList<Sensor> registeredSensors;
    private float[] values;
    private HashMap<Integer, String> valueString;
    private HashMap<Integer, String> nameString;
    private GifView gifView;
    private TextView uploadStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        gifView = (GifView) findViewById(R.id.gifView);
        uploadStatus = (TextView) findViewById(R.id.uploadStatus);

        setUpMap();

        valueString = new HashMap<Integer, String>();
        nameString = new HashMap<Integer, String>();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorEventListener = getSensorEventListener();

        getListofSensors();

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

                switch (sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        nameString.put(sensor.getType(), "ACCELEROMETER");
                        valueString.put(sensor.getType(), "X:" + values[0] + " Y:" + values[1] + " Z:" + values[2]);
                        break;
                    case Sensor.TYPE_LIGHT:
                        nameString.put(sensor.getType(), "LIGHT");
                        valueString.put(sensor.getType(), "" + values[0]);
                        break;
                    case Sensor.TYPE_PRESSURE:
                        nameString.put(sensor.getType(), "PRESSURE");
                        valueString.put(sensor.getType(), "" + values[0]);
                        break;
                    case Sensor.TYPE_PROXIMITY:
                        nameString.put(sensor.getType(), "PROXIMITY");
                        valueString.put(sensor.getType(), "" + values[0]);
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        nameString.put(sensor.getType(), "MAGNETIC_FIELD");
                        valueString.put(sensor.getType(), "X:" + values[0] + " Y:" + values[1] + " Z:" + values[2]);
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        nameString.put(sensor.getType(), "GYROSCOPE");
                        valueString.put(sensor.getType(), "X:" + values[0] + " Y:" + values[1] + " Z:" + values[2]);
                        break;
                    case Sensor.TYPE_GRAVITY:
                        nameString.put(sensor.getType(), "GRAVITY");
                        valueString.put(sensor.getType(), "" + values[0]);
                        break;
                    default:
                        break;
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
        sensordata.setValue(String.valueOf(value));

        new InsertSensorDataAsyncTask().execute(new Pair<TextView, SensorData>(uploadStatus, sensordata));
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
            updateNameString(sensor.getType());

            String s = nameString.get(sensor.getType());
            String outStr = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

            uploadStatus.setText(uploadStatus.getText() + "\n" + outStr + " sensor data uploading...");
            uploadStatus.setHorizontallyScrolling(true);
            uploadStatus.setAllCaps(false);

            //uploadStatus.setText(uploadStatus.getText() + "\n" + "Uploading: " + nameString.get(sensor.getType()));
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(sensor.getType()), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void updateNameString(int type) {
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER:
                nameString.put(type, "ACCELEROMETER");
                break;
            case Sensor.TYPE_LIGHT:
                nameString.put(type, "LIGHT");
                break;
            case Sensor.TYPE_PRESSURE:
                nameString.put(type, "PRESSURE");
                break;
            case Sensor.TYPE_PROXIMITY:
                nameString.put(type, "PROXIMITY");
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                nameString.put(type, "MAGNETIC_FIELD");
                break;
            case Sensor.TYPE_GYROSCOPE:
                nameString.put(type, "GYROSCOPE");
                break;
            case Sensor.TYPE_GRAVITY:
                nameString.put(type, "GRAVITY");
                break;
            default:
                break;
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
                    if (threadRunning) {

                        uploadSensorData();
                        // sleep 30 seconds to slow down the add of entries
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        };
    }

    private void uploadSensorData() {
        for (Sensor sensor : registeredSensors) {
            final String name = nameString.get(sensor.getType());
            if (name != null && valueString.get(sensor.getType()) != null)
                insertSensorDataInstance(name, valueString.get(sensor.getType()));

            Log.d("Upload Data", "uploaded: " + sensor.getName());
        }
    }
}
