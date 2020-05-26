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


/**
 * A simple {@link Fragment} subclass.
 */
public class CustCreditList extends Fragment implements MyCreditListAdapter.OnCreditClickListener {

    private RecyclerView recyclerView;
    private ArrayList<HashMap<String, String>> mCreditList;

    public CustCreditList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cust_credit_list, container, false);

//        Code untuk menampilkan recyclerView, mengisi data ke RecyclerView
        recyclerView = v.findViewById(R.id.recyclerView);

//        Ambil data dari CustomerMain.java
        CustomerMain activity = (CustomerMain) getActivity();
        assert null != activity;
        mCreditList = activity.getCreditList();
//        Buat Adapter Baru
        MyCreditListAdapter myAdapter = new MyCreditListAdapter(getActivity().getApplicationContext(), mCreditList, this);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return v;
    }

    //    Code saat customer di klik
    @Override
    public void onCreditClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        CustCreditDetail custCreditDetail = new CustCreditDetail();
        custCreditDetail.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, custCreditDetail).addToBackStack(null).commit();
//        Toast.makeText(getActivity().getApplicationContext(), "Position = "+position, Toast.LENGTH_SHORT).show();
    }
}
