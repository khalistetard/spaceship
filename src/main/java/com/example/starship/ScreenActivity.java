package com.example.starship;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenActivity extends AppCompatActivity  {
    private boolean isMute;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //puts the game in full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        findViewById(R.id.play).setOnClickListener(v -> startActivity( new Intent(ScreenActivity.this,SpaceActivity.class)));

        //findViewById(R.id.mode).setOnClickListener(v -> startActivity( new Intent(ScreenActivity.this,ModeActivity.class)));

        //High score presentation on Menu Screen
        TextView highScoreTxt = findViewById(R.id.highscoretext);
        //TextView modeScoreTxt = findViewById(R.id.modescoretext);

        SharedPreferences prefs = getSharedPreferences("game",MODE_PRIVATE);


        isMute = prefs.getBoolean("isMute",false);

        ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (isMute)
        { volumeCtrl.setImageResource(R.drawable.volume_mute); }
        else
        { volumeCtrl.setImageResource(R.drawable.volume_up); }

        volumeCtrl.setOnClickListener(view -> {
            isMute = !isMute;
            if (isMute)
            { volumeCtrl.setImageResource(R.drawable.volume_mute); }
            else
            { volumeCtrl.setImageResource(R.drawable.volume_up); }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isMute", isMute);
            editor.apply();
        });

        TextView reset = findViewById(R.id.highscoresetter);
        highScoreTxt.setText("HighScore: " +prefs.getInt( "highscore",0));
       // modeScoreTxt.setText("ModeScore: " +prefs.getInt( "modescore",0));

        reset.setOnClickListener(view -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highscore", 0);
            //editor.putInt("modescore", 0);
            editor.apply();
            highScoreTxt.setText("HighScore: " +prefs.getInt( "highscore",0));
            //modeScoreTxt.setText("ModeScore: " +prefs.getInt( "modescore",0));
        });
    }
}