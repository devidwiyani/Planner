package com.example.planner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemDailyActivity extends AppCompatActivity {

    ArrayList<DailyPlaner> dailyPlanerArrayList;
    DBHelper myDB;
    RecyclerViewAdapter customAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_daily);

        dailyPlanerArrayList = new ArrayList<>();
        myDB = new DBHelper(ItemDailyActivity.this);

        //dailyPlanerArrayList = myDB.read();


        customAdapter = new RecyclerViewAdapter(dailyPlanerArrayList, ItemDailyActivity.this);
        recyclerView = findViewById(R.id.daftarCart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ItemDailyActivity.this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(customAdapter);


    }

}