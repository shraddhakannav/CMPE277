package com.example.shraddha.cmpe277.backend;

/**
 * Created by Shraddha on 10/16/15.
 */
public class SensorData {

    private int sensordata_id;
    private String name;
    private String type;
    private float value;
    private double latitude;
    private double longitude;
    private String email;

    public int getSensordata_id() {
        return sensordata_id;
    }

    public void setSensordata_id(int sensordata_id) {
        this.sensordata_id = sensordata_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
