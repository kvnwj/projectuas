package com.kevin.smartleasing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class CustomerMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String mCustomerID;
    private String url_get_customer_data = "http://kev.inkomtek.co.id/umn/android/getCustomerMainData.php";

    private ArrayList<HashMap<String, String>> mCreditList;
    private HashMap<String, String> mCustomerProfile;

    //    Tags buat credit customer
    private static final String TAG_uang_muka = "uang_muka";
    private static final String TAG_tenor = "tenor";
    private static final String TAG_angsuran_per_bulan = "angsuran_per_bulan";
    private static final String TAG_status = "status";
    private static final String TAG_nama_produk = "nama_produk";
    private static final String TAG_harga_otr = "harga_otr";
    private static final String TAG_bunga = "bunga";
    private static final String TAG_nama_depan_emp = "nama_depan_emp";
    private static final String TAG_nama_belakang_emp = "nama_belakang_emp";

    //    Tags buat profil customer
    private static final String TAG_nama_depan_cust = "nama_depan_cust";
    private static final String TAG_nama_belakang_cust = "nama_belakang_cust";
    private static final String TAG_jenis_kelamin = "jenis_kelamin";
    private static final String TAG_nik = "nik";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_tempat_lahir = "tempat_lahir";
    private static final String TAG_tanggal_lahir = "tanggal_lahir";
    private static final String TAG_no_telp = "no_telp";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        Ambil data ID Customer dari Intent
        Intent intent = getIntent();
        mCustomerID = intent.getStringExtra("ID_customer");

//        Ambil data dari API getCustomerMainData.php menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(CustomerMain.this);
        queue.start();
//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_customer_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Buat JSONObject berdasarkan String response yang diterima
                            JSONObject object = new JSONObject(response);
                            JSONArray credit_list = object.getJSONArray("credit_list");
                            mCreditList = new ArrayList<>();
//                            Looping untuk mengambil data dari JSONArray credit_list
                            for (int i = 0; i < credit_list.length(); i++) {
                                HashMap<String, String> row = new HashMap<>();
                                JSONObject obj = credit_list.getJSONObject(i);

                                row.put(TAG_uang_muka, obj.getString(TAG_uang_muka));
                                row.put(TAG_tenor, obj.getString(TAG_tenor));
                                row.put(TAG_angsuran_per_bulan, obj.getString(TAG_angsuran_per_bulan));
                                row.put(TAG_status, obj.getString(TAG_status));
                                row.put(TAG_nama_produk, obj.getString(TAG_nama_produk));
                                row.put(TAG_harga_otr, obj.getString(TAG_harga_otr));
                                row.put(TAG_bunga, obj.getString(TAG_bunga));
                                row.put(TAG_nama_depan_emp, obj.getString(TAG_nama_depan_emp));
                                row.put(TAG_nama_belakang_emp, obj.getString(TAG_nama_belakang_emp));
                                mCreditList.add(row);
                            }

                            mCustomerProfile = new HashMap<>();
//                            Ambil data profil customer
                            JSONObject profile = object.getJSONObject("profile");
                            mCustomerProfile.put("nama_depan", profile.getString(TAG_nama_depan_cust));
                            mCustomerProfile.put("nama_belakang", profile.getString(TAG_nama_belakang_cust));
                            mCustomerProfile.put(TAG_jenis_kelamin, profile.getString(TAG_jenis_kelamin));
                            mCustomerProfile.put(TAG_nik, profile.getString(TAG_nik));
                            mCustomerProfile.put(TAG_alamat, profile.getString(TAG_alamat));
                            mCustomerProfile.put(TAG_tempat_lahir, profile.getString(TAG_tempat_lahir));
                            mCustomerProfile.put(TAG_tanggal_lahir, profile.getString(TAG_tanggal_lahir));
                            mCustomerProfile.put(TAG_no_telp, profile.getString(TAG_no_telp));

                            Toast.makeText(CustomerMain.this, "Welcome, " + mCustomerProfile.get("nama_depan"), Toast.LENGTH_LONG).show();

//                            Tampilkan nama dan nomor telpon di navigation drawer
                            TextView nav_nama = findViewById(R.id.nav_nama);
                            TextView nav_no_telp = findViewById(R.id.nav_no_telp);
                            String nama_lengkap = mCustomerProfile.get("nama_depan") + ' ' + mCustomerProfile.get("nama_belakang");
                            nav_nama.setText(nama_lengkap);
                            nav_no_telp.setText(mCustomerProfile.get(TAG_no_telp));

//                            Tampilkan fragment Credit List saat pertama kali menggunakan data dari Volley
//                            Fragment ditampilkan setelah Volley sudah mendapatkan Response dan Data sudah OK.
                            if (savedInstanceState == null) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustCreditList()).commit();
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Bila terdapat error
                Log.e("CustomerMain", error.getMessage());
                Toast.makeText(CustomerMain.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID_customer", mCustomerID);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
        navigationView.setCheckedItem(R.id.nav_credit_list);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
//        Bila back ditekan maka tutup dulu navigation drawer bila terbuka
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fm.getBackStackEntryCount() > 0) {
            Log.i("CustomerMain", "Popping Backstack");
            fm.popBackStack();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CustomerMain.this);
            dialog.setTitle("Konfirmasi");
            dialog.setMessage("Apakah Anda yakin mau keluar?");
            dialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("EmployeeMain", "Nothing on backstack, calling super");
                    CustomerMain.super.onBackPressed();
                }
            });
            dialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        Program untuk Navigation View
        switch (menuItem.getItemId()) {
            case R.id.nav_credit_list:
//                Buka fragment customer credit list berdasarkan customer tersebut
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustCreditList()).commit();
                break;
            case R.id.nav_cust_profile:
//                Buka fragment customer profile
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CustomerProfile()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public HashMap<String, String> getCustomerProfile() {
        return mCustomerProfile;
    }

    public ArrayList<HashMap<String, String>> getCreditList() {
        return mCreditList;
    }

    public HashMap<String, String> getSingleCredit(int position) {
        return mCreditList.get(position);
    }
}
