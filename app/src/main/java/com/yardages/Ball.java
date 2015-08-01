package com.yardages;

/**
 * Created by Conal on 28/07/2015.
 */
public class Ball {

    private long id;
    private double latitude;
    private double longitude;
    private int scatterId;
//    private boolean isArchived;

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

    public int getScatterId() {
        return scatterId;
    }

    public void setScatterId(int scatterId) {
        this.scatterId = scatterId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public boolean isArchived() {
//        return isArchived;
//    }
//
//    public void setIsArchived(boolean isArchived) {
//        this.isArchived = isArchived;
//    }
}
