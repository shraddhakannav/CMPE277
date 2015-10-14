package com.example.shraddha.cmpe277;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Dashboard extends AppCompatActivity implements SensorEventListener {

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
        setContentView(R.layout.dashboard);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

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



    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView = new ImageView(mContext);;
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setBackgroundColor(Color.CYAN);
            imageView.setImageResource(mThumbIds[position]);

            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.temp,
                R.drawable.light,
                R.drawable.pressure,
                R.drawable.magnetic,
        };
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
