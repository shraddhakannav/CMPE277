package com.example.shraddha.cmpe277;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class Dashboard extends AppCompatActivity implements SensorEventListener {

    TextView mTempSensorValue;
    TextView mPresSensorValue;
    DecimalFormat decimalFormat = new DecimalFormat("####.##");
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
    private float mSensorValues[] = {0, 0, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        final ImageAdapter imageAdapter = new ImageAdapter(this);
        gridview.setAdapter(imageAdapter);

//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(Dashboard.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        getListofSensors();
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Sensor sensor = event.sensor;
                if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    mSensorValues[0] = event.values[0];
                    mTempTimeStamp = event.timestamp;
                    imageAdapter.notifyDataSetChanged();


                } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    mSensorValues[1] = Float.valueOf(decimalFormat.format(event.values[0]));
                    mPressureTimeStamp = event.timestamp;
                    System.out.println("pressure value" + mPressureValue);
                    imageAdapter.notifyDataSetChanged();

                } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
                    mSensorValues[2] = event.values[0];
                    mHumidityTimeStamp = event.timestamp;
                    imageAdapter.notifyDataSetChanged();
                } else if (sensor.getType() == Sensor.TYPE_LIGHT) {
                    mSensorValues[3] = event.values[0];
                    mLightTimeStamp = event.timestamp;
                    System.out.println("Illumination" + mLightValue);
                    imageAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void getListofSensors() {
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : deviceSensors) {
            System.out.println(s.getName());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);

    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private int[] gridColors = {
                R.color.green,
                R.color.orange,
                R.color.blue,
                R.color.red
        };
        private String gridSensorLabels[] = {
                "Temperature", "pressure", "Humidity", "Light"
        };
        private String gridSensorUnits[] = {
                "°C", "hPA", "%", "lx"
        };

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            // return mThumbIds.length;
            return mSensorValues.length;
        }

        // references to our images
        //private Integer[] mThumbIds = {
        //        R.drawable.temp,
        //        R.drawable.light,
        //        R.drawable.pressure,
        //        R.drawable.magnetic,
        //};

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(mContext);
            }

            // get layout from grid_item.xml ( Defined Below )

            gridView = inflater.inflate(R.layout.custom_grid_item, null);

            RelativeLayout relativeLayout =
                    (RelativeLayout) gridView.findViewById(R.id.custom_grid_item_table);
            relativeLayout.setLayoutParams(new GridView.LayoutParams(365, 365));
            // textView.setsc(textView.ScaleType.CENTER_CROP);
            relativeLayout.setPadding(8, 8, 8, 8);
            relativeLayout.setBackgroundColor(getResources().getColor(gridColors[position]));


            TextView sensorLabel = (TextView) gridView.findViewById(R.id.SensorLabel);
            sensorLabel.setText(gridSensorLabels[position]);

            TextView sensorValue = (TextView) gridView.findViewById(R.id.SensorValue);
            sensorValue.setText(Float.toString(mSensorValues[position]));

            TextView unit = (TextView) gridView.findViewById(R.id.Unit);
            unit.setText(gridSensorUnits[position]);

            return gridView;
        }
    }
}
