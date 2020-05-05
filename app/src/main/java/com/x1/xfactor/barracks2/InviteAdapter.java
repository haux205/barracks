package com.x1.xfactor.barracks2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InviteAdapter extends RecyclerView.Adapter<InviteHolder> {
    private Context mContext;
    private List<Invite> mlist;
    public InviteAdapter(Context mContext,List<Invite> mlist){
        this.mContext=mContext;

   this.mlist=mlist;


    }

    @NonNull
    @Override
    public InviteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       LayoutInflater inflater = LayoutInflater.from(mContext);
       View view =inflater.inflate(R.layout.cards,null);

        return new InviteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteHolder holder, int position) {
Invite nInvite =mlist.get(position);
holder.modeText.setText("Mode:"+nInvite.getmMode());
holder.mapText.setText("Map:"+nInvite.getmMap());
holder.teamText.setText("Team:"+nInvite.getmTeam());
holder.timeText.setText("Time:"+String.valueOf(nInvite.getmHours())+" : "+String.valueOf(nInvite.getmMins()));
holder.nameText.setText("BY,"+nInvite.getmName());





    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
