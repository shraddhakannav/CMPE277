package com.example.shraddha.cmpe277;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Shraddha on 10/14/15.
 */
public class InsertSensorDataAsyncTask extends AsyncTask<Pair<TextView, SensorData>, Void, SensorData> {


    private TextView uploadedStatus;
    private SensorData sensorData;

    @Override
    protected SensorData doInBackground(Pair<TextView, SensorData>... params) {

        try {
            uploadedStatus = params[0].first;
            sensorData = params[0].second;

            ParseObject statusDetails = new ParseObject("SensorData");
            statusDetails.put("sensortype", sensorData.getSensortype());
            statusDetails.put("location", new ParseGeoPoint(sensorData.getLatitude(), sensorData.getLongitude()));
            statusDetails.put("value", sensorData.getValue());

            statusDetails.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        // Saved successfully.
                        Log.d("Upload Data", "uploaded successfully ");
                        //Toast.makeText(context, "Saved Sensor Data", Toast.LENGTH_SHORT).show();
                    } else {
                        // The save failed.
                        //Toast.makeText(context, "Failed to Save", Toast.LENGTH_SHORT).show();
                        Log.d(getClass().getSimpleName(), "User update error: " + e);
                        e.printStackTrace();
                    }
                }
            });

            return sensorData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(SensorData sensorData) {
        super.onPostExecute(sensorData);

    }
}

