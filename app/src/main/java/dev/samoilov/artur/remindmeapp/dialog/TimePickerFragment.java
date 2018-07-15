package dev.samoilov.artur.remindmeapp.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import dev.samoilov.artur.remindmeapp.R;

public class TimePickerFragment extends DialogFragment{

    TimePickerDialog.OnTimeSetListener callBack;

    public void setTime(TimePickerDialog.OnTimeSetListener callBack){
        this.callBack = callBack;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.DialogTheme,callBack,hour,minute, true);
    }

}
