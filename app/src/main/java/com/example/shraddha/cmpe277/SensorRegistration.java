package com.example.shraddha.cmpe277;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorRegistration extends AppCompatActivity {

    ListView list;
    private List<Sensor> sensors = new ArrayList<Sensor>();
    private List<String> itemname = new ArrayList<String>();
    private List<Integer> imgid = new ArrayList<Integer>();
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_registration);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        getListOfAvailableSensors();
        SensorListAdapter adapter = new SensorListAdapter(this, sensors, itemname, imgid);
        list = (ListView) findViewById(R.id.sensorslist);
        list.setAdapter(adapter);

    }

    private AdapterView.OnItemClickListener getItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SensorRegistration.this, SensorDetails.class);
                intent.putExtra("sensortype", sensors.get(position).getType());
                startActivity(intent);
            }
        };
    }

    public List<Sensor> getListOfAvailableSensors() {
        List<Sensor> sensors1 = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors1) {
            switch (s.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    itemname.add("ACCELEROMETER");
                    imgid.add(R.drawable.accelerometer);//accelerometer
                    sensors.add(s);
                    break;
                case Sensor.TYPE_LIGHT:
                    itemname.add("LIGHT");
                    imgid.add(R.drawable.light);
                    sensors.add(s);
                    break;
                case Sensor.TYPE_PRESSURE:
                    itemname.add("PRESSURE");
                    imgid.add(R.drawable.pressure);
                    sensors.add(s);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    itemname.add("PROXIMITY");
                    imgid.add(R.drawable.proximity);//proximity
                    sensors.add(s);
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    itemname.add("MAGNETIC_FIELD");
                    imgid.add(R.drawable.magnet100);//magnet100
                    sensors.add(s);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    itemname.add("GYROSCOPE");
                    imgid.add(R.drawable.gyroscopefilled100);//filled100
                    sensors.add(s);
                    break;
                case Sensor.TYPE_GRAVITY:
                    itemname.add("GRAVITY");
                    imgid.add(R.drawable.gravityfilled100);//gravityfilled100
                    sensors.add(s);
                    break;
                default:
                    break;
            }

            System.out.println(s.getName());
        }
        return sensors;
    }


    public void submitRegistration(View view) {
        Toast.makeText(this, "Successful registration", Toast.LENGTH_LONG);
    }


}
