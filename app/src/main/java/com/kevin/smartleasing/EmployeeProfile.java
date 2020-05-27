package com.kevin.smartleasing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import androidx.fragment.app.Fragment;

public class EmployeeProfile extends Fragment {

    private static final String TAG_nama_depan_emp = "nama_depan_emp";
    private static final String TAG_nama_belakang_emp = "nama_belakang_emp";
    private static final String TAG_jenis_kelamin = "jenis_kelamin";
    private static final String TAG_nik = "nik";
    private static final String TAG_alamat = "alamat";
    private static final String TAG_tempat_lahir = "tempat_lahir";
    private static final String TAG_tanggal_lahir = "tanggal_lahir";
    private static final String TAG_no_telp = "no_telp";
    private static final String TAG_ID_employee = "ID_employee";

    public EmployeeProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_employee_profile, container, false);
//        Tampilkan profil employee
        EmployeeMain activity = (EmployeeMain) getActivity();
        assert activity != null;
        HashMap<String, String> mEmployeeProfile = activity.getEmployeeProfile();

        TextView profil_txtNama = v.findViewById(R.id.txtNamaEmp);
        TextView profil_txtNIK = v.findViewById(R.id.txtNIKEmployee);
        TextView profil_txtNamaLengkap = v.findViewById(R.id.txtNamaLengkapEmployee);
        TextView profil_txtNoTelp = v.findViewById(R.id.txtNoTelpEmployee);
        TextView profil_txtAlamat = v.findViewById(R.id.txtAlamatEmployee);
        TextView profil_txtTempatLahir = v.findViewById(R.id.txtTempatLahirEmployee);
        TextView profil_txtTglLahir = v.findViewById(R.id.txtTglLahirEmployee);

        String nama_lengkap = mEmployeeProfile.get(TAG_nama_depan_emp) + ' ' + mEmployeeProfile.get(TAG_nama_belakang_emp);

        profil_txtNama.setText(mEmployeeProfile.get(TAG_nama_depan_emp));
        profil_txtNIK.setText(mEmployeeProfile.get(TAG_nik));
        profil_txtNamaLengkap.setText(nama_lengkap);
        profil_txtNoTelp.setText(mEmployeeProfile.get(TAG_no_telp));
        profil_txtAlamat.setText(mEmployeeProfile.get(TAG_alamat));
        profil_txtTempatLahir.setText(mEmployeeProfile.get(TAG_tempat_lahir));
        profil_txtTglLahir.setText(mEmployeeProfile.get(TAG_tanggal_lahir));
        return v;
    }
}
