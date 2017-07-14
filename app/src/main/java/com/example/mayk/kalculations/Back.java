package com.example.mayk.kalculations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Back extends AppCompatActivity {

    ImageButton Replay_btn;
    TextView sum;
    ImageButton shareafter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        Replay_btn = (ImageButton) findViewById(R.id.replay);
        shareafter = (ImageButton) findViewById(R.id.shareafter);
        shareafter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my new best Score in KALCULATIONS.Do you want to give a try ?");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share via .."));
            }
        });
        sum = (TextView) findViewById(R.id.sum);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
// get data via the key
        Bundle b = getIntent().getExtras();
        int index = b.getInt("index");
        sum.setText(" SUM: "+index);

        Replay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Back.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

}
