package dev.samoilov.artur.remindmeapp.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import dev.samoilov.artur.remindmeapp.MainActivity;
import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.adapters.TaskAdapter;
import dev.samoilov.artur.remindmeapp.model.Item;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public abstract class TaskFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected TaskAdapter adapter;

    public MainActivity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        addTaskFromDB();
    }

    public void addTask(ModelTask newTask, boolean saveToDB) {
        int position = -1;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isTask()) {
                ModelTask task = (ModelTask) adapter.getItem(i);
                if (newTask.getDate() < task.getDate()) {
                    position = i;
                    break;
                }
            }
        }
        if (position != -1) {
            adapter.addItem(position, newTask);
        } else {
            adapter.addItem(newTask);
        }

        if (saveToDB) {
            activity.dbHelper.saveTask(newTask);
        }
    }

    public void removeTaskDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.dialog_removed_task);

        Item item = adapter.getItem(position);

        ModelTask removingTask = (ModelTask) item;
        final long timeStamp = removingTask.getTimeStamp();
        final boolean[] isRemoved = {false};

        builder.setPositiveButton(R.string.removed_task, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.removeItem(position);
                isRemoved[0] = true;

                Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.content_frame)
                        , R.string.removed_task, Snackbar.LENGTH_LONG);
                snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addTask(activity.dbHelper.query().getTask(timeStamp), false);
                        isRemoved[0] = false;

                    }
                });

                snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {

                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        if (isRemoved[0]) {
                            activity.dbHelper.removeTask(timeStamp);
                        }
                    }
                });

                snackbar.show();

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    public abstract void addTaskFromDB();

    public abstract void moveTask(ModelTask task);

}
