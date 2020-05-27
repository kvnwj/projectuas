package com.kevin.smartleasing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCustomerListAdapter extends RecyclerView.Adapter<MyCustomerListAdapter.MyViewHolder> {
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

    ArrayList<HashMap<String, String>> mTransactionData;
    Context context;
    private OnCustomerListener mOnCustomerListener;

    //    Class Constructor
    public MyCustomerListAdapter(Context ct, @NonNull ArrayList<HashMap<String, String>> mTransactionData, OnCustomerListener onCustomerListener) {
        this.mTransactionData = mTransactionData;
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
        String namaLengkap = mTransactionData.get(position).get(TAG_nama_depan_customer) + " " + mTransactionData.get(position).get(TAG_nama_belakang_customer);
        holder.nama.setText(namaLengkap);
        holder.deskripsi.setText(mTransactionData.get(position).get(TAG_nama_produk));
    }

    @Override
    public int getItemCount() {
        return mTransactionData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nama, deskripsi;
        OnCustomerListener onCustomerListener;

        MyViewHolder(@NonNull View itemView, OnCustomerListener onCustomerListener) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtNamaRow);
            deskripsi = itemView.findViewById(R.id.txtDeskripsiRow);
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