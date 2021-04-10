package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class IntroActivity extends AppCompatActivity {




    ConstraintLayout introduction;

    ImageView first_page;
    ImageView second_page;
    ImageView third_page;
    ImageView firstAvatar;
    ImageView secondAvatar;

    Button prev;
    Button next;

    TextView welcomeWords;
    TextView iAmBuddy;
    TextView doYouKnow;
    TextView iCanHelp;

    int pageCount = 1;

    SpringDotsIndicator springDotsIndicator;
    ViewPager2 viewPager2;


    @SuppressLint({"UseCompatLoadingForDrawables", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        init();

        changePage(pageCount);



        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageCount > 1) {
                    pageCount--;
                    changePage(pageCount);
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pageCount < 3)
                    pageCount++;
                changePage(pageCount);
            }
        });

    }

    private void setImage(ImageView image, int size) {
        image.setImageResource(R.drawable.pages);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) image.getLayoutParams();
        params.width = size;
        image.setLayoutParams(params);
    }

    private void changePage(int page) {
        switch (page) {
            case (1):
                setImage(first_page, 140);
                setImage(second_page, 75);
                setImage(third_page, 75);

                welcomeWords.setVisibility(View.VISIBLE);
                firstAvatar.setVisibility(View.VISIBLE);
                iAmBuddy.setVisibility(View.VISIBLE);
                iCanHelp.setVisibility(View.GONE);
                secondAvatar.setVisibility(View.GONE);
                doYouKnow.setVisibility(View.GONE);

                welcomeWords.setText(R.string.introTextFirstWelcome);
                iAmBuddy.setText(R.string.introIamBuddy);
                break;

            case (2):
                setImage(first_page, 75);
                setImage(second_page, 140);
                setImage(third_page, 75);

                welcomeWords.setVisibility(View.GONE);
                firstAvatar.setVisibility(View.GONE);
                iAmBuddy.setVisibility(View.GONE);
                secondAvatar.setVisibility(View.VISIBLE);
                doYouKnow.setVisibility(View.VISIBLE);
                iCanHelp.setVisibility(View.VISIBLE);

                iCanHelp.setText(R.string.introSecondIwillhelpYou);
                break;

            case (3):
                setImage(first_page, 75);
                setImage(second_page, 75);
                setImage(third_page, 140);
                break;
        }
    }





    private void init() {
        first_page = findViewById(R.id.introImageFirst);
        second_page = findViewById(R.id.introImageSecond);
        third_page = findViewById(R.id.introImageThird);

        firstAvatar = findViewById(R.id.introFirstAvatar);
        firstAvatar.setImageResource(R.drawable.teddyup);
        secondAvatar = findViewById(R.id.introSecondAvatar);

        welcomeWords = findViewById(R.id.introText);
        iAmBuddy = findViewById(R.id.introIAmBuddy);
        doYouKnow = findViewById(R.id.introSecondDoYouKnow);
        iCanHelp = findViewById(R.id.introWillhelpText);


        introduction = findViewById(R.id.introduction);

        prev = findViewById(R.id.introPrev);
        next = findViewById(R.id.introNext);
    }
}
