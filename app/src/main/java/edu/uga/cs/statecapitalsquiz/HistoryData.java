package edu.uga.cs.statecapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HistoryData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper historyDbHelper;
    private static final String[] allColumns = {
            HistoryDBHelper.HISTORY_COLUMN_ID,
            HistoryDBHelper.HISTORY_COLUMN_DATE,
            HistoryDBHelper.HISTORY_COLUMN_SCORE,
    };

    public HistoryData(Context context) {
        this.historyDbHelper = HistoryDBHelper.getInstance(context);
    }

    public void open() {
        db = historyDbHelper.getWritableDatabase();
    }

    public void close() {
        if( historyDbHelper != null ) {
            historyDbHelper.close();
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    public List<History> retrieveAllResults() {
        ArrayList<History> histories = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query(HistoryDBHelper.TABLE_HISTORY, allColumns,
                    null, null, null, null, null );

            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 3) {

                        // Get all the attributes of this quiz
                        columnIndex = cursor.getColumnIndex(HistoryDBHelper.HISTORY_COLUMN_ID);
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex(HistoryDBHelper.HISTORY_COLUMN_DATE);
                        int date = cursor.getInt( columnIndex );
                        columnIndex = cursor.getColumnIndex(HistoryDBHelper.HISTORY_COLUMN_SCORE);
                        int score = cursor.getInt( columnIndex );

                        // Create a new History object
                        History history = new History(date, score);
                        history.setId(id); // Set the id of the history object
                        // Add it to the quiz history database
                        histories.add(history);
                    }
                }
            }
            Log.d("HistoryData", "Number of results retrieved: " + histories.size());
        }
        catch(Exception e) {
            Log.d("Exception", "Exception caught");
        }
        finally{
            // Closing the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // Return a list of all completed quizzes
        return histories;
    }

    public History storeResults(History history) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the History argument.
        // This is how we are providing persistence to a History (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put(HistoryDBHelper.HISTORY_COLUMN_DATE, history.getDate());
        values.put(HistoryDBHelper.HISTORY_COLUMN_SCORE, history.getScore());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert(HistoryDBHelper.TABLE_HISTORY, null, values);

        // Store the id (the primary key) in the History instance, as it is now persistent
        history.setId(id);

        return history;
    }
}
