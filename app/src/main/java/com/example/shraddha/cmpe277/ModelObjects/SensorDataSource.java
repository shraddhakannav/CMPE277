package com.example.shraddha.cmpe277.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;
import com.parse.ParseObject;
import java.io.Serializable;
import org.json.JSONArray;

public class SensorDataSource implements Serializable {
  private String objectId;
  private String institution;
  private String sourceId;
  private double latitude;
  private double longitude;
  private JSONArray variables;

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

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
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
      if (eachObject.has("latitude")) {
        this.latitude = eachObject.getDouble("latitude");
      }

      if (eachObject.has("longitude")) {
        this.longitude = eachObject.getDouble("longitude");
      }

      if (eachObject.has("variables") && eachObject.getJSONArray("variables") != null) {
        this.variables = eachObject.getJSONArray("variables");
      }
    }
  }
}
