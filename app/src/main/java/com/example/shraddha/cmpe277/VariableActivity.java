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
  private SensorDataSource source;

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

      InstitutionLabel = (TextView) findViewById(R.id.txtViewInstitutionName);
      availableSensorText = (TextView) findViewById(R.id.available_sensor_list);
      selectedSensorValue = (TextView) findViewById(R.id.selectedSensorValue);
      locationLabel = (TextView) findViewById(R.id.locationLabel);
      locationValue = (TextView) findViewById(R.id.latlng_value);
      scenicImage = (ImageView) findViewById(R.id.background_image);

      DateFormat format = new SimpleDateFormat("yyyy-mm-dd'T'HH:MM:SS'Z'", Locale.ENGLISH);
      final Date startdate = format.parse(source.getStartTime());
      final Date enddate = format.parse(source.getEndTime());

      txtViewSourceId = (TextView) findViewById(R.id.txtViewSourceId);
      txtViewSourceId.setText(source.getSourceId());

      txtViewSummary = (TextView) findViewById(R.id.txtViewSummary);
      txtViewSummary.setText(source.getSummary());

      infoURLButton = (Button) findViewById(R.id.infoURLButton);
      //infoURLButton.setOnClickListener(new View.OnClickListener() {
      //  @Override public void onClick(View v) {
      //    Intent intent = new Intent(VariableActivity.this, WebViewActivity.class);
      //    intent.putExtra("url", source.getInfoUrl());
      //    startActivity(intent);
      //  }
      //});

      InstitutionLabel.setText(source.getInstitution());

      latitudeForDisplay = source.getMinLatitude();
      latitudeForDisplay = source.getMinLongitude();
      availableSensorText.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
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
          DatePickerDialog dialog =
              new DatePickerDialog(VariableActivity.this, new mDateSetListener(buttonStartTime),
                  year, month, day);
          dialog.getDatePicker().setMaxDate(enddate.getTime());
          dialog.getDatePicker().setMinDate(startdate.getTime());
          dialog.show();
        }
      });

      buttonEndTime = (Button) findViewById(R.id.buttonEndTime);
      buttonEndTime.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          DatePickerDialog dialog =
              new DatePickerDialog(VariableActivity.this, new mDateSetListener(buttonEndTime), year,
                  month, day);
          dialog.getDatePicker().setMaxDate(enddate.getTime());
          dialog.getDatePicker().setMinDate(startdate.getTime());
          dialog.show();
        }
      });

      //createExpandableListView();
    } catch (ParseException e) {
      e.printStackTrace();
    }
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
    final String variablesAsArray[] = variables.split(",");

    // Strings to Show In Dialog with Radio Buttons
    final CharSequence[] items = variablesAsArray;

    // Creating and Building the Dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Available variables for this dataset");
    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int item) {
        levelDialog.dismiss();
        selectedSensorValue.setText(variablesAsArray[item]);
      }
    });
    levelDialog = builder.create();
    levelDialog.show();
  }

  private void initializeMaps() {
    MapFragment mapFragment =
        (MapFragment) getFragmentManager().findFragmentById(R.id.datasourcesMap);
    mapFragment.getMapAsync(new OnMapReadyCallback() {
      @Override public void onMapReady(GoogleMap googleMap) {
        //LatLngBounds latLngBounds =
        //    new LatLngBounds(new LatLng(37.7749, 122.4194), new LatLng(37.836213, -123.254866));
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(new LatLng(37.7749, -122.4194), 6));
        googleMap.addMarker(new MarkerOptions().position(
            new LatLng(source.getMinLatitude(), source.getMinLongitude()))
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_25)));
      }
    });
  }

  class mDateSetListener implements DatePickerDialog.OnDateSetListener {

    Button v;

    public mDateSetListener(Button button) {
      v = button;
    }

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
