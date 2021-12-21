package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton btnDaily, btnEvent;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDaily = findViewById(R.id.btn_daily);
        btnEvent = findViewById(R.id.btn_event);

        Intent bundle = getIntent();
        name = findViewById(R.id.name);

        name.setText(bundle.getStringExtra("data_name"));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences userPref = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                boolean isLoggedIn = userPref.getBoolean("isLoggedIn", false);
                if(isLoggedIn){
                    startActivity(new Intent(MainActivity.this, DailyActivity.class));
                    finish();
                }
                else {
                    isFirstTime();
                }
            }
        }, 1500);
    }

    private void isFirstTime() {
        //untuk mengecek apakah aplikasinya berjalan untuk pertama kalinya
        //valuenya di save di SharedPreferance
        SharedPreferences preferences = getApplication().getSharedPreferences("onboard", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if(isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            startActivity(new Intent(MainActivity.this, DailyActivity.class));
            finish();
        }else{
            startActivity(new Intent(MainActivity.this, CreateDailyActivity.class));
            finish();
        }

        btnDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyActivity.class);
                startActivity(intent);
            }
        });

        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });
    }
}