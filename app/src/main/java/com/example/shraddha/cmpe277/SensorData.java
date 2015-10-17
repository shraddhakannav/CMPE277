package com.example.shraddha.cmpe277;

/**
 * Created by Shraddha on 10/14/15.
 */
public class SensorData {

    private int id;
    private String sensortype;
    private double latitude;
    private double longitude;

    private String value;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSensortype() {
        return sensortype;
    }

    public void setSensortype(String sensortype) {
        this.sensortype = sensortype;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
