package com.yardages;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link YardagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YardagesFragment extends Fragment {
    private GoogleApiClient mGoogleApiClient;
    private Location currentLocation;
    private Double currentDistance, targetDistance;
    private LocationManager locationManager;
    private LocationListener locationListener;
    // Request code to use when launching the resolution activity

    private ButtonRectangle teeButton, addBallButton, deleteBallButton, saveButton, targetButton;
    private TextView distanceText, ballNumberText, lastBallText, targetText, accuracyText;
    private EditText nameEditText;
    private Context context;//this is just getActivity()

    private String description;
    private ArrayList<Ball> ballList = new ArrayList<Ball>();
    long scatterRowNum;
    private int changeCounts = 0, ballNumber = 0;
    private Location targetLocation, teeLocation;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment YardagesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static YardagesFragment newInstance() {
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

    }

    @Override
    public void onStart() {
        super.onStart();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        teeButton = (ButtonRectangle) getActivity().findViewById(R.id.teeBtn);
        addBallButton = (ButtonRectangle) getActivity().findViewById(R.id.addBallBtn);
        saveButton = (ButtonRectangle) getActivity().findViewById(R.id.saveBtn);
        deleteBallButton = (ButtonRectangle) getActivity().findViewById(R.id.deleteBallBtn);
        targetButton = (ButtonRectangle) getActivity().findViewById(R.id.targetBtn);

        distanceText = (TextView) getActivity().findViewById(R.id.distanceTxt);
        ballNumberText = (TextView) getActivity().findViewById(R.id.ballNumberTxt);
        lastBallText = (TextView) getActivity().findViewById(R.id.lastBallTxt);
        targetText = (TextView) getActivity().findViewById(R.id.targetTxt);
        accuracyText = (TextView) getActivity().findViewById(R.id.accuracyTxt);

        nameEditText = (EditText) getActivity().findViewById(R.id.nameEditTxt);

        locationListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                currentLocation = location;
                if(teeLocation != null && currentLocation != null){
                    currentDistance = getDistance(currentLocation,teeLocation);
                    distanceText.setText("Current dist: "+currentDistance +" yards");
                    accuracyText.setText("Accuracy: " +metresToYards(currentLocation.getAccuracy()) +" yards");
                } else {
                    distanceText.setText("Ready.");
                    accuracyText.setText("Accuracy: " +metresToYards(currentLocation.getAccuracy()) +" yards");
                }
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
                    + " must implement OnScatterSavedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setupButtonListeners(){

        nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //Clear focus here from edittext
                    description = nameEditText.getText().toString();
                    nameEditText.clearFocus();
                    nameEditText.setCursorVisible(false);
                }
                return false;
            }
        });

        teeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(currentLocation != null){
                    teeLocation = currentLocation;
                } else {
                    CharSequence text = "Location null, try again with signal.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }
            }
        });

        targetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (currentLocation != null) {
                    targetLocation = currentLocation;
                    if(teeLocation != null) {
                        targetDistance = getDistance(targetLocation, teeLocation);
                        targetText.setText("Target: " +targetDistance +" (yards)");
                    }
                } else {
                    CharSequence text = "Location null, try again with signal.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }
            }
        });

        addBallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ball b = new Ball();
                currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (currentLocation != null) {
                    b.setLatitude(currentLocation.getLatitude());
                    b.setLongitude(currentLocation.getLongitude());
                    ballList.add(b);

                    ballNumber=ballNumber+1;
                    ballNumberText.setText("Balls Added: " +ballNumber);

                    if(teeLocation != null) {
                        currentDistance = getDistance(currentLocation, teeLocation);
                        lastBallText.setText("Last Ball: " +currentDistance +" (yards)");
                    }
                } else {
                    CharSequence text = "Location null, try again with signal.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }
            }
        });

        deleteBallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ballList.size() > 0) {
                    ballList.remove(ballList.size() - 1);
                    ballNumber = ballNumber - 1;
                    ballNumberText.setText("Balls Added: " + ballNumber);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ballList.size()>0) {
                    Scatter sc = new Scatter();

                    sc.setDescription(description);
                    sc.setBallList(ballList);
                    if (teeLocation != null) {
                        sc.setTeeLatitude(teeLocation.getLatitude());
                        sc.setTeeLongitude(teeLocation.getLongitude());
                    }
                    if (targetLocation != null) {
                        sc.setTargetLatitude(targetLocation.getLatitude());
                        sc.setTargetLongitude(targetLocation.getLongitude());
                    }

                    YardagesDbHelper dbHelper = new YardagesDbHelper(getActivity());

                    try {
                        scatterRowNum = dbHelper.addScatter(sc);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    CharSequence text = "Scatter Saved.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();

                    //go to the scatter fragment
                    ScatterFragment scatterFragment = ScatterFragment.newInstance(scatterRowNum);
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.container, scatterFragment, null)
                            .addToBackStack(null)
                            .commit();
                } else {
                    CharSequence text = "ERROR: Record ball data.";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }
            }
        });
    }

    public double getDistance(Location l1, Location l2) {
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

        dist = metresToYards(dist);
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
        // TODO: Update argument type and description
        public void onFragmentInteraction(Uri uri);
    }

}
