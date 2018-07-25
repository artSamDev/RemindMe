package dev.samoilov.artur.remindmeapp.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class DBUpdateManager {

    SQLiteDatabase database;

    public DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }

    public void title(long timeStamp, String title){
        update(DBHelper.TITLE_TASK_COLUMN,timeStamp,title);
    }

    public void date(long timeStamp, long date){
        update(DBHelper.DATE_TASK_COLUMN,timeStamp,date);
    }

    public void priority(long timeStamp, int priority){
        update(DBHelper.DATE_TASK_COLUMN,timeStamp,priority);
    }

    public void status(long timeStamp, int status){
        update(DBHelper.DATE_TASK_COLUMN,timeStamp,status);
    }

    public void task(ModelTask modelTask){
        title(modelTask.getTime_stump(),modelTask.getTitle());
        date(modelTask.getTime_stump(),modelTask.getDate());
        priority(modelTask.getTime_stump(),modelTask.getPriority());
        status(modelTask.getTime_stump(),modelTask.getStatus());
    }

    private void update(String column, long key, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        database.update(DBHelper.TASKS_TABLE, contentValues, DBHelper.TIME_TASK_COLUMN + " = " + key, null);
    }

    private void update(String column, long key, long value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        database.update(DBHelper.TASKS_TABLE, contentValues, DBHelper.TIME_TASK_COLUMN + " = " + key, null);
    }
}
