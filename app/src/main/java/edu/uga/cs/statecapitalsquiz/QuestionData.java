package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QuestionData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper questionDbHelper;
    private static final String[] allColumns = {
            QuestionDBHelper.QUESTIONS_COLUMN_ID,
            QuestionDBHelper.QUESTIONS_COLUMN_STATE,
            QuestionDBHelper.QUESTIONS_COLUMN_ANSWER1,
            QuestionDBHelper.QUESTIONS_COLUMN_ANSWER2,
            QuestionDBHelper.QUESTIONS_COLUMN_ANSWER3,
    };

    public QuestionData( Context context ) {
        this.questionDbHelper = QuestionDBHelper.getInstance(context);
    }

    public void open() {
        db = questionDbHelper.getWritableDatabase();
    }

    public void close() {
        if( questionDbHelper != null ) {
            questionDbHelper.close();
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    public List<Question> retrieveAllQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query(QuestionDBHelper.TABLE_QUESTIONS, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 5) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex(QuestionDBHelper.QUESTIONS_COLUMN_ID );
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex(QuestionDBHelper.QUESTIONS_COLUMN_STATE);
                        String state = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex(QuestionDBHelper.QUESTIONS_COLUMN_ANSWER1);
                        String answer1 = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex(QuestionDBHelper.QUESTIONS_COLUMN_ANSWER2);
                        String answer2 = cursor.getString( columnIndex );
                        columnIndex = cursor.getColumnIndex(QuestionDBHelper.QUESTIONS_COLUMN_ANSWER3);
                        String answer3 = cursor.getString( columnIndex );

                        // create a new JobLead object and set its state to the retrieved values
                        Question question = new Question(state, answer1, answer2, answer3);
                        question.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        questions.add(question);
                    }
                }
            }
        }
        catch(Exception e) {
            Log.d("Exception", "Exception caught");
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return questions;
    }
}
