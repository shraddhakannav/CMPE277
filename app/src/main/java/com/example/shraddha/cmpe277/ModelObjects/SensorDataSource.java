package com.example.shraddha.cmpe277.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class SensorDataSource implements Parcelable {
  private String objectId;
  private String institution;
  private String sourceId;
  private double minLatitude;
  private double minLongitude;
  private double maxLatitude;
  private double maxLongitude;
  private String variables;
  private String infoUrl;
  private String summary;
  private String startTime;
  private String endTime;
  private static final long serialVersionUID = 1L;

  public SensorDataSource() {

  }

  public SensorDataSource(Parcel in) {
    objectId = in.readString();
    institution = in.readString();
    sourceId = in.readString();
    minLatitude = in.readDouble();
    minLongitude = in.readDouble();
    maxLatitude = in.readDouble();
    maxLongitude = in.readDouble();
    infoUrl = in.readString();
    summary = in.readString();
    startTime = in.readString();
    endTime = in.readString();
    variables = in.readString();
  }

  public static final Creator<SensorDataSource> CREATOR =
      new Parcelable.Creator<SensorDataSource>() {
        @Override public SensorDataSource createFromParcel(Parcel in) {
          return new SensorDataSource(in);
        }

        @Override public SensorDataSource[] newArray(int size) {
          return new SensorDataSource[size];
        }
      };

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

  public String getVariables() {
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
        this.variables = eachObject.getJSONArray("variables").toString();
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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {

    dest.writeString(objectId);
    dest.writeString(institution);
    dest.writeString(sourceId);
    dest.writeDouble(minLatitude);
    dest.writeDouble(minLongitude);
    dest.writeDouble(maxLatitude);
    dest.writeDouble(maxLongitude);
    dest.writeString(infoUrl);
    dest.writeString(summary);
    dest.writeString(startTime);
    dest.writeString(endTime);
    dest.writeString(variables);
  }
}

