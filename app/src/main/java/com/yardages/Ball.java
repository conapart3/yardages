package com.yardages;

import android.graphics.Canvas;

/**
 * Created by Conal on 28/07/2015.
 */
public class Ball {

    private long id;
    private double latitude;
    private double longitude;
    private int scatterId;
    private float x,y;
//    private boolean isArchived;

    public Ball (){

    }

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

    public void calculateDotLocationBasedOnLatLong(float width, float height){

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
