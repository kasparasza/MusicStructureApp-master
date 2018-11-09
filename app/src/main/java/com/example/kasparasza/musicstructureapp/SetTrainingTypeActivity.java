package com.example.kasparasza.musicstructureapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class SetTrainingTypeActivity extends AppCompatActivity implements View.OnClickListener {

    //Preferences file, which will save data about selections made by the user:
    public static final String PREFS_NAME = "userPreferences";

    //Declaration of global variables used:
    private ScrollView activity02_parent_view;
    private TextView activity02_purpose_text;
    private CardView start_music_player_button;
    private ImageView set_training_type_image;
    private TextView set_training_type_text;
    private TextView link_run_to_music_text;
    private TextView coach_support_text;
    private int trainingTypeButtonClicks;
    private int trainingTypeButtonState;
    private int linkRunToMusicButtonClicks;
    private int linkRunToMusicButtonState;
    private int coachSupportButtonClicks;
    private int coachSupportButtonState;
    private EditText runDistance;
    private EditText runRepeats;
    private EditText breakDistance;
    private String runDistanceEntered;
    private String runRepeatsEntered;
    private String breakDistanceEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensures that the activity is not started when user clicks back button
        if (getIntent().getBooleanExtra("EXIT", true)) {
            finish();
        }

        setContentView(R.layout.activity02_set_training_type);

        // Restore preferences of the User
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        trainingTypeButtonClicks = settings.getInt("TRAINING_TYPE_BUTTON_CLICKS", 0);
        trainingTypeButtonState = settings.getInt("TRAINING_TYPE_BUTTON_STATE", 0);
        linkRunToMusicButtonClicks = settings.getInt("LINK_RUN_TO_MUSIC_BUTTON_CLICKS", 0);
        linkRunToMusicButtonState = settings.getInt("LINK_RUN_TO_MUSIC_BUTTON_STATE", 0);
        coachSupportButtonClicks = settings.getInt("COACH_SUPPORT_BUTTON_CLICKS", 0);
        coachSupportButtonState = settings.getInt("COACH_SUPPORT_BUTTON_STATE", 0);
        runDistanceEntered = settings.getString("RUN_DISTANCE_ENTERED", null);
        runRepeatsEntered = settings.getString("RUN_REPEATS_ENTERED", null);
        breakDistanceEntered = settings.getString("BREAK_DISTANCE_ENTERED", null);

        // Declaring views:
        start_music_player_button = (CardView) findViewById(R.id.start_music_palyer_button);
        activity02_parent_view = (ScrollView) findViewById(R.id.activity02_parent_view);
        activity02_purpose_text = (TextView) findViewById(R.id.activity02_purpose_text);

        // Declaring views - set EditText with running distance parameters entered previously:
        runDistance = (EditText) findViewById(R.id.set_distance_input);
        runDistance.setText(runDistanceEntered);
        runRepeats = (EditText) findViewById(R.id.set_number_of_repeats_input);
        runRepeats.setText(runRepeatsEntered);
        breakDistance = (EditText) findViewById(R.id.set_distance_of_break_input);
        breakDistance.setText(breakDistanceEntered);

        // Declaring views - set_training_type_button:
        CardView set_training_type_button = (CardView) findViewById(R.id.set_training_type_button);
        set_training_type_image = (ImageView) findViewById(R.id.set_training_type_image);
        set_training_type_text = (TextView) findViewById(R.id.training_type_text);
        setTrainingTypeButton(trainingTypeButtonState);

        // Declaring views - link_run_to_music_button:
        CardView link_run_to_music_button = (CardView) findViewById(R.id.link_run_to_music_button);
        link_run_to_music_text = (TextView) findViewById(R.id.link_run_to_music_text);
        setLinkRunToMusicButton(linkRunToMusicButtonState);

        // Declaring views - coach_support_button:
        CardView coach_support_button = (CardView) findViewById(R.id.coach_support_button);
        coach_support_text = (TextView) findViewById(R.id.coach_support_text);
        setCoachSupportButton(coachSupportButtonState);

        // Setting particular TextViews to be scrollable - TextView: activity purpose text:
        activity02_purpose_text.setMovementMethod(new ScrollingMovementMethod());
        activity02_parent_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.activity02_purpose_text).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        activity02_purpose_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // This disallows the touch request for parent scrollView on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // Declaring onClick Listeners:
        start_music_player_button.setOnClickListener(this);
        set_training_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trainingTypeButtonClicks += 1;
                trainingTypeButtonState = trainingTypeButtonClicks % 5;
                setTrainingTypeButton(trainingTypeButtonState);
            }
        });
        link_run_to_music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkRunToMusicButtonClicks += 1;
                linkRunToMusicButtonState = linkRunToMusicButtonClicks % 2;
                setLinkRunToMusicButton(linkRunToMusicButtonState);
            }
        });
        coach_support_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coachSupportButtonClicks += 1;
                coachSupportButtonState = coachSupportButtonClicks % 3;
                setCoachSupportButton(coachSupportButtonState);
            }
        });
    }

    /**
     * Defines the appearance of the set_training_type_button, based on selections of the user
     *
     * @param trainingTypeButtonState int that defines 1 of 5 states
     */
    public void setTrainingTypeButton(int trainingTypeButtonState) {
        set_training_type_image.setImageLevel(trainingTypeButtonState);
        Resources res = getResources();
        String[] training_type_options = res.getStringArray(R.array.training_type_string_array);
        set_training_type_text.setText(training_type_options[trainingTypeButtonState]);
    }

    /**
     * Defines the appearance of the link_run_to_music_button, based on selections of the user
     *
     * @param linkRunToMusicButtonState int that defines 1 of 2 states
     */
    public void setLinkRunToMusicButton(int linkRunToMusicButtonState) {
        Resources res = getResources();
        String[] link_run_to_music_options = res.getStringArray(R.array.link_run_to_music_string_array);
        link_run_to_music_text.setText(link_run_to_music_options[linkRunToMusicButtonState]);
    }

    /**
     * Defines the appearance of the coach_support_button, based on selections of the user
     *
     * @param coachSupportButtonState int that defines 1 of 3 states
     */
    public void setCoachSupportButton(int coachSupportButtonState) {
        Resources res = getResources();
        String[] coach_support_options = res.getStringArray(R.array.coach_support_array);
        coach_support_text.setText(coach_support_options[coachSupportButtonState]);
    }

    /**
     * Saves selections of the user in order to: 1) recreate them at the time activity is restarted;
     * 2) to share them with other activities
     */
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("TRAINING_TYPE_BUTTON_CLICKS", trainingTypeButtonClicks);
        editor.putInt("TRAINING_TYPE_BUTTON_STATE", trainingTypeButtonState);
        editor.putInt("LINK_RUN_TO_MUSIC_BUTTON_CLICKS", linkRunToMusicButtonClicks);
        editor.putInt("LINK_RUN_TO_MUSIC_BUTTON_STATE", linkRunToMusicButtonState);
        editor.putInt("COACH_SUPPORT_BUTTON_CLICKS", coachSupportButtonClicks);
        editor.putInt("COACH_SUPPORT_BUTTON_STATE", coachSupportButtonState);
        editor.putString("RUN_DISTANCE_ENTERED", runDistance.getText().toString());
        editor.putString("RUN_REPEATS_ENTERED", runRepeats.getText().toString());
        editor.putString("BREAK_DISTANCE_ENTERED", breakDistance.getText().toString());
        editor.commit();
    }

    /**
     * Assigns onClick to music player button / goes back to music player activity
     * This is an alternative implementation of OnClickListener:
     * a separate OnClick method with switch, instead of declaration of all OnClickListener in
     * OnCreate method.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_music_palyer_button: {
                Intent goToMusicPlayerActivity = new Intent(SetTrainingTypeActivity.this, MusicPlayer.class);
                startActivity(goToMusicPlayerActivity);
            }
        }
    }
}



