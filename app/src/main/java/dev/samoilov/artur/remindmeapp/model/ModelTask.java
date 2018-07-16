package dev.samoilov.artur.remindmeapp.model;

public class ModelTask implements Item {

    private String task;
    private long date;

    public ModelTask() {
    }

    public ModelTask(String task, long date) {
        this.task = task;
        this.date = date;
    }

    @Override
    public boolean isTask() {
        return true;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
