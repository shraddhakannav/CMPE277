package com.example.shraddha.cmpe277.ModelObjects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Shraddha on 4/17/16.
 */
public class SensorData implements Parcelable {

    public static final Creator<SensorData> CREATOR = new Creator<SensorData>() {
        @Override
        public SensorData createFromParcel(Parcel in) {
            return new SensorData(in);
        }

        @Override
        public SensorData[] newArray(int size) {
            return new SensorData[size];
        }
    };
    private String time;
    private Double x;
    private Double trustValue;
    private String unit;

    public SensorData() {
    }

    protected SensorData(Parcel in) {
        time = in.readString();
        x = in.readDouble();
        trustValue = in.readDouble();
        unit = in.readString();
    }

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeDouble(this.x);
        dest.writeDouble(this.trustValue);
        dest.writeString(this.unit);
    }


}
