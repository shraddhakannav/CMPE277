package com.example.shraddha.cmpe277;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements SensorEventListener {

  private SensorManager mSensorManager;
  private Sensor mTemperatureSensor;
  private SensorEventListener sensorEventListener;

  ArrayList<Integer> images = new ArrayList<Integer>();
  ArrayList<String> desc = new ArrayList<String>();
  ArrayList<String> units = new ArrayList<String>();
  String sensorvalues[] = {
      "Unavailable", "Unavailable", "Unavailable", "Unavailable", "Unavailable", "Unavailable",
      "Unavailable"
  };

  private SensorData sensorChanged;
  DecimalFormat decimalFormat = new DecimalFormat("####.##");
  private String x, y, z;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dashboard);
    prepareCardView();
    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

    final ListView listView = (ListView) findViewById(R.id.sensorList);
    final DashboardAdapter dashboardAdapter =
        new DashboardAdapter(this, images, sensorvalues, units, desc);

    listView.setAdapter(dashboardAdapter);

    sensorEventListener = new SensorEventListener() {
      @Override public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        switch (sensor.getType()) {
          case Sensor.TYPE_ACCELEROMETER:

            sensorvalues[0] =
                "X:" + decimalFormat.format(event.values[0]) +"\n"+ "Y:" + decimalFormat.format(event.values[1]) + "\n"+"Z:" + decimalFormat.format(event.values[2]);

            break;
          case Sensor.TYPE_GRAVITY:
            sensorvalues[1] = decimalFormat.format((event.values[0]));
            break;

          case Sensor.TYPE_GYROSCOPE:

            sensorvalues[2] =
                "X:" + decimalFormat.format(event.values[0]) + "\n"+"Y:" + decimalFormat.format(event.values[1]) + "\n"+"Z:" + decimalFormat.format(event.values[2]);
            break;
          case Sensor.TYPE_LIGHT:

            sensorvalues[3] =(decimalFormat.format(event.values[0]));
            break;
          case Sensor.TYPE_MAGNETIC_FIELD:
            sensorvalues[4] = decimalFormat.format(event.values[0]);
            break;
          case Sensor.TYPE_PRESSURE:
            sensorvalues[5] = decimalFormat.format(event.values[0]);
            break;
          case Sensor.TYPE_PROXIMITY:
            sensorvalues[6] = decimalFormat.format(event.values[0]);
            break;
        }
        dashboardAdapter.notifyDataSetChanged();
      }

      @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

      }
    };
  }

  private void getListofSensors() {
    List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    for (Sensor s : deviceSensors) {
      System.out.println(s.getName());
      mSensorManager.registerListener(sensorEventListener,
          mSensorManager.getDefaultSensor(s.getType()), SensorManager.SENSOR_DELAY_NORMAL);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
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

  @Override public void onSensorChanged(SensorEvent event) {

  }

  @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }

  @Override public void onResume() {
    super.onResume();
    getListofSensors();
  }

  @Override public void onPause() {
    super.onPause();
    mSensorManager.unregisterListener(sensorEventListener);
  }

  public class DashboardAdapter extends ArrayAdapter {

    private final ArrayList<String> descriptions;
    private ArrayList<Integer> images;
    private String values[];
    private LayoutInflater inflater;
    private ArrayList<String> units;

    public DashboardAdapter(Context c, ArrayList<Integer> imgs, String v[], ArrayList<String> unit,
        ArrayList<String> desc) {
      super(c, R.layout.custom_grid_item, imgs);
      images = imgs;
      values = v;
      units = unit;
      descriptions = desc;
      inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
      // return mThumbIds.length;
      return values.length;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {

      View vi = convertView;
      if (convertView == null) {
        vi = inflater.inflate((R.layout.custom_grid_item), parent, false);
      }
      TextView value = (TextView) vi.findViewById(R.id.SensorValue);
      ImageView imgView = (ImageView) vi.findViewById(R.id.sensoricon);
      TextView description = (TextView) vi.findViewById(R.id.SensorLabel);
      TextView unit = (TextView) vi.findViewById(R.id.Unit);

      value.setText(values[position]);
      imgView.setImageResource(images.get(position));
      description.setText(descriptions.get(position));
      unit.setText(units.get(position));

      return vi;
    }
  }

  public void prepareCardView() {

    desc.add("Acceleration");
    desc.add("Gravity");
    desc.add("Gyroscope");
    desc.add("Light");
    desc.add("Magnetic Field");
    desc.add("Pressure");
    desc.add("Proximity");

    images.add(R.drawable.accelerometer);
    images.add(R.drawable.gravityfilled100);
    images.add(R.drawable.gyroscopefilled100);
    images.add(R.drawable.light);
    images.add(R.drawable.magnet100);
    images.add(R.drawable.pressure);
    images.add(R.drawable.proximity);

    units.add("m/s^2");
    units.add("m/s^2");
    units.add("rad/s");
    units.add("lx");
    units.add("μT");
    units.add("hPA");
    units.add("cm");
  }
}





