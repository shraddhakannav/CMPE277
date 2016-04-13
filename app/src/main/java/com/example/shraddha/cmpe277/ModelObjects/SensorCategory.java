package com.example.shraddha.cmpe277.ModelObjects;

import com.parse.ParseObject;

/**
 * Created by saraswathithanu on 4/11/16.
 */
public class SensorCategory {

  private String objectId;
  private String categoryName;
  private String URL;

  public String getObjectId() {
    return objectId;
  }

  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public void populateResult(ParseObject result){
    this.objectId = result.getObjectId();
    this.categoryName = result.getString("categoryName");
    this.URL = result.getString("categoryURL");
  }
}
