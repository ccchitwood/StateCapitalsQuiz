package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class EndScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_screen);

        Button retryQuizButton = findViewById(R.id.button3);
        Button pastQuizzesButton = findViewById(R.id.button4);
        TextView quizScore = findViewById(R.id.textView5);

        Intent intent = new Intent();
        intent = getIntent();
        int score = intent.getIntExtra("correctAnswerCount", 0);

        if (score >= 0 && score <= 2) {
            quizScore.setText("You answered " + score + "/6 correctly. Better luck next time!");
        } else if (score >= 3 && score <= 5) {
            quizScore.setText("You answered " + score + "/6 correctly. You did pretty good!");
        } else {
            quizScore.setText("You answered " + score + "/6 correctly. You really know your capitals!");
        }

        retryQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        pastQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // filler
            }
        });
    }

    public class ResultSaver extends AsyncTask<History, History> {

        private HistoryData historyData;
        private int score;

        public ResultSaver(HistoryData historyData, int score) {
            this.historyData = historyData;
            this.score = score;
        }

        protected History doInBackground(History... histories) {

            History history = new History();
            int currentDate = (int) (System.currentTimeMillis() / 1000);
            history.setDate(currentDate);
            history.setScore(score);
            historyData.storeResults(history);

            return new History();
        }

        // CHANGE THIS
        protected void onPostExecute (History history) {
            Intent intent = new Intent(EndScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
