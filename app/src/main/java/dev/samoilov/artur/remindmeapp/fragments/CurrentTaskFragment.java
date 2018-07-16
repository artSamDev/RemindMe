package dev.samoilov.artur.remindmeapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.adapters.CurrentTaskAdapter;
import dev.samoilov.artur.remindmeapp.model.ModelTask;


public class CurrentTaskFragment extends Fragment {

    private RecyclerView rvTaskFrag;
    private RecyclerView.LayoutManager layoutManager;

    CurrentTaskAdapter adapter;


    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        rvTaskFrag = rootView.findViewById(R.id.rvTaskFragment);

        layoutManager = new LinearLayoutManager(getActivity());
        rvTaskFrag.setLayoutManager(layoutManager);

        adapter = new CurrentTaskAdapter();
        rvTaskFrag.setAdapter(adapter);

        return rootView;
    }

    public void addTask(ModelTask newTask){
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
    }

}
