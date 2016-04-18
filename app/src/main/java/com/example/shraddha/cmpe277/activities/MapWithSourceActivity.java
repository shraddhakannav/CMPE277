package com.example.shraddha.cmpe277.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.shraddha.cmpe277.Adapters.ListViewAdapter;
import com.example.shraddha.cmpe277.DataAccessors.ParseDataAccessor;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.R;
import com.example.shraddha.cmpe277.SenseApplication;
import com.example.shraddha.cmpe277.VariableActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapWithSourceActivity extends AppCompatActivity {

  private ParseDataAccessor dataAccessor = SenseApplication.getParseDAInstance();
  private List<SensorDataSource> sensorDataSources = new ArrayList<>();
  private ListView listView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_with_source);
    sensorDataSources = dataAccessor.getCachedSourceForOneVariable();
    if (!sensorDataSources.isEmpty()) {
      listView = (ListView) findViewById(R.id.datasourcesList);
      ListViewAdapter listViewAdapter = new ListViewAdapter(sensorDataSources, this);
      listView.setAdapter(listViewAdapter);
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
          Intent intent = new Intent(MapWithSourceActivity.this, VariableActivity.class);
          //intent.putExtra("something", "something");
          //Bundle bundle = new Bundle();
          //bundle.putSerializable("SensorSourceDate", sensorDataSources.get(position));
          intent.putExtra("object", sensorDataSources.get(position));
          startActivity(intent);
        }
      });
    }

    //convertView.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //    Intent intent = new Intent(activity, VariableActivity.class);
    //    intent.putExtra("something", "something");
    //    //Bundle bundle = new Bundle();
    //    //bundle.putSerializable("SensorSourceDate", source);
    //    //intent.putExtras(bundle);
    //    activity.startActivity(intent);
    //  }
    //});

    initializeMaps();
  }

  private void initializeMaps() {
    MapFragment mapFragment =
        (MapFragment) getFragmentManager().findFragmentById(R.id.datasourcesMap);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(GoogleMap googleMap) {
        //LatLngBounds latLngBounds =
        //    new LatLngBounds(new LatLng(37.7749, 122.4194), new LatLng(37.836213, -123.254866));
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 6));
        for (SensorDataSource source : sensorDataSources) {
          googleMap.addMarker(new MarkerOptions().position(
              new LatLng(source.getMinLatitude(), source.getMinLongitude()))
              .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_25))
              .title(source.getSourceId())
              .snippet(source.getInstitution()));
        }
      }
    });
  }
}
