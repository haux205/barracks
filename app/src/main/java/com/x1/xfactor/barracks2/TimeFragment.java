package com.x1.xfactor.barracks2;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.widget.TimePicker;
import java.util.Calendar;

public class TimeFragment extends DialogFragment implements TimePicker.OnTimeChangedListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int hour=c.get(Calendar.HOUR_OF_DAY);
        int minute=c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)getActivity(),hour,minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

    }
}
