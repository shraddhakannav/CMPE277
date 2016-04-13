package com.example.shraddha.cmpe277.Gson;

import com.google.gson.annotations.SerializedName;


public class Institution {

  @SerializedName("columnNames") private String[] columnName;
  @SerializedName("columnTypes") private String[] columnTypes;
  @SerializedName("columnUnits") private String[] columnUnits;
  @SerializedName("rows") private String rows[];

  public String[] getColumnName() {
    return columnName;
  }

  public String[] getColumnTypes() {
    return columnTypes;
  }

  public String[] getColumnUnits() {
    return columnUnits;
  }

  public void setColumnName(String[] columnName) {
    this.columnName = columnName;
  }

  public void setColumnTypes(String[] columnTypes) {
    this.columnTypes = columnTypes;
  }

  public void setColumnUnits(String[] columnUnits) {
    this.columnUnits = columnUnits;
  }

  public void setRows(String[] rows) {
    this.rows = rows;
  }

  public String[] getRows() {
    return rows;
  }
}
