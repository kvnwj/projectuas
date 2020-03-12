package com.kevin.smartleasing;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Membuat Intent untuk membuka activity
                EditText username = findViewById(R.id.username);
                Log.i("infoku", username.getText().toString());
                Intent i = new Intent(getApplicationContext(), EmployeeMain.class);
                startActivity(i);
            }
        });
    }
}
