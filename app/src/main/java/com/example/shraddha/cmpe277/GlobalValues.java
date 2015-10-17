package com.example.shraddha.cmpe277;

/**
 * Created by Shraddha on 10/14/15.
 */
public class GlobalValues {

    private static double latitude;

    private static double longitude;
    private static String deviceId;

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

    public static String getDeviceId() {
        return deviceId;
    }

    public static void setDeviceId(String deviceId) {
        GlobalValues.deviceId = deviceId;
    }
}
