package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustCreditDetail extends Fragment {

    //    Tags buat Credit Customer
    private static final String TAG_uang_muka = "uang_muka";
    private static final String TAG_tenor = "tenor";
    private static final String TAG_angsuran_per_bulan = "angsuran_per_bulan";
    private static final String TAG_status = "status";
    private static final String TAG_nama_produk = "nama_produk";
    private static final String TAG_harga_otr = "harga_otr";
    private static final String TAG_bunga = "bunga";
    private static final String TAG_nama_depan_emp = "nama_depan_emp";
    private static final String TAG_nama_belakang_emp = "nama_belakang_emp";

    public CustCreditDetail() {
        // Required empty public constructor
    }

    HashMap<String, String> thisCredit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cust_credit_detail, container, false);

//        Ambil data posisi klik dari Fragment CustCreditList
        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position");

//        Lalu Ambil data Credit dari Activity CustomerMain
        CustomerMain activity = (CustomerMain) getActivity();
        thisCredit = activity.getSingleCredit(position);

//        Tampilkan data dari thisCredit
        TextView txtNamaProduk = v.findViewById(R.id.txtNamaProduk);
        TextView txtHargaOTR = v.findViewById(R.id.txtHargaOTR);
        TextView txtUangMuka = v.findViewById(R.id.txtUangMuka);
        TextView txtTenor = v.findViewById(R.id.txtTenor);
        TextView txtBunga = v.findViewById(R.id.txtBunga);
        TextView txtAngsuran = v.findViewById(R.id.txtAngsuran);
        TextView txtNamaKaryawan = v.findViewById(R.id.txtNamaKaryawan);

        String hargaOTR = "Rp. " + thisCredit.get(TAG_harga_otr);
        String uangMuka = "Rp. " + thisCredit.get(TAG_uang_muka);
        String tenor = thisCredit.get(TAG_tenor) + " Bulan";
//        Mengubah Tampilan bunga dari decimal ke persen
        double bungaDesimal = Double.parseDouble(thisCredit.get(TAG_bunga));
        bungaDesimal = bungaDesimal * 100;
        String bunga = bungaDesimal + "%";
        String angsuranPerBulan = "Rp. " + thisCredit.get(TAG_angsuran_per_bulan);
        String namaKaryawan = thisCredit.get(TAG_nama_depan_emp) + " " + thisCredit.get(TAG_nama_belakang_emp);

        txtNamaProduk.setText(thisCredit.get(TAG_nama_produk));
        txtHargaOTR.setText(hargaOTR);
        txtUangMuka.setText(uangMuka);
        txtTenor.setText(tenor);
        txtBunga.setText(bunga);
        txtAngsuran.setText(angsuranPerBulan);
        txtNamaKaryawan.setText(namaKaryawan);

//        Code untuk tombol OK
        Button btnOK = v.findViewById(R.id.btnOk);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustCreditList()).commit();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return v;
    }
}
