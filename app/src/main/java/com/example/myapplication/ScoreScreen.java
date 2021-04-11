package com.example.myapplication;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreScreen extends AppCompatActivity {
    TextView timer_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        timer_tv = findViewById(R.id.timerTv);

        timer_tv.setText(MySurfaceView.getTimer());

    }
}