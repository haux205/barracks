package com.x1.xfactor.barracks2;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class InviteHolder T {
    public TextView modeText;
    public TextView mapText;
    public TextView teamText;
    public TextView timeText;
    public TextView dateText;
    public TextView nameText;

    CardView cv;

    public InviteHolder(View itemView) {
        super(itemView);

        modeText=(TextView) itemView.findViewById(R.id.modeid);
        mapText=(TextView)itemView.findViewById(R.id.mapid);
        teamText=(TextView) itemView.findViewById(R.id.teamid);
        timeText=(TextView)itemView.findViewById(R.id.timeid);

        nameText=(TextView) itemView.findViewById(R.id.nameid);

    }
}
