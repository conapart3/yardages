package com.yardages;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
    private static final String ARG_PARAM1 = "param1";
    private long scatterId;

    private Dot dot;
    private Scatter scatter;

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
        args.putLong(ARG_PARAM1, scatterId);
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
            scatterId = getArguments().getLong(ARG_PARAM1);
        }

        dot = new Dot(getActivity(), 200, 100);
//        setContentView(dot);
    }

//    @Override
//    protected void onDraw(Canvas canvas){
//        super.onDraw(canvas);
//        Bitmap b = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
//        Canvas c = new Canvas(b);
//        dot = new Dot(getActivity(), 100, 100);
//        dot.drawBitmap(c);
//
//    }

    @Override
    public void onStart(){
        super.onStart();

        YardagesDbHelper dbHelper = new YardagesDbHelper(getActivity());
        scatter = dbHelper.getScatter(scatterId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scatter, container, false);
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
