package com.example.shraddha.cmpe277.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "sensorDataApi",
        version = "v1",
        resource = "sensorData",
        namespace = @ApiNamespace(
                ownerDomain = "backend.cmpe277.shraddha.example.com",
                ownerName = "backend.cmpe277.shraddha.example.com",
                packagePath = ""
        )
)
public class SensorDataEndpoint {

    private static final Logger logger = Logger.getLogger(SensorDataEndpoint.class.getName());
    static List<SensorData> dataList = new ArrayList<SensorData>();

    /**
     * This method gets the <code>SensorData</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>SensorData</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getSensorData")
    public SensorData getSensorData(@Named("id") int id) {
        // TODO: Implement this function
        logger.info("Calling getSensorData method");

        for (SensorData data : dataList) {
            if (data.getSensordata_id() == id) {
                return data;
            }
        }
        return null;
    }

    @ApiMethod(name = "deleteSensorData")
    public SensorData deleteSensorData(SensorData sensorData) {
        // TODO: Implement this function
        logger.info("Calling insertSensorData method");
        dataList.remove(sensorData);
        return sensorData;
    }


    /**
     * This inserts a new <code>SensorData</code> object.
     *
     * @param sensorData The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertSensorData")
    public SensorData insertSensorData(SensorData sensorData) {
        // TODO: Implement this function
        logger.info("Calling insertSensorData method");
        dataList.add(sensorData);
        return sensorData;
    }

    @ApiMethod(name = "listSensorData")
    public List<SensorData> listSensorData() {
        return dataList;
    }
}