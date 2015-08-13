package com.yardages;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScatterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScatterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScatterFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SCATTERID = "scatter_id";
    private long scatterId;

    private Scatter scatter;

    private int width, height;//screen width/height
    private Double maxLat, maxLong, minLat, minLong, xScale, yScale, averageBearing;
    private Double teeLong, teeLat, targetLong, targetLat;//hold individual tee and target lat/longs
    private Double deltaLat, deltaLong, deltaX, deltaY;//for onscreen gps convert to xy
    private PointF teePoint, targetPoint;

    Canvas c;

    private ArrayList<PointF> pointList;
    private ArrayList<Double> bearingList, distanceList;//for bearing calculations
    private ArrayList<Double> tempLongs, tempLats;//for calc max, min - lat, long
    private ArrayList<Double> ballLats, ballLongs;//to hold all ball lat/longs as double list

    LinearLayout linearLayout;
    ScatterView scatterView;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param scatterId Parameter 1.
     * @return A new instance of fragment ScatterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScatterFragment newInstance(long scatterId) {
        ScatterFragment fragment = new ScatterFragment();
        Bundle args = new Bundle();
        args.putLong("scatter_id", scatterId);
        fragment.setArguments(args);
        return fragment;
    }

    public ScatterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        scatterId = -1;
        if (getArguments() != null) {
            scatterId = getArguments().getLong(ARG_SCATTERID);
        }

//        setupTestLocations();
        c = new Canvas();
    }

    @Override
    public void onStart(){
        super.onStart();

        YardagesDbHelper dbHelper = new YardagesDbHelper(getActivity());
        scatter = dbHelper.getScatter(scatterId);

        scatterView = (ScatterView) getActivity().findViewById(R.id.scatterView);

        getOnScreenPositions();

        scatterView.setPointList(pointList);
        scatterView.setTargetPoint(targetPoint);
        scatterView.setTeePoint(teePoint);
    }

//    private void setupTestLocations(){
//        testTeeLat = 54.321540;
//        testTeeLong = -5.542994;
//
//        testTargetLat = 54.321775;
//        testTargetLong = -5.541011;
//
//        testLats = new ArrayList<Double>();
//        testLats.add(54.321937);
//        testLats.add(54.321586);
//        testLats.add(54.321628);
//        testLats.add(54.321846);
//        testLats.add(54.321794);
//        testLats.add(54.321807);
//        testLats.add(54.321586);//short
//
//        testLongs = new ArrayList<Double>();
//        testLongs.add(-5.540766);
//        testLongs.add(-5.541056);
//        testLongs.add(-5.540621);
//        testLongs.add(-5.541268);
//        testLongs.add(-5.540933);
//        testLongs.add(-5.540677);
//        testLongs.add(-5.540669);//short
//
//    }

    private void getOnScreenPositions(){

        //setup vars for calculating onscreen positions
        width = 1000;
        height = 1000;
        pointList = new ArrayList<PointF>();
        ballLats = new ArrayList<Double>();
        ballLongs = new ArrayList<Double>();
        tempLats = new ArrayList<Double>();
        tempLongs = new ArrayList<Double>();
        teeLat = scatter.getTeeLatitude();
        teeLong = scatter.getTeeLongitude();
        targetLat = scatter.getTargetLatitude();
        targetLong = scatter.getTargetLongitude();
        bearingList = new ArrayList<Double>();
        distanceList = new ArrayList<Double>();

        for(Ball b : scatter.getBallList()){
            ballLats.add(b.getLatitude());
            ballLongs.add(b.getLongitude());
        }

        findMaxMinLatLongs();

        deltaLat = maxLat-minLat;//degrees up/down
        deltaLong = maxLong - minLong;//degrees across
        deltaX = 300.0;//300 yards across x axis
        deltaY = 300.0;//300 yards down y axis

        yScale = Math.abs(deltaY/deltaLat);//num lat degrees per pixel
        xScale = Math.abs(deltaX/(Math.cos(teeLat)*deltaLong));//num long degrees per pixel

        double tx = ((Math.abs(teeLong-minLong)) * xScale);
        double ty = ((Math.abs(teeLat-maxLat)) * yScale);
        teePoint = new PointF((float)tx, (float)ty);

        if(targetLat != null && targetLong != null) {
            double trgx = ((Math.abs(targetLong - minLong)) * xScale);
            double trgy = ((Math.abs(targetLat - maxLat)) * yScale);
            targetPoint = new PointF((float) trgx, (float) trgy);
        }

        for(int i = 0; i<ballLats.size(); i++){
//            double x = (testLongs.get(i)+180)*(width/360);
//            double latRad = testLats.get(i)*Math.PI/180;
//            double mercN = Math.log(Math.tan((Math.PI/4)+(latRad/2)));
//            double y = (height/2)-(height*mercN/(2*Math.PI));

            double x = (Math.abs(ballLongs.get(i) - minLong)) * xScale;
            double y = (Math.abs(ballLats.get(i) - maxLat)) * yScale;
            pointList.add(new PointF((float)x, (float)y));

            //Bearing stuff below.....
            Location l = new Location("");
            l.setLatitude(ballLats.get(i));
            l.setLongitude(ballLongs.get(i));

            Location t = new Location("");
            t.setLatitude(teeLat);
            t.setLongitude(teeLong);

            double bearing = t.bearingTo(l);
            bearingList.add(bearing);
            double distance = getDistance(t,l);
            distanceList.add(distance);

        }

        double sum = 0;
        for(Double b : bearingList){
            sum += b;
        }
        averageBearing = sum/bearingList.size();

        for(int i=0; i<bearingList.size(); i++){
            double temp = bearingList.get(i);
            temp = temp - averageBearing;
            bearingList.set(i,temp);
        }

    }

    private void findMaxMinLatLongs(){

        if(ballLats.size()>0 && ballLongs.size()>0) {
            tempLats = ballLats;
            tempLongs = ballLongs;
        }
        if(teeLat != null && teeLong != null){
            tempLats.add(teeLat);
            tempLongs.add(teeLong);
        }
        if(targetLat != null && targetLong != null) {
            tempLats.add(targetLat);
            tempLongs.add(targetLong);
        }

        Collections.sort(tempLats);
        Collections.sort(tempLongs);
        maxLat = tempLats.get(tempLats.size()-1)+0.00015;
        maxLong = tempLongs.get(tempLongs.size()-1)+0.00015;
        minLat = tempLats.get(0)-0.00015;
        minLong = tempLongs.get(0)-0.00015;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_scatter, container, false);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.scatterLinLayout);
//        linearLayout.addView(new Dot(getActivity(),100,100));

        return rootView;
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
