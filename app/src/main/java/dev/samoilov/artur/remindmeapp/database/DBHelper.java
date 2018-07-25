package dev.samoilov.artur.remindmeapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import dev.samoilov.artur.remindmeapp.model.ModelTask;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "remindme_database";

    public static final String TASKS_TABLE = "tasks_table";

    public static final String TITLE_TASK_COLUMN = "task_title";
    public static final String DATE_TASK_COLUMN = "task_date";
    public static final String TIME_TASK_COLUMN = "task_time";
    public static final String PRIORITY_TASK_COLUMN = "task_priority";
    public static final String STATUS_TASK_COLUMN = "task_status";

    public static final String SELECTION_STATUS = DBHelper.STATUS_TASK_COLUMN + " = ?";

    private static final String TASKS_CREATE_TABLE = "CREATE TABLE "
            + TASKS_TABLE + " (" + BaseColumns._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_TASK_COLUMN + " TEXT NOT NULL,"
            + DATE_TASK_COLUMN + " LONG, "
            + PRIORITY_TASK_COLUMN + " INTEGER, "
            + STATUS_TASK_COLUMN + " INTEGER, "
            + TIME_TASK_COLUMN + " LONG);";

    private DBQueryManager dbQueryManager;
    private DBUpdateManager dbUpdateManager;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbQueryManager = new DBQueryManager(getReadableDatabase());
        dbUpdateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TASKS_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TASKS_TABLE);
        onCreate(db);
    }

    public void saveeTask(ModelTask task){
        ContentValues newValues = new ContentValues();

        newValues.put(TITLE_TASK_COLUMN, task.getTitle());
        newValues.put(DATE_TASK_COLUMN, task.getDate());
        newValues.put(TIME_TASK_COLUMN, task.getTime_stump());
        newValues.put(STATUS_TASK_COLUMN, task.getStatus());
        newValues.put(PRIORITY_TASK_COLUMN, task.getPriority());

        getWritableDatabase().insert(TASKS_TABLE,null, newValues);

    }

    public DBQueryManager query(){
        return dbQueryManager;
    }

    public DBUpdateManager update(){
        return dbUpdateManager;
    }
}
