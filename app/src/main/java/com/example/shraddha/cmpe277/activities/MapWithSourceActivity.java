package com.example.shraddha.cmpe277.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.shraddha.cmpe277.Adapters.ListViewAdapter;
import com.example.shraddha.cmpe277.DataAccessors.ParseDataAccessor;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.R;
import com.example.shraddha.cmpe277.SenseApplication;

import java.util.ArrayList;
import java.util.List;

public class MapWithSourceActivity extends AppCompatActivity {

    private ParseDataAccessor dataAccessor = SenseApplication.getParseDAInstance();
    private List<SensorDataSource> sensorDataSources = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_with_source);
        if (!sensorDataSources.isEmpty()) {
            sensorDataSources = dataAccessor.getCachedSourceForOneVariable();
            listView = (ListView) findViewById(R.id.datasourcesList);
            ListViewAdapter listViewAdapter = new ListViewAdapter(sensorDataSources, this);

        }
    }
}
