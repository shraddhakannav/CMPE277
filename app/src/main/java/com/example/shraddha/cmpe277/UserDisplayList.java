package com.example.shraddha.cmpe277;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserDisplayList extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listviewsample);
    initializeUI();
  }

  private void initializeUI() {

    ListView list = (ListView) findViewById(R.id.list);
    ArrayList<Integer> images = new ArrayList<Integer>();
    ArrayList<String> texts = new ArrayList<String>();

    texts.add("Dashboard");
    // texts.add("Maps");
    texts.add("Register Sensors");
    texts.add("Upload Data");
    // texts.add("Filter");
    images.add(R.drawable.dashboard);
    // images.add(R.drawable.maps);
    images.add(R.drawable.sensorregistration);
    images.add(R.drawable.reports);

    CustomAdapter adapter = new CustomAdapter(this, images, texts);
    list.setAdapter(adapter);
    list.setOnItemClickListener(new ListClickHandler());
  }

  public class ListClickHandler implements AdapterView.OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
      // TODO Auto-generated method stub
      TextView listText = (TextView) view.findViewById(R.id.info_text);
      String text = listText.getText().toString();

      if (position == 0) {
        Intent i = new Intent(UserDisplayList.this, Dashboard.class);
        startActivity(i);
      }
      //if (position == 1) {
      //  Intent i = new Intent(UserDisplayList.this, MapsActivity.class);
      //  startActivity(i);
      //}
      if (position == 1) {

        Intent i = new Intent(UserDisplayList.this, SensorRegistration.class);
        startActivity(i);
      }
      if (position == 2) {
        Intent i = new Intent(UserDisplayList.this, SensorActivity.class);
        startActivity(i);
      }
      //if (position == 4) {
      //  Intent i = new Intent(UserDisplayList.this, ExpandableListActivity.class);
      //  startActivity(i);
      //}
    }
  }
}