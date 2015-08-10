package com.yardages;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conal on 17/06/2015.
 */
public class ClubCardAdapter extends RecyclerView.Adapter<ClubCardAdapter.ClubCardViewHolder> {

    private static ClickListener clickListener;
    private ArrayList<Scatter> scatterList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ClubCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView vDescription;
        protected ImageView vImg;
        protected String vDate;
        protected String vAccuracy;

        public ClubCardViewHolder(View v) {
            super(v);
            vDescription = (TextView) v.findViewById(R.id.descriptionTxt);
            vImg = (ImageView) v.findViewById(R.id.cardImg);
            v.setOnClickListener(this);
//            v.setOnLongClickListener(this);
        }

//        @Override
        public void onClick(View v){
            clickListener.onItemClick(getPosition(),v);
        }

    }

    public void setOnItemClickListener(ClickListener clickListener){
        ClubCardAdapter.clickListener = clickListener;
    }

    public interface ClickListener{
        void onItemClick(int position, View v);
    }

    public ClubCardAdapter(ArrayList<Scatter> scatterList){
        this.scatterList = scatterList;
    }



    @Override
    public ClubCardAdapter.ClubCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //create new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        //set view's size, margins, paddings, layout params

        ClubCardViewHolder vh = new ClubCardViewHolder(itemView);

        return vh;
    }

    //replace contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(ClubCardViewHolder holder, int position) {
//        holder.mTextView.setText(mDataset[position]);

        holder.vDescription.setText(scatterList.get(position).getDescription());
        holder.vImg.setImageResource(R.drawable.ardglass);
    }

    @Override
    public int getItemCount() {
        return scatterList.size();
    }


}
