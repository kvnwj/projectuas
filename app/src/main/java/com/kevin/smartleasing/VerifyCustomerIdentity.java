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
public class VerifyCustomerIdentity extends Fragment {

    public VerifyCustomerIdentity() {
        // Required empty public constructor
    }

    private String url_post_konfirmasi_kredit = "http://kev.inkomtek.co.id/umn/android/konfirmasiKredit.php";
    private Button btnBack;
    //    Tag untuk tabel transaksi
    private static final String TAG_nama_produk = "nama_produk";
    private static final String TAG_nama_depan_customer = "nama_depan_cust";
    private static final String TAG_nama_belakang_customer = "nama_belakang_cust";
    private static final String TAG_ID_transaksi = "ID_transaksi";
    private static final String TAG_harga_otr = "harga_otr";
    private static final String TAG_uang_muka = "uang_muka";
    private static final String TAG_tenor = "tenor";
    private static final String TAG_bunga = "bunga";
    private static final String TAG_angsuran = "angsuran_per_bulan";
    private static final String TAG_REJECTED = "REJECTED";
    private static final String TAG_OK = "OK";
    private static final String TAG_status = "status";

    private static final String TAG_nama_depan_emp = "nama_depan_emp";
    private static final String TAG_nama_belakang_emp = "nama_belakang_emp";
    private static final String TAG_jenis_kelamin = "jenis_kelamin";
    private static final String TAG_nik = "nik";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_tempat_lahir = "tempat_lahir";
    private static final String TAG_tanggal_lahir = "tanggal_lahir";
    private static final String TAG_no_telp = "no_telp";
    private static final String TAG_ID_employee = "ID_employee";

    private HashMap<String, String> thisTransaction;
    private HashMap<String, String> mEmployeeProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_verify_customer_identity, container, false);

//        Ambil data posisi klik dari Fragment EmpCustomerList
        Bundle bundle = this.getArguments();
        int position = bundle.getInt("position");

//        Lalu ambil data transaksi dari Activity EmployeeMain
        EmployeeMain activity = (EmployeeMain) getActivity();
        assert activity != null;
        thisTransaction = activity.getSingleTransaction(position);
        mEmployeeProfile = activity.getEmployeeProfile();
//
//        if (thisTransaction != null && mEmployeeProfile != null){
//            Toast.makeText(getContext(), "Employee ID is: "+mEmployeeProfile.get(TAG_ID_employee)+"\nTransaction ID is: "+thisTransaction.get(TAG_ID_transaksi), Toast.LENGTH_LONG).show();
//        }

//        Tampilkan data dari thisTransaction
        TextView txtNamaLengkapVerifikasi = v.findViewById(R.id.txtNamaLengkapVerifikasi);
        TextView txtNIKverifikasi = v.findViewById(R.id.txtNIKverifikasi);
        TextView txtAlamatVerifikasi = v.findViewById(R.id.txtAlamatVerifikasi);
        TextView txtTempatLahirVerifikasi = v.findViewById(R.id.txtTempatLahirVerifikasi);
        TextView txtTanggalLahirVerifikasi = v.findViewById(R.id.txtTanggalLahirVerifikasi);
        TextView txtJKverifikasi = v.findViewById(R.id.txtJKverifikasi);
        TextView txtNoTelpVerifikasi = v.findViewById(R.id.txtNoTelpVerifikasi);

        TextView txtNamaProdukVerifikasi = v.findViewById(R.id.txtNamaProdukVerifikasi);
        TextView txtHargaOTRVerifikasi = v.findViewById(R.id.txtHargaOTRVerifikasi);
        TextView txtUangMukaVerifikasi = v.findViewById(R.id.txtUangMukaVerifikasi);
        TextView txtTenorVerifikasi = v.findViewById(R.id.txtTenorVerifikasi);
        TextView txtBungaVerifikasi = v.findViewById(R.id.txtBungaVerifikasi);
        TextView txtAngsuranVerifikasi = v.findViewById(R.id.txtAngsuranVerifikasi);
        TextView txtStatusVerifikasi = v.findViewById(R.id.txtStatusVerifikasi);

