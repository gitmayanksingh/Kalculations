package com.example.mayk.kalculations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class Back extends AppCompatActivity {

    ImageButton Replay_btn;
    TextView sum;
    ImageButton shareafter;
    File imagePath;
    ImageView bmImage;
    Bitmap bitmap;
    View screen;
    TextView highscore;
    TextView sum_n;
    TextView highscore_n;
    ImageButton home;
    int index;

    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;
    Constant constant;

    private static final String TAG = "MyActivity";
    public static final String PREFS_SCORE = "MyPrefsFile";

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
        setContentView(R.layout.activity_back);

        screen = findViewById(R.id.screen);
        Replay_btn = (ImageButton) findViewById(R.id.replay);
        shareafter = (ImageButton) findViewById(R.id.shareafter);
        highscore = (TextView) findViewById(R.id.highscore);
        highscore_n = (TextView) findViewById(R.id.highscore_n);
        home = (ImageButton) findViewById(R.id.home);

        // Activity changes from Back.class to Front.class with taking a bundle

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Front.class);
                Bundle bundle = new Bundle();
                bundle.putInt("index1", index);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Sharing Intent

        shareafter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                shareIt();
            }
        });

        sum = (TextView) findViewById(R.id.sum);
        sum_n = (TextView) findViewById(R.id.sum_n);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
// get data via the key from MainActivity

        Bundle b = getIntent().getExtras();
        index = b.getInt("index");
        sum_n.setText("" + index);




        Replay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Back.this, MainActivity.class);
                startActivity(i);
            }
        });

        // For  for getting saved preferences (saved HIGH SCORE)
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_SCORE, MODE_PRIVATE);
        String high = sharedPreferences.getString("High Score", ""); // "" indicates default value is null
        highscore_n.setText(high);


    }
    // To take the screenshot

    private Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        return bitmap;
    }

// To save in the the local directory

    private void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// Compressing in JPEG format
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("TAG", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("TAG", e.getMessage(), e);
        }
    }
// To share using Sharing Intent via share button

    private void shareIt() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "In Kalculations , My highest score with screen shot";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Kalculations score");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
// On Destroy Event

    @Override
    protected void onDestroy() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_SCORE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("High Score", sum_n.getText().toString());
        editor.apply();
        super.onDestroy();
        Toast.makeText(this, "Thanks ! for playing", Toast.LENGTH_SHORT).show();
    }


}
