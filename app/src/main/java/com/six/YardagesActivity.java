package com.six;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import android.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * Created by Conal on 17/06/2015.
 */
public class YardagesActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Location location1,location2;
    LocationManager locationManager;
    LocationListener locationListener;
// Request code to use when launching the resolution activity

    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yardages_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                Context context = getApplicationContext();
                CharSequence text = "Location changed.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras){}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}

        };

//        register listener with location manager to receive location updates.
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        setupButtonListeners();

//        RecyclerView recycList = (RecyclerView) findViewById(R.id.cardList);
//        recycList.setHasFixedSize(true);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recycList.setLayoutManager(llm);
//        ClubCardAdapter ca = new ClubCardAdapter(createList(30));
//        recycList.setAdapter(ca);

    }

    private void setupButtonListeners(){
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);

        final TextView latitude1 = (TextView) findViewById(R.id.latitude1);
        final TextView longitude1 = (TextView) findViewById(R.id.longitude1);

        final TextView latitude2 = (TextView) findViewById(R.id.latitude2);
        final TextView longitude2 = (TextView) findViewById(R.id.longitude2);

        final TextView distance = (TextView) findViewById(R.id.distance);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                location1 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

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
                location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

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
                float dist = location1.distanceTo(location2);
                distance.setText("" + dist);
            }
        });
    }

//    private List<ClubCard> createList(int size) {
//        List result = new ArrayList();
//        for(int i=1;i<=size;i++){
//            ClubCard cc = new ClubCard();
//            cc.name = ClubCard.NAME_PREFIX + i;
//            result.add(cc);
//        }
//        return result;
//    }


    @Override
    public void onConnected(Bundle bundle) {
// Connected to Google Play services!
        // The good stuff goes here.
    }

    @Override
    public void onConnectionSuspended(int i) {
// The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

//    private static final int TWO_MINUTES = 1000 * 60 * 2;
//
//    /** Determines whether one Location reading is better than the current Location fix
//     * @param location  The new Location that you want to evaluate
//     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
//     */
//    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
//        if (currentBestLocation == null) {
//            // A new location is always better than no location
//            return true;
//        }
//
//        // Check whether the new location fix is newer or older
//        long timeDelta = location.getTime() - currentBestLocation.getTime();
//        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
//        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
//        boolean isNewer = timeDelta > 0;
//
//        // If it's been more than two minutes since the current location, use the new location
//        // because the user has likely moved
//        if (isSignificantlyNewer) {
//            return true;
//            // If the new location is more than two minutes older, it must be worse
//        } else if (isSignificantlyOlder) {
//            return false;
//        }
//
//        // Check whether the new location fix is more or less accurate
//        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
//        boolean isLessAccurate = accuracyDelta > 0;
//        boolean isMoreAccurate = accuracyDelta < 0;
//        boolean isSignificantlyLessAccurate = accuracyDelta > 200;
//
//        // Check if the old and new location are from the same provider
//        boolean isFromSameProvider = isSameProvider(location.getProvider(),
//                currentBestLocation.getProvider());
//
//        // Determine location quality using a combination of timeliness and accuracy
//        if (isMoreAccurate) {
//            return true;
//        } else if (isNewer && !isLessAccurate) {
//            return true;
//        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
//            return true;
//        }
//        return false;
//    }
//
//    /** Checks whether two providers are the same */
//    private boolean isSameProvider(String provider1, String provider2) {
//        if (provider1 == null) {
//            return provider2 == null;
//        }
//        return provider1.equals(provider2);
//    }
}
