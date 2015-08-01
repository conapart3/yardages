package com.yardages;

import android.widget.ImageView;

/**
 * Created by Conal on 17/06/2015.
 */
public class ClubCard {
    private String description;
    private ImageView image;
    private String datetime;
    private String numBalls;
    private String accuracy;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getNumBalls() {
        return numBalls;
    }

    public void setNumBalls(String numBalls) {
        this.numBalls = numBalls;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
