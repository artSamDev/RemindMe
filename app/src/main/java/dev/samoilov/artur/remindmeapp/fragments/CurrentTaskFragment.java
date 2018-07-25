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


public class CurrentTaskFragment extends TaskFragment {

    public CurrentTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        recyclerView = rootView.findViewById(R.id.rvTaskFragment);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CurrentTaskAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


}
