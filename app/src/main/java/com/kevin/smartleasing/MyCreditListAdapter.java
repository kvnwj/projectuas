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

public class MyCreditListAdapter extends RecyclerView.Adapter<MyCreditListAdapter.MyViewHolder> {

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

    private ArrayList<HashMap<String, String>> mCreditList;
    private Context context;
    private OnCreditClickListener mOnCreditClickListener;
    private int itemCount;

    MyCreditListAdapter(Context ct, @NonNull ArrayList<HashMap<String, String>> mCreditList, OnCreditClickListener onCreditClickListener) {
        this.mCreditList = mCreditList;
        this.context = ct;
        this.mOnCreditClickListener = onCreditClickListener;

        this.itemCount = mCreditList.size();
    }

    @NonNull
    @Override
    public MyCreditListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.my_row, parent, false);

        return new MyViewHolder(v, mOnCreditClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama.setText(mCreditList.get(position).get(TAG_nama_produk));
        String deskripsi = "Rp. " + mCreditList.get(position).get(TAG_harga_otr);
        if (mCreditList.get(position).get(TAG_harga_otr).equals("dummy")) {
            deskripsi = "";
        }
        holder.deskripsi.setText(deskripsi);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nama, deskripsi;
        OnCreditClickListener onCreditClickListener;

        MyViewHolder(@NonNull View itemView, OnCreditClickListener onCreditClickListener) {
            super(itemView);
            nama = itemView.findViewById(R.id.txtNamaRow);
            deskripsi = itemView.findViewById(R.id.txtDeskripsiRow);
            this.onCreditClickListener = onCreditClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCreditClickListener.onCreditClick(getAdapterPosition());
        }
    }

    //    Interface untuk click listener
    public interface OnCreditClickListener {
        void onCreditClick(int position);
    }
}
