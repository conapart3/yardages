package com.yardages;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private com.gc.materialdesign.views.ButtonFloat floatButton;

    private OnFragmentInteractionListener mListener;
    private ArrayList<Scatter> scatterList;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CardListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardListFragment newInstance() {
        CardListFragment fragment = new CardListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public CardListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        YardagesDbHelper dbHelper = new YardagesDbHelper(getActivity());
        scatterList = dbHelper.getScatterList();

        floatButton = (com.gc.materialdesign.views.ButtonFloat) getActivity().findViewById(R.id.buttonFloat);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.cardRecyclerView);
        //recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity()); //using linear layout manager
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ClubCardAdapter(scatterList);
        recyclerView.setAdapter(adapter);

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //use the newInstance factory method to create new instances of fragments!
                YardagesFragment yardagesFragment = YardagesFragment.newInstance();
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.container, yardagesFragment, null)
                        .addToBackStack(null)
                        .commit();

                CharSequence text = "New Scatter.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getActivity(), text, duration);
                toast.show();

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_list, container, false);
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
