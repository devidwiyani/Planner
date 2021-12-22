package com.example.planner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String KEY = "seekBarValue";
    int seekBarValue;
    TextView alertNama, alertEmail, alertUmur, alertGender, alertStatus, umur1, alertGreeting;
    EditText regUsername, regPassword, regName;
    SeekBar seekBar;
    RadioGroup groupRadio;
    RadioButton buttonRadio1, buttonRadio2;
    CheckBox checkBox;
    Button btnRegister, btnOk;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DBHelper(this);

        regUsername = findViewById(R.id.reg_username);
        regPassword = findViewById(R.id.reg_password);;
        regName = findViewById(R.id.reg_name);
        seekBar = findViewById(R.id.seek_bar);
        umur1 = findViewById(R.id.text_umur1);
        groupRadio = findViewById(R.id.button_radio);
        buttonRadio1 = findViewById(R.id.button_radio1);
        buttonRadio2 = findViewById(R.id.button_radio2);
        checkBox = findViewById(R.id.check_box);
        btnRegister = findViewById(R.id.btn_register);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                umur1.setText(String.valueOf(progress));
                seekBarValue = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUsername = regUsername.getText().toString();
                String getPassword = regPassword.getText().toString();
                String getName = regName.getText().toString();
                int umurSekk = seekBar.getProgress();
                String getUmur = String.valueOf(umurSekk);
                int selectedGender = groupRadio.getCheckedRadioButtonId();

                if (getUsername.isEmpty() || getPassword.isEmpty() || getName.isEmpty() || seekBar.getProgress() == 0 || groupRadio.getCheckedRadioButtonId() == 0 ){
                    Toast.makeText(RegisterActivity.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
                }else {
                    // MEMBUAT ALERT DIALOG
                    AlertDialog.Builder dialog1 = new AlertDialog.Builder(RegisterActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.custom_alert,null);

                    alertGreeting = view.findViewById(R.id.alert_greeting);
                    alertNama = view.findViewById(R.id.alert_nama);
                    alertEmail = view.findViewById(R.id.alert_email);
                    alertUmur = view.findViewById(R.id.alert_umur);
                    alertGender = view.findViewById(R.id.alert_gender);
                    alertStatus = view.findViewById(R.id.alert_status);

                    // MENGESET GREETING PADA ALERT DIALOG
                    alertGreeting.setText("Hi " + getName + " \nberikut data kamu yang akan disimpan : ");
                    alertNama.setText(getUsername);
                    alertUmur.setText(String.valueOf(getUmur));

                    // MENGESET TEXT GENDER PADA ALERT DIALOG BERDASARKAN GENDER YANG DI-SELECT
                    if (selectedGender == buttonRadio1.getId()){
                        alertGender.setText("Perempuan");
                    } else if (selectedGender == buttonRadio2.getId()){
                        alertGender.setText("Laki- Laki");
                    }
                    String getGender = alertGender.getText().toString();

                    // MENGESET TEXT KEBENARAN DATA PADA ALERT DIALOG BERDASARKAN STATUS CENTANG PADA CHECK BOX
                    if (checkBox.isChecked()) {
                        alertStatus.setText("( Data Sudah Benar )");
                    } else {
                        alertStatus.setText("( Pastikan Data Benar)");
                    }

                    view.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            StringRequest request = new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Log.d("TAG", String.valueOf(object));
                                    if (object.getBoolean("success")){
                                        JSONObject user = object.getJSONObject("user");
                                        SharedPreferences userPref = getApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = userPref.edit();
                                        editor.putString("token",object.getString("token"));
                                        editor.putBoolean("isLoggedIn", true);
                                        editor.apply();

                                        ContentValues values = new ContentValues();

                                        values.put(DBHelper.row_username, user.getString("username"));
                                        values.put(DBHelper.row_name, user.getString("name"));
                                        values.put(DBHelper.row_umur, user.getString("umur"));
                                        values.put(DBHelper.row_gender, user.getString("gender"));
                                        dbHelper.insertUser(values);

                                        Toast.makeText(RegisterActivity.this, "Register Succesful", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }catch(JSONException e){
                                    e.printStackTrace();
                                }
                            },error -> {
                                error.printStackTrace();
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> map = new HashMap<>();
                                    map.put("username",getUsername);
                                    map.put("password",getPassword);
                                    map.put("name",getName);
                                    map.put("umur",getUmur);
                                    map.put("gender",getGender);
                                    return map;
                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                            queue.add(request);
                        }
                    });

                    dialog1.setView(view);
                    AlertDialog dialog2 = dialog1.create();
                    dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog2.show();
                }
            }
        });
    }
}