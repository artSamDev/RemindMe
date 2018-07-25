package dev.samoilov.artur.remindmeapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;

import dev.samoilov.artur.remindmeapp.MainActivity;
import dev.samoilov.artur.remindmeapp.adapters.CurrentTaskAdapter;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public abstract class TaskFragment extends android.support.v4.app.Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected CurrentTaskAdapter adapter;

    public MainActivity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null){
            activity = (MainActivity) getActivity();
        }

        addTaskFromDB();
    }

    public void addTask(ModelTask newTask, boolean saveToDB){
        int position = -1;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            if (adapter.getItem(i).isTask()){
                ModelTask task = (ModelTask) adapter.getItem(i);
                if (newTask.getDate()< task.getDate()){
                    position = i;
                    break;
                }
            }
        }
        if (position!= -1){
            adapter.addItem(position, newTask);
        }else{
            adapter.addItem(newTask);
        }

        if (saveToDB){
            activity.dbHelper.saveeTask(newTask);
        }
    }

    public abstract void addTaskFromDB();
    public abstract void moveTask(ModelTask modelTask);
}
