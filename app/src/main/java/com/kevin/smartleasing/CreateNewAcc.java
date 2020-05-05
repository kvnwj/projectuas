package com.kevin.smartleasing;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class CreateNewAcc extends AppCompatActivity {

    private Spinner spinnerGender;
    private String[] gender = {"-Jenis Kelamin-", "Laki-laki", "Perempuan"};
    private String pickedGender;

    private TextView txtTglLahir;
    private int year, month, day;

    //    private Button btnBack;
    private Button btnCreateNewAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_acc);

//        Coding untuk spinner gender
        spinnerGender = findViewById(R.id.spinnerJKBaru);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Get gender yang dipilih oleh user
                pickedGender = gender[position];
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
                        showDate(year, month + 1, dayOfMonth);
                    }
                });
                datePickerDialog.show();
            }
        });

//        Coding untuk tombol Back
//        btnBack = findViewById(R.id.btnBackBaru);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        Coding untuk tombol Create New Account
        btnCreateNewAcc = findViewById(R.id.btnCreateAccBaru);
        btnCreateNewAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Buat sebuah alert dialog baru
                AlertDialog.Builder dialog = new AlertDialog.Builder(CreateNewAcc.this);
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Apakah Anda yakin data untuk akun baru Anda sudah benar?");
                dialog.setPositiveButton("Ya, Buat Akun Sekarang", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Saat ya di klik, koding untuk masukkan data akun ke database
                        Toast.makeText(CreateNewAcc.this, "Terima Kasih, Silakan Login dengan Akun Baru Anda.", Toast.LENGTH_LONG).show();
//                        Setelah selesai lalu kembali ke Halaman Login Screen
                        Intent i = new Intent(CreateNewAcc.this, LoginScreen.class);
                        startActivity(i);
                    }
                });
                dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Saat Tidak di klik
                    }
                });
                dialog.show();
            }
        });

    }

    private void showDate(int year, int month, int day) {
        String dateString = day + " / " + month + " / " + year;
        txtTglLahir.setText(dateString);
    }
}
