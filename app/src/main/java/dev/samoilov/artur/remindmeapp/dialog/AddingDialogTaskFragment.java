package dev.samoilov.artur.remindmeapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.Utils;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class AddingDialogTaskFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText edTitle, edDate, edTime;
    private AddingTaskListener addingTaskListener;
    private Calendar calendar;

    public interface AddingTaskListener {

        void onTaskAdded(ModelTask newTask);

        void onTaskAddingCancel();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implements AddingTaskistener");
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout tilTitle = view.findViewById(R.id.tilTaskTitle);
        edTitle = tilTitle.getEditText();

        final TextInputLayout tilDate = view.findViewById(R.id.tilTaskDate);
        edDate = tilDate.getEditText();

        TextInputLayout tilTime = view.findViewById(R.id.tilTaskTime);
        edTime = tilTime.getEditText();

        builder.setView(view);

        final ModelTask task = new ModelTask();

        calendar = Calendar.getInstance();

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerDialog = new DatePickerFragment();
                datePickerDialog.setCallBack(AddingDialogTaskFragment.this);
                datePickerDialog.show(getFragmentManager(), "DatePickerFragment");
            }
        });

        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setTime(AddingDialogTaskFragment.this);
                timePickerFragment.show(getFragmentManager(), "TimePickerFragment");
            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setTask(edTitle.getText().toString());
                if (edDate.length()!=0 || edTime.length()!=0){
                    task.setDate(calendar.getTimeInMillis());
                }
                 addingTaskListener.onTaskAdded(task);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingTaskListener.onTaskAddingCancel();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(getResources().getColor(R.color.colorPrimary));
                if (edTitle.length() == 0) {
                    buttonPositive.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.title_is_emty));
                }

                edTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            buttonPositive.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.title_is_emty));
                        } else {
                            buttonPositive.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }
        });

        return alertDialog;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(year,month,dayOfMonth);
        edDate.setText(Utils.getDate(calendar.getTimeInMillis()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        edTime.setText(Utils.getTime(calendar.getTimeInMillis()));
    }
}
