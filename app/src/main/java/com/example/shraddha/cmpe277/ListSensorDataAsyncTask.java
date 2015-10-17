package com.example.shraddha.cmpe277;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.GoogleMap;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shraddha on 10/16/15.
 */
public class ListSensorDataAsyncTask extends AsyncTask<Pair<GoogleMap, String>, Void, List<ParseObject>> {
    private GoogleMap googleMap;
    private List<ParseObject> list;
//    private ArrayList<SensorData> sensors;

    @Override
    protected List<ParseObject> doInBackground(Pair<GoogleMap, String>... params) {

        googleMap = params[0].first;
        String sensortype = params[0].second;
        final ArrayList<SensorData> sensors = new ArrayList<SensorData>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SensorData");
        query.whereEqualTo("sensortype", sensortype);
        try {
            list = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> list, com.parse.ParseException e) {
//                if (e == null) {
//                    for (ParseObject object : list) {
//                        SensorData sensor = new SensorData();
//                        sensor.setSensortype(object.getString("sensortype"));
//                        sensor.setLatitude(object.getParseGeoPoint("location").getLatitude());
//                        sensor.setLongitude(object.getParseGeoPoint("location").getLatitude());
//                        sensor.setValue(object.getDouble("value"));
//                        sensors.add(sensor);
//                    }
//                    Log.d("score", "Retrieved " + list.size() + " Sensors are up");
//
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
        return list;
    }

    @Override
    protected void onPostExecute(List<ParseObject> sensorDatas) {
        super.onPostExecute(sensorDatas);
        for (ParseObject data : sensorDatas) {
            Log.d("SensorData ", data.getString("sensortype"));

        }
    }
}
