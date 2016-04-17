package com.example.shraddha.cmpe277.ModelObjects;

/**
 * Created by Shraddha on 4/17/16.
 */
public class SensorData {

    private String time;
    private Double x;
    private Double trustValue;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getTrustValue() {
        return trustValue;
    }

    public void setTrustValue(Double trustValue) {
        this.trustValue = trustValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{" + time + ": ");
        sb.append("x: " + x);
        sb.append("trust: " + trustValue);
        sb.append("}");
        return super.toString();
    }
}
