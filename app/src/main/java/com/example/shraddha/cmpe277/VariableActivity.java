package com.example.shraddha.cmpe277;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shraddha.cmpe277.Adapters.CustomExpandableListAdapter;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.Utils.Constants;
import com.example.shraddha.cmpe277.activities.WebViewActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

  private Date startdate;
  private Date enddate;

  private double latitudeForDisplay;
  private double longitudeForDisplay;
  private AlertDialog levelDialog = null;
  private int year;
  private int month;
  private int day;
  private List<String> listDataHeader;
  private HashMap<String, List<String>> listDataChild;

  private TextView txtViewSourceId, InstitutionLabel, availableSensorText, selectedSensorValue,
      locationLabel, locationValue;
  private TextView txtViewSummary;
  private Button infoURLButton;
  private ImageView scenicImage;
  private TextView startTimeValue, endTimeValue;
  private SensorDataSource source;
  private String currentVariable;
  private DatePickerDialog dialog;
  private DatePickerDialog enddialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
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

      currentVariable = bundle.getString("VARIABLE");
      InstitutionLabel = (TextView) findViewById(R.id.txtViewInstitutionName);
      availableSensorText = (TextView) findViewById(R.id.available_sensor_list);
      selectedSensorValue = (TextView) findViewById(R.id.selectedSensorValue);
      selectedSensorValue.setText(currentVariable);
      locationLabel = (TextView) findViewById(R.id.locationLabel);
      locationValue = (TextView) findViewById(R.id.latlng_value);
      scenicImage = (ImageView) findViewById(R.id.background_image);


      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS'Z'", Locale.ENGLISH);
      startdate = format.parse(source.getStartTime());
      enddate = format.parse(source.getEndTime());

      dialog =
              new DatePickerDialog(VariableActivity.this, new mStartDateSetListener(),
                      startdate.getYear(), startdate.getMonth(), startdate.getDay());
      dialog.getDatePicker().setMaxDate(enddate.getTime());
      dialog.getDatePicker().setMinDate(startdate.getTime());

      enddialog =
              new DatePickerDialog(VariableActivity.this, new mEndDateSetListener(),
                      enddate.getYear(), enddate.getMonth(), enddate.getDay());
      enddialog.getDatePicker().setMaxDate(enddate.getTime());
      enddialog.getDatePicker().setMinDate(startdate.getTime());

      txtViewSourceId = (TextView) findViewById(R.id.txtViewSourceId);
      txtViewSourceId.setText(source.getSourceId());

      txtViewSummary = (TextView) findViewById(R.id.txtViewSummary);
      txtViewSummary.setText(source.getSummary());

      infoURLButton = (Button) findViewById(R.id.infoURLButton);

      InstitutionLabel.setText(source.getInstitution());

      latitudeForDisplay = source.getMinLatitude();
      longitudeForDisplay = source.getMinLongitude();
      availableSensorText.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          buildAlertDialog();
        }
      });

      String latForDisplay = String.format("%.3f", latitudeForDisplay);
      String longForDisplay = String.format("%.3f", longitudeForDisplay);
      locationValue.setText(latForDisplay + " , " + longForDisplay);

      final Calendar c = Calendar.getInstance();
      year = c.get(Calendar.YEAR);
      month = c.get(Calendar.MONTH);
      day = c.get(Calendar.DAY_OF_MONTH);

      buttonStartTime = (Button) findViewById(R.id.buttonStartTime);
      buttonStartTime.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          dialog.show();
        }
      });

      buttonEndTime = (Button) findViewById(R.id.buttonEndTime);
      buttonEndTime.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
//          DatePickerDialog dialog =
//              new DatePickerDialog(VariableActivity.this, new mDateSetListener(buttonEndTime), year,
//                  month, day);
//          dialog.getDatePicker().setMaxDate(enddate.getTime());
//          dialog.getDatePicker().setMinDate(startdate.getTime());
          enddialog.show();
        }
      });

      startTimeValue = (TextView) findViewById(R.id.start_time_value);
      endTimeValue = (TextView) findViewById(R.id.end_time_value);
      startTimeValue.setText(source.getStartTime());
      endTimeValue.setText(source.getEndTime());

      initializeMaps();
      //createExpandableListView();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  public void seeData(View view) {
    try {
      Date currStartDate = Constants.getStandardDateFormat().parse(startDate);
      Date currEndDate = Constants.getStandardDateFormat().parse(endDate);

      if (currStartDate.getTime() < currEndDate.getTime()) {

        Intent intent = new Intent(VariableActivity.this, SensorDataActivity.class);
        intent.putExtra("dataset", source.getSourceId());
        intent.putExtra("variable", currentVariable);
        intent.putExtra("startdate", startDate);
        intent.putExtra("enddate", endDate);
        startActivity(intent);
      } else {
        Toast.makeText(this, "Please Select End Time greater than Start Time", Toast.LENGTH_SHORT).show();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void moreInfo(View view) {
    Intent intent = new Intent(VariableActivity.this, WebViewActivity.class);
    if (source != null) {
      intent.putExtra("url", source.getInfoUrl());
      startActivity(intent);
    }
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

  private void buildAlertDialog() {

    String variables = source.getVariables();
    variables = variables.replace("[", "");
    variables = variables.replace("]", "");
    variables = variables.replace("\"", "");
    final String variablesAsArray[] = variables.split(",");

    // Strings to Show In Dialog with Radio Buttons
    final CharSequence[] items = variablesAsArray;

    // Creating and Building the Dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Available variables for this dataset");
    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int item) {
        levelDialog.dismiss();
        currentVariable = variablesAsArray[item];
        selectedSensorValue.setText(variablesAsArray[item]);
      }
    });
    levelDialog = builder.create();
    levelDialog.show();
  }

  private void initializeMaps() {
    MapFragment mapFragment =
            (MapFragment) getFragmentManager().findFragmentById(R.id.singleSourceMap);

    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(GoogleMap googleMap) {
//        LatLngBounds latLngBounds =
//            new LatLngBounds(new LatLng(37.7749, 122.4194), new LatLng(37.836213, -123.254866));
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(new LatLng(source.getMinLatitude(), source.getMinLongitude()), 10));
        googleMap.addMarker(new MarkerOptions().position(
                new LatLng(source.getMinLatitude(), source.getMinLongitude()))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_25)));
      }
    });
  }

  class mStartDateSetListener implements DatePickerDialog.OnDateSetListener {

    @Override public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
      int mYear = year;
      int mMonth = monthOfYear;
      int mDay = dayOfMonth;

      String selectedDate = (new StringBuilder().append(mYear)
          .append("-")
          .append(mMonth + 1)
          .append("-")
          .append(mDay)).toString();

      DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
      DateFormat prettyFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

      try {
        Date date = simpleDateFormat.parse(selectedDate);

        startDate = format.format(date);
        selectedDate = prettyFormat.format(date);
        buttonStartTime.setText(selectedDate);
        System.out.println(buttonStartTime.getText().toString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  }

  class mEndDateSetListener implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
      int mYear = year;
      int mMonth = monthOfYear;
      int mDay = dayOfMonth;

      String selectedDate = (new StringBuilder().append(mYear)
              .append("-")
              .append(mMonth + 1)
              .append("-")
              .append(mDay)).toString();

      DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
      DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
      DateFormat prettyFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);

      try {
        Date date = simpleDateFormat.parse(selectedDate);

        endDate = format.format(date);
        selectedDate = prettyFormat.format(date);
        buttonEndTime.setText(selectedDate);
        System.out.println(buttonEndTime.getText().toString());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }
  }
}
