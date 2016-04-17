package com.example.shraddha.cmpe277.Gson;

import com.google.gson.annotations.SerializedName;


public class Institution {

    @SerializedName("columnNames")
    private String[] columnName;
    @SerializedName("columnTypes")
    private String[] columnTypes;
    @SerializedName("columnUnits")
    private String[] columnUnits;
    @SerializedName("rows")
    private String rows[];

    public String[] getColumnName() {
        return columnName;
    }

    public void setColumnName(String[] columnName) {
        this.columnName = columnName;
    }

    public String[] getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(String[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public String[] getColumnUnits() {
        return columnUnits;
    }

    public void setColumnUnits(String[] columnUnits) {
        this.columnUnits = columnUnits;
    }

    public String[] getRows() {
        return rows;
    }

    public void setRows(String[] rows) {
        this.rows = rows;
    }
}
