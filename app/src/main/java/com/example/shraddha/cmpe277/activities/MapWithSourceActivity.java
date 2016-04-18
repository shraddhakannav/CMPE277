package com.example.shraddha.cmpe277.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
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
import com.example.shraddha.cmpe277.Utils.Constants;
import com.example.shraddha.cmpe277.VariableActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapWithSourceActivity extends AppCompatActivity {

  private ParseDataAccessor dataAccessor = SenseApplication.getParseDAInstance();
  private List<SensorDataSource> sensorDataSources = new ArrayList<>();
  private ListView listView;
  private String currentVariable;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_with_source);

    currentVariable = getIntent().getStringExtra("VARIABLE");

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
          intent.putExtra("VARIABLE", currentVariable);
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
        //Drawable mDrawable = getResources().getDrawable(R.drawable.map_pin_25);
        //mDrawable.setColorFilter(new PorterDuffColorFilter(0xffff00, PorterDuff.Mode.MULTIPLY));

        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 6));
        for (SensorDataSource source : sensorDataSources) {

          googleMap.addMarker(new MarkerOptions().position(
              new LatLng(source.getMinLatitude(), source.getMinLongitude()))
              .icon(BitmapDescriptorFactory.defaultMarker(
                  Constants.getIconColorForInstitution(source.getInstitution())))
              .title(source.getSourceId())
              .snippet(source.getInstitution()));
        }

        //GroundOverlayOptions newarkMap = new GroundOverlayOptions()
        //    .image(BitmapDescriptorFactory.fromResource(R.drawable.dashboard))
        //    .position(new LatLng(37.7749, -122.4194), 8600f, 6500f);
        //googleMap.addGroundOverlay(newarkMap);
      }
    });
  }
}
