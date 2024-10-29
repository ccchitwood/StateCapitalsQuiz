package edu.uga.cs.statecapitalsquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Button startQuizButton = findViewById(R.id.button);
        Button viewPastQuizzesButton = findViewById(R.id.button2);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
        viewPastQuizzesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // filler
            }
        });
    }
}
