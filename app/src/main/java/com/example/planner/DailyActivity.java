package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageButton;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {

    Button btnCretaeDaily;
    ImageButton buttondelete;
    EditText inputDailyPlan, inputStartTime, inputEndTime;
    DBHelper dbHelper;
    ArrayList<DailyPlaner> dailyArrayList;
    RecyclerViewAdapter customAdapter;
    RecyclerView recyclerView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        dailyArrayList = new ArrayList<>();
        dbHelper = new DBHelper(DailyActivity.this);

        dailyArrayList = dbHelper.readDaily();
        dbHelper = new DBHelper(this);

        customAdapter = new RecyclerViewAdapter(dailyArrayList, DailyActivity.this);
        recyclerView = findViewById(R.id.daftarCart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DailyActivity.this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(customAdapter);

        btnCretaeDaily = findViewById(R.id.btn_create_daily);
        btnCretaeDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DailyActivity.this, CreateDailyActivity.class);
                startActivity(intent);
            }

        });
        buttondelete = findViewById(R.id.buttondelete);
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }
        void confirmDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete ?");
            builder.setMessage("Are you sure you want to delete this Daily Activity?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dbHelper = new DBHelper(DailyActivity.this);
                    dbHelper.deleteOneRow(DBHelper.row_dailyId);
                    //Intent intent = new Intent(DailyActivity.this, ItemDailyActivity.class);
                   // startActivity(intent);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.create().show();
        }
    }