package dev.samoilov.artur.remindmeapp.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.adapters.CurrentTaskAdapter;
import dev.samoilov.artur.remindmeapp.database.DBHelper;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

    public class CurrentTaskFragment extends TaskFragment {

        public CurrentTaskFragment() {
            // Required empty public constructor
        }

        OnTaskDoneListener onTaskDoneListener;

        public interface OnTaskDoneListener {
            void onTaskDone(ModelTask task);
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                onTaskDoneListener = (OnTaskDoneListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnTaskDoneListener");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_current, container, false);

            recyclerView = rootView.findViewById(R.id.rvTaskFragment);

            layoutManager = new LinearLayoutManager(getActivity());

            recyclerView.setLayoutManager(layoutManager);

            adapter = new CurrentTaskAdapter(this);
            recyclerView.setAdapter(adapter);

            return rootView;
        }


        @Override
        public void addTaskFromDB() {
            List<ModelTask> tasks = new ArrayList<>(activity.dbHelper.query().getTasks(DBHelper.SELECTION_STATUS + " OR "
                    + DBHelper.SELECTION_STATUS, new String[]{Integer.toString(ModelTask.STATUS_CURRENT),
                    Integer.toString(ModelTask.STATUS_OVERDUE)}, DBHelper.TASK_DATE_COLUMN));
            for (int i = 0; i < tasks.size(); i++) {
                addTask(tasks.get(i), false);
            }
        }

        @Override
        public void moveTask(ModelTask task) {
            onTaskDoneListener.onTaskDone(task);
        }
    }