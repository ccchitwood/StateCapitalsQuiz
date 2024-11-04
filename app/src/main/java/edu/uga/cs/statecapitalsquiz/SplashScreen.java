package edu.uga.cs.statecapitalsquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashScreen extends AppCompatActivity {

    private QuestionData questionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        questionData = new QuestionData(this);
        questionData.open();

        Button startQuizButton = findViewById(R.id.button);
        Button viewPastQuizzesButton = findViewById(R.id.button2);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QuestionLoader(questionData).execute();
            }
        });
        viewPastQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // filler
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (questionData != null) {
            questionData.close();
        }
    }

    public class QuestionLoader extends AsyncTask<Question, Question> {

        private QuestionData questionData;

        public QuestionLoader(QuestionData questionData) {
            this.questionData = questionData;
        }

        protected Question doInBackground(Question... questions) {
            try {
                InputStream inputStream = getAssets().open("state_capitals.csv");
                CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
                String[] nextRow;
                reader.readNext();
                while ((nextRow = reader.readNext()) != null) {
                    Question question = new Question();
                    question.setState(nextRow[0]);
                    question.setAnswer1(nextRow[1]);
                    question.setAnswer2(nextRow[2]);
                    question.setAnswer3(nextRow[3]);

                    questionData.storeQuestion(question);
                }
                reader.close();
            } catch (Exception e) {
                Log.e("InputStream Error","Exception in Reading CSV File");
            }
            return new Question();
        }

        protected void onPostExecute (Question question) {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
