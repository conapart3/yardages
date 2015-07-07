package com.yardages;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Conal on 17/06/2015.
 */
public class ClubCardViewHolder extends RecyclerView.ViewHolder{

    protected TextView vName;

    public ClubCardViewHolder(View v) {
        super(v);
        vName = (TextView) v.findViewById(R.id.txtName);
    }
}
