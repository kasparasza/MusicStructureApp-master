package com.example.kasparasza.musicstructureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class LinkYourAppsAndAccountsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensures that the activity is not started when user clicks back button
        if (getIntent().getBooleanExtra("EXIT", true)) {
            finish();
        }

        setContentView(R.layout.activity04_accounts_and_settings);

        //Declaration of global variables used:
        ScrollView activity04_parent_view;
        TextView activity04_purpose_text;

        // Declaring views:
        TextView music_player_link = (TextView) findViewById(R.id.go_to_player_link);
        activity04_parent_view = (ScrollView) findViewById(R.id.activity04_parent_view);
        activity04_purpose_text = (TextView) findViewById(R.id.activity04_purpose_text);

        // Setting particular TextViews to be scrollable - TextView: activity purpose text:
        activity04_purpose_text.setMovementMethod(new ScrollingMovementMethod());
        activity04_parent_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.activity04_purpose_text).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        activity04_purpose_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // This disallows the touch request for parent scrollView on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // Declaring onClick Listeners:
        music_player_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMusicPlayerActivity = new Intent(LinkYourAppsAndAccountsActivity.this, MusicPlayer.class);
                startActivity(goToMusicPlayerActivity);
            }
        });
    }
}
