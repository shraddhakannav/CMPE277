package com.example.shraddha.cmpe277;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

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
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    List<ParseObject> sensors = new ArrayList<ParseObject>();
    List<Double> latitudes = new ArrayList<Double>();
    List<Double> longitudes = new ArrayList<Double>();
    int zoomlevel = 12;
    private GoogleMap googleMap;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getParseData("PRESSURE");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setUpMap() {
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);

        Location location = locationManager.getLastKnownLocation(provider);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        LatLng latlang = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlang));
        addMarker(latitude, longitude, R.drawable.pinkmarker);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang, zoomlevel));

        GlobalValues.setLatitude(latitude);
        GlobalValues.setLongitude(longitude);
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

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        setUpMap();

        try {
            Log.d("Sensors", sensors.toString());
            for (ParseObject data : sensors) {
                Log.d("Sensors", data.getString("sensortype"));
                googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getParseGeoPoint("location").getLatitude(), data.getParseGeoPoint("location").getLongitude())).icon(BitmapDescriptorFactory.fromResource(R.drawable.bluemarker)));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalValues.setLatitude(latitude);
        GlobalValues.setLongitude(longitude);
    }

    public void addMarker(double latitude, double longitude, int icon) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).icon(BitmapDescriptorFactory.fromResource(icon)));
    }


    public void getParseData(String sensortype) {

        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("SensorData");
            query.whereContains("sensortype", sensortype);
            if (sensortype != null && !sensortype.isEmpty())
                query.whereEqualTo("sensortype", sensortype);
            sensors = query.find();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public boolean onMarkerClick(Marker marker) {


        LatLng latLng = marker.getPosition();
        double lat = latLng.latitude;
        double longitude = latLng.longitude;
        String title = marker.getTitle();
        String snippet = marker.getSnippet();

        for (ParseObject sensor : sensors) {
            if (sensor.getParseGeoPoint("location").getLatitude() == lat && sensor.getParseGeoPoint("location").getLongitude() == longitude) {
                snippet = sensor.getString("sensortype");
                title = sensor.getString("value") + " hPA";
            }
            marker.setTitle(title);
            marker.setSnippet(snippet);
            marker.showInfoWindow();

        }
        return true;
    }
}