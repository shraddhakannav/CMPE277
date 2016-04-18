package com.example.shraddha.cmpe277;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shraddha.cmpe277.Adapters.CustomExpandableListAdapter;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.Utils.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class VariableActivity extends AppCompatActivity {

    static final int DATE_PICKER_ID = 1111;
    Button buttonStartTime;
    Button buttonEndTime;

    ExpandableListView expListView;
    CustomExpandableListAdapter listAdapter;

    String VARIABLE_GROUP_NAME = "Sensors";
    private String startDate;
    private String endDate;

    private int year;
    private int month;
    private int day;

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    private TextView txtViewSourceId;
    private TextView txtViewSummary;
    private TextView txtViewInforUrl;

    private SensorDataSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable);

        try {
            Bundle bundle = getIntent().getExtras();

            source = (SensorDataSource) bundle.get("object");
            if (source != null) {
                Log.d("Institution passed", source.getInstitution());
                Log.d("Data set id ", source.getSourceId());
                Log.d("Variables", source.getVariables());
                Log.d("Summary", source.getSummary());
            }

            DateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:MM:SS'Z'", Locale.ENGLISH);
            final Date startdate = format.parse(source.getStartTime());
            final Date enddate = format.parse(source.getEndTime());

            txtViewSourceId = (TextView) findViewById(R.id.txtViewSourceId);
            txtViewSourceId.setText(source.getSourceId());

            txtViewSummary = (TextView) findViewById(R.id.txtViewSummary);
            txtViewSummary.setText(source.getSummary());

            txtViewInforUrl = (TextView) findViewById(R.id.txtViewInforUrl);
            txtViewInforUrl.setText(source.getInfoUrl());

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            buttonStartTime = (Button) findViewById(R.id.buttonStartTime);
            buttonStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog =
                            new DatePickerDialog(VariableActivity.this, new mDateSetListener(buttonStartTime), year,
                                    month, day);
                    dialog.getDatePicker().setMaxDate(enddate.getTime());
                    dialog.getDatePicker().setMinDate(startdate.getTime());
                    dialog.show();
                }
            });

            buttonEndTime = (Button) findViewById(R.id.buttonEndTime);
            buttonEndTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog dialog =
                            new DatePickerDialog(VariableActivity.this, new mDateSetListener(buttonEndTime), year,
                                    month, day);
                    dialog.getDatePicker().setMaxDate(enddate.getTime());
                    dialog.getDatePicker().setMinDate(startdate.getTime());
                    dialog.show();
                }
            });

            createExpandableListView();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void createExpandableListView() {
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

    public void seeData(View view) {
        Intent intent = new Intent(VariableActivity.this, SensorDataActivity.class);
        intent.putExtra("dataset", source.getSourceId());
//        "sea_water_temperature", "2016-04-13T23:36:00Z"
        intent.putExtra("variable", "sea_water_temperature");
        intent.putExtra("startdate", startDate);
        intent.putExtra("enddate", endDate);
        startActivity(intent);
    }

    private void prepareListData() {
        Constants.setCategoriesToVariables();
        listDataHeader = new ArrayList<String>();
        listDataHeader.add(VARIABLE_GROUP_NAME);
        listDataChild = new HashMap<String, List<String>>();
        List<String> arrayList = new ArrayList<String>();
        String[] list = source.getVariables().split(",");
        for (String string : list) {
            if (string.startsWith("[")) {
                string = string.substring(1);
            }
            arrayList.add(string);
        }
        Collections.sort(arrayList);
        listDataChild.put(VARIABLE_GROUP_NAME, arrayList);
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        Button v;

        public mDateSetListener(Button button) {
            v = button;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            String selectedDate = (new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay)).toString();

            DateFormat simpleDateFormat = Constants.getSimpleDateFormat();
            DateFormat format = Constants.getStandardDateFormat();
            DateFormat prettyFormat = Constants.getPrettyDateFormat();

            try {
                Date date = simpleDateFormat.parse(selectedDate);

                if (v.getId() == R.id.buttonStartTime) {
                    startDate = format.format(date);
                } else if (v.getId() == R.id.buttonEndTime) {
                    endDate = format.format(date);
                }

                selectedDate = prettyFormat.format(date);
                v.setText(selectedDate);
                System.out.println(v.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}
