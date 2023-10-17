package com.example.studentapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    private LinearLayout layoutName, layoutEmail,layoutPhone,layoutRPass;
    private TextView tvTitle, tvAccountInfo;
    private Button btnlogin;
    private EditText etName, etEmail, etPhone, etUserId, etPass, etRPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        layoutName = findViewById(R.id.layoutName);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPhone = findViewById(R.id.layoutPhone);
        layoutRPass = findViewById(R.id.layoutRPass);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPass = findViewById(R.id.etPass);
        etRPass = findViewById(R.id.etRPass);
        etName = findViewById(R.id.etName);
        etUserId = findViewById(R.id.etUserId);
        tvTitle = findViewById(R.id.tvTitle);
        tvAccountInfo = findViewById(R.id.tvAccountInfo);
        btnlogin = findViewById(R.id.btnlogin);
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sp.getString("user_id", "");
        String password = sp.getString("password", "");
        if (!username.isEmpty()) {
            Intent i = new Intent(Signup.this, login.class);
            startActivity(i);
        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this, login.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btnExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btnGO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = etUserId.getText().toString().trim();
                String pass = etPass.getText().toString().trim();
                String rPass = etRPass.getText().toString().trim();
                String Email = etEmail.getText().toString().trim();
                String Phone = etPhone.getText().toString().trim();
                String errMsg = "";
                if (etName.length() < 5) {
                    errMsg += "Name is too short\n";
                }
                if (userId.length() < 4) {
                    errMsg += "Invalid user id\n";
                }
                if (!pass.equals(rPass)) {
                    errMsg += "Password didn't match\n";
                }
                if (!Phone.equals("")) {
                    String prgx = "^01[87635][0-9]{8,8}$";
                    Pattern pattern = Pattern.compile(prgx, Pattern.UNICODE_CASE);
                    Matcher matcher = pattern.matcher(Phone);
                    if (matcher.matches() == false) {
                        errMsg += " Phone is invalid" + "\n";
                    }
                } else {
                    errMsg += " phone is empty" + "\n";
                }
                if (!Email.equals("")) {
                    String ergx = "^[a-z\\d\\._]+@([a-z\\d-]+\\.)+[a-z]{2,6}$";
                    Pattern pattern1 = Pattern.compile(ergx, Pattern.UNICODE_CASE);
                    Matcher matcher1 = pattern1.matcher(Email);
                    if (matcher1.matches() == false) {
                        errMsg += " email is invalid" + "\n";
                    }
                } else {
                    errMsg += " email is empty" + "\n";
                }
                if (errMsg.isEmpty()) {
                    SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
                    SharedPreferences.Editor spe = sp.edit();
                    spe.putString("user_id", userId);
                    spe.putString("password", pass);
                    spe.apply();
                    Intent i = new Intent(Signup.this, login.class);
                    startActivity(i);
                } else {
                    Toast.makeText(Signup.this, errMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}