package com.example.studentapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private EditText etUserId, etPass;
    private CheckBox etUser, etLogin;
    private Button btnGo,btnsignup,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUserId = findViewById(R.id.etUserId);
        etPass = findViewById(R.id.etPass);
        etUser = findViewById(R.id.cbUserId);
        etLogin = findViewById(R.id.cbLogin);
        btnGo = findViewById(R.id.btnGO);
        exit=findViewById(R.id.btnExit);
        btnsignup=findViewById(R.id.btnsignup);
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sp.getString("user_id", "");
        String password = sp.getString("password", "");
        boolean rmu = sp.getBoolean("rem_userid", false);
        boolean rml = sp.getBoolean("rem_userlog", false);
        if(rmu){
            etUserId.setText(username);
        }
        if(rml){
           etPass.setText(password);
        }
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean remUserId = etUser.isChecked();
                boolean remlogin = etLogin.isChecked();
                String userId = etUserId.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String errMsg = "";
                if (username.equals(userId) && password.equals(pass)) {
                    SharedPreferences.Editor spe = sp.edit();
                    spe.putString("user_id", userId);
                    spe.putString("password", pass);
                    spe.putBoolean("rem_userid", remUserId);
                    spe.putBoolean("rem_userlog", remlogin);
                    spe.apply();
                    Intent i = new Intent(login.this, StudentList.class);
                    startActivity(i);
                } else {
                    errMsg += "Password and Username didn't match\n";
                    Toast.makeText(login.this, errMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}