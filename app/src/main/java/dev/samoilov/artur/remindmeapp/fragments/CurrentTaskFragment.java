package dev.samoilov.artur.remindmeapp.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.adapters.CurrentTaskAdapter;
import dev.samoilov.artur.remindmeapp.model.ModelTask;


public class CurrentTaskFragment extends TaskFragment {

    OnTaskDoneListener onTaskDoneListener;

    public CurrentTaskFragment() {
        // Required empty public constructor
    }

    public interface OnTaskDoneListener {
        void onTaskDone(ModelTask task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onTaskDoneListener = (OnTaskDoneListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTaskDoneListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current, container, false);

        recyclerView = rootView.findViewById(R.id.rvCurrentTaskFragment);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CurrentTaskAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    @Override
    public void moveTask(ModelTask task) {
        onTaskDoneListener.onTaskDone(task);
    }
}
