package dev.samoilov.artur.remindmeapp.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import dev.samoilov.artur.remindmeapp.adapters.CurrentTaskAdapter;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public abstract class TaskFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected CurrentTaskAdapter adapter;


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
