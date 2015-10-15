package com.example.shraddha.cmpe277;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by Shraddha on 10/14/15.
 */
public class InsertSensorDataAsyncTask extends AsyncTask<Pair<Context, SensorData>, Void, SensorData> {


    private Context context;

    @Override
    protected SensorData doInBackground(Pair<Context, SensorData>... params) {

        try {
            context = params[0].first;
            SensorData sensorData = params[0].second;

            ParseObject statusDetails = new ParseObject("SensorData");
            statusDetails.put("sensortype", sensorData.getSensortype());
            statusDetails.put("location", new ParseGeoPoint(sensorData.getLatitude(), sensorData.getLongitude()));
            statusDetails.put("value", sensorData.getValue());

            statusDetails.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG);
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

        Toast.makeText(context, sensorData.getSensortype(), Toast.LENGTH_SHORT).show();
    }
}

