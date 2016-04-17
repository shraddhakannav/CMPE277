package com.example.shraddha.cmpe277.RESTApi;

import android.util.Log;

import com.example.shraddha.cmpe277.ModelObjects.DataResult;
import com.example.shraddha.cmpe277.SensorDataActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    static JSONObject obj = new JSONObject();
    private static String ERDDAP_BASE_URL = "http://erddap.cencoos.org/erddap/tabledap/";
    private static String SERVER_BASE_URL = "http://192.168.1.138:5858/";

    public static DataResult getOneWeekData(String datasetID, String variables, String currentDate,
                                            String prevWeekDay) {
        try {
            datasetID = URLEncoder.encode(datasetID, "utf-8");
            variables = URLEncoder.encode(variables, "utf-8");
            String buildQuery =
                    ERDDAP_BASE_URL + datasetID + ".json?" + variables + "&" + "time>=" + prevWeekDay + "&"
                            + "time<=" + currentDate;
            URL url = new URL(buildQuery);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null) json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            DataResult result = new DataResult();
            JSONObject tableObject = data.getJSONObject("table");
            JSONArray columnNames = tableObject.getJSONArray("columnNames");
            result.setColumnNames(columnNames);
            JSONArray columnTypes = tableObject.getJSONArray("columnTypes");
            result.setColumnDataTypes(columnTypes);
            JSONArray columnUnits = tableObject.getJSONArray("columnUnits");
            result.setUnits(columnUnits);
            JSONArray rows = tableObject.getJSONArray("rows");
            result.setRows(rows);
            for (int i = 0; i < rows.length(); i++) {
                JSONArray everyRow = rows.getJSONArray(i);
                Log.d("Every row value  ", everyRow.toString());
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getTrustForData(String datasetID, String variable, String currentDate, String enddate) {
        try {

            datasetID = URLEncoder.encode(datasetID, "utf-8");
            variable = URLEncoder.encode(variable, "utf-8");
            //http://192.168.1.138:5858/trust/ds/mlml_mlml_sea/sensor/sea_water_temperature/startdate/2016-04-04T23:36:00Z/7
            String url =
                    SERVER_BASE_URL + "trust/ds/" + datasetID + "/sensor/" + variable + "/startdate/" + currentDate + "/" + enddate;
            // URL url = new URL(buildQuery);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, null, new AsyncHttpResponseHandler() {
                // When the response returned by REST has Http response code '200'
                @Override
                public void onSuccess(String response) {
                    // Hide Progress Dialog
                    try {
                        // JSON Object
                        obj = new JSONObject(response);
                        // When the JSON response has status boolean value assigned with true
                        System.out.println("The dataset Id is: " + obj);
                        SensorDataActivity.callback.onJSONResponse(true, obj);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                    }
                }

                // When the response returned by REST has Http response code other than '200'
                @Override
                public void onFailure(int statusCode, Throwable error,
                                      String content) {
                    try {
                        // JSON Object
                        obj = new JSONObject("{ 'content': " + content + " }");
                        // When the JSON response has status boolean value assigned with true
                        System.out.println("The dataset Id is: " + obj);
                        SensorDataActivity.callback.onJSONResponse(false, obj);

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            //client.wait();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

