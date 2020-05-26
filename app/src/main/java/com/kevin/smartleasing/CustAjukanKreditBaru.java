package com.kevin.smartleasing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.fragment.app.Fragment;

public class CustAjukanKreditBaru extends Fragment {

    //    Tags untuk produk list
    private static final String TAG_nama_produk = "nama_produk";
    private static final String TAG_harga_otr = "harga_otr";
    private static final String TAG_bunga = "bunga";
    private static final String TAG_ID_produk = "ID_produk";
    private static final String TAG_tenor = "tenor";
    private static final String TAG_ID_customer = "ID_customer";
    private static final String TAG_uang_muka = "uang_muka";
    private static final String TAG_angsuran_per_bulan = "angsuran_per_bulan";

    private String url_post_new_credit = "http://kev.inkomtek.co.id/umn/android/createNewCredit.php";

    public CustAjukanKreditBaru() {
        // Required empty public constructor
    }

    private ArrayList<String> productName = new ArrayList<>();
    private String[] tenorArray = {"12", "24", "48"};
    private String pickedTenor;
    //    Variabel yang digunakan untuk POST ke server
    private HashMap<String, String> newCreditData = new HashMap<>();

    private int productIndex;
    private ArrayList<HashMap<String, String>> mProductList;
    private HashMap<String, String> mCustomerProfile;
    private CustomerMain activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cust_ajukan_kredit_baru, container, false);
        activity = (CustomerMain) getActivity();
        assert activity != null;
        mProductList = activity.getProductList();
        mCustomerProfile = activity.getCustomerProfile();
        final TextView txtHargaOTR = v.findViewById(R.id.txtHargaOTR);
        final TextView txtBunga = v.findViewById(R.id.txtBungaBaru);

//        Masukkan data ke variabel productName
        for (int i = 0; i < mProductList.size(); i++) {
            productName.add(mProductList.get(i).get(TAG_nama_produk));
        }

//        Coding untuk spinner nama produk
        productName.add(0, "-Pilih Produk Anda-");
        Spinner spinnerNamaProduk = v.findViewById(R.id.spinnerNamaProduk);
        spinnerNamaProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Get produk yang dipilih user
//                Code untuk menghindari elemen "-Pilih Produk Anda-"
                int adjPosition = (position >= 1) ? (position -= 1) : (position = 0);

//                Tampilkan harga dan bunga produk yang dipilih
                String myHargaOTR = "Harga OTR: Rp. " + mProductList.get(adjPosition).get(TAG_harga_otr);
                double bungaPersen = Double.parseDouble(Objects.requireNonNull(mProductList.get(adjPosition).get(TAG_bunga)));
                bungaPersen = bungaPersen * 100;
                String myBunga = "Bunga: " + bungaPersen + "%";
                txtHargaOTR.setText(myHargaOTR);
                txtBunga.setText(myBunga);

//                Ambil posisi klik Customer
                productIndex = adjPosition;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] productNameArray = productName.toArray(new String[productName.size()]);
        ArrayAdapter<String> adapterProduk = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, productNameArray);
        adapterProduk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNamaProduk.setAdapter(adapterProduk);

//        Code untuk spinner Tenor
        final Spinner spinnerTenor = v.findViewById(R.id.spinnerTenor);
        spinnerTenor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Get Tenor yang dipilih user
                pickedTenor = tenorArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapterTenor = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, tenorArray);
        adapterTenor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTenor.setAdapter(adapterTenor);

        final EditText edtUangMuka = v.findViewById(R.id.edtUangMuka);
        final ProgressBar progressBar = v.findViewById(R.id.creditProgressBar);
        final TextView txtAPB = v.findViewById(R.id.txtAPB);
//        Code untuk button melakukan perhitungan
        Button btnHitung = v.findViewById(R.id.btnHitung);
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCreditData.put(TAG_uang_muka, edtUangMuka.getText().toString());
//                Hitung angsuran_per_bulan
                int newApb = getAngsuranPerBulan();
                txtAPB.setText("Angsuran Per Bulan: Rp. " + newApb);
            }
        });


//        Code untuk tombol submit pengajuan kredit customer
        Button btnSubmit = v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Apakah Anda yakin data Kredit Baru Anda sudah Benar dan Lengkap?");
                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressBar.setVisibility(View.VISIBLE);
//                        Ambil data
                        newCreditData.put(TAG_ID_produk, mProductList.get(productIndex).get(TAG_ID_produk));
                        newCreditData.put(TAG_ID_customer, mCustomerProfile.get(TAG_ID_customer));
                        newCreditData.put(TAG_uang_muka, edtUangMuka.getText().toString());
                        newCreditData.put(TAG_tenor, pickedTenor);
                        newCreditData.put(TAG_angsuran_per_bulan, Integer.toString(getAngsuranPerBulan()));

//                        Panggil fungsi untuk masukkan data ke database
                        try {
                            postNewCredit();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.INVISIBLE);
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

    private void postNewCredit() throws JSONException {
//        Melakukan Post Request ke server menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.start();

//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_post_new_credit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("success");
//                    Program bila data berhasil disimpan
                    if (sukses == 1) {
                        Toast.makeText(getContext(), "Proses Pengajuan Kredit Selesai, Terima Kasih", Toast.LENGTH_LONG).show();
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
                return newCreditData;
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

    private int getAngsuranPerBulan() {
        double apb;
        double hargaOTR = Double.parseDouble(Objects.requireNonNull(mProductList.get(productIndex).get(TAG_harga_otr)));
        double bunga = Double.parseDouble(Objects.requireNonNull(mProductList.get(productIndex).get(TAG_bunga)));
        double uang_muka = Double.parseDouble(Objects.requireNonNull(newCreditData.get(TAG_uang_muka)));
        double tenor = Double.parseDouble(pickedTenor);
        double anr = (1 - Math.pow((1 + bunga), (-tenor))) / bunga;
        apb = (hargaOTR - uang_muka) / anr;
        int newApb = (int) Math.round(apb);
        return newApb;
    }
}
