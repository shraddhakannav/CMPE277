package com.example.shraddha.cmpe277.RESTApi;

import android.util.Log;

import com.example.shraddha.cmpe277.ModelObjects.DataResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API =
            "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
    private static String ERDDAP_BASE_URL = "http://erddap.cencoos.org/erddap/tabledap/";

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
}

