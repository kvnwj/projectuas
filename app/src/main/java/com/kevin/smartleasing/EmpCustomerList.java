package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EmpCustomerList extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> nama, deskripsi;

    public EmpCustomerList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_emp_customer_list, container, false);

//        Coding untuk Menampilkan RecyclerView, mengisi data ke RecyclerView
        recyclerView = v.findViewById(R.id.recyclerView);
//        Ambil data dummy
//        nama = getResources().getStringArray(R.array.contoh_nama);
//        deskripsi = getResources().getStringArray(R.array.contoh_deskripsi);

//        Menambahkan on click listener kepada RecyclerView

//        Membuat String nama dan deskripsi
//        for(int i = 0; i<list_customer.size();i++){
//            HashMap<String, String> hm = list_customer.get(i);
//            nama.add(hm.get(TAG_nama_lengkap));
//            deskripsi.add(hm.get(TAG_nama_produk));
//        }

//        Buat Adapter baru

        MyCustomerListAdapter myAdapter = new MyCustomerListAdapter(getContext(), nama, deskripsi);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}
