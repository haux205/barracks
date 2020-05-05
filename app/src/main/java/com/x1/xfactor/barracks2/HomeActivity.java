package com.x1.xfactor.barracks2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.RingtoneManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.support.v7.widget.LinearLayoutManager;

import android.content.Context;

import android.net.Uri;
import android.media.AudioAttributes;
import android.app.Notification;
import android.app.NotificationManager;
import  android.app.NotificationChannel;
import android.media.AudioManager;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.graphics.Color;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

interface ClickListener{
    public void onClick(View view,int position);
    public void onLongClick(View view,int position);
}

public class HomeActivity extends FragmentActivity {

    private FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseInviteRef;
    private ChildEventListener mInviteListener;
    Button mButton ,mButton2;
    Context context;
public static  int i=-1;
public static int pos;
    RecyclerView recycle;//new
    InviteAdapter adapter;
    //private List<Invite> inviteList;
private  List<Invite> mList;
Calendar calref,calref2;
Date d,date;

    public static final int RC_SIGN_IN = 123;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance(); //database instance
       mDatabaseInviteRef = mDatabase.getReference().child("invites");
        mFireBaseAuth = FirebaseAuth.getInstance(); //firebase auth instance





/*
Calendar calref2 =Calendar.getInstance();
calref.set(Calendar.YEAR,calref2.get(Calendar.YEAR));
calref.set(Calendar.MONTH,calref2.get(Calendar.MONTH));
calref.set(Calendar.DAY_OF_MONTH,calref.get(Calendar.DAY_OF_MONTH)); */

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

// The id of the channel.
        String id = "my_channel_01";

// The user-visible name of the channel.
        CharSequence name = getString(R.string.common_google_play_services_notification_channel_name);

// The user-visible description of the channel.
        String description = getString(R.string.mr_button_content_description);

        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(id, name,importance);

// Configure the notification channel.
        mChannel.setDescription(description);

        mChannel.enableLights(true);
// Sets the notification light color for notifications posted to this
// channel, if the device supports this feature.
        mChannel.setLightColor(Color.RED);

        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
Uri sounduri= Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.ring);
mChannel.setSound(sounduri,new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT).build());

        mNotificationManager.createNotificationChannel(mChannel);

        // Recycling shit starts
        recycle = (RecyclerView) findViewById(R.id.recycle);
        recycle.setHasFixedSize(true);
        mList = new ArrayList<>();




        adapter = new InviteAdapter(this, mList);

        //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        //((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager lin = new LinearLayoutManager(this);
        lin.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(lin);



        recycle.setAdapter(adapter);




        mButton = (Button) findViewById(R.id.formbutton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(v.getContext(), FormActivity.class);
                startActivity(mIntent);
            }
        });
        mButton2 = (Button) findViewById(R.id.signoutbutton);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               signout();
            }
        });
        }



    @Override
    protected void onStart() {
        super.onStart();
        mList.clear();
        FirebaseUser user = mFireBaseAuth.getCurrentUser();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    //if user is signed in
                    Toast.makeText(HomeActivity.this,"YOU ARE SIGNED IN !!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //if user is not signed in
                    startActivityForResult(
                            // Get an instance of AuthUI based on the default app
                            AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).build(),
                            RC_SIGN_IN);
                }
            }


        };

        mDatabaseInviteRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
Invite obj= new Invite();


    mList.add(dataSnapshot.getValue(Invite.class));

    adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



recycle.addOnItemTouchListener(new RecyclerTouchListener(this,
        recycle, new ClickListener() {
    @Override
    public void onClick(View view, int position) {


    }

    @Override
    public void onLongClick(View view, int position) {
        Invite obj = new Invite();

        obj = mList.get(position);
        int hours = obj.getmHours();
        int mins = obj.getmMins();


        Toast.makeText(HomeActivity.this,"Alarm set for "+hours+"hours and " +""+mins+"mins",Toast.LENGTH_SHORT).show();
        scheduleNotification(getNotification(), hours,mins);
    }
}));
    }

    @Override
    protected void onPause() {
        super.onPause();
mFireBaseAuth.removeAuthStateListener(mAuthStateListener);

    }


    @Override
    protected void onResume() {
        super.onResume();
      mFireBaseAuth.addAuthStateListener(mAuthStateListener);



    }

    @Override//menu inflater
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override //menu buttons
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.SIGN_OUT:
                AuthUI.getInstance().signOut(this);
        }
        return super.onOptionsItemSelected(item);

    }

    private void scheduleNotification(Notification notification, int h, int m) {
i=i+1;
        Intent notificationIntent = new Intent(this, NotificationReciever.class);
        notificationIntent.putExtra(NotificationReciever.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationReciever.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
Calendar cal = Calendar.getInstance();
cal.set(Calendar.HOUR_OF_DAY,h);
cal.set(Calendar.MINUTE,m);
        Toast.makeText(HomeActivity.this,""+cal.get(Calendar.HOUR_OF_DAY) +">"+cal.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
        long futureInMillis = cal.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
    }
    public Notification getNotification() {
        String CHANNEL_ID = "my_channel_01";
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Let's go !!!!!!");
        builder.setContentText("Your Squad is waiting !!!!! Good Hunting !!!!");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setChannelId(CHANNEL_ID);
        return builder.build();
    }
private void signout(){
    AuthUI.getInstance().signOut(this);
}

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}




