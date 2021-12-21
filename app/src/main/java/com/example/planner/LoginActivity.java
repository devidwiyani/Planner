package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    EditText logUsername, logPassword;
    Button btnLogin;
    DBHelper dbHelper;
    SharedPrefManager spm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(spm.getSPId(this) > 0)
        {
            Intent toDashboard = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(toDashboard);
        }
        logUsername = findViewById(R.id.log_username);
        logPassword = findViewById(R.id.log_password);
        btnLogin = findViewById(R.id.btn_login);
        dbHelper = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUsername = logUsername.getText().toString().trim();
                String getPassword = logPassword.getText().toString().trim();
                Cursor getName = dbHelper.selectUser(getUsername);

                Boolean res = dbHelper.checkUser(getUsername, getPassword);
                if (res){
                    Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    spm.saveSPInt(getBaseContext(), spm.SP_ID, dbHelper.checkUserId(getUsername, getPassword));
                    intent.putExtra("data_name", String.valueOf(getName));
                    intent.putExtra("UserNameLogin", getUsername);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void klikRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

}