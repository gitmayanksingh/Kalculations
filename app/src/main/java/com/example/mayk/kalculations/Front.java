package com.example.mayk.kalculations;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Front extends AppCompatActivity {

    ImageButton B1,B2,B3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        B1 = (ImageButton) findViewById(R.id.share);
        B2 = (ImageButton) findViewById(R.id.play);
        B3 = (ImageButton) findViewById(R.id.rate);

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Front.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
