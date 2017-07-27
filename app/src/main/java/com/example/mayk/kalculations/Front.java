package com.example.mayk.kalculations;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.mayk.kalculations.R.id.high_score_data;

public class Front extends AppCompatActivity {

    ImageButton B1,B2,B3;
    int index1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        B1 = (ImageButton) findViewById(R.id.trophy);
        final ListView lv = (ListView) findViewById(high_score_data);


        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             final AlertDialog.Builder builder = new AlertDialog.Builder(Front.this);
                final View view =getLayoutInflater().inflate(R.layout.high_score,null);

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(Front.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setView(view);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        B2 = (ImageButton) findViewById(R.id.play);
        B3 = (ImageButton) findViewById(R.id.setting);

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Front.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
