package com.curricle.waypoints.data;

/**
 * Created by conrad on 16/01/15.
 */
public class WayPoint {

    public int identifier;
    public double latitude;
    public double longitude;
    public long timestamp;
    public int type;
    public String message;
    public boolean transmitted;

    public WayPoint(int identifier, double latitude, double longitude, long timestamp, int type, String message, boolean transmitted) {
        this.identifier = identifier;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.type = type;
        this.message = message;
        this.transmitted = transmitted;
    }

    @Override
    public String toString() {
        return "WayPoint{" +
                "identifier=" + identifier +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", transmitted=" + transmitted +
                '}';
    }

}
