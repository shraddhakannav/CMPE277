package com.example.shraddha.cmpe277;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.shraddha.cmpe277.Adapters.CustomExpandableListAdapter;
import com.example.shraddha.cmpe277.Utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class VariableActivity extends AppCompatActivity {

    static final int DATE_PICKER_ID = 1111;
    Button buttonStartTime;
    Button buttonEndTime;
    ExpandableListView expListView;
    CustomExpandableListAdapter listAdapter;
    String VARIABLE_GROUP_NAME = "Sensors";
    private int year;
    private int month;
    private int day;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variable);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        buttonStartTime = (Button) findViewById(R.id.buttonStartTime);
        buttonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(VariableActivity.this,
                        new mDateSetListener(buttonStartTime), year, month, day);
                //TODO : Set the minimum maximum date you can select
//                dialog.getDatePicker().setMaxDate(LongValue);
//                dialog.getDatePicker().setMinDate(LongValue);
                dialog.show();
            }
        });

        buttonEndTime = (Button) findViewById(R.id.buttonEndTime);
        buttonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(VariableActivity.this,
                        new mDateSetListener(buttonEndTime), year, month, day);
                //TODO : Set the minimum maximum date you can select
//                dialog.getDatePicker().setMaxDate(LongValue);
//                dialog.getDatePicker().setMinDate(LongValue);
                dialog.show();
            }
        });

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
        startActivity(intent);
    }

    private void prepareListData() {
        Constants.setCategoriesToVariables();
        listDataHeader = new ArrayList<String>();
        listDataHeader.add(VARIABLE_GROUP_NAME);
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
        listDataChild.put(VARIABLE_GROUP_NAME, arrayList);

    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        Button v;

        public mDateSetListener(Button button) {
            v = button;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            v.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("/").append(mDay).append("/")
                    .append(mYear).append(" "));
            System.out.println(v.getText().toString());


        }
    }

}
