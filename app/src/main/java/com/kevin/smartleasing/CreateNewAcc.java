package com.kevin.smartleasing;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class CreateNewAcc extends AppCompatActivity {

    private String[] gender = {"-Jenis Kelamin-", "Laki-laki", "Perempuan"};
    private String[] genderForDB = {"X", "L", "P"};
    private String jenis_kelamin, username, password, nama_depan, nama_belakang, nik, alamat, tempat_lahir, tanggal_lahir, no_telp;

    private String url_post_new_acc = "http://kev.inkomtek.co.id/umn/android/createAcc.php";

    private TextView txtTglLahir;

    private static final String TAG_username = "username";
    private static final String TAG_password = "password";
    private static final String TAG_nama_depan = "nama_depan";
    private static final String TAG_nama_belakang = "nama_belakang";
    private static final String TAG_jenis_kelamin = "jenis_kelamin";
    private static final String TAG_nik = "nik";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_tempat_lahir = "tempat_lahir";
    private static final String TAG_tanggal_lahir = "tanggal_lahir";
    private static final String TAG_no_telp = "no_telp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acc);

        final EditText edtNamaDepan = findViewById(R.id.edtNamaDepanBaru);
        final EditText edtNamaBelakang = findViewById(R.id.edtNamaBelakangBaru);
        final EditText edtNIK = findViewById(R.id.edtNIKBaru);
        final EditText edtAlamat = findViewById(R.id.edtAlamatBaru);
        final EditText edtNoTelp = findViewById(R.id.edtNoTelpBaru);
        final EditText edtTempatLahir = findViewById(R.id.edtTempatLahirBaru);
        final EditText edtUsername = findViewById(R.id.edtUsernameBaru);
        final EditText edtPassword = findViewById(R.id.edtPasswordBaru);
        final EditText edtPasswordKonfirmasi = findViewById(R.id.edtPasswordBaru2);

//        Coding untuk spinner gender
        Spinner spinnerGender = findViewById(R.id.spinnerJKBaru);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Get gender yang dipilih oleh user
                jenis_kelamin = genderForDB[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateNewAcc.this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

//        Coding untuk date picker tanggal lahir
        txtTglLahir = findViewById(R.id.txtTglLahirBaru);
        txtTglLahir.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateNewAcc.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        setDate(year, month + 1, dayOfMonth);
                    }
                });
                datePickerDialog.show();
            }
        });

//        Coding untuk tombol Create New Account
        Button btnCreateNewAcc = findViewById(R.id.btnCreateAccBaru);
        btnCreateNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String p1, p2;
                p1 = edtPassword.getText().toString();
                p2 = edtPasswordKonfirmasi.getText().toString();
//                Bila Password sudah sama dengan konfirmasinya
                if (p1.equals(p2)) {
//                    Buat sebuah alert dialog baru
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CreateNewAcc.this);
                    dialog.setTitle("Konfirmasi");
                    dialog.setMessage("Apakah Anda yakin data untuk akun baru Anda sudah benar?");
                    dialog.setPositiveButton("Ya, Buat Akun Sekarang", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        Saat ya di klik, ambil semua data dari form
                            username = edtUsername.getText().toString();
                            password = p1;
                            nama_depan = edtNamaDepan.getText().toString();
                            nama_belakang = edtNamaBelakang.getText().toString();
                            nik = edtNIK.getText().toString();
                            alamat = edtAlamat.getText().toString();
                            tempat_lahir = edtTempatLahir.getText().toString();
                            no_telp = edtNoTelp.getText().toString();

//                        panggil fungsi untuk masukkan  data akun baru ke database
                            try {
                                postNewAccount();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        Saat Tidak di klik
                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(CreateNewAcc.this, "Password dan Konfirmasi Password Berbeda. Silahkan Disamakan.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void postNewAccount() throws JSONException {
//        Melakukan Post Request ke Server menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(CreateNewAcc.this);
        queue.start();

//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_post_new_acc,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Buat JSONObject response
                            JSONObject object = new JSONObject(response);
                            int sukses = object.getInt("success");
//                            Program bila data berhasil disimpan
                            if (sukses == 1) {
                                Toast.makeText(CreateNewAcc.this, "Terima Kasih, Silakan Login dengan Akun Baru Anda.", Toast.LENGTH_LONG).show();
                                finish();
//                                Setelah selesai lalu kembali ke Halaman Login Screen
                                Intent i = new Intent(CreateNewAcc.this, LoginScreen.class);
                                startActivity(i);
                            } else if (sukses == 0) {
                                Toast.makeText(CreateNewAcc.this, "Username Anda sudah ada yang ambil.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(CreateNewAcc.this, "Silakan isi Username dan Password Anda", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        Toast.makeText(CreateNewAcc.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(TAG_username, username);
                params.put(TAG_alamat, alamat);
                params.put(TAG_password, password);
                params.put(TAG_nama_depan, nama_depan);
                params.put(TAG_nama_belakang, nama_belakang);
                params.put(TAG_jenis_kelamin, jenis_kelamin);
                params.put(TAG_nik, nik);
                params.put(TAG_tempat_lahir, tempat_lahir);
                params.put(TAG_tanggal_lahir, tanggal_lahir);
                params.put(TAG_no_telp, no_telp);
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

    private void setDate(int year, int month, int day) {
        String dateString = day + " / " + month + " / " + year;
        txtTglLahir.setText(dateString);
        tanggal_lahir = year + "-" + month + "-" + day;
    }
}
