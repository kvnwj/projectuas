package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerProfile extends Fragment {

    private static final String TAG_nik = "nik";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_tempat_lahir = "tempat_lahir";
    private static final String TAG_tanggal_lahir = "tanggal_lahir";
    private static final String TAG_no_telp = "no_telp";

    public CustomerProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customer_profile, container, false);
//        Tampilkan profil customer

        CustomerMain activity = (CustomerMain) getActivity();
        HashMap<String, String> mCustomerProfile = activity.getCustomerProfile();

        TextView profil_txtNama = v.findViewById(R.id.txtNamaRow);
        TextView profil_txtNIK = v.findViewById(R.id.txtNIKCustomer);
        TextView profil_txtNamaLengkap = v.findViewById(R.id.txtNamaLengkapCustomer);
        TextView profil_txtNoTelp = v.findViewById(R.id.txtNoTelpCustomer);
        TextView profil_txtAlamat = v.findViewById(R.id.txtAlamatCustomer);
        TextView profil_txtTempatLahir = v.findViewById(R.id.txtTempatLahirCustomer);
        TextView profil_txtTglLahir = v.findViewById(R.id.txtTglLahirCustomer);

        String nama_lengkap = mCustomerProfile.get("nama_depan") + ' ' + mCustomerProfile.get("nama_belakang");

        profil_txtNama.setText(mCustomerProfile.get("nama_depan"));
        profil_txtNIK.setText(mCustomerProfile.get(TAG_nik));
        profil_txtNamaLengkap.setText(nama_lengkap);
        profil_txtNoTelp.setText(mCustomerProfile.get(TAG_no_telp));
        profil_txtAlamat.setText(mCustomerProfile.get(TAG_alamat));
        profil_txtTempatLahir.setText(mCustomerProfile.get(TAG_tempat_lahir));
        profil_txtTglLahir.setText(mCustomerProfile.get(TAG_tanggal_lahir));
        return v;
    }
}
