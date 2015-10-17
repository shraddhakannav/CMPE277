package com.example.shraddha.cmpe277;

import android.app.Activity;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Shraddha on 10/14/15.
 */
public class SensorListAdapter extends ArrayAdapter<Sensor> {

    public final static String STORE_PREFERENCES = "SenseProfile.txt";
    private final Activity context;
    private List<Sensor> sensors;
    private List<String> nameList;
    private List<Integer> imageList;
    private ParseObject parse;


    public SensorListAdapter(Activity context, List<Sensor> sensors, List<String> nameList, List<Integer> imageList) {
        super(context, R.layout.sensorlist, sensors);
        this.sensors = sensors;
        this.nameList = nameList;
        this.imageList = imageList;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.sensorlist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.itemname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extract = (TextView) rowView.findViewById(R.id.desc);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);

        checkBox.setOnCheckedChangeListener(getOnChangeListener(sensors.get(position)));

        Sensor sensor = sensors.get(position);


        if (sensorExists(sensor)) {
            checkBox.setChecked(true);
            checkBox.setText("Unregister");
        } else {
            checkBox.setChecked(false);
            checkBox.setText("Register");
        }

        txtTitle.setText(nameList.get(position));
        imageView.setImageResource(imageList.get(position));
        extract.setText("Description: " + sensor.getName());
        return rowView;

    }

    @NonNull
    private CompoundButton.OnCheckedChangeListener getOnChangeListener(final Sensor sensor) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    registerSensor(sensor);
                    buttonView.setText("Unregister");
                } else {
                    unregisterSensor(sensor);
                    buttonView.setText("Register");
                }
            }
        };
    }

    private void unregisterSensor(Sensor sensor) {

        Log.d("UnRegister a Button", " Registration of sensor data in cloud");

        if (sensor != null) {
            try {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(sensor.getName());
                editor.commit();

                Log.d("Sensor registration", "Saved the sensor in parse table");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    public boolean sensorExists(Sensor sensor) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
            int name = sharedPreferences.getInt(sensor.getName(), -1);
            return name != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registerSensor(Sensor sensor) {

        Log.d("Register a Button", " Registration of sensor data in cloud");

        if (sensor != null) {
            try {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(sensor.getName(), sensor.getType());
                editor.commit();

                Log.d("Sensor registration", "Saved the sensor in parse table");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}
