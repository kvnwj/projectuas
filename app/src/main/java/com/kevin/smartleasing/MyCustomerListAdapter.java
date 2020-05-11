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
    private OnCustomerListener mOnCustomerListener;

    //    Class Constructor
    public MyCustomerListAdapter(Context ct, @NonNull ArrayList<String> s1, @NonNull ArrayList<String> s2, OnCustomerListener onCustomerListener) {
        this.nama = s1;
        this.deskripsi = s2;
        this.context = ct;
        this.mOnCustomerListener = onCustomerListener;
    }

    @NonNull
    @Override
    public MyCustomerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(v, mOnCustomerListener);
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nama, deskripsi;
        OnCustomerListener onCustomerListener;

        MyViewHolder(@NonNull View itemView, OnCustomerListener onCustomerListener) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtNama);
            deskripsi = itemView.findViewById(R.id.txtDeskripsi);
            this.onCustomerListener = onCustomerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCustomerListener.onCustomerClick(getAdapterPosition());
        }
    }

    //    Interface untuk click listener
    public interface OnCustomerListener {
        void onCustomerClick(int position);
    }
}