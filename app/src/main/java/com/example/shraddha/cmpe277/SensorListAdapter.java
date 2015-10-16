package com.example.shraddha.cmpe277;

import android.app.Activity;
import android.hardware.Sensor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shraddha on 10/14/15.
 */
public class SensorListAdapter extends ArrayAdapter<Sensor> {

    private final Activity context;
    private List<Sensor> sensors; // = new ArrayList<Sensor>();

    public SensorListAdapter(Activity context, List<Sensor> sensors) {
        super(context, R.layout.sensorlist, sensors);

        this.sensors = sensors;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.sensorlist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.itemname);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extract = (TextView) rowView.findViewById(R.id.desc);

        Sensor sensor = sensors.get(position);
        txtTitle.setText(sensor.getName());
        imageView.setImageResource(R.drawable.pressure);
        extract.setText("Description: " + sensor.getPower());
        return rowView;

    }

    @NonNull
    private CompoundButton.OnCheckedChangeListener getOnChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                } else {
                }
            }
        };
    }
}
