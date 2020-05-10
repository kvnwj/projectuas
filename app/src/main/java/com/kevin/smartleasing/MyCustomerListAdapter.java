package com.kevin.smartleasing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCustomerListAdapter extends RecyclerView.Adapter<MyCustomerListAdapter.MyViewHolder> {

    ArrayList<String> nama, deskripsi;
    Context context;

    //    Class Constructor
    public MyCustomerListAdapter(Context ct, ArrayList<String> s1, ArrayList<String> s2) {
        nama = s1;
        deskripsi = s2;
        context = ct;
    }

    @NonNull
    @Override
    public MyCustomerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(nama.get(position));
        holder.deskripsi.setText(deskripsi.get(position));
    }

    @Override
    public int getItemCount() {
        return nama.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtNama);
            deskripsi = itemView.findViewById(R.id.txtDeskripsi);
        }
    }
}