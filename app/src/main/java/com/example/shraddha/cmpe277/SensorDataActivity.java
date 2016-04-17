package com.example.shraddha.cmpe277;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.shraddha.cmpe277.Adapters.CustomExpandableListAdapter;
import com.example.shraddha.cmpe277.RESTApi.RemoteFetch;
import com.example.shraddha.cmpe277.Utils.Constants;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SensorDataActivity extends AppCompatActivity {

    public static OnJSONResponseCallback callback;
    ExpandableListView expListView;
    CustomExpandableListAdapter listAdapter;
    JSONObject allData;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    /**
     * Requesting Cloud Server for the Data from date till no of days of Sensor variable from Source dataset.
     */
    private static JSONObject getJsonData(String dataset, String variable, String date, int days) {
        //http://192.168.1.138:5858/trust/ds/mlml_mlml_sea/sensor/sea_water_temperature/startdate/2016-04-04T23:36:00Z/7
        try {
            JSONObject jsonData = RemoteFetch.getTrustForData(dataset, variable, date, days);
            System.out.println("The trust values are : " + jsonData);
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);

        getDataFromServer();
        setExapndableListView();
    }

    private void getDataFromServer() {

        callback = new OnJSONResponseCallback() {
            @Override
            public void onJSONResponse(boolean success, JSONObject response) {
                if (success) {
                    allData = response;
                    prepareListData();
//                    expListView.
                } else
                    System.out.println(response);
            }
        };

        getJsonData("mlml_mlml_sea", "sea_water_temperature", "2016-04-13T23:36:00Z", 2);
    }

    private void setExapndableListView() {
        expListView = (ExpandableListView) findViewById(R.id.lvExpVariable);

        // preparing list data
        prepareListData();
        listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                // CAll parse for data sets containing this variable
                String selectedGroup = listDataHeader.get(groupPosition);
                String selectedValue =
                        listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
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

    private void prepareListData() {
        Constants.setCategoriesToVariables();

        try {
            listDataHeader = new ArrayList<String>();

            for (int index = 0; index < allData.getJSONArray("trust").length(); index++) {

            }

            listDataHeader.add("Something");
            listDataChild = new HashMap<String, List<String>>();
            List<String> arrayList = new ArrayList<String>();
            arrayList.add("Temperature");
            arrayList.add("Water");
            arrayList.add("Pressure");
            arrayList.add("Temperature");
            arrayList.add("Water");
            arrayList.add("Pressure");
            arrayList.add("Temperature");
            arrayList.add("Water");
            arrayList.add("Pressure");
            arrayList.add("Temperature");
            arrayList.add("Water");
            arrayList.add("Pressure");
            listDataChild.put("Something", arrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public interface OnJSONResponseCallback {
        void onJSONResponse(boolean success, JSONObject response);
    }

}
