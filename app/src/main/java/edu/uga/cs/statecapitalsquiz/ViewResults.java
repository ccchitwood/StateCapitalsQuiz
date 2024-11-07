package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ViewResults extends AppCompatActivity {

    private HistoryData historyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_results);

        // Open history database
        HistoryData historyData = new HistoryData(this);
        historyData = new HistoryData(this);
        historyData.open();

        Button startQuizButton = findViewById(R.id.button8);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewResults.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Creating parameters for the TableLayout
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        android.widget.TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT );
        layoutParams.setMargins(20, 0, 20, 0);

        List<History> quizHistory = historyData.retrieveAllResults();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss", Locale.getDefault());
        Log.d("size of quiz history", quizHistory.size() + "");
        for (int i = quizHistory.size() - 1; i >= 0; i--) {
            TableRow tableRow = new TableRow(this);

            TextView score = new TextView(this);
            score.setText(String.format("%d", quizHistory.get(i).getScore()) + "/6");
            TextView time = new TextView(this);
            time.setText(String.format("%d", quizHistory.get(i).getDate()));
            Date date = new Date((long) quizHistory.get(i).getDate() * 1000);
            String formattedDate = sdf.format(date);
            time.setText(formattedDate);

            tableRow.addView(score, layoutParams);
            tableRow.addView(time, layoutParams);

            tableLayout.addView(tableRow);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (historyData != null) {
            historyData.close();
        }
    }
}