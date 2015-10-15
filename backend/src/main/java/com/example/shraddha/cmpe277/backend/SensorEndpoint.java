package com.example.shraddha.cmpe277.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "sensorApi",
        version = "v1",
        resource = "sensor",
        namespace = @ApiNamespace(
                ownerDomain = "backend.cmpe277.shraddha.example.com",
                ownerName = "backend.cmpe277.shraddha.example.com",
                packagePath = ""
        )
)
public class SensorEndpoint {

    private static final Logger logger = Logger.getLogger(SensorEndpoint.class.getName());

    /**
     * This method gets the <code>Sensor</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Sensor</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getSensor")
    public Sensor getSensor(@Named("id") long id) {
        // TODO: Implement this function
        logger.info("Calling getSensor method");
        Sensor sensor = new Sensor();
        sensor.setName("Mysensor");
        return sensor;
    }

    /**
     * This inserts a new <code>Sensor</code> object.
     *
     * @param sensor The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertSensor")
    public Sensor insertSensor(Sensor sensor) {
        // TODO: Implement this function
        logger.info("Calling insertSensor method");
        return sensor;
    }


}