package com.yardages;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link YardagesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YardagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YardagesFragment extends Fragment {
    GoogleApiClient mGoogleApiClient;
    Location location1,location2;
    LocationManager locationManager;
    LocationListener locationListener;
    int changeCounts = 0;
    // Request code to use when launching the resolution activity

    private ButtonRectangle button1, button2, button3;
    private TextView latitude1, longitude1, latitude2, longitude2, distance, distanceOld;
    private ButtonFloat floatButton;
    private Context context;

    private ArrayList<Location> locationList;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YardagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YardagesFragment newInstance(String param1, String param2) {
        YardagesFragment fragment = new YardagesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public YardagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();

        button1 = (ButtonRectangle) getActivity().findViewById(R.id.button1);
        button2 = (ButtonRectangle) getActivity().findViewById(R.id.button2);
        button3 = (ButtonRectangle) getActivity().findViewById(R.id.button3);
        floatButton = (ButtonFloat) getActivity().findViewById(R.id.buttonFloat);
        latitude1 = (TextView) getActivity().findViewById(R.id.latitude1);
        longitude1 = (TextView) getActivity().findViewById(R.id.longitude1);
        latitude2 = (TextView) getActivity().findViewById(R.id.latitude2);
        longitude2 = (TextView) getActivity().findViewById(R.id.longitude2);
        distance = (TextView) getActivity().findViewById(R.id.distance);
        distanceOld = (TextView) getActivity().findViewById(R.id.distanceOld);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    }

    @Override
    public void onStart() {
        super.onStart();
        locationListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                changeCounts = changeCounts + 1;
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
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yardages, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence text = "Golf ball added.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
//
//                ScatterFragment scatterFragment = new ScatterFragment();
//                Bundle args = new Bundle();
//                args.putInt("key1", 100);
//                scatterFragment.setArguments(args);
//
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, scatterFragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
