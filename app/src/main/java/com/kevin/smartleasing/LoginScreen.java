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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class LoginScreen extends AppCompatActivity {

    private String url_login = "http://kev.inkomtek.co.id/umn/android/loginProcess.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Button btnLogin = findViewById(R.id.btnLogin);
        final EditText edtUserName = findViewById(R.id.username);
        final EditText edtPassword = findViewById(R.id.password);

//        Code saat btnLogin di klik
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUserName.getText().toString();
                final String password = edtPassword.getText().toString();
//                Gunakan VOLLEY untuk proses login
                RequestQueue queue = Volley.newRequestQueue(LoginScreen.this);
                queue.start();
//                Buat StringRequest Baru
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                                Parse Data dari Response
                                try {
                                    JSONObject object = new JSONObject(response);
                                    int success = object.getInt("success");
                                    switch (success) {
                                        case 1:
//                                            Code Bila sukses Login
                                            myLoginHandler(object);
                                            break;
                                        case 0:
//                                            Code Bila password salah
                                            String message = object.getString("message");
                                            Toast.makeText(LoginScreen.this, message, Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:
//                                            Code Bila user tidak ditemukan
                                            Toast.makeText(LoginScreen.this, "User Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -2:
//                                            Code bila data yang dikirim ke DB kosong
                                            Toast.makeText(LoginScreen.this, "Silahkan isi Username dan Password Anda", Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("LoginScreen", error.toString());
                        Toast.makeText(LoginScreen.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Kirim data username dan password ke server
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username);
                        params.put("password", password);
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/x-www-form-urlencoded");
                        return params;
                    }
                };
                queue.getCache().clear();
                queue.add(stringRequest);
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

    private void myLoginHandler(JSONObject object) throws JSONException {
//        Tentukan dulu apakah customer atau employee yang login
        String role = object.getString("userType");
        if (role.equals("customer")) {
//            Login ke laman customer
            Intent i = new Intent(LoginScreen.this, CustomerMain.class);
            i.putExtra("ID_customer", object.getString("ID"));
            startActivity(i);
        } else {
//            Login ke laman employee
            Intent i = new Intent(LoginScreen.this, EmployeeMain.class);
            i.putExtra("ID_employee", object.getString("ID"));
            startActivity(i);
        }
    }
}