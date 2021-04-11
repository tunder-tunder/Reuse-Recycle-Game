package com.example.myapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    TextView question;
    TextView status;
    TextView var1;
    TextView var2;
    TextView var3;
    TextView var4;

    ProgressBar progressBar;

    final int QUIZ_SIZE = 4;

    ArrayList<Integer> questionIndexes = new ArrayList<>();
    int random = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        init();
        quizRound();

    }

    private void quizRound() {
        if (!isItVictory(progressBar.getProgress())) {

            progressBar.setProgress(progressBar.getProgress() + (progressBar.getMax()/QUIZ_SIZE));
            status.setText("Квиз пройден на: " + progressBar.getProgress() + "%");

            String strForRes = "quiz" + random;
            random++;

            question.setText(getStringByName(strForRes));

            var1.setText(getStringArrayByName(strForRes)[0]);
            var2.setText(getStringArrayByName(strForRes)[1]);
            var3.setText(getStringArrayByName(strForRes)[2]);
            var4.setText(getStringArrayByName(strForRes)[3]);

            var1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizRound();
                }
            });

            var2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizRound();
                }
            });

            var3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizRound();
                }
            });

            var4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quizRound();
                }
            });
        }
    }


    private String getStringByName(String id) {
        return getResources().getString(getResources().getIdentifier(id, "string", getPackageName()));
    }

    private String[] getStringArrayByName(String id) {
        return getResources().getStringArray(getResources().getIdentifier(id, "array", getPackageName()));
    }

    private boolean isItVictory(int status) {
        if (status >= progressBar.getMax()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Ты успешный", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }
        return false;
    }



    private void init() {
        question = findViewById(R.id.quizQuestion);
        status = findViewById(R.id.quizProgressBarStatus);
        progressBar = findViewById(R.id.quizProgressBar);

        var1 = findViewById(R.id.quizVarOne);
        var2 = findViewById(R.id.quizVarTwo);
        var3 = findViewById(R.id.quizVarThree);
        var4 = findViewById(R.id.quizVarFour);

        for (int i = 0; i < QUIZ_SIZE; i++) {
            questionIndexes.add(i);
        }

        progressBar.setProgress(0);
    }
}
