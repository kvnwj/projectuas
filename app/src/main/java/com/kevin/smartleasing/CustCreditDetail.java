package com.kevin.smartleasing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private static final String TAG_ID_transaksi = "ID_transaksi";


    private static final String url_delete_credit = "http://kev.inkomtek.co.id/umn/android/deleteCredit.php";

    public CustCreditDetail() {
        // Required empty public constructor
    }

    private HashMap<String, String> thisCredit;

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
        TextView txtStatus = v.findViewById(R.id.txtStatus);


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
        txtStatus.setText(thisCredit.get(TAG_status));

////        Code untuk tombol OK
//        Button btnOK = v.findViewById(R.id.btnOk);
//        btnOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustCreditList()).commit();
//                getActivity().getSupportFragmentManager().popBackStack();
//            }
//        });

//        Code untuk tombol hapus kredit customer
        Button btnDelete = v.findViewById(R.id.btnHapusKredit);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Apakah Anda yakin untuk Menghapus Kredit Anda?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Panggil fungsi untuk masukkan data ke database
                        deleteThisCredit();
                    }
                });
                dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    private void deleteThisCredit() {
//        Melakukan Post Request ke server menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.start();

//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_delete_credit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("success");
//                    Program bila kredit berhasil dihapus
                    if (sukses == 1) {
                        Toast.makeText(getContext(), "Proses Penghapusan Kredit Selesai, Terima Kasih", Toast.LENGTH_LONG).show();
//                        Setelah selesai lalu kembali ke Halaman Get Credit List
                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        startActivity(intent);
                    } else if (sukses == 0) {
                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                Toast.makeText(getContext(), "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(TAG_ID_transaksi, thisCredit.get(TAG_ID_transaksi));
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
}
