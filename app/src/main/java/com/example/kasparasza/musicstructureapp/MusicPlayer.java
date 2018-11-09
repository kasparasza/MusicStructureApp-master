package com.example.kasparasza.musicstructureapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.LevelListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MusicPlayer extends AppCompatActivity {

    //Preferences file, which will save data about selections made by the user:
    public static final String PREFS_NAME = "userPreferences";

    //Declaration of global variables used:
    private ScrollView activity01_parent_view;
    private TextView activity_purpose_text;
    private CardView set_training_type_button;
    private ImageView set_training_type_image;
    private TextView set_training_type_text;
    private CardView choose_music_button;
    private CardView go_to_more_settings_button;
    private ImageButton play_pause_button;
    private ImageButton repeat_shuffle_button;
    private int trainingTypeButtonState;
    private int playPauseButtonClicks;
    private int playPauseButtonState;
    private int repeatShuffleButtonClicks;
    private int repeatShuffleButtonState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity01_music_player);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        trainingTypeButtonState = settings.getInt("TRAINING_TYPE_BUTTON_STATE", 0);
        playPauseButtonClicks = settings.getInt("PLAY_PAUSE_BUTTON_CLICKS", 0);
        playPauseButtonState = settings.getInt("PLAY_PAUSE_BUTTON_STATE", 0);
        repeatShuffleButtonClicks = settings.getInt("REPEAT_SHUFFLE_BUTTON_CLICKS", 0);
        repeatShuffleButtonState = settings.getInt("REPEAT_SHUFFLE_BUTTON_STATE", 0);

        // Declaring views:
        go_to_more_settings_button = (CardView) findViewById(R.id.go_to_more_settings_button);
        activity01_parent_view = (ScrollView) findViewById(R.id.activity01_parent_view);
        activity_purpose_text = (TextView) findViewById(R.id.activity_purpose_text);

        // Declaring views - set_training_type_button:
        set_training_type_button = (CardView) findViewById(R.id.set_training_type_button);
        set_training_type_image = (ImageView) findViewById(R.id.set_training_type_image);
        set_training_type_text = (TextView) findViewById(R.id.training_type_text);
        setTrainingTypeButton(trainingTypeButtonState);

        // Declaring views - choose_music_button:
        choose_music_button = (CardView) findViewById(R.id.choose_music_button);

        // Declaring views - play_pause_button:
        play_pause_button = (ImageButton) findViewById(R.id.play_pause_button);
        setPlayPauseButton(playPauseButtonState);

        // Declaring views - repeat_shuffle_button:
        repeat_shuffle_button = (ImageButton) findViewById(R.id.repeat_shuffle_button);
        setRepeatShuffleButton(repeatShuffleButtonState);

        // Setting particular TextViews to be scrollable - TextView: activity purpose text:
        activity_purpose_text.setMovementMethod(new ScrollingMovementMethod());
        activity01_parent_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.activity_purpose_text).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        activity_purpose_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // This disallows the touch request for parent scrollView on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // Declaring onClick Listeners:
        set_training_type_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSetTrainingActivity = new Intent(MusicPlayer.this, SetTrainingTypeActivity.class);
                //// Clears the back stack, so that the user will not be able to navigate back to the activity with the back button
                goToSetTrainingActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                goToSetTrainingActivity.putExtra("EXIT", false);
                startActivity(goToSetTrainingActivity);
            }
        });
        choose_music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToChooseMusicActivity = new Intent(MusicPlayer.this, ChooseMusicActivity.class);
                //// Clears the back stack, so that the user will not be able to navigate back to the activity with the back button
                goToChooseMusicActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                goToChooseMusicActivity.putExtra("EXIT", false);
                startActivity(goToChooseMusicActivity);
            }
        });
        go_to_more_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToLinkYourAppsAndAccountsActivity = new Intent(MusicPlayer.this, LinkYourAppsAndAccountsActivity.class);
                //// Clears the back stack, so that the user will not be able to navigate back to the activity with the back button
                goToLinkYourAppsAndAccountsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                goToLinkYourAppsAndAccountsActivity.putExtra("EXIT", false);
                startActivity(goToLinkYourAppsAndAccountsActivity);
            }
        });
        play_pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseButtonClicks += 1;
                playPauseButtonState = playPauseButtonClicks % 2;
                setPlayPauseButton(playPauseButtonState);
            }
        });
        repeat_shuffle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatShuffleButtonClicks += 1;
                repeatShuffleButtonState = repeatShuffleButtonClicks % 4;
                Resources res = getResources();
                String[] repeat_and_shuffle_options = res.getStringArray(R.array.repeat_and_shuffle_array);
                Toast.makeText(getApplicationContext(), repeat_and_shuffle_options[repeatShuffleButtonState], Toast.LENGTH_SHORT).show();
                setRepeatShuffleButton(repeatShuffleButtonState);
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
     * Defines the appearance of the play_pause_button, based on selections of the user
     *
     * @param playPauseButtonState int that defines 1 of 2 states
     */
    public void setPlayPauseButton(int playPauseButtonState) {
        play_pause_button.setImageLevel(playPauseButtonState);
    }

    /**
     * Defines the appearance of the repeat_shuffle_button, based on selections of the user
     *
     * @param repeatShuffleButtonState int that defines 1 of 4 states
     */
    public void setRepeatShuffleButton(int repeatShuffleButtonState) {
        repeat_shuffle_button.setImageLevel(repeatShuffleButtonState);
    }

    /**
     * Saves selections of the user in order to: 1) recreate them at the time activity is restarted;
     * 2) to share them with other activities
     */
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("PLAY_PAUSE_BUTTON_CLICKS", playPauseButtonClicks);
        editor.putInt("PLAY_PAUSE_BUTTON_STATE", playPauseButtonState);
        editor.putInt("REPEAT_SHUFFLE_BUTTON_CLICKS", repeatShuffleButtonClicks);
        editor.putInt("REPEAT_SHUFFLE_BUTTON_STATE", repeatShuffleButtonState);
        editor.commit();
    }

    /**
     * Deletes the settings that the user has made upon the exit.
     * Aim - to avoid excessively large numbers accumulation in the SharedPreferences.
     * NOTE: this method is optional / can be deleted.
     */
    public void onDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        super.onDestroy();
    }

}
