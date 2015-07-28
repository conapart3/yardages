package com.yardages;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Conal on 27/07/2015.
 */
public class Scatter {

    private String description;
    private ArrayList<Location> locationList;
    private Location teeLocation;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(ArrayList<Location> locationList) {
        this.locationList = locationList;
    }

    public Location getTeeLocation() {
        return teeLocation;
    }

    public void setTeeLocation(Location teeLocation) {
        this.teeLocation = teeLocation;
    }
}
