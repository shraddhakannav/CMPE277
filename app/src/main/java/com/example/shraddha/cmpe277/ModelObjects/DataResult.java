package com.example.shraddha.cmpe277.ModelObjects;

import java.util.Arrays;

public class DataResult {

  private String[] columnNames;
  private String[] columnDataTypes;
  private String[] units;
  private String[][] rows;

  public String[] getColumnNames() {
    return columnNames;
  }

  public void setColumnNames(String[] columnNames) {
    this.columnNames = columnNames;
  }

  public String[] getColumnDataTypes() {
    return columnDataTypes;
  }

  public void setColumnDataTypes(String[] columnDataTypes) {
    this.columnDataTypes = columnDataTypes;
  }

  public String[] getUnits() {
    return units;
  }

  public void setUnits(String[] units) {
    this.units = units;
  }

  public String[][] getRows() {
    return rows;
  }

  public void setRows(String[][] rows) {
    this.rows = rows;
  }
}
