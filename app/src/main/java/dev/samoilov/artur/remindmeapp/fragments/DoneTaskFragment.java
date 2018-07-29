package dev.samoilov.artur.remindmeapp.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.adapters.DoneTaskAdapter;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class DoneTaskFragment extends TaskFragment {

    OnTaskRestoreListener onTaskRestoreListener;

    public DoneTaskFragment() {
        // Required empty public constructor
    }

    public interface OnTaskRestoreListener{
        void onTaskRestore(ModelTask task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            onTaskRestoreListener = (OnTaskRestoreListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + "must implement OnTaskRestoreListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_done, container, false);
        recyclerView = rootView.findViewById(R.id.rvDoneTaskFragment);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DoneTaskAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void moveTask(ModelTask task) {
        onTaskRestoreListener.onTaskRestore(task);
    }
}
