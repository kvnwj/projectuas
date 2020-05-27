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

public class EmployeeMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private String mEmployeeID;
    private String url_get_employee_main_data = "http://kev.inkomtek.co.id/umn/android/getEmployeeMainData.php";
    private HashMap<String, String> mEmployeeProfile;

    ArrayList<String> nama, deskripsi, ID_transaksi;
    ArrayList<HashMap<String, String>> mTransactionData;

    //    Tag untuk tabel transaksi
    private static final String DATA_TRANSAKSI = "dataTransaksi";
    private static final String TAG_nama_produk = "nama_produk";
    private static final String TAG_nama_depan_customer = "nama_depan_cust";
    private static final String TAG_nama_belakang_customer = "nama_belakang_cust";
    private static final String TAG_ID_transaksi = "ID_transaksi";
    private static final String TAG_harga_otr = "harga_otr";
    private static final String TAG_uang_muka = "uang_muka";
    private static final String TAG_tenor = "tenor";
    private static final String TAG_bunga = "bunga";
    private static final String TAG_angsuran = "angsuran_per_bulan";
    private static final String TAG_status = "status";

    //    Tag untuk profil employee
    private static final String TAG_nama_depan_emp = "nama_depan_emp";
    private static final String TAG_nama_belakang_emp = "nama_belakang_emp";
    private static final String TAG_jenis_kelamin = "jenis_kelamin";
    private static final String TAG_nik = "nik";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_tempat_lahir = "tempat_lahir";
    private static final String TAG_tanggal_lahir = "tanggal_lahir";
    private static final String TAG_no_telp = "no_telp";
    private static final String TAG_ID_employee = "ID_employee";

//    @Override
//    protected void onResume() {
//        Log.i("EmployeeMain", "onResume Called");
//        super.onResume();
//        queue.getCache().clear();
//    }
//
//    @Override
//    protected void onRestart() {
//        Log.i("EmployeeMain", "onRestart Called");
//        super.onRestart();
//        recreate();
//    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

//        Instantiate variables
        nama = new ArrayList<>();
        deskripsi = new ArrayList<>();
        ID_transaksi = new ArrayList<>();
        mTransactionData = new ArrayList<>();

//        Code untuk toolbar, drawer, toggle
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        Ambil data ID Employee dari Intent
        Intent intent = getIntent();
        mEmployeeID = intent.getStringExtra("ID_employee");

//        Ambil data dari API getEmployeeMain.php menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(EmployeeMain.this);
        queue.getCache().clear();
//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_employee_main_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Buat JSONObject berdasarkan String response yang diterima
                            JSONObject object = new JSONObject(response);
                            String adaTransaksi = object.getString("adaTransaksi");
//                            Bila ada transaksi
                            if (adaTransaksi.equals("ada")) {
                                JSONArray data = object.getJSONArray(DATA_TRANSAKSI);
//                            Looping untuk mengambil data transaksi
                                for (int i = 0; i < data.length(); i++) {
//                                obj mewakili satu buah transaksi
                                    JSONObject obj = data.getJSONObject(i);

                                    HashMap<String, String> row = new HashMap<>();
                                    row.put(TAG_nama_depan_customer, obj.getString(TAG_nama_depan_customer));
                                    row.put(TAG_nama_belakang_customer, obj.getString(TAG_nama_belakang_customer));
                                    row.put(TAG_nik, obj.getString(TAG_nik));
                                    row.put(TAG_alamat, obj.getString(TAG_alamat));
                                    row.put(TAG_tempat_lahir, obj.getString(TAG_tempat_lahir));
                                    row.put(TAG_tanggal_lahir, obj.getString(TAG_tanggal_lahir));
                                    row.put(TAG_no_telp, obj.getString(TAG_no_telp));
                                    row.put(TAG_jenis_kelamin, obj.getString(TAG_jenis_kelamin));
                                    row.put(TAG_nama_produk, obj.getString(TAG_nama_produk));
                                    row.put(TAG_harga_otr, obj.getString(TAG_harga_otr));
                                    row.put(TAG_uang_muka, obj.getString(TAG_uang_muka));
                                    row.put(TAG_tenor, obj.getString(TAG_tenor));
                                    row.put(TAG_bunga, obj.getString(TAG_bunga));
                                    row.put(TAG_angsuran, obj.getString(TAG_angsuran));
                                    row.put(TAG_status, obj.getString(TAG_status));
                                    row.put(TAG_ID_transaksi, obj.getString(TAG_ID_transaksi));
                                    mTransactionData.add(row);
                                }
                            } else {
//                                Masukkan data dummy
                                HashMap<String, String> row = new HashMap<>();
                                row.put(TAG_nama_depan_customer, "Tidak Ada Transaksi Aktif");
                                row.put(TAG_nama_belakang_customer, "");
                                row.put(TAG_nik, "");
                                row.put(TAG_alamat, "");
                                row.put(TAG_tempat_lahir, "");
                                row.put(TAG_tanggal_lahir, "");
                                row.put(TAG_no_telp, "");
                                row.put(TAG_jenis_kelamin, "");
                                row.put(TAG_nama_produk, "");
                                row.put(TAG_harga_otr, "");
                                row.put(TAG_uang_muka, "");
                                row.put(TAG_tenor, "");
                                row.put(TAG_bunga, "0.01");
                                row.put(TAG_angsuran, "");
                                mTransactionData.add(row);
                            }
                            mEmployeeProfile = new HashMap<>();
                            mEmployeeProfile.put(TAG_ID_employee, mEmployeeID);
