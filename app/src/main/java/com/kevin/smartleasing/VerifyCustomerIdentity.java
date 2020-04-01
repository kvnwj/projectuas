package com.kevin.smartleasing;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyCustomerIdentity extends Fragment {

    public VerifyCustomerIdentity() {
        // Required empty public constructor
    }

    private Spinner spinnerGender;
    private String[] gender = {"-Jenis Kelamin-", "Laki-laki", "Perempuan"};
    private String pickedGender;

    private TextView txtTglLahir;
    private int year, month, day;

    private Button btnVerify;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_verify_customer_identity, container, false);
//        Coding untuk spinner gender
        spinnerGender = v.findViewById(R.id.spinnerJK);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pickedGender = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

//        Coding untuk date picker tanggal lahir
        txtTglLahir = v.findViewById(R.id.txtTglLahir);
        txtTglLahir.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getContext()));
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        showDate(year, month + 1, dayOfMonth);
                    }
                });
                datePickerDialog.show();
            }
        });

//        Coding untuk tombol Verify
        btnVerify = v.findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Buat sebuah alert dialog baru
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Apakah Anda yakin data yang dimasukkan benar?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Saat ya di klik
                        Toast.makeText(getContext(), "Terima Kasih, Permintaan Anda Sedang Diproses", Toast.LENGTH_LONG).show();
//                        Kembali ke Fragment CustomerList
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustomerList()).commit();
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
        return v;
    }

    private void showDate(int year, int month, int day) {
        String dateString = day + " / " + month + " / " + year;
        txtTglLahir.setText(dateString);
    }
}
