package dev.samoilov.artur.remindmeapp.model;

import java.util.Date;

import dev.samoilov.artur.remindmeapp.R;

public class ModelTask implements Item {

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_MEDIUM = 1;
    public static final int PRIORITY_HIGH = 2;


    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    private String title;
    private long date;
    private int priority;
    private int status;
    private long timeStamp;


    public ModelTask() {
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public ModelTask(String title, long date, int priority, int status, long timeStamp) {
        this.title = title;
        this.date = date;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    //get

    public int getPriorityColor() {
        switch (getPriority()) {
            case PRIORITY_HIGH:
                if (getStatus() != STATUS_CURRENT || getStatus() != STATUS_OVERDUE) {
                    return R.color.priority_high;
                } else {
                    return R.color.priority_high_selected;
                }
            case PRIORITY_MEDIUM:
                if (getStatus() != STATUS_CURRENT || getStatus() != STATUS_OVERDUE) {
                    return R.color.priority_medium;
                } else {
                    return R.color.priority_medium_selected;
                }
            case PRIORITY_LOW:
                if (getStatus() != STATUS_CURRENT || getStatus() != STATUS_OVERDUE) {
                    return R.color.priority_low;
                } else {
                    return R.color.priority_low_selected;
                }
                default:
                    return 0;
        }
    }

    public String getTitle() {
        return title;
    }

    public long getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

    public int getStatus() {
        return status;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    //set

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
