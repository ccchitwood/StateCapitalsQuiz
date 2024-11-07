package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HistoryDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "history.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_HISTORY = "history";
    public static final String HISTORY_COLUMN_ID = "_id";
    public static final String HISTORY_COLUMN_DATE = "date";
    public static final String HISTORY_COLUMN_SCORE = "score";

    // This is a reference to the only instance for the helper.
    private static HistoryDBHelper helperInstance;

    // A Create table SQL statement to create a table for quizzes.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_HISTORY =
            "create table " + TABLE_HISTORY + " ("
                    + HISTORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + HISTORY_COLUMN_DATE + " INTEGER, " // store the timestamp as an integer
                    + HISTORY_COLUMN_SCORE + " INTEGER"
                    + ")";

    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private HistoryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public static synchronized HistoryDBHelper getInstance(Context context) {
        // check if the instance already exists and if not, create the instance
        if(helperInstance == null) {
            helperInstance = new HistoryDBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHelper", "Creating database/table");
        db.execSQL(CREATE_HISTORY);
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_HISTORY);
        onCreate(db);
    }
}
