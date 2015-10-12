package com.example.shraddha.cmpe277.backend;

/**
 * Created by Shraddha on 10/12/15.
 */
public class Sensor {

    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void Sensor(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
