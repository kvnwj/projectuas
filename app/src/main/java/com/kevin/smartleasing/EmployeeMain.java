package com.kevin.smartleasing;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

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

    ArrayList<String> nama, deskripsi, ID_transaksi;
    String url_get_customer = "http://kev.inkomtek.co.id/umn/android/getCustomer.php";

    //    Tag untuk tabel transaksi
    private static final String TAG_customer = "data";
    private static final String TAG_nama_produk = "nama_produk";
    private static final String TAG_nama_depan = "nama_depan";
    private static final String TAG_nama_belakang = "nama_belakang";
    private static final String TAG_ID_transaksi = "ID_transaksi";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_main);

//        Instantiate variables
        nama = new ArrayList<>();
        deskripsi = new ArrayList<>();
        ID_transaksi = new ArrayList<>();

//        Code untuk toolbar, drawer, toggle
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        Ambil data dari API getCustomer.php menggunakan VOLLEY
        RequestQueue queue = Volley.newRequestQueue(EmployeeMain.this);
        queue.start();
//        Buat StringRequest Baru
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_get_customer,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Buat JSONObject berdasarkan String response yang diterima
                            JSONObject object = new JSONObject(response);
                            JSONArray data = object.getJSONArray(TAG_customer);
//                            Looping untuk mengambil semua data dari JSONArray data
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject obj = data.getJSONObject(i);
                                String namaDepan = obj.getString(TAG_nama_depan);
                                String namaBelakang = obj.getString(TAG_nama_belakang);
                                String namaLengkap = namaDepan + " " + namaBelakang;
                                String namaProduk = obj.getString(TAG_nama_produk);
                                String idTransaksi = obj.getString(TAG_ID_transaksi);

                                nama.add(namaLengkap);
                                deskripsi.add(namaProduk);
                                ID_transaksi.add(idTransaksi);
                            }
                            Log.i("EmployeeMain", "Get Data OK");

//                            Tampilkan fragment Customer List saat pertama kali menggunakan data dari Volley
//                            Fragment ditampilkan setelah Volley sudah mendapatkan Response dan data sudah OK
                            if (savedInstanceState == null) {
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EmpCustomerList()).commit();
                            }
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
        });
//        Menambahkan stringRequest ke requestQueue agar diproses
        queue.add(stringRequest);
        navigationView.setCheckedItem(R.id.nav_customer_list);
    }

    //    Fungsi yang dipanggil fragment untuk mendapatkan data
    public ArrayList<ArrayList<String>> getCustomerList() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        data.add(0, nama);
        data.add(1, deskripsi);
        data.add(2, ID_transaksi);
        return data;
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
}