//        Penyesuaian dengan tampilan
        String namaLengkap = "Nama Lengkap: " + thisTransaction.get(TAG_nama_depan_customer) + " " + thisTransaction.get(TAG_nama_belakang_customer);
        String nik = "NIK: " + thisTransaction.get(TAG_nik);
        String alamat = "Alamat: " + thisTransaction.get(TAG_alamat);
        String tempatLahir = "Tempat Lahir: " + thisTransaction.get(TAG_tempat_lahir);
        String tanggalLahir = "Tanggal Lahir: " + thisTransaction.get(TAG_tanggal_lahir);
        String jenisKelamin;
        if (thisTransaction.get(TAG_jenis_kelamin).equals("L")) {
            jenisKelamin = "Jenis Kelamin: Laki-Laki";
        } else if (thisTransaction.get(TAG_jenis_kelamin).equals("P")) {
            jenisKelamin = "Jenis Kelamin: Perempuan";
        } else {
            jenisKelamin = "Jenis Kelamin: ";
        }
        String nomorTelepon = "Nomor Telepon: " + thisTransaction.get(TAG_no_telp);
        String namaProduk = "Nama Produk: " + thisTransaction.get(TAG_nama_produk);
        String hargaOTR = "Harga OTR: Rp. " + thisTransaction.get(TAG_harga_otr);
        String uangMuka = "Uang Muka: Rp. " + thisTransaction.get(TAG_uang_muka);
        String tenor = "Tenor: " + thisTransaction.get(TAG_tenor) + " bulan";
        Double bungaDouble = Double.parseDouble(thisTransaction.get(TAG_bunga));
        bungaDouble = bungaDouble * 100;
        String bunga = "Bunga: " + bungaDouble + "%";
        String angsuranPerBulan = "Angsuran Per Bulan: Rp. " + thisTransaction.get(TAG_angsuran);
        String status = "Status: " + thisTransaction.get(TAG_status);

        txtNamaLengkapVerifikasi.setText(namaLengkap);
        txtNIKverifikasi.setText(nik);
        txtAlamatVerifikasi.setText(alamat);
        txtTempatLahirVerifikasi.setText(tempatLahir);
        txtTanggalLahirVerifikasi.setText(tanggalLahir);
        txtJKverifikasi.setText(jenisKelamin);
        txtNoTelpVerifikasi.setText(nomorTelepon);
        txtNamaProdukVerifikasi.setText(namaProduk);
        txtHargaOTRVerifikasi.setText(hargaOTR);
        txtUangMukaVerifikasi.setText(uangMuka);
        txtTenorVerifikasi.setText(tenor);
        txtBungaVerifikasi.setText(bunga);
        txtAngsuranVerifikasi.setText(angsuranPerBulan);
        txtStatusVerifikasi.setText(status);

//        Coding untuk tombol Reject
        Button btnReject = v.findViewById(R.id.btnReject);
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Buat sebuah alert dialog baru
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Apakah Anda yakin untuk Menolak Kredit Customer?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Saat ya di klik
                        gantiStatusCredit(TAG_REJECTED);
//                        Kembali ke Fragment EmpCustomerList
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmpCustomerList()).commit();
//                        getActivity().getSupportFragmentManager().popBackStack();
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

        //        Coding untuk tombol Approve
        Button btnApprove = v.findViewById(R.id.btnApprove);
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Buat sebuah alert dialog baru
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Apakah Anda yakin untuk Menyetujui Kredit Customer?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Saat ya di klik
                        gantiStatusCredit(TAG_OK);
//                        Kembali ke Fragment EmpCustomerList
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmpCustomerList()).commit();
//                        getActivity().getSupportFragmentManager().popBackStack();
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

//        Code untuk tombol Back
        btnBack = v.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Kembali tampilkan Customer List
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmpCustomerList()).commit();
//                Pop Back Stack
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

//        Return view seperti yang seharusnya
        return v;
    }

    private void gantiStatusCredit(final String statusBaru) {
//        Melakukan Post Request ke server menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.start();

//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_post_konfirmasi_kredit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("success");
//                    Program bila kredit berhasil dikonfirmasi
                    if (sukses == 1) {
                        Toast.makeText(getContext(), object.getString("message"), Toast.LENGTH_LONG).show();
//                        Setelah selesai lalu kembali ke EmpCustomerList
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
//                Toast.makeText(getContext(), "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(TAG_status, statusBaru);
                params.put(TAG_ID_employee, mEmployeeProfile.get(TAG_ID_employee));
                params.put(TAG_ID_transaksi, thisTransaction.get(TAG_ID_transaksi));
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