//                            Ambil data profil employee
                            JSONObject profile = object.getJSONObject("profile");
                            mEmployeeProfile.put(TAG_nama_depan_emp, profile.getString(TAG_nama_depan_emp));
                            mEmployeeProfile.put(TAG_nama_belakang_emp, profile.getString(TAG_nama_belakang_emp));
                            mEmployeeProfile.put(TAG_jenis_kelamin, profile.getString(TAG_jenis_kelamin));
                            mEmployeeProfile.put(TAG_nik, profile.getString(TAG_nik));
                            mEmployeeProfile.put(TAG_alamat, profile.getString(TAG_alamat));
                            mEmployeeProfile.put(TAG_tempat_lahir, profile.getString(TAG_tempat_lahir));
                            mEmployeeProfile.put(TAG_tanggal_lahir, profile.getString(TAG_tanggal_lahir));
                            mEmployeeProfile.put(TAG_no_telp, profile.getString(TAG_no_telp));

//                            Tampilkan nama dan nomor telpon di navigation drawer
                            TextView nav_nama = findViewById(R.id.nav_nama_emp);
                            TextView nav_no_telp = findViewById(R.id.nav_no_telp_emp);
                            String nama_lengkap = mEmployeeProfile.get(TAG_nama_depan_emp) + " " + mEmployeeProfile.get(TAG_nama_belakang_emp);
                            nav_nama.setText(nama_lengkap);
                            nav_no_telp.setText(mEmployeeProfile.get(TAG_no_telp));

                            Log.i("EmployeeMain", "Get Data OK");

//                            Tampilkan fragment Customer List saat pertama kali menggunakan data dari Volley
//                            Fragment ditampilkan setelah Volley sudah mendapatkan Response dan data sudah OK

                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmpCustomerList()).commit();

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Bila terdapat error
                Log.e("EmployeeMain", error.getMessage());
                Toast.makeText(EmployeeMain.this, "Silahkan Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID_employee", mEmployeeID);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
//        Menambahkan stringRequest ke requestQueue agar diproses
        stringRequest.setShouldCache(false);
        queue.getCache().remove(url_get_employee_main_data);
        queue.add(stringRequest);
        navigationView.setCheckedItem(R.id.nav_customer_list);
    }

    //    Fungsi yang dipanggil fragment untuk mendapatkan data
    public ArrayList<HashMap<String, String>> getEmployeeMainData() {
        return mTransactionData;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
//        Bila back ditekan maka tutup dulu navigation drawer bila terbuka
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
//            Bila ada BackStack
        } else if (fm.getBackStackEntryCount() > 0) {
            Log.i("EmployeeMain", "Popping Backstack");
            fm.popBackStack();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(EmployeeMain.this);
            dialog.setTitle("Konfirmasi");
            dialog.setMessage("Apakah Anda yakin mau keluar?");
            dialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("EmployeeMain", "Nothing on backstack, calling super");
                    EmployeeMain.super.onBackPressed();
                    finish();
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
//        Program untuk Navigation View, logika klik
        switch (menuItem.getItemId()) {
            case R.id.nav_customer_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmpCustomerList()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmployeeProfile()).commit();
                break;
        }
//        Tutup Drawer setelah user memilih NavigationItem
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public HashMap<String, String> getEmployeeProfile() {
        return mEmployeeProfile;
    }

    public HashMap<String, String> getSingleTransaction(int position) {
        return mTransactionData.get(position);
    }
}