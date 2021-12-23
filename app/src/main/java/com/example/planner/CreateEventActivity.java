package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity {

    EditText inputEventPlan, inputLocationPlan, inputDatePlan, inputTimePlan;
    Button btnSubmitEvent, btnShare;
    DBHelper dbHelper;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog timePickerDialog;
    String id, eventName, eventLocation, eventDate, eventTime;
    SharedPrefManager spm;
    public int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        idUser= spm.getSPId(this);

        inputEventPlan = findViewById(R.id.input_event_plan);
        inputLocationPlan = findViewById(R.id.input_location_plan);
        inputDatePlan = findViewById(R.id.input_date_plan);
        inputTimePlan = findViewById(R.id.input_time_plan);
        btnSubmitEvent = findViewById(R.id.btn_submit_event);
        btnShare = findViewById(R.id.btn_share);

        dbHelper = new DBHelper(this);

        //megambil data untuk ditampilkan berdasarkan posisition
        id = getIntent().getStringExtra("id");
        eventName = getIntent().getStringExtra("eventName");
        eventLocation = getIntent().getStringExtra("eventLocation");
        eventDate = getIntent().getStringExtra("eventDate");
        eventTime = getIntent().getStringExtra("eventTime");

        inputEventPlan.setText(eventName);
        inputLocationPlan.setText(eventLocation);
        inputDatePlan.setText(eventDate);
        inputTimePlan.setText(eventTime);

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TextView tanggal = findViewById(R.id.input_date_plan);
                String myFormat = "dd-MMMM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tanggal.setText(sdf.format(myCalendar.getTime()));
            }
        };

        inputDatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateEventActivity.this, date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        inputTimePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }

        });

        btnSubmitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getId = id;
                Integer userId = idUser;
                String getEventPlan = inputEventPlan.getText().toString();
                String getLocationPlan = inputLocationPlan.getText().toString();
                String getDatePlan = inputDatePlan.getText().toString();
                String getTimePlan = inputTimePlan.getText().toString();

                if (getEventPlan.isEmpty() || getLocationPlan.isEmpty() || getDatePlan.isEmpty()){
                    Toast.makeText(CreateEventActivity.this, "Data belum lengkap", Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues values = new ContentValues();
                    Intent intent = new Intent(CreateEventActivity.this,EventActivity.class);
                    startActivity(intent);

                    dbHelper.insertEvent(getId, userId, getEventPlan, getLocationPlan, getDatePlan, getTimePlan);

                    Toast.makeText(CreateEventActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getEventPlan = inputEventPlan.getText().toString();
                String getLocationPlan = inputLocationPlan.getText().toString();
                String getDatePlan = inputDatePlan.getText().toString();
                String getTimePlan = inputTimePlan.getText().toString();

                AlertDialog.Builder dialog1 = new AlertDialog.Builder(CreateEventActivity.this);
                View view = getLayoutInflater().inflate(R.layout.alert_add_friend,null);

                EditText input = (EditText) view.findViewById(R.id.input);

                view.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String userInput = input.getText().toString();
                        Toast.makeText(v.getRootView().getContext(), "Entered: "+userInput, Toast.LENGTH_LONG).show();

                        int friendId = dbHelper.checkFriendId(userInput);
                        if (friendId > 0){
                            dbHelper.addFriendEvent(friendId, getEventPlan, getLocationPlan, getDatePlan, getTimePlan);
                        }else{
                            Toast.makeText(v.getRootView().getContext(), "User Salah", Toast.LENGTH_SHORT).show();
                        }

//                        ContentValues values = new ContentValues();
//                        String getEventPlan = inputEventPlan.getText().toString();
//                        String getLocationPlan = inputLocationPlan.getText().toString();
//                        String getDatePlan = inputDatePlan.getText().toString();
//                        String getTimePlan = inputTimePlan.getText().toString();
//
//                        Intent intent = new Intent(CreateEventActivity.this, ShareActivity.class);
//                        intent.putExtra("event_name", getEventPlan);
//                        intent.putExtra("event_location", getLocationPlan);
//                        intent.putExtra("event_date", getDatePlan);
//                        intent.putExtra("event_time", getTimePlan);
//                        startActivity(intent);
//
//                        Toast.makeText(CreateEventActivity.this, "Register Succesful", Toast.LENGTH_SHORT).show();

                    }
                });

                dialog1.setView(view);
                AlertDialog dialog2 = dialog1.create();
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });



    }

    private void showTimeDialog() {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                inputTimePlan.setText(hourOfDay+":"+minute);

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                DateFormat.is24HourFormat(this));

        timePickerDialog.show();

    }
}