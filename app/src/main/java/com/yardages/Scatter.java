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
    private Location teeLocation;
    private Location targetLocation;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Location getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
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

    public Location getTeeLocation() {
        return teeLocation;
    }

    public void setTeeLocation(Location teeLocation) {
        this.teeLocation = teeLocation;
    }

//    public boolean isArchived() {
//        return isArchived;
//    }
//
//    public void setIsArchived(boolean isArchived) {
//        this.isArchived = isArchived;
//    }
}
