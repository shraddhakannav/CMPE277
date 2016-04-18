package com.example.shraddha.cmpe277.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.shraddha.cmpe277.Adapters.CustomExpandableListAdapter;
import com.example.shraddha.cmpe277.DataAccessors.ParseDataAccessor;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.R;
import com.example.shraddha.cmpe277.SenseApplication;
import com.example.shraddha.cmpe277.Utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListActivity extends AppCompatActivity {

  ExpandableListAdapter listAdapter;
  ExpandableListView expListView;
  List<String> listDataHeader;
  HashMap<String, List<String>> listDataChild;
  ArrayList<SensorDataSource> dataSourcesForVariable = new ArrayList<>();
  ParseDataAccessor dataAccessor = SenseApplication.getParseDAInstance();
  private ProgressDialog progress;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_expandable_list);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    //fab.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View view) {
    //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
    //        .setAction("Action", null)
    //        .show();
    //  }
    //});

    progress = new ProgressDialog(this);
    expListView = (ExpandableListView) findViewById(R.id.lvExp);

    // preparing list data
    prepareListData();
    listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild);

    // setting list adapter
    expListView.setAdapter(listAdapter);

    expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

      @Override public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
          int childPosition, long id) {
        // CAll parse for data sets containing this variable
        showProgressDialog();
        String selectedGroup = listDataHeader.get(groupPosition);
        String selectedValue =
            listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
        dataAccessor.getSourceForVariable(selectedValue);
        if (dataAccessor.getCachedSourceForOneVariable() != null
            && dataAccessor.getCachedSourceForOneVariable().size() > 0) {
          Intent intent = new Intent(ExpandableListActivity.this, MapWithSourceActivity.class);
          intent.putExtra("VARIABLE", selectedValue);
          startActivity(intent);
          dismissDialog();
        } else {
          dismissDialog();
          Toast.makeText(ExpandableListActivity.this,
              "No matching datasets found, please select another variable", Toast.LENGTH_SHORT)
              .show();
        }

        return false;
      }
    });

    expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

      @Override public void onGroupExpand(int groupPosition) {
        //Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Expanded",
        //    Toast.LENGTH_SHORT).show();
      }
    });

    expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

      @Override public void onGroupCollapse(int groupPosition) {
        //Toast.makeText(getApplicationContext(), listDataHeader.get(groupPosition) + " Collapsed",
        //    Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void prepareListData() {
    Constants.setCategoriesToVariables();
    listDataHeader = new ArrayList<String>();
    listDataHeader.addAll(Constants.categoriesToVariables.keySet());
    listDataChild = Constants.categoriesToVariables;
  }

  private void showProgressDialog() {
    progress.setTitle("Loading");
    progress.setMessage("Wait while loading...");
    progress.show();
  }

  private void dismissDialog() {
    progress.dismiss();
  }
}
