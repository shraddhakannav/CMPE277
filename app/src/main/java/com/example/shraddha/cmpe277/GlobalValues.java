package com.example.shraddha.cmpe277;

/**
 * Created by Shraddha on 10/14/15.
 */
public class GlobalValues {

    private static double latitude;

    private static double longitude;

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        GlobalValues.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        GlobalValues.longitude = longitude;
    }
}
