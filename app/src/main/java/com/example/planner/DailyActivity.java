package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DailyActivity extends AppCompatActivity {

    Button btnCretaeDaily;
    EditText inputDailyPlan, inputStartTime, inputEndTime;
    DBHelper dbHelper;
    ArrayList<DailyPlaner> dailyArrayList;
    RecyclerViewAdapter customAdapter;
    RecyclerView recyclerView;
    SharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        int idUser= spm.getSPId(this);

        dailyArrayList = new ArrayList<>();
        dbHelper = new DBHelper(DailyActivity.this);

        dailyArrayList = dbHelper.readDaily(idUser);

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
    }
}