package dev.samoilov.artur.remindmeapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.Utils;
import dev.samoilov.artur.remindmeapp.model.Item;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class CurrentTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

    List<Item> items = new ArrayList<>();

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int position, Item item) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_TASK:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_task, parent, false);
                TextView title = view.findViewById(R.id.tvTaskTitle);
                TextView date = view.findViewById(R.id.tvTaskDate);
                return new TaskViewHolder(view, title, date);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            ModelTask modelTask = (ModelTask) item;
            TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            taskViewHolder.title.setText(modelTask.getTask() );
            if (modelTask.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(modelTask.getDate()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()) {
            return TYPE_TASK;
        } else {
            return TYPE_SEPARATOR;
        }
    }

     private class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title, date;

        public TaskViewHolder(View itemView, TextView title, TextView date) {
            super(itemView);

            this.title = title;
            this.date = date;
        }
    }
}
