package com.example.mayk.kalculations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int seconds = 10;
    TextView C;
    ImageButton right;
    ImageButton wrong;
    TextView question;
    TextView answer;
    TextView score;


    boolean isResultRight;
    private int s = 0;
    private boolean stopTimer = false;

    SharedPreferences app_preferences;
    public static final String PREFS_SCORE_SEEK = "MyPrefsFile";
    int appTheme;
    int themeColor;
    int appColor;
    Constant constant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color",0);
        appTheme = app_preferences.getInt("theme",0);
        themeColor = appColor;
        constant.color = appColor;
        if(themeColor==0)
        {
            setTheme(Constant.theme);
        }else if (appTheme==0){
            setTheme(Constant.theme);
        }else{
            setTheme(appTheme);
        }
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_SCORE_SEEK,MODE_PRIVATE);
        int seek_progress = sharedPreferences.getInt("progressChanged",10); // indicates default value is null
         seconds=seek_progress;
//        C.setText("TIME :" + seek_progress);


        setContentView(R.layout.activity_main);

        C = (TextView) findViewById(R.id.time);
        right = (ImageButton) findViewById(R.id.right);
        wrong = (ImageButton) findViewById(R.id.wrong);
        question = (TextView) findViewById(R.id.question);
        answer = (TextView) findViewById(R.id.answer);
        score = (TextView) findViewById(R.id.score);

        timmer();

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(true);
            }
        });
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAnswer(false);
            }
        });

        generateQuestions();

    }


    // Function timmer() handle the timer

    private void timmer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                C.setText("TIME :" + seconds);
                seconds--;
                if (seconds < 0) {

                    // Sends Intent to Back.class with a Bundle containing the Score ie 's'

                      Intent i = new Intent(MainActivity.this, Back.class);
                      Bundle bundle = new Bundle();
                      bundle.putInt("index", s);
                      i.putExtras(bundle);
                      startActivity(i);

                      //  Sends the intent and stop the timer
                      stopTimer = true;

                }
                if (stopTimer == false) {
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    // For Generating Questions Randomly

    private void generateQuestions() {
        isResultRight = true;
        Random rand = new Random();
        int a = rand.nextInt(100);
        int b = rand.nextInt(100);
        int result = a + b;

        // For generating the Wrong Output

        float f = rand.nextFloat();
        if (f > 0.5f) {
            result = rand.nextInt(100);
            isResultRight = false;
        }
        question.setText(a + "+" + b);
        answer.setText("=" + result);
    }

// For verifying the answers after pressing Right or Wrong button

    public void verifyAnswer(boolean a) {
        if (a == isResultRight) {
            s = s + 1;
            score.setText("SCORE :" + s);
            generateQuestions();
        } else {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);

            Intent i = new Intent(MainActivity.this, Back.class);
            Bundle bundle = new Bundle();
            bundle.putInt("index", s);
            i.putExtras(bundle);
            startActivity(i);
            stopTimer = true;

        }

    }

    @Override

// When Activity life cycle is in onPause() state

    protected void onPause() {
        super.onPause();
        stopTimer = false; // the countdown won't stop
        finish();
    }

}
