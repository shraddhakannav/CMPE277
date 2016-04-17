package com.example.shraddha.cmpe277.ModelObjects;

import org.json.JSONArray;

public class DataResult {

    private String datasetId;
    private String institution;
    private String latitude;
    private String longitude;
    private JSONArray columnNames;
    private JSONArray columnDataTypes;
    private JSONArray units;
    private JSONArray rows;

    public JSONArray getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(JSONArray columnNames) {
        this.columnNames = columnNames;
    }

    public JSONArray getColumnDataTypes() {
        return columnDataTypes;
    }

    public void setColumnDataTypes(JSONArray columnDataTypes) {
        this.columnDataTypes = columnDataTypes;
    }

    public JSONArray getUnits() {
        return units;
    }

    public void setUnits(JSONArray units) {
        this.units = units;
    }

    public JSONArray getRows() {
        return rows;
    }

    public void setRows(JSONArray rows) {
        this.rows = rows;
    }
}
