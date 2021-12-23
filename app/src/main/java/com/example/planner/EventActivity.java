package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    Button btnCreateEvent;
    ArrayList<Event> eventArrayList;
    DBHelper myDB;
    EventAdapter customAdapter;
    RecyclerView recyclerEvent;
    SharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        int idUser= spm.getSPId(this);

        eventArrayList = new ArrayList<>();
        myDB = new DBHelper(EventActivity.this);

        eventArrayList = myDB.readEvent(idUser);


        customAdapter = new EventAdapter(eventArrayList, EventActivity.this);
        recyclerEvent = findViewById(R.id.recyclerEvent);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(EventActivity.this, RecyclerView.VERTICAL, false);

        recyclerEvent.setLayoutManager(linearLayoutManager);

        recyclerEvent.setAdapter(customAdapter);

        btnCreateEvent = findViewById(R.id.btn_create_event);
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }
}