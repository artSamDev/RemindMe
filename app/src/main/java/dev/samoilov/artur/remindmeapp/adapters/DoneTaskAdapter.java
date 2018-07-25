package dev.samoilov.artur.remindmeapp.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.samoilov.artur.remindmeapp.R;
import dev.samoilov.artur.remindmeapp.Utils;
import dev.samoilov.artur.remindmeapp.fragments.CurrentTaskFragment;
import dev.samoilov.artur.remindmeapp.model.Item;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class DoneTaskAdapter extends TaskAdapter {

    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

    public DoneTaskAdapter(CurrentTaskFragment currentTaskFragment) {
        super(currentTaskFragment);
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
                CircleImageView circleImageView = view.findViewById(R.id.spTaskPriority);
                return new TaskAdapter.TaskViewHolder(view, title, date, circleImageView);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            final ModelTask modelTask = (ModelTask) item;
            final TaskAdapter.TaskViewHolder taskViewHolder = (TaskAdapter.TaskViewHolder) holder;

            final View itemView = taskViewHolder.itemView;
            final Resources resources = itemView.getResources();

            taskViewHolder.title.setText(modelTask.getTitle());
            if (modelTask.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(modelTask.getDate()));
            } else {
                taskViewHolder.date.setText(null);
            }

            itemView.setVisibility(View.VISIBLE);
            itemView.setBackgroundColor(resources.getColor(R.color.grey_200));

            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_disable_text));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_disable_text));
            taskViewHolder.priority.setImageResource(R.drawable.check_circle);
            taskViewHolder.priority.setColorFilter(resources.getColor(modelTask.getPriorityColor()));

            taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelTask.setStatus(ModelTask.STATUS_CURRENT);

                    getTaskFragment().activity.dbHelper.update().status(modelTask.getTime_stump(), ModelTask.STATUS_CURRENT);

                    itemView.setBackgroundColor(resources.getColor(R.color.grey_50));

                    taskViewHolder.title.setTextColor(resources.getColor(android.R.color.primary_text_light));
                    taskViewHolder.date.setTextColor(resources.getColor(android.R.color.secondary_text_light));
                    taskViewHolder.priority.setColorFilter(resources.getColor(modelTask.getPriorityColor()));

                    ObjectAnimator anim = ObjectAnimator.ofFloat(taskViewHolder.priority, "rotationY", -180f, 0f);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                        }

                        @Override
                        public void onAnimationPause(Animator animation) {
                            super.onAnimationPause(animation);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (modelTask.getStatus() == ModelTask.STATUS_DONE){
                                taskViewHolder.priority.setImageResource(R.drawable.check_circle);

                                ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                        "translationX", 0f, itemView.getWidth());
                                ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                        "translationX", itemView.getWidth(), 0f);

                                translationX.addListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                        super.onAnimationCancel(animation);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        itemView.setVisibility(View.GONE);
                                        getTaskFragment().moveTask(modelTask);
                                        removeItem(taskViewHolder.getLayoutPosition());
                                        super.onAnimationEnd(animation);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                        super.onAnimationRepeat(animation);
                                    }

                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        super.onAnimationStart(animation);
                                    }
                                });

                                AnimatorSet translationSet = new AnimatorSet();
                                translationSet.play(translationX).before(translationXBack);
                                translationSet.start();


                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()) {
            return TYPE_TASK;
        } else {
            return TYPE_SEPARATOR;
        }
    }
}
