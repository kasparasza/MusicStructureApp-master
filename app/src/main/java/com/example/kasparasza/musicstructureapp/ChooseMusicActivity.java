package com.example.kasparasza.musicstructureapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ChooseMusicActivity extends AppCompatActivity {

    //Preferences file, which will save data about selections made by the user:
    public static final String PREFS_NAME = "userPreferences";

    //Declaration of global variables used:
    private ScrollView activity03_parent_view;
    private TextView activity03_purpose_text;
    private CardView start_music_player_button;
    private CardView music_source_button;
    private ImageView music_source_image;
    private TextView music_source_text;
    private int musicSourceButtonClicks;
    private int musicSourceButtonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensures that the activity is not started when user clicks back button
        if (getIntent().getBooleanExtra("EXIT", true)) {
            finish();
        }

        setContentView(R.layout.activity03_choose_music);

        // Restore preferences of the User
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        musicSourceButtonClicks = settings.getInt("MUSIC_SOURCE_BUTTON_CLICKS", 0);
        musicSourceButtonState = settings.getInt("MUSIC_SOURCE_BUTTON_STATE", 0);

        // Declaring views:
        start_music_player_button = (CardView) findViewById(R.id.start_music_player_button_02);
        activity03_parent_view = (ScrollView) findViewById(R.id.activity03_parent_view);
        activity03_purpose_text = (TextView) findViewById(R.id.activity03_purpose_text);

        // Declaring views - music_source_button:
        music_source_button = (CardView) findViewById(R.id.music_source_button);
        music_source_image = (ImageView) findViewById(R.id.music_source_image);
        music_source_text = (TextView) findViewById(R.id.music_source_text);

        // Setting particular TextViews to be scrollable - TextView: activity purpose text:
        activity03_purpose_text.setMovementMethod(new ScrollingMovementMethod());
        activity03_parent_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.activity03_purpose_text).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        activity03_purpose_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // This disallows the touch request for parent scrollView on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // Declaring onClick Listeners:
        start_music_player_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMusicPlayerActivity = new Intent(ChooseMusicActivity.this, MusicPlayer.class);
                startActivity(goToMusicPlayerActivity);
            }
        });
        music_source_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSourceButtonClicks += 1;
                musicSourceButtonState = musicSourceButtonClicks % 2;
                setMusicSourceButton(musicSourceButtonState);
            }
        });
    }

    /**
     * Defines the appearance of the music_source_button, based on selections of the user
     *
     * @param musicSourceButtonState int that defines 1 of 2 states
     */
    public void setMusicSourceButton(int musicSourceButtonState) {
        music_source_image.setImageLevel(musicSourceButtonState);
        Resources res = getResources();
        String[] music_source_options = res.getStringArray(R.array.music_source_array);
        music_source_text.setText(music_source_options[musicSourceButtonState]);
    }

    /**
     * Saves selections of the user in order to: 1) recreate them at the time activity is restarted;
     * 2) to share them with other activities
     */
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("MUSIC_SOURCE_BUTTON_CLICKS", musicSourceButtonClicks);
        editor.putInt("MUSIC_SOURCE_BUTTON_STATE", musicSourceButtonState);
        editor.commit();
    }
}
