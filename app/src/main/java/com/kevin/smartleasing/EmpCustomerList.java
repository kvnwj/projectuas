package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EmpCustomerList extends Fragment implements MyCustomerListAdapter.OnCustomerListener {

    RecyclerView recyclerView;

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

    public EmpCustomerList() {
        // Required empty public constructor
    }

    ArrayList<HashMap<String, String>> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_emp_customer_list, container, false);

//        Code untuk Menampilkan RecyclerView, mengisi data ke RecyclerView
        recyclerView = v.findViewById(R.id.recyclerView);

//        Ambil data dari Activity EmployeeMain
        EmployeeMain activity = (EmployeeMain) getActivity();
        assert null != activity;
        data = activity.getEmployeeMainData();

//        Buat Adapter baru
        MyCustomerListAdapter myAdapter = new MyCustomerListAdapter(getActivity().getApplicationContext(), data, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return v;
    }

    //    Code saat transaksi di klik
    @Override
    public void onCustomerClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        VerifyCustomerIdentity verifyCustomerIdentity = new VerifyCustomerIdentity();
        verifyCustomerIdentity.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, verifyCustomerIdentity).addToBackStack(null).commit();
    }
}