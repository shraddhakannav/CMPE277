package com.example.shraddha.cmpe277.ModelObjects;

import com.parse.ParseObject;

import org.json.JSONArray;

import java.io.Serializable;

public class SensorDataSource implements Serializable {
  private String objectId;
  private String institution;
  private String sourceId;
  private double minLatitude;
  private double minLongitude;
  private double maxLatitude;
  private double maxLongitude;
  private JSONArray variables;
  private String infoUrl;
  private String summary;
  private String startTime;
  private String endTime;

  public String getInfoUrl() {
    return infoUrl;
  }

  public String getSummary() {
    return summary;
  }

  public String getStartTime() {
    return startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public String getSourceId() {
    return sourceId;
  }

  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }

  public double getMinLatitude() {
    return minLatitude;
  }

  public void setMinLatitude(double minLatitude) {
    this.minLatitude = minLatitude;
  }

  public double getMinLongitude() {
    return minLongitude;
  }

  public void setMinLongitude(double minLongitude) {
    this.minLongitude = minLongitude;
  }

  public JSONArray getVariables() {
    return variables;
  }

  public void populateResult(ParseObject eachObject) {

    if (eachObject != null) {
      if (eachObject.has("objectId") && eachObject.getString("objectId") != null) {
        this.objectId = eachObject.getString("objectId");
      }
      if (eachObject.has("institution") && eachObject.getString("institution") != null) {
        this.institution = eachObject.getString("institution");
      }
      if (eachObject.has("sourceId") && eachObject.getString("sourceId") != null) {
        this.sourceId = eachObject.getString("sourceId");
      }
      if (eachObject.has("minLatitude")) {
        this.minLatitude = eachObject.getDouble("minLatitude");
      }

      if (eachObject.has("minLongitude")) {
        this.minLongitude = eachObject.getDouble("minLongitude");
      }

      if (eachObject.has("maxLatitude")) {
        this.maxLatitude = eachObject.getDouble("maxLatitude");
      }

      if (eachObject.has("maxLongitude")) {
        this.maxLongitude = eachObject.getDouble("maxLongitude");
      }

      if (eachObject.has("variables") && eachObject.getJSONArray("variables") != null) {
        this.variables = eachObject.getJSONArray("variables");
      }

      if (eachObject.has("infoUrl") && eachObject.getString("infoUrl") != null) {
        this.infoUrl = eachObject.getString("infoUrl");
      }

      if (eachObject.has("summary") && eachObject.getString("summary") != null) {
        this.summary = eachObject.getString("summary");
      }

      if (eachObject.has("startTime") && eachObject.getString("startTime") != null) {
        this.startTime = eachObject.getString("startTime");
      }

      if (eachObject.has("endTime") && eachObject.getString("endTime") != null) {
        this.endTime = eachObject.getString("endTime");
      }
    }
  }
}
