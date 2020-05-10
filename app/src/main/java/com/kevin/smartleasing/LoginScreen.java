package com.kevin.smartleasing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button btnLogin = findViewById(R.id.btnLogin);
        final EditText edtUserName = findViewById(R.id.username);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUserName.getText().toString();
//                Program sementara untuk login ke Employee/Customer
                if (userName.equals("emp")) {
//                Membuat Intent untuk membuka activity employee
                    Log.i("LoginScreen", "Username: " + userName + " Accessing EmployeeMain");
                    Intent i = new Intent(LoginScreen.this, EmployeeMain.class);
                    startActivity(i);
                } else {
//                    Membuat Intent untuk membuka activity Customer
                    Log.i("LoginScreen", "Username: " + userName + " Accessing CustomerMain");
                    Intent i = new Intent(LoginScreen.this, CustomerMain.class);
                    startActivity(i);
                }
            }
        });

        //        Code untuk tombol Buat Akun Baru
        Button btnCreateNewAcc = findViewById(R.id.btnCreateAcc);
        btnCreateNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Membuat Intent untuk membuka activity
                Intent i = new Intent(getApplicationContext(), CreateNewAcc.class);
                startActivity(i);
            }
        });

//        Set background status bar to match logo color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }
}
