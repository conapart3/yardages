package com.six;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.gc.materialdesign.views.Button;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Conal on 17/06/2015.
 */
public class YardagesActivity extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

    GoogleApiClient mGoogleApiClient;
    Location location1,location2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yardages_main);

        buildGoogleApiClient();

//        RecyclerView recycList = (RecyclerView) findViewById(R.id.cardList);
//        recycList.setHasFixedSize(true);

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recycList.setLayoutManager(llm);

//        ClubCardAdapter ca = new ClubCardAdapter(createList(30));
//        recycList.setAdapter(ca);

        setupListeners();


    }

    private void setupListeners(){
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
                location1 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if(location1 != null){
                    latitude1.setText(String.valueOf(location1.getLatitude()));
                    longitude1.setText(String.valueOf(location1.getLongitude()));
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location2 = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if(location2 != null){
                    latitude2.setText(String.valueOf(location2.getLatitude()));
                    longitude2.setText(String.valueOf(location2.getLongitude()));
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float dist = location1.distanceTo(location2);
                distance.setText(""+dist);
            }
        });
    }

    private List<ClubCard> createList(int size) {
        List result = new ArrayList();
        for(int i=1;i<=size;i++){
            ClubCard cc = new ClubCard();
            cc.name = ClubCard.NAME_PREFIX + i;
            result.add(cc);
        }
        return result;
    }

    protected synchronized void buildGoogleApiClient(){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
