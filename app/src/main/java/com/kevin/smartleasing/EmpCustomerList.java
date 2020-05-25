package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EmpCustomerList extends Fragment implements MyCustomerListAdapter.OnCustomerListener {

    RecyclerView recyclerView;

    public EmpCustomerList() {
        // Required empty public constructor
    }

    ArrayList<ArrayList<String>> data;

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
        data = activity.getCustomerList();

//        Buat Adapter baru
        MyCustomerListAdapter myAdapter = new MyCustomerListAdapter(getActivity().getApplicationContext(), data.get(0), data.get(1), this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        return v;
    }

    //    Code saat customer di klik
    @Override
    public void onCustomerClick(int position) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VerifyCustomerIdentity()).addToBackStack(null).commit();
        Toast.makeText(getContext(), "ID_transaksi yang di klik adalah: " + data.get(2).get(position), Toast.LENGTH_LONG).show();
    }
}