package com.example.mayk.kalculations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.hanks.htextview.animatetext.HText;

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
    boolean endGameState;
    private int s = 0;
    private boolean stopTimer = false;
    public static final String MyTIMEPREFERENCES = "MyTimePrefs" ;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        timer();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        C = (TextView) findViewById(R.id.time);
        right = (ImageButton) findViewById(R.id.right);
        wrong = (ImageButton) findViewById(R.id.wrong);
        question = (TextView) findViewById(R.id.question);
        answer = (TextView) findViewById(R.id.answer);
        score = (TextView) findViewById(R.id.score);

//        sharedPreferences = getSharedPreferences(MyTIMEPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("time", seconds);
//        editor.apply();

        getTime();



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

    private void getTime() {
        Bundle bundle = getIntent().getExtras();
        int time = bundle.getInt("time");
        seconds = time;


//        sharedPreferences = getSharedPreferences(MyTIMEPREFERENCES, Context.MODE_PRIVATE);
//        C.setText("TIME :" + seconds);
        if (seconds<0) {

//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("Pref_time",seconds);
//        editor.apply();
            C.setText("" + seconds);
        }
    }


    // Function timmer() handle the timer
    private void timer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                C.setText("" + seconds);
                seconds--;

                if (seconds < 0) {
                    if (false == endGameState){
                    endGame();
                    stopTimer=true;}else{
//                        Toast.makeText(MainActivity.this, "You did mistake ", Toast.LENGTH_SHORT).show();
                        stopTimer=true;
                    }
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
            score.setText("" + s);
            generateQuestions();

        } else {
            Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
            endGame();
            endGameState=true;
        }
    }

    private void endGame() {

        Intent i = new Intent(MainActivity.this, Back.class);
        Bundle bundle = new Bundle();
        bundle.putInt("index", s);
        bundle.putInt("time",seconds);
        i.putExtras(bundle);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        stopTimer = true;
    }

    // When Activity life cycle is in onPause() state
    @Override
    protected void onPause() {
        super.onPause();
        stopTimer = false;// the countdown won't stop
        finish();
    }


}
