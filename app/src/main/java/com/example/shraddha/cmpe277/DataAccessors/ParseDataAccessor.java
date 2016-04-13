package com.example.shraddha.cmpe277.DataAccessors;

import com.example.shraddha.cmpe277.ModelObjects.SensorCategory;
import com.example.shraddha.cmpe277.ModelObjects.SensorDataSource;
import com.example.shraddha.cmpe277.SensorData;
import com.example.shraddha.cmpe277.Utils.Constants;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

public class ParseDataAccessor {

  private static ParseDataAccessor parseDataAccessor;
  private static String SENSOR_DATASOURCE_TABLE = "Source";
  private ArrayList<SensorDataSource> cachedSources;
  private static String SENSOR_CATEGORIES = "Category";

  public ArrayList<SensorCategory> getCachedCategories() {
    return cachedCategories;
  }

  private ArrayList<SensorCategory> cachedCategories;

  public static void initInstance() {

    if (parseDataAccessor == null) {
      parseDataAccessor = new ParseDataAccessor();
    }
  }

  public ArrayList<SensorDataSource> getCachedSources() {
    return cachedSources;
  }

  public static ParseDataAccessor getInstance() {
    return parseDataAccessor;
  }

  public void getSourcesFromParse() {
    final ArrayList<SensorDataSource> sensorDataSources = new ArrayList<>();
    ParseQuery<ParseObject> query = ParseQuery.getQuery(SENSOR_DATASOURCE_TABLE);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> list, ParseException e) {
        if (e == null) {
          for (ParseObject eachObject : list) {
            SensorDataSource dataSource = new SensorDataSource();
            dataSource.populateResult(eachObject);
            sensorDataSources.add(dataSource);
          }
        } else {
          System.out.print(
              "Error in fetching data sources from Parse" + e.getCode() + e.getStackTrace());
        }
      }
    });

    this.cachedSources = sensorDataSources;
  }

  public void getSensorCategoriesFromParse() {
    final ArrayList<SensorCategory> allCategories = new ArrayList<>();
    ParseQuery<ParseObject> query = ParseQuery.getQuery(SENSOR_CATEGORIES);
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> list, ParseException e) {
        if (e == null) {
          for (ParseObject eachObject : list) {
            SensorCategory category = new SensorCategory();
            category.populateResult(eachObject);
            allCategories.add(category);
          }
        } else {
          System.out.print(
              "Error in fetching data sources from Parse" + e.getCode() + e.getStackTrace());
        }
      }
    });

    this.cachedCategories = allCategories;
  }

  public ArrayList<SensorDataSource> getSensorLocations(String source, String sensorType) {
    final ArrayList<SensorDataSource> sensorDataSources = new ArrayList<>();

    ParseQuery<ParseObject> query = ParseQuery.getQuery(SENSOR_DATASOURCE_TABLE);

    if (sensorType.equalsIgnoreCase(Constants.ALL_SENSORS)) {
      if (!(source.equalsIgnoreCase(Constants.ALL_SOURCES))) {
        query.whereEqualTo("institution", source);
      }
      try {
        query.setLimit(1000);
        List<ParseObject> list = query.find();
        for (ParseObject eachObject : list) {
          SensorDataSource dataSource = new SensorDataSource();
          dataSource.populateResult(eachObject);
          sensorDataSources.add(dataSource);
        }
        query.setSkip(1000);
        list = query.find();
        for (ParseObject eachObject : list) {
          SensorDataSource dataSource = new SensorDataSource();
          dataSource.populateResult(eachObject);
          sensorDataSources.add(dataSource);
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    } else {
      query.whereEqualTo("variables", sensorType);
      if (!(source.equalsIgnoreCase(Constants.ALL_SOURCES))) {
        query.whereEqualTo("institution", source);
      }
      List<ParseObject> list = null;
      try {
        list = query.find();
        for (ParseObject eachObject : list) {
          SensorDataSource dataSource = new SensorDataSource();
          dataSource.populateResult(eachObject);
          sensorDataSources.add(dataSource);
        }
      } catch (ParseException e) {
        e.printStackTrace();
      }
    }

    System.out.println("No of fetched results are " + sensorDataSources.size());
    for (SensorDataSource dataSource : sensorDataSources) {
      System.out.println(
          dataSource.getInstitution() + "with data source id" + dataSource.getSourceId());
    }
    return sensorDataSources;
  }
}
