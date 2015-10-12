package com.example.shraddha.cmpe277;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Priya on 10/11/2015.
 */
public class UserDisplayList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewsample);

        ListView list = (ListView) findViewById(R.id.list);
        ArrayList<Integer> images = new ArrayList<Integer>();
        ArrayList<String> texts = new ArrayList<String>();
        texts.add("Sensor Registration");
        texts.add("Dashboard");
        texts.add("Maps");
        texts.add("Reports");
        images.add(R.drawable.light);
        images.add(R.drawable.magnetic);
        images.add(R.drawable.pressure);
        images.add(R.drawable.temp);

        CustomAdapter adapter = new CustomAdapter(this,images,texts);
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

                Intent i = new Intent(UserDisplayList.this, SensorRegistration.class);

                startActivity(i);
            }
            if (position == 1) {
                Intent i = new Intent(UserDisplayList.this, Dashboard.class);

                startActivity(i);
            }
            if (position == 2) {
                Intent i = new Intent(UserDisplayList.this, Maps.class);

                startActivity(i);

            }
            if (position == 3) {
                Intent i = new Intent(UserDisplayList.this, Report.class);

                startActivity(i);

            }
        }
    }

}