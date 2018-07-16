package dev.samoilov.artur.remindmeapp.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.util.Calendar;

import dev.samoilov.artur.remindmeapp.R;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener callBack;

    public void setCallBack(DatePickerDialog.OnDateSetListener callBack){
        this.callBack = callBack;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme ,callBack, year, month, day);

        Field mDatePickerField;
        try {
            mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
            mDatePickerField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        return dialog;
    }

}

