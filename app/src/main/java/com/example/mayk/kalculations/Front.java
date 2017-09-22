package com.example.mayk.kalculations;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.steelkiwi.library.IncrementProductView;
import com.steelkiwi.library.listener.OnStateListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.ListPopupWindow.WRAP_CONTENT;
import static com.example.mayk.kalculations.R.id.front_main_layout;
import static com.example.mayk.kalculations.R.id.front_menu_settings;
import static com.example.mayk.kalculations.R.id.high_score_data;

public class Front extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAl = 2;

    boolean doubleBackToExitPressedOnce = false;

    ImageButton B1, B2, B3;
    int index1;

    private ListView lv;
    IncrementProductView productView;
    int count1;
    public static final String MyTIMEPREFERENCES = "MyTimePrefs";
    SharedPreferences sharedPreferences;
    DatabaseReference databaseReference;
    LayoutInflater layoutInflater;
    private PopupWindow popupSettings;
    LinearLayout linearLayout;
    private SwitchCompat switchMusic;
    private SwitchCompat switchSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        setContentView(R.layout.activity_front);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        B1 = (ImageButton) findViewById(R.id.trophy);
        lv = (ListView) findViewById(high_score_data);
        productView = (IncrementProductView) findViewById(R.id.product_view);
        productView.setOnStateListener(new OnStateListener() {
            @Override
            public void onCountChange(int count) {
                count1 = count;
            }

            @Override
            public void onConfirm(int count) {

                Toast.makeText(Front.this, "You got " + count1 + " secs to play ", Toast.LENGTH_SHORT).show();

                sharedPreferences = getSharedPreferences(MyTIMEPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("time", count1);
                editor.apply();

            }

            @Override
            public void onClose() {

            }
        });

        requestPermission();
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                highsore();


//             final AlertDialog.Builder builder = new AlertDialog.Builder(Front.this);
//                final View view =getLayoutInflater().inflate(R.layout.high_score,null);
//
//                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        Toast.makeText(Front.this, "Success", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.setView(view);
//                builder.setCancelable(false);
//                AlertDialog dialog = builder.create();
//                dialog.show();
            }
        });
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getValue(String.class)!=null){
//                    String key = dataSnapshot.getKey();
//                    if(key.equals("Score")){
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        B2 = (ImageButton) findViewById(R.id.play);
        B3 = (ImageButton) findViewById(R.id.setting);

        linearLayout = (LinearLayout) findViewById(R.id.front_menu_settings);
        B3.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
//                Intent intent = new Intent(Front.this, Settings.class);
//                startActivity(intent);
                                      AnimationSet animSet = new AnimationSet(true);
                                      animSet.setInterpolator(new DecelerateInterpolator());
                                      animSet.setFillAfter(true);
                                      animSet.setFillEnabled(true);

                                      final RotateAnimation animRotate = new RotateAnimation(0.0f, -90.0f,
                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f);

                                      animRotate.setDuration(1500);
                                      animRotate.setFillAfter(true);
                                      animSet.addAnimation(animRotate);

                                      B3.startAnimation(animSet);
                                      try {
                                          layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                                          ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.settings_bubble, null);
                                          popupSettings = new PopupWindow(container, 400, 400, true);
                                          ScaleAnimation growAnim = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0f);
                                          final ScaleAnimation shrinkAnim = new ScaleAnimation(1.15f, 1.0f, 1.15f, 1.0f);
                                          growAnim.setFillAfter(true);
                                          growAnim.setDuration(1000);
                                          shrinkAnim.setDuration(2000);
                                          container.setAnimation(growAnim);
                                          growAnim.start();

                                          linearLayout = (LinearLayout) findViewById(R.id.settings_page);


                                           switchMusic = (SwitchCompat) container.findViewById(R.id.switchMusic);
                                          switchSound = (SwitchCompat) container.findViewById(R.id.switchSound);
                                          popupSettings.showAsDropDown(B3, Gravity.NO_GRAVITY, 0, 0);

                                          switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {

                                                  Snackbar snackbar = Snackbar.make(findViewById(front_main_layout), "Switch state checked " + isChecked, Snackbar.LENGTH_LONG)
                                                          .setAction("UNDO", new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View view) {

                                                                  if (switchMusic.isChecked()) {
                                                                      switchMusic.setChecked(false);
                                                                  } else {
                                                                      switchMusic.setChecked(true);
                                                                  }

                                                                  Snackbar snackbar1 = Snackbar.make(buttonView, "Message is restored!", Snackbar.LENGTH_SHORT);
                                                                  snackbar1.show();
                                                              }
                                                          });
                                                  snackbar.show();

                                              }
                                          });

                                          switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              @Override
                                              public void onCheckedChanged(final CompoundButton buttonView, boolean isChecked) {

                                                  Snackbar snackbar = Snackbar.make(findViewById(front_main_layout), "Switch state checked " + isChecked, Snackbar.LENGTH_LONG)
                                                          .setAction("UNDO", new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View view) {
                                                                  if (switchSound.isChecked()) {
                                                                      switchSound.setChecked(false);
                                                                  } else {
                                                                      switchSound.setChecked(true);
                                                                  }
                                                                  Snackbar snackbar2 = Snackbar.make(buttonView, "Message is restored!", Snackbar.LENGTH_SHORT);
                                                                  snackbar2.show();
                                                              }
                                                          });
                                                  snackbar.show();
                                              }
                                          });


                                      } catch (Exception e) {
                                          e.printStackTrace();
                                      }
                                  }

                              }
        );

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in = new Intent(Front.this, MainActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putInt("time", count1);
//                in.putExtras(bundle1);
//                startActivity(in);


                SharedPreferences sharedPreferences = getSharedPreferences(MyTIMEPREFERENCES, MODE_PRIVATE);
                int lastTime = sharedPreferences.getInt("time",0);

                count1=lastTime;

                if (count1 > 1) {

                    Intent i = new Intent(Front.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("time", count1);
                    i.putExtras(bundle);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);


                } else {
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.front_main_layout), "Please correct the play Duration", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Front.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Front.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Front.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Front.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Front.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Toast.makeText(this, "Permission is needed to share your Screenshots", Toast.LENGTH_SHORT).show();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Front.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(this, "Permission granted . Thanks !", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission was'nt granted .", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAl: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void highsore() {
        Bundle b = getIntent().getExtras();
        index1 = b.getInt("index1");
        String hScore = String.valueOf(index1); // Converting INT to STRING


        // Creating a database reference ,making a root named Score and storing the value in "SCORE"
        databaseReference.child("Score").child(hScore).setValue(true);


        Toast.makeText(this, "Score added to database.", Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(Front.this);
        final View view = getLayoutInflater().inflate(R.layout.high_score, null);


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

    //    private void showData(DataSnapshot dataSnapshot){
//
//        HighscoreInfo hInfo = new HighscoreInfo();
//        hInfo.setScore(dataSnapshot.getValue(HighscoreInfo.class).getScore());
//        ArrayList<String> array = new ArrayList<>();
//        array.add(hInfo.getScore());
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
//        lv.setAdapter(adapter);
//    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
//    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        View parentLayout = findViewById(R.id.front_main_layout);
        Snackbar snackbar = Snackbar.make(parentLayout, "Please click BACK again to EXIT!", Snackbar.LENGTH_SHORT);
        snackbar.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences = getSharedPreferences(MyTIMEPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("time", count1);
        editor.apply();
    }
}
