package com.yardages;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ButtonFloat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.location.LocationListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Conal on 17/06/2015.
 */
public class YardagesActivity extends Activity  {

    GoogleApiClient mGoogleApiClient;
    Location location1,location2;
    LocationManager locationManager;
    LocationListener locationListener;
    int changeCounts = 0;
    // Request code to use when launching the resolution activity

    private ButtonRectangle button1, button2, button3;
    private TextView latitude1, longitude1, latitude2, longitude2, distance, distanceOld;
    private ButtonFloat floatButton;

    private ArrayList<Location> locationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yardages_main);

        button1 = (ButtonRectangle) findViewById(R.id.button1);
        button2 = (ButtonRectangle) findViewById(R.id.button2);
        button3 = (ButtonRectangle) findViewById(R.id.button3);
        floatButton = (ButtonFloat) findViewById(R.id.buttonFloat);
        latitude1 = (TextView) findViewById(R.id.latitude1);
        longitude1 = (TextView) findViewById(R.id.longitude1);
        latitude2 = (TextView) findViewById(R.id.latitude2);
        longitude2 = (TextView) findViewById(R.id.longitude2);
        distance = (TextView) findViewById(R.id.distance);
        distanceOld = (TextView) findViewById(R.id.distanceOld);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                changeCounts = changeCounts + 1;
                Context context = getApplicationContext();
                CharSequence text = "Location changed." + changeCounts;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras){}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}

        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        setupButtonListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }
    private void setupButtonListeners(){

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                location1 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location1 != null){
                    latitude1.setText(String.valueOf(location1.getLatitude()));
                    longitude1.setText(String.valueOf(location1.getLongitude()));
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Location null, try again with signal.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                location2 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                location2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location2 != null) {
                    latitude2.setText(String.valueOf(location2.getLatitude()));
                    longitude2.setText(String.valueOf(location2.getLongitude()));
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Location null, try again with signal.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "";
                if (location1 == null && location2 == null)
                    text = "Point A and B not set, wait for GPS signal.";
                else if (location1 == null && location2 != null)
                    text = "Point A not set, select your start point.";
                else if (location1 != null && location2 == null)
                    text = "Select Point B location.";

                if (location1 != null && location2 != null) {
                    double distOld = location1.distanceTo(location2);
                    double dist = getDistanceMetres(location1, location2);
//                    distOld = metresToYards(distOld);
                    dist = metresToYards(dist);
                    distanceOld.setText("" + distOld);
                    distance.setText("" + dist);
                    text = "Distance shown.";
                }

                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "Golf ball added.";
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    public double getDistanceMetres(Location l1, Location l2) {
        double lat1 = l1.getLatitude();
        double lon1 = l1.getLongitude();
        double lat2 = l2.getLatitude();
        double lon2 = l2.getLongitude();

        int EARTH_RADIUS_KM = 6371;
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLonRad = Math.toRadians(lon2 - lon1);

        double dist = Math.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.cos(deltaLonRad)) * EARTH_RADIUS_KM;
        dist = dist * 1000;
        return dist;
    }

    public double metresToYards(double metres) {
        double yards = metres*1.0936133;
        yards = (double)(Math.round(yards*100))/100;
        return yards;
    }

}
