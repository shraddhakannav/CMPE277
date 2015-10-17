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
        SensorListAdapter adapter = new SensorListAdapter(this, sensors);
        list = (ListView) findViewById(R.id.sensorslist);
        list.setAdapter(adapter);

//        list.setOnItemClickListener(getItemClickListener());

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
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : sensors) {
            itemname.add(s.getName());
            imgid.add(R.drawable.light);
            System.out.println(s.getName());
        }
        return sensors;
    }


    public void submitRegistration(View view) {
        Toast.makeText(this, "Successful registration", Toast.LENGTH_LONG);
    }


}
