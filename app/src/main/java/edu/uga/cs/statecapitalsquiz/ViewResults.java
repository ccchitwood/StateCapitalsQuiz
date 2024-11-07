package edu.uga.cs.statecapitalsquiz;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ViewResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_results);

        HistoryData historyData = new HistoryData(this);
        historyData.open();

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        android.widget.TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT );
        layoutParams.setMargins(20, 0, 20, 0);

        List<History> quizHistory = historyData.retrieveAllResults();
        Log.d("size of quiz history", quizHistory.size() + ""); // Returns 0
        for (int i = quizHistory.size() - 1; i >= 0; i--) {
            TableRow tableRow = new TableRow(this);

            TextView score = new TextView(this);
            score.setText(String.format("%d", quizHistory.get(i).getScore()) + "/6");
            TextView time = new TextView(this);
            time.setText(String.format("%d", quizHistory.get(i).getDate()));

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
}