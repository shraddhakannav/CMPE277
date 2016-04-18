package com.example.shraddha.cmpe277;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.shraddha.cmpe277.Adapters.SesnorDataExpandableListAdapter;
import com.example.shraddha.cmpe277.Charts.DependencyChartActivity;
import com.example.shraddha.cmpe277.ModelObjects.SensorData;
import com.example.shraddha.cmpe277.RESTApi.RemoteFetch;
import com.example.shraddha.cmpe277.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SensorDataActivity extends AppCompatActivity {

    public static OnJSONResponseCallback callback;
    ExpandableListView expListView;
    SesnorDataExpandableListAdapter listAdapter;
    JSONObject allData;
    ProgressDialog progress;

    private List<String> listDataHeader;
    private HashMap<String, List<com.example.shraddha.cmpe277.ModelObjects.SensorData>> listDataChild;
    private String dataset;
    private String variable;
    private String startdate;
    private String enddate;

    /**
     * Requesting Cloud Server for the Data from date till no of days of Sensor variable from Source dataset.
     */
    private JSONObject getJsonData(String dataset, String variable, String date, String enddate) {
        //http://192.168.1.138:5858/trust/ds/mlml_mlml_sea/sensor/sea_water_temperature/startdate/2016-04-04T23:36:00Z/7
        try {
            JSONObject jsonData = RemoteFetch.getTrustForData(dataset, variable, date, enddate);
            System.out.println("The trust values are : " + jsonData);
            showProgressDialog();
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            dismissDialog();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        dataset = getIntent().getStringExtra("dataset");
        variable = getIntent().getStringExtra("variable");
        startdate = getIntent().getStringExtra("startdate");
        enddate = getIntent().getStringExtra("enddate");

        progress = new ProgressDialog(this);

        getDataFromServer();
        //setExapndableListView();
    }

    private void getDataFromServer() {

        callback = new OnJSONResponseCallback() {
            @Override
            public void onJSONResponse(boolean success, JSONObject response) {
                if (success) {
                    allData = response;
                    //prepareListData();
                    setExapndableListView();
                    dismissDialog();
                } else {
                    System.out.println(response);
                    Log.d("No Data Available", "No data available");
                    dismissDialog();
                    Toast.makeText(SensorDataActivity.this, "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }
        };

        getJsonData(dataset, variable, startdate, enddate);
    }

    private void setExapndableListView() {
        expListView = (ExpandableListView) findViewById(R.id.lvExpVariable);

        // preparing list data
        prepareListData();
        listAdapter = new SesnorDataExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                // CAll parse for data sets containing this variable
                String selectedGroup = listDataHeader.get(groupPosition);

                ArrayList<SensorData> listToPlot = (ArrayList<SensorData>) listDataChild.get(selectedGroup);

                SensorData selectedValue =
                        listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);

                //Intent intent = new Intent(SensorDataActivity.this, ChartActivity.class);
                Intent intent = new Intent(SensorDataActivity.this, DependencyChartActivity.class);
                intent.putParcelableArrayListExtra("ListOfData", listToPlot);

                startActivity(intent);

                System.out.println("The selected variable is: " + selectedValue);
                return false;
            }
        });

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showProgressDialog() {

        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.show();
    }

    private void dismissDialog() {
        progress.dismiss();
    }

    private void prepareListData() {
        Constants.setCategoriesToVariables();

        try {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<SensorData>>();

            JSONObject trust = (JSONObject) allData.get("trust");
            Iterator keysToCopyIterator = trust.keys();
            while (keysToCopyIterator.hasNext()) {
                String key = (String) keysToCopyIterator.next();
                listDataHeader.add(key);
                List<SensorData> arrayList = new ArrayList<SensorData>();

                JSONArray array = trust.getJSONArray(key);
                for (int index = 0; index < array.length(); index++) {
                    SensorData data = new SensorData();
                    JSONObject currentValue = (JSONObject) array.get(index);
                    data.setX(currentValue.getDouble("x"));
                    data.setTrustValue(currentValue.getDouble("trustValue"));
                    data.setTime(currentValue.getString("time"));
                    arrayList.add(data);
                }
                listDataChild.put(key, arrayList);
            }

            Constants.setDataForVariable(listDataChild);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface OnJSONResponseCallback {
        void onJSONResponse(boolean success, JSONObject response);
    }

}
