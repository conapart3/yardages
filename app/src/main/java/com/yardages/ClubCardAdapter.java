package com.yardages;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Conal on 17/06/2015.
 */
public class ClubCardAdapter extends RecyclerView.Adapter<ClubCardViewHolder> {

    private List<ClubCard> clubCardList;

    public ClubCardAdapter(List<ClubCard> clubCardList){
        this.clubCardList = clubCardList;
    }

    @Override
    public ClubCardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card, viewGroup, false);
        return new ClubCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClubCardViewHolder clubCardViewHolder, int i) {
        ClubCard c = clubCardList.get(i);
        clubCardViewHolder.vName.setText(c.name);

    }

    @Override
    public int getItemCount() {
        return clubCardList.size();
    }


}
