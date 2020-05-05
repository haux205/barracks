package com.x1.xfactor.barracks2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;



import java.util.Calendar;
import java.util.Date;

public class FormActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, OnItemSelectedListener{
    Button mButton,mButton2;


TextView timeset;
    FirebaseDatabase mData;

    DatabaseReference mDataRef,mDataRef2,mDataref3;
RadioGroup modegroup,teamgroup ;
RadioButton rbutton,rbutton2;
EditText rEditText;
String nameValue;
    String modevalue;
    String teamvalue;
    String mapvalue;
    int hours,mins,i=1000;
 Date dateValue;
 String uid;
    Spinner spinner ;
    int y,m,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
final Calendar cal = Calendar.getInstance();


timeset=(TextView)findViewById(R.id.time);
modegroup=(RadioGroup)findViewById(R.id.Modes);
teamgroup=(RadioGroup) findViewById(R.id.Team);
spinner=(Spinner)findViewById(R.id.Maps);
rEditText=(EditText) findViewById(R.id.nameText);

modegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

    @SuppressLint("ResourceType")
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {



        if(checkedId == R.id.Classic){
            spinner3();

           rbutton=(RadioButton)findViewById(R.id.Classic);
           modevalue=rbutton.getText().toString();
        }

        else {
           spinner4();
            rbutton=(RadioButton)findViewById(R.id.Arcade);
            modevalue=rbutton.getText().toString();
        }
    }
});
teamgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId==R.id.duo)
        {
            rbutton2=(RadioButton)findViewById(R.id.duo);
            teamvalue=rbutton2.getText().toString();
        }
        else {
            rbutton2=(RadioButton)findViewById(R.id.squad);
            teamvalue=rbutton2.getText().toString();
        }
    }
});

mButton=(Button) findViewById(R.id.timeset);
mButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DialogFragment frag = new TimeFragment();
        frag.show(getFragmentManager(),"time");





    }
});
mButton2=(Button)findViewById(R.id.submit);
mButton2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
int refHours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
int refMins= Calendar.getInstance().get(Calendar.MINUTE);
/*
Calendar cal = Calendar.getInstance();
Calendar cal2 = Calendar.getInstance();
cal.set(cal2.get(Calendar.YEAR),cal2.get(Calendar.MONTH),cal2.get(Calendar.DAY_OF_MONTH));*/



nameValue=rEditText.getText().toString();


if(hours<refHours){

    Toast.makeText(FormActivity.this,"Enter valid time ",Toast.LENGTH_SHORT).show();
}
else if(hours==refHours && mins<refMins){
    Toast.makeText(FormActivity.this,"Enter valid time ",Toast.LENGTH_SHORT).show();
}
else {
    y=cal.get(Calendar.YEAR);
    m=cal.get(Calendar.MONTH);
    d=cal.get(Calendar.DAY_OF_MONTH);

    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    mDataRef = FirebaseDatabase.getInstance().getReference();
    mDataRef2 = mDataRef.child("invites");
    String keyvalue = mDataRef2.push().getKey();
    Toast.makeText(FormActivity.this, " + >"+"Unique Id >>"+FirebaseAuth.getInstance().getCurrentUser().getUid() + keyvalue, Toast.LENGTH_SHORT).show();
    mDataref3 = mDataRef.child("invites").child(keyvalue);
    Invite mInvite = new Invite(modevalue, teamvalue, mapvalue, hours, mins, nameValue,uid,y,m,d);
    mDataref3.setValue(mInvite);

scheduleNotification(getNotification(),hours,mins);
}


        Intent mIntent = new Intent(v.getContext(), HomeActivity.class);
        startActivity(mIntent);
    }
});


    }



    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hours=hourOfDay;
        mins=minute;
                                                                                  //HOUR, MIN


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

         mapvalue = spinner.getSelectedItem().toString();                                     //mapvalue

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    public void spinner3 (){


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mapsarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public  void spinner4(){


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arcademaps, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
        Toast.makeText(FormActivity.this,""+cal.get(Calendar.HOUR_OF_DAY) +">"+cal.get(Calendar.MINUTE),Toast.LENGTH_SHORT).show();
        long futureInMillis = cal.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        Toast.makeText(FormActivity.this, " The alarm for your match has been set !!!", Toast.LENGTH_SHORT).show();
    }
    public Notification getNotification() {
        String CHANNEL_ID = "my_channel_01";
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Let's go !!!!!!");
        builder.setContentText("Your Squad is waiting !!!!! Good Hunting !!!!");
        builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark).setChannelId(CHANNEL_ID);
        return builder.build();
    }
}


