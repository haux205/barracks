package com.x1.xfactor.barracks2;

import com.google.firebase.database.ServerValue;

import java.util.Calendar;
import java.util.Date;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class Invite {

    public String mMode;
    public String mTeam;
    public String mMap;
    public int mHours;
    public int  mMins;
public String mName;
public  String mUid;
public  int y ,m,d;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Invite() {
    }

    public Invite(String mMode, String mTeam, String mMap, int mHours, int mMins, String mName, String mUid ,int y,int m,int d) {
        this.mMode=mMode;
        this.mTeam=mTeam;
       this.mMap=mMap;
        this.mHours=mHours;
        this.mMins=mMins;
this.mName=mName;

this.mUid=mUid;
this.y=y;
this.m=m;
this.d=d;




    }
    public String getmMode(){
return mMode;
    }
    public String getmTeam(){
        return  mTeam;
    }

    public String getmMap() {
        return mMap;
    }

    public int getmHours() {
        return mHours;
    }

    public int getmMins() {
        return mMins;
    }
    public  String getmName(){ return mName;}

    public String getmUid(){return  mUid;}
    public  int getY(){return  y;}
    public  int getM(){return  m;}
    public  int getD(){return d; }

    //set methods
    public void setmMode(String mMode) {
        this.mMode = mMode;
    }

    public void setmTeam(String mTeam) {
        this.mTeam = mTeam;
    }

    public void setmMap(String mMap) {
        this.mMap = mMap;
    }

    public void setmHours(int mHours) {
        this.mHours = mHours;
    }

    public void setmMins(int mMins) {
        this.mMins = mMins;
    }

}
