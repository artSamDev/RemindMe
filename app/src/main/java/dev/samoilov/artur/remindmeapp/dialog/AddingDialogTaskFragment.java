package dev.samoilov.artur.remindmeapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.Utils;

public class AddingDialogTaskFragment extends DialogFragment implements IonDateSelectorListner {

    EditText edTitle,edDate,edTime;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_task, null);

        TextInputLayout tilTitle = view.findViewById(R.id.tilTaskTitle);
        edTitle = tilTitle.getEditText();

        TextInputLayout tilDate = view.findViewById(R.id.tilTaskDate);
        edDate = tilDate.getEditText();

        TextInputLayout tilTime = view.findViewById(R.id.tilTaskTime);
        edTime = tilTime.getEditText();

        builder.setView(view);

        return super.onCreateDialog(savedInstanceState);
    }


    @Override
    public void onDateSelected(String a) {
        edDate.setText(a);
    }
}
