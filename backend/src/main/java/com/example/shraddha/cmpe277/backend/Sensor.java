package com.example.shraddha.cmpe277.backend;

/**
 * Created by Shraddha on 10/12/15.
 */
public class Sensor {

    private long sensor_id;
    private String name;
    private String type;
    private long device_id;

    public long getDevice_id() {
        return device_id;
    }

    public void setDevice_id(long device_id) {
        this.device_id = device_id;
    }

    public long getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(long sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void Sensor(long device_id) {
        this.device_id = device_id;
    }
}
