package dev.samoilov.artur.remindmeapp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class DBQueryManager {

    private SQLiteDatabase database;

    public DBQueryManager(SQLiteDatabase database) {
        this.database = database;
    }

    public List<ModelTask> getTasks(String selection, String[] selectionArgs, String orderBy) {
        List<ModelTask> tasks = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TASKS_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_TASK_COLUMN));
                long date = cursor.getLong(cursor.getColumnIndex(DBHelper.DATE_TASK_COLUMN));
                long time_stump = cursor.getLong(cursor.getColumnIndex(DBHelper.TIME_TASK_COLUMN));
                int priority = cursor.getInt(cursor.getColumnIndex(DBHelper.PRIORITY_TASK_COLUMN));
                int status = cursor.getInt(cursor.getColumnIndex(DBHelper.STATUS_TASK_COLUMN));

                ModelTask modelTask = new ModelTask(title,date,priority,status,time_stump);
                tasks.add(modelTask);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return tasks;

    }
}
