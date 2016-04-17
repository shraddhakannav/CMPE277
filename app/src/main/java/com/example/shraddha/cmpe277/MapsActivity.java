package com.example.shraddha.cmpe277;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.shraddha.cmpe277.Gson.Institution;
import com.example.shraddha.cmpe277.ModelObjects.DataResult;
import com.example.shraddha.cmpe277.ModelObjects.SensorCategory;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.RESTApi.RemoteFetch;
import com.example.shraddha.cmpe277.RESTApi.RestHelper;
import com.example.shraddha.cmpe277.Utils.Constants;
import com.example.shraddha.cmpe277.Utils.CustomFonts;
import com.example.shraddha.cmpe277.activities.MapDetailsTab;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity
    implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

  List<ParseObject> sensors = new ArrayList<ParseObject>();
  List<Double> latitudes = new ArrayList<Double>();
  List<Double> longitudes = new ArrayList<Double>();
  int zoomlevel = 12;
  private GoogleMap googleMap;
  private double latitude;
  private double longitude;
  private TextView filterButton, refreshButton;
  private LinearLayout filterOptionsContainer;
  private Spinner sensorTypeSpinner, sensorSourceSpinner;
  private Animation slideDown, slideUp;
  private boolean isFilterOptionsShowing = false;
  private ArrayList<SensorDataSource> sensorDataSources;
  private ArrayList<SensorCategory> sensorCategories;
  private ArrayList sensorsourcesFromRest;
  private String selectedSensorCategory = Constants.ALL_SENSORS;
  private String selectedSensorSource = Constants.ALL_SOURCES;
  private ArrayList<SensorDataSource> resultSourcesFromFilter;
  private Location userLocation;
  private boolean isMarkerPresent = false;
  ProgressDialog progress;
  protected HashMap<Marker, SensorDataSource> allMarkerInfo = new HashMap<>();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    sensorCategories = SenseApplication.getParseDAInstance().getCachedCategories();
    sensorsourcesFromRest = SenseApplication.getRestHelperInstance().allInstitutions;
    progress = new ProgressDialog(this);
    initializeUI();
    addSensorCategoriesToSpinner();
    addSensorSourcesToSpinner();
    initializeAnimation();
  }

  private void initializeUI() {
    MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    filterButton = (TextView) findViewById(R.id.filter_button);
    refreshButton = (TextView) findViewById(R.id.refresh_button);

    filterButton.setTypeface(CustomFonts.setCaviarBold(this));
    refreshButton.setTypeface(CustomFonts.setCaviarBold(this));

    filterOptionsContainer = (LinearLayout) findViewById(R.id.filter_options_container);

    filterButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        openFilterOptions();
      }
    });

    refreshButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        closeFilterOptions();
        getDataForMap();
      }
    });
    sensorTypeSpinner = (Spinner) findViewById(R.id.sensor_type_spinner);
    sensorSourceSpinner = (Spinner) findViewById(R.id.sensor_source_spinner);
  }

  private void addSensorSourcesToSpinner() {

    sensorTypeSpinner = (Spinner) findViewById(R.id.sensor_type_spinner);
    List<String> list = new ArrayList<String>();
    list.add("All Sources");

    if (sensorsourcesFromRest != null && !sensorsourcesFromRest.isEmpty()) {
      list.addAll(sensorsourcesFromRest);
    }
    ArrayAdapter<String> dataAdapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sensorSourceSpinner.setAdapter(dataAdapter);
    sensorSourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(
            "Item selected is" + sensorSourceSpinner.getItemAtPosition(position).toString());
        selectedSensorSource = sensorSourceSpinner.getItemAtPosition(position).toString();
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  private void addSensorCategoriesToSpinner() {

    List<String> categoryLabel = new ArrayList<String>();
    categoryLabel.add("All Sensors");
    if (sensorCategories != null) {
      for (SensorCategory category : sensorCategories) {
        categoryLabel.add(category.getCategoryName());
      }
    }
    ArrayAdapter<String> dataAdapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryLabel);
    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sensorTypeSpinner.setAdapter(dataAdapter);
    sensorTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(
            "Item selected is   :" + sensorTypeSpinner.getItemAtPosition(position).toString());
        selectedSensorCategory = sensorTypeSpinner.getItemAtPosition(position).toString();
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  private void setUpMap() {
    googleMap.setMyLocationEnabled(true);
    googleMap.setOnMarkerClickListener(this);
    googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
      @Override public void onInfoWindowClick(Marker marker) {
        String variables = "wind_speed,wind_speed_of_gust,wind_from_direction,depth";
        //allMarkerInfo.get(marker).getVariables();
        //time>=2013-09-05T00:00:00Z&time<=2013-09-12T13:50:00Z
        if (variables != null && variables.length() > 0) {
          getOneWeekDataForVariables("edu_ucsc_lml", variables, "2013-09-12T13:50:00Z",
              "2013-09-05T00:00:00Z");
          //RestHelper.getInstance()
          //    .getOneWeekDataForVariables(variables, "edu_ucsc_lml", "2013-09-12T13:50:00Z",
          //        "2013-09-05T00:00:00Z");
        } //openTabbedActivity();
      }
    });

    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    LocationListener locationListener = new LocationListener() {
      public void onLocationChanged(Location location) {
        // Called when a new location is found by the network location provider.
        userLocation = location;
      }

      public void onStatusChanged(String provider, int status, Bundle extras) {
      }

      public void onProviderEnabled(String provider) {
      }

      public void onProviderDisabled(String provider) {
      }
    };

    // Register the listener with the Location Manager to receive location updates
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    String locationProvider = LocationManager.GPS_PROVIDER;
    userLocation = locationManager.getLastKnownLocation(locationProvider);

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

    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    if (userLocation != null) {
      latitude = userLocation.getLatitude();
      longitude = userLocation.getLongitude();
    }

    LatLng latlang = new LatLng(latitude, longitude);
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
    addMarker(latitude, longitude, R.drawable.pinkmarker);
    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang, zoomlevel));

    GlobalValues.setLatitude(latitude);
    GlobalValues.setLongitude(longitude);
  }

  private void getOneWeekDataForVariables(final String datasetId, final String variables,
      final String currentDate, final String lastweekday) {
    AsyncTask<String, Integer, DataResult> fetchOneWeekData =
        new AsyncTask<String, Integer, DataResult>() {
          @Override protected DataResult doInBackground(String... params) {
            DataResult fetchedData =
                RemoteFetch.getOneWeekData(datasetId, variables, currentDate, lastweekday);
            return fetchedData;
          }

          @Override protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
          }

          @Override protected void onPostExecute(DataResult result) {
            super.onPostExecute(result);
            //Log.d("Printing all values__", result.toString());
            dismissDialog();
          }
        };
    fetchOneWeekData.execute(datasetId, variables, currentDate, lastweekday);
  }

  private void showProgressDialog() {

    progress.setTitle("Loading");
    progress.setMessage("Wait while loading...");
    progress.show();
  }

  private void dismissDialog() {
    progress.dismiss();
  }

  public GoogleMap getGoogleMap() {
    return googleMap;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  @Override public void onMapReady(GoogleMap map) {
    googleMap = map;
    setUpMap();
    getDataForMap();

    GlobalValues.setLatitude(latitude);
    GlobalValues.setLongitude(longitude);
  }

  public void addMarker(double latitude, double longitude, int icon) {
    googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
        .icon(BitmapDescriptorFactory.fromResource(icon)));
  }

  public List<ParseObject> getNearbyLocation(ParseGeoPoint location, int kilometers) {
    ParseQuery query = new ParseQuery("PlaceObject");
    query.whereNear("location", location);

    try {
      return query.find();
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override public boolean onMarkerClick(Marker marker) {
    LatLng latLng = marker.getPosition();
    double lat = latLng.latitude;
    double longitude = latLng.longitude;
    String title = marker.getTitle();
    String snippet = marker.getSnippet();

    if (!marker.isInfoWindowShown()) {
      marker.showInfoWindow();
    } else {
      marker.hideInfoWindow();
    }
    return true;
  }

  private void openTabbedActivity() {
    Intent intent = new Intent(this, MapDetailsTab.class);
    startActivity(intent);
  }

  private void openFilterOptions() {
    if (!isFilterOptionsShowing) {
      filterOptionsContainer.setVisibility(View.VISIBLE);
      filterOptionsContainer.startAnimation(slideDown);
      isFilterOptionsShowing = true;
    }
  }

  private void closeFilterOptions() {
    if (isFilterOptionsShowing) {
      filterOptionsContainer.setVisibility(View.GONE);
      filterOptionsContainer.startAnimation(slideUp);
      isFilterOptionsShowing = false;
    }
  }

  private void initializeAnimation() {
    slideDown = AnimationUtils.loadAnimation(this, R.anim.abc_slide_in_top);
    slideUp = AnimationUtils.loadAnimation(this, R.anim.abc_slide_out_top);
  }

  public void getDataForMap() {
    GetDataForMapTask task = new GetDataForMapTask();
    task.execute(selectedSensorSource, selectedSensorCategory);
  }

  class GetDataForMapTask extends AsyncTask<String, Integer, List<SensorDataSource>> {

    @Override protected List<SensorDataSource> doInBackground(String... params) {

      System.out.print("In do in background method");
      List<SensorDataSource> sourcesFromParse = SenseApplication.getParseDAInstance()
          .getSensorLocations(selectedSensorSource, selectedSensorCategory);
      return sourcesFromParse;
    }

    @Override protected void onPostExecute(List<SensorDataSource> sensorDataSources) {
      super.onPostExecute(sensorDataSources);
      System.out.print("IN Post execute");
      try {

        if (googleMap != null) {
          if (isMarkerPresent) {
            googleMap.clear();
          }
          if (sensorDataSources != null) {
            for (SensorDataSource data : sensorDataSources) {

              Marker marker = googleMap.addMarker(
                  new MarkerOptions().position(new LatLng(data.getLatitude(), data.getLongitude()))
                      .icon(BitmapDescriptorFactory.fromResource(R.drawable.bluemarker))
                      .title(data.getSourceId())
                      .snippet(data.getInstitution()));
              allMarkerInfo.put(marker, data);
              isMarkerPresent = true;
            }
          }
          dismissDialog();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override protected void onPreExecute() {
      super.onPreExecute();
      showProgressDialog();
    }
  }
}