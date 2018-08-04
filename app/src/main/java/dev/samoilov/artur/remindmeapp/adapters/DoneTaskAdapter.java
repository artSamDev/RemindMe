package dev.samoilov.artur.remindmeapp.adapters;

import android.animation.Animator;
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
import dev.samoilov.artur.remindmeapp.fragments.DoneTaskFragment;
import dev.samoilov.artur.remindmeapp.model.Item;
import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class DoneTaskAdapter extends TaskAdapter {

    private static final int TYPE_TASK = 0;

    public DoneTaskAdapter(DoneTaskFragment taskFragment) {
        super(taskFragment);
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
                CircleImageView priority = view.findViewById(R.id.cvTaskPriority);
                return new TaskViewHolder(view, title, date, priority);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Item item = items.get(position);

        if (item.isTask()) {
            holder.itemView.setEnabled(true);
            final ModelTask modelTask = (ModelTask) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            final View itemView = taskViewHolder.itemView;
            final Resources resources = itemView.getResources();

            taskViewHolder.title.setText(modelTask.getTitle());
            if (modelTask.getDate() != 0) {
                taskViewHolder.date.setText(Utils.getFullDate(modelTask.getDate()));
            } else {
                taskViewHolder.date.setText(null);
            }

            itemView.setVisibility(View.VISIBLE);

            taskViewHolder.itemView.setBackgroundColor(resources.getColor(R.color.gray_200));

            taskViewHolder.title.setTextColor(resources.getColor(R.color.disable_title_text));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.disable_date_text));
            taskViewHolder.priority.setColorFilter(resources.getColor(modelTask.getPriorityColor()));
            taskViewHolder.priority.setImageResource(R.drawable.check_circle);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    new android.os.Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getTaskFragment().removeTaskDialog(taskViewHolder.getAdapterPosition());
                        }
                    },1000);


                    return true;
                }
            });


            taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelTask.setStatus(ModelTask.STATUS_CURRENT);

                    getTaskFragment().activity.dbHelper.update().status(modelTask.getTimeStamp(),ModelTask.STATUS_CURRENT);

                    itemView.setBackgroundColor(resources.getColor(R.color.gray_50));

                    taskViewHolder.title.setTextColor(resources.getColor(android.R.color.primary_text_light));
                    taskViewHolder.date.setTextColor(resources.getColor(android.R.color.secondary_text_light));
                    taskViewHolder.priority.setColorFilter(resources.getColor(modelTask.getPriorityColor()));
                    taskViewHolder.priority.setImageResource(R.drawable.circle);



                    ObjectAnimator animator = ObjectAnimator.ofFloat(taskViewHolder.priority, "rotationY", 180f, 0f);

                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                    "translationX", 0f, -itemView.getWidth());

                            ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                    "translationX", -itemView.getWidth(), 0f);

                            translationX.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    itemView.setVisibility(View.GONE);
                                    getTaskFragment().moveTask(modelTask);
                                    removeItem(taskViewHolder.getLayoutPosition());

                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {

                                }
                            });

                            AnimatorSet translationSet = new AnimatorSet();
                            translationSet.play(translationX).before(translationXBack);
                            translationSet.start();


                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.play(animator);
                    animatorSet.start();
                }
            });
        }
    }
}
