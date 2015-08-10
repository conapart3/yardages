package com.yardages;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Conal on 27/07/2015.
 */
public class Scatter {

    private int id;
    private String description;
    private ArrayList<Ball> ballList;
    private Double teeLatitude, teeLongitude, targetLatitude, targetLongitude;
    private Date date;
//    private boolean isArchived;

    public Scatter(){
        ballList = new ArrayList<Ball>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public ArrayList<Ball> getBallList() {

        return ballList;
    }

    public void setBallList(ArrayList<Ball> ballList) {
        this.ballList = ballList;
    }

    public void addBall(Ball b){
        ballList.add(b);
    }

    public Ball getBall(int i){
        return ballList.get(i);
    }

    public Double getTeeLatitude() {
        return teeLatitude;
    }

    public void setTeeLatitude(Double teeLatitude) {
        this.teeLatitude = teeLatitude;
    }

    public Double getTeeLongitude() {
        return teeLongitude;
    }

    public void setTeeLongitude(Double teeLongitude) {
        this.teeLongitude = teeLongitude;
    }

    public Double getTargetLatitude() {
        return targetLatitude;
    }

    public void setTargetLatitude(Double targetLatitude) {
        this.targetLatitude = targetLatitude;
    }

    public Double getTargetLongitude() {
        return targetLongitude;
    }

    public void setTargetLongitude(Double targetLongitude) {
        this.targetLongitude = targetLongitude;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {

        this.date = date;
    }

}
